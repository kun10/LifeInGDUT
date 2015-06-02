package com.LifeInGDUT.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.LifeInGDUT.service.RepairAdminService;

@Controller
@RequestMapping("/repairAdmin")
public class RepairAdminController {
	
	@Autowired
	private RepairAdminService rService;
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public void login(@RequestParam String name, @RequestParam String password, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		if(rService.login(name, password)){
			session.setAttribute("repairAdmin", name);
			try {
				response.sendRedirect("/LifeInGDUT/repair/showWebRepair");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			request.setAttribute("info", "请确认用户名和密码");
			try {
				request.getRequestDispatcher("/WEB-INF/jsp/repairLogin.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession(false);
		if(null!=session.getAttribute("repairAdmin"))
			session.removeAttribute("repairAdmin"); 
		try {
			response.sendRedirect("/LifeInGDUT/repairAdmin/login");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@RequestMapping("/login")
	public String loginShow(){
		return "repairLogin";
	}
}
