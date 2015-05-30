package com.LifeInGDUT.service;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.LifeInGDUT.model.User;


public class testMessageService {
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	MessageService m = (MessageService)context.getBean("messageService");
	@Test
	public void test(){
//		List<User> users = m.getPraisesUser(1);
//		for(User user : users){
//			System.out.println(user.getStudentId());
//		}
	}
}
