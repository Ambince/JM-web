package ssoft.service;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import ssoft.dao.UserBaseInfoDao;
import ssoft.dao.UserDao;
import ssoft.dao.UserRelationDao;
import ssoft.domain.UserBaseInfo;
import ssoft.domain.UserInfo;
import ssoft.domain.UserRelation;
import ssoft.factory.BasicFactory;

public class UserRelationServiceImpl implements UserRelationService {
	private UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);
	private UserBaseInfoDao userBaseInfoDao = BasicFactory.getFactory().getDao(UserBaseInfoDao.class);
	private UserRelationDao userRelationDao = BasicFactory.getFactory().getDao(
			UserRelationDao.class);
	Gson gson = new Gson();
	

	@Override
	public String addFriends(String id, String password, String friendId,
			String call, String remark, String message) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		// 1 检查用户
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1"); // -1代表不存在
			return gson.toJson(map);
		}
		UserInfo userFriend = userDao.findUserById(friendId);
		if (null == userFriend) {
			map.put("flag", "false");
			map.put("errorCode", "-1"); // -1代表不存在
			return gson.toJson(map);
		}
		UserBaseInfo userBaseInfoById = userBaseInfoDao.findUserBaseInfoById(friendId);
		if(userBaseInfoById.getStrangerfind()==0){
			map.put("flag", "false");
			map.put("errorCode", "-1"); // -1代表不存在
			return gson.toJson(map);
		}
		UserRelation userRelation = userRelationDao.checkExist(id, friendId);
		if (null != userRelation) {
			if (id.equals(String.valueOf(userRelation.getUser_id1()))) {
				if (String.valueOf(userRelation.getAgree1to2()).equals("1")) {
					map.put("flag", "false");
					map.put("errorCode", "1"); // 1代表已经是好友了
					return gson.toJson(map);
				}
			} else if (id.equals(String.valueOf(userRelation.getUser_id2()))) {
				if (String.valueOf(userRelation.getAgree2to1()).equals("1")) {
					map.put("flag", "false");
					map.put("errorCode", "1"); // 1代表已经是好友了
					return gson.toJson(map);
				}
			}
		}

		// 2 添加用户关系表记录
		int result = userRelationDao.addFrind(id, friendId, call, remark,
				message);
		if (result < 0) {
			map.put("flag", "false");
			map.put("errorCode", "0"); // 0代表数据库操作失败
			return gson.toJson(map);
		}
		map.put("flag", "true");
		return gson.toJson(map);

	}

	@Override
	public String confirmFriends(String id, String password, String friendId,
			String call, String remark) {
		Map<String, String> map = new HashMap<String, String>();
		// 1 检查用户
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1"); // -1代表不存在
			return gson.toJson(map);
		}
		UserInfo userFriend = userDao.findUserById(friendId);
		if (null == userFriend) {
			map.put("flag", "false");
			map.put("errorCode", "-1"); // -1代表不存在
			return gson.toJson(map);
		}

		// 2 检查是否已经通过
		UserRelation userRelation = userRelationDao.checkExist(id, friendId);
		if (null != userRelation) {
			if (id.equals(String.valueOf(userRelation.getUser_id1()))) {
				if (String.valueOf(userRelation.getAgree1to2()).equals("1")) {
					map.put("flag", "false");
					map.put("errorCode", "1"); // 1代表已经是好友了
					return gson.toJson(map);
				}
			} else if (id.equals(String.valueOf(userRelation.getUser_id2()))) {
				if (String.valueOf(userRelation.getAgree2to1()).equals("1")) {
					map.put("flag", "false");
					map.put("errorCode", "1"); // 1代表已经是好友了
					return gson.toJson(map);
				}
			}
		}

		// 3 通过添加

		int result = userRelationDao.confirmFriends(id, friendId, call, remark);

		if (result < 0) {
			map.put("flag", "false");
			map.put("errorCode", "0"); // 1数据库操作失败
			return gson.toJson(map);
		}
		map.put("flag", "true");
		return gson.toJson(map);

	}

	@Override
	public String setFriendInfo(String userid, String password,
			String friendid, String remark, String call, String message_sound) {
		Map<String, String> map = new HashMap<String, String>();
		// 判断是否存在
		UserInfo userInfo = userDao.checkUserById(userid, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1"); // -1代表不存在
			return gson.toJson(map);
		}
		UserInfo userFriend = userDao.findUserById(friendid);
		if (null == userFriend) {
			map.put("flag", "false");
			map.put("errorCode", "-1"); // -1代表不存在
			return gson.toJson(map);
		}
		// 判断是否是好友
		UserRelation userRelation = userRelationDao
				.checkExist(userid, friendid);
		if (null == userRelation) {
			map.put("flag", "false");
			map.put("errorCode", "1"); // 1代表不是好友
			return gson.toJson(map);
		}
		// 修改好友信息
		int result = -1;
		if(userid.equals(String.valueOf(userRelation.getUser_id1()))){
			result = userRelationDao.setFriendInfo1To2(userid, friendid, call,
					remark, message_sound);
		}else if(userid.equals(String.valueOf(userRelation.getUser_id2()))){
			result = userRelationDao.setFriendInfo2To1(userid, friendid, call,
					remark, message_sound);
		}
		
		if (result < 0) {
			map.put("flag", "false");
			map.put("errorCode", "0"); // 0代表数据库操作失败
			return gson.toJson(map);
		}
		map.put("flag", "true");
		return gson.toJson(map);
	}

	@Override
	public String deleteFriends(String id, String password, String friendId) {
		Map<String, String> map = new HashMap<String, String>();
		// 判断是否存在
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1"); // -1代表不存在
			return gson.toJson(map);
		}
		
		int result = -1;
		
		result = userRelationDao.deleteFriendById(id,friendId);
		if(result < 0){
			map.put("flag", "false");
			map.put("errorCode", "-2"); // -2数据库出错
			return gson.toJson(map);
		}
		
		map.put("flag", "true");
		return gson.toJson(map);
	}

}
