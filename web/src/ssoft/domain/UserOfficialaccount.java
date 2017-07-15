package ssoft.domain;

import java.io.Serializable;

public class UserOfficialaccount implements Serializable {

	private int id;
	private int user_id;
	private int officialaccount_id;
	private int isme;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getOfficialaccount_id() {
		return officialaccount_id;
	}
	public void setOfficialaccount_id(int officialaccount_id) {
		this.officialaccount_id = officialaccount_id;
	}
	public int getIsme() {
		return isme;
	}
	public void setIsme(int isme) {
		this.isme = isme;
	}
	
	
}
