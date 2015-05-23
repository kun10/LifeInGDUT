package com.LifeInGDUT.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.LifeInGDUT.model.Reply;

public class testRepairService {
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	ReplyService rService = (ReplyService)context.getBean("replyService");
	
	@Test
	public void test(){
		for(Reply r: rService.getReply(1, 4, 1)){
			System.out.println("userName="+r.getUserName());
			System.out.println("time="+r.getTime());
			if(r.getReply()!=null){
				System.out.println(r.getReply().getId());
			}else{
				System.out.println("to_id is null");
			}
		}
	}
	public void testAdd(){
		rService.add("content", "kun", 2, 1);
	}
}
