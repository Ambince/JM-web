package ssoft.service;

import com.google.gson.Gson;
import ssoft.dao.*;
import ssoft.domain.*;
import ssoft.factory.BasicFactory;
import ssoft.utils.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiaryServiceImpl implements DiaryService {
    private UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);
    private DiaryDao diaryDao = BasicFactory.getFactory()
            .getDao(DiaryDao.class);
    private UserDiaryDao userDiaryDao = BasicFactory.getFactory().getDao(
            UserDiaryDao.class);
    private GroupDiaryDao groupDiaryDao = BasicFactory.getFactory().getDao(
            GroupDiaryDao.class);
    private OfficialaccountDiaryDao officialDiaryDao = BasicFactory
            .getFactory().getDao(OfficialaccountDiaryDao.class);
    private IslandDiaryDao islandDiaryDao = BasicFactory.getFactory().getDao(
            IslandDiaryDao.class);
    private RemarkDiaryDao remarkDiaryDao = BasicFactory.getFactory().getDao(
            RemarkDiaryDao.class);
    private UserRelationDao userRelationDao = BasicFactory.getFactory().getDao(
            UserRelationDao.class);
    private UserIslandDao userIslandDao = BasicFactory.getFactory().getDao(
            UserIslandDao.class);
    private User_GroupDao userGroupDao = BasicFactory.getFactory().getDao(
            User_GroupDao.class);
    private GroupChatDao groupDao = BasicFactory.getFactory().getDao(
            GroupChatDao.class);
    private UserBaseInfoDao userBaseInfoDao = BasicFactory.getFactory().getDao(
            UserBaseInfoDao.class);
    Gson gson = new Gson();

    @Override
    public String createDiary(String id, String password, String diaryText,
                              String imgUrl, String imgNum, String friends, String groupIds,
                              String officialIds, String islandIds, String myPhoto,
                              String groupPhotoIds) {
        Map<String, String> map = new HashMap<String, String>();

        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        // 2创建一个随记
        Diary diary = diaryDao.addDiary(diaryText, imgUrl, imgNum);
        if (null == diary) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 0是数据库错误
            return gson.toJson(map);
        }

        // 3检查是否同步到画卷 并添加一条随记与用户的关系记录
        if (!friends.equals("1") && !friends.equals("3")) {
            map.put("flag", "false");
            map.put("errorCode", "-2");// -2是参数有误
            return gson.toJson(map);
        }

        UserDiary userDiary = userDiaryDao.createUserDiaryItem(id,
                String.valueOf(diary.getId()), myPhoto, friends);
        if (null == userDiary) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 0是数据库错误
            return gson.toJson(map);
        }

        // 5添加同步到群组的记录

        List<String> groupIdList = gson.fromJson(groupIds, List.class);
        for (String groupid : groupIdList) {
            GroupChatDiary groupChatDiary = groupDiaryDao.createItemById(
                    String.valueOf(diary.getId()), groupid);
            if (null == groupChatDiary) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0是数据库错误
                return gson.toJson(map);
            }
        }

        // 6添加同步到公众号的记录
        List<String> officialIdList = gson.fromJson(officialIds, List.class);
        for (String officialId : officialIdList) {
            OfficialaccountDiary officialaccountDiary = officialDiaryDao
                    .createItemById(String.valueOf(diary.getId()), officialId);
            if (null == officialaccountDiary) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0是数据库错误
                return gson.toJson(map);
            }
        }

        // 7添加同步到萌岛的记录
        List<String> islandIdList = gson.fromJson(islandIds, List.class);
        islandIdList.remove("");
        for (String islandId : islandIdList) {

            IslandDiary islandDiary = islandDiaryDao.createItemById(
                    String.valueOf(diary.getId()), islandId);
            if (null == islandDiary) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0是数据库错误
                return gson.toJson(map);
            }
        }
        // 8 同步到画卷的群id groupPhotoIds
        List<String> groupPhotoIdList = gson
                .fromJson(groupPhotoIds, List.class);
        for (String groupPhotoId : groupPhotoIdList) {
            int result = -1;
            // 如果已经存在了则将sharephoto设置成1 就是说明即属于群又分享到画卷
            result = groupDiaryDao.sharePhotoToGroup(
                    String.valueOf(diary.getId()), groupPhotoId,
                    String.valueOf(1), String.valueOf(1));
            if (result < 0) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0是数据库错误
                return gson.toJson(map);
            }
            if (result == 0) {
                GroupChatDiary groupChatDiary = groupDiaryDao.createItemById(
                        String.valueOf(diary.getId()), groupPhotoId);
                if (null == groupChatDiary) {
                    map.put("flag", "false");
                    map.put("errorCode", "0");// 0是数据库错误
                    return gson.toJson(map);
                }
                result = -1;
                result = groupDiaryDao.sharePhotoToGroup(
                        String.valueOf(diary.getId()), groupPhotoId,
                        String.valueOf(1), String.valueOf(0));
                if (result < 0) {
                    map.put("flag", "false");
                    map.put("errorCode", "0");// 0是数据库错误
                    return gson.toJson(map);
                }
            }

        }

        map.put("flag", "true");
        map.put("diaryId", String.valueOf(diary.getId()));
        map.put("releasetime", String.valueOf(diary.getReleasetime()));
        return gson.toJson(map);
    }

    @Override
    public String likeDiary(String id, String password, String diaryId) {
        Map<String, String> map = new HashMap<String, String>();

        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        // 判断随记是否存在
        Diary diary = diaryDao.findDiaryById(diaryId);
        if (null == diary) {
            map.put("flag", "false");
            map.put("errorCode", "-2");// -2随记不存在
            return gson.toJson(map);
        }

        // 先判断是否已经存在关系
        UserDiary userDiary = userDiaryDao.checkExist(id, diaryId);
        if (null == userDiary) {
            int ret = -1;
            ret = userDiaryDao.createItem(id, diaryId);
            if (ret < 0) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0数据库出错
                return gson.toJson(map);
            }

        }

        // 2点赞
        int result = -1;
        result = userDiaryDao.likeDiary(id, diaryId);
        if (result < 0) {
            map.put("flag", "false");
            map.put("errorCode", "0");// -1数据库出错
            return gson.toJson(map);
        }

        map.put("flag", "true");
        return gson.toJson(map);

    }

    @Override
    public String favoriteDiary(String id, String password, String diaryId) {
        Map<String, String> map = new HashMap<String, String>();

        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }
        // 判断随记是否存在
        Diary diary = diaryDao.findDiaryById(diaryId);
        if (null == diary) {
            map.put("flag", "false");
            map.put("errorCode", "-2");// -2随记不存在
            return gson.toJson(map);
        }

        // 先判断是否已经存在关系
        UserDiary userDiary = userDiaryDao.checkExist(id, diaryId);
        if (null == userDiary) {
            int ret = -1;
            ret = userDiaryDao.createItem(id, diaryId);
            if (ret < 0) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0数据库出错
                return gson.toJson(map);
            }

        }

        // 2收藏
        int result = -1;
        result = userDiaryDao.favoriteDiary(id, diaryId);
        if (result < 0) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 0数据库出错
            return gson.toJson(map);
        }

        map.put("flag", "true");
        return gson.toJson(map);
    }

    @Override
    public String unlikeDiary(String id, String password, String diaryId) {
        Map<String, String> map = new HashMap<String, String>();

        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        // 2取消点赞
        int result = -1;
        result = userDiaryDao.unlikeDiary(id, diaryId);
        if (result < 0) {
            map.put("flag", "false");
            map.put("errorCode", "0");// -1数据库出错
            return gson.toJson(map);
        }

        map.put("flag", "true");
        return gson.toJson(map);
    }

    @Override
    public String reamarkDiary(String id, String password, String diaryId,
                               String remarkText, String remarkImgUrl, String remarkImgNum,
                               String mark) {
        Map<String, String> map = new HashMap<String, String>();

        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }
        // 2 创建随记
        Diary diary = diaryDao.addDiary(remarkText, remarkImgUrl, remarkImgNum);
        if (null == diary) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 0数据库错误
            return gson.toJson(map);
        }

        // 3 添加一条随记与用户的关系
        UserDiary userDiary = userDiaryDao.addRemarkItem(id,
                String.valueOf(diary.getId()));
        if (null == userDiary) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 0数据库错误
            return gson.toJson(map);
        }

        // 4添加一条随记与随记的关系 即评论
        RemarkDiary remarkDiary = remarkDiaryDao.addRemark(diaryId,
                String.valueOf(diary.getId()), mark);
        if (null == remarkDiary) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 0数据库错误
            return gson.toJson(map);
        }

        map.put("flag", "true");
        map.put("diaryId", String.valueOf(diary.getId()));
        return gson.toJson(map);

    }

    @Override
    public String reportDiary(String id, String password, String diaryId,
                              String reportCode) {
        Map<String, String> map = new HashMap<String, String>();

        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        // 2添加一条举报记录
        int result = -1;
        result = userDiaryDao.reportDiary(id, diaryId, reportCode);
        if (result < 0) {
            map.put("flag", "false");
            map.put("errorCode", "0");// -1用户不存在
            return gson.toJson(map);
        }
        if (result == 0) {
            int resultTemp = -1;
            resultTemp = userDiaryDao.createItem(id, diaryId);
            if (resultTemp > 0) {
                result = userDiaryDao.reportDiary(id, diaryId, reportCode);
                if (result < 0) {
                    map.put("flag", "false");
                    map.put("errorCode", "-2");// -2数据库失败
                    return gson.toJson(map);
                }
            }
        }

        map.put("flag", "true");
        return gson.toJson(map);
    }

    @Override
    public String modifyDiary(String id, String password, String diaryId,
                              String diaryText, String imgUrl, String imgNum, String friends,
                              String groupIds, String officialIds, String islandIds,
                              String myPhoto, String groupPhotoIds) {
        Map<String, String> map = new HashMap<String, String>();

        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }
        // 2验证随记是否存在
        Diary diary = diaryDao.findDiaryById(diaryId);
        if (null == diary) {
            map.put("flag", "false");
            map.put("errorCode", "-2");// -2随机不存在
            return gson.toJson(map);
        }
        int result = -1;
        // 3修改随记

        result = diaryDao.modifyDiary(diaryId, diaryText, imgUrl, imgNum);
        if (result < 0) {
            map.put("flag", "false");
            map.put("errorCode", "-3");// -3数据库出错
            return gson.toJson(map);
        }

        // 4修改朋友圈 与画轴
        result = -1;
        result = userDiaryDao.modifyFriends(id, diaryId, friends, myPhoto);
        if (result < -1) {
            map.put("flag", "false");
            map.put("errorCode", "-3");// -3数据库出错
            return gson.toJson(map);
        }
        // 修改随记与群的记录

        // 5添加同步到群组的记录

        List<String> groupIdList = gson.fromJson(groupIds, List.class);
        for (String groupid : groupIdList) {
            GroupChatDiary groupChatDiary = groupDiaryDao.createItemById(
                    String.valueOf(diary.getId()), groupid);
            if (null == groupChatDiary) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0是数据库错误
                return gson.toJson(map);
            }
        }

        // 6添加同步到公众号的记录
        List<String> officialIdList = gson.fromJson(officialIds, List.class);
        for (String officialId : officialIdList) {
            OfficialaccountDiary officialaccountDiary = officialDiaryDao
                    .createItemById(String.valueOf(diary.getId()), officialId);
            if (null == officialaccountDiary) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0是数据库错误
                return gson.toJson(map);
            }
        }

        // 7添加同步到萌岛的记录
        List<String> islandIdList = gson.fromJson(islandIds, List.class);
        for (String islandId : islandIdList) {
            IslandDiary islandDiary = islandDiaryDao.createItemById(
                    String.valueOf(diary.getId()), islandId);
            if (null == islandDiary) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0是数据库错误
                return gson.toJson(map);
            }
        }
        // 8 同步到画卷的群id groupPhotoIds
        List<String> groupPhotoIdList = gson
                .fromJson(groupPhotoIds, List.class);
        for (String groupPhotoId : groupPhotoIdList) {
            result = -1;
            // 如果已经存在了则将sharephoto设置成1 就是说明即属于群又分享到画卷
            result = groupDiaryDao.sharePhotoToGroup(
                    String.valueOf(diary.getId()), groupPhotoId,
                    String.valueOf(1), String.valueOf(1));
            if (result < 0) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0是数据库错误
                return gson.toJson(map);
            }
            if (result == 0) {
                GroupChatDiary groupChatDiary = groupDiaryDao.createItemById(
                        String.valueOf(diary.getId()), groupPhotoId);
                if (null == groupChatDiary) {
                    map.put("flag", "false");
                    map.put("errorCode", "0");// 0是数据库错误
                    return gson.toJson(map);
                }
                result = -1;
                result = groupDiaryDao.sharePhotoToGroup(
                        String.valueOf(diary.getId()), groupPhotoId,
                        String.valueOf(1), String.valueOf(0));
                if (result < 0) {
                    map.put("flag", "false");
                    map.put("errorCode", "0");// 0是数据库错误
                    return gson.toJson(map);
                }
            }

        }

        Diary diary2 = diaryDao.findDiaryById(diaryId);
        map.put("flag", "true");
        map.put("diaryId", diaryId);
        map.put("releasetime", String.valueOf(diary2.getReleasetime()));
        return gson.toJson(map);
    }

    @Override
    public String destroyDiary(String id, String password, String diaryId, String isCheckBelong) {
        Map<String, String> map = new HashMap<String, String>();

        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }
        if (null != isCheckBelong && isCheckBelong.equals("true")) {
            //检查是不是自己的随记
            UserDiary userDiary = userDiaryDao.getDiaryBelongs(diaryId);
            if (null == userDiary || userDiary.getUser_id() != Integer.parseInt(id)) {
                map.put("flag", "false");
                map.put("errorCode", "-2");// 0是数据库错误
                return gson.toJson(map);
            }
        }


        // 2删除用户与随机表记录
        int result = -1;
        result = userDiaryDao.deleteItemByDiaryId(diaryId);
        if (result < 0) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 0是数据库错误
            return gson.toJson(map);
        }
        // 3删除群组与随机表记录
        // List<GroupChatDiary> groupChatDiaryList =
        // groupDiaryDao.getItemByDiaryId(diaryId);
        // if (groupChatDiaryList.size() > 0) {
        result = -1;
        result = groupDiaryDao.deleteItemByDiaryId(diaryId);
        if (result < 0) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 0是数据库错误
            return gson.toJson(map);
        }
        // }

        // 4删除公众号与随机表记录

        // List<OfficialaccountDiary> officialaccountDiarieList =
        // officialDiaryDao.getOfficialDiarysByDiaryId(diaryId);
        // if (officialaccountDiarieList.size() > 0) {
        result = -1;
        result = officialDiaryDao.deleteItemByDiaryId(diaryId);
        if (result < 0) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 0是数据库错误
            return gson.toJson(map);
        }
        // }

        // 5删除萌岛与随机表记录
        result = -1;
        result = islandDiaryDao.deleteItemByDiaryId(diaryId);
        if (result < 0) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 0是数据库错误
            return gson.toJson(map);
        }
        // 6删除评论与随机表记录
        result = -1;
        result = remarkDiaryDao.deleteItemByDiaryId(diaryId);
        if (result < 0) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 0是数据库错误
            return gson.toJson(map);
        }
        // 7删除随记
        result = -1;
        result = diaryDao.deleteDiaryById(diaryId);
        if (result < 0) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 0是数据库错误
            return gson.toJson(map);
        }

        map.put("flag", "true");
        return gson.toJson(map);
    }

    @Override
    public String closeDiaryShare(String id, String password, String diaryId,
                                  String targetId, String type) {
        Map<String, String> map = new HashMap<String, String>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }
        // 判断类型
        if (type.equals("island")) {
            int result = -1;
            result = islandDiaryDao.deleteItemByDiaryAndIslandId(diaryId,
                    targetId);
            if (result < 0) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0是数据库错误
                return gson.toJson(map);
            }

        } else if (type.equals("group")) {
            int result = -1;
            result = groupDiaryDao.deleteItemByDiaryAndGroupId(diaryId,
                    targetId);
            if (result < 0) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0是数据库错误
                return gson.toJson(map);
            }
        } else if (type.equals("official")) {
            int result = -1;
            result = officialDiaryDao.deleteItemByDiaryAndOfficialId(diaryId,
                    targetId);
            if (result < 0) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0是数据库错误
                return gson.toJson(map);
            }

        } else if (type.equals("friends")) {
            int result = -1;
            result = userDiaryDao.closeFriendsShare(id, diaryId);
            if (result < 0) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0是数据库错误
                return gson.toJson(map);
            }
        } else if (type.equals("photo")) {
            int result = -1;
            result = userDiaryDao.closeMyPhotoShare(id, diaryId);
            if (result < 0) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0是数据库错误
                return gson.toJson(map);
            }
        }

        map.put("flag", "true");
        return gson.toJson(map);
    }

    @Override
    public String getGroupDiary(String id, String password, String groupId, String mark) {
        Map<String, Object> map = new HashMap<String, Object>();

        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        // 2 根据群id获取群的虽有的diaryId
        List<GroupChatDiary> groupChatDiarys = groupDiaryDao
                .getDiaryByGroupId(groupId);
        List<Diary> diaryListTemp = new ArrayList<Diary>();
        for (GroupChatDiary groupDiary : groupChatDiarys) {
            Diary diary = diaryDao.findDiaryById(String.valueOf(groupDiary
                    .getDiary_id()));
            if (null == diary) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0数据库出错
                return gson.toJson(map);
            }
            diaryListTemp.add(diary);

        }
        if (diaryListTemp.size() <= 0) {
            map.put("flag", "false");
            map.put("errorCode", "-2");// -2没有随记
            return gson.toJson(map);
        }

        List<Diary> diaryList = new ArrayList<Diary>();
        List<Map<String, String>> userList = new ArrayList<Map<String, String>>();

        getUserInfoAndDiaryInfo(id, diaryListTemp, diaryList, userList, mark);
        orderByRemarkDiary(diaryList, userList, mark);
        // ---------------
        // for (Diary diary : diaryListTemp) {
        // UserDiary userDiary =
        // userDiaryDao.getDiaryBelongs(String.valueOf(diary.getId()));
        //
        // UserInfo userInfo2 = userDao.findUserById(String.valueOf(userDiary
        // .getUser_id()));
        //
        // if (null != diary && null != userInfo2) {
        // if (userDiary.getIsme() == 1 || userDiary.getForward() == 1) {
        //
        // List<Diary> remarkList = getDiaryRemarkList(String
        // .valueOf(diary.getId()));
        // diary.setRemarkList(remarkList);
        // // 判断是不是对别人的转发 如果是的话就把准发的dairy查出来
        // if (userDiary.getForward() == 1) {
        // List<RemarkDiary> RemarkDiarys = remarkDiaryDao
        // .getItemsByRemarkId(String.valueOf(userDiary
        // .getDiary_id()));
        // for (RemarkDiary remarkDiary : RemarkDiarys) {
        // if (userDiary.getDiary_id() == remarkDiary
        // .getRemark_id()) {// 如果我是对别人的转发
        // // 则这条随记的id就是在remark_id一直
        // Diary forwardFrom = diaryDao
        // .findDiaryById(String
        // .valueOf(RemarkDiarys.get(0)
        // .getDiary_id()));
        // if (null != forwardFrom) {
        // diary.setForwardFromDiary(forwardFrom);
        // // 获取转发的随记的作者的信息
        // UserDiary userDiary2 = userDiaryDao
        // .getDiaryBelongs(String
        // .valueOf(forwardFrom
        // .getId()));
        // if (null != userDiary2) {
        // UserInfo userInfo3 = userDao
        // .findUserById(String
        // .valueOf(userDiary2
        // .getUser_id()));
        //
        // if (null != userInfo3) {
        // Map<String, String> maptemp = new HashMap<String, String>();
        // maptemp.put("name",
        // userInfo3.getName());
        // maptemp.put("literaryName",
        // userInfo3.getLiteraryName());
        // maptemp.put("gender", String
        // .valueOf(userInfo3
        // .getGender()));
        // maptemp.put("id", String
        // .valueOf(userInfo3.getId()));
        // diary.setForwordFromInfo(maptemp);
        // // diaryList.add(diary);
        // }
        // }
        // }
        // }
        // }
        //
        // }
        //
        // // 处理这些随记的盘评论与回复
        //
        // List<Diary> commentDiaryList = new ArrayList<Diary>();
        // List<Diary> commentDiaryListTemp = getDiaryRemarkList(String
        // .valueOf(diary.getId()));
        // if (null != commentDiaryListTemp) {
        // List<UserInfo> commentUserInfoList = new ArrayList<UserInfo>();
        // for (int i = 0; i < commentDiaryListTemp.size(); i++) {
        // UserDiary userDiaryTemp = userDiaryDao
        // .getRemarkBelongs(String
        // .valueOf(commentDiaryListTemp
        // .get(i).getId()));
        // if (null != userDiaryTemp) {
        // UserInfo userInfoTemp = userDao
        // .findUserById(String
        // .valueOf(userDiaryTemp
        // .getUser_id()));
        // if (null != userInfoTemp) {
        // commentDiaryList.add(commentDiaryListTemp
        // .get(i));
        // commentUserInfoList.add(userInfoTemp);
        // }
        //
        // }
        //
        // }
        // List<Map<String, String>> commentUserListRet = new
        // ArrayList<Map<String, String>>();
        // for (UserInfo userInfoTemp : commentUserInfoList) {
        // Map<String, String> mapTemp = new HashMap<String, String>();
        //
        // mapTemp.put("name", userInfoTemp.getName());
        // mapTemp.put("literaryName",
        // userInfoTemp.getLiteraryName());
        // mapTemp.put("gender",
        // String.valueOf(userInfoTemp.getGender()));
        // mapTemp.put("id",
        // String.valueOf(userInfoTemp.getId()));
        // commentUserListRet.add(mapTemp);
        //
        // }
        //
        // diary.setRemarkList(commentDiaryList);
        // diary.setRemarkUserInfoList(commentUserListRet);
        // }
        //
        // diaryList.add(diary);
        // // userList.add(userInfo2);
        // Map<String, String> mapTemp = new HashMap<String, String>();
        // List<UserDiary> likeItemsList = userDiaryDao
        // .getLikeItemsByDiaryId(String.valueOf(userDiary
        // .getDiary_id()));
        // UserDiary userDiary2 = userDiaryDao.checkExist(id,
        // String.valueOf(userDiary.getDiary_id()));
        // if (null != userDiary2) {
        // mapTemp.put("like",
        // String.valueOf(userDiary2.getIlike()));
        // } else {
        // mapTemp.put("like", "0");
        // }
        // mapTemp.put("name", userInfo2.getName());
        // mapTemp.put("literaryName", userInfo2.getLiteraryName());
        // mapTemp.put("gender", String.valueOf(userInfo2.getGender()));
        // mapTemp.put("id", String.valueOf(userInfo2.getId()));
        //
        // mapTemp.put("likeNum", String.valueOf(likeItemsList.size()));
        // userList.add(mapTemp);
        // }
        // }
        // }

        // -----------------

        // List<Diary> diaryList = new ArrayList<Diary>();
        // List<Map<String, String>> userList = new ArrayList<Map<String,
        // String>>();
        // getUserInfoFromDiary(diaryListTemp, diaryList, userList);

        map.put("flag", "true");
        map.put("diaryList", diaryList);
        map.put("userList", userList);
        return gson.toJson(map);
    }

    /**
     * 通过随记返回随记的所有者信息
     *
     * @param diaryListTemp
     * @param diaryList
     * @param userList
     */
    private void getUserInfoFromDiary(List<Diary> diaryListTemp,
                                      List<Diary> diaryList, List<Map<String, String>> userList) {
        // TODO Auto-generated method stub
        for (Diary diary : diaryListTemp) {
            // 属于谁
            UserDiary userDiary = userDiaryDao.getDiaryBelongs(String
                    .valueOf(diary.getId()));
            if (null != userDiary) {
                UserInfo userInfo2 = userDao.findUserById(String
                        .valueOf(userDiary.getUser_id()));
                if (null != userInfo2) {
                    diaryList.add(diary);
                    Map<String, String> mapTemp = new HashMap<String, String>();
                    mapTemp.put("name", userInfo2.getName());
                    mapTemp.put("ilike", String.valueOf(userDiary.getIlike()));
                    mapTemp.put("literaryName", userInfo2.getLiteraryName());
                    mapTemp.put("gender", String.valueOf(userInfo2.getGender()));
                    mapTemp.put("id", String.valueOf(userInfo2.getId()));
                    userList.add(mapTemp);
                }

            } else {
                userDiary = userDiaryDao.getRemarkBelongs(String.valueOf(diary
                        .getId()));
                if (null != userDiary) {
                    UserInfo userInfo2 = userDao.findUserById(String
                            .valueOf(userDiary.getUser_id()));
                    if (null != userInfo2) {
                        diaryList.add(diary);
                        Map<String, String> mapTemp = new HashMap<String, String>();
                        mapTemp.put("name", userInfo2.getName());
                        mapTemp.put("ilike",
                                String.valueOf(userDiary.getIlike()));
                        mapTemp.put("literaryName", userInfo2.getLiteraryName());
                        mapTemp.put("gender",
                                String.valueOf(userInfo2.getGender()));
                        mapTemp.put("id", String.valueOf(userInfo2.getId()));
                        userList.add(mapTemp);
                    }
                }
            }

        }
    }

    /**
     * 工具函数 用随记查出随记的用户已经评论情况
     */
    private void getUserInfoAndDiaryInfo(String id, List<Diary> diaryListTemp,
                                         List<Diary> diaryList, List<Map<String, String>> userList, String mark) {
        for (Diary diary : diaryListTemp) {
            UserDiary userDiary = userDiaryDao.getDiaryBelongs(String
                    .valueOf(diary.getId()));

            if (null == userDiary) {
                continue;
            }
            UserInfo userInfo2 = userDao.findUserById(String.valueOf(userDiary
                    .getUser_id()));

            if (null != diary && null != userInfo2) {
                if (userDiary.getIsme() == 1 || userDiary.getIsme() == 3
                        || userDiary.getForward() == 1) {

                    List<Diary> remarkList = getDiaryRemarkList(String
                            .valueOf(diary.getId()), mark);
                    diary.setRemarkList(remarkList);
                    // 判断是不是对别人的转发 如果是的话就把准发的dairy查出来
                    if (userDiary.getForward() == 1) {
                        List<RemarkDiary> RemarkDiarys = remarkDiaryDao
                                .getItemsByRemarkId(String.valueOf(userDiary
                                        .getDiary_id()));
                        for (RemarkDiary remarkDiary : RemarkDiarys) {
                            if (userDiary.getDiary_id() == remarkDiary
                                    .getRemark_id()) {// 如果我是对别人的转发
                                // 则这条随记的id就是在remark_id一直
                                Diary forwardFrom = diaryDao
                                        .findDiaryById(String
                                                .valueOf(RemarkDiarys.get(0)
                                                        .getDiary_id()));
                                if (null != forwardFrom) {
                                    diary.setForwardFromDiary(forwardFrom);
                                    // 获取转发的随记的作者的信息
                                    UserDiary userDiary2 = userDiaryDao
                                            .getDiaryBelongs(String
                                                    .valueOf(forwardFrom
                                                            .getId()));
                                    if (null != userDiary2) {
                                        UserInfo userInfo3 = userDao
                                                .findUserById(String
                                                        .valueOf(userDiary2
                                                                .getUser_id()));

                                        if (null != userInfo3) {
                                            Map<String, String> maptemp = new HashMap<String, String>();
                                            maptemp.put("name",
                                                    userInfo3.getName());
                                            UserBaseInfo userBaseInfo = userBaseInfoDao.findUserBaseInfoById(String.valueOf(userInfo3.getId()));
                                            if (userBaseInfo != null && userBaseInfo.getSockpuppet() != null && !"".equals(userBaseInfo.getSockpuppet())) {
                                                maptemp.put("rebotName", userBaseInfo.getSockpuppet());
                                            } else {
                                                maptemp.put("rebotName", "萌喜鹊");
                                            }
                                            maptemp.put("literaryName",
                                                    userInfo3.getLiteraryName());
                                            maptemp.put("gender", String
                                                    .valueOf(userInfo3
                                                            .getGender()));

                                            maptemp.put("id", String
                                                    .valueOf(userInfo3.getId()));
                                            diary.setForwordFromInfo(maptemp);
                                            // diaryList.add(diary);
                                        }
                                    }
                                }
                            }
                        }

                    }

                    // 处理这些随记的盘评论与回复

                    List<Diary> commentDiaryList = new ArrayList<Diary>();
                    List<Diary> commentDiaryListTemp = getDiaryRemarkList(String
                            .valueOf(diary.getId()), mark);
                    if (null != commentDiaryListTemp) {
                        List<UserInfo> commentUserInfoList = new ArrayList<UserInfo>();
                        for (int i = 0; i < commentDiaryListTemp.size(); i++) {
                            UserDiary userDiaryTemp = userDiaryDao
                                    .getRemarkBelongs(String
                                            .valueOf(commentDiaryListTemp
                                                    .get(i).getId()));
                            if (null != userDiaryTemp) {
                                UserInfo userInfoTemp = userDao
                                        .findUserById(String
                                                .valueOf(userDiaryTemp
                                                        .getUser_id()));
                                if (null != userInfoTemp) {
                                    commentDiaryList.add(commentDiaryListTemp
                                            .get(i));
                                    commentUserInfoList.add(userInfoTemp);
                                }

                            }

                        }
                        List<Map<String, String>> commentUserListRet = new ArrayList<Map<String, String>>();
                        for (UserInfo userInfoTemp : commentUserInfoList) {
                            Map<String, String> mapTemp = new HashMap<String, String>();

                            mapTemp.put("name", userInfoTemp.getName());
                            UserBaseInfo userBaseInfo = userBaseInfoDao.findUserBaseInfoById(String.valueOf(userInfoTemp.getId()));
                            if (userBaseInfo != null && userBaseInfo.getSockpuppet() != null && !"".equals(userBaseInfo.getSockpuppet())) {
                                mapTemp.put("rebotName", userBaseInfo.getSockpuppet());
                            } else {
                                mapTemp.put("rebotName", "萌喜鹊");
                            }
                            mapTemp.put("literaryName",
                                    userInfoTemp.getLiteraryName());

                            mapTemp.put("gender",
                                    String.valueOf(userInfoTemp.getGender()));
                            mapTemp.put("id",
                                    String.valueOf(userInfoTemp.getId()));
                            commentUserListRet.add(mapTemp);

                        }

                        diary.setRemarkList(commentDiaryList);
                        diary.setRemarkUserInfoList(commentUserListRet);
                    }

                    // List<UserDiary> likeUserLIst =
                    // userDiaryDao.getAllLike(diary.getId());
                    // if (null != likeUserLIst) {
                    // diary.setLikeNum(likeUserLIst.size());
                    // }
                    //
                    diaryList.add(diary);
                    // userList.add(userInfo2);
                    Map<String, String> mapTemp = new HashMap<String, String>();
                    List<UserDiary> likeItemsList = userDiaryDao
                            .getLikeItemsByDiaryId(String.valueOf(userDiary
                                    .getDiary_id()));
                    UserDiary userDiary2 = userDiaryDao.checkExist(id,
                            String.valueOf(userDiary.getDiary_id()));
                    if (null != userDiary2) {
                        mapTemp.put("ilike",
                                String.valueOf(userDiary2.getIlike()));
                    } else {
                        mapTemp.put("ilike", "0");
                    }
                    mapTemp.put("name", userInfo2.getName());
                    UserBaseInfo userBaseInfo = userBaseInfoDao.findUserBaseInfoById(String.valueOf(userInfo2.getId()));
                    if (userBaseInfo != null && userBaseInfo.getSockpuppet() != null && !"".equals(userBaseInfo.getSockpuppet())) {
                        mapTemp.put("rebotName", userBaseInfo.getSockpuppet());
                    } else {
                        mapTemp.put("rebotName", "萌喜鹊");
                    }
                    mapTemp.put("literaryName", userInfo2.getLiteraryName());
                    mapTemp.put("gender", String.valueOf(userInfo2.getGender()));
                    mapTemp.put("id", String.valueOf(userInfo2.getId()));
                    // mapTemp.put("ilike",
                    // String.valueOf(userDiary.getIlike()));
                    mapTemp.put("likeNum", String.valueOf(likeItemsList.size()));
                    userList.add(mapTemp);
                }
            }
        }
    }

    @Override
    public String getOfficialaccountDiary(String id, String password,
                                          String officialId, String start, String offset, String mark) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }
        // 2 根据群id获取群的虽有的diaryId
        List<OfficialaccountDiary> officialDiarys = officialDiaryDao
                .getOfficialDiarysByOfficialId(officialId, start, offset);
        List<Diary> diaryListTemp = new ArrayList<Diary>();
        for (OfficialaccountDiary officialDiary : officialDiarys) {
            Diary diary = diaryDao.findDiaryById(String.valueOf(officialDiary
                    .getDiary_id()));
            if (null == diary) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0数据库出错
                return gson.toJson(map);
            }
            diaryListTemp.add(diary);

        }
        if (diaryListTemp.size() <= 0) {
            map.put("flag", "false");
            map.put("errorCode", "-2");// -2没有随记
            return gson.toJson(map);
        }

        List<Diary> diaryList = new ArrayList<Diary>();
        List<Map<String, String>> userList = new ArrayList<Map<String, String>>();

        getUserInfoAndDiaryInfo(id, diaryListTemp, diaryList, userList, mark);

        orderByRemarkDiary(diaryList, userList, mark);

        map.put("flag", "true");
        map.put("diaryList", diaryList);
        map.put("userList", userList);
        return gson.toJson(map);
    }

    private void orderByRemarkDiary(List<Diary> diaryList,
                                    List<Map<String, String>> userList, String mark) {
        // TODO Auto-generated method stub
        List<RemarkDiary> remarkDiaries = new ArrayList<RemarkDiary>();
        List<Diary> diaryListTemp = diaryList;
        List<Map<String, String>> userListTemp = userList;

        // 1先找到所有的随记的最新的一天评论
        for (int i = 0; i < diaryList.size(); i++) {
            List<RemarkDiary> remarkDiaryTemp = remarkDiaryDao
                    .getItemsByDiaryId(String.valueOf(diaryList.get(i).getId()), mark);

            RemarkDiary remarkDiary = new RemarkDiary();
            if (null != remarkDiaryTemp && remarkDiaryTemp.size() > 0) {

                remarkDiary.setId(remarkDiaryTemp.get(remarkDiaryTemp.size() - 1).getRemark_id());
                remarkDiaries.add(remarkDiary);
            } else {

                if (null != remarkDiary) {
                    remarkDiary.setId(diaryList.get(i).getId());
                    remarkDiaries.add(remarkDiary);
                } else {
                    remarkDiary = new RemarkDiary();
                    remarkDiary.setId(diaryList.get(i).getId());
                    remarkDiaries.add(remarkDiary);
                }
            }


        }

        // 2判断评论的id最大的排在前边
        for (int i = 0; i < remarkDiaries.size(); i++) {
            for (int j = 0; j < remarkDiaries.size() - i - 1; j++) {
                RemarkDiary remarkDiaryTemp = remarkDiaries.get(j);
                Diary diaryTemp = diaryListTemp.get(j);
                Map<String, String> mapTemp = userListTemp.get(j);

                if (remarkDiaries.get(j).getId() < remarkDiaries.get(j + 1)
                        .getId()) {
                    remarkDiaries.set(j, remarkDiaries.get(j + 1));
                    remarkDiaries.set(j + 1, remarkDiaryTemp);
                    diaryListTemp.set(j, diaryListTemp.get(j + 1));
                    diaryListTemp.set(j + 1, diaryTemp);
                    userListTemp.set(j, userListTemp.get(j + 1));
                    userListTemp.set(j + 1, mapTemp);

                }

            }
        }

        diaryList = new ArrayList<Diary>();
        userList = new ArrayList<Map<String, String>>();
        diaryList = diaryListTemp;
        userList = userListTemp;
    }

    @Override
    public String getIslandIdDiary(String id, String password, String islandId, String mark) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }
        // 2 根据群id获取群的虽有的diaryId
        List<IslandDiary> islandDiarys = islandDiaryDao
                .getIslandDiarysByDiaryId(islandId);
        List<Diary> diaryListTemp = new ArrayList<Diary>();
        for (IslandDiary islandDiary : islandDiarys) {
            Diary diary = diaryDao.findDiaryById(String.valueOf(islandDiary
                    .getDiary_id()));
            if (null == diary) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0数据库出错
                return gson.toJson(map);
            }
            diaryListTemp.add(diary);

        }

        List<UserIsland> islandUser = userIslandDao.getUserByIslandId(islandId);
        UserIsland userIsland = userIslandDao.checkExistByUserAndIslandId(id,
                islandId);

        List<Diary> diaryList = new ArrayList<Diary>();
        List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
        if (diaryListTemp.size() > 0) {
            getUserInfoAndDiaryInfo(id, diaryListTemp, diaryList, userList, mark);
        }

        map.put("flag", "true");
        if (null != islandUser) {
            map.put("islandUserNum", String.valueOf(islandUser.size()));
        } else {
            map.put("islandUserNum", "0");
        }
        if (null != userIsland) {
            map.put("belongTo", "1");
        } else {
            map.put("belongTo", "0");
        }
        map.put("diaryList", diaryList);
        map.put("userList", userList);
        return gson.toJson(map);
    }

    @Override
    public String getPersonalPhoto(String id, String password) {
        Map<String, String> map = new HashMap<String, String>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        // 2 现获取我同步到画轴的所有的随记的id
        List<UserDiary> userDiaryList = userDiaryDao
                .getMyPhotoDiaryByUserId(id);
        if (userDiaryList.size() <= 0) {
            map.put("flag", "false");
            map.put("errorCode", "-2");// -1用户不存在
            return gson.toJson(map);
        }

        // 获取所有的随记
        List<Diary> diaryList = new ArrayList<Diary>();
        for (UserDiary userDiary : userDiaryList) {
            Diary diary = diaryDao.findDiaryById(String.valueOf(userDiary
                    .getDiary_id()));
            if (null != diary && userDiary.getMyphoto() == 1) {
                diaryList.add(diary);
            }

        }

        map.put("flag", "true");
        map.put("diaryList", gson.toJson(diaryList));
        return gson.toJson(map);
    }

    @Override
    public String getGroupPhoto(String id, String password, String groupId) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }
        // 2找到所有分享到群画卷的记录
        List<GroupChatDiary> groupChatDiaryList = groupDiaryDao
                .getGroupPhotoDiary(groupId);
        if (groupChatDiaryList.size() <= 0) {
            map.put("flag", "false");
            map.put("errorCode", "-2");// -1用户不存在
            return gson.toJson(map);
        }

        // // 3获取所有的随记
        // List<Diary> diaryList = new ArrayList<Diary>();
        // for (GroupChatDiary groupDiary : groupChatDiaryList) {
        // Diary diary = diaryDao.findDiaryById(String.valueOf(groupDiary
        // .getDiary_id()));
        // diaryList.add(diary);
        // }
        //
        // map.put("flag", "true");
        // map.put("diaryList", diaryList);
        // return gson.toJson(map);

        // mapTemp.put("diaryId", String.valueOf(diaryTemp.getId()));
        // mapTemp.put("content", diaryTemp.getContent());
        // if (null != likeList) {
        // mapTemp.put("likeNum", String.valueOf(likeList.size()));
        // }
        //
        // mapTemp.put("user_id", String.valueOf(diaryBelongs.getUser_id()));
        // mapTemp.put("releasetime",
        // String.valueOf(diaryTemp.getReleasetime()));
        // mapTemp.put("photoNum",
        // String.valueOf(diaryTemp.getImage_num()));

        // 3获取所有的随记
        List<Map<String, String>> diaryList = new ArrayList<Map<String, String>>();
        for (GroupChatDiary groupDiary : groupChatDiaryList) {
            Diary diary = diaryDao.findDiaryById(String.valueOf(groupDiary
                    .getDiary_id()));
            Map<String, String> mapTemp = new HashMap<String, String>();
            List<UserDiary> likeList = userDiaryDao
                    .getLikeItemsByDiaryId(String.valueOf(diary.getId()));
            UserDiary diaryBelongs = userDiaryDao.getDiaryBelongs(String
                    .valueOf(diary.getId()));
            UserInfo userInfo2 = new UserInfo();
            if (null != likeList && null != diaryBelongs) {
                userInfo2 = userDao.findUserById(String.valueOf(diaryBelongs
                        .getUser_id()));
                mapTemp.put("likeNum", String.valueOf(likeList.size()));
                mapTemp.put("user_id",
                        String.valueOf(diaryBelongs.getUser_id()));
            }
            UserDiary userDiary = userDiaryDao.checkExist(id,
                    String.valueOf(diary.getId()));
            mapTemp.put("user_name", userInfo2.getName());
            mapTemp.put("image_num", String.valueOf(diary.getImage_num()));
            mapTemp.put("releasetime", String.valueOf(diary.getReleasetime()));
            mapTemp.put("id", String.valueOf(diary.getId()));
            mapTemp.put("content", diary.getContent());
            if (null != userDiary) {
                mapTemp.put("ilike", String.valueOf(userDiary.getIlike()));
            } else {
                mapTemp.put("ilike", "0");
            }

            diaryList.add(mapTemp);
        }

        map.put("flag", "true");
        map.put("diaryList", diaryList);
        return gson.toJson(map);
    }

    @Override
    public String getFavoriteDiary(String id, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }
        // 2 现获取我同步到画轴的所有的随记的id
        List<UserDiary> userDiaryList = userDiaryDao
                .getFavoriteDiaryByUserId(id);
        if (userDiaryList.size() <= 0) {
            map.put("flag", "false");
            map.put("errorCode", "-2");// -1用户不存在
            return gson.toJson(map);
        }
        // 获取所有的随记
        List<Diary> diaryList = new ArrayList<Diary>();
        for (UserDiary userDiary : userDiaryList) {
            if (null != userDiary && userDiary.getFavorite() == 1) {
                Diary diary = diaryDao.findDiaryById(String.valueOf(userDiary
                        .getDiary_id()));

                diaryList.add(diary);
            }

        }
        map.put("flag", "true");
        map.put("diaryList", diaryList);
        return gson.toJson(map);

    }

    @Override
    public String getDiaryRemark(String id, String password, String diaryId, String m) {
        Map<String, String> map = new HashMap<String, String>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }
        //检测该用户是否为随机的拥有者
        UserDiary ud = userDiaryDao.checkExist(id, diaryId);
        String mark = m;
        if (ud != null && ud.getReport() == 0) {
            mark = "";
        }
        List<Diary> diaryList = new ArrayList<Diary>();
        List<Diary> diaryListTemp = getDiaryRemarkList(diaryId, mark);
        if (null == diaryListTemp) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 没有评论
            return gson.toJson(map);
        }

        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        for (int i = 0; i < diaryListTemp.size(); i++) {
            UserDiary userDiary = userDiaryDao.getRemarkBelongs(String
                    .valueOf(diaryListTemp.get(i).getId()));
            if (null != userDiary) {
                UserInfo userInfo2 = userDao.findUserById(String
                        .valueOf(userDiary.getUser_id()));
                if (null != userInfo2) {
                    diaryList.add(diaryListTemp.get(i));
                    userInfoList.add(userInfo2);
                }

            }

        }
        List<Map<String, String>> userListRet = new ArrayList<Map<String, String>>();
        for (UserInfo userInfoTemp : userInfoList) {
            Map<String, String> mapTemp = new HashMap<String, String>();

            mapTemp.put("name", userInfoTemp.getName());
            mapTemp.put("literaryName", userInfoTemp.getLiteraryName());
            mapTemp.put("gender", String.valueOf(userInfoTemp.getGender()));
            mapTemp.put("id", String.valueOf(userInfoTemp.getId()));
            userListRet.add(mapTemp);

        }

        map.put("flag", "true");
        map.put("diaryList", gson.toJson(diaryList));
        map.put("userList", gson.toJson(userListRet));
        return gson.toJson(map);

    }

    /**
     * 获取随记的评论列表 工具函数
     *
     * @param diaryId
     */
    public List<Diary> getDiaryRemarkList(String diaryId, String mark) {
        // TODO Auto-generated method stub
        List<RemarkDiary> remarkDiaryList = remarkDiaryDao
                .getItemsByDiaryId(diaryId, mark);

        if (remarkDiaryList.size() > 0) {
            List<Diary> remarkList = new ArrayList<Diary>();
            // 设置随记的评论列表
            for (RemarkDiary remarkDiary : remarkDiaryList) {
                Diary diary = diaryDao.findDiaryById(String.valueOf(remarkDiary
                        .getRemark_id()));
                if (null != diary) {
                    // 子评论的随记
                    List<Map<String, String>> userInfoList = new ArrayList<Map<String, String>>();
                    List<Diary> childRemarkDiaryList = new ArrayList<Diary>();
                    childRemarkDiaryList = getDiaryRemarkList(String
                            .valueOf(diary.getId()), mark);
                    if (null != childRemarkDiaryList) {
                        if (childRemarkDiaryList.size() > 0) {
                            for (Diary diary2 : childRemarkDiaryList) {
                                UserDiary userDiary = userDiaryDao
                                        .getRemarkBelongs(String.valueOf(diary2
                                                .getId()));
                                UserInfo userInfo = userDao.findUserById(String
                                        .valueOf(userDiary.getUser_id()));
                                if (null != userInfo) {
                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put("name", userInfo.getName());
                                    map.put("literaryName",
                                            userInfo.getLiteraryName());
                                    map.put("gender", String.valueOf(userInfo
                                            .getGender()));
                                    map.put("id",
                                            String.valueOf(userInfo.getId()));
                                    userInfoList.add(map);
                                }
                            }
                            diary.setRemarkList(childRemarkDiaryList);
                            diary.setRemarkUserInfoList(userInfoList);
                        }
                    }

                }

                // 查看是不是转发 转发不是评论
                UserDiary userDiary = userDiaryDao.getRemarkBelongs(String
                        .valueOf(diary.getId()));
                if (null == userDiary) {
                    return null;
                }
                if (userDiary.getForward() != 1) {
                    remarkList.add(diary);
                }

            }
            return remarkList;
        }

        return null;

    }


    private List<Diary> getDiaryRemarkList(String diaryId, String mark,String friends) {
        // TODO Auto-generated method stub
        List<RemarkDiary> remarkDiaryList = remarkDiaryDao
                .getItemsByDiaryId(diaryId, mark);

        if (remarkDiaryList.size() > 0) {
            List<Diary> remarkList = new ArrayList<Diary>();
            // 设置随记的评论列表
            for (RemarkDiary remarkDiary : remarkDiaryList) {
                Diary diary = diaryDao.findDiaryById(String.valueOf(remarkDiary
                        .getRemark_id()));
                if (null != diary) {
                    // 子评论的随记
                    List<Map<String, String>> userInfoList = new ArrayList<Map<String, String>>();
                    List<Diary> childRemarkDiaryList = new ArrayList<Diary>();
                    childRemarkDiaryList = getDiaryRemarkList(String
                            .valueOf(diary.getId()), mark,friends);
                    if (null != childRemarkDiaryList) {
                        if (childRemarkDiaryList.size() > 0) {
                            for (Diary diary2 : childRemarkDiaryList) {
                                UserDiary userDiary = userDiaryDao
                                        .getRemarkBelongs(String.valueOf(diary2
                                                .getId()));
                                UserInfo userInfo = userDao.findUserById(String
                                        .valueOf(userDiary.getUser_id()));
                                if (null != userInfo) {
                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put("name", userInfo.getName());
                                    map.put("literaryName",
                                            userInfo.getLiteraryName());
                                    map.put("gender", String.valueOf(userInfo
                                            .getGender()));
                                    map.put("id",
                                            String.valueOf(userInfo.getId()));
                                    userInfoList.add(map);
                                }
                            }
                            diary.setRemarkList(childRemarkDiaryList);
                            diary.setRemarkUserInfoList(userInfoList);
                        }
                    }

                }

                // 查看是不是转发 转发不是评论
                UserDiary userDiary = userDiaryDao.getRemarkBelongs(String
                        .valueOf(diary.getId()));
                if (null == userDiary) {
                    return null;
                }
                if (userDiary.getForward() != 1) {
                    remarkList.add(diary);
                }

            }
            return remarkList;
        }

        return null;

    }

    @Override
    public String getUserDiary(String id, String password, String start,
                               String offset) {
        Map<String, String> map = new HashMap<String, String>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }
        List<UserDiary> userDiaries = userDiaryDao.getUserDiaryOffset(id,
                start, offset);
        if (userDiaries.size() <= 0) {
            map.put("flag", "false");
            map.put("errorCode", "-2");// -没有随记
            return gson.toJson(map);
        }

        List<Diary> diaryList = new ArrayList<Diary>();
        for (UserDiary userDiary : userDiaries) {
            Diary diary = diaryDao.findDiaryById(String.valueOf(userDiary
                    .getDiary_id()));
            if (null != diary) {
                if (userDiary.getForward() == 1) {// 检查是否是转发
                    List<RemarkDiary> remarkFromDiary = remarkDiaryDao
                            .getItemsByRemarkId(String.valueOf(userDiary
                                    .getDiary_id()));
                    if (remarkFromDiary.size() > 0) {
                        // 获取到转发随机
                        Diary diary2 = diaryDao.findDiaryById(String
                                .valueOf(remarkFromDiary.get(0).getDiary_id()));

                        if (null != diary2) {
                            diary.setForwardFromDiary(diary2);
                            // 获取转发的随记的作者的信息
                            UserDiary userDiary2 = userDiaryDao
                                    .getDiaryBelongs(String.valueOf(diary2
                                            .getId()));
                            if (null != userDiary2) {
                                UserInfo userInfo2 = userDao
                                        .findUserById(String.valueOf(userDiary2
                                                .getUser_id()));

                                if (null != userInfo2) {
                                    Map<String, String> maptemp = new HashMap<String, String>();
                                    maptemp.put("name", userInfo2.getName());
                                    maptemp.put("literaryName",
                                            userInfo2.getLiteraryName());
                                    maptemp.put("gender", String
                                            .valueOf(userInfo2.getGender()));
                                    maptemp.put("id",
                                            String.valueOf(userInfo2.getId()));
                                    diary.setForwordFromInfo(maptemp);
                                    diaryList.add(diary);
                                }
                            }
                        }

                    }
                } else {
                    diaryList.add(diary);
                }

            }

        }

        map.put("flag", "true");
        map.put("diaryList", gson.toJson(diaryList));// 没有评论
        return gson.toJson(map);
    }

    @Override
    public String unfavoriteDiary(String id, String password, String diaryId) {
        Map<String, String> map = new HashMap<String, String>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        // 2取消收藏
        int result = -1;
        result = userDiaryDao.unfavoriteDiary(id, diaryId);
        if (result < 0) {
            map.put("flag", "false");
            map.put("errorCode", "0");// -1数据库出错
            return gson.toJson(map);
        }

        map.put("flag", "true");
        return gson.toJson(map);
    }

    @Override
    public String getFriendDiary(String id, String password, String friendId,
                                 String start, String offset) {
        Map<String, String> map = new HashMap<String, String>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }
        List<UserDiary> userDiaries = userDiaryDao.getUserDiaryOffset(friendId,
                start, offset);
        if (userDiaries.size() <= 0) {
            map.put("flag", "false");
            map.put("errorCode", "-2");// -没有随记
            return gson.toJson(map);
        }

        // List<Diary> diaryList = new ArrayList<Diary>();
        // for (UserDiary userDiary : userDiaries) {
        // Diary diary = diaryDao.findDiaryById(String.valueOf(userDiary
        // .getDiary_id()));
        // if (null != diary) {
        // diaryList.add(diary);
        // }
        //
        // }
        List<Diary> diaryList = new ArrayList<Diary>();
        for (UserDiary userDiary : userDiaries) {
            Diary diary = diaryDao.findDiaryById(String.valueOf(userDiary
                    .getDiary_id()));
            if (null != diary) {
                if (userDiary.getForward() == 1) {// 检查是否是转发
                    List<RemarkDiary> remarkFromDiary = remarkDiaryDao
                            .getItemsByRemarkId(String.valueOf(userDiary
                                    .getDiary_id()));
                    if (remarkFromDiary.size() > 0) {
                        // 获取到转发随机
                        Diary diary2 = diaryDao.findDiaryById(String
                                .valueOf(remarkFromDiary.get(0).getDiary_id()));

                        if (null != diary2) {
                            diary.setForwardFromDiary(diary2);
                            // 获取转发的随记的作者的信息
                            UserDiary userDiary2 = userDiaryDao
                                    .getDiaryBelongs(String.valueOf(diary2
                                            .getId()));
                            if (null != userDiary2) {
                                UserInfo userInfo2 = userDao
                                        .findUserById(String.valueOf(userDiary2
                                                .getUser_id()));

                                if (null != userInfo2) {
                                    Map<String, String> maptemp = new HashMap<String, String>();
                                    maptemp.put("name", userInfo2.getName());
                                    maptemp.put("literaryName",
                                            userInfo2.getLiteraryName());
                                    maptemp.put("gender", String
                                            .valueOf(userInfo2.getGender()));
                                    maptemp.put("id",
                                            String.valueOf(userInfo2.getId()));
                                    diary.setForwordFromInfo(maptemp);
                                    diaryList.add(diary);
                                }
                            }
                        }

                    }
                } else {
                    diaryList.add(diary);
                }

            }

        }

        map.put("flag", "true");
        map.put("diaryList", gson.toJson(diaryList));// 没有评论
        return gson.toJson(map);
    }

	@Override
	public String getFriendCircleDiary(String id, String password,
			String start, String offset, String mark) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 1 验证用户密码是否正确
		UserInfo userInfo = userDao.checkUserById(id, password);
		if (null == userInfo) {
			map.put("flag", "false");
			map.put("errorCode", "-1");// -1用户不存在
			return gson.toJson(map);
		}

        String friends = getFriendIds(id, true);
		if ("".equals(friends)){
		    friends=id;
        }else {
            friends +="," + id + "";
        }
        // 3 找出这些好友的随记
        List<UserDiary> userDiaryList = new ArrayList<UserDiary>();
        userDiaryList = userDiaryDao.getFriendCircleDiary(friends, start,
                offset);

        List<Diary> diaryList = new ArrayList<Diary>();
        // List<UserInfo> userList = new ArrayList<UserInfo>();
        List<Map<String, String>> userListRet = new ArrayList<Map<String, String>>();
        for (UserDiary userDiary : userDiaryList) {
            Diary diary = diaryDao.findDiaryById(String.valueOf(userDiary
                    .getDiary_id()));
            UserInfo userInfo2 = userDao.findUserById(String.valueOf(userDiary
                    .getUser_id()));

            if (null != diary && null != userInfo2) {
                if (userDiary.getIsme() == 1 || userDiary.getForward() == 1) {

                    List<Diary> remarkList = getDiaryRemarkList(String
                            .valueOf(diary.getId()), mark,friends);
                    diary.setRemarkList(remarkList);
                    // 判断是不是对别人的转发 如果是的话就把准发的dairy查出来
                    if (userDiary.getForward() == 1) {
                        List<RemarkDiary> RemarkDiarys = remarkDiaryDao
                                .getItemsByRemarkId(String.valueOf(userDiary
                                        .getDiary_id()));
                        for (RemarkDiary remarkDiary : RemarkDiarys) {
                            if (userDiary.getDiary_id() == remarkDiary
                                    .getRemark_id()) {// 如果我是对别人的转发
                                // 则这条随记的id就是在remark_id一直
                                Diary forwardFrom = diaryDao
                                        .findDiaryById(String
                                                .valueOf(RemarkDiarys.get(0)
                                                        .getDiary_id()));
                                if (null != forwardFrom) {
                                    diary.setForwardFromDiary(forwardFrom);
                                    // 获取转发的随记的作者的信息
                                    UserDiary userDiary2 = userDiaryDao
                                            .getDiaryBelongs(String
                                                    .valueOf(forwardFrom
                                                            .getId()));
                                    if (null != userDiary2) {
                                        UserInfo userInfo3 = userDao
                                                .findUserById(String
                                                        .valueOf(userDiary2
                                                                .getUser_id()));

                                        if (null != userInfo3) {
                                            Map<String, String> maptemp = new HashMap<String, String>();
                                            maptemp.put("name",
                                                    userInfo3.getName());
                                            maptemp.put("literaryName",
                                                    userInfo3.getLiteraryName());
                                            maptemp.put("gender", String
                                                    .valueOf(userInfo3
                                                            .getGender()));
                                            maptemp.put("id", String
                                                    .valueOf(userInfo3.getId()));
                                            diary.setForwordFromInfo(maptemp);
                                            // diaryList.add(diary);
                                        }
                                    }
                                }
                            }
                        }

                    }

                    // 处理这些随记的盘评论与回复

                    List<Diary> commentDiaryList = new ArrayList<Diary>();
                    List<Diary> commentDiaryListTemp = getDiaryRemarkList(String
                            .valueOf(diary.getId()), mark,friends);
                    if (null != commentDiaryListTemp) {
                        List<UserInfo> commentUserInfoList = new ArrayList<UserInfo>();
                        for (int i = 0; i < commentDiaryListTemp.size(); i++) {
                            UserDiary userDiaryTemp = userDiaryDao.getRemarkBelongs(String.valueOf(commentDiaryListTemp.get(i).getId()),friends);
                            if (null != userDiaryTemp) {
                                UserInfo userInfoTemp = userDao
                                        .findUserById(String
                                                .valueOf(userDiaryTemp
                                                        .getUser_id()));
                                if (null != userInfoTemp) {
                                    commentDiaryList.add(commentDiaryListTemp
                                            .get(i));
                                    commentUserInfoList.add(userInfoTemp);
                                }

                            }

                        }
                        List<Map<String, String>> commentUserListRet = new ArrayList<Map<String, String>>();
                        for (UserInfo userInfoTemp : commentUserInfoList) {
                            Map<String, String> mapTemp = new HashMap<String, String>();

                            mapTemp.put("name", userInfoTemp.getName());
                            mapTemp.put("literaryName",
                                    userInfoTemp.getLiteraryName());
                            mapTemp.put("gender",
                                    String.valueOf(userInfoTemp.getGender()));
                            mapTemp.put("id",
                                    String.valueOf(userInfoTemp.getId()));
                            commentUserListRet.add(mapTemp);

                        }

                        diary.setRemarkList(commentDiaryList);
                        diary.setRemarkUserInfoList(commentUserListRet);
                    }

                    diaryList.add(diary);
                    // userList.add(userInfo2);
                    Map<String, String> mapTemp = new HashMap<String, String>();
                    List<UserDiary> likeItemsList = userDiaryDao
                            .getLikeItemsByDiaryId(String.valueOf(userDiary
                                    .getDiary_id()));
                    UserDiary userDiary2 = userDiaryDao.checkExist(id,
                            String.valueOf(userDiary.getDiary_id()));
                    if (null != userDiary2) {
                        mapTemp.put("like",
                                String.valueOf(userDiary2.getIlike()));
                    } else {
                        mapTemp.put("like", "0");
                    }
                    mapTemp.put("name", userInfo2.getName());
                    mapTemp.put("literaryName", userInfo2.getLiteraryName());
                    mapTemp.put("gender", String.valueOf(userInfo2.getGender()));
                    mapTemp.put("id", String.valueOf(userInfo2.getId()));

                    mapTemp.put("likeNum", String.valueOf(likeItemsList.size()));
                    userListRet.add(mapTemp);
                }
            }

        }

        // for (UserInfo userInfoTemp : userList) {
        // Map<String, String> mapTemp = new HashMap<String, String>();
        //
        // mapTemp.put("name", userInfoTemp.getName());
        // mapTemp.put("literaryName", userInfoTemp.getLiteraryName());
        // mapTemp.put("gender", String.valueOf(userInfoTemp.getGender()));
        // mapTemp.put("id", String.valueOf(userInfoTemp.getId()));
        //
        // userListRet.add(mapTemp);
        // }
        map.put("flag", "true");
        map.put("diaryList", gson.toJson(diaryList));
        System.out.println(gson.toJson(diaryList));
