package ssoft.domain;

import java.io.Serializable;
import java.sql.Date;

public class UserGroup implements Serializable {
	private int id;
	private int user_id;
	private int groupchat_id;
	private int isme;
	private Date deletemembertime;
	private int messagesound;
	public int getMessagesound() {
		return messagesound;
	}
	public void setMessagesound(int messagesound) {
		this.messagesound = messagesound;
	}
	public int getId() {
		return id;
	}
	public Date getDeletemembertime() {
		return deletemembertime;
	}
	public void setDeletemembertime(Date deletemembertime) {
		this.deletemembertime = deletemembertime;
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
	
	public int getGroupchat_id() {
		return groupchat_id;
	}
	public void setGroupchat_id(int groupchat_id) {
		this.groupchat_id = groupchat_id;
	}
	public int getIsme() {
		return isme;
	}
	public void setIsme(int isme) {
		this.isme = isme;
	}
	

}
