package ssoft.domain;

import java.io.Serializable;

public class OfficialaccountDiary implements Serializable {
	private int id;
	private int diary_id;
	private int officialaccount_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDiary_id() {
		return diary_id;
	}
	public void setDiary_id(int diary_id) {
		this.diary_id = diary_id;
	}
	public int getOfficialaccount_id() {
		return officialaccount_id;
	}
	public void setOfficialaccount_id(int officialaccount_id) {
		this.officialaccount_id = officialaccount_id;
	}
}
