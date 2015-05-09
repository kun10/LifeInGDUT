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
	
	public List<Repair> selectByStatus(int first, int max, String status){
		return  superDao.getSession().createQuery("from Repair where status = :status ").setString("status", status).setFirstResult(first).setMaxResults(max).list();
	}
}
