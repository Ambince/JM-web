package ssoft.dao;



import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mysql.jdbc.log.Log;

import ssoft.domain.UserInfo;
import ssoft.utils.MD5Utils;
import ssoft.utils.TransactionManager;

public class UserDaoImpl implements UserDao {

	@Override
	public UserInfo findUserByPhone(String phone) {
		// TODO Auto-generated method stub
		String sql = "select * from users where phone = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<UserInfo>(UserInfo.class),phone);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public int register(UserInfo user) {
		// TODO Auto-generated method stub
		String sql = "insert into users values(null,?,?,?,?,?,?,null,?,?,?,?)";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,user.getName(),user.getLiteraryName(),user.getGender(),user.getCountryCode(),user.getPhone(),MD5Utils.md5(user.getPassword()),sdf.format(new Date(System.currentTimeMillis())),sdf.format(new Date(System.currentTimeMillis())),user.getPassword().length(),sdf.format(new Date(System.currentTimeMillis())));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	




	


	@Override
	public UserInfo checkUserById(String id, String password) {
		// TODO Auto-generated method stub
		String sql = "select * from users where id = ? and password = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<UserInfo>(UserInfo.class),id,password);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	
	}


	@Override
	public UserInfo checkUserByPhone(String phone, String password) {
		// TODO Auto-generated method stub
		String sql = "select * from users where phone = ? and password = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<UserInfo>(UserInfo.class),phone,password);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	
	}


	@Override
	public UserInfo checkUserIsExist(String key) {
		// TODO Auto-generated method stub
		String sql = "select * from users where phone = ? or id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<UserInfo>(UserInfo.class),key,key);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}


	@Override
	public int unBindPhone(String phone) {
		// TODO Auto-generated method stub
		String sql = "delete from users where phone = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,phone);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public int updateName(String user_id, String name, String literaryname) {
		// TODO Auto-generated method stub
		String sql = "update users set name = ? ,literaryname = ? where id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,name,literaryname,user_id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserInfo getUserInfoById(int user_id) {
		// TODO Auto-generated method stub
		String sql = "select * from users where id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<UserInfo>(UserInfo.class), user_id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public int saveToken(String token,String id) {
		// TODO Auto-generated method stub
		String sql = "update users set token = ?  where id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,token,id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserInfo findUserById(String Id) {
		// TODO Auto-generated method stub
		String sql = "select * from users where id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<UserInfo>(UserInfo.class),Id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public List<UserInfo> findUserByName(String qinyouName) {
		String sql = "select * from users where name = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserInfo>(UserInfo.class),qinyouName);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public int resetPassword(String id, String newPassword) {
		String sql = "update users set password = ?,passwordlength = ?  where id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,MD5Utils.md5(newPassword),newPassword.length(),id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public List<UserInfo> getAllUser() {
		String sql = "select * from users";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserInfo>(UserInfo.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserInfo> getAllFemaleUser() {
		String sql = "select * from users where gender = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserInfo>(UserInfo.class), 0);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserInfo> getAllMaleUser() {
		String sql = "select * from users where gender = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserInfo>(UserInfo.class), 1);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	@Override
	public List<UserInfo> getTodayUser() {
		String sql = "select * from users where DATE_FORMAT(lastlogintime, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d')";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserInfo>(UserInfo.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<UserInfo> getMouthUser() {
		String sql = "select * from users where DATE_FORMAT(lastlogintime, '%Y-%m-%d') <= DATE_FORMAT(now(), '%Y-%m-%d') and DATE_FORMAT(lastlogintime, '%Y-%m-%d') >= DATE_FORMAT(date_add(now(),INTERVAL -30 day), '%Y-%m-%d')";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserInfo>(UserInfo.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserInfo> getUserAndPaging(int start, int perPage) {
//		String sql = "select * from users order by registertime desc limit ?, ?";
		String sql = "select * from users order by id desc limit ?, ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<UserInfo>(UserInfo.class), start, perPage);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updateLoginTime(String id) {
		String sql = "update users set lastlogintime = ? where id = ?";
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,sdf.format(new Date(System.currentTimeMillis())),id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updatePhoneToEmpty(String phone, String user_id) {
		String sql = "update users set phone = ? where id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.update(sql,"",user_id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserInfo getUserInfoByPhone(String phone) {
		String sql = "select * from users where phone = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<UserInfo>(UserInfo.class),phone);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	



}
