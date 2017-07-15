package ssoft.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ssoft.factory.BasicFactory;
import ssoft.global.Global;
import ssoft.service.UserService;

import com.google.gson.Gson;
import com.mob.sms.spi.SmsVerifyKit;
/**
 * 短信验证
 * @author Administrator
 *
 */
public class SmsVerifyServlet extends Servlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	

		// 设置请求的编码格式
		super.setEncoding(request,response);
		String content = request.getParameter("content");
		Map<String, String> mapContent = new HashMap<String, String>();
		Gson gson = new Gson();
		mapContent = gson.fromJson(content, Map.class);
		// 先判断短信验证码
		try {
			String result = "";
			result = new SmsVerifyKit(Global.Mob_App_Key, mapContent.get("phone"),
					mapContent.get("countryCode"), mapContent.get("smsCode")).go();
			System.out.println(result);
			Map<String, Double> map0 = new HashMap<String, Double>();

			map0 = gson.fromJson(result, Map.class);
			System.out.println(map0.get("status"));
			if (map0.get("status") == 200) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("flag", "true");
				map.put("errorCode", map0.get("status"));
				response.getWriter().write(gson.toJson(map));
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("flag", "false");
				map.put("errorCode", map0.get("status"));
				response.getWriter().write(gson.toJson(map));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
