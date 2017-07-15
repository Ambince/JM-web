package ssoft.service;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import ssoft.dao.GroupChatDao;
import ssoft.dao.GroupDiaryDao;
import ssoft.dao.UserBaseInfoDao;
import ssoft.dao.UserDao;
import ssoft.dao.UserRelationDao;
import ssoft.dao.User_GroupDao;
import ssoft.domain.GroupChat;
import ssoft.domain.UserBaseInfo;
import ssoft.domain.UserGroup;
import ssoft.domain.UserInfo;
import ssoft.domain.UserRelation;
import ssoft.factory.BasicFactory;
import ssoft.global.Global;

public class GroupMemberServiceImpl implements GroupMemberService {
	private UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);
	private UserRelationDao userRelationDao = BasicFactory.getFactory().getDao(UserRelationDao.class);
	private GroupChatDao groupChatDao = BasicFactory.getFactory().getDao(
			GroupChatDao.class);
	private GroupDiaryDao groupDiaryDao = BasicFactory.getFactory().getDao(
			GroupDiaryDao.class);
	private UserBaseInfoDao userBaseInfoDao = BasicFactory.getFactory().getDao(
			UserBaseInfoDao.class);
	private User_GroupDao userGroupDao = BasicFactory.getFactory().getDao(
			User_GroupDao.class);
	Gson gson = new Gson();

	@Override
	public String removeMember(String id, String password, String groupId,
			String removeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		// 1 验证用户名密码
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}

		// 2验证当前用户是否在群
		UserGroup userGroup = userGroupDao.checkExistById(id, groupId);
		if (null == userGroup) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// -1代表当前用户不存在该群
			return gson.toJson(map);
		}
		// 3验证上次删除群成员的时间
		Date deletemembertime = userGroup.getDeletemembertime();
		Date currentTime = new Date(System.currentTimeMillis());
		if ((currentTime.getTime() - deletemembertime.getTime()) / 3600 <= 24) {
			map.put("flag", "false");
			map.put("errorCode", "-4");// -1代表当前用户不能再删除好友了当天
			return gson.toJson(map);
		}
		// 4验证要删除的群是否在群
		userGroup = null;
		userGroup = userGroupDao.checkExistById(removeId, groupId);
		if (null == userGroup) {
			map.put("flag", "false");
			map.put("errorCode", "-3");// -1代表删除的成员不存在该群
			return gson.toJson(map);
		}

		// 5删除群成员
		int result = -1;
		result = userGroupDao.deleteItemByUserIdAndGroupId(removeId, groupId);
		if (result < 0) {
			map.put("flag", "false");
			map.put("errorCode", "-5");// -1代表数据库错误
			return gson.toJson(map);
		}
		//5.5 判断群里是否还有用户
		int num = userGroupDao.getGroupsByGroupId(Integer.parseInt(groupId)).size();
		if(num == 0){
			//删除所有群聊的信息
			groupDiaryDao.deleteItemByGroupId(groupId);	
			groupChatDao.deleteGroup(groupId);				
		}
		
		// 6在融云的群里删除该成员
		try {
			SdkHttpResult removeRet = ApiHttpClient.quitGroup(
					Global.Rong_App_Key, Global.Rong_App_Secret, removeId,
					groupId, FormatType.json);
			Map<String, Object> mapCode = new HashMap<String, Object>();

			mapCode = gson.fromJson(removeRet.getResult(), Map.class);

			if ((Double)mapCode.get("code") != 200) {
				map.put("flag", "false");
				map.put("errorCode", "-6");// -6融云删除失败
				return gson.toJson(map);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 7保存这次删除人的时间
		result = -1;
		result = userGroupDao.saveCurrentdeletetime(id, groupId, currentTime);
		if (result < 0) {
			map.put("flag", "false");
			map.put("errorCode", "-5");// -1代表数据库错误
			return gson.toJson(map);
		}

		map.put("flag", "true");
		return gson.toJson(map);
	}

	@Override
	public String addMembers(String id, String password, String groupId,
			List<String> memberIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 1验证用户
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}
		// 2检查当前用户是否在群
		UserGroup userGroup = userGroupDao.checkExistById(id, groupId);
		if (null == userGroup) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// -2代表当前用户不存在该群
			return gson.toJson(map);
		}
		// 3检查memberIds是否已经存在了
		for (String memberidTemp : memberIds) {
			UserGroup userGroupTemp = userGroupDao.checkExistById(memberidTemp,
					groupId);
			if (null == userGroupTemp) {
				// 4添加到群成员
				Date currentTime = new Date(System.currentTimeMillis());
				Date deletetime = new Date(currentTime.getTime() - 24 * 3600);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (int i = 0;i<memberIds.size();i++) {
					String memberid = memberIds.get(i);
					int addRet = userGroupDao.addUser(groupId, memberid, "0", "1",
							sdf.format(deletetime));
					if (addRet < 0) {
						map.put("flag", "false");
						map.put("errorCode", "-4");// -3代表所有的用户都已经存在了
						return gson.toJson(map);
					}

				}
				// 5在融云添加成员
				try {
					GroupChat groupChat = groupChatDao.getGroupChatById(Integer
							.parseInt(groupId));
					SdkHttpResult removeRet = ApiHttpClient.joinGroupBatch(
							Global.Rong_App_Key, Global.Rong_App_Secret, memberIds,
							groupId, groupChat.getName(), FormatType.json);
					Map<String, Object> mapCode = new HashMap<String, Object>();

					mapCode = gson.fromJson(removeRet.getResult(), Map.class);

					if ((Double)mapCode.get("code") != 200) {
						map.put("flag", "false");
						map.put("errorCode", "-5");// -6融云删除失败
						return gson.toJson(map);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				//入群后自动与群中成员加好友（无需验证）
				List<UserGroup> members = userGroupDao.getGroupsByGroupId(Integer.parseInt(groupId));
				for(UserGroup m : members){
					//是本人跳过
					if(m.getId() == Integer.parseInt(memberidTemp))
						continue;
					//已经是好友跳过
					UserRelation ur = userRelationDao.checkExist(memberidTemp, m.getId() + "");
					if(ur != null)
						continue;
					//添加好友
					userRelationDao.addFrind(memberidTemp, m.getId() + "");
				}
			}else{
				map.put("flag", "false");
				map.put("errorCode", "-3");// -6融云删除失败
				return gson.toJson(map);
			}
		}
		

		map.put("flag", "true");
		return gson.toJson(map);
	}

	@Override
	public String getMembers(String id, String password, String groupId) {
		Map<String, List<Map<String, String>>> mapResult = new HashMap<String, List<Map<String, String>>>();
		// TODO Auto-generated method stub
		// 1 验证用户名密码
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			List<Map<String, String>> flagList = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			map.put("flag", "false");
			flagList.add(map);
			mapResult.put("flag", flagList);
			gson.toJson(flagList);

		}

		// 2 获取当前群里边成员的id
		List<UserGroup> userGroups = new ArrayList<UserGroup>();
		userGroups = userGroupDao.getGroupsByGroupId(Integer.parseInt(groupId));
		if (userGroups.size() < 0) {
			List<Map<String, String>> flagList = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			map.put("flag", "false");
			flagList.add(map);
			mapResult.put("flag", flagList);
			return gson.toJson(mapResult);
		}
		List<Map<String, String>> memberInfoList = new ArrayList<Map<String, String>>();
		for (UserGroup userGroup : userGroups) {
			UserInfo userInfo2 = userDao.findUserById(String.valueOf(userGroup
					.getUser_id()));
			
				UserBaseInfo userBaseInfo = userBaseInfoDao
						.findUserBaseInfoById(String.valueOf(userInfo2.getId()));
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", String.valueOf(userInfo2.getId()));
				map.put("name", userInfo2.getName());
				map.put("literaryName", userInfo2.getLiteraryName());
				if(null != userBaseInfo){
					map.put("user_image", userBaseInfo.getUser_image());
				}
				
				memberInfoList.add(map);
		
			
		}

		List<Map<String, String>> flagList = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("flag", "true");
		flagList.add(map);
		mapResult.put("flag", flagList);
		mapResult.put("memberInfoList", memberInfoList);
		return gson.toJson(mapResult);
	}

	@Override
	public String getMembersNum(String id, String password, String groupId) {
		Map<String, String> map = new HashMap<String, String>();
		// 1 验证用户名密码
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {			
			map.put("flag", "false");
			map.put("num", "0");
			return gson.toJson(map);

		}
		// 2 获取当前群里边成员的数量
		int num = userGroupDao.getGroupsByGroupId(Integer.parseInt(groupId)).size();
		map.put("flag", "true");
		map.put("num", num + "");
		return gson.toJson(map);
	}

	@Override
	public String checkMembers(String id, String password, String groupId,
			List<String> memberIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 1验证用户
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}
		List<String> list = new ArrayList<String>();
		for (String tempId : memberIds) {
			UserGroup userGroup = userGroupDao.checkExistById(tempId, groupId);
			if (null != userGroup) {
				list.add("1");
			}else{
				list.add("0");
			}
			
		}
		map.put("flag", "true");
		map.put("exitList", gson.toJson(list));
		return gson.toJson(map);
		
	}

}
