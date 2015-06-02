package com.LifeInGDUT.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LifeInGDUT.model.Reply;
import com.LifeInGDUT.model.User;

@Component
public class ReplyDao {
	@Autowired
	private SuperDao superDao;
	
	public void insert(Reply reply){
		superDao.getSession().save(reply);
	}

	public List<Reply> selectByMessage_id(int first,  int max, int message_id) {
		return superDao.getSession().createQuery("from Reply where message_id=" + message_id+" order by time desc").setFirstResult(first).setMaxResults(max).list();
//		superDao.getSession().createSQLQuery("select r.content,r2.content from reply r join reply r2 on r.to_id = r2.id where r.message_id=" + message_id+" order by r.time desc").setFirstResult(first).setMaxResults(max).list();
	}

	public int countBiggerThenId(Integer reply_id, int message_id) {
		Long l = (Long)superDao.getSession().createQuery("select count(*) from Reply where message_id = "+message_id+"and id>="+reply_id).uniqueResult(); 
		return l.intValue();
	}

	public Reply selectOneById(int reply_id) {
		return (Reply)superDao.getSession().createQuery("from Reply where id = "+reply_id).uniqueResult();
	}

	public int selectCountByMessage_Id(int message_id) {
		Long l = (Long)superDao.getSession().createQuery("select count(*) from Reply where message_id="+message_id).uniqueResult(); 
		return l.intValue();
	}

	public Reply selectTo_idReplyById(int id) {
		return (Reply)superDao.getSession().createSQLQuery("select r2.* from reply r1 join reply r2 on r1.to_id = r2.id where r1.id = "+id).uniqueResult();
//		return (Reply)superDao.getSession().createQuery("from Reply where id= 21").uniqueResult();
	}
}
