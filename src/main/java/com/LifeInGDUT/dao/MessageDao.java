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
	
	public int countBiggerThenId(int id, int section) {
		Long l = (Long)superDao.getSession().createQuery("select count(*) from Message where id>="+id+" and section = "+section).uniqueResult(); 
		return l.intValue();
	}
	
	public List<Message> select(int first, int max){
		return superDao.getSession().createQuery("from Message order by time desc").setFirstResult(first).setMaxResults(max).list();
	}
	
	public int count(){
		Long l = (Long)superDao.getSession().createQuery("select count(*)from Message").uniqueResult(); 
		return l.intValue();
	}

	public void updatePraisesById(int id, int sign) {
		if(sign==1){
			superDao.getSession().createSQLQuery("update message set praise_number=praise_number+1 where id = "+id).executeUpdate();
		}else if(sign==0){
			superDao.getSession().createSQLQuery("update message set praise_number=praise_number-1 where id = "+id).executeUpdate();
		}
	}
	
	public void insertMessage_User(int message_id, String studentId) {
		superDao.getSession().createSQLQuery("insert into message_user (message_id, user_id) values("+message_id+", "+studentId+")").executeUpdate();
	}

	public List<User> selectUserById(int message_id) {
		return superDao.getSession().createQuery("select m.users from Message m where m.id = "+message_id).list();
	}

	public List<Message> selectBySection(int first, int max, int section) {
		return superDao.getSession().createQuery("from Message where section = "+section+"order by time desc").setFirstResult(first).setMaxResults(max).list();
	}

	public List<Message> selectByTeam_id(int first, int max, int id) {
		return superDao.getSession().createQuery("from Message where team_id = "+id+"order by time desc").setFirstResult(first).setMaxResults(max).list();
	}
	public List<Integer> selectMessage_idByUser_idFrommessage_user(String studentId) {
		return superDao.getSession().createSQLQuery("select message_id from message_user where user_id = :studentId ").setString("studentId", studentId).list();
	}
	public void deleteMessage_User(int message_id, String studentId) {
		superDao.getSession().createSQLQuery("delete from message_user where message_id = "+message_id+" and user_id = "+studentId).executeUpdate();
	}
	public boolean selectFromTableMessage_user(int message_id, String studentId) {
		if(superDao.getSession().createSQLQuery("select * from message_user where message_id = "+message_id+" and user_id = "+studentId).uniqueResult()!=null){
			return false;
		}
		return true;
	}
	
	public int countReplyByMessage_id(int message_id){
		Long l = (Long)superDao.getSession().createQuery("select count(*)from Reply r where r.message.id = "+message_id).uniqueResult(); 
		return l.intValue();
	}
	public int countBiggerThenIdByStudentId(Integer message_id, String studentId) {
		Long l = (Long)superDao.getSession().createQuery("select count(m.*) from Message m where m.id>="+message_id+" and m.user.studentId = :studentId").setString("studentId", studentId).uniqueResult(); 
		return l.intValue();
	}
	public List<Message> selectByStudentId(int first, int page_size, String studentId) {
		return superDao.getSession().createQuery("from Message m where m.user.studentId = :studentId order by time desc").setString("studentId", studentId).setFirstResult(first).setMaxResults(page_size).list();
	}
	
	public List<Message> selectByOneId(int first, int page_size, String studentId, Integer id, int section) {
		System.out.println("id="+id);
		System.out.println("section="+section);
		String sql = null;
		if(section==1){
			sql ="from Message m where m.user.studentId = '"+studentId+"' order by time desc";
		}else if(section==2){
			sql = "from Message m where m.team.id = "+id+" order by time desc";
		}else if(section==3){
			sql = "from Message m where m.newsAdmin.id ="+id+" order by time desc";
		}
		return superDao.getSession().createQuery(sql).setFirstResult(first).setMaxResults(page_size).list();
	}
	public int countBiggerThenIdBySection(String studentId, Integer message_id, Integer id, int section) {
		String sql = null;
		if(section==1){
			sql ="select count(*) from Message m where m.id>="+message_id+" and m.user.studentId = '"+studentId+"'";
		}else if(section==2){
			sql = "select count(*) from Message m where m.id>="+message_id+" and m.team.id = "+id;
		}else if(section==3){
			sql = "select count(*) from Message m where m.id>="+message_id+" and m.newsAdmin.id = "+id;
		}
		Long l = (Long)superDao.getSession().createQuery(sql).uniqueResult(); 
		return l.intValue();
	}

}
