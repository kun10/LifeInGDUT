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
	@Autowired
	private TeamService tService;

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
		int temp = 0;     							//是否成功
		StringBuilder sb1 = new StringBuilder(); 						//原图路径
		StringBuilder sb2 = new StringBuilder();							//压缩图路径
		if(files!=null){
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
				if(ImageUtils.resize(originalPath, pressPath, 700	, 700)){    			 //压缩存放
					temp = 1;						//压缩成功
					System.out.println("temp1="+temp);
					sb2.append("photo/life/fuzzy/").append(time).append(file.getOriginalFilename()).append(";");                                                  //压缩成功的路径
				} else{
					sb2.append("photo/life/clear/").append(time).append(file.getOriginalFilename()).append(";");       											//压缩不成功的路径
				}      
				sb1.append("photo/life/clear/").append(time).append(file.getOriginalFilename()).append(";"); 								//原图路径      
			}
		}
		if(sb1.length()!=0){
			m.setImg(sb1.substring(0, sb1.length()-1));
			m.setImg_small(sb2.substring(0, sb2.length()-1));
		}
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
		int first = 0;
		List<Message> messages = new ArrayList<Message>();
		if(section!=0){
			if(id!=null){
				first = mDao.countBiggerThenId(id, section);    //当前数据库主键小于等于id的数量
			}
			messages =  mDao.selectBySection(first, page_size, section);
		}else{
			if(id!=null){
				first = mDao.countBiggerThenId(id);    //当前数据库主键小于等于id的数量
			}
			messages =  mDao.select(first, page_size);
		} 
		//点赞对应的user的list为空
		setUsersNull(messages);
		if(studentId!=null){
			List<Integer> message_ids = mDao.selectMessage_idByUser_idFrommessage_user(studentId);     //当前学号的学生赞过的生活圈
			for(Message message : messages){
				this.setAuthorNullAndSetNumber(message);
				for(int message_id : message_ids){
					if(message.getId()==message_id){
						message.setHavePraise(1);
						break;
					}
				}
			}
		}else{
			for(Message message : messages){
				this.setAuthorNullAndSetNumber(message);
				message.setHavePraise(0);
			}
		}
		return messages;
	}
	
	public void setAuthorNullAndSetNumber(Message message){
		int section1 = message.getSection();
		if(section1==1){
			User user = message.getUser();
			user.setPassword(null);
			user.setNumber(0);
			user.setSign(null);
		}else if(section1==2){
			message.getTeam().setPassword(null);
			message.getTeam().setUser(null);
		}else if(section1==3){
			message.getNewsAdmin().setPassword(null);
		}
		message.setNumber(mDao.countReplyByMessage_id(message.getId()));
	}

	public void doPraise(int message_id, String studentId, int sign) {
		if(sign==1&&mDao.selectFromTableMessage_user(message_id, studentId)){
			mDao.updatePraisesById(message_id, 1);							//sign=1,Message表中praise_number自增1;sign=0,Message表中praise_number自减1
			mDao.insertMessage_User(message_id, studentId);		
		}
		if(sign==0){
			mDao.updatePraisesById(message_id, 0);
			mDao.deleteMessage_User(message_id, studentId);	
		}
	}

	public List<User> getPraisesUser(int message_id) {
		List<User> users = mDao.selectUserById(message_id);
		for(User user : users){
			user.setNumber(0);
			user.setPassword(null);
			user.setPhone(null);
			user.setSign(null);
		}
		return users;
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
	
	public int getTeamIdByName(String name){
		return tService.getTeamByName(name).getId();
	}

	/**
	 * 
	 * @Title: showOne
	 * @Description: TODO
	 * @param message_id 
	 * @param id
	 * @param studentId  如果是要显示的是student，studentId表示查看的studentId
	 * @param my_studentId  自己的studentId
	 * @param page_size
	 * @param section
	 * @return
	 */
	public List<Message>getOne(Integer message_id, Integer id,String studentId, String my_studentId, int page_size, int section) {
		int first = 0;
		if(message_id!=null){
			first = mDao.countBiggerThenIdBySection(studentId, message_id, id, section);    //当前数据库主键小于等于id的数量
		}
		List<Message> messages =  mDao.selectByOneId(first, page_size, studentId, id, section);
		setUsersNull(messages);
		if(my_studentId!=null){
			List<Integer> message_ids = mDao.selectMessage_idByUser_idFrommessage_user(my_studentId);     //当前学号的学生赞过的生活圈
			for(Message message : messages){
				//每条message中的作者中的密码等重要信息置空
				this.setAuthorNullAndSetNumber(message);
				//当前学号的学生赞过的生活圈里面有这一条message
				for(int messageId : message_ids){
					if(message.getId()==messageId){
						message.setHavePraise(1);
						break;
					}
				}
			}
		}else{
			for(Message message : messages){
				this.setAuthorNullAndSetNumber(message);
				message.setHavePraise(0);
			}
		}
		return messages;
	}
	
}
