package com.LifeInGDUT.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LifeInGDUT.dao.UserDao;
import com.LifeInGDUT.dao.WaterDao;
import com.LifeInGDUT.exception.IllegalOperationException;
import com.LifeInGDUT.exception.LackInfoException;
import com.LifeInGDUT.exception.WaterNotEnoughException;
import com.LifeInGDUT.model.User;
import com.LifeInGDUT.model.Water;
import com.LifeInGDUT.util.UserUtil;

@Service
public class WaterService {

	@Autowired
	private WaterDao waterDao;

	@Autowired
	private UserDao userDao;

	public void placeOrder(User user, Water water) throws Exception {
		if (!UserUtil.isPerfect(user)) {
			throw new LackInfoException("请先完善个人信息");
		} else if (user.getNumber() < 0) {
			throw new WaterNotEnoughException("剩余水票不足");
		} else {
			waterDao.placeOrder(water);
			userDao.updateUserInfo(user);
		}
	}

	public void deleteOrder(String studentId, int id)
			throws IllegalOperationException {
		Water water = waterDao.getOrderById(id);
		if (water == null || !water.getStudentId().equals(studentId)) {
			throw new IllegalOperationException("非法操作！！");
		} else {
			waterDao.deleteOrder(id);
		}
	}

	public List<Water> getOrder(String studentId, int state, int page, int size) {
		return waterDao.getOrder(studentId, state, getStart(page, size), size);
	}

	public int getStart(int page, int size) {
		return (page - 1) * size;
	}

}
