package ssoft.dao;

import java.util.List;

import ssoft.domain.Diary;

public interface DiaryDao extends Dao {

	/**
	 * 创建一条随记
	 * 
	 * @param diaryText
	 * @param imgUrl
	 * @param imgNum
	 * @return
	 */
	Diary addDiary( String diaryText, String imgUrl, String imgNum);

	/**
	 * 修改随记
	 * @param diaryId
	 * @param diaryText
	 * @param imgUrl
	 * @param imgNum
	 * @return
	 */
	int modifyDiary(String diaryId, String diaryText, String imgUrl,
			String imgNum);

	/**
	 * 通过随记的id查找随记
	 * @param diaryId
	 * @return
	 */
	Diary findDiaryById(String diaryId);

	/**
	 * 删除随记
	 * @param diaryId
	 * @return
	 */
	int deleteDiaryById(String diaryId);

	
	/**
	 * 作者：zjf
	 * 功能：获取所有随记
	 * @param
	 * @return
	 */
	List<Object[]> getAllDiary();

	/**
	 * 获取24小时之内的随记
	 * @return
	 */
	List<Diary> getTodayDiary();
	

}
