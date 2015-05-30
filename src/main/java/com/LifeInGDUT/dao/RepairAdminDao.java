package com.LifeInGDUT.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LifeInGDUT.model.RepairAdmin;

@Component
public class RepairAdminDao {
	@Autowired
	private SuperDao superDao;
	
	public RepairAdmin selectByName(String name){
		return (RepairAdmin)superDao.getSession().createQuery("from RepairAdmin where name = :name ").setString("name", name).uniqueResult();
	}
}
