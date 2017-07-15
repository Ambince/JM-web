package ssoft.service;

import java.util.List;
import java.util.Map;

import ssoft.annotation.Tran;

public interface DiaryService extends Service {

	/**
	 * 创建随记
	 * @param id
	 * @param password
	 * @param diaryText
	 * @param imgUrl
	 * @param imgNum
	 * @param friends
	 * @param groupIds
	 * @param officialIds
	 * @param islandIds
	 * @param myPhoto 同步到我的画卷
	 * @param groupPhotoIds 
	 * @return
	 */
	@Tran
	String createDiary(String id, String password, String diaryText,
			String imgUrl, String imgNum, String friends, String groupIds,
			String officialIds, String islandIds, String myPhoto, String groupPhotoIds);

	/**
	 * 点赞
	 * @param id
	 * @param password
	 * @param diaryId
	 * @return
	 */
	String likeDiary(String id, String password, String diaryId);

	/**
	 * 收藏随记
	 * @param id
	 * @param password
	 * @param diaryId
	 * @return
	 */
	String favoriteDiary(String id, String password, String diaryId);

	/**
	 * 取消点赞
	 * @param id
	 * @param password
	 * @param diaryId
	 * @return
	 */
	String unlikeDiary(String id, String password, String diaryId);

	/**
	 * 对diaryId这个随机评论
	 * @param id
	 * @param password
	 * @param diaryId
	 * @param remarkText
	 * @param remarkImgUrl
	 * @param remarkImgNum
	 * @param mark
	 * @return
	 */
	String reamarkDiary(String id, String password, String diaryId,
			String remarkText, String remarkImgUrl, String remarkImgNum,
			String mark);

	/**
	 * 举报随记
	 * @param id
	 * @param password
	 * @param diaryId
	 * @param report
	 * @return
	 */
	String reportDiary(String id, String password, String diaryId, String reportCode);

	/**
	 * 修改随机
	 * @param id
	 * @param password
	 * @param diaryId
	 * @param diaryText
	 * @param imgUrl
	 * @param imgNum
	 * @param friends
	 * @param groupIds
	 * @param officialIds
	 * @param islandIds
	 * @param myPhoto
	 * @param groupPhotoIds 
	 * @return
	 */
	String modifyDiary(String id, String password, String diaryId,
			String diaryText, String imgUrl, String imgNum, String friends,
			String groupIds, String officialIds, String islandIds,
			String myPhoto, String groupPhotoIds);

	/**
	 * 销毁随记
	 * @param id
	 * @param password
	 * @param diaryId
	 * @param isCheckBelong 
	 * @return
	 */
	String destroyDiary(String id, String password, String diaryId, String isCheckBelong);

	/**
	 * 关闭分享
	 * @param id
	 * @param password
	 * @param targetId
	 * @param diaryId
	 * @param type
	 * @return
	 */
	String closeDiaryShare(String id, String password, String diaryId,String targetId,
			String type);

	/**
	 * 获取群聊的随机
	 * @param id
	 * @param password
	 * @param groupId
	 * @return
	 */
	String getGroupDiary(String id, String password, String groupId, String mark);

	/**
	 * 获取公众号的所有随记
	 * @param id
	 * @param password
	 * @param officialId
	 * @return
	 */
	String getOfficialaccountDiary(String id, String password, String officialId,String start,String offset, String mark);

	/**
	 * 获取萌岛的随记
	 * @param id
	 * @param password
	 * @param islandId
	 * @return
	 */
	String getIslandIdDiary(String id, String password, String islandId, String mark);

	/**
	 * 获取个人画卷
	 * @param id
	 * @param password
	 * @return
	 */
	String getPersonalPhoto(String id, String password);

	/**
	 * 获取群的画卷
	 * @param id
	 * @param password
	 * @param groupId
	 * @return
	 */
	String getGroupPhoto(String id, String password, String groupId);

	/**
	 * 获得收藏的随记
	 * @param id
	 * @param password
	 * @return
	 */
	String getFavoriteDiary(String id, String password);

	/**
	 * 获取一天随记的评论
	 * @param id
	 * @param password
	 * @param diaryId
	 * @return
	 */
	String getDiaryRemark(String id, String password, String diaryId, String mark);

	/**
	 * 获取用户的随记
	 * @param id
	 * @param password
	 * @param start
	 * @param offset
	 * @return
	 */
	String getUserDiary(String id, String password, String start, String offset);

	/**
	 * 取消收藏随记
	 * @param id
	 * @param password
	 * @param diaryId
	 * @return
	 */
	String unfavoriteDiary(String id, String password, String diaryId);

	/**
	 * 获取朋友的随记
	 * @param id
	 * @param password
	 * @param friendId
	 * @param offset 
	 * @param start 
	 * @return
	 */
	String getFriendDiary(String id, String password, String friendId, String start, String offset);

	/**
	 * 获取朋友圈的随记
	 * @param id
	 * @param password
	 * @param offset 
	 * @param start 
	 * @return
	 */
	String getFriendCircleDiary(String id, String password, String start, String offset, String mark);

