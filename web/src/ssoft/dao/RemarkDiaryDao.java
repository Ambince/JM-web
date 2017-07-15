package ssoft.dao;

import java.util.List;

import ssoft.domain.RemarkDiary;

public interface RemarkDiaryDao extends Dao {

	/**
	 * 对diary_id这个随机的评论随记remark_id，添加一条记录
	 * @param diary_id
	 * @param remark_id
	 * @param mark
	 * @return
	 */
	RemarkDiary addRemark(String diary_id, String remark_id, String mark);

	/**
	 * 删除随记评论记录
	 * @param diaryId
	 * @return
	 */
	int deleteItemByDiaryId(String diaryId);

	/**
	 * 获取一个随记的评论记录
	 * @param diaryId
	 * @return
	 */
	List<RemarkDiary> getItemsByDiaryId(String diaryId, String mark);

	/**
	 * 通过remark_id   获取记录   因为当转发或者评论的时候  主动转发的一方的id号一定在  remark 上
	 * @param remark_id
	 * @return
	 */
	List<RemarkDiary> getItemsByRemarkId(String remark_id);

	List<RemarkDiary> getItemsByDiaryId(String diaryId, String mark,String friendIds);
	

}
