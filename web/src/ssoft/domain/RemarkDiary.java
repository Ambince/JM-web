package ssoft.domain;

import java.io.Serializable;
import java.sql.Date;

public class RemarkDiary implements Serializable {

	private int id;
	private int diary_id;
	private int remark_id;
	private Date releasetime;
	private int mark;
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
	public int getRemark_id() {
		return remark_id;
	}
	public void setRemark_id(int remark_id) {
		this.remark_id = remark_id;
	}
	public Date getReleasetime() {
		return releasetime;
	}
	public void setReleasetime(Date releasetime) {
		this.releasetime = releasetime;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
}
