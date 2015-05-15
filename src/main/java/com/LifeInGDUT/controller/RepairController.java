package com.LifeInGDUT.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.LifeInGDUT.model.Repair;
import com.LifeInGDUT.service.RepairService;


@Controller
@RequestMapping("/repair")
public class RepairController {
	@Autowired
	private RepairService rService;
	/**
	 * 提交保修表单
	 * @param repair
	 */
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST , produces = "application/json;charset=UTF-8")
	public String addRepair(Repair repair){
		System.out.println("add...");
		rService.add(repair);
		return "{\"state\": \"成功\"}";
	}
	
	/**
	 * 查询某学号学生维修状态为“未处理”和“已处理”，按页码分页返回
	 * @param studentId
	 * @param pageNumber
	 * @param page_size
	 * @param state
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/showRepair", method=RequestMethod.POST , produces = "application/json;charset=UTF-8")
	public String showRepair(@RequestParam String studentId, @RequestParam int pageNumber, @RequestParam int page_size, @RequestParam String state, HttpServletResponse response){
		List<Repair> repairs = rService.getRepairByState(studentId, pageNumber, page_size, state);
		net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(repairs);
		System.out.println(json.toString());
		if("[]".equals(json.toArray())){
			return null;
		}else{
			return json.toString();
		}
	}
	@RequestMapping("/showWebRepair")
	public ModelAndView showRepair(@RequestParam(value="state", required = false)String state, @RequestParam(value="pageNumber", required = false)Integer pageNumber, HttpServletResponse response){
		if(state==null){
			state = "处理中";
		}
		if(pageNumber==null){
			pageNumber = 1;
		}
		ModelAndView mv = new ModelAndView();
		List<Repair> repairs = rService.getRepairByState(pageNumber, 5, state);
		int allPages = rService.getAllPageCount(5);
		mv.addObject("repairs", repairs);
		mv.addObject("allPages", allPages);
		mv.addObject("pageNumber", pageNumber);
		mv.setViewName("repair");
		return mv;
	}
	
	@RequestMapping("/delete")
	public void deleteRepair(@RequestParam int id,@RequestParam int pageNumber, @RequestParam String state, HttpServletResponse response){
		rService.delete(id);
		try {
			response.sendRedirect("/LifeInGDUT/repair/showWebRepair?state="+state+"&pageNumber="+pageNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getMore")
	public void getMore(@RequestParam int id, HttpServletResponse response){
		Repair repair = rService.getRepairById(id);
		net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(repair);
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		try {
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("changeState")
	public void changeState(@RequestParam(value="ids", required=false)Integer[] ids,@RequestParam int pageNumber){
		if(ids != null){
			
		}
	}
	
//	@ResponseBody
//	@RequestMapping(value="/test", method=RequestMethod.POST )
//	public String test(@RequestParam String name){
//		System.out.println("test...");
//		return "{\"name\":\""+name+"\"}";
//	}
	
//	@RequestMapping(value="", method=RequestMethod.POST)
//	public String show(){
//		
//	}
}



