package com.LifeInGDUT.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.LifeInGDUT.model.PreTeam;

@Component
@RequestMapping("/preTeam")
public class PreTeamDao {
	@Autowired
	private SuperDao superDao;

	public PreTeam selectByName(String name) {
		return (PreTeam)superDao.getSession().createQuery("from PreTeam where name = :name").setString("name", name).uniqueResult();
	}

	public void insert(PreTeam p) {
		superDao.getSession().save(p);
	}

	public PreTeam selectById(int id) {
		return (PreTeam)superDao.getSession().get(PreTeam.class, id);
	}
	public void delete(PreTeam p){
		superDao.getSession().delete(p);
	}

	public List<PreTeam> select(int first, int max, int isCheck) {
		return superDao.getSession().createQuery("from PreTeam where isCheck = "+isCheck+" order by time desc").setFirstResult(first).setMaxResults(max).list();
	}

	public int count(int isCheck) {
		Long l =  (Long)superDao.getSession().createQuery("select count(*) from PreTeam where isCheck = "+isCheck).uniqueResult();
		return l.intValue();
	}

	public void updateIsCheckByName(String name) {
		superDao.getSession().createSQLQuery("update preTeam set isCheck = 1 where name = :name ").setString("name", name).executeUpdate();
	}
	
	
}
