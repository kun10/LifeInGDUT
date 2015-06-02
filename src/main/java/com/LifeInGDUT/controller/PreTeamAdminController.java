package com.LifeInGDUT.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.LifeInGDUT.service.PreTeamAdminService;

@Controller
@RequestMapping("/preTeamAdmin")
public class PreTeamAdminController {
	@Autowired
	private PreTeamAdminService pService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(){
		return "preTeamAdminLogin";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public void login(@RequestParam String name, @RequestParam String password, HttpServletRequest request, HttpServletResponse response){
		if(!pService.login(name, password)){
			request.setAttribute("info", "请确认用户名和密码");
			try {
				request.getRequestDispatcher("/WEB-INF/jsp/preTeamAdminLogin.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			request.getSession().setAttribute("preTeamAdmin", name);
			try {
				response.sendRedirect("/LifeInGDUT/preTeam/show");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
