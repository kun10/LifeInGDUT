package com.LifeInGDUT.controller;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.LifeInGDUT.exception.IllegalOperationException;
import com.LifeInGDUT.model.User;
import com.LifeInGDUT.model.Water;
import com.LifeInGDUT.service.UserService;
import com.LifeInGDUT.service.WaterService;

@Controller
public class WaterController {

	/* 每页显示的数目 */
	private static final int SIZE = 10;

	@Autowired
	private WaterService waterService;

	@Autowired
	private UserService userService;

	/**
	 * 学生叫水
	 * 
	 * @param password
	 *            密码
	 * @param water
	 *            包括学号studentId,密码password,数量number
	 * @return 成功{"state":"success"};失败{"state":"fail","reason":错误信息}
	 */
	@ResponseBody
	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String placeOrder(char[] password, Water water) {
		JSONObject json = new JSONObject();
		User user = new User();
		user.setPassword(password);
		user.setStudentId(water.getStudentId());
		try {
			user = userService.login(user);
			waterService.placeOrder(user, water);
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("state", "fail");
			json.accumulate("reason", e.getMessage());
			return json.toString();
		}
		json.accumulate("state", "success");
		return json.toString();
	}

	/**
	 * 学生获取水单记录
	 * 
	 * @param studentId
	 *            学号
	 * @param state
	 *            -1代表处理中,0代表配送中,1代表已配送
	 * @param page
	 *            页码
	 * @return 成功{"state":"success":"order":[]};
	 *         失败{"state":"fail","reason":"暂无记录" }
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrder", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String getOrder(String studentId, int state, int page) {
		JSONObject json = new JSONObject();
		List<Water> order = waterService.getOrder(studentId, state, page, SIZE);
		if (order != null) {
			json.accumulate("state", "success");
			json.accumulate("order", JSONArray.fromObject(order));
			return json.toString();
		} else {
			json.accumulate("state", "fail");
			json.accumulate("reason", "暂无记录");
			return json.toString();
		}
	}

	/**
	 * 学生删除水单
	 * 
	 * @param studentId
	 *            学号
	 * @param id
	 *            水单号
	 * @return 成功{"state":"success"};失败:{"state":"fail","reason":错误信息}
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteOrder", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String deleteOrder(String studentId, int id) {
		JSONObject json = new JSONObject();
		try {
			waterService.deleteOrder(studentId, id);
			json.accumulate("state", "success");
			return json.toString();
		} catch (IllegalOperationException e) {
			json.accumulate("state", "fail");
			json.accumulate("reason", e.getMessage());
			return json.toString();
		}
	}
}
