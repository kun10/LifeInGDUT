package com.LifeInGDUT.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Entity
@Table(name = "water")
public class Water {

	public static final int UNHANDLED = -1;
	public static final int HANDLED = 0;
	public static final int FINISH = 1;

	/* 水单号 */
	private int id;
	/* 学号 */
	private String studentId;
	/* 学生电话 */
	private String sphone;
	/* 宿舍 */
	private String dormitory;
	/* 下单数量 */
	private int number;
	/* 配送员名字 */
	private String deliver;
	/* 配送员电话 */
	private String dphone;
	/* 水单状态 */
	private int state;
	/* 下单时间 */
	private String time;
	/* 完成时间 */
	private String finishTime;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getSphone() {
		return sphone;
	}

	public void setSphone(String sphone) {
		this.sphone = sphone;
	}

	public String getDormitory() {
		return dormitory;
	}

	public void setDormitory(String dormitory) {
		this.dormitory = dormitory;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDeliver() {
		return deliver;
	}

	public void setDeliver(String deliver) {
		this.deliver = deliver;
	}

	public String getDphone() {
		return dphone;
	}

	public void setDphone(String dphone) {
		this.dphone = dphone;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String toString() {
		return JSONArray.fromObject(this).toString();
	}

	public static void main(String[] args) {
		System.out.println(new JSONObject().accumulate("order", JSONArray.fromObject(new Water())).toString());
	}
}
