package com.LifeInGDUT.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.LifeInGDUT.dao.MessageDao;
import com.LifeInGDUT.model.Message;
import com.LifeInGDUT.model.NewsAdmin;
import com.LifeInGDUT.model.Repair;
import com.LifeInGDUT.model.Team;
import com.LifeInGDUT.model.User;
import com.LifeInGDUT.util.ImageUtils;

@Component
public class MessageService {
	@Autowired
	private MessageDao mDao;

	public void add(String content, int section, MultipartFile[] files, String path, Integer id, String studentId) {
		Message m = new Message();
		m.setContent(content);
		m.setSection(section);
		if(section==1){
			User user = new User();
			user.setStudentId(studentId);
			m.setUser(user);
		}else if(section==2){
			Team team = new Team();
			team.setId(id);
			m.setTeam(team);
		}else if(section==3){
			NewsAdmin n = new NewsAdmin();
			n.setId(id);
			m.setNewsAdmin(n);
		}
		StringBuilder sb = new StringBuilder(); 
		for(MultipartFile file: files){
			long time = System.currentTimeMillis();
			String originalPath = path+"photo/life/clear/"+time+file.getOriginalFilename();   //原图存放路径和图片名
			String pressPath = path+"photo/life/fuzzy/"+time+file.getOriginalFilename();		//压缩图片存放路径和图片名
			System.out.println("origina="+originalPath);
			System.out.println("press="+pressPath);	
			try {
				file.transferTo(new File(originalPath));													//原图存放
			} catch (IOException e) {
				e.printStackTrace();
			}
			ImageUtils.resize(originalPath, pressPath, 700	, 700);                         //压缩存放
			sb.append("photo/life/clear/").append(time).append(file.getOriginalFilename()).append(";");                                                       //拼凑几张图片路径
		}
		if(sb.length()!=0){
			m.setImg(sb.substring(0, sb.length()-1));
			String small_path = sb.substring(0, sb.length()-1).replace("clear", "fuzzy");
			m.setImg_small(small_path);
		}
		System.out.println(sb.toString());
		m.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
		m.setPraise_number(0);
		mDao.insert(m, section);
	}
	
	public int getAllPageCount(int page_size){
		int sum = mDao.count();
		if(sum==0)
			return 0;
		else
			return (sum-1)/page_size+1;
	}
	
	public List<Message> getMessages(Integer id, String studentId, int page_size, int section){
		List<Integer> message_ids = mDao.selectMessage_idByUser_idFrommessage_user(studentId);
		int first = 0;
		if(id!=null){
			first = mDao.countBiggerThenId(id);    //当前数据库主键小于等于id的数量
		}
		List<Message> messages = new ArrayList<Message>();
		if(section!=0){
			messages =  mDao.selectBySection(first, page_size, section);
		}else{
			messages =  mDao.select(first, page_size);
		} 
		//点赞对应的user的list为空
		setUsersNull(messages);
		if(studentId!=null){
			for(Message message : messages){
				int section1 = message.getSection();
				if(section1==1){
					User user = message.getUser();
					user.setPassword(null);
					user.setNumber(0);
					user.setSign(null);
				}else if(section1==2){
					message.getTeam().setPassword(null);
				}else if(section1==3){
					message.getNewsAdmin().setPassword(null);
				}
				for(Integer message_id : message_ids){
					if(message.getId()==message_id){
						message.setHavePraise(1);
						break;
					}
				}
			}
		}else{
			for(Message message : messages){
				message.setHavePraise(0);
			}
		}
		return messages;
	}

	public void addPraise(int message_id, String studentId) {
		mDao.updatePraisesById(message_id);							//Message表中praise_number自增1
		mDao.insertMessage_User(message_id, studentId);		//关联表加关联
	}

	public List<String> getPraisesUser(int message_id) {
		List<User> users = mDao.selectUserById(message_id);
		List<String> names = new ArrayList<String>();
		for(User user : users){
			names.add(user.getNickName());
		}
		return names;
	}

	public List<Message> getMessages(int pageNumber, int page_size, int section) {
		int first = (pageNumber-1)*page_size;
		List<Message> messages = mDao.selectBySection(first, page_size, section);
		setUsersNull(messages);
		return messages;
	}

	public void setUsersNull(List<Message> messages){
		for(Message m : messages){
			m.setUsers(null);
		}
	}

	public List<Message> getMessagesFromTeam(int pageNumber, int page_size, int team_id) {
		int first = (pageNumber-1)*page_size;
		return mDao.selectByTeam_id(first, page_size, team_id);
	}
	
}
