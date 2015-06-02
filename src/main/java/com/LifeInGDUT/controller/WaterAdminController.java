package com.LifeInGDUT.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.LifeInGDUT.exception.AuthenticationException;
import com.LifeInGDUT.model.User;
import com.LifeInGDUT.model.Water;
import com.LifeInGDUT.model.WaterAdmin;
import com.LifeInGDUT.service.UserService;
import com.LifeInGDUT.service.WaterAdminService;

@Controller
@RequestMapping(value = "/water")
public class WaterAdminController {

	/* 每页显示的数目 */
	private static final int SIZE = 10;

	@Autowired
	private WaterAdminService waterAdminService;

	@Autowired
	private UserService userService;

	/**
	 * 送水后台管理员进入登录页面
	 * 
	 * @param modelMap
	 * @return 未登录进入waterLogin.jsp;已登录进入untreatedOrder.jsp
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "waterlogin";
	}

	/**
	 * 退出登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute("waterAdmin");
		return "waterlogin";
	}

	/**
	 * 管理员登录
	 * 
	 * @param waterAdmin
	 *            包括用户名userName和密码password
	 * @param model
	 * @param attributs
	 * @return 登录成功跳到getOrder方法，获取未处理的水单;登录失败把错误信息返回到登录页面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public String login(WaterAdmin waterAdmin, Model model, RedirectAttributes attributes, HttpServletRequest request) {
		try {
			waterAdmin = waterAdminService.login(waterAdmin);
			request.getSession().setAttribute("waterAdmin", waterAdmin.getUserName());
		} catch (AuthenticationException e) {
			model.addAttribute("errmsg", e.getMessage());
			e.printStackTrace();
			return "waterlogin";
		}
		attributes.addAttribute("state", Water.UNHANDLED);
		attributes.addAttribute("page", 1);
		return "redirect:/water/getOrder";
	}

	/**
	 * 获取水单
	 * 
	 * @param state
	 *            -1表示未处理水单，0代表配送中的水单，1代表已配送的水单
	 * @param page
	 *            页码
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getOrder", method = RequestMethod.GET)
	public String getOrder(int state, int page, Model model) {
		if (state != Water.UNHANDLED && state != Water.HANDLED && state != Water.FINISH) {
			state = Water.UNHANDLED;
		}
		model.addAttribute("page", page);
		model.addAttribute("state", state);
		model.addAttribute("url", 0);
		model.addAttribute("count", waterAdminService.getOrderPage(state, SIZE));
		model.addAttribute("orders", waterAdminService.getOrder(state, page, SIZE));
		if (state == Water.HANDLED) {
			return "acceptOrder";
		} else if (state == Water.FINISH) {
			return "treatedOrder";
		} else {
			return "untreatedOrder";
		}
	}

	/**
	 * 接受下单
	 * 
	 * @param orders
	 *            被接受的水单号数组
	 * @param deliver
	 *            配送员
	 * @param model
	 * @param response
	 * @param attributs
	 */
	@RequestMapping(value = "/accept", method = RequestMethod.POST)
	public void accept(String[] orders, String deliver, HttpServletResponse response) {
		waterAdminService.accept(orders, deliver);
		try {
			response.getWriter().write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除功能，但是暂时没实现，感觉没必要有这个功能
	 * 
	 * @param orderId
	 * @param page
	 * @param state
	 * @param model
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(int orderId, int page, int state, Model model, RedirectAttributes attributes) {
		waterAdminService.delete(orderId);
		attributes.addAttribute("page", page);
		attributes.addAttribute("state", state);
		return "redirect:/water/getOrder";
	}

	/**
	 * 把水单状态由"配送中"改为"已完成"
	 * 
	 * @param orders
	 *            被处理的水单号数组
	 * @param response
	 */
	@RequestMapping(value = "/finish")
	public void finish(String[] orders, HttpServletResponse response) {
		waterAdminService.finish(orders);
		try {
			response.getWriter().write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 进入充值页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/charge", method = RequestMethod.GET)
	public String charge() {
		return "charge";
	}

	/**
	 * 确认充值
	 * 
	 * @param studentId
	 *            学号
	 * @param number
	 *            充值桶数
	 * @param model
	 * @param response
	 */
	@RequestMapping(value = "/charge", method = RequestMethod.POST)
	public void charge(String studentId, int number, Model model, HttpServletResponse response) {
		String data = null;
		User user = userService.getUserById(studentId);
		if (user != null) {
			waterAdminService.charge(user, number);
			data = "success";
		} else {
			data = "fail";
		}
		try {
			response.getWriter().write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取充值记录
	 * 
	 * @param page
	 *            页码
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getChargeRecord", method = RequestMethod.GET)
	public String getChargeRecord(int page, Model model) {
		model.addAttribute("url", 1);
		model.addAttribute("page", page);
		model.addAttribute("count", waterAdminService.getChargePage(SIZE));
		model.addAttribute("charges", waterAdminService.getChargeRecord(page, SIZE));
		return "chargeRecord";
	}
}
