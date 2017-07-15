package ssoft.service;

import java.util.List;
import java.util.Map;

import ssoft.annotation.Tran;
import ssoft.domain.MemberInfo;
import ssoft.domain.UserInfo;

public interface UserService extends Service {

	/**
	 * 注册用户
	 * 
	 * @return 将返回值封装返回
	 * 
	 */
	@Tran
	String register(UserInfo user);

	/**
	 * 通过手机号去查找绑定天数
	 * @param phone
	 * @return
	 */
	int getBindTimeByPhone(String phone);


	/**
	 * 通过id查询验证登录
	 * @param number
	 * @param password
	 * @return
	 */
	String checkLoginById(String id, String password);

	/**
	 * 通过手机验证登录
	 * @param number
	 * @param password
	 * @return
	 */
	String checkLoginByPhone(String phone, String password);

	
	/**
	 * 查询电话号码是否存在
	 * @param phone
	 * @return
	 */
	boolean checkPhone(String phone);

	/**
	 * 解绑手机号
	 * @param phone
	 * @return
	 */
	@Tran
	int unBindPhone(String phone);

	
	/**
	 * 修改密码验证
	 * @param id
	 * @param password
	 * @param phone
	 * @param countryCode
	 * @param smsCode
	 * @param qinyouName
	 * @param jiatingName
	 * @param jiazuName
	 * @return
	 */
	String retrievePassword(String phone,
			String countryCode, String smsCode, String qinyouName,
			String jiatingName, String jiazuName,String newPassword);

	/**
	 * 获取密码长度
	 * @param id
	 * @param password
	 * @return
	 */
	String getPasswordLength(String id, String password);

	/**
	 * 中心设置密码
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	String resetPasswrod(String id, String oldPassword, String newPassword);

	/**
	 * 查找好友
	 * @param id
	 * @param password
	 * @param user_id
	 * @return
	 */
	String seekUser(String id, String password, String user_id);

	/**
	 * 获取添加好友请求方的信息
	 * @param id
	 * @param password
	 * @param fromId
	 * @return
	 */
	String getAddFriendFromInfo(String id, String password, String fromId);
	
	
	/**
	 * 作者：zjf
	 * 功能：获取用户总数
	 * @param
	 * @return String
	 */
	String getAllMember();
	/**
	 * 作者：zjf
	 * 功能：获取男用户总数
	 * @param
	 * @return String
	 */
	String getAllFemaleMember();
	/**
	 * 作者：zjf
	 * 功能：获取女用户总数
	 * @param
	 * @return String
	 */
	String getAllMaleMember();
	/**
	 * 作者：zjf
	 * 功能：获取今日登陆用户总数
	 * @param
	 * @return String
	 */
	String getTodayMember();
	
	/**
	 * 作者：zx
	 * 功能：获取30日内登陆用户总数
	 * @param
	 * @return String
	 */
	String getMouthMember();
	
	/**
	 * 作者：zjf
	 * 功能：获取用户信息
	 * @param
	 * @return List<MembersInfo>
	 */
	List<MemberInfo> getMembersInfo(int page);
	/**
	 * 作者：zjf
	 * 功能：获取分页信息
	 * @param
	 * @return map
	 */
	Map<String, Integer> getPageInfo(int page);
	
	/**
	 * 获取好友的信息
	 * @param id
	 * @param password
	 * @param friendId
	 * @return
	 */
	String getFriendInfo(String id, String password, String friendId);

	/**
	 * 通过手机号检查用户的信息  用于找回密码
	 * @param phone
	 * @param name
	 * @param type
	 * @return
	 */
	String checkInfoByPhone(String phone, String name, String type);

}
