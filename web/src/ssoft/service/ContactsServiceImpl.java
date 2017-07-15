package ssoft.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssoft.dao.GroupChatDao;
import ssoft.dao.OfficialaccountsDao;
import ssoft.dao.UserDao;
import ssoft.dao.UserRelationDao;
import ssoft.dao.User_GroupDao;
import ssoft.dao.User_OfficialaccountDao;
import ssoft.domain.GroupChat;
import ssoft.domain.Officialaccounts;
import ssoft.domain.UserGroup;
import ssoft.domain.UserInfo;
import ssoft.domain.UserOfficialaccount;
import ssoft.domain.UserRelation;
import ssoft.factory.BasicFactory;

import com.google.gson.Gson;

public class ContactsServiceImpl implements ContactsService {

	private UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);
	private GroupChatDao groupChatDao = BasicFactory.getFactory().getDao(
			GroupChatDao.class);
	private OfficialaccountsDao officialaccountsDao = BasicFactory.getFactory()
			.getDao(OfficialaccountsDao.class);
	private User_GroupDao userGroupDao = BasicFactory.getFactory().getDao(
			User_GroupDao.class);
	private User_OfficialaccountDao userOfficialaccountDao = BasicFactory
			.getFactory().getDao(User_OfficialaccountDao.class);
	private UserRelationDao userRelationDao = BasicFactory.getFactory().getDao(
			UserRelationDao.class);
	Gson gson = new Gson();
	Map<String, List<Map<String, String>>> mapContact = new HashMap<String, List<Map<String, String>>>();

	@Override
	public String getContacts(String id, String password) {
		// TODO Auto-generated method stub

		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {

			List<Map<String, String>> flagList = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			map.put("flag", "false");
			flagList.add(map);
			mapContact.put("flag", flagList);
			return gson.toJson(mapContact);

		}

		// 1 现获取所在的群的群组
		List<Map<String, String>> groupChatList = new ArrayList<Map<String, String>>();
		List<UserGroup> groupIdList = userGroupDao.getGroupsByUserId(id);
		if (null != groupIdList) {
			for (UserGroup userGroup : groupIdList) {
				Map<String, String> map = new HashMap<String, String>();

				int group_id = userGroup.getGroupchat_id();
				GroupChat groupChat = groupChatDao.getGroupChatById(group_id);
				List<UserGroup> userGroups = userGroupDao
						.getGroupsByGroupId(group_id);
				map.put("groupChatName", groupChat.getName());
				map.put("groupChatId", String.valueOf(groupChat.getId()));
				map.put("groupType", String.valueOf(groupChat.getType()));
				map.put("groupChatMemberNum", String.valueOf(userGroups.size()));
				groupChatList.add(map);

			}
			
			//处理分类
			List<Map<String, String>> classTemp = new ArrayList<Map<String,String>>();
			List<Map<String, String>> familyTemp = new ArrayList<Map<String,String>>();
			List<Map<String, String>> nationTemp = new ArrayList<Map<String,String>>();
			for (int i = 0; i < groupChatList.size(); i++) {
				Map<String, String> mapTemp = groupChatList.get(i);
				String typeTemp = mapTemp.get("groupType");
				if (typeTemp.equals("0")) {
					classTemp.add(mapTemp);
				}else if (typeTemp.equals("1")) {
					familyTemp.add(mapTemp);
				}else if (typeTemp.equals("2")) {
					nationTemp.add(mapTemp);
				}
			}
			
			
			
			
			groupChatList = new ArrayList<Map<String,String>>();
			groupChatList.addAll(classTemp);
			groupChatList.addAll(familyTemp);
			groupChatList.addAll(nationTemp);
			
			List<Map<String, String>> flagList = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			map.put("flag", "true");
			flagList.add(map);
			mapContact.put("flag", flagList);
			mapContact.put("GroupChatList", groupChatList);
		}

		// 2获取自己的公众号
		List<Map<String, String>> officialaccountList = new ArrayList<Map<String, String>>();
		// List<Object> OfficialaccountList = new ArrayList<Object>();
		List<UserOfficialaccount> officialaccounts = userOfficialaccountDao
				.getOfficialaccountsByUserId(id);
		if (null != officialaccounts) {
			for (UserOfficialaccount userOfficialaccount : officialaccounts) {
				Map<String, String> map = new HashMap<String, String>();

				int officialaccount_id = userOfficialaccount
						.getOfficialaccount_id();
				Officialaccounts officialaccount = officialaccountsDao
						.getOfficialaccountById(officialaccount_id);
				List<UserOfficialaccount> userOfficialaccounts = userOfficialaccountDao
						.getOfficialaccountsByOfficialaccountId(officialaccount_id);
				map.put("officialaccountName", officialaccount.getName());
				map.put("officialaccountId",
						String.valueOf(officialaccount.getId()));
				map.put("officialaccountType",
						String.valueOf(officialaccount.getType()));
				map.put("officialaccountMemberNum",
						String.valueOf(userOfficialaccounts.size()));
				officialaccountList.add(map);
			}
			
			//处理分类
			List<Map<String, String>> schoolTemp = new ArrayList<Map<String,String>>();
			List<Map<String, String>> plotTemp = new ArrayList<Map<String,String>>();
			List<Map<String, String>> villageTemp = new ArrayList<Map<String,String>>();
			for (int i = 0; i < officialaccountList.size(); i++) {
				Map<String, String> mapTemp = officialaccountList.get(i);
				String typeTemp = mapTemp.get("officialaccountType");
				if (typeTemp.equals("0")) {
					villageTemp.add(mapTemp);
				}else if (typeTemp.equals("1")) {
					plotTemp.add(mapTemp);
				}else if (typeTemp.equals("2")) {
					schoolTemp.add(mapTemp);
				}
			}
			
			officialaccountList = new ArrayList<Map<String,String>>();
			officialaccountList.addAll(villageTemp);
			officialaccountList.addAll(plotTemp);
			officialaccountList.addAll(schoolTemp);
			
			
			
			List<Map<String, String>> flagList = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			map.put("flag", "true");
			flagList.add(map);
			mapContact.put("flag", flagList);

			mapContact.put("OfficialaccountList", officialaccountList);
		}

		// 3获取好友
		List<Map<String, String>> friendsList = new ArrayList<Map<String, String>>();
		// List<Object> friendsList = new ArrayList<Object>();
		List<UserRelation> userrelations = new ArrayList<UserRelation>();
		userrelations = userRelationDao.getAllFriends(id);
		if (null != userrelations) {
			for (UserRelation userRelation : userrelations) {
				Map<String, String> map = new HashMap<String, String>();

				if (id.equals(String.valueOf(userRelation.getUser_id1()))
						&& userRelation.getAgree1to2() == 1
						&& userRelation.getAgree2to1() == 1) {

					UserInfo friendsInfo = userDao.getUserInfoById(userRelation
							.getUser_id2());
					map.put("FriendName", friendsInfo.getName());
					map.put("literaryName", friendsInfo.getLiteraryName());
					map.put("call", String.valueOf(userRelation.getCall1to2()));
					map.put("remark",
							String.valueOf(userRelation.getRemark1to2()));
					map.put("groupid",
							String.valueOf(userRelation.getGroupid1to2()));

					map.put("friendId", String.valueOf(friendsInfo.getId()));
					friendsList.add(map);
				} else if (id
						.equals(String.valueOf(userRelation.getUser_id2()))
						&& userRelation.getAgree2to1() == 1
						&& userRelation.getAgree1to2() == 1) {
					UserInfo friendsInfo = userDao.getUserInfoById(userRelation
							.getUser_id1());
					map.put("FriendName", friendsInfo.getName());
					map.put("literaryName", friendsInfo.getLiteraryName());
					map.put("call", String.valueOf(userRelation.getCall2to1()));
					map.put("remark",
							String.valueOf(userRelation.getRemark2to1()));
					map.put("groupid",
							String.valueOf(userRelation.getGroupid2to1()));
					map.put("friendId", String.valueOf(friendsInfo.getId()));
					friendsList.add(map);
				}
			}
			List<Map<String, String>> flagList = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			map.put("flag", "true");
			flagList.add(map);
			mapContact.put("flag", flagList);
			mapContact.put("FriendsList", friendsList);
		}

		// 4获取黑名单

		return gson.toJson(mapContact);
	}

}
