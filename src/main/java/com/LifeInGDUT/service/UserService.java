package com.LifeInGDUT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LifeInGDUT.dao.UserDao;
import com.LifeInGDUT.exception.AuthenticationException;
import com.LifeInGDUT.model.User;
import com.LifeInGDUT.util.UserUtil;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public void register(User user, char[] confirmPassword) throws AuthenticationException {
		if (!String.valueOf(user.getPassword()).equals(String.valueOf(confirmPassword))) {
			throw new AuthenticationException("两次密码不一致");
		} else if (userDao.getUserById(user.getStudentId()) != null) {
			throw new AuthenticationException("用户名已存在");
		} else {
			user.setPassword(UserUtil.encryptPassword(user.getPassword()));
			userDao.updateUserInfo(user);
		}
	}

	public User login(User user) throws AuthenticationException {
		User newUser = userDao.getUserById(user.getStudentId());
		if (newUser == null) {
			throw new AuthenticationException("用户名不存在");
		} else {
			if (newUser.equals(user)) {
				return newUser;
			} else {
				throw new AuthenticationException("用户名或密码错误");
			}
		}
	}

	public User resetPassword(User user, char[] confirmPassword) throws AuthenticationException {
		User newUser = userDao.getUserById(user.getStudentId());
		if (!String.valueOf(user.getPassword()).equals(String.valueOf(confirmPassword))) {
			throw new AuthenticationException("两次密码不一致");
		} else if (newUser != null) {
			newUser.setPassword(UserUtil.encryptPassword(user.getPassword()));
			userDao.updateUserInfo(newUser);
			return newUser;
		} else {
			throw new AuthenticationException("该用户不存在");
		}
	}

	public User getUserById(String studentId) {
		return userDao.getUserById(studentId);
	}

	public void updateUserInfo(User user, String headImg) {

		User newUser = userDao.getUserById(user.getStudentId());
		newUser.setNickName(user.getNickName());
		newUser.setHeadImg(headImg);
		newUser.setSex(user.getSex());
		newUser.setDormitory(user.getDormitory());
		newUser.setPhone(user.getPhone());
		newUser.setSign(user.getSign());
		userDao.updateUserInfo(newUser);
	}
}
