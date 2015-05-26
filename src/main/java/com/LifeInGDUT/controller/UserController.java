package com.LifeInGDUT.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.LifeInGDUT.exception.AuthenticationException;
import com.LifeInGDUT.model.User;
import com.LifeInGDUT.service.UserService;
import com.LifeInGDUT.util.UserUtil;

@Controller
public class UserController {

	private static final int REGISTER = 0;

	private static final int RESET_PASSWORD = 1;

	private static final String POSITION = "headimg";

	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String login(User user) {
		JSONObject json = new JSONObject();
		try {
			json.accumulate("state", "success");
			return userService.login(user).getJson(json).toString();
		} catch (AuthenticationException e) {
			json.accumulate("state", "fail");
			json.accumulate("reason", e.getMessage());
			return json.toString();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String register(User user, char[] confirmPassword, int method) {
		JSONObject json = new JSONObject();
		if (!String.valueOf(user.getPassword()).equals(String.valueOf(confirmPassword))) {
			json.accumulate("state", "fail");
			json.accumulate("reason", "两次密码不一致");
			return json.toString();
		}
		try {
			if (method == REGISTER) {
				userService.register(user);
			} else if (method == RESET_PASSWORD) {
				userService.resetPassword(user);
			}
		} catch (AuthenticationException e) {
			json.accumulate("state", "fail");
			json.accumulate("reason", e.getMessage());
			return json.toString();
		}
		json.accumulate("state", "success");
		return json.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String getUserInfo(String studentId) {
		JSONObject json = new JSONObject();
		json.accumulate("state", "success");
		return userService.getUserById(studentId).getJson(json).toString();
	}

	@ResponseBody
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String updateUserInfo(HttpServletRequest request, User user) {
		JSONObject json = new JSONObject();
		try {
			String fileName = user.getStudentId() + ".jpg";
			UserUtil.deletePhoto(request, POSITION, fileName);
			UserUtil.uploadHeadImg(request, POSITION, user.getHeadImg(), fileName);
			User oldUser = userService.getUserById(user.getStudentId());
			user.setNumber(oldUser.getNumber());
			user.setPassword(oldUser.getPassword());
			user.setHeadImg(fileName);
			System.out.println(user);
			userService.updateUserInfo(user);
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("state", "fail");
			json.accumulate("reason", e.getMessage());
			return json.toString();
		}
		json.accumulate("state", "success");
		return json.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public String getUserById(String studentId) {
		System.out.println("dasfsd");
		System.out.println(userService.getUserById(studentId));
		return userService.getUserById(studentId).toString();

	}
}
