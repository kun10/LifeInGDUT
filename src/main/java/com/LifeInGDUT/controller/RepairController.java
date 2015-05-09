package com.LifeInGDUT.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.LifeInGDUT.model.Repair;
import com.LifeInGDUT.service.RepairService;


@Controller
@RequestMapping("/repair")
public class RepairController {
	@Autowired
	private RepairService rService;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public void addRepair(Repair repair){
		rService.add(repair);
	}
	
	@RequestMapping(value="/showRepair", method=RequestMethod.POST)
	public String showRepair(@RequestParam int pageNumber, @RequestParam int page_size, @RequestParam String status, HttpServletResponse response){
		List<Repair> repairs = rService.getRepairByStatus(pageNumber, page_size, status);
		net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(repairs);
		System.out.println(json.toString());
		return json.toString();
	}
	
}
