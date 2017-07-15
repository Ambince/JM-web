package ssoft.service;

import java.util.List;

import ssoft.annotation.Tran;

public interface GroupMemberService extends Service {

	/**
	 * 删除群成员
	 * @param id
	 * @param password
	 * @param groupId
	 * @param removeId
	 * @return
	 */
	@Tran
	String removeMember(String id, String password, String groupId,
			String removeId);

	/**
	 * 添加群组成员
	 * @param id
	 * @param password
	 * @param groupId
	 * @param memberIds
	 * @return
	 */
	String addMembers(String id, String password, String groupId,
			List<String> memberIds);

	/**
	 * 获取群聊的成员
	 * @param id
	 * @param password
	 * @param groupId
	 * @return
	 */
	String getMembers(String id, String password, String groupId);
	/**
	 * 获取群聊的成员数量
	 * @param id
	 * @param password
	 * @param groupId
	 * @return
	 */
	String getMembersNum(String id, String password, String groupId);

	/**
	 * 检查成员是否在群组里边
	 * @param id
	 * @param password
	 * @param groupId
	 * @param memberIds
	 * @return
	 */
	String checkMembers(String id, String password, String groupId,
			List<String> memberIds);


}
