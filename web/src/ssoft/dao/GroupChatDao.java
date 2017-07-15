package ssoft.dao;

import java.util.List;

import ssoft.domain.GroupChat;
import ssoft.domain.UserGroup;

public interface GroupChatDao extends Dao {

	/**
	 * 通过群聊的id查询群信息
	 * @param group_id
	 * @return
	 */
	GroupChat getGroupChatById(int group_id);

	/**
	 * 创建群组
	 * @param groupName
	 * @param type
	 * @return
	 */
	GroupChat createGroup(String groupName, String type);
	
	/**
	 * 删除群组
	 * @param groupName
	 * @param type
	 * @return
	 */
	void deleteGroup(String group_id);

	/**
	 * 通过群聊的名字查找群是否存在
	 * @param groupName
	 * @return
	 */
	GroupChat getJiazuByName(String groupName);

	/**
	 * 通过名字查找群聊
	 * @param jiatingName
	 * @return
	 */
	List<GroupChat> getGroupsByName(String jiatingName);
	
	/**
	 * 作者：zjf
	 * 功能：获取全部班级
	 * @param
	 * @return
	 */
	List<GroupChat> getClassNum();
	/**
	 * 作者：zjf
	 * 功能：获取全部家庭
	 * @param
	 * @return
	 */
	List<GroupChat> getHomeNum();
	/**
	 * 作者：zjf
	 * 功能：获取全部家族
	 * @param
	 * @return
	 */
	List<GroupChat> getClanNum();
	/**
	 * 作者：zjf
	 * 功能：分页获取家族
	 * @param
	 * @return
	 */
	List<GroupChat> getClanAndPaging(int start, int offset);
	/**
	 * 作者：zjf
	 * 功能：分页获取家庭
	 * @param
	 * @return
	 */
	List<GroupChat> getHomeAndPaging(int start, int offset);
	/**
	 * 作者：zjf
	 * 功能：分页获取班级
	 * @param
	 * @return
	 */
	List<GroupChat> getClassAndPaging(int start, int offset);

	/**
	 * 更新家族的族史
	 * @param groupId
	 * @param info
	 * @return
	 */
	int updateNativeInfoById(String groupId, String info);

	/**
	 * 通过名字查询家族
	 * @param name
	 * @return
	 */
	GroupChat getNationByName(String name);

	/**
	 * 通过id改变群聊的名字
	 * @param groupId
	 * @param groupName
	 */
	int resetGroupNameById(String groupId, String groupName);
	
}
