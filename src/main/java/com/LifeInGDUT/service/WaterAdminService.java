package com.LifeInGDUT.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LifeInGDUT.dao.WaterAdminDao;
import com.LifeInGDUT.exception.AuthenticationException;
import com.LifeInGDUT.model.Charge;
import com.LifeInGDUT.model.User;
import com.LifeInGDUT.model.Water;
import com.LifeInGDUT.model.WaterAdmin;
import com.LifeInGDUT.util.UserUtil;

@Service
public class WaterAdminService {

	private static final long delay = 1000 * 60 * 60 * 24;

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

	public List<Water> getOrder(int state, int page, int size) {
		return waterAdminDao.getOrder(state, getStart(page, size), size);
	}

	public int getOrderPage(int state, int size) {
		int count = waterAdminDao.getOrderCount(state);
		if (count == 0) {
			return 0;
		} else {
			return (count - 1) / size + 1;
		}
	}

	public void accept(String[] orders, String deliver) {
		final Integer[] ids = new Integer[orders.length];
		for (int i = 0; i < orders.length; i++) {
			ids[i] = Integer.valueOf(orders[i]);
		}
		waterAdminDao.accept(ids, deliver);
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				waterAdminDao.finish(ids);
			}
		}, delay);
	}

	public void delete(int orderId) {
		waterAdminDao.delete(orderId);
	}

	public void finish(String[] orders) {
		Integer[] ids = new Integer[orders.length];
		for (int i = 0; i < orders.length; i++) {
			ids[i] = Integer.valueOf(orders[i]);
		}
		waterAdminDao.finish(ids);
	}

	public void charge(User user, int number) {
		user.setNumber(user.getNumber() + number);
		Charge charge = new Charge();
		charge.setNumber(number);
		charge.setStudentId(user.getStudentId());
		charge.setTime(UserUtil.getCurrentTime());
		waterAdminDao.charge(user, charge);
	}

	public int getChargePage(int size) {
		int count = waterAdminDao.getChargeCount();
		if (count == 0) {
			return 0;
		} else {
			return (count - 1) / size + 1;
		}
	}

	public List<Charge> getChargeRecord(int page, int size) {
		return waterAdminDao.getChargeRecord(getStart(page, size), size);
	}

	private int getStart(int page, int size) {
		return (page - 1) * size;
	}
}
