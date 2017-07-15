package ssoft.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ssoft.domain.Diary;
import ssoft.utils.TransactionManager;

public class DiaryDaoImpl implements DiaryDao {

	@Override
	public Diary addDiary(String diaryText, String imgUrl,
			String imgNum) {
		// TODO Auto-generated method stub
		String sql = "insert into diary values(null,?,?,?,?)";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			runner.update(sql,diaryText,imgUrl,imgNum,format.format(new Date(System.currentTimeMillis())));
			sql = "select * from diary where id = (select max(id) from diary)";
			return runner.query(sql, new BeanHandler<Diary>(Diary.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int modifyDiary(String diaryId, String diaryText, String imgUrl,
			String imgNum) {
		String sql = "update diary set content = ? ,image_url = ?,image_num = ? where id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,diaryText,imgUrl,imgNum,diaryId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Diary findDiaryById(String diaryId) {
		String sql = "select * from diary where id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanHandler<Diary>(Diary.class),diaryId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteDiaryById(String diaryId) {
		String sql = "delete from diary where id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,diaryId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	@Override
	public List<Object[]> getAllDiary() {
		String sql = "select * from diary";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new ArrayListHandler());
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Diary> getTodayDiary() {
		// TODO Auto-generated method stub
				String sql = "insert into diary values(null,?,?,?,?)";
				try {
					QueryRunner runner = new QueryRunner(TransactionManager.getSource());
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String today = format.format(new Date(System.currentTimeMillis()));
					String befor = format.format(new Date(System.currentTimeMillis()-24*3600*1000));
					sql = "select * from diary where releasetime between ? and ?";
					return runner.query(sql, new BeanListHandler<Diary>(Diary.class),befor,today);
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
	}
	

}
