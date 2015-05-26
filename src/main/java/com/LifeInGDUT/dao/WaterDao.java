package com.LifeInGDUT.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.LifeInGDUT.model.Water;

@Repository
@Transactional
public class WaterDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void placeOrder(Water water) {
		sessionFactory.getCurrentSession().save(water);
	}

	@SuppressWarnings("unchecked")
	public List<Water> getOrder(String studentId, int state, int start, int size) {
		String hql = "";
		if (state == Water.UNHANDLED || state == Water.HANDLED) {
			hql = "from Water water where water.studentId = :studentId and state != :state order by water.time desc";
		} else if (state == Water.FINISH) {
			hql = "from Water water where water.studentId = :studentId and state = :state order by water.finishTime desc";
		}
		return sessionFactory.getCurrentSession().createQuery(hql).setString("studentId", studentId)
				.setInteger("state", Water.FINISH).setFirstResult(start).setMaxResults(size).list();
	}

	public Water getOrderById(int orderId) {
		return (Water) sessionFactory.getCurrentSession().get(Water.class, orderId);
	}

	public void deleteOrder(int id) {
		sessionFactory.getCurrentSession().createQuery("delete from Water water where water.id = :id")
				.setInteger("id", id).executeUpdate();
	}
}
