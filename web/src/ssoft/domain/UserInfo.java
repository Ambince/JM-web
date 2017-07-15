package ssoft.domain;

import java.io.Serializable;
import java.sql.Date;

/**
 * 用户的常用信息
 * @author Administrator
 *
 */
public class UserInfo implements Serializable {
	

	private int id;
	public Date getRegistertime() {
		return registertime;
	}
	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}
	public Date getBindtime() {
		return bindtime;
	}
	public void setBindtime(Date bindtime) {
		this.bindtime = bindtime;
	}
	public int getPasswordlength() {
		return passwordlength;
	}
	public void setPasswordlength(int passwordlength) {
		this.passwordlength = passwordlength;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String name;
	private String literaryName;
	private int gender;
	private String countryCode;
	private String phone;
	private String password;
	private String token;
	private Date registertime;
	private Date bindtime;
	private Date lastlogintime;
	private String smsCode;

	public Date getLastlogintime() {
		return lastlogintime;
	}
	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}
	private int passwordlength;


	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String code) {
		this.smsCode = code;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", name=" + name + ", literaryName="
				+ literaryName + ", gender=" + gender + ", countryCode="
				+ countryCode + ", phone=" + phone + ", password=" + password
				+ ", token=" + token + ", registertime=" + registertime
				+ ", bindtime=" + bindtime + ", passwordlength="
				+ passwordlength + ", smsCode=" + smsCode + "]";
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLiteraryName() {
		return literaryName;
	}
	public void setLiteraryName(String literaryName) {
		this.literaryName = literaryName;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	

}
