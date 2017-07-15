package ssoft.domain;

import java.io.Serializable;

public class MemberInfo implements Serializable{
	private int id;
	private String sex;
	private String registTime;
	private String lastLoginTime;
	//家族数
	private int jz;
	//家庭数
	private int jt;
	//小区数
	private int xq; 
	//城村数
	private int cc; 
	//班级数
	private int bj; 
	//学校数
	private int xx; 
	//萌岛数
	private int md; 
	//好友数
	private int hy;
	//随记数
	private int sj;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getRegistTime() {
		return registTime;
	}
	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public int getJz() {
		return jz;
	}
	public void setJz(int jz) {
		this.jz = jz;
	}
	public int getJt() {
		return jt;
	}
	public void setJt(int jt) {
		this.jt = jt;
	}
	public int getXq() {
		return xq;
	}
	public void setXq(int xq) {
		this.xq = xq;
	}
	public int getCc() {
		return cc;
	}
	public void setCc(int cc) {
		this.cc = cc;
	}
	public int getBj() {
		return bj;
	}
	public void setBj(int bj) {
		this.bj = bj;
	}
	public int getXx() {
		return xx;
	}
	public void setXx(int xx) {
		this.xx = xx;
	}
	public int getMd() {
		return md;
	}
	public void setMd(int md) {
		this.md = md;
	}
	public int getHy() {
		return hy;
	}
	public void setHy(int hy) {
		this.hy = hy;
	}
	public int getSj() {
		return sj;
	}
	public void setSj(int sj) {
		this.sj = sj;
	}
}
