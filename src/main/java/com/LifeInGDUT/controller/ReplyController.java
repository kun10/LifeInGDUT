package com.LifeInGDUT.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.LifeInGDUT.model.Reply;
import com.LifeInGDUT.service.ReplyService;
import com.LifeInGDUT.util.JsonFormatUtil;

@Controller
@RequestMapping("/reply")
public class ReplyController {
	@Autowired
	private ReplyService rService;
	
	@RequestMapping(value="/add", method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String add(@RequestParam String content, @RequestParam String userName, @RequestParam int message_id, @RequestParam(value="to_id", required=false) int to_id){
		rService.add(content, userName, message_id, to_id);
		return "{\"state\": \"成功\"}";
	}
	
	@RequestMapping(value="/show", method=RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String show(@RequestParam(value="pageNumber", required=false)Integer pageNumber, @RequestParam int page_size, @RequestParam int message_id){
		List<Reply> replys = rService.getReply(pageNumber, page_size, message_id);
		return JsonFormatUtil.JsonFormat(replys);
	}
	
	
}
