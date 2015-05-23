package com.LifeInGDUT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LifeInGDUT.dao.PreTeamAdminDao;
import com.LifeInGDUT.model.PreTeamAdmin;

@Component
public class PreTeamAdminService {
	@Autowired
	private PreTeamAdminDao pDao;
	
	public boolean login(String name, String password){
		PreTeamAdmin admin =  pDao.selectByName(name);
		if(admin==null){
			return false;
		}else if(password.equals(admin.getPassword())){
			return true;
		}else{
			return false;
		}
	}
}
