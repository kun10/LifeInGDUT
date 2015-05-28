package com.LifeInGDUT.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LifeInGDUT.model.Message;
import com.LifeInGDUT.model.NewsAdmin;
import com.LifeInGDUT.model.Team;
import com.LifeInGDUT.model.User;

@Component
public class MessageDao {
	@Autowired
	private SuperDao superDao;

	public void insert(Message m, int section) {
		if(section==1){
			superDao.getSession().get(User.class, m.getUser().getStudentId());
		}else if(section==2){
			superDao.getSession().get(Team.class, m.getTeam().getId());
		}else if(section==3){
			superDao.getSession().get(NewsAdmin.class,m.getNewsAdmin().getId());
		}
		superDao.getSession().save(m);
	}
	public int countBiggerThenId(int id){
		Long l = (Long)superDao.getSession().createQuery("select count(*) from Message where id>="+id).uniqueResult(); 
		return l.intValue();
	}
	
	public List<Message> select(int first, int max){
		return superDao.getSession().createQuery("from Message order by time desc").setFirstResult(first).setMaxResults(max).list();
	}
	
	public int count(){
		Long l = (Long)superDao.getSession().createQuery("select count(*)from Message").uniqueResult(); 
		return l.intValue();
	}

	public void updatePraisesById(int id) {
		superDao.getSession().createSQLQuery("update Message set goods=goods+1 where id = "+id).executeUpdate();
	}
	
	public void insertMessage_User(int message_id, String studentId) {
		superDao.getSession().createSQLQuery("select into message_user (message_id, user_id) values("+message_id+", "+studentId+")").executeUpdate();
	}

	public List<User> selectUserById(int message_id) {
		return superDao.getSession().createQuery("select m.users from Message m where m.id = "+message_id).list();
	}

	public List<Message> selectBySection(int first, int max, int section) {
		return superDao.getSession().createQuery("from Message where section = "+section).setFirstResult(first).setMaxResults(max).list();
	}

	public List<Message> selectByTeam_id(int first, int max, int id) {
		return superDao.getSession().createQuery("from Message where team_id = "+id+"order by time desc").setFirstResult(first).setMaxResults(max).list();
	}
	public List<Integer> selectMessage_idByUser_idFrommessage_user(String studentId) {
		return superDao.getSession().createSQLQuery("select message_id from message_user where user_id = :studentId ").setString("studentId", studentId).list();
	}
}
