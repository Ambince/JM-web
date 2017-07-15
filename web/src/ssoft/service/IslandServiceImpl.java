package ssoft.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssoft.dao.DiaryDao;
import ssoft.dao.IslandDao;
import ssoft.dao.IslandDiaryDao;
import ssoft.dao.RemarkDiaryDao;
import ssoft.dao.UserDao;
import ssoft.dao.UserDiaryDao;
import ssoft.dao.UserIslandDao;
import ssoft.domain.Diary;
import ssoft.domain.Island;
import ssoft.domain.IslandDiary;
import ssoft.domain.RemarkDiary;
import ssoft.domain.UserDiary;
import ssoft.domain.UserInfo;
import ssoft.domain.UserIsland;
import ssoft.factory.BasicFactory;
import ssoft.utils.Page;

import com.google.gson.Gson;

public class IslandServiceImpl implements IslandService {
	private UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);
	private IslandDao islandDao = BasicFactory.getFactory().getDao(
			IslandDao.class);
	private UserIslandDao userIslandDao = BasicFactory.getFactory().getDao(
			UserIslandDao.class);
	private IslandDiaryDao islandDiaryDao = BasicFactory.getFactory().getDao(
			IslandDiaryDao.class);
	private DiaryDao diaryDao = BasicFactory.getFactory()
			.getDao(DiaryDao.class);
	private UserDiaryDao userDiaryDao = BasicFactory.getFactory().getDao(
			UserDiaryDao.class);
	private RemarkDiaryDao remarkDiaryDao = BasicFactory.getFactory().getDao(
			RemarkDiaryDao.class);
	Gson gson = new Gson();

	@Override
	public String createIsland(String id, String password, String islandName) {
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		// 1 验证用户名密码
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}

		// 2检查是否已经存在
		Island island0 = islandDao.getIslandByName(islandName);
		if (null != island0) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// -1代表数据库操作失败
			return gson.toJson(map);
		}
		// 2 创建一个萌岛
		Island island = islandDao.createIslandByName(islandName);
		if (null == island) {
			map.put("flag", "false");
			map.put("errorCode", "0");// -1代表数据库操作失败
			return gson.toJson(map);
		}

		// 3添加一条用户与萌岛的记录
		UserIsland userIsland = userIslandDao.createItemById(id,
				String.valueOf(island.getId()));
		if (null == userIsland) {
			map.put("flag", "false");
			map.put("errorCode", "0");// -1代表数据库操作失败
			return gson.toJson(map);
		}

		map.put("flag", "true");
		map.put("islandId", String.valueOf(island.getId()));
		return gson.toJson(map);
	}

	@Override
	public String getHotIsland(String id, String password, String start,
			String offset) {
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		// 1 验证用户名密码
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}
		List<Map<String, String>> isalndInfoList = new ArrayList<Map<String, String>>();
		List<Island> islands = islandDao.getHotIsland(start, offset);
		List<Map<String, String>> belongs = new ArrayList<Map<String, String>>();
		for (Island island : islands) {
			UserIsland userIsland = userIslandDao.checkExistByUserAndIslandId(
					id, String.valueOf(island.getId()));
			if (null != userIsland) {
				Map<String, String> mapTemp = new HashMap<String, String>();
				mapTemp.put("belong", "1");
				belongs.add(mapTemp);
			} else {
				Map<String, String> mapTemp = new HashMap<String, String>();
				mapTemp.put("belong", "0");
				belongs.add(mapTemp);
			}
			List<UserIsland> islandUsers = userIslandDao
					.getUserByIslandId(String.valueOf(island.getId()));
			List<IslandDiary> islandDiaryList = islandDiaryDao
					.getIslandDiarysByIslandId(String.valueOf(island.getId()));
			Map<String, String> mapUserNum = new HashMap<String, String>();
			mapUserNum.put("userNum", String.valueOf(islandUsers.size()));
			mapUserNum.put("diaryNum", String.valueOf(islandDiaryList.size()));
			isalndInfoList.add(mapUserNum);
		}
		map.put("flag", "true");
		map.put("hotIsland", gson.toJson(islands));
		map.put("belongs", belongs);
		map.put("islandInfoList", isalndInfoList);
		return gson.toJson(map);
	}

	@Override
	public String searchIsland(String id, String password, String islandName) {
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		// 1 验证用户名密码
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}
		List<Island> islands = new ArrayList<Island>();
		List<Map<String, String>> belongs = new ArrayList<Map<String, String>>();
		List<Map<String, String>> isalndInfoList = new ArrayList<Map<String, String>>();
		// Island island = islandDao.getIslandByName(islandName);
		// if (null == island) {
		// Island island2 = islandDao.createIslandByName(islandName);
		// if (null == island2) {
		// map.put("flag", "false");
		// map.put("errorCode", "-2");// -2数据库出错
		// return gson.toJson(map);
		// }
		// // 3添加一条用户与萌岛的记录
		// UserIsland userIsland = userIslandDao.createItemById(id,
		// String.valueOf(island2.getId()));
		// if (null == userIsland) {
		// map.put("flag", "false");
		// map.put("errorCode", "0");// -1代表数据库操作失败
		// return gson.toJson(map);
		// }
		// island = island2;
		// islands.add(island);
		// Map<String,String> mapTemp = new HashMap<String, String>();
		// mapTemp.put("belong", "1");
		// belongs.add(mapTemp);
		// List<UserIsland> islandUsers =
		// userIslandDao.getUserByIslandId(String.valueOf(island.getId()));
		// List<IslandDiary> islandDiaryList =
		// islandDiaryDao.getIslandDiarysByIslandId(String.valueOf(island.getId()));
		// Map<String, String> mapUserNum = new HashMap<String, String>();
		// mapUserNum.put("userNum",String.valueOf(islandUsers.size()));
		// mapUserNum.put("diaryNum",String.valueOf(islandDiaryList.size()));
		// isalndInfoList.add(mapUserNum);
		// }

		List<Island> islands2 = islandDao.searchIslandByLikeName(islandName);

		if (islands2.size() > 0) {
			for (Island island2 : islands2) {
				// if (!island2.getName().equals(island.getName())) {
				// arrayList在迭代的时候不能删除
				// islands2.remove(island2);
				UserIsland userIsland = userIslandDao
						.checkExistByUserAndIslandId(id,
								String.valueOf(island2.getId()));
				if (null != userIsland) {
					Map<String, String> mapTemp = new HashMap<String, String>();
					mapTemp.put("belong", "1");
					belongs.add(mapTemp);
				} else {
					Map<String, String> mapTemp = new HashMap<String, String>();
					mapTemp.put("belong", "0");
					belongs.add(mapTemp);
				}
				islands.add(island2);
				List<UserIsland> islandUsers = userIslandDao
						.getUserByIslandId(String.valueOf(island2.getId()));
				List<IslandDiary> islandDiaryList = islandDiaryDao
						.getIslandDiarysByIslandId(String.valueOf(island2
								.getId()));
				Map<String, String> mapUserNum = new HashMap<String, String>();
				mapUserNum.put("userNum", String.valueOf(islandUsers.size()));
				mapUserNum.put("diaryNum",
						String.valueOf(islandDiaryList.size()));
				isalndInfoList.add(mapUserNum);
				// }
			}
		}

		// islands.addAll(islands2);

		map.put("flag", "true");
		map.put("searchIsland", gson.toJson(islands));
		map.put("belongs", belongs);
		map.put("islandInfoList", isalndInfoList);

		return gson.toJson(map);

	}

	@Override
	public String getUserIsland(String id, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		// 1 验证用户名密码
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}
		List<UserIsland> userIslandList = userIslandDao.getIslandsByUserId(id);
		if (userIslandList.size() <= 0) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// -2表示没有萌岛
			return gson.toJson(map);
		}

		List<Island> islands = new ArrayList<Island>();
		for (int i = 0; i < userIslandList.size(); i++) {
			Island island = islandDao.getIslandById(String
					.valueOf(userIslandList.get(i).getIsland_id()));
			if (null != island) {
				islands.add(island);
			}
		}

		if (islands.size() <= 0) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// -2表示没有萌岛
			return gson.toJson(map);
		}

		map.put("flag", "true");
		map.put("userIslands", gson.toJson(islands));
		return gson.toJson(map);
	}

	@Override
	public int getAllIsland() {
		return islandDao.getAllIsland().size();
	}

	@Override
	public Map<String, Integer> getPageInfo(int page) {
		int pageInfo[] = Page.getPageInfo(page,
				islandDao.getAllIsland().size(), 10);
		int pagenum = pageInfo[2];
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("pagenum", pagenum);
		map.put("curpage", page);
		return map;
	}

	@Override
	public List<Map<String, Object>> getIslandsInfo(int page) {
		// 分页
		int[] pageInfo = Page.getPageInfo(page,
				islandDao.getAllIsland().size(), 10);
		// 分页查询
		List<Island> islands = islandDao.getIslandAndPaging(pageInfo[0],
				pageInfo[1]);
		List<Map<String, Object>> islandsInfo = new ArrayList<Map<String, Object>>();
		for (Island island : islands) {
			Map<String, Object> islandInfo = new HashMap<String, Object>();
			islandInfo.put("id", island.getId());
			islandInfo.put("name", island.getName());
			islandInfo.put("createTime", island.getCreatetime());
			islandInfo.put("diaryNum", islandDiaryDao
					.getIslandDiarysByIslandId(island.getId() + "").size());
			islandInfo
					.put("userNum",
							userIslandDao
									.getUserByIslandId(island.getId() + "")
									.size());
			islandInfo.put("isRecommend", island.getIsrecommend());
			islandsInfo.add(islandInfo);
		}
		
		Collections.sort(islandsInfo, new Comparator<Map<String, Object>>() {

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
		
		
		return islandsInfo;
	}

	@Override
	public String getIslandsInfo(String id, String password, int page) {
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		// 1 验证用户名密码
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}

		int[] pageInfo = Page.getPageInfo(page,
				islandDao.getAllIsland().size(), 10);
		// 分页查询
		List<Island> islands = islandDao.getIslandAndPaging(pageInfo[0],
				pageInfo[1]);

		List<Map<String, String>> belongs = new ArrayList<Map<String, String>>();
		List<Map<String, String>> isalndInfoList = new ArrayList<Map<String, String>>();
		for (Island island : islands) {
			UserIsland userIsland = userIslandDao.checkExistByUserAndIslandId(
					id, String.valueOf(island.getId()));
			if (null != userIsland) {
				Map<String, String> mapTemp = new HashMap<String, String>();
				mapTemp.put("belong", "1");
				belongs.add(mapTemp);
			} else {
				Map<String, String> mapTemp = new HashMap<String, String>();
				mapTemp.put("belong", "0");
				belongs.add(mapTemp);
			}
			List<UserIsland> islandUsers = userIslandDao
					.getUserByIslandId(String.valueOf(island.getId()));
			List<IslandDiary> islandDiaryList = islandDiaryDao
					.getIslandDiarysByIslandId(String.valueOf(island.getId()));
			Map<String, String> mapUserNum = new HashMap<String, String>();
			mapUserNum.put("userNum", String.valueOf(islandUsers.size()));
			mapUserNum.put("diaryNum", String.valueOf(islandDiaryList.size()));
			isalndInfoList.add(mapUserNum);
		}

		map.put("belongs", belongs);
		map.put("islandInfoList", isalndInfoList);
		map.put("flag", "true");
		map.put("islands", gson.toJson(islands));
		return gson.toJson(map);
	}

	@Override
	public String recommendIsland(String id) {
		if (islandDao.recommendIsland(id) != 0) {
			return "推荐成功";
		} else {
			return "推荐失败";
		}
	}

	@Override
	public String cancleRecommendIsland(String id) {
		if (islandDao.cancelRecommendIsland(id) != 0) {
			return "取消推荐成功";
		} else {
			return "取消推荐失败";
		}
	}

	@Override
	public Map<String, Integer> getRecommendPageInfo(int page) {
		int pageInfo[] = Page.getPageInfo(page, islandDao.getRecommendIsland()
				.size(), 10);
		int pagenum = pageInfo[2];
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("pagenum", pagenum);
		map.put("curpage", page);
		return map;
	}

	@Override
	public List<Map<String, Object>> getRecommendIslandsInfo(int page) {
		// 分页
		int[] pageInfo = Page.getPageInfo(page, islandDao.getRecommendIsland()
				.size(), 10);
		// 分页查询
		List<Island> islands = islandDao.getRecommendIslandAndPaging(
				pageInfo[0], pageInfo[1]);
		List<Map<String, Object>> islandsInfo = new ArrayList<Map<String, Object>>();
		for (Island island : islands) {
			Map<String, Object> islandInfo = new HashMap<String, Object>();
			islandInfo.put("id", island.getId());
			islandInfo.put("name", island.getName());
			islandInfo.put("createTime", island.getCreatetime());
			islandInfo.put("diaryNum", islandDiaryDao
					.getIslandDiarysByIslandId(island.getId() + "").size());
			islandInfo
					.put("userNum",
							userIslandDao
									.getUserByIslandId(island.getId() + "")
									.size());
			islandInfo.put("isRecommend", island.getIsrecommend());
			islandsInfo.add(islandInfo);
		}
		
		Collections.sort(islandsInfo, new Comparator<Map<String, Object>>() {

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
		
		return islandsInfo;
	}

	@Override
	public String getMengdaoPageInfo(String id, String password, String mark) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		// 1先检查用户
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}
		// 2找到所有的热点萌岛
		// List<Island> recommendIslands = new ArrayList<Island>();
		List<Island> recommendIslands = new ArrayList<Island>();
		List<Map<String, String>> recommendIslandRet = new ArrayList<Map<String, String>>();

		// 处理返回的信息
		recommendIslands = islandDao.getRecommendIsland();
		if (null != recommendIslands && recommendIslands.size() > 0) {
			for (Island islandTemp : recommendIslands) {
				Map<String, String> mapTemp = new HashMap<String, String>();
				UserIsland userIslandExist = userIslandDao
						.checkExistByUserAndIslandId(id,
								String.valueOf(islandTemp.getId()));
				if (null != userIslandExist) {
					mapTemp.put("follow", "1");
				} else {
					mapTemp.put("follow", "0");
				}
				mapTemp.put("id", String.valueOf(islandTemp.getId()));
				mapTemp.put("name", String.valueOf(islandTemp.getName()));
				List<UserIsland> islandUsers = userIslandDao
						.getUserByIslandId(String.valueOf(islandTemp.getId()));
				if (null != islandUsers) {
					mapTemp.put("memberNum", String.valueOf(islandUsers.size()));
				} else {
					mapTemp.put("memberNum", "0");
				}
				List<IslandDiary> islandDairys = islandDiaryDao
						.getIslandDiarysByIslandId(String.valueOf(islandTemp
								.getId()));
				if (null != islandDairys) {
					mapTemp.put("diaryNum", String.valueOf(islandDairys.size()));
				} else {
					mapTemp.put("diaryNum", "0");
				}

				recommendIslandRet.add(mapTemp);
			}
		}

		// 3找到我的萌岛
		List<UserIsland> userDiaryList = userIslandDao.getIslandsByUserId(id);

		List<Map<String, String>> myIslandsRet = new ArrayList<Map<String, String>>();
		if (null != userDiaryList && userDiaryList.size() > 0) {

			for (int i = 0; i < userDiaryList.size(); i++) {

				Island island = islandDao.getIslandById(String
						.valueOf(userDiaryList.get(i).getIsland_id()));
				if (null != island) {

					Map<String, String> mapTemp = new HashMap<String, String>();
					mapTemp.put("id", String.valueOf(island.getId()));
					mapTemp.put("name", String.valueOf(island.getName()));
					List<UserIsland> islandUsers = userIslandDao
							.getUserByIslandId(String.valueOf(island.getId()));
					if (null != islandUsers) {
						mapTemp.put("memberNum",
								String.valueOf(islandUsers.size()));
					} else {
						mapTemp.put("memberNum", "0");
					}
					List<IslandDiary> islandDairys = islandDiaryDao
							.getIslandDiarysByIslandId(String.valueOf(island
									.getId()));
					if (null != islandDairys) {
						mapTemp.put("diaryNum",
								String.valueOf(islandDairys.size()));
					} else {
						mapTemp.put("diaryNum", "0");
					}

					myIslandsRet.add(mapTemp);

				}
			}
		}

		// 4找到最萌贝壳
		// List<IslandDiary> hotIslandDiaryList = islandDiaryDao
		// .getHotIslandDiary(0, 10);
		List<IslandDiary> hotIslandDiaryList = getHotIslandDiary(mark);
		List<Diary> hotIslandDiaryRet = new ArrayList<Diary>();
		List<Map<String, String>> hotDiaryIslandInfo = new ArrayList<Map<String, String>>();

		if (null != hotIslandDiaryList && hotIslandDiaryList.size() > 0) {
			for (IslandDiary islandDiary : hotIslandDiaryList) {
				Diary diaryTemp = diaryDao.findDiaryById(String
						.valueOf(islandDiary.getDiary_id()));
				UserDiary userDiary = userDiaryDao.getDiaryBelongs(String
						.valueOf(diaryTemp.getId()));
				if (null != userDiary) {
					UserInfo userInfo2 = userDao.findUserById(String
							.valueOf(userDiary.getUser_id()));

					if (null != diaryTemp && null != userInfo2) {
						Map<String, String> mapTemp = new HashMap<String, String>();
						mapTemp.put("islandId",
								String.valueOf(islandDiary.getIsland_id()));
						Island islandTemp = islandDao.getIslandById(String
								.valueOf(islandDiary.getIsland_id()));
						if (null != islandTemp) {
							mapTemp.put("islandName", islandTemp.getName());
						}
						mapTemp.put("userId", String.valueOf(userInfo2.getId()));
						mapTemp.put("userName",
								String.valueOf(userInfo2.getName()));
						mapTemp.put("gender",
								String.valueOf(userInfo2.getGender()));
						hotDiaryIslandInfo.add(mapTemp);

						hotIslandDiaryRet.add(diaryTemp);

					}
				}

			}

		}

		map.put("flag", "true");
		map.put("recommendIslandRet", recommendIslandRet);
		map.put("myIslandsRet", myIslandsRet);
		map.put("hotIslandDiaryRet", hotIslandDiaryRet);
		map.put("hotDiaryIslandInfo", hotDiaryIslandInfo);
		return gson.toJson(map);
	}

	private List<IslandDiary> getHotIslandDiary(String mark) {
		List<IslandDiary> hotIslandDiaryListTemp = new ArrayList<IslandDiary>();
		List<IslandDiary> todayIslandDiaryListTemp = new ArrayList<IslandDiary>();
		// 1找到所有的24小时之内的随记
		List<Diary> todayDiaryTemp = diaryDao.getTodayDiary();
		if (todayDiaryTemp == null) {
			return hotIslandDiaryListTemp;
		}

		// 2属于萌岛的
		for (int i = 0; i < todayDiaryTemp.size(); i++) {
			Diary diary = todayDiaryTemp.get(i);
			List<IslandDiary> islandDiaries = islandDiaryDao
					.findDiaryByDiaryId(String.valueOf(diary.getId()));
			if (islandDiaries != null && islandDiaries.size() > 0) {
				todayIslandDiaryListTemp.addAll(islandDiaries);
			}

		}
		List<Integer> remarNum = new ArrayList<Integer>();
		// 3 获取虽有这些随记的评论
		if (null != todayIslandDiaryListTemp
				&& todayIslandDiaryListTemp.size() > 0) {
			for (int i = 0; i < todayIslandDiaryListTemp.size(); i++) {
				IslandDiary islandDiary = todayIslandDiaryListTemp.get(i);
				List<RemarkDiary> remarkList = remarkDiaryDao
						.getItemsByDiaryId(String.valueOf(islandDiary
								.getDiary_id()), mark);
				if (remarkList != null) {
					remarNum.add(remarNum.size());
				} else {
					remarNum.add(0);
				}

			}
		}

		// 排序
		for (int i = 0; i < todayIslandDiaryListTemp.size() - 1; i++) {
			for (int j = i; j < todayIslandDiaryListTemp.size() - 1; j++) {
				IslandDiary islandDiary = todayIslandDiaryListTemp.get(j);
				int num = remarNum.get(j);
				if (num < remarNum.get(j + 1)) {
					todayIslandDiaryListTemp.set(j,
							todayIslandDiaryListTemp.get(j + 1));
					remarNum.set(j, remarNum.get(j + 1));
					todayIslandDiaryListTemp.set(j + 1, islandDiary);
					remarNum.set(j + 1, num);
				}
			}

		}

		// 4得出前十个
		for (int i = 0; i < todayIslandDiaryListTemp.size(); i++) {
			if (i < 10) {
				hotIslandDiaryListTemp.add(todayIslandDiaryListTemp.get(i));
			} else {
				break;
			}
		}
		// TODO Auto-generated method stub
		return hotIslandDiaryListTemp;
	}

	@Override
	public String attentionIsland(String id, String password, String islandId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 1先检查用户
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}

		UserIsland userIslandExist = userIslandDao.checkExistByUserAndIslandId(
				id, islandId);
		if (null != userIslandExist) {
			map.put("flag", "false");
			map.put("errorCode", "-3");// -2代表萌岛
			return gson.toJson(map);
		}

		// 2关注萌岛 创建一条萌岛于用户的记录
		Island island = islandDao.getIslandById(islandId);
		if (null == island) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// -2代表萌岛
			return gson.toJson(map);
		}

		UserIsland userIsland = userIslandDao.createItemById(id, islandId);
		if (null == userIsland) {
			map.put("flag", "false");
			map.put("errorCode", "0");// 0数据库失败
			return gson.toJson(map);
		}

		map.put("flag", "true");

		return gson.toJson(map);
	}

	@Override
	public String unAttentionIsland(String id, String password, String islandId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 1先检查用户
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}

		// 2关注萌岛 创建一条萌岛于用户的记录

		Island island = islandDao.getIslandById(islandId);
		if (null == island) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// -2代表萌岛
			return gson.toJson(map);
		}

		int result = -1;
		result = userIslandDao.deleteByUserIdAndIslandId(id, islandId);
		if (result < 0) {
			map.put("flag", "false");
			map.put("errorCode", "0");// 0数据库失败
			return gson.toJson(map);
		}

		map.put("flag", "true");

		return gson.toJson(map);
	}

	@Override
	public String checkDiary2Island(String id, String password,
			String islandId, String diaryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 1先检查用户
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1代表不存在
			return gson.toJson(map);

		}

		IslandDiary islandDiary = islandDiaryDao.getItemByDiaryIdAndIslangId(
				diaryId, islandId);
		if (null == islandDiary) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// -1代表不存在
			return gson.toJson(map);
		}
		map.put("flag", "true");

		return gson.toJson(map);
	}

}
