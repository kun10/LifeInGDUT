package com.LifeInGDUT.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.sf.json.JSONObject;

import com.LifeInGDUT.util.UserUtil;

@Entity
@Table(name = "user")
public class User {

	public static final int MALE = 1;
	public static final int FEMALE = -1;
	public static final int NULL = 0;

	/* 学号 */
	private String studentId;
	/* 头像 */
	private String headImg;
	/* 性别 */
	private int sex;
	/* 个性签名 */
	private String sign;
	/* 宿舍 */
	private String dormitory;
	/* 昵称 */
	private String nickName;
	/* 联系电话 */
	private String phone;
	/* 工大助手密码 */
	private char[] password;
	/* 剩余水的数量 */
	private int number;

	@Id
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getDormitory() {
		return dormitory;
	}

	public void setDormitory(String dormitory) {
		this.dormitory = dormitory;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean equals(User user) {
		if (user.getStudentId().equals(this.getStudentId())
				&& String.valueOf(UserUtil.encryptPassword(user.getPassword()))
						.equals(String.valueOf(this.getPassword())))
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.accumulate("studentId", studentId);
		json.accumulate("headImg", headImg);
		json.accumulate("sex", sex);
		json.accumulate("sign", sign);
		json.accumulate("dormitory", dormitory);
		json.accumulate("nickName", nickName);
		json.accumulate("phone", phone);
		json.accumulate("number", number);
		return json.toString();
	}
	
	public JSONObject getJson(JSONObject json) {
		json.accumulate("studentId", studentId);
		json.accumulate("headImg", headImg);
		json.accumulate("sex", sex);
		json.accumulate("sign", sign);
		json.accumulate("dormitory", dormitory);
		json.accumulate("nickName", nickName);
		json.accumulate("phone", phone);
		json.accumulate("number", number);
		return json;
	}
}
