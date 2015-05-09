package com.LifeInGDUT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
}
