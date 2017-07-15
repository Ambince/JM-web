package ssoft.service;

import java.util.List;
import java.util.Map;

public interface OfficialaccountService extends Service {

	/**
	 * 创建公众号
	 * @param id
	 * @param password
	 * @param officialaccountName
	 * @param type
	 * @param coordinate
	 * @param messagesound 
	 * @return
	 */
	String createOfficialaccount(String id, String password,
			String officialaccountName, String type, String coordinate, String messagesound);

	/**
	 * 关注或者取消关注公众号
	 * @param id
	 * @param password
	 * @param officialId
	 * @param type
	 * @return
	 */
	String followOfficial(String id, String password, String officialId,
			String type);

	/**
	 * 查找附近的公众号
	 * @param id
	 * @param password
	 * @param latLng
	 * @param type
	 * @return
	 */
	String getNearByOfficial(String id, String password, String latLng,
			String type,String radius);
	
	
	/**
	 * 作者：zjf
	 * 功能：返回城村分页信息
	 * @param
	 * @return
	 */
	Map<String, Integer> getVillagePageInfo(int page);
	/**
	 * 作者：zjf
	 * 功能：返回学校分页信息
	 * @param
	 * @return
	 */
	Map<String, Integer> getSchoolPageInfo(int page);
	/**
	 * 作者：zjf
	 * 功能：返回小区分页信息
	 * @param
	 * @return
	 */
	Map<String, Integer> getCommunityPageInfo(int page);
	/**
	 * 作者：zjf
	 * 功能：返回小区个数
	 * @param
	 * @return
	 */
	int getCommunityNum();
	/**
	 * 作者：zjf
	 * 功能：返回城村个数
	 * @param
	 * @return
	 */
	int getVillageNum();
	/**
	 * 作者：zjf
	 * 功能：返回学校个数
	 * @param
	 * @return
	 */
	int getSchoolNum();
	/**
	 * 作者：zjf
	 * 功能：分页返回小区信息
	 * @param
	 * @return
	 */
	List<Map<String, Object>> getCommunitiesInfo(int page);
	/**
	 * 作者：zjf
	 * 功能：分页返回城村信息
	 * @param
	 * @return
	 */
	List<Map<String, Object>> getVillagesInfo(int page);
	/**
	 * 作者：zjf
	 * 功能：分页返回学校信息
	 * @param
	 * @return
	 */
	List<Map<String, Object>> getSchoolsInfo(int page);

	/**
	 * 获取附近的公众号
	 * @param userid
	 * @param password
	 * @param latitude
	 * @param longitude
	 * @param type 
	 * @return
	 */
	String getNearbyOfficialaccounts(String userid, String password,
			String latitude, String longitude, String type);
	/**
	 * 作者：zjf
	 * 功能：通过id查找公众号
	 * @param
	 * @return
	 */
	String getOfficialacountPositionById(String id, String password, String type,
			String oid);

	/**
	 * 检查堆积与公众号的关系
	 * @param id
	 * @param password
	 * @param officialId
	 * @param diaryId
	 * @return
	 */
	String checkDiary2Official(String id, String password, String officialId,
			String diaryId);

}
