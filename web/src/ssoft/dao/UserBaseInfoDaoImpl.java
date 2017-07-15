package ssoft.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ssoft.domain.UserBaseInfo;
import ssoft.utils.TransactionManager;

public class UserBaseInfoDaoImpl implements UserBaseInfoDao {

	@Override
	public UserBaseInfo findUserBaseInfoById(String id) {
		// TODO Auto-generated method stub
		String sql = "select * from userbaseinfo where user_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<UserBaseInfo>(UserBaseInfo.class),id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public UserBaseInfo findUserBaseInfoBySockpupet(String sockpuppet) {
		String sql = "select * from userbaseinfo where sockpuppet = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<UserBaseInfo>(UserBaseInfo.class),sockpuppet);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		}
	}

	@Override
	public int addSysset(String user_id, String soundplay_mode,
			String message_sound, String message_shake) {
		String sql = "insert into userbaseinfo values(null,?,null,null,null,null,null,null,null,null,null,null,null,?,?,?,null,null,null,null)";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,user_id,soundplay_mode,message_sound,message_shake);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updateSysset(String user_id, String soundplay_mode,
			String message_sound, String message_shake) {
		String sql = "update userbaseinfo set soundplay_mode = ?,message_sound = ?,message_shake = ?  where user_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,soundplay_mode,message_sound,message_shake,user_id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int addBaseInfo(String user_id,String realname,String strangerfind,
			String birthday, String liveplace, String nativeplace,
			String userintroduce, String skill,String sockpuppet) {
		String sql = "insert into userbaseinfo values(null,?,null,null,?,?,?,?,?,?,?,null,null,null,null,null,?,null,null,null)";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,user_id,strangerfind,realname,realname,nativeplace,liveplace,userintroduce,skill,sockpuppet);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int addBaseInfo(String user_id, String sockpuppet) {
		String sql = "insert into userbaseinfo values(null,?,null,null,?,null,null,null,null,null,null,null,null,null,null,null,?,null,null,null)";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,user_id,1,sockpuppet);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updateBaseInfo(String user_id, String realname,
			String strangerfind, String birthday, String liveplace,
			String nativeplace, String userintroduce, String skill,String sockpuppet) {
		// TODO Auto-generated method stub
		String sql = "update userbaseinfo set realname = ?,strangerfind = ?,birthday = ?,liveplace = ?,nativeplace = ?,userintroduce = ?,skill = ?,sockpuppet = ? where user_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,realname,strangerfind,birthday,liveplace,nativeplace,userintroduce,skill,sockpuppet,user_id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updateBaseInfo(String id, String strangerfind) {
		String sql="update userbaseinfo set strangerfind=? where user_id=?";
		try{
			QueryRunner runner=new QueryRunner(TransactionManager.getSource());
					return runner.update(sql,strangerfind,id);
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int addRobotInfo(String robot_icall, String robot_callme,
			String robot_name,String soundplaytext,String user_id) {
		// TODO Auto-generated method stub
		String sql = "insert into userbaseinfo values(null,?,?,null,null,null,null,null,null,null,null,?,?,null,null,null,null,null,?,null)";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,user_id,robot_name,robot_icall,robot_callme,soundplaytext);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updateRobotInfo(String robot_icall, String robot_callme,
			String robot_name,String soundplaytext, String user_id) {
		// TODO Auto-generated method stub
		String sql = "update userbaseinfo set robot_icall = ?,robot_callme = ?,robot_name = ?,soundplay_mode = ? where user_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,robot_icall,robot_callme,robot_name,soundplaytext,user_id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteItemByUserId(int id) {
		String sql = "delete from userbaseinfo where user_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,id);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public int resetOldPassword(String id, String oldPassword) {
		String sql = "update userbaseinfo set oldpassword = ? where user_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,oldPassword,id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}



}
