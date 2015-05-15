package com.LifeInGDUT.service;

import java.text.SimpleDateFormat;
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
		repair.setState("未处理");
		String submitTime = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new java.util.Date());
		repair.setSubmitTime(submitTime);
		System.out.println(repair.getId());
		repairDao.insert(repair);
	}
	
	/**
	 * 根据state分页查询数据
	 * @param pageNumber	页码
	 * @param page_size	每一页显示数量
	 * @param state
	 * @return
	 */
	public List<Repair> getRepairByState(int pageNumber, int page_size, String state){
		int first = (pageNumber-1)*page_size;
		return repairDao.selectByState(first, page_size, state);
	}
	
	/**
	 * 根据state和studentId分页查询数据
	 * @param studentId
	 * @param pageNumber
	 * @param page_size
	 * @param state
	 * @return
	 */
	public List<Repair> getRepairByState(String studentId, int pageNumber, int page_size, String state){
		int first = (pageNumber-1)*page_size;
		return repairDao.selectByState(studentId, first, page_size, state);
	}
	
	
	public int getAllPageCount(int page_size){
		int sum = repairDao.count();
		if(sum==0)
			return 0;
		else
			return (sum-1)/page_size+1;
	}

	public void delete(int id) {
		Repair r = new Repair();
		r.setId(id);
		repairDao.delete(r);
	}

	public Repair getRepairById(int id) {
		return repairDao.selectById(id);
	}
	
	public void changeState(Integer[] ids){
		
	}
}
