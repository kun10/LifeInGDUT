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
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView login(@RequestParam String name, @RequestParam String password, HttpSession session){
		ModelAndView mv = new ModelAndView();
		if(nService.check(name, password)){
			session.setAttribute("newsAdmin_name", name);
			session.setAttribute("newsAdmin_id", nService.getNewsAdmin(name).getId());
			mv.setViewName("newsSend");
		}else{
			mv.addObject("info", "请确认用户名和密码");
			mv.setViewName("newsAdminLogin");
		}
		return mv;
	}
	
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession(false);
		if(null!=session.getAttribute("newsAdmin_name"))
			session.removeAttribute("newsAdmin_name");
			session.removeAttribute("newsAdmin_id");
		try {
			response.sendRedirect("/LifeInGDUT/newsAdmin/login");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}
