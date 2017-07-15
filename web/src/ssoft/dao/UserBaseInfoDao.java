package ssoft.dao;

import ssoft.domain.UserBaseInfo;

public interface UserBaseInfoDao extends Dao {

	/**
	 * 通过id去查询用户的基础信息
	 * @param id
	 * @return 
	 */
	UserBaseInfo findUserBaseInfoById(String id);

	/**
	 * 添加一条用户系统设置
	 * @param user_id
	 * @param soundplay_mode
	 * @param message_sound
	 * @param message_shake
	 * @return
	 */
	int addSysset(String user_id, String soundplay_mode, String message_sound,
			String message_shake);

	/**
	 * 更新用户系统设置
	 * @param user_id
	 * @param soundplay_mode
	 * @param message_sound
	 * @param message_shake
	 * @return
	 */
	int updateSysset(String user_id, String soundplay_mode,
			String message_sound, String message_shake);

	/**
	 * 添加用户个人信息
	 * @param user_id
	 * @param robot_icall
	 * @param robot_callme
	 * @param robot_name
	 * @param strangerfind
	 * @param birthday
	 * @param liveplace
	 * @param nativeplace
	 * @param userintroduce
	 * @param skill
	 * @return
	 */
	int addBaseInfo(String user_id,String realname, String strangerfind, String birthday,
			String liveplace, String nativeplace, String userintroduce,
			String skill,String sockpuppet);

	/**
	 * 更新用户信息
	 * @param user_id
	 * @param robot_icall
	 * @param robot_callme
	 * @param robot_name
	 * @param realname
	 * @param strangerfind
	 * @param birthday
	 * @param liveplace
	 * @param nativeplace
	 * @param userintroduce
	 * @param skill
	 * @return
	 */
	int updateBaseInfo(String user_id,String realname, String strangerfind,
			String birthday, String liveplace, String nativeplace,
			String userintroduce, String skill,String sockpuppet);

	/**
	 * 修改
	 * @param id
	 * @param strangerfind
	 */
	int updateBaseInfo(String id, String strangerfind);


	/**
	 * 添加梦喜鹊的信息
	 * @param robot_icall
	 * @param robot_callme
	 * @param robot_name
	 * @return
	 */
	int addRobotInfo(String robot_icall, String robot_callme, String robot_name,String soundplaytext,String user_id);

	/**
	 * 更新梦喜鹊的信息
	 * @param robot_icall
	 * @param robot_callme
	 * @param robot_name
	 * @param id
	 * @return
	 */
	int updateRobotInfo(String robot_icall, String robot_callme,
			String robot_name,String soundplaytext, String user_id);

	/**
	 * 通过用户的id删除记录
	 * @param id
	 * @return
	 */
	int deleteItemByUserId(int id);

	int resetOldPassword(String id, String oldPassword);


	int addBaseInfo(String s, String sockpuppet);

	UserBaseInfo findUserBaseInfoBySockpupet(String sockpuppet);

}
