package ssoft.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ssoft.domain.UserGroup;
import ssoft.domain.UserInfo;
import ssoft.domain.UserOfficialaccount;
import ssoft.utils.MD5Utils;
import ssoft.utils.TransactionManager;

public class User_GroupDaoImpl implements User_GroupDao {

	@Override
	public List<UserGroup> getGroupsByUserId(String id) {
		// TODO Auto-generated method stub
		String sql = "select * from user_group_relation where user_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserGroup>(UserGroup.class),id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public List<UserGroup> getGroupsByGroupId(int group_id) {
		String sql = "select * from user_group_relation where groupchat_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserGroup>(UserGroup.class),group_id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public int addUser(String group_id, String user_id, String isme,
			String messagesound, String deletemembertime) {
		String sql = "insert into user_group_relation values(null,?,?,?,?,?)";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,user_id,group_id,isme,messagesound,deletemembertime);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteItemByUserId(int id) {
		String sql = "delete from user_group_relation where user_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,id);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public UserGroup checkExistById(String userId, String groupId) {
		String sql = "select * from user_group_relation where user_id = ? and groupchat_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<UserGroup>(UserGroup.class),userId,groupId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public int saveMessagesound(String id, String targetId, String messagesound) {
		String sql = "update user_group_relation set messagesound = ? where  user_id = ? and groupchat_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,messagesound,id,targetId);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteItemByUserIdAndGroupId(String userId, String groupId) {
		String sql = "delete from user_group_relation where user_id = ? and groupchat_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,userId,groupId);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public int saveCurrentdeletetime(String userId, String groupId,Date currentTime) {
		String sql = "update user_group_relation set deletemembertime = ? where  user_id = ? and groupchat_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,currentTime,userId,groupId);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserGroup> getUseridfromUserGroup(String userId) {
//		select user_id from user_group_relation where groupchat_id in (select  groupchat_id from user_group_relation where user_id=?)
		String sql = "select * from user_group_relation where groupchat_id in (select  groupchat_id from user_group_relation where user_id=?) group by user_id";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserGroup>(UserGroup.class),userId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		}
	}
}
