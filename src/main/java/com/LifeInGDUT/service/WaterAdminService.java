package com.LifeInGDUT.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LifeInGDUT.dao.WaterAdminDao;
import com.LifeInGDUT.exception.AuthenticationException;
import com.LifeInGDUT.model.Charge;
import com.LifeInGDUT.model.User;
import com.LifeInGDUT.model.WaterAdmin;

@Service
public class WaterAdminService {

	@Autowired
	private WaterAdminDao waterAdminDao;

	public WaterAdmin login(WaterAdmin admin) throws AuthenticationException {
		if (admin.getUserName().equals("") || admin.getPassword().length == 0) {
			throw new AuthenticationException("用户名和密码不能为空");
		} else {
			WaterAdmin newAdmin = waterAdminDao.checkAdmin(admin);
			if (newAdmin == null) {
				throw new AuthenticationException("管理员账号不存在");
			} else {
				if (!newAdmin.equals(admin)) {
					throw new AuthenticationException("用户名或密码错误");
				} else {
					return newAdmin;
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public List getOrder(int state, int page, int size) {
		return waterAdminDao.getOrder(state, getStart(page, size), size);
	}

	public void accept(Integer[] orders, String deliver) {
		waterAdminDao.accept(orders, deliver);
	}

	public void delete(String orderId) {
		waterAdminDao.delete(Integer.parseInt(orderId));
	}

	public void finish(Integer[] orders) {
		waterAdminDao.finish(orders);

	}

	public void charge(User user) {
		waterAdminDao.charge(user);
	}

	public List<Charge> getChargeRecord(int page, int size) {
		return waterAdminDao.getChargeRecord(getStart(page, size), size);
	}

	private int getStart(int page, int size) {
		return (page - 1) * size;
	}
}
