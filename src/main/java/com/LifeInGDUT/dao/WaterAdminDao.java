package com.LifeInGDUT.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.LifeInGDUT.model.Charge;
import com.LifeInGDUT.model.User;
import com.LifeInGDUT.model.Water;
import com.LifeInGDUT.model.WaterAdmin;

@Repository
@Transactional
public class WaterAdminDao {

	@Autowired
	private SessionFactory sessionFactory;

	public WaterAdmin checkAdmin(WaterAdmin admin) {
		return (WaterAdmin) sessionFactory.getCurrentSession()
				.createQuery("from WaterAdmin admin where admin.userName = :userName")
				.setString("userName", admin.getUserName()).uniqueResult();
	}

	@SuppressWarnings("rawtypes")
	public List getOrder(int state, int start, int size) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from Water water where water.state = :state")
				.setInteger("state", state).setFirstResult(start).setMaxResults(size).list();
	}

	public void accept(Integer[] orders, String deliver) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "";
		for (int order : orders) {
			hql = "update from Water water set water.state = :state and water.deliver = :deliver where water.id = :id";
			session.createQuery(hql).setInteger("state", Water.HANDLED).setString("deliver", deliver)
					.setInteger("id", order).executeUpdate();
		}
	}

	public void delete(int orderId) {
		sessionFactory.getCurrentSession().createQuery("delete from Water water where water.id = :id")
				.setInteger("id", orderId).executeUpdate();
	}

	public void finish(Integer[] orders) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "";
		for (int order : orders) {
			hql = "update from Water water set water.state = :state where water.id = :id ";
			session.createQuery(hql).setInteger("state", Water.FINISH).setInteger("id", order).executeUpdate();
		}
	}

	public void charge(User user) {
		sessionFactory.getCurrentSession().update(user);
	}

	@SuppressWarnings("unchecked")
	public List<Charge> getChargeRecord(int start, int size) {
		return sessionFactory.getCurrentSession().createQuery("from Charge charge order by charge.time desc")
				.setFirstResult(start).setMaxResults(size).list();
	}

}
