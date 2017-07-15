package ssoft.dao;

import java.util.List;

import ssoft.domain.GroupChatDiary;

public interface GroupDiaryDao extends Dao {

	/**
	 * 创建群组随记记录通过随记id 和群组id
	 * @param diary_id
	 * @param group_id
	 * @return
	 */
	GroupChatDiary createItemById(String diary_id, String group_id);

	/**
	 * 删除随记与群组记录
	 * @param diaryId
	 * @return
	 */
	int deleteItemByDiaryId(String diaryId);

	/**
	 * 通过群组的id 和随记的id  删除条目
	 * @param diaryId
	 * @param targetId
	 * @return
	 */
	int deleteItemByDiaryAndGroupId(String diaryId, String targetId);
	
	/**
	 * 通过群组的id 删除条目
	 * @param group_id
	 * @return
	 */
	int deleteItemByGroupId(String group_id);

	/**
	 * 通过群聊的id获取到所有的随记记录
	 * @param groupId
	 */
	List<GroupChatDiary> getDiaryByGroupId(String groupId);

	/**
	 * 同步随记的图片到群画卷
	 * @param groupPhotoId
	 * @param diary_id 
	 * @param sharephoto
	 * @param belong 
	 * @return
	 */
	int sharePhotoToGroup(String diary_id, String groupPhotoId,String sharephoto,String belong);

	/**
	 * 获取到所有分享到群画卷的随记记录
	 * @param groupId
	 * @return
	 */
	List<GroupChatDiary> getGroupPhotoDiary(String groupId);

	/**
	 * 通过随记的id获取随记与群的关系
	 * @param diaryId
	 * @return
	 */
	List<GroupChatDiary> getItemByDiaryId(String diaryId);

	/**
	 * 通过群聊的id和随记的id查找条目
	 * @param groupId
	 * @param diaryId
	 * @return
	 */
	GroupChatDiary getItemBydiaryIdAndGroupId(String groupId, String diaryId);

	

}
