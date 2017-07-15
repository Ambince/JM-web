package ssoft.domain;

import java.io.Serializable;

public class UserDiary implements Serializable {

	private int id;
	private int user_id;
	private int diary_id;
	private int ilike;
	private int isme;
	private int report;
	private int favorite;
	private int myphoto;
	private int forward;
	public int getMyphoto() {
		return myphoto;
	}
	public void setMyphoto(int myphoto) {
		this.myphoto = myphoto;
	}
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
	public int getDiary_id() {
		return diary_id;
	}
	public void setDiary_id(int diary_id) {
		this.diary_id = diary_id;
	}
	public int getIlike() {
		return ilike;
	}
	public void setIlike(int ilike) {
		this.ilike = ilike;
	}
	public int getIsme() {
		return isme;
	}
	public void setIsme(int isme) {
		this.isme = isme;
	}

	public int getReport() {
		return report;
	}
	public void setReport(int report) {
		this.report = report;
	}
	public int getForward() {
		return forward;
	}
	public void setForward(int forward) {
		this.forward = forward;
	}
	public int getFavorite() {
		return favorite;
	}
	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	@Override
	public String toString() {
		return "UserDiary{" +
				"id=" + id +
				", user_id=" + user_id +
				", diary_id=" + diary_id +
				", ilike=" + ilike +
				", isme=" + isme +
				", report=" + report +
				", favorite=" + favorite +
				", myphoto=" + myphoto +
				", forward=" + forward +
				'}';
	}
}
