package ssoft.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.mchange.v2.c3p0.stmt.GooGooStatementCache;
import com.mob.sms.spi.SmsVerifyKit;
import com.mob.sms.spi.SmsWebApiKit;


import ssoft.domain.UserInfo;
import ssoft.factory.BasicFactory;
import ssoft.global.Global;
import ssoft.service.UserService;
/**
 * 注册接口
 * @author Administrator
 *
 */
public class RegisterServlet extends Servlet {
	
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		// 获取服务
		UserService service = BasicFactory.getFactory().getService(
				UserService.class);

		//设置请求的编码格式
		super.setEncoding(request,response);
		
		// 创建javaBean
		UserInfo user = new UserInfo();

	
		String content = request.getParameter("content");
		System.out.println(content);
		Gson gson = new Gson();
		
		user = gson.fromJson(content, UserInfo.class);
		
		if (null == user) {
			
			return;
			
		}
		
//		//不验证短信，立即注册
//		String result  = service.register(user);
//		System.out.println(result);
//		response.getWriter().write(result);
//		
		
		
		//先判断短信验证码
		
		try {
			String result ="";
			System.out.println(user.getPhone());
			System.out.println(user.getCountryCode());
			System.out.println(user.getSmsCode());
			//result = new SmsWebApiKit(Global.Mob_App_Key).checkcode(user.getPhone(), user.getCountryCode(),user.getSmsCode());
			result= new SmsVerifyKit(Global.Mob_App_Key, user.getPhone(),user.getCountryCode(),user.getSmsCode()).go();
			System.out.println(result);
			Map<String, Double> map0 = new HashMap<String, Double>();
			
			if (null == result) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("flag", "false");
				map.put("errorCode", String.valueOf(-1));   //-1未获得验证码
				response.getWriter().write(gson.toJson(map));
			}
			map0 = gson.fromJson(result, Map.class);
			System.out.println(map0.get("status"));
			if(map0.get("status") == 200){
				result  = "";
				System.out.println("=====");
				//注册用户
				result  = service.register(user);
				response.getWriter().write(result);
			}else{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("flag", "false");
				map.put("errorCode",String.valueOf(Global.SMS_ERROR));
				response.getWriter().write(gson.toJson(map));
			}
	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
