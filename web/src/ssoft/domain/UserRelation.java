package ssoft.domain;

import java.io.Serializable;

public class UserRelation implements Serializable {
	
	
	private int id;    //
	private int user_id1;   //用户1
	private int user_id2;   //用户2
//	称呼
	private String call1to2;   
	private String call2to1; 
//	备注
	private String remark1to2;
	private String remark2to1;
//	分组
	private String groupid1to2;
	private String groupid2to1;
//	添加好友
	private int agree1to2;
	private int agree2to1;
//	是否开启消息声音
	private int message_sound1to2;
	private int message_sound2to1;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id1() {
		return user_id1;
	}
	public void setUser_id1(int user_id1) {
		this.user_id1 = user_id1;
	}
	public int getUser_id2() {
		return user_id2;
	}
	public void setUser_id2(int user_id2) {
		this.user_id2 = user_id2;
	}
	public String getCall1to2() {
		return call1to2;
	}
	public void setCall1to2(String call1to2) {
		this.call1to2 = call1to2;
	}
	public String getCall2to1() {
		return call2to1;
	}
	public void setCall2to1(String call2to1) {
		this.call2to1 = call2to1;
	}
	public String getRemark1to2() {
		return remark1to2;
	}
	public void setRemark1to2(String remark1to2) {
		this.remark1to2 = remark1to2;
	}
	public String getRemark2to1() {
		return remark2to1;
	}
	public void setRemark2to1(String remark2to1) {
		this.remark2to1 = remark2to1;
	}
	public String getGroupid1to2() {
		return groupid1to2;
	}
	public void setGroupid1to2(String groupid1to2) {
		this.groupid1to2 = groupid1to2;
	}
	public String getGroupid2to1() {
		return groupid2to1;
	}
	public void setGroupid2to1(String groupid2to1) {
		this.groupid2to1 = groupid2to1;
	}
	public int getAgree1to2() {
		return agree1to2;
	}
	public void setAgree1to2(int agree1to2) {
		this.agree1to2 = agree1to2;
	}
	public int getAgree2to1() {
		return agree2to1;
	}
	public void setAgree2to1(int agree2to1) {
		this.agree2to1 = agree2to1;
	}
	public int getMessage_sound1to2() {
		return message_sound1to2;
	}
	public void setMessage_sound1to2(int message_sound1to2) {
		this.message_sound1to2 = message_sound1to2;
	}
	public int getMessage_sound2to1() {
		return message_sound2to1;
	}
	public void setMessage_sound2to1(int message_sound2to1) {
		this.message_sound2to1 = message_sound2to1;
	}
	
	
}
