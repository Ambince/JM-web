package ssoft.service;

import com.google.gson.Gson;
import ssoft.dao.UserBaseInfoDao;
import ssoft.dao.UserDao;
import ssoft.domain.UserBaseInfo;
import ssoft.domain.UserInfo;
import ssoft.factory.BasicFactory;

import java.util.HashMap;
import java.util.Map;

public class UserBaseInfoServiceImpl implements UserBaseInfoService {
	private UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);
	private UserBaseInfoDao baseInfoDao = BasicFactory.getFactory().getDao(
			UserBaseInfoDao.class);
	Gson gson = new Gson();

	@Override
	public String getSysset(String id, String password) {
		// TODO Auto-generated method stub
		UserInfo userInfo = userDao.checkUserById(id, password);
		Map<String, String> map = new HashMap<String, String>();
		// 用户不存在
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1"); // -1代表不存在
			return gson.toJson(map);

		}
		UserBaseInfo baseInfo = baseInfoDao.findUserBaseInfoById(id);
		if (null == baseInfo) {
			map.put("flag", "false");
			map.put("errorCode", "0"); // 0代表没有设置过，所有都是默认
			return gson.toJson(map);
		}
		map.put("flag", "true");
		map.put("soundplay_mode", String.valueOf(baseInfo.getSoundplay_mode()));
		map.put("message_sound", String.valueOf(baseInfo.getMessage_sound()));
		map.put("message_shake", String.valueOf(baseInfo.getMessage_shake()));
		return gson.toJson(map);
	}

	@Override
	public String getBaseInfo(String id, String password) {
		// TODO Auto-generated method stub
		UserInfo userInfo = userDao.checkUserById(id, password);
		Map<String, String> map = new HashMap<String, String>();
		// 用户不存在
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1"); // -1代表用户不存在 或者用户名密码错误
			return gson.toJson(map);

		}
		UserBaseInfo baseInfo = baseInfoDao.findUserBaseInfoById(id);
		if (null == baseInfo) {
			map.put("flag", "true");
			map.put("gender", String.valueOf(userInfo.getGender()));
			System.out.println(String.valueOf(userInfo.getGender()));
			map.put("passwordsize",
					String.valueOf(userInfo.getPasswordlength()));
			map.put("phone", userInfo.getPhone());

			return gson.toJson(map);
		}
		map.put("flag", "true");
		map.put("gender", String.valueOf(userInfo.getGender()));
		map.put("phone", userInfo.getPhone());
		map.put("sockpuppet", baseInfo.getSockpuppet());
		map.put("realname", baseInfo.getRealname());
		map.put("passwordsize", String.valueOf(userInfo.getPasswordlength()));
		map.put("strangerfind", String.valueOf(baseInfo.getStrangerfind()));
		map.put("birthday", baseInfo.getBirthday());
		map.put("nativeplace", baseInfo.getNativeplace());
		map.put("liveplace", baseInfo.getLiveplace());
		map.put("userintroduce", baseInfo.getUserintroduce());
		map.put("skill", baseInfo.getSkill());
		return gson.toJson(map);
	}

	@Override
	public String saveSysset(String user_id, String password,
							 String soundplay_mode, String message_sound, String message_shake) {
		// TODO Auto-generated method stub
		UserInfo userInfo = userDao.checkUserById(user_id, password);
		Map<String, String> map = new HashMap<String, String>();
		// 用户不存在
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1"); // -1代表用户不存在 或者用户名密码错误
			return gson.toJson(map);

		}
		UserBaseInfo baseInfo = baseInfoDao.findUserBaseInfoById(user_id);
		// 找不到说明第一次设置
		if (null == baseInfo) {
			int result = baseInfoDao.addSysset(user_id, soundplay_mode,
					message_sound, message_shake);
			if (result < 0) {
				map.put("flag", "false");
				map.put("errorCode", "0"); // 0代表添加用户系统设置失败
				return gson.toJson(map);
			}
			map.put("flag", "true");
			return gson.toJson(map);
		} else {
			int result = baseInfoDao.updateSysset(user_id, soundplay_mode,
					message_sound, message_shake);
			if (result < 0) {
				map.put("flag", "false");
				map.put("errorCode", "1"); // 代表更新用户系统设置失败
				return gson.toJson(map);
			}
			map.put("flag", "true");
			return gson.toJson(map);
		}

	}

	@Override
	public String saveBaseInfo(String user_id, String password, String name,
							   String literaryname, String realname, String strangerfind,
							   String birthday, String liveplace, String nativeplace,
							   String userintroduce, String skill, String sockpuppet) {
		// TODO Auto-generated method stub
		UserInfo userInfo = userDao.checkUserById(user_id, password);
		Map<String, String> map = new HashMap<String, String>();
		// 用户不存在
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1"); // -1代表用户不存在 或者用户名密码错误
			return gson.toJson(map);

		}
		String strangerFindDB = baseInfoDao.findUserBaseInfoById(user_id).getStrangerfind()+"";
		if(strangerFindDB.equals(strangerfind)){
            /*
        * T0012
		* 不能重复
		* */
			UserBaseInfo baseInf = baseInfoDao.findUserBaseInfoBySockpupet(sockpuppet);
			if (null != baseInf) {
				map.put("flag", "false");
				map.put("errorCode", "2"); // 2代表马甲值重复
				return gson.toJson(map);
			}
		}




		UserBaseInfo baseInfo = baseInfoDao.findUserBaseInfoById(user_id);
		if (null == baseInfo) {
			// 第一次设置
			int result = baseInfoDao.addBaseInfo(user_id, realname,
					strangerfind, birthday, liveplace, nativeplace,
					userintroduce, skill, sockpuppet);
			if (result < 0) {
				map.put("flag", "false");
				map.put("errorCode", "0"); // 0代表添加用户个人信息失败
				return gson.toJson(map);
			}

			result = userDao.updateName(user_id, name, literaryname);
			if (result < 0) {
				map.put("flag", "false");
				map.put("errorCode", "0"); // 0代表添加用户名失败
				return gson.toJson(map);
			}

			map.put("flag", "true");
			return gson.toJson(map);
		} else {
			// 更新信息
			int result = baseInfoDao.updateBaseInfo(user_id, realname,
					strangerfind, birthday, liveplace, nativeplace,
					userintroduce, skill, sockpuppet);
			if (result < 0) {
				map.put("flag", "false");
				map.put("errorCode", "1"); // 代表更新用户个人信息失败
				return gson.toJson(map);
			}
			result = userDao.updateName(user_id, name, literaryname);
			if (result < 0) {
				map.put("flag", "false");
				map.put("errorCode", "0"); // 0代表添加用户名字失败
				return gson.toJson(map);
			}

			map.put("flag", "true");
			return gson.toJson(map);
		}

	}

	@Override
	public String getRobotInfo(String id, String password) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		UserInfo userInfo = userDao.checkUserById(id, password);
		Map<String, String> map = new HashMap<String, String>();
		// 用户不存在
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1"); // -1代表用户不存在 或者用户名密码错误
			return gson.toJson(map);

		}
		UserBaseInfo baseInfo = baseInfoDao.findUserBaseInfoById(id);
		if (null == baseInfo) {
			map.put("flag", "false");
			map.put("errorCode", "0"); // 0没有设置过信息
			return gson.toJson(map);
		} else {
			map.put("flag", "true");
			map.put("robot_icall", baseInfo.getRobot_icall());
			map.put("robot_name", baseInfo.getRobot_name());
			map.put("robot_callme", baseInfo.getRobot_callme());
			map.put("soundplaytext", String.valueOf(baseInfo.getSoundplay_mode()));
			return gson.toJson(map);
		}
	}

	@Override
	public String saveRobotInfo(String id, String password, String robot_icall,
								String robot_callme, String robot_name, String soundplaytext) {
		// TODO Auto-generated method stub
		UserInfo userInfo = userDao.checkUserById(id, password);
		Map<String, String> map = new HashMap<String, String>();
		// 用户不存在
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1"); // -1代表用户不存在 或者用户名密码错误
			return gson.toJson(map);

		}
		UserBaseInfo baseInfo = baseInfoDao.findUserBaseInfoById(id);
		if (null == baseInfo) {//第一次设置
			int result = baseInfoDao.addRobotInfo(robot_icall, robot_callme, robot_name, soundplaytext, id);
			if (result < 0) {
				map.put("flag", "false");
				map.put("errorCode", "0"); // 0代表数据库操作失败
				return gson.toJson(map);
			}
			map.put("flag", "true");
			return gson.toJson(map);
		} else {
			int result = baseInfoDao.updateRobotInfo(robot_icall, robot_callme, robot_name, soundplaytext, id);
			if (result < 0) {
				map.put("flag", "false");
				map.put("errorCode", "0"); // 0代表数据库操作失败
				return gson.toJson(map);
			}
			map.put("flag", "true");
			return gson.toJson(map);
		}
	}

	@Override
	public String updateBaseInfo(String id, String strangerfind) {
		baseInfoDao.updateBaseInfo(id, strangerfind);
		return null;
	}
}
