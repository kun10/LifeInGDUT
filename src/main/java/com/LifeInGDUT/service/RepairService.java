package com.LifeInGDUT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LifeInGDUT.dao.RepairDao;
import com.LifeInGDUT.model.Repair;

@Component	
public class RepairService {
	@Autowired
	private RepairDao repairDao;
	
	public void add(Repair repair){
		repair.setStatus("处理中");
		repairDao.insert(repair);
	}
	
}
