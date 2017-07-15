package ssoft.dao;

import java.util.List;

import ssoft.domain.UserIsland;

public interface UserIslandDao extends Dao {

	/**
	 * 通过用户的id删除用户萌岛记录
	 * @param id
	 * @return
	 */
	int deleteItemByUserId(int id);

	/**
	 * 创建一条萌岛记录
	 * @param user_id
	 * @param island_id
	 * @return
	 */
	UserIsland createItemById(String user_id, String island_id);

	/**
	 * 获取一个用户的所有萌岛记录
	 * @param id
	 * @return
	 */
	List<UserIsland> getIslandsByUserId(String id);
	
	/**
	 * 作者：zjf
	 * 功能：通过用户id查询萌岛
	 * @param user_id
	 * @return
	 */
	List<UserIsland> getIslandByUserId(String user_id);
	/**
	 * 作者：zjf
	 * 功能：通过萌岛id查询全部用户
	 * @param user_id
	 * @return
	 */
	List<UserIsland> getUserByIslandId(String island_id);

	/**
	 * 删除一条用户与萌岛的记录
	 * @param id
	 * @param islandId
	 * @return
	 */
	int deleteByUserIdAndIslandId(String id, String islandId);

	/**
	 * 通过用户和萌岛的id查询用户是否在萌岛里边
	 * @param id
	 * @param islandId
	 * @return
	 */
	UserIsland checkExistByUserAndIslandId(String id, String islandId);

}
