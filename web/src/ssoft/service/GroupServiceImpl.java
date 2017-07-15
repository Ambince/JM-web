package ssoft.service;

import io.rong.models.GroupInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import ssoft.dao.DiaryDao;
import ssoft.dao.GroupChatDao;
import ssoft.dao.GroupDiaryDao;
import ssoft.dao.UserDao;
import ssoft.dao.User_GroupDao;
import ssoft.domain.GroupChat;
import ssoft.domain.GroupChatDiary;
import ssoft.domain.Island;
import ssoft.domain.NativeInfo;
import ssoft.domain.UserInfo;
import ssoft.factory.BasicFactory;
import ssoft.utils.Page;

public class GroupServiceImpl implements GroupService {
	private GroupChatDao groupChatDao = BasicFactory.getFactory().getDao(
			GroupChatDao.class);
	private User_GroupDao userGroupDao = BasicFactory.getFactory().getDao(
			User_GroupDao.class);
	private GroupDiaryDao groupDiaryDao = BasicFactory.getFactory().getDao(
			GroupDiaryDao.class);
	private DiaryDao diaryDao = BasicFactory.getFactory()
			.getDao(DiaryDao.class);
	private UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);
	Gson gson = new Gson();

	@Override
	public Map<String, Integer> getClassPageInfo(int page) {
		int pageInfo[] = Page.getPageInfo(page, groupChatDao.getClassNum()
				.size(), 10);
		int pagenum = pageInfo[2];
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("pagenum", pagenum);
		map.put("curpage", page);
		return map;
	}

	@Override
	public Map<String, Integer> getHomePageInfo(int page) {
		int pageInfo[] = Page.getPageInfo(page, groupChatDao.getHomeNum()
				.size(), 10);
		int pagenum = pageInfo[2];
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("pagenum", pagenum);
		map.put("curpage", page);
		return map;
	}

	@Override
	public Map<String, Integer> getClanPageInfo(int page) {
		int pageInfo[] = Page.getPageInfo(page, groupChatDao.getClanNum()
				.size(), 10);
		int pagenum = pageInfo[2];
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("pagenum", pagenum);
		map.put("curpage", page);
		return map;
	}

	@Override
	public int getClanNum() {
		return groupChatDao.getClanNum().size();
	}

	@Override
	public int getHomeNum() {
		return groupChatDao.getHomeNum().size();
	}

	@Override
	public int getClassNum() {
		return groupChatDao.getClassNum().size();
	}

	@Override
	public List<Map<String, Object>> getClansInfo(int page) {
		// 分页
		int[] pageInfo = Page.getPageInfo(page, groupChatDao.getClanNum()
				.size(), 10);
		// 分页查询
		List<GroupChat> clans = groupChatDao.getClanAndPaging(pageInfo[0],
				pageInfo[1]);
		List<Map<String, Object>> clansInfo = new ArrayList<Map<String, Object>>();
		for (GroupChat clan : clans) {
			Map<String, Object> clanInfo = new HashMap<String, Object>();
			clanInfo.put("id", clan.getId());
			clanInfo.put("name", clan.getName());
			clanInfo.put("createTime", clan.getCreatetime());
			clanInfo.put("userNum",
					userGroupDao.getGroupsByGroupId(clan.getId()).size());
			clanInfo.put("postNum",
					groupDiaryDao.getDiaryByGroupId(clan.getId() + "").size());
			int photoNum = 0;
			for (GroupChatDiary groupChatDiary : groupDiaryDao
					.getDiaryByGroupId(clan.getId() + "")) {
				if (groupChatDiary.getSharephoto() == 1) {
					photoNum += diaryDao.findDiaryById(
							groupChatDiary.getDiary_id() + "").getImage_num();
				}
			}
			clanInfo.put("photoNum", photoNum);
			clansInfo.add(clanInfo);
		}
		
		Collections.sort(clansInfo, new Comparator<Map<String, Object>>() {

			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				// TODO Auto-generated method stub
				Date time1 = (Date) o1.get("createTime");
				Date time2 = (Date) o2.get("createTime");
				int i = time1.compareTo(time2);

				if (i > 0) {
					return -1;
				} else if (i == 0) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		
		return clansInfo;
	}

	@Override
	public List<Map<String, Object>> getHomesInfo(int page) {
		// 分页
		int[] pageInfo = Page.getPageInfo(page, groupChatDao.getHomeNum()
				.size(), 10);
		// 分页查询
		List<GroupChat> homes = groupChatDao.getHomeAndPaging(pageInfo[0],
				pageInfo[1]);
		List<Map<String, Object>> homesInfo = new ArrayList<Map<String, Object>>();
		for (GroupChat home : homes) {
			Map<String, Object> homeInfo = new HashMap<String, Object>();
			homeInfo.put("id", home.getId());
			homeInfo.put("name", home.getName());
			homeInfo.put("createTime", home.getCreatetime());
			homeInfo.put("userNum",
					userGroupDao.getGroupsByGroupId(home.getId()).size());
			homeInfo.put("postNum",
					groupDiaryDao.getDiaryByGroupId(home.getId() + "").size());
			int photoNum = 0;
			for (GroupChatDiary groupChatDiary : groupDiaryDao
					.getDiaryByGroupId(home.getId() + "")) {
				if (groupChatDiary.getSharephoto() == 1) {
					photoNum += diaryDao.findDiaryById(
							groupChatDiary.getDiary_id() + "").getImage_num();
				}
			}
			homeInfo.put("photoNum", photoNum);
			homesInfo.add(homeInfo);
		}
		
		Collections.sort(homesInfo, new Comparator<Map<String, Object>>() {

			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				// TODO Auto-generated method stub
				Date time1 = (Date) o1.get("createTime");
				Date time2 = (Date) o2.get("createTime");
				int i = time1.compareTo(time2);

				if (i > 0) {
					return -1;
				} else if (i == 0) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		
		return homesInfo;
	}

	@Override
	public List<Map<String, Object>> getClassesInfo(int page) {
		// 分页
		int[] pageInfo = Page.getPageInfo(page, groupChatDao.getClassNum()
				.size(), 10);
		// 分页查询
		List<GroupChat> classes = groupChatDao.getClassAndPaging(pageInfo[0],
				pageInfo[1]);
		List<Map<String, Object>> classesInfo = new ArrayList<Map<String, Object>>();
		for (GroupChat cl : classes) {
			Map<String, Object> classInfo = new HashMap<String, Object>();
			classInfo.put("id", cl.getId());
			classInfo.put("name", cl.getName());
			classInfo.put("createTime", cl.getCreatetime());
			classInfo.put("userNum", userGroupDao
					.getGroupsByGroupId(cl.getId()).size());
			classInfo.put("postNum",
					groupDiaryDao.getDiaryByGroupId(cl.getId() + "").size());
			int photoNum = 0;
			for (GroupChatDiary groupChatDiary : groupDiaryDao
					.getDiaryByGroupId(cl.getId() + "")) {
				if (groupChatDiary.getSharephoto() == 1) {
					photoNum += diaryDao.findDiaryById(
							groupChatDiary.getDiary_id() + "").getImage_num();
				}
			}
			classInfo.put("photoNum", photoNum);
			classesInfo.add(classInfo);
		}
		
		Collections.sort(classesInfo, new Comparator<Map<String, Object>>() {

			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				// TODO Auto-generated method stub
				Date time1 = (Date) o1.get("createTime");
				Date time2 = (Date) o2.get("createTime");
				int i = time1.compareTo(time2);

				if (i > 0) {
					return -1;
				} else if (i == 0) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		
		return classesInfo;
	}

	@Override
	public String saveNativeInfo(String id, String password, String type,
			String info, String groupId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		// 1 验证用户名密码
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}

		GroupChat groupChat = groupChatDao.getGroupChatById(Integer
				.parseInt(groupId));
		NativeInfo nativeInfo = gson.fromJson(groupChat.getInfo(),
				NativeInfo.class);
		if (null == nativeInfo) {
			nativeInfo = new NativeInfo();
		}
		switch (type) {
		case "zuXun":
			nativeInfo.setZuXun(info);
			break;
		case "secretIntro":
			nativeInfo.setSecretIntro(info);
			break;
		case "publicIntro":
			nativeInfo.setPublicIntro(info);
			break;
		case "zhuanJi":
			nativeInfo.setZhuanJi(info);
			break;
		default:
			break;
		}
		int result = -1;
		result = groupChatDao.updateNativeInfoById(groupId,
				gson.toJson(nativeInfo));
		if (result <= 0) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// -2代表数据库失败
			return gson.toJson(map);
		}
		map.put("flag", "true");
		return gson.toJson(map);

	}

	@Override
	public String getNativeInfo(String id, String password, String groupId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		// 1 验证用户名密码
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}
		NativeInfo nativeInfo = new NativeInfo();
		GroupChat groupChat = groupChatDao.getGroupChatById(Integer
				.parseInt(groupId));
		nativeInfo = gson.fromJson(groupChat.getInfo(), NativeInfo.class);

		map.put("flag", "true");
		map.put("info", nativeInfo);

		return gson.toJson(map);
	}

	@Override
	public String getNativePublicInfo(String id, String password, String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		// 1 验证用户名密码
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}
		NativeInfo nativeInfo = new NativeInfo();

		GroupChat groupChat = groupChatDao.getNationByName(name);
		// GroupChat groupChat =
		// groupChatDao.getGroupChatById(Integer.parseInt(groupId));
		nativeInfo = gson.fromJson(groupChat.getInfo(), NativeInfo.class);

		map.put("flag", "true");
		map.put("publicInfo", nativeInfo.getPublicIntro());// publicIntro

		return gson.toJson(map);
	}

	@Override
	public String resetGroupName(String id, String password, String groupName,
			String groupId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		// 1 验证用户名密码
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}
		GroupChat groupChat = groupChatDao.getGroupChatById(Integer
				.parseInt(groupId));
		if (groupChat.getType() == 2) {
			GroupChat groupChat2 = groupChatDao.getNationByName(groupName);
			if (null != groupChat2) {
				map.put("flag", "false");
				map.put("errorCode", "-3");// -2已经有家族叫这个名字了
				return gson.toJson(map);
			}

		}
		int result = -1;
		result = groupChatDao.resetGroupNameById(groupId, groupName);
		if (result < 0) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// -2数据库出错
			return gson.toJson(map);
		}

		map.put("flag", "true");

		return gson.toJson(map);
	}

	@Override
	public String checkDiary2Group(String id, String password, String groupId,
			String diaryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		// 1 验证用户名密码
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}
		GroupChatDiary groupChatDiary =  groupDiaryDao.getItemBydiaryIdAndGroupId(groupId,diaryId);
		if (null == groupChatDiary) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// 
			return gson.toJson(map);
		}

		map.put("flag", "true");

		return gson.toJson(map);
	}

	@Override
	public String checkGroupExist(String id, String password, String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		// 1 验证用户名密码
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}
		 GroupChat groupChat = groupChatDao.getJiazuByName(name);
		if (null == groupChat) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// 不存在
			return gson.toJson(map);
		}

		map.put("flag", "true");
		map.put("groupId", String.valueOf(groupChat.getId()));

		return gson.toJson(map);
	}

}
