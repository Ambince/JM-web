package ssoft.domain;

import java.io.Serializable;

public class GroupChatDiary implements Serializable {
	private int id;
	private int diary_id;
	private int groupchat_id;
	private int sharephoto;
	private int belong;
	public int getBelong() {
		return belong;
	}
	public void setBelong(int belong) {
		this.belong = belong;
	}
	public int getSharephoto() {
		return sharephoto;
	}
	public void setSharephoto(int sharephoto) {
		this.sharephoto = sharephoto;
	}
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
	public int getGroupchat_id() {
		return groupchat_id;
	}
	public void setGroupchat_id(int groupchat_id) {
		this.groupchat_id = groupchat_id;
	}

}
