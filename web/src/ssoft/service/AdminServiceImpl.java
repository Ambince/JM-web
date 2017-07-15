package ssoft.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssoft.dao.AdminDao;
import ssoft.domain.Admin;
import ssoft.factory.BasicFactory;

public class AdminServiceImpl implements AdminService{
	
	private AdminDao ad = BasicFactory.getFactory().getDao(AdminDao.class);
	
	@Override
	public Admin getAdminByNameAndPsw(String username, String password) {
		// TODO Auto-generated method stub
		return ad.getAdmin(username, password);
	}

	@Override
	public String regist(Admin admin) {
		// TODO Auto-generated method stub
		try{
			//1.校验用户名是否已经存在
			if(ad.findUserByName(admin.getUsername())!=null){
				return "用户名已经存在";
			}
			//2.调用ad中的方法添加用户到数据库
			ad.addAdmin(admin);
			return "添加成功";
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public String modifyPassword(String username, String oldpassword, String newpassword) {
		// TODO Auto-generated method stub
		Admin admin = ad.getAdmin(username, oldpassword);
		if(admin != null){
			ad.modifyAdminPassword(admin.getId()+"", newpassword);
			return "修改密码成功";
		}else{
			return "旧密码输入错误";
		}
	}
}
