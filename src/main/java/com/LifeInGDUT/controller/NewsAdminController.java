package com.LifeInGDUT.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.LifeInGDUT.service.NewsAdminService;

@Controller
@RequestMapping("/newsAdmin")
public class NewsAdminController {
	@Autowired
	private NewsAdminService nService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(){
		return "newsAdminLogin";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public void login(@RequestParam String name, @RequestParam String password, HttpSession session, HttpServletResponse response){
		if(nService.check(name, password)){
			session.setAttribute("newsAdmin_name", name);
			session.setAttribute("newsAdmin_id", nService.getNewsAdmin(name).getId());
			//跳到进入页面
		}else{
			try {
				response.sendRedirect("/LifeInGDUT/login");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
