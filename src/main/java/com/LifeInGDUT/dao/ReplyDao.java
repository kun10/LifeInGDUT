package com.LifeInGDUT.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LifeInGDUT.model.Reply;

@Component
public class ReplyDao {
	@Autowired
	private SuperDao superDao;
	
	public void insert(Reply reply){
		superDao.getSession().save(reply);
	}

	public List<Reply> selectByMessage_id(int first, int max, int message_id) {
		return superDao.getSession().createQuery("from Reply where message_id=" + message_id+" order by time desc").setFirstResult(first).setMaxResults(max).list();
	}

	public int countBiggerThenId(Integer reply_id) {
		Long l = (Long)superDao.getSession().createQuery("select count(*) from Reply where id>="+reply_id).uniqueResult(); 
		return l.intValue();
	}

	public Reply selectOneById(int reply_id) {
		return (Reply)superDao.getSession().createQuery("from Reply where id = "+reply_id).uniqueResult();
	}
}
