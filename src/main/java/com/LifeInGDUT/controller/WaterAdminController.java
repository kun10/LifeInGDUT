package com.LifeInGDUT.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.LifeInGDUT.dao.WaterAdminDao;
import com.LifeInGDUT.exception.AuthenticationException;
import com.LifeInGDUT.model.Charge;
import com.LifeInGDUT.model.User;
import com.LifeInGDUT.model.Water;
import com.LifeInGDUT.model.WaterAdmin;
import com.LifeInGDUT.service.UserService;
import com.LifeInGDUT.service.WaterAdminService;

@Controller
@SessionAttributes(value = "waterAdmin")
@RequestMapping(value = "/water")
public class WaterAdminController {

	private static final int SIZE = 10;

	private static final long delay = 1000 * 60 * 60 * 24;

	@Autowired
	private WaterAdminService waterAdminService;

	@Autowired
	private UserService userService;

	@ModelAttribute(value = "waterAdmin")
	public WaterAdmin getWaterAdmin() {
		return new WaterAdmin();
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap modelMap) {
		WaterAdmin waterAdmin = (WaterAdmin) modelMap.get("waterAdmin");
		if (waterAdmin.getUserName() != null) {
			return "main";
		} else {
			return "waterlogin";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public String login(@ModelAttribute(value = "waterAdmin") WaterAdmin waterAdmin, Model model,
			RedirectAttributes attributs) {
		if (waterAdmin.getPassword() == null) {
			System.out.println("username is null");
		} else if (waterAdmin.getPassword().length == 0) {
			System.out.println("username is \"\"");
		} else {
			System.out.println("else");
		}
		System.out.println(waterAdmin.getUserName());
		System.out.println(waterAdmin.getPassword());
		try {
			waterAdmin = waterAdminService.login(waterAdmin);
			model.addAttribute("waterAdmin", waterAdmin);
		} catch (AuthenticationException e) {
			model.addAttribute("errmsg", e.getMessage());
			e.printStackTrace();
			return "waterlogin";
		}
		attributs.addAttribute("state", -1);
		attributs.addAttribute("page", 1);
		return "redirect:/water/getOrder";
	}

	@RequestMapping(value = "/test")
	public String test(int state, int page, Model model) {
		System.out.println("state:" + state);
		System.out.println("page:" + page);
		System.out.println("来了");
		return "untreatedOrder";
	}

	@RequestMapping(value = "/getOrder", method = RequestMethod.GET)
	public String getOrder(int state, int page, Model model) {
		if (state != Water.UNHANDLED && state != Water.HANDLED && state != Water.FINISH) {
			state = Water.UNHANDLED;
		}
		@SuppressWarnings("rawtypes")
		List orders = new ArrayList();
		orders = waterAdminService.getOrder(state, page, SIZE);
		model.addAttribute("orders", orders);
		if (state == Water.HANDLED) {
			return "acceptOrder";
		} else if (state == Water.FINISH) {
			return "treatedOrder";
		} else {
			return "untreatedOrder";
		}
	}

	public void accept(final Integer[] orders, String deliver, Model model) {
		waterAdminService.accept(orders, deliver);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				finish(orders);
			}
		}, delay);
	}

	public String delete(String orderId, Model model) {
		waterAdminService.delete(orderId);
		return "";
	}

	public String finish(Integer[] orders) {
		waterAdminService.finish(orders);
		return "";
	}

	@RequestMapping(value = "/charge")
	public String charge() {
		return "charge";
	}

	public String charge(String studentId, int number, Model model) {
		User user = new User();
		user = userService.getUserById(studentId);
		if (user != null) {
			user.setNumber(user.getNumber() + number);
			waterAdminService.charge(user);
			return "";
		}
		return "";
	}

	@RequestMapping(value = "/getChargeRecord", method = RequestMethod.GET)
	public String getChargeRecord(int page, Model model) {
		List<Charge> charges = waterAdminService.getChargeRecord(page, SIZE);
		model.addAttribute("charges", charges);
		return "chargeRecord";
	}

	public static void timer() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println("呵呵呵");
			}
		}, 10000);
	}

	public static void main(String[] args) {
		WaterAdminDao dao = new WaterAdminDao();
		List<Charge> list = dao.getChargeRecord(0, 3);
		for (Charge c : list) {
			System.out.println(c.getTime());
		}
	}

}
