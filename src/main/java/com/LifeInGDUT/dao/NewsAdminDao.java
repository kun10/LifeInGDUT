package com.LifeInGDUT.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LifeInGDUT.model.NewsAdmin;

@Component
public class NewsAdminDao {
	@Autowired
	private SuperDao superDao;
	
	public NewsAdmin selectByName(String name){
		return (NewsAdmin)superDao.getSession().createQuery("from NewsAdmin where name = :name").setString("name", name).uniqueResult();
	}
}