	/**
	 * 转发随记
	 * @param id
	 * @param password
	 * @param diaryId
	 * @param forward
	 * @param myPhoto
	 * @param friends
	 * @param groupIds
	 * @param officialIds
	 * @param islandIds
	 * @param groupphotoIds
	 * @return
	 */
	String transmitDiary(String id, String password, String diaryId,
			String forward, String myPhoto, String friends, String groupIds,
			String officialIds, String islandIds, String groupphotoIds);

	/**
	 * 获取顶赞的人
	 * @param id
	 * @param password
	 * @param diaryId
	 * @return
	 */
	String getDiaryLikeUser(String id, String password, String diaryId);

	/**
	 * 作者：zjf
	 * 功能：获取所有随记
	 * @param
	 * @return
	 */
	int getAllDiary();
	/**
	 * 作者：zjf
	 * 功能：获取所有主随记
	 * @param
	 * @return
	 */
	int getAllStartDiary();
	/**
	 * 作者：zjf
	 * 功能：获取所有评论
	 * @param
	 * @return
	 */
	int getAllComment();
	/**
	 * 作者：zjf
	 * 功能：分页获取举报
	 * @param
	 * @return
	 */
	List<Map<String, Object>> getReportsInfo(int page);
	/**
	 * 功能：分页获取意见
	 * @param
	 * @return
	 */
	List<Map<String, Object>> getSuggestionsInfo(int page);
	/**
	 * 作者：zjf
	 * 功能：获取分页信息
	 * @param
	 * @return
	 */
	Map<String, Integer> getPageInfo(int page);
	/**
	 * 功能：获取意见箱分页信息
	 * @param
	 * @return
	 */
	Map<String, Integer> getSuggestionPageInfo(int page);
	/**
	 * 功能：添加意见
	 * @param
	 * @return
	 */
	String addSuggestion(String id, String password, String Suggestion);
	/**
	 * 作者：zjf
	 * 功能：忽略举报
	 * @param
	 * @return
	 */
	String ignoreReport(String diaryId);
	/**
	 * 作者：zjf
	 * 功能：删除举报
	 * @param
	 * @return
	 */
	String deleteReport(String diaryId);

	/**
	 * 获取我的画卷
	 * @param id
	 * @param password
	 * @return
	 */
	String getMyPhoto(String id, String password);

	/**
	 * 获取时光卷的随记
	 * @param id
	 * @param password
	 * @param start
	 * @param offset
	 * @return
	 */
	String getMyShareToFriendDiary(String id, String password, String start,
			String offset);

	/**
	 * 获取最近的三章图片
	 * @param id
	 * @param password
	 * @param targetId
	 * @return
	 */
	String getLastThreePhoto(String id, String password, String targetId);

	/**
	 * 获取最新的好友的动态
	 * @param id
	 * @param password
	 * @return
	 */
	String getLastFriendDiary(String id, String password);

	/**
	 * 检查朋友圈的更新
	 * @param id
	 * @param password
	 * @param diaryId
	 * @return
	 */
	String checkFriendCircleUpdate(String id, String password, String diaryId);

	/**
	 * 获取我的随记里边的更新
	 * @param id
	 * @param password
	 * @param diaryId
	 * @return
	 */
	String checkMyDiaryUpdate(String id, String password, String diaryId);

	/**
	 * 检查收藏随机的更新
	 * @param id
	 * @param password
	 * @param diaryId
	 * @return
	 */
	String checkCollectDiaryUpdate(String id, String password, String diaryId);

	/**
	 * 检查我的画卷的更新
	 * @param id
	 * @param password
	 * @param diaryId
	 * @return
	 */
	String checkMyPhotoUpdate(String id, String password, String diaryId);

	/**
	 * 检查群聊的随记更新
	 * @param id
	 * @param password
	 * @param groupId
	 * @param diaryId
	 * @return
	 */
	String checkGroupDiaryUpdate(String id, String password, String groupId,
			String diaryId);

	/**
	 * 检查公众号的堆积更新
	 * @param id
	 * @param password
	 * @param officialId
	 * @param diaryId
	 * @return
	 */
	String checkOfficialDiaryUpdate(String id, String password,
			String officialId, String diaryId);

	/**
	 * 检查萌岛的随记的更新
	 * @param id
	 * @param password
	 * @param islandId
	 * @param diaryId
	 * @return
	 */
	String checkIslandDiaryUpdate(String id, String password, String islandId,
			String diaryId);

	/**
	 * 作者：zjf
	 * 功能：获取举报分页信息
	 * @param
	 * @return
	 */
	Map<String, Integer> getReportPageInfo(int page);

	/**
	 * 分段获取们萌岛的随记
	 * @param id
	 * @param password
	 * @param islandId
	 * @param start
	 * @param offset
	 * @return
	 */
	String getIslandIdDiary(String id, String password, String islandId,
			String start, String offset, String mark);

	/**
	 * 分段获取收藏的随记
	 * @param id
	 * @param password
	 * @param start
	 * @param offset
	 * @return
	 */
	String getFavoriteDiary(String id, String password, String start,
			String offset);



}
