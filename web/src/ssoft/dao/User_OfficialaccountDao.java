package ssoft.dao;

import java.util.List;

import ssoft.domain.Officialaccounts;
import ssoft.domain.UserOfficialaccount;

public interface User_OfficialaccountDao extends Dao {

	/**
	 * 获取包含该用户的公众号
	 * @param id
	 * @return
	 */
	List<UserOfficialaccount> getOfficialaccountsByUserId(String id);

	/**
	 * 通过公众号的id查询
	 * @param officialaccount_id
	 * @return
	 */
	List<UserOfficialaccount> getOfficialaccountsByOfficialaccountId(
			int officialaccount_id);

	/**
	 * 
	 * @param messagesound 
	 * @param officialaccountId
	 * @param userId
	 * @return
	 */
	int addUser(int officialaccountId, String userId, String messagesound, String isme);

	
	/**
	 * 通过用户的id删除记录
	 * @param id
	 * @return
	 */
	int deleteItemByUserId(int id);

	/**
	 * 设置公众号消息的声音提示
	 * @param id
	 * @param targetId
	 * @param messagesound
	 * @return
	 */
	int saveMessagesound(String id, String targetId, String messagesound);

	/**
	 * 取消关注公众号
	 * @param user_id
	 * @param officialId
	 */
	int unFollowOfficial(String user_id, String officialId);
	
	/**
	 * 作者：zjf
	 * 功能：获取全部学校
	 * @param
	 * @return
	 */
	List<Officialaccounts> getSchoolNum();
	/**
	 * 作者：zjf
	 * 功能：获取全部小区
	 * @param
	 * @return
	 */
	List<Officialaccounts> getCommunityNum();
	/**
	 * 作者：zjf
	 * 功能：获取全部城村
	 * @param
	 * @return
	 */
	List<Officialaccounts> getVillageNum();
	/**
	 * 作者：zjf
	 * 功能：分页获取小区
	 * @param
	 * @return
	 */
	List<Officialaccounts> getCommunityAndPaging(int start, int offset);
	/**
	 * 作者：zjf
	 * 功能：分页获取城村
	 * @param
	 * @return
	 */
	List<Officialaccounts> getVillageAndPaging(int start, int offset);
	/**
	 * 作者：zjf
	 * 功能：分页获取学校
	 * @param
	 * @return
	 */
	List<Officialaccounts> getSchoolAndPaging(int start, int offset);

	/**
	 * 检查是否存在
	 * @param user_id
	 * @param officialId
	 * @return
	 */
	UserOfficialaccount checkExistById(String user_id, String officialId);

}
