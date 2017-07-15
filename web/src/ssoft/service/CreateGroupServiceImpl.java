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
import ssoft.dao.UserDao;
import ssoft.dao.User_GroupDao;
import ssoft.domain.GroupChat;
import ssoft.domain.UserInfo;
import ssoft.factory.BasicFactory;
import ssoft.global.Global;

public class CreateGroupServiceImpl implements CreateGroupService {
	private UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);
	private GroupChatDao groupDao = BasicFactory.getFactory().getDao(GroupChatDao.class);
	private User_GroupDao userGroupDao = BasicFactory.getFactory().getDao(User_GroupDao.class);
	Gson gson = new Gson();
	@Override
	public String createGroup(String id, String password, String groupName,
			String type) {
		//1 判断用户是否存在
		UserInfo userInfo = userDao.checkUserById(id, password);
		Map<String, String> map = new HashMap<String, String>();
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "0");//用户不存在
			return gson.toJson(map);
		}
		
		
		//2 创建一个群组  
		if (type.equals("2")) {//家族名字不能重复
			GroupChat groupChat = groupDao.getJiazuByName(groupName);
			if (null != groupChat) {
				map.put("flag", "false");
				map.put("errorCode", "-3");//数据库操作失败
				return gson.toJson(map);
			}
		}
		//int result0 = -1;
		GroupChat groupChat = groupDao.createGroup(groupName,type);
		//GroupChat groupChat = groupDao.getGroupChatById(result0);
		if (null == groupChat) {
			map.put("flag", "false");
			map.put("errorCode", "-1");//数据库操作失败
			return gson.toJson(map);
		}
		
		
		//3添加一天群组与用户的关系记录
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int result = userGroupDao.addUser(String.valueOf(groupChat.getId()),id,"1","1",sdf.format(new Date(System.currentTimeMillis())));
		if (result < 0 ) {
			map.put("flag", "false");
			map.put("errorCode", "-1");//数据库操作失败
			return gson.toJson(map);
		}
		
		
		//4给融云发送创建请求
		List<String> list = new ArrayList<String>();
		list.add(id);
		try {
			SdkHttpResult createRet = ApiHttpClient.createGroup(Global.Rong_App_Key, Global.Rong_App_Secret, list, String.valueOf(groupChat.getId()), groupChat.getName(),FormatType.json);
			Map<String, Double> mapCode = new HashMap<String, Double>();

			mapCode = gson.fromJson(createRet.getResult(), Map.class);

			if (mapCode.get("code") != 200) {
				map.put("flag", "false");
				map.put("errorCode", "-2");//-2代表在融云申请失败
				return gson.toJson(map);
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		map.put("flag", "true");
		map.put("groupId", String.valueOf(groupChat.getId()));
		return gson.toJson(map);
	}

}
