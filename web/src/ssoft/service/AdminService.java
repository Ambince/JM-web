package ssoft.service;

import ssoft.annotation.Tran;
import ssoft.domain.Admin;
public interface AdminService extends Service{
	/**
	 * 作者：zjf
	 * 功能：查找管理员
	 * @param username
	 * @param password
	 * @return boolean
	 */
	@Tran
	Admin getAdminByNameAndPsw (String username, String password);
	/**
	 * 作者：zjf
	 * 功能：添加管理员
	 * @param Admin
	 * @return String
	 */
	String regist(Admin admin);
	/**
	 * 作者：zjf
	 * 功能：修改管理员密码
	 * @param newpassword2 
	 * @param Admin
	 * @return String
	 */
	String modifyPassword(String username, String oldpassword, String newpassword);
}
