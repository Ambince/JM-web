package ssoft.domain;

import java.awt.image.SampleModel;
import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class Diary implements Serializable {
	private int id;
	private String content;
	private String image_url;
	private int image_num;
	private List<Diary> remarkList;
	private List<Map<String, String>> remarkUserInfoList;
	private Date releasetime;
	private Diary forwardFromDiary;//转自哪里
	private Map<String, String> forwordFromInfo;
	private int likeNum;
	
	public int getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}
	public List<Map<String, String>> getRemarkUserInfoList() {
		return remarkUserInfoList;
	}
	public void setRemarkUserInfoList(List<Map<String, String>> remarkUserInfoList) {
		this.remarkUserInfoList = remarkUserInfoList;
	}
	public Map<String, String> getForwordFromInfo() {
		return forwordFromInfo;
	}
	public void setForwordFromInfo(Map<String, String> forwordFromInfo) {
		this.forwordFromInfo = forwordFromInfo;
	}
	public Diary getForwardFromDiary() {
		return forwardFromDiary;
	}
	public void setForwardFromDiary(Diary forwardFromDiary) {
		this.forwardFromDiary = forwardFromDiary;
	}
	public List<Diary> getRemarkList() {
		return remarkList;
	}
	public void setRemarkList(List<Diary> remarkList) {
		this.remarkList = remarkList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public int getImage_num() {
		return image_num;
	}
	public void setImage_num(int image_num) {
		this.image_num = image_num;
	}
	public Date getReleasetime() {
		
		
		return releasetime;
	}
	public void setReleasetime(Date releasetime) {
		this.releasetime = releasetime;
	}

	@Override
	public String toString() {
		return "Diary{" +
				"id=" + id +
				", content='" + content + '\'' +
				", image_url='" + image_url + '\'' +
				", image_num=" + image_num +
				", remarkList=" + remarkList +
				", remarkUserInfoList=" + remarkUserInfoList +
				", releasetime=" + releasetime +
				", forwardFromDiary=" + forwardFromDiary +
				", forwordFromInfo=" + forwordFromInfo +
				", likeNum=" + likeNum +
				'}';
	}
}
