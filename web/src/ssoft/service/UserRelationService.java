package ssoft.service;

public interface UserRelationService extends Service {
	

	/**
	 * 添加好友
	 * @param id
	 * @param password
	 * @param friendId
	 * @param call
	 * @param remark
	 * @param message 
	 * @return
	 */
	String addFriends(String id, String password, String friendId,String call ,String remark, String message);

	/**
	 * 通过好友添加
	 * @param id
	 * @param password
	 * @param friendId
	 * @param call
	 * @param remark
	 * @return
	 */
	String confirmFriends(String id, String password, String friendId,
			String call, String remark);

	/**
	 * 作者：zjf
	 * 功能：修改我对好友的信息
	 * @param
	 * @return
	 */
	String setFriendInfo(String userid, String password, String friendid,
			String remark, String call, String message_sound);

	/**
	 * 删除好友
	 * @param id
	 * @param password
	 * @param friendId
	 * @return
	 */
	String deleteFriends(String id, String password, String friendId);

}
