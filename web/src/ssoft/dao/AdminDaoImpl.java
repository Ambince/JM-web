package ssoft.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import ssoft.domain.Admin;
import ssoft.utils.MD5Utils;
import ssoft.utils.TransactionManager;

public class AdminDaoImpl implements AdminDao {

	@Override
	public Admin getAdmin(String username, String password) {
		// TODO Auto-generated method stub
		String sql = "select * from admin where username=? and password = ?";
		try {
			QueryRunner qr = new QueryRunner(TransactionManager.getSource());
			return qr.query(sql, new BeanHandler<Admin>(Admin.class), username,
					password);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addAdmin(Admin admin) {
		String sql = "insert into admin values(null,?,?)";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql, admin.getUsername(), admin.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public void delAdmin(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Admin findUserByName(String username) {
		// TODO Auto-generated method stub
		String sql = "select * from admin where username = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<Admin>(Admin.class),
					username);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void modifyAdminPassword(String id, String newpassword) {
		// TODO Auto-generated method stub
		String sql = "update admin set password = ? where id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql, newpassword, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
