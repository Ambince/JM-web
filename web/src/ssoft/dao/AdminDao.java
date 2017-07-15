package ssoft.dao;

import ssoft.domain.Admin;

public interface AdminDao extends Dao{
	/**
	 * 作者：zjf
	 * 功能：查找管理员是否存在
	 * @param username
	 * @param password
	 * @return Admin
	 */
	Admin getAdmin (String username, String password);

	/**
	 * 作者：zjf
	 * 功能：添加管理员
	 * @param Admin
	 * @return void 
	 */
	void addAdmin(Admin admin);

	/**
	 * 作者：zjf
	 * 功能：删除管理员
	 * @param id
	 * @return void 
	 */
	void delAdmin(int id);

	/**
	 * 作者：zjf
	 * 功能：通过username查找管理员
	 * @param id
	 * @return Admin 
	 */
	Admin findUserByName(String username);

	void modifyAdminPassword(String string, String newpassword);

}
