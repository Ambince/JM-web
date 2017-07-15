package ssoft.service;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;

import java.util.*;

import ssoft.dao.GroupChatDao;
import ssoft.dao.OfficialaccountsDao;
import ssoft.dao.UserBaseInfoDao;
import ssoft.dao.UserDao;
import ssoft.dao.UserDiaryDao;
import ssoft.dao.UserIslandDao;
import ssoft.dao.UserRelationDao;
import ssoft.dao.User_GroupDao;
import ssoft.dao.User_OfficialaccountDao;
import ssoft.domain.GroupChat;
import ssoft.domain.MemberInfo;
import ssoft.domain.Officialaccounts;
import ssoft.domain.UserBaseInfo;
import ssoft.domain.UserGroup;
import ssoft.domain.UserInfo;
import ssoft.domain.UserOfficialaccount;
import ssoft.domain.UserRelation;
import ssoft.factory.BasicFactory;
import ssoft.global.Global;
import ssoft.utils.Page;

import com.google.gson.Gson;

public class UserServiceImpl implements UserService {

	private UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);
	private UserRelationDao userRelationDao = BasicFactory.getFactory().getDao(
			UserRelationDao.class);
	private UserBaseInfoDao userBaseInfoDao = BasicFactory.getFactory().getDao(
			UserBaseInfoDao.class);
	private User_OfficialaccountDao userOfficialaccountDao = BasicFactory
			.getFactory().getDao(User_OfficialaccountDao.class);
	private User_GroupDao userGroupDao = BasicFactory.getFactory().getDao(
			User_GroupDao.class);
	private UserIslandDao userIslandDao = BasicFactory.getFactory().getDao(
			UserIslandDao.class);
	private UserDiaryDao userDiaryDao = BasicFactory.getFactory().getDao(
			UserDiaryDao.class);
	private GroupChatDao groupChatDao = BasicFactory.getFactory().getDao(
			GroupChatDao.class);
	private OfficialaccountsDao officialaccountsDao = BasicFactory.getFactory()
			.getDao(OfficialaccountsDao.class);

	Gson gson = new Gson();

	public String register(UserInfo user) {

		Map<String, Object> map = new HashMap<String, Object>();

		UserInfo userInfo = null;
		userInfo = userDao.findUserByPhone(user.getPhone());

		// 账号已经存在
		if (userInfo != null) {

			// 大于三十天
			if (getBindTimeByPhone(user.getPhone()) >= 30) {

				unBindPhone(user.getPhone());

//				// 获取token
//				SdkHttpResult token = null;
//				try {
//
////					token = ApiHttpClient.getToken(Global.Rong_App_Key,
////							Global.Rong_App_Secret,
////							String.valueOf(user.getPhone()), user.getName(),
////							"http://aa.com/a.png", FormatType.json);
//					token = ApiHttpClient.getToken(Global.Rong_App_Key,
//							Global.Rong_App_Secret,
//							String.valueOf(userInfo.getId()),
//							userInfo.getName(), "http://aa.com/a.png",
//							FormatType.json);
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//				Map<String, String> mapToken = new HashMap<String, String>();
//
//				mapToken = gson.fromJson(token.getResult(), Map.class);
//
//				user.setToken(mapToken.get("token"));
//				// 注册用户
//				int result = -1;
//
//				result = userDao.register(user);
//				// 由于数据库操作导致注册失败
//				if (result < 0) {
//					map.put("flag", "false");
//					map.put("errorCode", "-1");
//					return gson.toJson(map);
//				}
//				userInfo = userDao.findUserByPhone(user.getPhone());
//
//				System.out.println(userInfo);
//
//				// 注册成功
//				map.put("flag", "true");
//
//				map.put("userId", String.valueOf(userInfo.getId()));
//				return gson.toJson(map);
				
				// 注册用户
				int result = -1;

				result = userDao.register(user);
				// 由于数据库操作导致注册失败
				if (result < 0) {
					map.put("flag", "false");
					map.put("errorCode", "-1");// -1数据库操作失败
					return gson.toJson(map);
				}
				userInfo = userDao.findUserByPhone(user.getPhone());

				System.out.println(userInfo);

				if (null != userInfo) {
					// 获取token
					SdkHttpResult token = null;
					try {
						token = ApiHttpClient.getToken(Global.Rong_App_Key,
								Global.Rong_App_Secret,
								String.valueOf(userInfo.getId()),
								userInfo.getName(), "http://aa.com/a.png",
								FormatType.json);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					Map<String, String> mapToken = new HashMap<String, String>();

					mapToken = gson.fromJson(token.getResult(), Map.class);

					user.setToken(mapToken.get("token"));
					int resut = userDao.saveToken(mapToken.get("token"),
							String.valueOf(userInfo.getId()));
					if (result < 0) {
						map.put("flag", "false");
						map.put("errorCode", "-1");// -1数据库操作失败
						return gson.toJson(map);
					}
					// 注册成功
					map.put("flag", "true");

//				T001
                    addBaseInfo(userInfo.getId(),userInfo.getName());
					map.put("userId", String.valueOf(userInfo.getId()));
					return gson.toJson(map);
				} else {
					map.put("flag", "false");
					map.put("errorCode", "-1");// -1数据库操作失败
					return gson.toJson(map);
				}

			}
		}

		// 账号还没有注册

		if (null == userInfo) {

			// 注册用户
			int result = -1;

			result = userDao.register(user);
			// 由于数据库操作导致注册失败
			if (result < 0) {
				map.put("flag", "false");
				map.put("errorCode", "-1");// -1数据库操作失败
				return gson.toJson(map);
			}


			userInfo = userDao.findUserByPhone(user.getPhone());

			System.out.println(userInfo);

			if (null != userInfo) {
				// 获取token
				SdkHttpResult token = null;
				try {
					token = ApiHttpClient.getToken(Global.Rong_App_Key,
							Global.Rong_App_Secret,
							String.valueOf(userInfo.getId()),
							userInfo.getName(), "http://aa.com/a.png",
							FormatType.json);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Map<String, String> mapToken = new HashMap<String, String>();

				mapToken = gson.fromJson(token.getResult(), Map.class);

				user.setToken(mapToken.get("token"));
				int resut = userDao.saveToken(mapToken.get("token"),
						String.valueOf(userInfo.getId()));
				if (result < 0) {
					map.put("flag", "false");
					map.put("errorCode", "-1");// -1数据库操作失败
					return gson.toJson(map);
				}
				// 注册成功
				map.put("flag", "true");
//				T001
				addBaseInfo(userInfo.getId(),userInfo.getName());
				map.put("userId", String.valueOf(userInfo.getId()));
				return gson.toJson(map);
			} else {
				map.put("flag", "false");
				map.put("errorCode", "-1");// -1数据库操作失败
				return gson.toJson(map);
			}

		} else {

			// 1已经注册
			map.put("flag", "false");
			map.put("errorCode", "1");
			return gson.toJson(map);
		}
	}

	/**
	 * T001在用户注册的时候添加baseInfo
	 * @param userId
	 * @return
	 */
	public  boolean addBaseInfo(int userId,String username){
        char[] chars = username.toCharArray();
        Date date = new Date();
        String sockpuppet=chars[0]+date.getYear()+date.getMonth()+date.getDay()+date.getHours()+date.getMinutes()+date.getSeconds()+"";
		int i=userBaseInfoDao.addBaseInfo(String.valueOf(userId),sockpuppet);
		if(i>0)return true;
		else return false;
	}
	@Override
	public int getBindTimeByPhone(String phone) {
		// TODO Auto-generated method stub
		if (null == userDao.findUserByPhone(phone)) {
			System.out.println("=====");
			return -1;
		}
		Date bingTime = userDao.findUserByPhone(phone).getBindtime();
		System.out.println(bingTime);
		if (null != bingTime) {

			long bindTime = bingTime.getTime();

			long currentTime = new Date().getTime();

			return (int) ((currentTime - bindTime) / 1000 / 3600 / 24);
		}
		return -1;

	}

	@Override
	public String checkLoginById(String id, String password) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();

		UserInfo userInfo = userDao.findUserById(id);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "0");// 0用户账号不存在
			return gson.toJson(map);
		}

		// 检查用户名密码是否匹配
		userInfo = userDao.checkUserById(id, password);
		if (null != userInfo) {
			int result = -1;
			result = userDao.updateLoginTime(id);
			if (result == -1) {
				map.put("flag", "false");
				map.put("errorCode", "-2");// -2数据库错误

				return gson.toJson(map);
			}
			map.put("flag", "true");
			map.put("id", String.valueOf(userInfo.getId()));
			map.put("token", userInfo.getToken());
			map.put("name", userInfo.getName());
			map.put("literaryName", userInfo.getLiteraryName());
			return gson.toJson(map);

		} else {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1用户账号密码错误

			return gson.toJson(map);
		}

	}

	@Override
	public String checkLoginByPhone(String phone, String password) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		UserInfo userInfo = userDao.findUserByPhone(phone);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "0");// 0用户账号不存在
			return gson.toJson(map);
		}

		// 检查用户名密码是否匹配
		userInfo = userDao.checkUserByPhone(phone, password);

		if (null != userInfo) {
			int result = userDao.updateLoginTime(String.valueOf(userInfo.getId()));
			if (result == -1) {
				map.put("flag", "false");
				map.put("errorCode", "-2");// -2数据库错误

				return gson.toJson(map);
			}
			map.put("flag", "true");
			map.put("id", String.valueOf(userInfo.getId()));
			map.put("token", userInfo.getToken());
			map.put("name", userInfo.getName());
			map.put("literaryName", userInfo.getLiteraryName());
			System.out.println(map);
			return gson.toJson(map);
		} else {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1用户账号密码错误

			return gson.toJson(map);

		}

	}

	@Override
	public boolean checkPhone(String phone) {
		// TODO Auto-generated method stub
		UserInfo userInfo = userDao.findUserByPhone(phone);
        return !(null == userInfo || phone.equals(""));
    }

	@Override
	public int unBindPhone(String phone) {
		// TODO Auto-generated method stub
		UserInfo userInfo = userDao.findUserByPhone(phone);
		if (null != userInfo) {
			return userDao.updatePhoneToEmpty(phone,String.valueOf(userInfo.getId()));
		}
		return -1;
		
//		if (null == userInfo) {
//			return -1;
//		}
//		int result = -1;
//		// 1删除用户基本信息
//		result = userBaseInfoDao.deleteItemByUserId(userInfo.getId());
//		if (result < 0) {
//			return result;
//		}
//		// 2删除该用户的好友
//		result = userRelationDao.deleteItemByUserId(userInfo.getId());
//		if (result < 0) {
//			return result;
//		}
//
//		// 4删除该用户所在的公众号记录
//		result = userOfficialaccountDao.deleteItemByUserId(userInfo.getId());
//		if (result < 0) {
//			return result;
//		}
//		// 5 删除该用户所在的群
//		result = userGroupDao.deleteItemByUserId(userInfo.getId());
//		if (result < 0) {
//			return result;
//		}
//		// 6删除该用户的萌岛记录
//		result = userIslandDao.deleteItemByUserId(userInfo.getId());
//		if (result < 0) {
//			return result;
//		}
//		// 7 删除该用户的随记
//		result = userDiaryDao.deleteItemByUserId(userInfo.getId());
//		if (result < 0) {
//			return result;
//		}
//
//		return userDao.unBindPhone(phone);

	}

	@Override
	public String retrievePassword( String phone,
			String countryCode, String smsCode, String qinyouName,
			String jiatingName, String jiazuName, String newPassword) {
		// 验证用户是否存在
//		UserInfo userInfo = userDao.checkUserById(id, password);
		Map<String, String> map = new HashMap<String, String>();
//		if (null == userInfo) {
//			map.put("flag", "false");
//			map.put("errorCode", "-1");// -1用户不存在
//			return gson.toJson(map);
//		}
		UserInfo userInfo = userDao.findUserByPhone(phone);

		// //2 验证短信
		// try {
		// String result ="";
		// result= new SmsVerifyKit(Global.Mob_App_Key,
		// phone,countryCode,smsCode).go();
		// System.out.println(result);
		// Map<String, Double> map0 = new HashMap<String, Double>();
		//
		// if (null == result) {
		// map.put("flag", "false");
		// map.put("errorCode", "0"); //0短信验证失败
		// return gson.toJson(map);
		// }
		// map0 = gson.fromJson(result, Map.class);
		// System.out.println(map0.get("status"));
		// if(map0.get("status") != 200){
		// map.put("flag", "false");
		// map.put("errorCode","0"); //0短信验证失败
		// return gson.toJson(map);
		// }
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// 3 验证用户亲友
		List<UserInfo> friendInfoList = new ArrayList<UserInfo>();
		friendInfoList = userDao.findUserByName(qinyouName);
		if (friendInfoList.size() <= 0) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// -2代表亲友不存在
			return gson.toJson(map);
		}
		for (int i = 0; i < friendInfoList.size(); i++) {
			UserInfo userInfo2 = friendInfoList.get(i);
			UserRelation relation = userRelationDao.checkExist(String.valueOf(userInfo.getId()),
					String.valueOf(userInfo2.getId()));
			if (null != relation) {
				break;
			}
			if (i == friendInfoList.size() - 1) {
				map.put("flag", "false");
				map.put("errorCode", "-2");// -2代表亲友不存在
				return gson.toJson(map);
			}

		}

		// 4 验证家庭
		List<GroupChat> groupChats = new ArrayList<GroupChat>();
		groupChats = groupChatDao.getGroupsByName(jiatingName);
		for (int i = 0; i < groupChats.size(); i++) {
			GroupChat groupChat = groupChats.get(i);
			if (groupChat.getType() == 1) {// 家庭
				UserGroup userGroup = userGroupDao.checkExistById(String.valueOf(userInfo.getId()),
						String.valueOf(groupChat.getId()));
				if (null != userGroup) {
					break;
				}
				if (i == groupChats.size() - 1) {
					map.put("flag", "false");
					map.put("errorCode", "-3");// -3代表家庭不存在
					return gson.toJson(map);
				}
			}

		}

		// 5 验证家族
		List<GroupChat> groupChats1 = new ArrayList<GroupChat>();
		groupChats1 = groupChatDao.getGroupsByName(jiazuName);
		for (int i = 0; i < groupChats1.size(); i++) {
			GroupChat groupChat = groupChats1.get(i);
			if (groupChat.getType() == 2) {// 家族
				UserGroup userGroup = userGroupDao.checkExistById(String.valueOf(userInfo.getId()),
						String.valueOf(groupChat.getId()));
				if (null != userGroup) {
					break;
				}
				if (i == groupChats1.size() - 1) {
					map.put("flag", "false");
					map.put("errorCode", "-4");// -4代表家族不存在
					return gson.toJson(map);
				}
			}

		}

		// 6 修改密码
		int result = userDao.resetPassword(String.valueOf(userInfo.getId()), newPassword);
		if (result < 0) {
			map.put("flag", "false");
			map.put("errorCode", "-5");// -5数据库错误
			return gson.toJson(map);
		}

		map.put("flag", "true");
		return gson.toJson(map);
	}

	@Override
	public String getPasswordLength(String id, String password) {
		Map<String, String> map = new HashMap<String, String>();
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1用户不存在
			return gson.toJson(map);
		}

		map.put("flag", "true");
		map.put("passwordLength", String.valueOf(userInfo.getPasswordlength()));

		return gson.toJson(map);
	}

	@Override
	public String resetPasswrod(String id, String oldPassword,
			String newPassword) {
		// 检查用户
		Map<String, String> map = new HashMap<String, String>();
		UserInfo userInfo = userDao.checkUserById(id, oldPassword);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1用户不存在
			return gson.toJson(map);
		}
		// 重新设置密码
		int result = userDao.resetPassword(id, newPassword);
		if (result < 0) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// -2数据库失败
			return gson.toJson(map);
		}

		// 保存原来的密码
		UserBaseInfo userBaseInfo = userBaseInfoDao.findUserBaseInfoById(id);
		result = -1;
		result = userBaseInfoDao.resetOldPassword(id,
				userBaseInfo.getOldPassword() + "," + oldPassword);
		if (result < 0) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// -2数据库失败
			return gson.toJson(map);
		}

		map.put("flag", "true");

		return gson.toJson(map);
	}

	@Override
	public String seekUser(String id, String password, String user_id) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 1判断用户存不存在
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1用户账号不存在
			return gson.toJson(map);
		}
		// 判断是否在一个群组里边
		UserInfo userInfo2 = userDao.getUserInfoById(Integer.parseInt(user_id));
		// 2判断请求的用户是否允许陌生人添加

		if (null == userInfo2) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// -2查找的的用户不存在
			return gson.toJson(map);
		}

		List<UserGroup> Mygroups = userGroupDao.getGroupsByUserId(id);
		// 有群的话判断在不在一个群里
		if (Mygroups.size() > 0) {
			for (int i = 0; i < Mygroups.size(); i++) {
				UserGroup userGroup2 = userGroupDao.checkExistById(user_id,
						String.valueOf(Mygroups.get(i).getId()));
				if (null != userGroup2) {
					break;
				}
				if (i == Mygroups.size() - 1) {

					UserBaseInfo userBaseInfo = userBaseInfoDao
							.findUserBaseInfoById(user_id);
					if (null != userBaseInfo) {
						if (userBaseInfo.getStrangerfind() == 0) {
							map.put("flag", "false");
							map.put("errorCode", "-3");// -3用户陌生人不能添加
							map.put("gender",
									String.valueOf(userInfo2.getGender()));
							return gson.toJson(map);
						}
					}

				}

			}

		}

		// 没有的话判断允不允许陌生人加好友
		UserBaseInfo userBaseInfo = userBaseInfoDao
				.findUserBaseInfoById(user_id);
		if (null != userBaseInfo) {
			if (userBaseInfo.getStrangerfind() == 0) {
				map.put("flag", "false");
				map.put("errorCode", "-3");// -3用户陌生人不能添加
				map.put("gender", String.valueOf(userInfo2.getGender()));
				return gson.toJson(map);
			}
		}

		// 3查找家族
		List<UserGroup> userGroups = userGroupDao.getGroupsByUserId(user_id);
		String str = "";
		for (UserGroup userGroup : userGroups) {
			GroupChat groupChat = groupChatDao.getGroupChatById(userGroup
					.getGroupchat_id());
			if (groupChat.getType() == 2) {
				str += groupChat.getName().charAt(0) + ",";
			}

		}

		map.put("flag", "true");
		map.put("groups", str);
		map.put("gender", String.valueOf(userInfo2.getGender()));

		return gson.toJson(map);

	}

	@Override
	public String getAddFriendFromInfo(String id, String password, String fromId) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 1判断用户存不存在
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1用户账号不存在
			return gson.toJson(map);
		}

		// 2查找到该用户
		UserInfo userInfo2 = userDao.getUserInfoById(Integer.parseInt(fromId));
		if (null == userInfo2) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// -2查找的的用户不存在
			return gson.toJson(map);
		}

		// 3获取到家族

		List<UserGroup> userGroups = userGroupDao.getGroupsByUserId(String
				.valueOf(userInfo2.getId()));
		String str = "";
		for (UserGroup userGroup : userGroups) {
			GroupChat groupChat = groupChatDao.getGroupChatById(userGroup
					.getId());
			if (null != groupChat) {
				str += groupChat.getName() + ",";
			}

		}

		// 4获取验证信息

		UserRelation userRelation = userRelationDao.checkExist(id, fromId);

		map.put("flag", "true");
		map.put("gender", String.valueOf(userInfo2.getGender()));
		map.put("groups", str);
		map.put("name", userInfo2.getName());

		return gson.toJson(map);
	}

	@Override
	public String getAllMember() {
		List<UserInfo> userGroups = userDao.getAllUser();
		return userGroups.size() + "";
	}

	@Override
	public String getAllFemaleMember() {
		List<UserInfo> userGroups = userDao.getAllFemaleUser();
		return userGroups.size() + "";
	}

	@Override
	public String getAllMaleMember() {
		List<UserInfo> userGroups = userDao.getAllMaleUser();
		return userGroups.size() + "";
	}

	@Override
	public String getTodayMember() {
		List<UserInfo> userGroups = userDao.getTodayUser();
		return userGroups.size() + "";
	}
	
	@Override
	public String getMouthMember() {
		List<UserInfo> userGroups = userDao.getMouthUser();
		return userGroups.size() + "";
	}

	@Override
	public List<MemberInfo> getMembersInfo(int page) {
		/*
		 * 作者：zjf 功能：分页
		 */
		int[] pageInfo = Page
				.getPageInfo(page, userDao.getAllUser().size(), 10);
		// 分页查询
		List<UserInfo> userInfos = userDao.getUserAndPaging(pageInfo[0],
				pageInfo[1]);
		List<MemberInfo> membersInfo = new ArrayList<MemberInfo>();
		for (UserInfo userInfo : userInfos) {
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setId(userInfo.getId());
			memberInfo.setSex(userInfo.getGender() == 0 ? "女" : "男");
			memberInfo.setRegistTime(userInfo.getRegistertime().toString());
			memberInfo.setLastLoginTime(userInfo.getLastlogintime().toString());
			for (UserGroup ug : userGroupDao.getGroupsByUserId(userInfo.getId()
					+ "")) {
				GroupChat gc = groupChatDao.getGroupChatById(ug
						.getGroupchat_id());
				switch (gc.getType()) {
				case 0:
					memberInfo.setBj(memberInfo.getBj() + 1);
					break;
				case 1:
					memberInfo.setJt(memberInfo.getJt() + 1);
					break;
				case 2:
					memberInfo.setJz(memberInfo.getJz() + 1);
					break;
				}
			}
			for (UserOfficialaccount uo : userOfficialaccountDao
					.getOfficialaccountsByUserId(userInfo.getId() + "")) {
				Officialaccounts o = officialaccountsDao
						.getOfficialaccountById(uo.getOfficialaccount_id());
				switch (o.getType()) {
				case 0:
					memberInfo.setCc(memberInfo.getCc() + 1);
					break;
				case 1:
					memberInfo.setXq(memberInfo.getXq() + 1);
					break;
				case 2:
					memberInfo.setXx(memberInfo.getXx() + 1);
					break;
				}
			}
			memberInfo.setMd(userIslandDao.getIslandByUserId(
					userInfo.getId() + "").size());
			memberInfo.setHy(userRelationDao.getAllFriends(
					userInfo.getId() + "").size());
			memberInfo.setSj(userDiaryDao.getDiaryByUserId(
					userInfo.getId() + "").size());
			membersInfo.add(memberInfo);
		}
		return membersInfo;
	}

	@Override
	public Map<String, Integer> getPageInfo(int page) {
		int pageInfo[] = Page
				.getPageInfo(page, userDao.getAllUser().size(), 10);
		int pagenum = pageInfo[2];
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("pagenum", pagenum);
		map.put("curpage", page);
		return map;
	}

	@Override
	public String getFriendInfo(String id, String password, String friendId) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 1判断用户存不存在
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1用户账号不存在
			return gson.toJson(map);
		}

		// 2检查是不是好友
		UserRelation userRelation = userRelationDao.checkExist(id, friendId);
		if (null != userRelation) {
			if (id.equals(String.valueOf(userRelation.getUser_id1()))) {
				if (userRelation.getAgree2to1() == 1) {
					map.put("remark", userRelation.getRemark1to2());
					map.put("call", userRelation.getCall1to2());
					map.put("message_sound",
							String.valueOf(userRelation.getMessage_sound1to2()));
				}

			} else if (id.equals(String.valueOf(userRelation.getUser_id2()))) {
				if (userRelation.getAgree1to2() == 1) {
					map.put("remark", userRelation.getRemark2to1());
					map.put("call", userRelation.getCall2to1());
					map.put("message_sound",
							String.valueOf(userRelation.getMessage_sound2to1()));
				}

			}
		}

		// 3查询好友的信息
		UserInfo friendInfo = userDao.findUserById(friendId);
		if (null == friendInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-2");
			return gson.toJson(map);
		}
		UserBaseInfo userBaseInfo = userBaseInfoDao
				.findUserBaseInfoById(friendId);
		List<UserGroup> userGroupList = userGroupDao.getGroupsByUserId(String
				.valueOf(friendInfo.getId()));

		List<String> familyList = new ArrayList<String>();
		List<String> nationList = new ArrayList<String>();

		for (UserGroup userGroup2 : userGroupList) {
			GroupChat groupChat = groupChatDao.getGroupChatById(userGroup2
					.getGroupchat_id());
			if (groupChat.getType() == 1) {
				familyList.add(groupChat.getName());
			} else if (groupChat.getType() == 2) {
				nationList.add(groupChat.getName());
			}
		}

		map.put("flag", "true");
		if (userBaseInfo != null ) {
			map.put("robotName", userBaseInfo.getSockpuppet());
		}else{
			map.put("robotName", "萌喜鹊");
		}
		
		map.put("gender", String.valueOf(friendInfo.getGender()));
		map.put("familyList", familyList);
		map.put("nationList", nationList);

		return gson.toJson(map);
	}

	@Override
	public String checkInfoByPhone(String phone, String name, String type) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 1判断用户存不存在
		UserInfo userInfo = userDao.getUserInfoByPhone(phone);
		if(type.equals("friend")){
			// 3 验证用户亲友
			List<UserInfo> friendInfoList = new ArrayList<UserInfo>();
			friendInfoList = userDao.findUserByName(name);
			if (friendInfoList.size() <= 0) {
				map.put("flag", "false");
				map.put("errorCode", "-2");// -2代表亲友不存在
				return gson.toJson(map);
			}
			for (int i = 0; i < friendInfoList.size(); i++) {
				UserInfo userInfo2 = friendInfoList.get(i);
				UserRelation relation = userRelationDao.checkExist(String.valueOf(userInfo.getId()),
						String.valueOf(userInfo2.getId()));
				if (null != relation) {
					break;
				}
				if (i == friendInfoList.size() - 1) {
					map.put("flag", "false");
					map.put("errorCode", "-2");// -2代表亲友不存在
					return gson.toJson(map);
				}

			}
		}else if(type.equals("family")){
			// 4 验证家庭
			List<GroupChat> groupChats = new ArrayList<GroupChat>();
			groupChats = groupChatDao.getGroupsByName(name);
			if (groupChats.size() <= 0) {
				map.put("flag", "false");
				map.put("errorCode", "-3");// -3代表家庭不存在
				return gson.toJson(map);
			}
			for (int i = 0; i < groupChats.size(); i++) {
				GroupChat groupChat = groupChats.get(i);
				if (groupChat.getType() == 1) {// 家庭
					UserGroup userGroup = userGroupDao.checkExistById(String.valueOf(userInfo.getId()),
							String.valueOf(groupChat.getId()));
					if (null != userGroup) {
						break;
					}
					if (i == groupChats.size() - 1) {
						map.put("flag", "false");
						map.put("errorCode", "-3");// -3代表家庭不存在
						return gson.toJson(map);
					}
				}

			}
		}else if(type.equals("nation")){

			// 5 验证家族
			List<GroupChat> groupChats1 = new ArrayList<GroupChat>();
			groupChats1 = groupChatDao.getGroupsByName(name);
			if (groupChats1.size() <= 0) {
				map.put("flag", "false");
				map.put("errorCode", "-4");// -4代表家族不存在
				return gson.toJson(map);
			}
			for (int i = 0; i < groupChats1.size(); i++) {
				GroupChat groupChat = groupChats1.get(i);
				if (groupChat.getType() == 2) {// 家族
					UserGroup userGroup = userGroupDao.checkExistById(String.valueOf(userInfo.getId()),
							String.valueOf(groupChat.getId()));
					if (null != userGroup) {
						break;
					}
					if (i == groupChats1.size() - 1) {
						map.put("flag", "false");
						map.put("errorCode", "-4");// -4代表家族不存在
						return gson.toJson(map);
					}
				}

			}
		}
		
		
		map.put("flag", "true");
		return gson.toJson(map);
		
	}

}
