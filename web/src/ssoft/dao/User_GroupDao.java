package ssoft.dao;

import java.sql.Date;
import java.util.List;

import ssoft.domain.UserGroup;
import ssoft.domain.UserInfo;
import ssoft.domain.UserOfficialaccount;

public interface User_GroupDao extends Dao {

	/**
	 * 通过用户的id获取
	 * @param id
	 * @return
	 */
	List<UserGroup> getGroupsByUserId(String id);

	/**
	 * 通过群组的id查询用户群组关系表
	 * @param group_id
	 * @return
	 */
	List<UserGroup> getGroupsByGroupId(int group_id);

	/**
	 * 添加一条用户  群组记录
	 * @param valueOf
	 * @param id
	 * @param string
	 * @param string2
	 * @param format
	 * @return
	 */
	int addUser(String group_id, String user_id, String isme, String messagesound,
			String deletemembertime);

	/**
	 * 通过用户的id删除记录
	 * @param id
	 * @return
	 */
	int deleteItemByUserId(int id);

	/**
	 * 通过Id去验证家庭是否存在
	 * @param userId
	 * @param groupId
	 * @return
	 */
	UserGroup checkExistById(String userId, String groupId);

	/**
	 * 设置群聊的语音消息消息
	 * @param id
	 * @param targetId
	 * @param messagesound
	 * @return
	 */
	int saveMessagesound(String id, String targetId, String messagesound);

	/**
	 * 根据用户id和群的id删除群成员
	 * @param userId
	 * @param groupId
	 * @return
	 */
	int deleteItemByUserIdAndGroupId(String userId, String groupId);

	/**
	 * 设置这次删除好友的时间
	 * @param groupId 
	 * @param userd 
	 * @param currentTime
	 * @return
	 */
	int saveCurrentdeletetime(String userId, String groupId, Date currentTime);


	List<UserGroup> getUseridfromUserGroup(String id);
}
