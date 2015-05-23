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
	public void add(String name, String password, String user_id, MultipartFile[] files, String path){
		PreTeam p = new PreTeam();
		User user = new User();
		user.setStudentId(user_id);
		p.setName(name);
		p.setPassword(password);
		p.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		p.setUser(user);
		String url = this.uploadPic(files, path);
		p.setUrl(url);
		p.setIsCheck(0);
		pDao.insert(p);
	}
	
	/**
	 * 
	 * @Title: uploadPic
	 * @Description: 上传申请认证图片
	 * @param files
	 * @param path
	 * @return 返回图片拼凑的路径
	 */
	public String uploadPic(MultipartFile[] files, String path){
		StringBuilder sb = new StringBuilder(); 
		for(MultipartFile file: files){
			long time = System.currentTimeMillis();
			String originalPath = path+"WEB-INF/checkImg/"+time+file.getOriginalFilename();
			try {
				file.transferTo(new File(originalPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
			sb.append("WEB-INF/checkImg/").append(time).append(file.getOriginalFilename()).append(";");         
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
	 * @Description: 申请通过,删除preTeam表数据,新增Team表数据
	 * @param id 
	 */
	public void changeToTeam(int id){
		PreTeam p = pDao.selectById(id);
		teamService.add(p.getName(), p.getPassword(), p.getUser().getStudentId());
		pDao.delete(p);
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

	public void changeToOk(Integer[] ids) {
		for(int id : ids){
			this.changeToTeam(id);
		}
	}
	
	
}
