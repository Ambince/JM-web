package ssoft.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import ssoft.domain.UserInfo;

public interface UserDao extends Dao {

	/**
	 * 通过手机查找用户
	 * @param phone
	 * @return
	 */
	UserInfo findUserByPhone(String phone);

	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	int register(UserInfo user);
	
	

	/**
	 * 通过手机号获取绑定时间
	 * @param phone
	 * @return
	 */
	
	UserInfo checkUserById(String number, String password);

	/**
	 * 通过电话验证登录
	 * @param number
	 * @param password
	 * @return
	 */
	UserInfo checkUserByPhone(String number, String password);

	/**
	 * 检查用户是否存在  
	 * @param key  机可以是电话  也可以是id
	 * @return
	 */
	UserInfo checkUserIsExist(String key);

	
	/**
	 * 解绑手机号
	 * @param phone
	 * @return
	 */
	int unBindPhone(String phone);

	/**
	 * 更新用户的名字和昵称
	 * @param user_id
	 * @param name
	 * @param literaryname
	 * @return
	 */
	int updateName(String user_id, String name, String literaryname);

	/**
	 * 通过id获取用户信息
	 * @param user_id
	 * @return
	 */
	UserInfo getUserInfoById(int user_id);

	/**
	 * 保存用户的的token
	 * @param string
	 * @return
	 */
	int saveToken(String token,String id);

	
	/**
	 * 通过id查询用户
	 * @param Id
	 * @return
	 */
	UserInfo findUserById(String Id);

	/**
	 * 通过用户名查找好友
	 * @param qinyouName
	 * @return
	 */
	List<UserInfo> findUserByName(String qinyouName);

	/**
	 * 重设密码
	 * @param id
	 * @param newPassword
	 * @return
	 */
	int resetPassword(String id, String newPassword);


	/**
	 * 作者：zjf
	 * 功能：获取用户
	 * @param
	 * @return List<UserInfo>
	 */
	List<UserInfo> getAllUser();
	
	/**
	 * 作者：zjf
	 * 功能：获取女用户
	 * @param
	 * @return List<UserInfo>
	 */
	List<UserInfo> getAllFemaleUser();
	
	/**
	 * 作者：zjf
	 * 功能：获取男用户
	 * @param
	 * @return List<UserInfo>
	 */
	List<UserInfo> getAllMaleUser();
	
	/**
	 * 作者：zjf
	 * 功能：获取今日登陆用户
	 * @param
	 * @return List<UserInfo>
	 */
	List<UserInfo> getTodayUser();
	
	/**
	 * 作者：zx
	 * 功能：获取30日内登陆用户
	 * @param
	 * @return List<UserInfo>
	 */
	List<UserInfo> getMouthUser();
	/**
	 * 作者：zjf
	 * 功能：分页获取用户
	 * @param
	 * @return List<UserInfo>
	 */
	List<UserInfo> getUserAndPaging(int start, int perPage);

	/**
	 * 更新登陆时间
	 * @param id
	 */
	int updateLoginTime(String id);

	/**
	 * 
	 * @param phone
	 * @param user_id
	 * @return
	 */
	int updatePhoneToEmpty(String phone, String user_id);

	/**
	 * 通过电话号码找到用户信息
	 * @param phone
	 * @return
	 */
	UserInfo getUserInfoByPhone(String phone);


}
