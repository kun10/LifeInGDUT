package com.LifeInGDUT.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LifeInGDUT.model.Repair;

@Component
public class RepairDao {
	@Autowired
	private SuperDao superDao;
	
	public void insert(Repair repair){
		superDao.getSession().save(repair);
	}
	
	public List<Repair> selectByState(int first, int max, String state){
		return  superDao.getSession().createQuery("from Repair where state = :state ").setString("state", state).setFirstResult(first).setMaxResults(max).list();
	}
	
	public List<Repair> selectByStudentId(String studentId, int first, int max){
		return  superDao.getSession().createQuery("from Repair where studentId = :studentId order by submitTime desc").setString("studentId", studentId).setFirstResult(first).setMaxResults(max).list();
	}

	public int count(String state) {
		Long l =  (Long)superDao.getSession().createQuery("select count(*) from Repair where state = :state").setString("state", state).uniqueResult();
		return l.intValue();
	}

	public void delete(Repair r) {
		superDao.getSession().delete(r);
	}

	public Repair selectById(int id) {
		return (Repair)superDao.getSession().get(Repair.class, id);
	}

	public void updateStateById(int id, String state) {
		superDao.getSession().createSQLQuery("update repair set state = :state where id = "+id).setString("state", state).executeUpdate();
		superDao.getSession().clear();
	}

	public Repair selectByStudentIdAndType(String type, String studentId) {
		return (Repair)superDao.getSession().createQuery("from Repair where type = :type and studentId = :studentId and state = :state").setString("type", type).setString("studentId", studentId).setString("state", "未处理").uniqueResult();
	}
}
