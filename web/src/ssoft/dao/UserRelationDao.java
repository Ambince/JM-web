package ssoft.dao;

import java.util.List;

import ssoft.domain.UserRelation;

public interface UserRelationDao extends Dao {

	/**
	 * 得到所有朋友
	 * @param id
	 * @return
	 */
	List<UserRelation> getAllFriends(String id);

	/**
	 * 获取当前用户标记的朋友
	 * @param id
	 * @return
	 */
	List<UserRelation> getCurrentUserFriends(String id);

	/**
	 * 检查好友关系表中是否已经存在
	 * @param id
	 * @param friendId
	 * @return
	 */
	UserRelation checkExist(String id, String friendId);

	/**
	 * 添加好友（无需验证）
	 * @param id
	 * @param friendId

	 * @return
	 */
	int addFrind(String id, String friendId);
	
	/**
	 * 添加好友
	 * @param id
	 * @param friendId
	 * @param remark 
	 * @param call 
	 * @param message 
	 * @return
	 */
	int addFrind(String id, String friendId, String call, String remark, String message);

	/**
	 * 通过好友的添加
	 * @param id
	 * @param password
	 * @param friendId
	 * @param call
	 * @param remark
	 * @return
	 */
	int confirmFriends(String id, String friendId,
			String call, String remark);

	/**
	 * 通过用户的id删除用户的好友
	 * @param id
	 * @return
	 */
	int deleteItemByUserId(int id);
	
	/**
	 * 作者：zjf
	 * 功能：修改我对好友信息1to2
	 * @param
	 * @return
	 */
	int setFriendInfo1To2(String userid, String friendid, String call,
			String remark, String message_sound);
	/**
	 * 作者：zjf
	 * 功能：修改我对好友信息2to1
	 * @param
	 * @return
	 */
	int setFriendInfo2To1(String userid, String friendid, String call,
			String remark, String message_sound);

	/**
	 * 根据id删除好友
	 * @param id
	 * @param friendId
	 * @return
	 */
	int deleteFriendById(String id, String friendId);
	
}
