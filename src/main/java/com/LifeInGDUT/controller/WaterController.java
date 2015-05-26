package com.LifeInGDUT.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

	private static final int SIZE = 10;

	@Autowired
	private WaterService waterService;

	@Autowired
	private UserService userService;

	// 学生叫水
	@ResponseBody
	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String placeOrder(char[] password, Water water) {
		JSONObject json = new JSONObject();
		User user = new User();
		user.setPassword(password);
		user.setStudentId(water.getStudentId());
		try {
			user = userService.login(user);
			user.setNumber(user.getNumber() - water.getNumber());
			water.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			water.setState(Water.UNHANDLED);
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

	// 学生获取水单
	@ResponseBody
	@RequestMapping(value = "/getOrder", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String getOrder(String studentId, int state, int page) {
		System.out.println("00000000");
		System.out.println(studentId);
		System.out.println(state);
		System.out.println(page);
		JSONObject json = new JSONObject();
		List<Water> order = new ArrayList<Water>();
		order = waterService.getOrder(studentId, state, page, SIZE);
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

	@ResponseBody
	@RequestMapping(value = "/deleteOrder", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String deleteOrder(String studentId, int id) {
		System.out.println(studentId);
		System.out.println(id);
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

	public static void test(final int id) {
		System.out.println("------------这里是test(" + id + ")开始--------------");
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println("---------------这里是timer(" + id + ")----------");
			}
		}, 10000);
		System.out.println("----------------这里是test(" + id + ")结束--------------");
	}

}
