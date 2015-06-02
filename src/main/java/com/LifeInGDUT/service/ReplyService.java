package com.LifeInGDUT.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LifeInGDUT.dao.ReplyDao;
import com.LifeInGDUT.model.Message;
import com.LifeInGDUT.model.Reply;
import com.LifeInGDUT.model.User;

@Component
public class ReplyService {
	@Autowired
	private ReplyDao rDao;
	
	public void add(String content, String studentId, int message_id, Integer to_id){
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Reply reply = new Reply();
		reply.setContent(content);
		reply.setTime(time);
		Message message = new Message();
		message.setId(message_id);
		reply.setMessage(message);
		User user = new User();
		user.setStudentId(studentId);
		reply.setUser(user);
		if(to_id!=null){
			Reply r = new Reply();
			r.setId(to_id);
			reply.setReply(r);
		}
		rDao.insert(reply);
	}
	
	public List<Reply> getReply(Integer reply_id, int page_size, int message_id){
		int first = 0;
		if(reply_id!=null){
			first = rDao.countBiggerThenId(reply_id);    //当前数据库主键小于等于id的数量
		}
		List<Reply> replys =  rDao.selectByMessage_id(first, page_size, message_id);
		int number;
		for(Reply reply : replys){
			number = setMessageNull(reply) - 1;
			reply.setNumber(number);
			reply.setReply(null);    //朋友圈首页不需要恢复的评论
			reply.getUser().setNumber(0);
			reply.getUser().setPassword(null);
			reply.getUser().setSign(null);
		}
		return replys;
	}
	
	/**
	 * 递归设置message为null
	 * @Title: setMessageNull
	 * @Description: TODO
	 * @param reply
	 * @return 返回回复里面的数量
	 */
	public int setMessageNull(Reply reply){
		int size = 1;
		reply.setMessage(null);
		if(reply.getReply()!=null){
			size += setMessageNull(reply.getReply());
		}
		return size;
	}

	public Reply getReply(int reply_id) {
		Reply reply = rDao.selectOneById(reply_id);
		setMessageNull(reply);
		reply.getUser().setNumber(0);
		reply.getUser().setPassword(null);
		reply.getUser().setSign(null);
		return reply;
	}
}
