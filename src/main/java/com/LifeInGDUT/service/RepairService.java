package com.LifeInGDUT.service;

import java.util.List;

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
	
	/**
	 * 根据status分页查询数据
	 * @param pageNumber	页码
	 * @param page_size	每一页显示数量
	 * @param status
	 * @return
	 */
	public List<Repair> getRepairByStatus(int pageNumber, int page_size, String status){
		int first = (pageNumber-1)*page_size;
		return repairDao.selectByStatus(first, page_size, status);
	}
	
}
