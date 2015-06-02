package com.LifeInGDUT.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.LifeInGDUT.service.TeamService;

@Controller
@RequestMapping("/team")
public class TeamController {
	@Autowired
	private TeamService tService;
	
	@RequestMapping("/show")
	public ModelAndView show(@RequestParam(value="pageNumber", required=false, defaultValue="1")Integer pageNumber){
		ModelAndView mv = new ModelAndView();
		mv.addObject("teams", tService.getTeam(pageNumber, 2));
		mv.addObject("pageNumber", pageNumber);
		mv.addObject("allPages", tService.getAllPageCount(2));;
		mv.setViewName("checkOk");
		return mv;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(){
		return "teamLogin";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView login(@RequestParam String name, @RequestParam String password, HttpSession session){
		ModelAndView mv = new ModelAndView();
		if(!tService.login(name, password)){
			mv.addObject("info", "请确认用户名和密码");
			mv.setViewName("teamLogin");
		}else{
			session.setAttribute("teamName", name);
			mv.setViewName("teamSend");
		}
		return mv;
	}
	
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession(false);
		if(null!=session.getAttribute("teamName"))
			session.removeAttribute("teamName");
		try {
			response.sendRedirect("/LifeInGDUT/team/login");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}
