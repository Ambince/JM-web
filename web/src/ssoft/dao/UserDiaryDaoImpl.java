package ssoft.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ssoft.domain.Diary;
import ssoft.domain.Suggestion;
import ssoft.domain.UserDiary;
import ssoft.utils.TransactionManager;

public class UserDiaryDaoImpl implements UserDiaryDao {

	@Override
	public int deleteItemByUserId(int id) {
		String sql = "delete from user_diary_relation where user_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,id);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public UserDiary createUserDiaryItem(String user_id, String diary_id,
			String myPhoto, String friends) {
		String sql = "insert into user_diary_relation values(null,?,?,null,?,null,null,?,null,null)";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql,user_id,diary_id,friends,myPhoto);
			sql = "select * from user_diary_relation where id = (select max(id) from user_diary_relation)";
			return runner.query(sql,new BeanHandler<UserDiary>(UserDiary.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int likeDiary(String user_id, String diary_id) {
		String sql = "update user_diary_relation set ilike = 1 where user_id = ? and diary_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,user_id,diary_id);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public int favoriteDiary(String user_id, String diary_id) {
		String sql = "update user_diary_relation set favorite = 1 where user_id = ? and diary_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,user_id,diary_id);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public int unlikeDiary(String user_id, String diary_id) {
		String sql = "update user_diary_relation set ilike = 0 where user_id = ? and diary_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,user_id,diary_id);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public UserDiary addRemarkItem(String user_id, String diary_id) {
		String sql = "insert into user_diary_relation values(null,?,?,null,?,null,null,null,null,null)";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql,user_id,diary_id,4);//4代表评论
			sql = "select * from user_diary_relation where id = (select max(id) from user_diary_relation)";
			return runner.query(sql,new BeanHandler<UserDiary>(UserDiary.class));//4代表评论
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int reportDiary(String user_id, String diary_id, String report) {
		String sql = "update user_diary_relation set report = ? where user_id = ? and diary_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,report,user_id,diary_id);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public int modifyFriends(String user_id, String diary_id, String friends, String myPhoto) {
		String sql = "update user_diary_relation set isme = ?,myphoto = ? where user_id = ? and diary_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,friends,myPhoto,user_id,diary_id);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public int deleteItemByDiaryId(String diaryId) {
		String sql = "delete from user_diary_relation where diary_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,diaryId);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public int closeFriendsShare(String user_id, String diaryId) {
		String sql = "update user_diary_relation set isme = ? where user_id = ? and diary_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,3,user_id,diaryId);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public List<UserDiary> getMyPhotoDiaryByUserId(String id) {
		String sql = "select * from user_diary_relation where user_id = ? and myphoto = ? order by id desc";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanListHandler<UserDiary>(UserDiary.class),id,1);//4代表评论
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserDiary> getFavoriteDiaryByUserId(String id) {
		String sql = "select * from user_diary_relation where user_id = ? and favorite = ? order by id desc";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanListHandler<UserDiary>(UserDiary.class),id,1);//4代表评论
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserDiary> getUserDiaryOffset(String id, String start, String offset) {
		// TODO Auto-generated method stub
		//String sql = "select * from user_diary_relation where user_id = ? limit ?,?";
		String sql = "select * from user_diary_relation where user_id = ? and isme != 4 order by id desc limit ?,?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanListHandler<UserDiary>(UserDiary.class),id,Integer.parseInt(start),Integer.parseInt(offset));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		

	}

	@Override
	public int unfavoriteDiary(String user_id, String diary_id) {
		String sql = "update user_diary_relation set favorite = 0 where user_id = ? and diary_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,user_id,diary_id);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public List<UserDiary> getFriendCircleDiary(String friends, String start, String offset) {
		String sql = "select * from user_diary_relation where user_id in ("+friends+") and isme in(1,2) order by id desc limit ?,?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanListHandler<UserDiary>(UserDiary.class),Integer.parseInt(start),Integer.parseInt(offset));//friends,
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserDiary checkExist(String user_id, String diaryId) {
		String sql = "select * from user_diary_relation where user_id = ? and diary_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanHandler<UserDiary>(UserDiary.class),user_id,diaryId);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public int createItem(String id, String diaryId) {
		String sql = "insert into user_diary_relation values(null,?,?,null,null,null,null,null,null,null)";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,id,diaryId);
//			sql = "select * from user_diary_relation where id = (select max(id) from user_diary_relation)";
//			return runner.query(sql,new BeanHandler<UserDiary>(UserDiary.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

//	

	@Override
	public UserDiary addTransmitItem(String id, String diaryId, String myPhoto, String friends) {
		String sql = "insert into user_diary_relation values(null,?,?,null,?,1,null,?,null,null)";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql,id,diaryId,friends,myPhoto);//4代表评论
			sql = "select * from user_diary_relation where id = (select max(id) from user_diary_relation)";
			return runner.query(sql,new BeanHandler<UserDiary>(UserDiary.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserDiary getDiaryBelongs(String diaryId) {
		String sql = "select * from user_diary_relation where (diary_id = ? and isme = 1) or (diary_id = ? and isme = 3)";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanHandler<UserDiary>(UserDiary.class),diaryId,diaryId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserDiary getRemarkBelongs(String diaryId) {
		String sql = "select * from user_diary_relation where diary_id = ? and isme = 4";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanHandler<UserDiary>(UserDiary.class),diaryId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public UserDiary getRemarkBelongs(String diaryId,String friends) {
		String sql = "select * from user_diary_relation where diary_id = ? and isme = 4 and user_id in(?)";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanHandler<UserDiary>(UserDiary.class),diaryId,friends);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserDiary> getLikeItemsByDiaryId(String diaryId) {
		String sql = "select * from user_diary_relation where diary_id = ? and ilike = 1";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanListHandler<UserDiary>(UserDiary.class),diaryId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public List<UserDiary> getDiaryByUserId(String user_id) {
		String sql = "select * from user_diary_relation where user_id =? order by id desc";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserDiary>(UserDiary.class), user_id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Object[]> getAllComment() {
		String sql = "select * from user_diary_relation where isme = 4";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new ArrayListHandler());
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserDiary> getAllReport() {
		String sql = "select * from user_diary_relation where report != 0";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserDiary>(UserDiary.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<UserDiary> getAllSuggestion() {
		String sql = "select * from user_diary_relation where suggestion != 0";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserDiary>(UserDiary.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserDiary getPersionByDiaryId(int diary_id) {
		String sql = "select * from user_diary_relation where isme != 1";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<UserDiary>(UserDiary.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserDiary> getReportAndPaging(int start, int offset) {
		String sql = "select * from user_diary_relation where report != 0 limit ?, ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserDiary>(UserDiary.class), start, offset);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Suggestion> getSuggestionsAndPaging(int start, int offset) {
		String sql = "select user_id,content,releasetime from user_diary_relation,diary where user_diary_relation.suggestion != 0 and user_diary_relation.diary_id = diary.id order by releasetime DESC limit ?, ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<Suggestion>(Suggestion.class), start, offset);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Suggestion addSuggestions(String user_id, String diary_id) {
		String sql = "insert into user_diary_relation values(null,?,?,null,null,null,null,null,null,1)";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql,user_id,diary_id);
			sql = "select user_id,content,releasetime from user_diary_relation,diary where user_diary_relation.suggestion != 0 and user_diary_relation.diary_id = diary.id and user_diary_relation.user_id = ? and user_diary_relation.diary_id = ?";
			return runner.query(sql,new BeanHandler<Suggestion>(Suggestion.class),user_id,diary_id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int cancelReportDiary(String diaryid) {
		String sql = "update user_diary_relation set report = 0 where diary_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql, diaryid);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserDiary> getAllLike(int id) {
		String sql = "select * from user_diary_relation where id = ? and ilike = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserDiary>(UserDiary.class), id, 1);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int closeMyPhotoShare(String id, String diaryId) {
		String sql = "update user_diary_relation set myphoto = 0 where user_id = ? and diary_id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,id,diaryId);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public UserDiary getLastFriendDiary(String ids) {
//		String sql = "select * from user_diary_relation where diary_id = (select max(diary_id) from (select * from user_diary_relation where user_id in ("+ids+")))";
		String sql = "select * from user_diary_relation where diary_id = (select max(diary_id) from (select * from user_diary_relation where user_id in ("+ids+")) as total)";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanHandler<UserDiary>(UserDiary.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserDiary> getFavoriteDiaryByUserId(String id, String start,
			String offset) {
		String sql = "select * from user_diary_relation where user_id = ? and favorite = ? order by id desc limit ?,?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanListHandler<UserDiary>(UserDiary.class),id,1,Integer.parseInt(start),Integer.parseInt(offset));//4代表评论
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<UserDiary> getAllStartDiary(){
		String sql = "select * from user_diary_relation where isme <> 4";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserDiary>(UserDiary.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
