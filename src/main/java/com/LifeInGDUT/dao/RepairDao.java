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
	
	public List<Repair> selectByState(String studentId, int first, int max, String state){
		return  superDao.getSession().createQuery("from Repair where state = :state and studentId = :studentId").setString("state", state).setString("studentId", studentId).setFirstResult(first).setMaxResults(max).list();
	}

	public int count() {
		Long l =  (Long)superDao.getSession().createQuery("select count(*) from Repair").uniqueResult();
		return l.intValue();
	}

	public void delete(Repair r) {
		superDao.getSession().delete(r);
	}

	public Repair selectById(int id) {
		return (Repair)superDao.getSession().get(Repair.class, id);
	}
}
