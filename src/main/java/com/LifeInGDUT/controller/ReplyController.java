package com.LifeInGDUT.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.LifeInGDUT.model.Reply;
import com.LifeInGDUT.service.ReplyService;
import com.LifeInGDUT.util.JsonFormatUtil;

@Controller
@RequestMapping("/reply")
public class ReplyController {
	@Autowired
	private ReplyService rService;
	
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String add(@RequestParam String content, @RequestParam String studentId, @RequestParam int message_id, @RequestParam(value="to_id", required=false) Integer to_id){
		rService.add(content, studentId, message_id, to_id);
		return "{\"state\": \"成功\"}";
	}
	
	/**
	 * 	
	 * @Title: show
	 * @Description: TODO
	 * @param reply_id
	 * @param page_size
	 * @param message_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/show", method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String show(@RequestParam(value="reply_id", required=false)Integer reply_id, @RequestParam int page_size, @RequestParam int message_id){
		List<Reply> replys = rService.getReply(reply_id, page_size, message_id);
		int size = rService.getReplySizeByMessage(message_id); 
		String json = JsonFormatUtil.JsonFormat(replys);
		String return1 = "{ 'replys':"+json+",'size':"+size+"}";
		return return1;
	}
	
	@ResponseBody
	@RequestMapping(value="/showToReply", method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String showToReply(@RequestParam int reply_id){
		Reply reply = rService.getReply(reply_id);
		return JsonFormatUtil.JsonFormat(reply);
	}
	
}
