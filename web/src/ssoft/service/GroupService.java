package ssoft.service;

import ssoft.annotation.Tran;

import java.util.List;
import java.util.Map;

public interface GroupService extends Service{
	/**
	 * 作者：zjf
	 * 功能：返回班级分页信息
	 * @param
	 * @return
	 */
	Map<String, Integer> getClassPageInfo(int page);
	/**
	 * 作者：zjf
	 * 功能：返回家庭分页信息
	 * @param
	 * @return
	 */
	Map<String, Integer> getHomePageInfo(int page);
	/**
	 * 作者：zjf
	 * 功能：返回家族分页信息
	 * @param
	 * @return
	 */
	Map<String, Integer> getClanPageInfo(int page);
	/**
	 * 作者：zjf
	 * 功能：返回家族个数
	 * @param
	 * @return
	 */
	int getClanNum();
	/**
	 * 作者：zjf
	 * 功能：返回家庭个数
	 * @param
	 * @return
	 */
	int getHomeNum();
	/**
	 * 作者：zjf
	 * 功能：返回班级个数
	 * @param
	 * @return
	 */
	int getClassNum();
	/**
	 * 作者：zjf
	 * 功能：分页返回家族信息
	 * @param
	 * @return
	 */
	List<Map<String, Object>> getClansInfo(int page);
	/**
	 * 作者：zjf
	 * 功能：分页返回家庭信息
	 * @param
	 * @return
	 */
	List<Map<String, Object>> getHomesInfo(int page);
	/**
	 * 作者：zjf
	 * 功能：分页返回班级信息
	 * @param
	 * @return
	 */
	List<Map<String, Object>> getClassesInfo(int page);
	
	/**
	 * 保存族史
	 * @param id
	 * @param password
	 * @param type
	 * @param info
	 * @param groupId
	 * @return
	 */
	String saveNativeInfo(String id, String password, String type, String info,String groupId);
	
	/**
	 * 获取家族的族史
	 * @param id
	 * @param password
	 * @param groupId
	 * @return
	 */
	String getNativeInfo(String id, String password, String groupId);
	
	/**
	 * 获取家族的公开介绍
	 * @param id
	 * @param password
	 * @param groupId
	 * @return
	 */
	String getNativePublicInfo(String id, String password, String groupId);
	
	/**
	 * 重新设置群聊名称
	 * @param id
	 * @param password
	 * @param groupName
	 * @param groupId
	 * @return
	 */
	String resetGroupName(String id, String password, String groupName,
			String groupId);
	
	/**
	 * 检查群组与随记是否包含
	 * @param id
	 * @param password
	 * @param groupId
	 * @param diaryId
	 * @return
	 */
	String checkDiary2Group(String id, String password, String groupId,
			String diaryId);
	
	/**
	 * 检查群组是否存在
	 * @param id
	 * @param password
	 * @param name
	 * @return
	 */
	String checkGroupExist(String id, String password, String name);

}
