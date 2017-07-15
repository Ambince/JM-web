package ssoft.dao;

import java.util.List;

import ssoft.domain.OfficialaccountDiary;

public interface OfficialaccountDiaryDao extends Dao {

	/**
	 * 添加一条公众号与随记的记录
	 * @param diary_id
	 * @param official_id
	 * @return
	 */
	OfficialaccountDiary createItemById(String diary_id, String official_id);

	/**
	 * 删除公众号 堆积记录
	 * @param diaryId
	 * @return
	 */
	int deleteItemByDiaryId(String diaryId);

	/**
	 * 通过公众号的id  和随记的id删除条目
	 * @param diaryId
	 * @param targetId
	 * @return
	 */
	int deleteItemByDiaryAndOfficialId(String diaryId, String targetId);

	/**
	 * 通过公众号的id获取公众号的所有的随记
	 * @param officialId
	 * @return
	 */
	List<OfficialaccountDiary> getOfficialDiarysByOfficialId(String officialId);

	/**
	 * 通过随记的id查询所有的公众号与随记的记录
	 * @param diaryId
	 * @return
	 */
	List<OfficialaccountDiary> getOfficialDiarysByDiaryId(String diaryId);

	/**
	 * 通过公众号的id和随记的id查询条目
	 * @param diaryId
	 * @param officialId
	 * @return
	 */
	OfficialaccountDiary getItemByDiaryIdAndOfficalId(String diaryId,
			String officialId);

	/**
	 * 获取公众号的随记  带有limit
	 * @param officialId
	 * @param start
	 * @param offset
	 * @return
	 */
	List<OfficialaccountDiary> getOfficialDiarysByOfficialId(String officialId,
			String start, String offset);

}
