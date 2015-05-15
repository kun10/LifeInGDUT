package com.LifeInGDUT.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.LifeInGDUT.service.RepairAdminService;
import com.LifeInGDUT.service.RepairService;

@Controller
@RequestMapping("/repairAdmin")
public class RepairAdminController {
	
	@Autowired
	private RepairAdminService rService;
	
	@RequestMapping("/loginSubmit")
	public void login(@RequestParam String name, @RequestParam String password, HttpSession session, HttpServletResponse response){
		String redirect = null;
		if(rService.login(name, password)){
			session.setAttribute("repairAdmin", name);
			redirect = "/LifeInGDUT/repair/showWebRepair";
		}else{
			redirect = "/LifeInGDUT/repairAdmin/preLogin";
		}
		try {
			response.sendRedirect(redirect);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/login")
	public String loginShow(){
		return "login";
	}
}
