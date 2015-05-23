package com.LifeInGDUT.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="message")
public class Message {
	private int id;
	private String content;
	private String img;
	private String time;
	private int section;
	private int praise_number;
	private User user;
	private Team team;
	private NewsAdmin newsAdmin;
	private List<User> users = new ArrayList<User>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getSection() {
		return section;
	}
	public void setSection(int section) {
		this.section = section;
	}
	public int getPraise_number() {
		return praise_number;
	}
	public void setPraise_number(int praise_number) {
		this.praise_number = praise_number;
	}
	
	@ManyToMany
	@JoinTable(name = "message_user",
	    joinColumns = { @JoinColumn(name = "message_id", referencedColumnName = "id")},
	    inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "studentId")}
    )
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	@OneToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@OneToOne
	@JoinColumn(name="team_id")
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	@OneToOne
	@JoinColumn(name="newsAdmin_id")
	public NewsAdmin getNewsAdmin() {
		return newsAdmin;
	}
	public void setNewsAdmin(NewsAdmin newsAdmin) {
		this.newsAdmin = newsAdmin;
	}
	
}
