package com.LifeInGDUT.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LifeInGDUT.dao.TeamDao;
import com.LifeInGDUT.model.Team;
import com.LifeInGDUT.model.User;

@Component
public class TeamService {
	@Autowired
	private TeamDao tDao;
	
	public void add(String name, String password, String studentId, String headImg){
		Team t = new Team();
		User user = new User();
		user.setStudentId(studentId);
		t.setName(name);
		t.setPassword(password);
		t.setUser(user);
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		t.setTime(time);
		t.setHeadImg(headImg);
		tDao.insert(t);
	}
	
	public boolean login(String name, String password){
		Team team = this.getTeamByName(name);
		if(team==null){
			return false; 
		}else if(password.equals(team.getPassword())){
			return true;
		}else{
			return false;
		}
	}
	
	public Team getTeamByName(String name){
		Team team = tDao.selectByName(name);
		return team;
	}
	
	public List<Team> getTeam(int pageNumber, int page_size){
		int first = (pageNumber-1)*page_size;
		List<Team> teams = tDao.select(first, page_size);
		for(Team p : teams){
			p.setPassword(null);
			p.getUser().setPassword(null);
		}
		return teams;
	}
	
	public int getAllPageCount(int page_size){
		int sum = tDao.count();
		if(sum==0)
			return 0;
		else
			return (sum-1)/page_size+1;
	}
}
