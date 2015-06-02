package com.LifeInGDUT.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LifeInGDUT.model.PreTeamAdmin;

@Component
public class PreTeamAdminDao {
	@Autowired
	private SuperDao superDao;
	
	public PreTeamAdmin selectByName(String name){
		return (PreTeamAdmin)superDao.getSession().createQuery("from PreTeamAdmin where name = :name").setString("name", name).uniqueResult();
	}
	
	
}
