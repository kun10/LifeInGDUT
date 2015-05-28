package com.LifeInGDUT.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wateradmin")
public class WaterAdmin {

	private int id;
	private String userName;
	private char[] password;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public boolean equals(Object object) {
		if (object != null && (object instanceof WaterAdmin)) {
			WaterAdmin admin = (WaterAdmin) object;
			if (admin.getUserName().equals(this.getUserName())
					&& String.valueOf(admin.getPassword()).equals(
							String.valueOf(this.getPassword()))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
