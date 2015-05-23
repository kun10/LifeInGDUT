package com.LifeInGDUT.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LifeInGDUT.dao.ReplyDao;
import com.LifeInGDUT.model.Message;
import com.LifeInGDUT.model.Reply;

@Component
public class ReplyService {
	@Autowired
	private ReplyDao rDao;
	
	public void add(String content, String userName, int message_id, Integer to_id){
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Reply reply = new Reply();
		reply.setContent(content);
		reply.setTime(time);
		Message message = new Message();
		message.setId(message_id);
		reply.setMessage(message);
		reply.setUserName(userName);
		if(to_id!=null){
			Reply r = new Reply();
			r.setId(to_id);
			reply.setReply(r);
		}
		rDao.insert(reply);
	}
	
	public List<Reply> getReply(Integer pageNumber, int page_size, int message_id){
		if(pageNumber==null){
			pageNumber = 1;
		}
		int first = (pageNumber-1)*page_size;
		List<Reply> replys =  rDao.selectByMessage_id(first, page_size, message_id);
		for(Reply reply : replys){
			setMessageNull(reply);
		}
		return replys;
	}
	
	/**
	 * 递归设置message为null
	 * @Title: setMessageNull
	 * @Description: TODO
	 * @param reply
	 * @return
	 */
	public Reply setMessageNull(Reply reply){
		reply.setMessage(null);
		if(reply.getReply()!=null){
			setMessageNull(reply.getReply());
		}
		return reply;
	}
}
