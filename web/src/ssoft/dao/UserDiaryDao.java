package ssoft.dao;

import java.util.List;

import ssoft.domain.Diary;
import ssoft.domain.Suggestion;
import ssoft.domain.UserDiary;

public interface UserDiaryDao extends Dao {

	/**
	 * 通过用户的id删除随记
	 * @param id
	 * @return
	 */
	int deleteItemByUserId(int id);

	/**
	 * 创建一天用户与随记得记录
	 * @param user_id
	 * @param valueOf
	 * @param myPhoto
	 * @param friends 
	 * @return
	 */
	UserDiary createUserDiaryItem(String user_id, String diary_id, String myPhoto, String friends);

	/**
	 * 点赞
	 * @param user_id
	 * @param diary_id
	 * @return
	 */
	int likeDiary(String user_id, String diary_id);


	/**
	 * 收藏随机
	 * @param user_id
	 * @param diary_id
	 * @return
	 */
	int favoriteDiary(String user_id, String diary_id);

	/**
	 * 取消点赞
	 * @param user_id
	 * @param diary_id
	 * @return
	 */
	int unlikeDiary(String user_id, String diary_id);

	/**
	 * 
	 * @param user_id
	 * @param diary_id
	 * @return
	 */
	UserDiary addRemarkItem(String user_id, String diary_id);

	/**
	 * 
	 * @param user_id
	 * @param diaryId
	 * @param report
	 * @return
	 */
	int reportDiary(String user_id, String diary_id, String report);

	/**
	 * 修改朋友圈是否可见
	 * @param user_id
	 * @param diary_id
	 * @param friends 
	 * @param myPhoto 
	 * @return
	 */
	int modifyFriends(String user_id, String diary_id, String friends, String myPhoto);

	/**
	 * 删除随记的虽有用户的关系
	 * @param diaryId
	 * @return
	 */
	int deleteItemByDiaryId(String diaryId);

	/**
	 * 
	 * @param diaryId
	 * @return
	 */
	int closeFriendsShare(String user_id, String diaryId);

	/**
	 * 获取用户的所有的同步到画卷的画卷记录
	 * @param id
	 * @return
	 */
	List<UserDiary> getMyPhotoDiaryByUserId(String id);

	/**
	 * 获取收藏的影虎随记记录
	 * @param id
	 * @return
	 */
	List<UserDiary> getFavoriteDiaryByUserId(String id);

	/**
	 * 获取用户的随记offset条从start开始
	 * @param id
	 * @param start
	 * @param offset
	 */
	List<UserDiary> getUserDiaryOffset(String id, String start, String offset);


	/**
	 * 取消收藏
	 * @param user_id
	 * @param diary_id
	 * @return
	 */
	int unfavoriteDiary(String user_id, String diary_id);

	/**
	 * 找出所有的好友的随记  包括我自己的
	 * @param friends
	 * @param offset 
	 * @param start 
	 * @return
	 */
	List<UserDiary> getFriendCircleDiary(String friends, String start, String offset);

	/**
	 * 检查用户跟随记的关系是否存在
	 * @param user_id
	 * @param diaryId
	 * @return
	 */
	UserDiary checkExist(String user_id, String diaryId);

	/**
	 * 根据随记的id和用户的id 创建记录
	 * @param id
	 * @param diaryId
	 * @return
	 */
	int createItem(String id, String diaryId);

	

	/**
	 * 添加一天转发记录
	 * @param id
	 * @param diaryId
	 * @param friends 
	 * @param myPhoto 
	 */
	UserDiary addTransmitItem(String id, String diaryId, String myPhoto, String friends);

	/**
	 * 获取随记的所属人
	 * @param diaryId
	 * @return
	 */
	UserDiary getDiaryBelongs(String diaryId);

	/**
	 * 获取评论的所有者
	 * @param diaryId
	 * @return
	 */
	UserDiary getRemarkBelongs(String diaryId);

	UserDiary getRemarkBelongs(String diaryId,String friends);
	/**
	 * 获取点赞的人
	 * @param diaryId
	 * @return
	 */
	List<UserDiary> getLikeItemsByDiaryId(String diaryId);

	
	/**
	 * 作者：zjf
	 * 功能：通过用户id查找所有随记
	 * @param
	 * @return
	 */
	List<UserDiary> getDiaryByUserId(String user_id);
	/**
	 * 作者：zjf
	 * 功能：查找所有主随记
	 * @param
	 * @return
	 */
	List<UserDiary> getAllStartDiary();
	/**
	 * 作者：zjf
	 * 功能：查找所有评论
	 * @param
	 * @return
	 */
	List<Object[]> getAllComment();
	/**
	 * 作者：zjf
	 * 功能：查找所有举报
	 * @param 
	 * @return
	 */
	List<UserDiary> getAllReport();
	/**
	 * 功能：查找所有意见
	 * @param 
	 * @return
	 */
	List<UserDiary> getAllSuggestion();
	/**
	 * 作者：zjf
	 * 功能：通过随记id获取是谁发的
	 * @param
	 * @return
	 */
	UserDiary getPersionByDiaryId(int diary_id);
	/**
	 * 作者：zjf
	 * 功能：分页查找举报
	 * @param 
	 * @return
	 */
	List<UserDiary> getReportAndPaging(int start, int offset);
	/**
	 * 功能：分页查找意见
	 * @param 
	 * @return
	 */
	List<Suggestion> getSuggestionsAndPaging(int start, int offset);
	/**
	 * 功能：提交意见
	 * @param 
	 * @return
	 */
	Suggestion addSuggestions(String user_id, String diary_id);
	/**
	 * 作者：zjf
	 * 功能：通过随记id取消举报
	 * @param 
	 * @return
	 */
	int cancelReportDiary(String diaryid);

	
	/**
	 * 获取所有的喜欢随记的人
	 * @param id
	 * @return
	 */
	List<UserDiary> getAllLike(int id);

	/**
	 * 关闭我的画卷分享
	 * @param id
	 * @param diaryId
	 * @return
	 */
	int closeMyPhotoShare(String id, String diaryId);

	/**
	 * 获取所有好友最新消息的记录
	 * @param ids
	 * @return
	 */
	UserDiary getLastFriendDiary(String ids);

	/**
	 * 分段获取收藏的随记
	 * @param id
	 * @param start
	 * @param offset
	 * @return
	 */
	List<UserDiary> getFavoriteDiaryByUserId(String id, String start,
			String offset);
	

}
