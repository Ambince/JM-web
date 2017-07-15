package ssoft.service;

import com.google.gson.Gson;
import ssoft.dao.OfficialaccountDiaryDao;
import ssoft.dao.OfficialaccountsDao;
import ssoft.dao.UserDao;
import ssoft.dao.User_OfficialaccountDao;
import ssoft.domain.OfficialaccountDiary;
import ssoft.domain.Officialaccounts;
import ssoft.domain.UserInfo;
import ssoft.domain.UserOfficialaccount;
import ssoft.factory.BasicFactory;
import ssoft.utils.Page;

import java.util.*;

public class OfficialaccountServiceImpl implements OfficialaccountService {
	private UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);
	private OfficialaccountsDao officialaccountDao = BasicFactory.getFactory()
			.getDao(OfficialaccountsDao.class);
	private User_OfficialaccountDao userOfficialaccountDao = BasicFactory
			.getFactory().getDao(User_OfficialaccountDao.class);
	private OfficialaccountDiaryDao officialaccountDiaryDao = BasicFactory
			.getFactory().getDao(OfficialaccountDiaryDao.class);
	Gson gson = new Gson();

	@Override
	public String createOfficialaccount(String id, String password,
			String officialaccountName, String type, String coordinate,
			String messagesound) {
		// TODO Auto-generated method stub
		UserInfo userInfo = userDao.checkUserById(id, password);
		Map<String, String> map = new HashMap<String, String>();
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "0");// 用户不存在
			return gson.toJson(map);
		}
		// 创建一个公众号
		Officialaccounts officialaccounts = officialaccountDao
				.createOfficialaccount(officialaccountName, coordinate, type,id);
		if (null == officialaccounts) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1 数据库失败
			return gson.toJson(map);
		}

		// 添加一条公众号与用户关系记录
		int result = userOfficialaccountDao.addUser(officialaccounts.getId(),
				id, messagesound, "1");
		if (result < 0) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1 数据库失败
			return gson.toJson(map);
		}

		map.put("flag", "true");
		map.put("officialaccountId", String.valueOf(officialaccounts.getId()));
		return gson.toJson(map);
		// // 创建一个公众号
		// Officialaccounts officialaccounts = officialaccountDao
		// .createOfficialaccount(officialaccountName, coordinate, type);
		// if (null == officialaccounts) {
		// map.put("flag", "false");
		// map.put("errorCode", "-1");// -1 数据库失败
		// return gson.toJson(map);
		// }
		//
		// // 添加一条公众号与用户关系记录
		// int result = userOfficialaccountDao.addUser(officialaccounts.getId(),
		// id, messagesound);
		// if (result < 0) {
		// map.put("flag", "false");
		// map.put("errorCode", "-1");// -1 数据库失败
		// return gson.toJson(map);
		// }
		//
		// map.put("flag", "true");
		// map.put("officialaccountId",
		// String.valueOf(officialaccounts.getId()));
		// return gson.toJson(map);
	}

	@Override
	public String followOfficial(String id, String password, String officialId,
			String type) {
		UserInfo userInfo = userDao.checkUserById(id, password);
		Map<String, String> map = new HashMap<String, String>();
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// 用户不存在
			return gson.toJson(map);
		}
		if (type.equals("0")) {
			int result = -1;
			result = userOfficialaccountDao.unFollowOfficial(id, officialId);
			if (result < 0) {
				map.put("flag", "false");
				map.put("errorCode", "-2");// -2 数据库错误
				return gson.toJson(map);
			}
		} else if (type.equals("1")) {
			UserOfficialaccount userOfficialaccount = userOfficialaccountDao
					.checkExistById(id, officialId);
			if (null != userOfficialaccount) {
				map.put("flag", "false");
				map.put("errorCode", "-3");// -3已经存在
				return gson.toJson(map);
			}

			int result = -1;
			result = userOfficialaccountDao.addUser(
					Integer.parseInt(officialId), id, "1", "0");
			if (result < 0) {
				map.put("flag", "false");
				map.put("errorCode", "-2");// -2 数据库错误
				return gson.toJson(map);
			}

		} else {
			map.put("flag", "false");
			map.put("errorCode", "-3");// 参数有误
			return gson.toJson(map);
		}

		map.put("flag", "true");
		return gson.toJson(map);
	}

	@Override
	public String getNearByOfficial(String id, String password, String latLng,
			String type, String radius) {
		// LatLng centerLatLng = gson.fromJson(latLng, LatLng.class);
		Map<String, String> map = new HashMap<String, String>();
		// UserInfo userInfo = userDao.checkUserById(id, password);
		// if (null == userInfo) {
		// map.put("flag", "false");
		// map.put("errorCode", "-1");// 用户不存在
		// return gson.toJson(map);
		// }
		//
		// List<Officialaccounts> officialaccounts =
		// officialaccountDao.getAllOfficial();
		// List<Officialaccounts> nearbyOfficialList;
		// for (Officialaccounts officialaccount : officialaccounts) {
		// LatLng tempLatlng = gson.fromJson(officialaccount.getCorrdinate(),
		// LatLng.class);
		//
		// if(SpatialRelationUtil.isCircleContainsPoint(centerLatLng,Integer.parseInt(radius),
		// tempLatlng)){
		// nearbyOfficialList.add(officialaccount);
		// }
		// }
		//

		map.put("flag", "true");
		// map.put("nearbyOfficialList", gson.toJson(nearbyOfficialList));
		return gson.toJson(map);
	}

	@Override
	public Map<String, Integer> getVillagePageInfo(int page) {
		int pageInfo[] = Page.getPageInfo(page, userOfficialaccountDao
				.getVillageNum().size(), 10);
		int pagenum = pageInfo[2];
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("pagenum", pagenum);
		map.put("curpage", page);
		return map;
	}

	@Override
	public Map<String, Integer> getSchoolPageInfo(int page) {
		int pageInfo[] = Page.getPageInfo(page, userOfficialaccountDao
				.getSchoolNum().size(), 10);
		int pagenum = pageInfo[2];
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("pagenum", pagenum);
		map.put("curpage", page);
		return map;
	}

	@Override
	public Map<String, Integer> getCommunityPageInfo(int page) {
		int pageInfo[] = Page.getPageInfo(page, userOfficialaccountDao
				.getCommunityNum().size(), 10);
		int pagenum = pageInfo[2];
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("pagenum", pagenum);
		map.put("curpage", page);
		return map;
	}

	@Override
	public int getCommunityNum() {
		return userOfficialaccountDao.getCommunityNum().size();
	}

	@Override
	public int getVillageNum() {
		return userOfficialaccountDao.getVillageNum().size();
	}

	@Override
	public int getSchoolNum() {
		return userOfficialaccountDao.getSchoolNum().size();
	}

	@Override
	public List<Map<String, Object>> getCommunitiesInfo(int page) {
		// 分页
		int[] pageInfo = Page.getPageInfo(page, userOfficialaccountDao
				.getCommunityNum().size(), 10);
		// 分页查询
		List<Officialaccounts> communities = userOfficialaccountDao
				.getCommunityAndPaging(pageInfo[0], pageInfo[1]);
		List<Map<String, Object>> communitiesInfo = new ArrayList<Map<String, Object>>();

		for (Officialaccounts community : communities) {
			Map<String, Object> communityInfo = new HashMap<String, Object>();
			communityInfo.put("id", community.getId());
			communityInfo.put("name", community.getName());
			String createPeople = null;
			for (UserOfficialaccount userOfficialaccount : userOfficialaccountDao
					.getOfficialaccountsByOfficialaccountId(community.getId())) {
				if (userOfficialaccount.getIsme() == 1) {
					createPeople = userOfficialaccount.getUser_id() + "";
					// createPeople = userDao.getUserInfoById(
					// userOfficialaccount.getUser_id()).getName();
				}
			}
			/*
			* T003
			*
			* */
			communityInfo.put("createPeople", community.getUserid());
			communityInfo.put("createTime", community.getCreatetime());
			communityInfo.put("attentionNum", userOfficialaccountDao
					.getOfficialaccountsByOfficialaccountId(community.getId())
					.size());
			communityInfo.put("postNum", officialaccountDiaryDao
					.getOfficialDiarysByOfficialId(community.getId() + "")
					.size());
			communitiesInfo.add(communityInfo);
		}

		Collections.sort(communitiesInfo,
				new Comparator<Map<String, Object>>() {

					@Override
					public int compare(Map<String, Object> o1,
							Map<String, Object> o2) {
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

		return communitiesInfo;
	}

	@Override
	public List<Map<String, Object>> getVillagesInfo(int page) {
		// 分页
		int[] pageInfo = Page.getPageInfo(page, userOfficialaccountDao
				.getVillageNum().size(), 10);
		// 分页查询
		List<Officialaccounts> villages = userOfficialaccountDao
				.getVillageAndPaging(pageInfo[0], pageInfo[1]);
		List<Map<String, Object>> villagesInfo = new ArrayList<Map<String, Object>>();
		for (Officialaccounts village : villages) {
			Map<String, Object> villageInfo = new HashMap<String, Object>();
			villageInfo.put("id", village.getId());
			villageInfo.put("name", village.getName());
			String createPeople = null;
			for (UserOfficialaccount userOfficialaccount : userOfficialaccountDao
					.getOfficialaccountsByOfficialaccountId(village.getId())) {
				if (userOfficialaccount.getIsme() == 1) {
					createPeople = userOfficialaccount.getUser_id() + "";

					// createPeople = userDao.getUserInfoById(
					// userOfficialaccount.getUser_id()).getName();
				}
			}
			villageInfo.put("createPeople", village.getUserid());
			villageInfo.put("createTime", village.getCreatetime());
			villageInfo.put("attentionNum", userOfficialaccountDao
					.getOfficialaccountsByOfficialaccountId(village.getId())
					.size());
			villageInfo
					.put("postNum",
							officialaccountDiaryDao
									.getOfficialDiarysByOfficialId(
											village.getId() + "").size());
			villagesInfo.add(villageInfo);
		}

		Collections.sort(villagesInfo, new Comparator<Map<String, Object>>() {

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

		return villagesInfo;
	}

	@Override
	public List<Map<String, Object>> getSchoolsInfo(int page) {
		// 分页
		int[] pageInfo = Page.getPageInfo(page, userOfficialaccountDao
				.getSchoolNum().size(), 10);
		// 分页查询
		List<Officialaccounts> schools = userOfficialaccountDao
				.getSchoolAndPaging(pageInfo[0], pageInfo[1]);
		List<Map<String, Object>> schoolsInfo = new ArrayList<Map<String, Object>>();
		for (Officialaccounts school : schools) {
			Map<String, Object> schoolInfo = new HashMap<String, Object>();
			schoolInfo.put("id", school.getId());
			schoolInfo.put("name", school.getName());
			String createPeople = null;
			for (UserOfficialaccount userOfficialaccount : userOfficialaccountDao
					.getOfficialaccountsByOfficialaccountId(school.getId())) {
				if (userOfficialaccount.getIsme() == 1) {
					createPeople = userOfficialaccount.getUser_id() + "";

					//createPeople = userDao.getUserInfoById(
					//		userOfficialaccount.getUser_id()).getName();
				}
			}
			schoolInfo.put("createPeople", school.getUserid());
			schoolInfo.put("createTime", school.getCreatetime());
			schoolInfo.put("attentionNum", userOfficialaccountDao
					.getOfficialaccountsByOfficialaccountId(school.getId())
					.size());
			schoolInfo.put("postNum", officialaccountDiaryDao
					.getOfficialDiarysByOfficialId(school.getId() + "").size());
			schoolsInfo.add(schoolInfo);
		}

		Collections.sort(schoolsInfo, new Comparator<Map<String, Object>>() {

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

		return schoolsInfo;
	}

	@Override
	public String getNearbyOfficialaccounts(String userid, String password,
			String latitude, String longitude, String type) {
		UserInfo userInfo = userDao.checkUserById(userid, password);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> officialList = new ArrayList<>();
		double myLatitude = (Math.PI / 180) * Double.valueOf(latitude);
		double myLongitude = (Math.PI / 180) * Double.valueOf(longitude);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// 用户不存在
			return gson.toJson(map);
		}
		List<Officialaccounts> officialaccounts = officialaccountDao
				.getAllOfficial();
		if (type.equals("2")) {
			for (Officialaccounts officialaccount : officialaccounts) {
				if (type.equals(String.valueOf(officialaccount.getType()))) {
					// 位置列表
					List<Object> corrdinateList = new ArrayList<>();
					// 位置map
					Map<String, Object> corrdinateMap = new HashMap<>();
					// Map<String, Object> tempmap = new HashMap<>();
					// 得到位置json数据
					String corrdinate = officialaccount.getCorrdinate();
					if (corrdinate != null) {
						// json转成list
						corrdinateList = gson.fromJson(corrdinate,
								ArrayList.class);
						for (Object json : corrdinateList) {
							Map<String, Object> tempmap = new HashMap<>();
							corrdinateMap = gson.fromJson(json.toString(),
									Map.class);
							double tempLatitude = (Math.PI / 180)
									* (Double) corrdinateMap.get("latitude");
							double tempLongitude = (Math.PI / 180)
									* (Double) corrdinateMap.get("longitude");
							// 得出距离 distance公里数
							double distance = Math.acos(Math.sin(myLatitude)
									* Math.sin(tempLatitude)
									+ Math.cos(myLatitude)
									* Math.cos(tempLatitude)
									* Math.cos(tempLongitude - myLongitude)) * 6371;
							// System.out.println(officialaccount.getName() +
							// "=====" +
							// distance);
							if (distance < 100) {
								tempmap.put("id",
										String.valueOf(officialaccount.getId()));
								tempmap.put("name", officialaccount.getName());
								tempmap.put("corrdinate", json.toString());
								List<UserOfficialaccount> userOfficialaccounts = userOfficialaccountDao
										.getOfficialaccountsByOfficialaccountId(officialaccount
												.getId());
								if (null != userOfficialaccounts
										&& userOfficialaccounts.size() > 0) {
									tempmap.put("memberNum", String
											.valueOf(userOfficialaccounts
													.size()));
								} else {
									tempmap.put("memberNum", "0");
								}
								officialList.add(tempmap);
							}
						}
					}

				}
			}
		} else {
			for (Officialaccounts officialaccount : officialaccounts) {
				if (type.equals(String.valueOf(officialaccount.getType()))) {
					Map<String, Object> corrdinateMap = new HashMap<>();
					Map<String, Object> tempmap = new HashMap<>();
					String corrdinate = officialaccount.getCorrdinate();

					if (corrdinate != null) {
						corrdinateMap = gson.fromJson(corrdinate, Map.class);
						if (corrdinateMap != null) {
							double tempLatitude = (Math.PI / 180)
									* (Double) corrdinateMap.get("latitude");
							double tempLongitude = (Math.PI / 180)
									* (Double) corrdinateMap.get("longitude");
							// 得出距离 distance公里数
							double distance = Math.acos(Math.sin(myLatitude)
									* Math.sin(tempLatitude)
									+ Math.cos(myLatitude)
									* Math.cos(tempLatitude)
									* Math.cos(tempLongitude - myLongitude)) * 6371;
							// System.out.println(officialaccount.getName() +
							// "=====" +
							// distance);
							if (distance < 100) {
								tempmap.put("id",
										String.valueOf(officialaccount.getId()));
								tempmap.put("name", officialaccount.getName());
								tempmap.put("corrdinate",
										officialaccount.getCorrdinate());
								List<UserOfficialaccount> userOfficialaccounts = userOfficialaccountDao
										.getOfficialaccountsByOfficialaccountId(officialaccount
												.getId());
								if (null != userOfficialaccounts
										&& userOfficialaccounts.size() > 0) {
									tempmap.put("memberNum", String
											.valueOf(userOfficialaccounts
													.size()));
								} else {
									tempmap.put("memberNum", "0");
								}
								officialList.add(tempmap);
							}
						}

					}

				}
			}
		}
		map.put("flag", "true");
		map.put("nearbyOfficialaccounts", officialList);
		return gson.toJson(map);
	}

	@Override
	public String getOfficialacountPositionById(String id, String password,
			String type, String oid) {
		UserInfo userInfo = userDao.checkUserById(id, password);
		Map<String, String> map = new HashMap<String, String>();
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "0");// 用户不存在
			return gson.toJson(map);
		}

		Officialaccounts officialaccount = officialaccountDao
				.getOfficialaccountById(Integer.parseInt(oid));
		if (null == officialaccount) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1 数据库失败
			return gson.toJson(map);
		}

		map.put("flag", "true");
		map.put("oCorrdinate", officialaccount.getCorrdinate());
		return gson.toJson(map);
	}

	@Override
	public String checkDiary2Official(String id, String password,
			String officialId, String diaryId) {
		UserInfo userInfo = userDao.checkUserById(id, password);
		Map<String, String> map = new HashMap<String, String>();
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "0");// 用户不存在
			return gson.toJson(map);
		}

		OfficialaccountDiary officialaccountDiary = officialaccountDiaryDao
				.getItemByDiaryIdAndOfficalId(diaryId, officialId);
		if (null == officialaccountDiary) {
			map.put("flag", "false");
			map.put("errorCode", "-2");// 用户不存在
			return gson.toJson(map);
		}

		map.put("flag", "true");
		return gson.toJson(map);
	}

}
