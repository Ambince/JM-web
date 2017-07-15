package ssoft.service;

import java.util.List;
import java.util.Map;

import ssoft.annotation.Tran;

public interface IslandService extends Service {

	/**
	 * 创建萌岛
	 * @param id
	 * @param password
	 * @param islandName
	 * @return
	 */
	@Tran
	String createIsland(String id, String password, String islandName);

	/**
	 * 获取热门的萌岛
	 * @param id
	 * @param password
	 * @param start
	 * @param offset
	 * @return
	 */
	String getHotIsland(String id, String password, String start, String offset);

	/**
	 * 通过萌岛的名字模糊查询萌岛
	 * @param id
	 * @param password
	 * @param islandName
	 * @return
	 */
	String searchIsland(String id, String password, String islandName);

	/**
	 * 获取用户的萌岛
	 * @param id
	 * @param password
	 * @return
	 */
	String getUserIsland(String id, String password);

	
	
	/**
	 * 作者：zjf
	 * 功能：返回萌岛总数量
	 * @param
	 * @return
	 */
	int getAllIsland();
	/**
	 * 作者：zjf
	 * 功能：返回分页信息
	 * @param
	 * @return
	 */
	Map<String, Integer> getPageInfo(int page);
	/**
	 * 作者：zjf
	 * 功能：返回萌岛信息
	 * @param
	 * @return
	 */
	List<Map<String, Object>> getIslandsInfo(int page);

	String getIslandsInfo(String id, String password, int page);
	/**
	 * 作者：zjf
	 * 功能：通过萌岛id推荐萌岛
	 * @param
	 * @return
	 */
	String recommendIsland(String id);
	/**
	 * 作者：zjf
	 * 功能：通过萌岛id取消推荐萌岛
	 * @param
	 * @return
	 */
	String cancleRecommendIsland(String id);
	/**
	 * 作者：zjf
	 * 功能：返回推荐萌岛分页信息
	 * @param
	 * @return
	 */
	Map<String, Integer> getRecommendPageInfo(int page);
	/**
	 * 作者：zjf
	 * 功能：返回推荐萌岛信息
	 * @param
	 * @return
	 */
	List<Map<String, Object>> getRecommendIslandsInfo(int page);

	/**
	 * 获取萌岛的主页的信息
	 * @param id
	 * @param password
	 * @return
	 */
	String getMengdaoPageInfo(String id, String password, String mark);

	/**
	 * 关注萌岛
	 * @param id
	 * @param password
	 * @param islandId
	 * @return
	 */
	String attentionIsland(String id, String password, String islandId);

	/**
	 * 取消关注萌岛
	 * @param id
	 * @param password
	 * @param islandId
	 * @return
	 */
	String unAttentionIsland(String id, String password, String islandId);

	/**
	 * 检查随记与萌岛的关系
	 * @param id
	 * @param password
	 * @param islandId
	 * @param diaryId
	 * @return
	 */
	String checkDiary2Island(String id, String password, String islandId,
			String diaryId);

	
	

}
