package ssoft.domain;

import java.io.Serializable;
import java.sql.Date;

public class Officialaccounts implements Serializable {
	
	private int id;
	private String name;
	private String corrdinate;
	private int type;
	private Date createtime;
	private String imageurl;
	private String userid;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	private int memberNum;
	public int getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCorrdinate() {
		return corrdinate;
	}
	public void setCorrdinate(String corrdinate) {
		this.corrdinate = corrdinate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
