package com.LifeInGDUT.service;

import com.LifeInGDUT.dao.NewsAdminDao;
import com.LifeInGDUT.model.NewsAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewsAdminService {
	@Autowired
	private NewsAdminDao nDao;
	
	public boolean check(String name, String password){
		NewsAdmin n = nDao.selectByName(name);
		if(n==null){
			return false;
		}else if(password.equals(n.getPassword())){
			return true;
		}else{
			return false;
		}
	}

	public NewsAdmin getNewsAdmin(String name) {
		return nDao.selectByName(name);
	}
}
