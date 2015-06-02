package com.LifeInGDUT.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.LifeInGDUT.model.Message;
import com.LifeInGDUT.model.User;
import com.LifeInGDUT.service.MessageService;
import com.LifeInGDUT.service.ReplyService;
import com.LifeInGDUT.util.JsonFormatUtil;

@Controller
@RequestMapping("/message")
public class MessageController {
	@Autowired
	private MessageService mService;
	@Autowired
	private ReplyService rService;
	
	
	/**
	 * 
	 * @Title: addMessage
	 * @Description: user增加一条生活圈
	 * @param content
	 * @param section
	 * @param files
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String addMessage(@RequestParam(value="studentId",required=false)String studentId, @RequestParam String content, @RequestParam int section, @RequestParam(value="files", required=false)MultipartFile[] files, HttpSession session){
		Integer id = null;
		if(section==2){
			if(session.getAttribute("teamName")==null){
				return "{\"state\": \"fail\"}";
			}
			id = mService.getTeamIdByName(session.getAttribute("teamName").toString());
		}else if(section==3){
			if(session.getAttribute("newsAdmin_id")==null){
				return "{\"state\": \"fail\"}";
			}
			id = Integer.parseInt(session.getAttribute("newsAdmin_id").toString());
		}
		//差一个判空操作
		String path = session.getServletContext().getRealPath("/");
		mService.add(content, section, files, path, id, studentId);
		return "{\"state\": \"success\"}";
	}
	
	@RequestMapping(value="add", method=RequestMethod.GET)
	public String addMessage(){
		return "addMessage";
	}
	
	/**
	 * 
	 * @Title: showMessages
	 * @Description:  显示全部生活圈
	 * @param message_id 该message_id为上页最后一条
	 * @param studentId 如果为登录用户，需要参数学号
	 * @param section 0代表取全部，1代表取用户，2代表取社团，3代表取校内通知
	 * @param page_size 取出改id后面数据的数量
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/showMessages", method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String showMessages(@RequestParam(value="message_id", required=false)Integer message_id, @RequestParam(value="studentId", required=false)String studentId, @RequestParam int section, @RequestParam int page_size){
		System.out.println("message_id="+message_id);
		List<Message> messages = mService.getMessages(message_id, studentId, page_size, section);
		return JsonFormatUtil.JsonFormat(messages);
	}
	
	/**
	 * 
	 * @Title: getMessageFromOne
	 * @Description: TODO
	 * @param message_id
	 * @param id
	 * @param studentId
	 * @param my_studentId
	 * @param section
	 * @param page_size
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/showOne", method=RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String getMessageFromOne(@RequestParam(value="message_id", required=false)Integer message_id,@RequestParam(value="id", required=false)Integer id,  @RequestParam(value="studentId", required=false) String studentId, @RequestParam(value="my_studentId", required=false) String my_studentId,  @RequestParam int section, @RequestParam int page_size){
		System.out.println("message_id="+message_id);
		List<Message> messages = mService.getOne(message_id, id, studentId, my_studentId, page_size, section);
		return JsonFormatUtil.JsonFormat(messages);
	}
	
	@ResponseBody
	@RequestMapping(value="/showOne", method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String getMessageFromtestOne(@RequestParam(value="message_id", required=false)Integer message_id,@RequestParam(value="id", required=false)Integer id,  @RequestParam(value="studentId", required=false) String studentId, @RequestParam(value="my_studentId", required=false) String my_studentId,  @RequestParam int section, @RequestParam int page_size){
		System.out.println("message_id="+message_id);
		List<Message> messages = mService.getOne(message_id, id, studentId, my_studentId, page_size, section);
		return JsonFormatUtil.JsonFormat(messages);
	}
	
	/**
	 * 
	 * @Title: praise
	 * @Description: 点赞
	 * @param message_id
	 * @param user_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/praise", method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String praise(@RequestParam int message_id, @RequestParam String user_id, @RequestParam int sign){
		System.out.println("user_id="+user_id);
		mService.doPraise(message_id, user_id, sign);
		String returnStr = null;
		if(sign==1){
			returnStr = "{\"state\": 1}";
		}else if(sign==0){
			returnStr = "{\"state\": 0}";
		}
		return returnStr;
	}
	
	/**
	 * 
	 * @Title: getPraises
	 * @Description: 得到一条生活圈点赞的人
	 * @param message_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getPraisesUser", method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String getPraises(@RequestParam int message_id){
		List<User> users = mService.getPraisesUser(message_id);
		return JsonFormatUtil.JsonFormat(users);
	}
	
	
	
}
