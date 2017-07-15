package ssoft.domain;

import java.io.Serializable;

public class UserIsland implements Serializable {
	private int id;
	private int user_id;
	private int island_id;
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
	public int getIsland_id() {
		return island_id;
	}
	public void setIsland_id(int island_id) {
		this.island_id = island_id;
	}

}
