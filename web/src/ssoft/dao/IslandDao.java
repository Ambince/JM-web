package ssoft.dao;

import java.util.List;

import ssoft.domain.Island;

public interface IslandDao extends Dao {

	/**
	 * 创建萌岛
	 * @param islandName
	 * @return
	 */
	Island createIslandByName(String islandName);

	/**
	 * 查询萌岛是否存在
	 * @param islandName
	 * @return
	 */
	Island getIslandByName(String islandName);

	/**
	 * 获取热门的萌岛 从start 开始  offset个
	 * @param start
	 * @param offset
	 * @return
	 */
	List<Island> getHotIsland(String start, String offset);

	/**
	 * 通过萌岛的名字模糊查找
	 * @param islandName
	 * @return
	 */
	List<Island> searchIslandByLikeName(String islandName);

	/**
	 * 通过萌岛的id 查询萌岛
	 * @param id
	 * @return
	 */
	Island getIslandById(String id);
	
	
	/**
	 * 作者：zjf
	 * 功能：获取全部萌岛
	 * @param islandName
	 * @return
	 */
	List<Island> getAllIsland();
	/**
	 * 作者：zjf
	 * 功能：分页获取萌岛
	 * @param islandName
	 * @return
	 */
	List<Island> getIslandAndPaging(int start, int offset);
	/**
	 * 作者：zjf
	 * 功能：通过萌岛id推荐萌岛
	 * @param islandName
	 * @return
	 */
	int recommendIsland(String id);
	/**
	 * 作者：zjf
	 * 功能：通过萌岛id取消推荐萌岛
	 * @param islandName
	 * @return
	 */
	int cancelRecommendIsland(String id);
	/**
	 * 作者：zjf
	 * 功能：获取全部推荐萌岛
	 * @param islandName
	 * @return
	 */
	List<Island> getRecommendIsland();
	/**
	 * 作者：zjf
	 * 功能：分页获取推荐萌岛
	 * @param islandName
	 * @return
	 */
	List<Island> getRecommendIslandAndPaging(int start, int offset);

	/**
	 * 功能：记录萌岛搜索次数
	 * @param id
	 * @return
	 */
	void saveIslandSearchByIslandId(String id,int search);

}
