package ssoft.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ssoft.domain.Officialaccounts;
import ssoft.domain.RemarkDiary;
import ssoft.utils.TransactionManager;

public class RemarkDiaryDaoImpl implements RemarkDiaryDao {

	@Override
	public RemarkDiary addRemark(String diary_id, String remark_id, String mark) {
		String sql = "insert into diary_remark_relation values(null,?,?,?,?)";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql,diary_id,remark_id,new Date(System.currentTimeMillis()),mark);
			sql = "select * from diary_remark_relation where id = (select max(id) from diary_remark_relation)";
			return runner.query(sql,new BeanHandler<RemarkDiary>(RemarkDiary.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteItemByDiaryId(String diaryId) {
		String sql = "delete from diary_remark_relation where diary_id = ? or remark_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,diaryId,diaryId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<RemarkDiary> getItemsByDiaryId(String diaryId, String mark) {
		String sql = "";
		if("".equals(mark))
			sql = "select * from diary_remark_relation where diary_id = ?";
		else
			sql = "select * from diary_remark_relation where diary_id = ? and mark = " + mark;
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanListHandler<RemarkDiary>(RemarkDiary.class),diaryId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	public List<RemarkDiary> getItemsByDiaryId(String diaryId, String mark,String friendIds) {
		String sql = "";
		if("".equals(mark))
			sql = "select * from diary_remark_relation where diary_id = "+diaryId;
		else
			sql = "select * from diary_remark_relation where diary_id in (select diary_id from user_diary_relation where user_id in ("+friendIds+")) and mark="+mark +" and diary_id = "+diaryId;
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanListHandler<RemarkDiary>(RemarkDiary.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<RemarkDiary> getItemsByRemarkId(String remark_id) {
		String sql = "select * from diary_remark_relation where remark_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanListHandler<RemarkDiary>(RemarkDiary.class),remark_id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


}
