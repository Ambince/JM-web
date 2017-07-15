package ssoft.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ssoft.domain.UserBaseInfo;
import ssoft.domain.UserRelation;
import ssoft.utils.MD5Utils;
import ssoft.utils.TransactionManager;

public class UserRelationDaoImpl implements UserRelationDao {

    @Override
    public List<UserRelation> getAllFriends(String id) {
        String sql = "select * from userrelation where user_id1 = ? or user_id2 = ?";
        return doQuery(id,sql);
    }

    private List<UserRelation> doQuery(String id , String sql){
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.query(sql, new BeanListHandler<UserRelation>(
                    UserRelation.class), id, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
    }

    @Override
    public List<UserRelation> getCurrentUserFriends(String id) {
        String sql = "select * from userrelation where user_id1 = ?  and agree1to2=1 and agree2to1=1 or user_id2 = ?";
        return doQuery(id,sql);
    }

    @Override
    public UserRelation checkExist(String id, String friendId) {
        // TODO Auto-generated method stub
        String sql = "select * from userrelation where (user_id1 = ? and user_id2 = ?) or ( user_id1 = ? and user_id2 = ?)";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.query(sql, new BeanHandler<UserRelation>(
                    UserRelation.class), id, friendId, friendId, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
    }

    @Override
    public int addFrind(String id, String friendId) {
        String sql = "insert into userrelation values(null,?,?,null,null,null,null,null,null,1,1,null,null)";
        try {
            if(getbyuserid1(id,friendId).size()!=0) return 1;
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.update(sql, id, friendId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addFrind(String id, String friendId, String call, String remark, String message) {
        String sql = "insert into userrelation values(null,?,?,?,null,?,null,null,null,?,?,?,null)";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if(getbyuserid1(id,friendId).size()!=0) return 1;
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.update(sql, id, friendId, call, remark, 1, 0, 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public List<UserRelation> getbyuserid1(String id,String friendId){
        String sql="select * from userrelation where user_id1=? and user_id2=?";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.query(sql, new BeanListHandler<UserRelation>(
                    UserRelation.class), id, friendId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
    }

    @Override
    public int confirmFriends(String id, String friendId, String call, String remark) {
        String sql = "update userrelation set call2to1 = ?, remark2to1 = ?,agree2to1 = ? where  user_id1 = ? and user_id2 = ?";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            int result = runner.update(sql, call, remark, 1, friendId, id);
//			if (result < 0) {
//				sql = "update userrelation set call2to1 = ?, remark2to1 = ? where  user_id1 = ? and user_id2 = ?";
//				return runner.update(sql,call,remark,friendId,id);
//			}
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteItemByUserId(int id) {
        String sql = "delete from userrelation where user_id1 = ? or user_id2 = ?";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.update(sql, id, id);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
    }


    @Override
    public int setFriendInfo1To2(String userid, String friendid, String call,
                                 String remark, String message_sound) {
        String sql = "update userrelation set call1to2 = ?, remark1to2 = ?,message_sound1to2 = ? where  user_id1 = ? and user_id2 = ?";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            int result = runner.update(sql, call, remark, message_sound, userid, friendid);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int setFriendInfo2To1(String userid, String friendid, String call,
                                 String remark, String message_sound) {
        String sql = "update userrelation set call2to1 = ?, remark2to1 = ?,message_sound2to1 = ? where  user_id2 = ? and user_id1 = ?";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            int result = runner.update(sql, call, remark, message_sound, userid, friendid);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteFriendById(String id, String friendId) {
        String sql = "delete from userrelation where (user_id1 = ? and user_id2 = ?) or (user_id1 = ? and user_id2 = ?)";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            int result = runner.update(sql, id, friendId, friendId, id);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
