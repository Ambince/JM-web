package ssoft.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ssoft.domain.GroupChat;
import ssoft.domain.UserGroup;
import ssoft.domain.UserInfo;
import ssoft.utils.MD5Utils;
import ssoft.utils.TransactionManager;

public class GroupChatDaoImpl implements GroupChatDao {

	@Override
	public GroupChat getGroupChatById(int group_id) {
		// TODO Auto-generated method stub
		String sql = "select * from groupchat where id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<GroupChat>(GroupChat.class),group_id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public GroupChat createGroup(String groupName, String type) {
		// TODO Auto-generated method stub
		String sql = "insert into groupchat values(null,?,?,null,?,null)";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			runner.update(sql,groupName,type,sdf.format(new Date(System.currentTimeMillis())));
			//sql = "SELECT MAX(id) FROM groupchat";
			sql = "select * from groupchat where id = (select max(id) from groupchat)";
			return runner.query(sql, new BeanHandler<GroupChat>(GroupChat.class));
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteGroup(String group_id) {
		String sql = "delete from groupchat where id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql,group_id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public GroupChat getJiazuByName(String groupName) {
		// TODO Auto-generated method stub
		String sql = "select * from groupchat where name = ? and type = 2";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<GroupChat>(GroupChat.class),groupName);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public List<GroupChat> getGroupsByName(String jiatingName) {
		String sql = "select * from groupchat where name = ? ";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<GroupChat>(GroupChat.class),jiatingName);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}
	
	@Override
	public List<GroupChat> getClassNum() {
		String sql = "select * from groupchat where type = 0";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			return runner.query(sql,new BeanListHandler<GroupChat>(GroupChat.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<GroupChat> getHomeNum() {
		String sql = "select * from groupchat where type = 1";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			return runner.query(sql,new BeanListHandler<GroupChat>(GroupChat.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<GroupChat> getClanNum() {
		String sql = "select * from groupchat where type = 2";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			return runner.query(sql,new BeanListHandler<GroupChat>(GroupChat.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<GroupChat> getClanAndPaging(int start, int offset) {
		String sql = "select * from groupchat where type = 2 limit ?, ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanListHandler<GroupChat>(GroupChat.class), start, offset);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<GroupChat> getHomeAndPaging(int start, int offset) {
		String sql = "select * from groupchat where type = 1 limit ?, ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanListHandler<GroupChat>(GroupChat.class), start, offset);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<GroupChat> getClassAndPaging(int start, int offset) {
		String sql = "select * from groupchat where type = 0 limit ?, ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanListHandler<GroupChat>(GroupChat.class), start, offset);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updateNativeInfoById(String groupId, String info) {
		String sql = "update groupchat set info = ? where id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,info, groupId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public GroupChat getNationByName(String name) {
		// TODO Auto-generated method stub
				String sql = "select * from groupchat where name = ? and type = 2";
				try{
					QueryRunner runner = new QueryRunner(TransactionManager.getSource());
					return runner.query(sql, new BeanHandler<GroupChat>(GroupChat.class),name);
				}catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
					
				}
	}

	@Override
	public int resetGroupNameById(String groupId, String groupName) {
		// TODO Auto-generated method stub
		String sql = "update groupchat set name = ? where id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,groupName, groupId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
