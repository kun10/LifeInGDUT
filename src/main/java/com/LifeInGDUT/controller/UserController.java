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

	/* 用户注册操作判断码 */
	private static final int REGISTER = 0;

	/* 用户找回密码操作判断码 */
	private static final int RESET_PASSWORD = 1;

	/* 用户头像的存放的文件夹 */
	private static final String POSITION = "headimg";

	/* 头像路径前缀 */
	private static final String PRE_PATH = "photo/headimg/";

	@Autowired
	private UserService userService;

	/**
	 * 用户登录
	 * 
	 * @param user
	 *            包括学号studentId和密码password
	 * @return 成功:{"state":"success",""};失败:{"state":"fail","reason":错误信息}
	 */
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

	/**
	 * 注册
	 * 
	 * @param user
	 *            包括学号studentId和密码password
	 * @param confirmPassword
	 *            确认密码
	 * @param method
	 *            method=0则为注册，method则为重置密码密码
	 * @return 成功:{"state":"success"};失败:{"state":"fail","reason":错误信息}
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String register(User user, char[] confirmPassword, int method) {
		JSONObject json = new JSONObject();
		try {
			if (method == REGISTER) {
				userService.register(user, confirmPassword);
			} else if (method == RESET_PASSWORD) {
				userService.resetPassword(user, confirmPassword);
			}
		} catch (AuthenticationException e) {
			json.accumulate("state", "fail");
			json.accumulate("reason", e.getMessage());
			return json.toString();
		}
		json.accumulate("state", "success");
		return json.toString();
	}

	/**
	 * 获取用户信息,暂时没用到
	 * 
	 * @param studentId
	 *            学号
	 * @return {"state":"success",""}
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String getUserInfo(String studentId) {
		JSONObject json = new JSONObject();
		json.accumulate("state", "success");
		return userService.getUserById(studentId).getJson(json).toString();
	}

	/**
	 * 用户更新个人信息
	 * 
	 * @param request
	 * @param user
	 *            包括昵称nickName,性别sex,宿舍dormitory,电话phone,头像headImg和个性签名sign
	 * @return 成功{"state":"success"};失败{"state":"fail","reason":错误信息}
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String updateUserInfo(HttpServletRequest request, User user) {
		JSONObject json = new JSONObject();
		String fileName = user.getStudentId() + ".jpg";
		UserUtil.deletePhoto(request, POSITION, fileName);
		try {
			UserUtil.uploadHeadImg(request, POSITION, user.getHeadImg(), fileName);
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("state", "fail");
			json.accumulate("reason", e.getMessage());
			return json.toString();
		}
		userService.updateUserInfo(user, PRE_PATH + fileName);
		json.accumulate("state", "success");
		return json.toString();
	}
}
