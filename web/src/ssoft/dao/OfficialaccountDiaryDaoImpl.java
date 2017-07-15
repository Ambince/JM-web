package ssoft.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ssoft.domain.GroupChatDiary;
import ssoft.domain.OfficialaccountDiary;
import ssoft.utils.TransactionManager;

public class OfficialaccountDiaryDaoImpl implements OfficialaccountDiaryDao {

	@Override
	public OfficialaccountDiary createItemById(String diary_id,
			String official_id) {
		String sql = "insert into diary_officialaccount values(null,?,?)";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			runner.update(sql, diary_id, official_id);
			sql = "select * from diary_officialaccount where id = (select max(id) from diary_officialaccount)";
			return runner.query(sql, new BeanHandler<OfficialaccountDiary>(
					OfficialaccountDiary.class));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteItemByDiaryId(String diaryId) {
		String sql = "delete from diary_officialaccount where diary_id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.update(sql, diaryId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteItemByDiaryAndOfficialId(String diaryId, String targetId) {
		String sql = "delete from diary_officialaccount where diary_id = ? and officialaccount_id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.update(sql, diaryId,targetId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<OfficialaccountDiary> getOfficialDiarysByOfficialId(
			String officialId) {
		String sql = "select * from diary_officialaccount where officialaccount_id = ? order by id desc";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.query(sql, new BeanListHandler<OfficialaccountDiary>(
					OfficialaccountDiary.class), officialId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<OfficialaccountDiary> getOfficialDiarysByDiaryId(String diaryId) {
		String sql = "select * from diary_officialaccount where diary_id = ? order by id desc";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.query(sql, new BeanListHandler<OfficialaccountDiary>(
					OfficialaccountDiary.class), diaryId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public OfficialaccountDiary getItemByDiaryIdAndOfficalId(String diaryId,
			String officialId) {
		String sql = "select * from diary_officialaccount where diary_id = ? and officialaccount_id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.query(sql, new BeanHandler<OfficialaccountDiary>(
					OfficialaccountDiary.class), diaryId,officialId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<OfficialaccountDiary> getOfficialDiarysByOfficialId(
			String officialId, String start, String offset) {
		String sql = "select * from diary_officialaccount where officialaccount_id = ?  order by id desc limit ?,?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.query(sql, new BeanListHandler<OfficialaccountDiary>(
					OfficialaccountDiary.class), officialId,Integer.parseInt(start),Integer.parseInt(offset));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
