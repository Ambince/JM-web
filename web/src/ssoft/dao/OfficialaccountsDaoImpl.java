package ssoft.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ssoft.domain.Officialaccounts;
import ssoft.utils.TransactionManager;

public class OfficialaccountsDaoImpl implements OfficialaccountsDao {

	

	@Override
	public Officialaccounts getOfficialaccountById(int officialaccount_id) {
		// TODO Auto-generated method stub
		String sql = "select * from officialaccounts where id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<Officialaccounts>(
					Officialaccounts.class), officialaccount_id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Officialaccounts createOfficialaccount(String officialaccountName,
												  String coordinate,String type, String userid) {
		String sql = "insert into officialaccounts values(null,?,?,?,?,null,?)";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		
			runner.update(sql,officialaccountName,coordinate,type,sdf.format(new Date(System.currentTimeMillis())),userid);
		
			sql = "select * from officialaccounts where id = (select max(id) from officialaccounts)";
		return runner.query(sql, new BeanHandler<Officialaccounts>(Officialaccounts.class));
		
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Officialaccounts> getAllOfficial() {
		String sql = "select * from officialaccounts";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<Officialaccounts>(
					Officialaccounts.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
