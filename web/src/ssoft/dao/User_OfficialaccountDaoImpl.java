package ssoft.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ssoft.domain.Officialaccounts;
import ssoft.domain.UserOfficialaccount;
import ssoft.utils.TransactionManager;

public class User_OfficialaccountDaoImpl implements User_OfficialaccountDao {

	@Override
	public List<UserOfficialaccount> getOfficialaccountsByUserId(String id) {
		String sql = "select * from user_officialaccount where user_id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserOfficialaccount>(
					UserOfficialaccount.class), id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		}
	}

	@Override
	public List<UserOfficialaccount> getOfficialaccountsByOfficialaccountId(
			int officialaccount_id) {
		String sql = "select * from user_officialaccount where officialaccount_id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserOfficialaccount>(
					UserOfficialaccount.class), officialaccount_id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		}
	}

	@Override
	public int addUser(int officialaccountId, String userId,String messagesound,String isme) {
		// TODO Auto-generated method stub
		String sql = "insert into user_officialaccount values(null,?,?,?,?)";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,userId,officialaccountId,isme,messagesound);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteItemByUserId(int id) {
		String sql = "delete from user_officialaccount where user_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,id);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public int saveMessagesound(String id, String targetId, String messagesound) {
		String sql = "update user_officialaccount set messagesound = ? where  user_id = ? and officialaccount_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,messagesound,id,targetId);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int unFollowOfficial(String user_id, String officialId) {
		// TODO Auto-generated method stub
		String sql = "delete from user_officialaccount where user_id = ? and officialaccount_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,user_id,officialId);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}
	
	@Override
	public List<Officialaccounts> getSchoolNum() {
		String sql = "select * from officialaccounts where type = 2";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			return runner.query(sql,new BeanListHandler<Officialaccounts>(Officialaccounts.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Officialaccounts> getCommunityNum() {
		String sql = "select * from officialaccounts where type = 1";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			return runner.query(sql,new BeanListHandler<Officialaccounts>(Officialaccounts.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Officialaccounts> getVillageNum() {
		String sql = "select * from officialaccounts where type = 0";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			return runner.query(sql,new BeanListHandler<Officialaccounts>(Officialaccounts.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Officialaccounts> getCommunityAndPaging(int start, int offset) {
		String sql = "select * from officialaccounts where type = 1 limit ? , ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			return runner.query(sql, new BeanListHandler<Officialaccounts>(Officialaccounts.class), start, offset);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Officialaccounts> getVillageAndPaging(int start, int offset) {
		String sql = "select * from officialaccounts where type = 0 limit ? , ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			return runner.query(sql, new BeanListHandler<Officialaccounts>(Officialaccounts.class), start, offset);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Officialaccounts> getSchoolAndPaging(int start, int offset) {
		String sql = "select * from officialaccounts where type = 2 limit ? , ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<Officialaccounts>(Officialaccounts.class), start, offset);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserOfficialaccount checkExistById(String user_id, String officialId) {
		String sql = "select * from user_officialaccount where  user_id = ? and officialaccount_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<UserOfficialaccount>(UserOfficialaccount.class), user_id, officialId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
