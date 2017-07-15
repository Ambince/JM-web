package ssoft.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ssoft.domain.GroupChat;
import ssoft.domain.GroupChatDiary;
import ssoft.utils.TransactionManager;

public class GroupDiaryDaoImpl implements GroupDiaryDao {

	@Override
	public GroupChatDiary createItemById(String diary_id, String group_id) {
		String sql = "insert into diary_groupchat_relation values(null,?,?,null,?)";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			runner.update(sql, diary_id, group_id, "1");
			sql = "select * from diary_groupchat_relation where id = (select max(id) from diary_groupchat_relation)";
			return runner.query(sql, new BeanHandler<GroupChatDiary>(
					GroupChatDiary.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public int deleteItemByDiaryId(String diaryId) {
		String sql = "delete from diary_groupchat_relation where diary_id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.update(sql, diaryId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteItemByGroupId(String group_id) {
		String sql = "delete from diary_groupchat_relation where groupchat_id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.update(sql, group_id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteItemByDiaryAndGroupId(String diaryId, String targetId) {
		String sql = "delete from diary_groupchat_relation where diary_id = ? and groupchat_id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.update(sql, diaryId, targetId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<GroupChatDiary> getDiaryByGroupId(String groupId) {
		String sql = "select * from diary_groupchat_relation where groupchat_id = ? order by id desc";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.query(sql, new BeanListHandler<GroupChatDiary>(
					GroupChatDiary.class), groupId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int sharePhotoToGroup(String diary_id, String groupPhotoId,
			String sharephoto, String belong) {
		String sql = "update diary_groupchat_relation set sharephoto = ?,belong = ? where  groupchat_id = ? and diary_id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql, sharephoto, belong, groupPhotoId,
					diary_id);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<GroupChatDiary> getGroupPhotoDiary(String groupId) {
		String sql = "select * from diary_groupchat_relation where groupchat_id = ? and sharephoto = ? order by id desc";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.query(sql, new BeanListHandler<GroupChatDiary>(
					GroupChatDiary.class), groupId, 1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<GroupChatDiary> getItemByDiaryId(String diaryId) {
		String sql = "select * from diary_groupchat_relation where diary_id = ? ";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.query(sql, new BeanListHandler<GroupChatDiary>(
					GroupChatDiary.class), diaryId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public GroupChatDiary getItemBydiaryIdAndGroupId(String groupId,
			String diaryId) {
		String sql = "select * from diary_groupchat_relation where diary_id = ? and groupchat_id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());

			return runner.query(sql, new BeanHandler<GroupChatDiary>(
					GroupChatDiary.class), diaryId,groupId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
