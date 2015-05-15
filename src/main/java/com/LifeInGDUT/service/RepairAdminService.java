package com.LifeInGDUT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LifeInGDUT.dao.RepairAdminDao;
import com.LifeInGDUT.model.RepairAdmin;

@Component
public class RepairAdminService {
	@Autowired
	private RepairAdminDao rDao;
	
	public boolean login(String name, String password){
		RepairAdmin admin = rDao.selectByName(name);
		if(admin==null){
			return false;
		}else if(admin.getPassword().equals(password)){
			return true;
		}else{
			return false;
		}
	}
}
