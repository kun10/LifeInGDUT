package com.LifeInGDUT.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.LifeInGDUT.dao.PreTeamDao;
import com.LifeInGDUT.model.PreTeam;
import com.LifeInGDUT.model.Team;
import com.LifeInGDUT.model.User;
import com.LifeInGDUT.util.ImageUtils;

@Component
public class PreTeamService {
	@Autowired
	private PreTeamDao pDao;
	@Autowired
	private TeamService teamService;
	
	/**
	 * 
	 * @Title: add
	 * @Description: 社团用户申请
	 * @param name
	 * @param password
	 * @param user_id
	 * @param files
	 * @param path
	 */
	public void add(String name, String password, String user_id, MultipartFile[] files, String path, MultipartFile head){
		PreTeam p = new PreTeam();
		User user = new User();
		user.setStudentId(user_id);
		p.setName(name);
		p.setPassword(password);
		p.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		p.setUser(user);
		String url =  this.uploadPic(files, path+"WEB-INF/checkImg/", "checkImg/");
		MultipartFile[] fs = new MultipartFile[1];
		fs[0] = head;
		String headImg =  this.uploadPic(fs, path+"photo/preTeamHeadImg/", "photo/preTeamHeadImg/");
		p.setUrl(url);
		p.setHeadImg(headImg);
		p.setIsCheck(0);
		pDao.insert(p);
	}
	
	/**
	 * 
	 * @Title: uploadPic
	 * @Description: 上传多张图片并返回图片名的拼凑字符串
	 * @param files
	 * @param path 上传路径
	 * @return 
	 */
	public String uploadPic(MultipartFile[] files, String path, String pre){
		StringBuilder sb = new StringBuilder(); 
		for(MultipartFile file: files){
			long time = System.currentTimeMillis();
			String originalPath = path+time+file.getOriginalFilename();
			try {
				file.transferTo(new File(originalPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
			sb.append(pre).append(time).append(file.getOriginalFilename()).append(";");         
		}
		if(sb.length()!=0){
			return sb.substring(0, sb.length()-1);
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: isNameExist
	 * @Description: 申请社团名字是否已经被申请或者已经被注册
	 * @param name
	 * @return
	 */
	public boolean isNameExist(String name){
		PreTeam preTeam = pDao.selectByName(name);
		Team team = teamService.getTeamByName(name);
		if(preTeam!=null||team!=null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: changeToTeam
	 * @Description: 申请通过,删除preTeam表数据,新增Team表数据,删除提交的证明材料，删除photo/preTeamHeadImg/下头像，头像复制到photo/teamHeadImg/下
	 * @param id 
	 */
	public void changeToTeam(String name, String path){
		PreTeam p = pDao.selectByName(name);
		String headImg = p.getHeadImg();
		String urls = p.getUrl();
		System.out.println("headImg="+headImg);
		String newHeadImg = "photo/teamHeadImg/"+headImg.substring(21, headImg.length());
		ImageUtils.copyFile(path+headImg, path+newHeadImg);
		ImageUtils.deleteFile(path+headImg);
		delete(urls, path);   //删除证明
		teamService.add(p.getName(), p.getPassword(), p.getUser().getStudentId(), newHeadImg);
		pDao.delete(p);
	}
	
	public void delete(String urls, String path){
		int index = urls.indexOf(';');
		if(index!=-1){
			String str1 = urls.substring(0, index);
			System.out.println(str1);
			ImageUtils.deleteFile(path+"WEB-INF/"+str1);
			urls = urls.substring(index+1, urls.length());
			index = urls.indexOf(';');
			if(index!=-1){
				String str2 = urls.substring(0, index);
				System.out.println(str2);
				ImageUtils.deleteFile(path+"WEB-INF/"+str2);
				urls = urls.substring(index+1, urls.length());
			}
		}
		System.out.println(urls);
		ImageUtils.deleteFile(path+"WEB-INF/"+urls);
	}
	
	/**
	 * 
	 * @Title: getPreTeam
	 * @Description: 审核时查看申请
	 * @param pageNumber
	 * @param page_size
	 * @return
	 */
	public List<PreTeam> getPreTeam(int pageNumber, int page_size, int isCheck){
		int first = (pageNumber-1)*page_size;
		List<PreTeam> preTeams = pDao.select(first, page_size, isCheck);
		for(PreTeam p : preTeams){
			p.setPassword(null);
			p.getUser().setPassword(null);
		}
		return preTeams;
	}
	
	/**
	 * 
	 * @Title: getAllPageCount
	 * @Description: 取得相应isCheck的总页数
	 * @param page_size
	 * @param isCheck 0代表未审核，1代表审核不通过
	 * @return
	 */
	public int getAllPageCount(int page_size, int isCheck){
		int sum = 0;
		if(isCheck==0){
			sum = pDao.count(0);
		}else if(isCheck==1){
			sum = pDao.count(1);
		}
		if(sum==0)
			return 0;
		else
			return (sum-1)/page_size+1;
	}

	public void changeToOk(String[] names, String path) {
		for(String name : names){
			this.changeToTeam(name, path);
		}
	}

	public void changeToNotOk(String[] names, String path) {
		for(String name : names){
			pDao.updateIsCheckByName(name);
		}
	}

	public void deleteOnePreTeam(int id, String path) {
		PreTeam p = pDao.selectById(id);
		if(p.getIsCheck()==1){
			String headImg = p.getHeadImg();
			String urls = p.getUrl();
			this.delete(urls, path);				//删除证明材料
			this.delete(headImg, path);		//删除头像
			pDao.delete(p);
		}
	}
	
	
}