//        map.put("diaryList", diaryList);
        map.put("userList", gson.toJson(userListRet));
        System.out.println(userDiaryList);
//        map.put("userList", userListRet);
        return gson.toJson(map);
    }

    @Override
    public String transmitDiary(String id, String password, String diaryId,
                                String forward, String myPhoto, String friends, String groupIds,
                                String officialIds, String islandIds, String groupphotoIds) {

        Map<String, String> map = new HashMap<String, String>();

        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        // 2创建一个随记
        Diary diary = diaryDao.addDiary(forward, "", "0");
        if (null == diary) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 0是数据库错误
            return gson.toJson(map);
        }
        // 天假随记与随记的记录
        RemarkDiary remarkDiary = remarkDiaryDao.addRemark(diaryId,
                String.valueOf(diary.getId()), "0");
        if (null == remarkDiary) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 0是数据库错误
            return gson.toJson(map);
        }

        // 3检查是否同步到画卷 并添加一条随记与用户的关系记录
        if (!friends.equals("1") && !friends.equals("3")) {
            map.put("flag", "false");
            map.put("errorCode", "-2");// -2是参数有误
            return gson.toJson(map);
        }
        UserDiary userDiary = userDiaryDao.checkExist(id,
                String.valueOf(diary.getId()));

        if (null == userDiary) {
            userDiary = userDiaryDao.addTransmitItem(id,
                    String.valueOf(diary.getId()), myPhoto, friends);
            // userDiary = userDiaryDao.createUserDiaryItem(id,
            // String.valueOf(diary.getId()), myPhoto, friends);
            if (null == userDiary) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0是数据库错误
                return gson.toJson(map);
            }
        } else {
            if (userDiary.getIsme() == 1) {
                map.put("flag", "false");
                map.put("errorCode", "2");// 2自己的不能转发
                return gson.toJson(map);
            }
        }
        int result = -1;
        // result = userDiaryDao.TransmitDiary(id,
        // String.valueOf(diary.getId()),
        // forward);
        // if (result < 0) {
        // map.put("flag", "false");
        // map.put("errorCode", "0");// 0是数据库错误
        // return gson.toJson(map);
        // }

        // 5添加同步到群组的记录

        List<String> groupIdList = gson.fromJson(groupIds, List.class);
        if (groupIdList.size() > 0) {
            for (String groupid : groupIdList) {
                GroupChatDiary groupChatDiary = groupDiaryDao.createItemById(
                        String.valueOf(diary.getId()), groupid);
                if (null == groupChatDiary) {
                    map.put("flag", "false");
                    map.put("errorCode", "0");// 0是数据库错误
                    return gson.toJson(map);
                }
            }
        }

        // 6添加同步到公众号的记录
        List<String> officialIdList = gson.fromJson(officialIds, List.class);
        if (officialIdList.size() > 0) {
            for (String officialId : officialIdList) {
                OfficialaccountDiary officialaccountDiary = officialDiaryDao
                        .createItemById(String.valueOf(diary.getId()),
                                officialId);
                if (null == officialaccountDiary) {
                    map.put("flag", "false");
                    map.put("errorCode", "0");// 0是数据库错误
                    return gson.toJson(map);
                }
            }
        }

        // 7添加同步到萌岛的记录
        List<String> islandIdList = gson.fromJson(islandIds, List.class);
        if (islandIdList.size() > 0) {
            for (String islandId : islandIdList) {
                IslandDiary islandDiary = islandDiaryDao.createItemById(
                        String.valueOf(diary.getId()), islandId);
                if (null == islandDiary) {
                    map.put("flag", "false");
                    map.put("errorCode", "0");// 0是数据库错误
                    return gson.toJson(map);
                }
            }
        }

        // 8 同步到画卷的群id groupPhotoIds
        List<String> groupPhotoIdList = gson
                .fromJson(groupphotoIds, List.class);
        if (groupPhotoIdList.size() > 0) {
            for (String groupPhotoId : groupPhotoIdList) {
                result = -1;
                // 如果已经存在了则将sharephoto设置成1 就是说明即属于群又分享到画卷
                result = groupDiaryDao.sharePhotoToGroup(
                        String.valueOf(diary.getId()), groupPhotoId,
                        String.valueOf(1), String.valueOf(1));
                if (result < 0) {
                    map.put("flag", "false");
                    map.put("errorCode", "0");// 0是数据库错误
                    return gson.toJson(map);
                }
                if (result == 0) {
                    GroupChatDiary groupChatDiary = groupDiaryDao
                            .createItemById(String.valueOf(diary.getId()),
                                    groupPhotoId);
                    if (null == groupChatDiary) {
                        map.put("flag", "false");
                        map.put("errorCode", "0");// 0是数据库错误
                        return gson.toJson(map);
                    }
                    result = -1;
                    result = groupDiaryDao.sharePhotoToGroup(
                            String.valueOf(diary.getId()), groupPhotoId,
                            String.valueOf(1), String.valueOf(0));
                    if (result < 0) {
                        map.put("flag", "false");
                        map.put("errorCode", "0");// 0是数据库错误
                        return gson.toJson(map);
                    }
                }

            }
        }

        map.put("flag", "true");

        return gson.toJson(map);
    }

    @Override
    public String getDiaryLikeUser(String id, String password, String diaryId) {
        Map<String, String> map = new HashMap<String, String>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        // 判断随记是否是用户的
        UserDiary userDiary = userDiaryDao.checkExist(id, diaryId);

        if (null == userDiary || userDiary.getIsme() != 1) {
            map.put("flag", "false");
            map.put("errorCode", "-2");// -2随记不是用户的
            return gson.toJson(map);
        }

        List<UserDiary> userDiaries = userDiaryDao
                .getLikeItemsByDiaryId(diaryId);
        if (userDiaries.size() <= 0) {
            map.put("flag", "false");
            map.put("errorCode", "0");// 0没有点赞的人
            return gson.toJson(map);
        }
        List<Map<String, String>> maps = new ArrayList<Map<String, String>>();

        for (UserDiary userDiary2 : userDiaries) {
            Map<String, String> mapTemp = new HashMap<String, String>();

            UserInfo userInfo2 = userDao.getUserInfoById(userDiary2
                    .getUser_id());

            if (null != userInfo2) {
                mapTemp.put("id", String.valueOf(userInfo2.getId()));
                mapTemp.put("name", userInfo2.getLiteraryName());
                UserBaseInfo userBaseInfo = userBaseInfoDao.findUserBaseInfoById(String.valueOf(userInfo2.getId()));
                if (userBaseInfo != null && userBaseInfo.getSockpuppet() != null && !"".equals(userBaseInfo.getSockpuppet())) {
                    mapTemp.put("rebotName", userBaseInfo.getSockpuppet());
                } else {
                    mapTemp.put("rebotName", "萌喜鹊");
                }
                maps.add(mapTemp);
            }
        }

        map.put("flag", "true");
        map.put("likeUserList", gson.toJson(maps));
        return gson.toJson(map);
    }

    @Override
    public int getAllDiary() {
        return diaryDao.getAllDiary().size();
    }

    @Override
    public int getAllComment() {
        return userDiaryDao.getAllComment().size();
    }

    @Override
    public List<Map<String, Object>> getReportsInfo(int page) {
        /*
         * 作者：zjf 功能：分页
		 */
        int[] pageInfo = Page.getPageInfo(page, userDiaryDao.getAllReport()
                .size(), 10);
        // 分页查询
        List<UserDiary> uds = userDiaryDao.getReportAndPaging(pageInfo[0],
                pageInfo[1]);
        // 返回的list
        List<Map<String, Object>> reportsInfo = new ArrayList<Map<String, Object>>();
        for (UserDiary ud : uds) {
            Map<String, Object> reportInfo = new HashMap<String, Object>();
            try {
                reportInfo.put("diaryId", ud.getDiary_id());
                Diary diary = diaryDao.findDiaryById(ud.getDiary_id() + "");
                if (diary != null) {
                    reportInfo.put("content", diary.getContent());
                } else {
                    continue;
                }
                reportInfo.put("reason", ud.getReport());
                reportInfo.put("reportPersion",
                        userDao.getUserInfoById(ud.getUser_id()).getId());
                reportInfo
                        .put("publishPersion",
                                userDao.getUserInfoById(
                                        userDiaryDao.getDiaryBelongs(String.valueOf(diary.getId())).getUser_id()).getId());
                reportsInfo.add(reportInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reportsInfo;
    }

    @Override
    public List<Map<String, Object>> getSuggestionsInfo(int page) {
        int pageInfo[] = Page.getPageInfo(page, userDiaryDao.getAllSuggestion().size(),
                10);
        // 分页查询
        List<Suggestion> uds = userDiaryDao.getSuggestionsAndPaging(pageInfo[0],
                pageInfo[1]);
        // 返回的list
        List<Map<String, Object>> reportsInfo = new ArrayList<Map<String, Object>>();
        for (Suggestion ud : uds) {
            Map<String, Object> reportInfo = new HashMap<String, Object>();
            try {
                reportInfo.put("userId", ud.getUser_id());
                reportInfo.put("content", ud.getContent());
                reportInfo.put("date", ud.getReleasetime());
                reportsInfo.add(reportInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reportsInfo;
    }

    @Override
    public String addSuggestion(String id, String password, String Suggestion) {
        Map<String, String> map = new HashMap<String, String>();
        Diary d = diaryDao.addDiary(Suggestion, null, null);
        Suggestion s = userDiaryDao.addSuggestions(id, d.getId() + "");
        if (s != null) {
            map.put("flag", "true");
            return gson.toJson(map);
        }
        map.put("flag", "false");
        return gson.toJson(map);
    }

    @Override
    public Map<String, Integer> getPageInfo(int page) {
        int pageInfo[] = Page.getPageInfo(page, userDiaryDao.getAllReport().size(),
                10);
        int pagenum = pageInfo[2];
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("pagenum", pagenum);
        map.put("curpage", page);
        return map;
    }

    @Override
    public Map<String, Integer> getSuggestionPageInfo(int page) {
        int pageInfo[] = Page.getPageInfo(page, userDiaryDao.getAllSuggestion().size(),
                10);
        int pagenum = pageInfo[2];
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("pagenum", pagenum);
        map.put("curpage", page);
        return map;
    }

    @Override
    public String ignoreReport(String diaryId) {
        if (userDiaryDao.cancelReportDiary(diaryId) != 0) {
            return "忽略成功";
        } else {
            return "忽略失败";
        }
    }

    @Override
    public String deleteReport(String diaryId) {
        if (userDiaryDao.deleteItemByDiaryId(diaryId) != 0) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }

    @Override
    public String getMyPhoto(String id, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
        List<UserDiary> myPhotoDiaryList = userDiaryDao
                .getMyPhotoDiaryByUserId(id);
        if (null != myPhotoDiaryList && myPhotoDiaryList.size() > 0) {
            for (int i = 0; i < myPhotoDiaryList.size(); i++) {
                Diary diaryTemp = diaryDao.findDiaryById(String
                        .valueOf(myPhotoDiaryList.get(i).getDiary_id()));
                List<UserDiary> likeList = userDiaryDao
                        .getLikeItemsByDiaryId(String.valueOf(diaryTemp.getId()));
                UserDiary diaryBelongs = userDiaryDao.getDiaryBelongs(String
                        .valueOf(diaryTemp.getId()));

                if (null != diaryTemp && null != diaryBelongs) {
                    Map<String, String> mapTemp = new HashMap<String, String>();
                    mapTemp.put("diaryId", String.valueOf(diaryTemp.getId()));
                    mapTemp.put("content", diaryTemp.getContent());
                    UserInfo userInfo2 = userDao.findUserById(String
                            .valueOf(diaryBelongs.getUser_id()));
                    if (null != likeList) {
                        mapTemp.put("likeNum", String.valueOf(likeList.size()));
                    }
                    UserDiary userDiary = userDiaryDao.checkExist(id,
                            String.valueOf(diaryTemp.getId()));

                    if (null != userDiary) {

                        mapTemp.put("ilike",
                                String.valueOf(userDiary.getIlike()));

                    }
                    mapTemp.put("user_name", userInfo2.getName());
                    mapTemp.put("user_id",
                            String.valueOf(diaryBelongs.getUser_id()));
                    mapTemp.put("releasetime",
                            String.valueOf(diaryTemp.getReleasetime()));
                    mapTemp.put("photoNum",
                            String.valueOf(diaryTemp.getImage_num()));
                    maps.add(mapTemp);
                }
            }
        }

        map.put("flag", "true");
        map.put("myPhotoDiaryList", maps);
        return gson.toJson(map);
    }

    @Override
    public String getMyShareToFriendDiary(String id, String password,
                                          String start, String offset) {
        Map<String, String> map = new HashMap<String, String>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }
        List<UserDiary> userDiaries = userDiaryDao.getUserDiaryOffset(id,
                start, offset);
        if (userDiaries.size() <= 0) {
            map.put("flag", "false");
            map.put("errorCode", "-2");// -没有随记
            return gson.toJson(map);
        }

        List<Diary> diaryList = new ArrayList<Diary>();
        for (UserDiary userDiary : userDiaries) {
            if (userDiary.getIsme() == 1 || userDiary.getIsme() == 4) {
                Diary diary = diaryDao.findDiaryById(String.valueOf(userDiary
                        .getDiary_id()));
                if (null != diary) {
                    if (userDiary.getForward() == 1) {// 检查是否是转发
                        List<RemarkDiary> remarkFromDiary = remarkDiaryDao
                                .getItemsByRemarkId(String.valueOf(userDiary
                                        .getDiary_id()));
                        if (remarkFromDiary.size() > 0) {
                            // 获取到转发随机
                            Diary diary2 = diaryDao.findDiaryById(String
                                    .valueOf(remarkFromDiary.get(0)
                                            .getDiary_id()));

                            if (null != diary2) {
                                diary.setForwardFromDiary(diary2);
                                // 获取转发的随记的作者的信息
                                UserDiary userDiary2 = userDiaryDao
                                        .getDiaryBelongs(String.valueOf(diary2
                                                .getId()));
                                if (null != userDiary2) {
                                    UserInfo userInfo2 = userDao
                                            .findUserById(String
                                                    .valueOf(userDiary2
                                                            .getUser_id()));

                                    if (null != userInfo2) {
                                        Map<String, String> maptemp = new HashMap<String, String>();
                                        maptemp.put("name", userInfo2.getName());
                                        maptemp.put("literaryName",
                                                userInfo2.getLiteraryName());
                                        maptemp.put("gender", String
                                                .valueOf(userInfo2.getGender()));
                                        maptemp.put("id", String
                                                .valueOf(userInfo2.getId()));
                                        diary.setForwordFromInfo(maptemp);
                                        diaryList.add(diary);
                                    }
                                }
                            }

                        }
                    } else {
                        diaryList.add(diary);
                    }

                }
            }

        }

        map.put("flag", "true");
        map.put("diaryList", gson.toJson(diaryList));// 没有评论
        return gson.toJson(map);
    }

    @Override
    public String getLastThreePhoto(String id, String password, String targetId) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        List<UserDiary> diaryList = userDiaryDao.getDiaryByUserId(targetId);

        List<String> urls = new ArrayList<String>();
        for (int i = 0; i < diaryList.size(); i++) {
            Diary diary = diaryDao.findDiaryById(String.valueOf(diaryList
                    .get(i).getDiary_id()));
            for (int j = 0; j < diary.getImage_num(); j++) {
                if (urls.size() < 3) {
                    urls.add(diary.getId() + "/" + j);
                } else {
                    break;
                }
            }

        }

        map.put("flag", "true");
        map.put("urls", urls);
        return gson.toJson(map);
    }

    /*
    * T002
    * */
    @Override
    public String getLastFriendDiary(String id, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        // 2获取所有的朋友
        String ids = getFriendIds(id, true);


        // 3获取所有的diary
        UserDiary userDiary = new UserDiary();
        if (!ids.equals("")) {
            userDiary = userDiaryDao.getLastFriendDiary(ids);
        }

        map.put("flag", "true");
        if (null != userDiary) {
            map.put("user_id", String.valueOf(userDiary.getUser_id()));
        }

        return gson.toJson(map);

    }

    @Override
    public String checkFriendCircleUpdate(String id, String password,
                                          String diaryId) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        // 2获取所有的朋友
        String ids = getFriendIds(id, true);

        // 3获取所有的diary
        UserDiary userDiary = new UserDiary();
        if (!ids.equals("")) {
            userDiary = userDiaryDao.getLastFriendDiary(ids);
        }
        if (null != userDiary) {
            if (!diaryId.equals(String.valueOf(userDiary.getDiary_id()))) {
                map.put("flag", "true");// 需要更新

                return gson.toJson(map);
            }
        }
        map.put("flag", "false");

        return gson.toJson(map);

    }

    /**
     *
     * T002
     * 获取所有的朋友 工具函数
     */

    public String getFriendIds(String id, boolean flag) {
        List<UserRelation> allFriends=null;
        List<UserGroup> listuseridinSameGroup=null;
        // 亲友
        /*
        在关系表中的一对一关系
        * */
        allFriends = userRelationDao.getCurrentUserFriends(id);
        if (flag) {
            /*
            * 这里需要修改为加上在同在一个群里的关系
            * */
           listuseridinSameGroup=userGroupDao.getUseridfromUserGroup(id);
        }
        String ids = "";
        if (null != allFriends) {

            for (UserRelation allFriend : allFriends) {
                ids+=""+allFriend.getUser_id1()+",";
                ids+=""+allFriend.getUser_id2()+",";

            }
            if(null!=listuseridinSameGroup){
                for(UserGroup userGroup:listuseridinSameGroup){
                    ids+=""+userGroup.getUser_id()+",";
                }
            }
        }
        // 群聊好友
        if(ids.endsWith(",")){
            ids=ids.substring(0,ids.length()-1);
        }
        
        return ids;
    }

    @Override
    public String checkMyDiaryUpdate(String id, String password, String diaryId) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        List<UserDiary> myIDiaryList = userDiaryDao.getDiaryByUserId(id);
        if (null != myIDiaryList
                && myIDiaryList.size() > 0
                && !diaryId.equals(String.valueOf(myIDiaryList.get(0)
                .getDiary_id()))) {
            map.put("flag", "true");
            return gson.toJson(map);
        }

        map.put("flag", "false");

        return gson.toJson(map);
    }

    @Override
    public String checkCollectDiaryUpdate(String id, String password,
                                          String diaryId) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        List<UserDiary> myFavoriteDiaryList = userDiaryDao
                .getFavoriteDiaryByUserId(id);
        if (null != myFavoriteDiaryList
                && myFavoriteDiaryList.size() > 0
                && !diaryId.equals(String.valueOf(myFavoriteDiaryList.get(0)
                .getDiary_id()))) {
            map.put("flag", "true");
            return gson.toJson(map);
        }

        map.put("flag", "false");

        return gson.toJson(map);
    }

    @Override
    public String checkMyPhotoUpdate(String id, String password, String diaryId) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        List<UserDiary> myPhotoDiaryList = userDiaryDao
                .getMyPhotoDiaryByUserId(id);
        if (null != myPhotoDiaryList
                && myPhotoDiaryList.size() > 0
                && !diaryId.equals(String.valueOf(myPhotoDiaryList.get(0)
                .getDiary_id()))) {
            map.put("flag", "true");
            return gson.toJson(map);
        }

        map.put("flag", "false");

        return gson.toJson(map);
    }

    @Override
    public String checkGroupDiaryUpdate(String id, String password,
                                        String groupId, String diaryId) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        // List<UserDiary> myPhotoDiaryList =
        // userDiaryDao.getMyPhotoDiaryByUserId(id);
        List<GroupChatDiary> groupDiaryList = groupDiaryDao
                .getDiaryByGroupId(groupId);

        if (null != groupDiaryList
                && groupDiaryList.size() > 0
                && !diaryId.equals(String.valueOf(groupDiaryList.get(0)
                .getDiary_id()))) {
            map.put("flag", "true");
            return gson.toJson(map);
        }

        map.put("flag", "false");

        return gson.toJson(map);
    }

    @Override
    public String checkOfficialDiaryUpdate(String id, String password,
                                           String officialId, String diaryId) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        List<OfficialaccountDiary> officialDiaryList = officialDiaryDao
                .getOfficialDiarysByOfficialId(officialId);

        if (null != officialDiaryList
                && officialDiaryList.size() > 0
                && !diaryId.equals(String.valueOf(officialDiaryList.get(0)
                .getDiary_id()))) {
            map.put("flag", "true");
            return gson.toJson(map);
        }

        map.put("flag", "false");

        return gson.toJson(map);
    }

    @Override
    public String checkIslandDiaryUpdate(String id, String password,
                                         String islandId, String diaryId) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }

        List<IslandDiary> islandDiaryList = islandDiaryDao
                .getIslandDiarysByIslandId(islandId);

        if (null != islandDiaryList
                && islandDiaryList.size() > 0
                && !diaryId.equals(String.valueOf(islandDiaryList.get(0)
                .getDiary_id()))) {
            map.put("flag", "true");
            return gson.toJson(map);
        }

        map.put("flag", "false");

        return gson.toJson(map);
    }

    @Override
    public Map<String, Integer> getReportPageInfo(int page) {
        int pageInfo[] = Page.getPageInfo(page, userDiaryDao.getAllReport()
                .size(), 10);
        int pagenum = pageInfo[2];
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("pagenum", pagenum);
        map.put("curpage", page);
        return map;
    }

    @Override
    public String getIslandIdDiary(String id, String password, String islandId,
                                   String start, String offset, String m) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }
        // 1.5 验证该用户是否为该梦岛的拥有者
        UserIsland ui = userIslandDao.checkExistByUserAndIslandId(id, islandId);
        String mark = m;
        if (ui != null) {
            mark = "";
        }
        // 2 根据群id获取群的虽有的diaryId
        List<IslandDiary> islandDiarys = islandDiaryDao
                .getIslandDiarysByDiaryId(islandId, start, offset);
        List<Diary> diaryListTemp = new ArrayList<Diary>();
        for (IslandDiary islandDiary : islandDiarys) {
            Diary diary = diaryDao.findDiaryById(String.valueOf(islandDiary
                    .getDiary_id()));
            if (null == diary) {
                map.put("flag", "false");
                map.put("errorCode", "0");// 0数据库出错
                return gson.toJson(map);
            }
            diaryListTemp.add(diary);

        }

        List<UserIsland> islandUser = userIslandDao.getUserByIslandId(islandId);
        UserIsland userIsland = userIslandDao.checkExistByUserAndIslandId(id,
                islandId);

        List<Diary> diaryList = new ArrayList<Diary>();
        List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
        if (diaryListTemp.size() > 0) {
            getUserInfoAndDiaryInfo(id, diaryListTemp, diaryList, userList, mark);
            orderByRemarkDiary(diaryList, userList, mark);

        }

        map.put("flag", "true");
        if (null != islandUser) {
            map.put("islandUserNum", String.valueOf(islandUser.size()));
        } else {
            map.put("islandUserNum", "0");
        }
        if (null != userIsland) {
            map.put("belongTo", "1");
        } else {
            map.put("belongTo", "0");
        }
        map.put("diaryList", diaryList);
        map.put("userList", userList);
        return gson.toJson(map);
    }

    @Override
    public String getFavoriteDiary(String id, String password, String start,
                                   String offset) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1 验证用户密码是否正确
        UserInfo userInfo = userDao.checkUserById(id, password);
        if (null == userInfo) {
            map.put("flag", "false");
            map.put("errorCode", "-1");// -1用户不存在
            return gson.toJson(map);
        }
        // 2 现获取我同步到画轴的所有的随记的id
        List<UserDiary> userDiaryList = userDiaryDao
                .getFavoriteDiaryByUserId(id, start, offset);
        if (userDiaryList.size() <= 0) {
            map.put("flag", "false");
            map.put("errorCode", "-2");// -1用户不存在
            return gson.toJson(map);
        }
        // 获取所有的随记
        List<Diary> diaryList = new ArrayList<Diary>();
        for (UserDiary userDiary : userDiaryList) {
            if (null != userDiary && userDiary.getFavorite() == 1) {
                Diary diary = diaryDao.findDiaryById(String.valueOf(userDiary
                        .getDiary_id()));

                diaryList.add(diary);
            }

        }
        map.put("flag", "true");
        map.put("diaryList", diaryList);
        return gson.toJson(map);
    }

    @Override
    public int getAllStartDiary() {
        // TODO Auto-generated method stub
        return userDiaryDao.getAllStartDiary().size();
    }
}
