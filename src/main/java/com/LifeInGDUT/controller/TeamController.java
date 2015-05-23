package com.LifeInGDUT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.LifeInGDUT.service.TeamService;

@Controller
@RequestMapping("/team")
public class TeamController {
	@Autowired
	private TeamService tService;
	
	@RequestMapping("/show")
	public ModelAndView show(@RequestParam(value="pageNumber", required=false)Integer pageNumber){
		if(pageNumber==null){
			pageNumber = 1;
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("teams", tService.getTeam(pageNumber, 2));
		mv.addObject("pageNumber", pageNumber);
		mv.addObject("allPages", tService.getAllPageCount(2));;
		mv.setViewName("checkOk");
		return mv;
	}
}
