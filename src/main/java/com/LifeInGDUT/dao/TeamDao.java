package com.LifeInGDUT.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LifeInGDUT.model.Team;

@Component
public class TeamDao {
	@Autowired
	private SuperDao superDao;
	
	public void insert(Team team){
		superDao.getSession().save(team);
	}

	public Team selectByName(String name) {
		return (Team)superDao.getSession().createQuery("from Team where name = :name").setString("name", name).uniqueResult();
	}
	
	public List<Team> select(int first, int max){
		return superDao.getSession().createQuery("from Team order by time desc").setFirstResult(first).setMaxResults(max).list();
	}

	public int count() {
		Long l =  (Long)superDao.getSession().createQuery("select count(*) from Team").uniqueResult();
		return l.intValue();
	}
}
