package ssoft.domain;

import java.io.Serializable;

public class IslandDiary implements Serializable {

	private int id;
	private int diary_id;
	private int island_id;
	private int ishot;
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
	public int getIsland_id() {
		return island_id;
	}
	public void setIsland_id(int island_id) {
		this.island_id = island_id;
	}
	public int getIshot() {
		return ishot;
	}
	public void setIshot(int ishot) {
		this.ishot = ishot;
	}
}
