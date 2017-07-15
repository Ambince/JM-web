package ssoft.service;

import ssoft.annotation.Tran;

public interface CreateGroupService extends Service {

	/**
	 * 创建群组
	 * @param id
	 * @param password
	 * @param groupName
	 * @param type
	 * @return
	 */
	@Tran
	String createGroup(String id, String password, String groupName, String type);

}
