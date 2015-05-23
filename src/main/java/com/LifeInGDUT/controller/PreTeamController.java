package com.LifeInGDUT.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.LifeInGDUT.service.PreTeamService;

@Controller
@RequestMapping("/preTeam")
public class PreTeamController {
	@Autowired
	private PreTeamService pService;
	
	@RequestMapping("/apply")
	public void add(@RequestParam String name, @RequestParam String studentId, @RequestParam String password, @RequestParam String password_again, @RequestParam MultipartFile[] files, HttpSession session){
		if(pService.isNameExist(name)){
			//名字存在
		}else{
			String path = session.getServletContext().getRealPath("/");
			pService.add(name, password, studentId, files, path);
		}
	}
	
	@RequestMapping("/show")
	public ModelAndView show(@RequestParam(value="pageNumber", required=false)Integer pageNumber, @RequestParam(value="isCheck", required=false)Integer isCheck, HttpSession session){
		if(pageNumber==null){
			pageNumber = 1;
		}
		if(isCheck==null){
			isCheck = 0;
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageNumber", pageNumber);
		mv.addObject("preTeams", pService.getPreTeam(pageNumber, 2, isCheck));
		mv.addObject("allPages", pService.getAllPageCount(2, isCheck));
		if(isCheck==0){
			mv.setViewName("check");
		}
		if(isCheck==1){
			mv.setViewName("checkNotOk");
		}
		return mv;
	}
	
	@RequestMapping(value="changeToOk",method=RequestMethod.GET)
	public void changeToOk(@RequestParam(value="ids", required=false)Integer[] ids, HttpServletResponse response){
		if(ids != null){
			pService.changeToOk(ids);
		}
		try {
			response.getWriter().write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
