package ssoft.domain;

import java.io.Serializable;


/**
 * 用户的基本信息
 * @author Administrator
 *
 */
public class UserBaseInfo implements Serializable {
	private int id;
	private int user_id;
	private String robot_name;//喜鹊名
	private String user_image;//头像
	private int strangerfind;
	private String realname;
	private String birthday;
	private String nativeplace;
	private String liveplace;
	private String userintroduce;
	private String skill; 
	private String robot_icall;
	private String robot_callme;
	private int soundplay_mode;
	private int message_sound;
	private int message_shake;
	private String sockpuppet;
	private String sockpuppet_image;
	private String oldPassword;
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getSockpuppet() {
		return sockpuppet;
	}
	public void setSockpuppet(String sockpuppet) {
		this.sockpuppet = sockpuppet;
	}
	public String getSockpuppet_image() {
		return sockpuppet_image;
	}
	public void setSockpuppet_image(String sockpuppet_image) {
		this.sockpuppet_image = sockpuppet_image;
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
	public String getRobot_name() {
		return robot_name;
	}
	public void setRobot_name(String robot_name) {
		this.robot_name = robot_name;
	}
	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	public int getStrangerfind() {
		return strangerfind;
	}
	public void setStrangerfind(int strangerfind) {
		this.strangerfind = strangerfind;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getNativeplace() {
		return nativeplace;
	}
	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}
	public String getLiveplace() {
		return liveplace;
	}
	public void setLiveplace(String liveplace) {
		this.liveplace = liveplace;
	}
	public String getUserintroduce() {
		return userintroduce;
	}
	public void setUserintroduce(String userintroduce) {
		this.userintroduce = userintroduce;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getRobot_icall() {
		return robot_icall;
	}
	public void setRobot_icall(String robot_icall) {
		this.robot_icall = robot_icall;
	}
	public String getRobot_callme() {
		return robot_callme;
	}
	public void setRobot_callme(String robot_callme) {
		this.robot_callme = robot_callme;
	}
	public int getSoundplay_mode() {
		return soundplay_mode;
	}
	public void setSoundplay_mode(int soundplay_mode) {
		this.soundplay_mode = soundplay_mode;
	}
	public int getMessage_sound() {
		return message_sound;
	}
	public void setMessage_sound(int message_sound) {
		this.message_sound = message_sound;
	}
	public int getMessage_shake() {
		return message_shake;
	}
	public void setMessage_shake(int message_shake) {
		this.message_shake = message_shake;
	}

}
