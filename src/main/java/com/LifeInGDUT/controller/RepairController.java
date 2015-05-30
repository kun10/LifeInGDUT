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
import com.LifeInGDUT.util.JsonFormatUtil;


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
		if(rService.isRepeat(repair.getType(), repair.getStudentId())){
			return "{\"state\": \"fail\"}";
		}
		System.out.println("add...");
		rService.add(repair);
		return "{\"state\": \"success\"}";
	}
	
	/**
	 * 查询某学号学生保修单，按页码分页返回。已对接完成，差安卓下拉刷新
	 * @param studentId
	 * @param pageNumber
	 * @param page_size
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/showRepair", method=RequestMethod.POST , produces = "application/json;charset=UTF-8")
	public String showRepair(@RequestParam String studentId, @RequestParam int pageNumber){
		int page_size = 10;
		List<Repair> repairs = rService.getRepairByStudentId(studentId, pageNumber, page_size);
		net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(repairs);
		System.out.println(json.toString());
		if("[]".equals(json.toArray())){
			return "[]";
		}else{
			return json.toString();
		}
	}
	@RequestMapping("/showWebRepair")
	public ModelAndView showRepair(@RequestParam(value="state", required = false)Integer state, @RequestParam(value="pageNumber", required = false)Integer pageNumber, HttpServletResponse response){
		if(state==null){
			state = 1;
		}
		if(pageNumber==null){
			pageNumber = 1;
		}
		ModelAndView mv = new ModelAndView();
		List<Repair> repairs = rService.getRepairByState(pageNumber, 10, state);
		int allPages = rService.getAllPageCount(10, state);
		mv.addObject("repairs", repairs);
		mv.addObject("state", state);
		mv.addObject("allPages", allPages);
		mv.addObject("pageNumber", pageNumber);
		mv.setViewName("repair");
		return mv;
	}
	
	@RequestMapping("/delete")
	public void deleteRepair(@RequestParam int id,@RequestParam int pageNumber, @RequestParam int state, HttpServletResponse response){
		rService.delete(id);
		try {
			response.sendRedirect("/LifeInGDUT/repair/showWebRepair?state="+state+"&pageNumber="+pageNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/getMore", method=RequestMethod.POST)
	public void getMore(@RequestParam int id, HttpServletResponse response){
		Repair repair = rService.getRepairById(id);
		response.setHeader("Content-type", "text/json;charset=UTF-8"); 
		try {
			response.getWriter().write(JsonFormatUtil.JsonFormat(repair));	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="changeState",method=RequestMethod.GET)
	public void changeState(@RequestParam(value="ids", required=false)Integer[] ids, HttpServletResponse response){
		if(ids != null){
			rService.changeState(ids);
		}
		try {
			response.getWriter().write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}



