package ssoft.service;

import ssoft.annotation.Tran;

public interface UserBaseInfoService extends Service {

	/**
	 * 通过账号密码获取系统设置
	 * @param user_id
	 * @param password
	 * @return
	 */
	@Tran
	String getSysset(String user_id, String password);

	/**
	 * 获取基本信息
	 * @param user_id
	 * @param password
	 * @return
	 */
	@Tran
	String getBaseInfo(String user_id, String password);

	/**
	 * 保存用户的系统设置
	 * @param user_id
	 * @param password
	 * @param soundplay_mode
	 * @param message_sound
	 * @param message_shake
	 * @return
	 */
	@Tran
	String saveSysset(String user_id, String password, String soundplay_mode,
			String message_sound, String message_shake);

	/**
	 * 保存用户基本信息
	 * @param user_id
	 * @param password
	 * @param name
	 * @param literaryname
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
	@Tran
	String saveBaseInfo(String user_id, String password, String name,
			String literaryname,String realname, String strangerfind, String birthday,
			String liveplace, String nativeplace, String userintroduce,
			String skill ,String sockpuppet);

	/**
	 * 获取梦喜鹊的信息
	 * @param id
	 * @param password
	 * @return
	 */
	String getRobotInfo(String id, String password);

	
	/**
	 * 保存梦喜鹊的信息
	 * @param id
	 * @param password
	 * @param robot_icall
	 * @param robot_callme
	 * @param robot_name
	 * @return
	 */
	String saveRobotInfo(String id, String password, String robot_icall,
			String robot_callme, String robot_name,String soundplaytext);

	/**

	 * @param id
	 * @param strangerfind
	 * @return
	 */
    String updateBaseInfo(String id, String strangerfind);
}
