package serviceImpl;

import io.rong.util.GsonUtil;
import org.junit.Before;
import org.junit.Test;
import ssoft.dao.*;
import ssoft.domain.*;
import ssoft.factory.BasicFactory;
import ssoft.service.DiaryService;
import ssoft.service.DiaryServiceImpl;
import ssoft.service.OfficialaccountService;

import java.util.List;


/**
 * Created by hewen on 2017/6/14.
 */

public class DiaryServiceImplTest {

    @Before
    public void before(){}

    @Test
    public void testgetFrients(){
        // 获取服务
        DiaryService service = BasicFactory.getFactory().getService(
                DiaryService.class);
        String result = service.getLastFriendDiary("10000", "c10f614f0dfce12c698f9c81494d67d8");
        System.out.println(result);
    }
    @Test
    public void testGetgetCurrentUserFriends(){
        UserRelationDao dao = BasicFactory.getFactory().getDao(UserRelationDao.class);
        List<UserRelation> currentUserFriends = dao.getCurrentUserFriends("10003");
        System.out.println(GsonUtil.toJson(currentUserFriends,List.class));
    }
    @Test
    public void getUseridfromUserGroup(){
        User_GroupDao dao = BasicFactory.getFactory().getDao(User_GroupDao.class);
        List<UserGroup> currentUserFriends = dao.getUseridfromUserGroup("10001");
        System.out.println(currentUserFriends.size());
        System.out.println(GsonUtil.toJson(currentUserFriends,List.class));
    }
    @Test
    public void testgetFriends(){
        DiaryServiceImpl s=new DiaryServiceImpl();
        String friendIds = s.getFriendIds("10003", true);
        System.out.println(friendIds);
        UserDiaryDao dao = BasicFactory.getFactory().getDao(UserDiaryDao.class);

        List<UserDiary> friendCircleDiary = dao.getFriendCircleDiary(friendIds, String.valueOf(0), String.valueOf(10));
        System.out.println(GsonUtil.toJson(friendCircleDiary,List.class));

    }
    @Test
    public void testUserOffcicalcocountDao(){
        User_OfficialaccountDao dao = BasicFactory.getFactory().getDao(User_OfficialaccountDao.class);
        List<Officialaccounts> communityAndPaging = dao.getCommunityAndPaging(0, 10);
        System.out.println(GsonUtil.toJson(communityAndPaging,List.class));
    }
    @Test
    public void testgetFriendCircleDiary(){
        DiaryService service = BasicFactory.getFactory().getService(DiaryService.class);
        String friendCircleDiary = service.getFriendCircleDiary("10003", "0be5184dd18dfc4254fc9629fce0163f", "0", "5", "0");
        System.out.println(friendCircleDiary);
    }
    @Test
    public void testaddRemark(){
        RemarkDiaryDao service = BasicFactory.getFactory().getDao(RemarkDiaryDao.class);
        RemarkDiary remarkDiary = service.addRemark("5", "16", "1");
        System.out.println(remarkDiary);
    }
    @Test
    public void testaddFrind(){
        UserRelationDao service = BasicFactory.getFactory().getDao(UserRelationDao.class);
        int i = service.addFrind("10003", "10071", "哥哥", "傻逼", "添加我吧");
        System.out.println(i);
    }
    @Test
    public void testaddRemarkRelation(){
        RemarkDiaryDao service = BasicFactory.getFactory().getDao(RemarkDiaryDao.class);
        List<RemarkDiary> itemsByDiaryId = service.getItemsByDiaryId("16", "1");
        System.out.println(GsonUtil.toJson(itemsByDiaryId,List.class));
        List<RemarkDiary> itemsByDiaryId2 = service.getItemsByDiaryId("16", "0");
        System.out.println(GsonUtil.toJson(itemsByDiaryId2,List.class));
        List<RemarkDiary> itemsByRemarkId = service.getItemsByRemarkId("43");
        System.out.println(GsonUtil.toJson(itemsByRemarkId,List.class));
    }
    @Test
    public void testcreateOfficialaccount(){
        OfficialaccountService service = BasicFactory.getFactory().getService(OfficialaccountService.class);
        String officialaccount = service.createOfficialaccount("10003", "0be5184dd18dfc4254fc9629fce0163f", "家园小区", "1", "没有未知", "1");
        System.out.println(officialaccount);
    }
    @Test
    public void testgetDiaryRemark(){
        DiaryService service = BasicFactory.getFactory().getService(DiaryService.class);
        String diaryRemark = service.getDiaryRemark("10003", "0be5184dd18dfc4254fc9629fce0163f", "275", "0");
        System.out.println(diaryRemark);
    }
    @Test
    public void testgetDiaryRemarkList(){
        DiaryServiceImpl   d=new DiaryServiceImpl();
        List<Diary> diaryRemarkList = d.getDiaryRemarkList("275", "0");
        System.out.println(GsonUtil.toJson(diaryRemarkList,List.class));
        System.out.println(diaryRemarkList.size());
    }
    @Test
    public void testCreateOfficalacountservice(){
        OfficialaccountService service = BasicFactory.getFactory().getService(OfficialaccountService.class);
        long l = System.currentTimeMillis();
        String test = service.createOfficialaccount("10003", "0be5184dd18dfc4254fc9629fce0163f", "这是一个测试", "1", "123", "1");
        System.out.println(System.currentTimeMillis()-l);
        System.out.println(test);
    }
    @Test
    public void testIslandDao(){
        IslandDao dao = BasicFactory.getFactory().getDao(IslandDao.class);
        List<Island> islandAndPaging = dao.getIslandAndPaging(0, 20);
        System.out.println(GsonUtil.toJson(islandAndPaging,List.class));
    }

    @Test
    public void testGetOfficialaccountDiaryServlet(){
        DiaryService service = BasicFactory.getFactory().getService(
                DiaryService.class);
        String result = service.getOfficialaccountDiary("1003", "0be5184dd18dfc4254fc9629fce0163f", "12","0","10","0");
        System.out.println(result);

    }
}
