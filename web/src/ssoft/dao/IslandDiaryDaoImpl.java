package ssoft.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ssoft.domain.GroupChatDiary;
import ssoft.domain.IslandDiary;
import ssoft.domain.OfficialaccountDiary;
import ssoft.utils.TransactionManager;

public class IslandDiaryDaoImpl implements IslandDiaryDao {

	@Override
	public IslandDiary createItemById(String diary_id, String island_id) {
		String sql = "insert into diary_island_relation values(null,?,?,null)";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			runner.update(sql,diary_id,island_id);
			sql = "select * from diary_island_relation where id = (select max(id) from diary_island_relation)";
			return runner.query(sql,new BeanHandler<IslandDiary>(IslandDiary.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteItemByDiaryId(String diaryId) {
		String sql = "delete from diary_island_relation where diary_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			return runner.update(sql,diaryId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteItemByDiaryAndIslandId(String diaryId, String targetId) {
		String sql = "delete from diary_island_relation where diary_id = ? and island_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			return runner.update(sql,diaryId,targetId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<IslandDiary> getIslandDiarysByDiaryId(String islandId) {
		String sql = "select * from diary_island_relation where island_id = ? order by id desc";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.query(sql, new BeanListHandler<IslandDiary>(
					IslandDiary.class), islandId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<IslandDiary> getIslandDiarysByIslandId(String islandId) {
		String sql = "select * from diary_island_relation where island_id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<IslandDiary>(
					IslandDiary.class), islandId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<IslandDiary> getHotIslandDiary(int start, int offset) {
		String sql = "select * from diary_island_relation where ishot = 1 limit ?,?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<IslandDiary>(
					IslandDiary.class),start,offset);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public IslandDiary getItemByDiaryIdAndIslangId(String diaryId,
			String islandId) {
		String sql = "select * from diary_island_relation where diary_id = ? and island_id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<IslandDiary>(
					IslandDiary.class),diaryId,islandId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<IslandDiary> getIslandDiarysByDiaryId(String islandId,
			String start, String offset) {
		String sql = "select * from diary_island_relation where island_id = ? order by id desc limit ?,? ";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.query(sql, new BeanListHandler<IslandDiary>(
					IslandDiary.class), islandId,Integer.parseInt(start),Integer.parseInt(offset));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<IslandDiary> findDiaryByDiaryId(String diary_id) {
		String sql = "select * from diary_island_relation where diary_id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.query(sql, new BeanListHandler<IslandDiary>(
					IslandDiary.class),diary_id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
