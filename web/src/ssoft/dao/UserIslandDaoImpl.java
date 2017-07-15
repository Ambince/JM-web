package ssoft.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ssoft.domain.GroupChatDiary;
import ssoft.domain.Island;
import ssoft.domain.UserIsland;
import ssoft.utils.TransactionManager;

public class UserIslandDaoImpl implements UserIslandDao {

	@Override
	public int deleteItemByUserId(int id) {
		String sql = "delete from user_island_relation where user_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,id);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public UserIsland createItemById(String user_id, String island_id) {
		String sql = "insert into user_island_relation values(null,?,?)";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			runner.update(sql,user_id,island_id);
			sql = "select * from user_island_relation where id = (select max(id) from user_island_relation)";
			return runner.query(sql, new BeanHandler<UserIsland>(UserIsland.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserIsland> getIslandsByUserId(String id) {
		String sql = "select * from user_island_relation where user_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			return runner.query(sql,new BeanListHandler<UserIsland>(UserIsland.class),id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public List<UserIsland> getIslandByUserId(String user_id) {
		String sql = "select * from user_island_relation where user_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserIsland>(UserIsland.class), user_id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserIsland> getUserByIslandId(String island_id) {
		String sql = "select * from user_island_relation where island_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserIsland>(UserIsland.class), island_id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteByUserIdAndIslandId(String id, String islandId) {
		String sql = "delete from user_island_relation where user_id = ? and island_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,id,islandId);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public UserIsland checkExistByUserAndIslandId(String id, String islandId) {
		String sql = "select * from user_island_relation where user_id = ? and island_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			return runner.query(sql,new BeanHandler<UserIsland>(UserIsland.class),id,islandId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
