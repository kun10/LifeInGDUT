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
		String submitTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
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
	public List<Repair> getRepairByState(int pageNumber, int page_size, int state){
		int first = (pageNumber-1)*page_size;
		if(state==1){
			return repairDao.selectByState(first, page_size, "未处理");
		}else {
			return repairDao.selectByState(first, page_size, "已处理");
		}
	}
	
	/**
	 * 根据studentId分页查询数据
	 * @param studentId
	 * @param pageNumber
	 * @param page_size
	 * @param state
	 * @return
	 */
	public List<Repair> getRepairByStudentId(String studentId, int pageNumber, int page_size){
		int first = (pageNumber-1)*page_size;
		List<Repair> repairs = repairDao.selectByStudentId(studentId, first, page_size);
		return repairs;
	}
	
	
	public int getAllPageCount(int page_size, int state){
		int sum = 0;
		if(state==1){
			sum = repairDao.count("未处理");
		}else if(state==2){
			sum = repairDao.count("已处理");
		}
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
		for(int id : ids){
			repairDao.updateStateById(id, "已处理");
		}
	}

	public boolean isRepeat(String type,String studentId) {
		if(repairDao.selectByStudentIdAndType(type , studentId)!=null){
			return true;
		}else{
			return false;
		}
	}
}
