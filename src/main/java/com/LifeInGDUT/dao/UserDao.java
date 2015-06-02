package com.LifeInGDUT.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.LifeInGDUT.model.User;

@Repository
@Transactional
public class UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	public User getUserById(String studentId) {
		return (User) sessionFactory.getCurrentSession()
				.createQuery("from User user where user.studentId = :studentId").setString("studentId", studentId)
				.uniqueResult();
	}

	public void updateUserInfo(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}
}
