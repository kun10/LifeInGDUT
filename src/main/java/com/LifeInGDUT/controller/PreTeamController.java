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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.LifeInGDUT.service.PreTeamService;

@Controller
@RequestMapping("/preTeam")
public class PreTeamController {
	@Autowired
	private PreTeamService pService;
	
	@RequestMapping(value="/apply", method=RequestMethod.POST)
	public void add(@RequestParam String name, @RequestParam String studentId, @RequestParam String GDUTPassword, @RequestParam String password, @RequestParam String password_again, @RequestParam MultipartFile[] files, @RequestParam MultipartFile head, HttpServletRequest request,HttpServletResponse response, HttpSession session){
		if(pService.isNameExist(name)){
			request.setAttribute("info", "该社团名字已被注册");
			try {
				request.getRequestDispatcher("WEB-INF/jsp/preTeamRegister.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(!password.equals(password_again)){
			request.setAttribute("info", "两次输入的密码不一致");
			try {
				request.getRequestDispatcher("WEB-INF/jsp/preTeamRegister.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(false){ 
			/*学号和工大密码匹配检查*/
			return;
		}else{
			String path = session.getServletContext().getRealPath("/");
			pService.add(name, password, studentId, files, path, head);
		}
	}
	
	@RequestMapping(value="/apply", method=RequestMethod.GET)
	public String apply(){
		return "preTeamRegister";
	}
	
	@RequestMapping("/show")
	public ModelAndView show(@RequestParam(value="pageNumber", required=false, defaultValue= "1")Integer pageNumber, @RequestParam(value="isCheck", required=false, defaultValue="0")Integer isCheck, HttpSession session){
//		if(pageNumber==null){
//			pageNumber = 1;
//		}
//		if(isCheck==null){
//			isCheck = 0;
//		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageNumber", pageNumber);
		mv.addObject("preTeams", pService.getPreTeam(pageNumber, 4, isCheck));
		mv.addObject("allPages", pService.getAllPageCount(4, isCheck));
		if(isCheck==0){
			mv.setViewName("check");
		}
		if(isCheck==1){
			mv.setViewName("checkNotOk");
		}
		return mv;
	}
	
	@RequestMapping(value="changeToOk",method=RequestMethod.GET)
	public void changeToOk(@RequestParam(value="names", required=false)String[] names, HttpServletResponse response, HttpSession session){
		if(names != null){
			pService.changeToOk(names, session.getServletContext().getRealPath("/"));
		}
		try {
			response.getWriter().write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="changeToNotOk",method=RequestMethod.GET)
	public void changeToNotOk(@RequestParam(value="names", required=false)String[] names, HttpServletResponse response, HttpSession session){
		if(names != null){
			pService.changeToNotOk(names, session.getServletContext().getRealPath("/"));
		}
		try {
			response.getWriter().write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public void delete(@RequestParam int id, HttpServletResponse response, HttpSession session){
		pService.deleteOnePreTeam(id, session.getServletContext().getRealPath("/"));
		try {
			response.getWriter().write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
