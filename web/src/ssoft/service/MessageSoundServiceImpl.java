package ssoft.service;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import ssoft.dao.UserDao;
import ssoft.dao.User_GroupDao;
import ssoft.dao.User_OfficialaccountDao;
import ssoft.domain.UserGroup;
import ssoft.domain.UserInfo;
import ssoft.factory.BasicFactory;

public class MessageSoundServiceImpl implements MessageSoundService {

	private UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);
	private User_OfficialaccountDao userOfficialaccountDao = BasicFactory
			.getFactory().getDao(User_OfficialaccountDao.class);
	private User_GroupDao userGroupDao = BasicFactory.getFactory().getDao(
			User_GroupDao.class);
	Gson gson = new Gson();

	@Override
	public String saveSet(String id, String password, String type,
			String targetId, String messagesound) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表用户不存在
			return gson.toJson(map);
		}
		if (type.equals("group")) {
			int result = userGroupDao.saveMessagesound(id, targetId,
					messagesound);
			if (result < 0) {
				map.put("flag", "false");
				map.put("errorCode", "-2");// -2数据库导致设置失败
				return gson.toJson(map);
			}
		} else if (type.equals("official")) {
			int result = userOfficialaccountDao.saveMessagesound(id, targetId,
					messagesound);
			if (result < 0) {
				map.put("flag", "false");
				map.put("errorCode", "-2");// -2数据库导致设置失败
				return gson.toJson(map);
			}
		}
		map.put("flag", "true");
		return gson.toJson(map);
	}

	@Override
	public String getSet(String id, String password, String type,
			String targetId) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表用户不存在
			return gson.toJson(map);
		}
		if (type.equals("group")) {
			UserGroup userGroup = userGroupDao.checkExistById(id, targetId);
			if (null == userGroup) {
				map.put("flag", "false");
				map.put("errorCode", "-2");// -2数据库导致设置失败
				return gson.toJson(map);
			}
			map.put("flag", "true");
			map.put("messagesound", userGroup.getMessagesound());
			return gson.toJson(map);
		} else if (type.equals("official")) {
			UserGroup userGroup = userGroupDao.checkExistById(id, targetId);
			if (null == userGroup) {
				map.put("flag", "false");
				map.put("errorCode", "-2");// -2数据库导致设置失败
				return gson.toJson(map);
			}
			map.put("flag", "true");
			map.put("messagesound", userGroup.getMessagesound());
			return gson.toJson(map);
		} else {
			map.put("flag", "false");
			map.put("errorCode", "-3");// -3参数有误
			return gson.toJson(map);
		}

	}

}
