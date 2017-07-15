package ssoft.dao;

import java.util.List;

import ssoft.domain.IslandDiary;

public interface IslandDiaryDao extends Dao {

	/**
	 * 创建一天萌岛记录
	 * @param diary_id
	 * @param island_id
	 * @return
	 */
	IslandDiary createItemById(String diary_id, String island_id);

	/**
	 * 删除萌岛与随记记录
	 * @param diaryId
	 * @return
	 */
	int deleteItemByDiaryId(String diaryId);

	/**
	 * 通过萌岛的id和随记的id删除条目
	 * @param diaryId
	 * @param targetId
	 * @return
	 */
	int deleteItemByDiaryAndIslandId(String diaryId, String targetId);

	/**
	 * 获取萌岛的随记
	 * @param islandId
	 * @return
	 */
	List<IslandDiary> getIslandDiarysByDiaryId(String islandId);
	
	/**
	 * 作者：zjf
	 * 功能：获取某萌岛全部随记
	 * @param islandId
	 * @return
	 */
	List<IslandDiary> getIslandDiarysByIslandId(String islandId);

	/**
	 * 获取最萌贝壳
	 * @param string
	 * @param string2
	 * @return
	 */
	List<IslandDiary> getHotIslandDiary(int start, int offset);

	/**
	 * 根据萌岛的id和随记的id查找条目
	 * @param diaryId
	 * @param islandId
	 * @return
	 */
	IslandDiary getItemByDiaryIdAndIslangId(String diaryId, String islandId);

	/**
	 * 分段获取随记
	 * @param islandId
	 * @param start
	 * @param offset
	 * @return
	 */
	List<IslandDiary> getIslandDiarysByDiaryId(String islandId, String start,
			String offset);

	/**
	 * 通过随记的id获取包含随记的所有的条目
	 * @param diary_id
	 * @return
	 */
	List<IslandDiary> findDiaryByDiaryId(String diary_id);

}
