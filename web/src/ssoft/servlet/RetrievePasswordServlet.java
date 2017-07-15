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
import ssoft.service.UserService;

import com.google.gson.Gson;
/**
 * 找回面=密码
 * @author Administrator
 *
 */
public class RetrievePasswordServlet extends Servlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取服务
		UserService service = BasicFactory.getFactory().getService(
				UserService.class);

		// 设置请求的编码格式
		super.setEncoding(request,response);
		String content = request.getParameter("content");
		Map<String, String> mapContent = new HashMap<String, String>();
		Gson gson = new Gson();
		mapContent = gson.fromJson(content, Map.class);
		
	
		String phone = mapContent.get("phone");
		String countryCode = mapContent.get("countryCode");
		String smsCode = mapContent.get("smsCode");
		String qinyouName = mapContent.get("qinyouName");
		String jiatingName = mapContent.get("jiatingName");
		String jiazuName = mapContent.get("jiazuName");
		String newPassword = mapContent.get("newPassword");
		
		String result = service.retrievePassword(phone,countryCode,smsCode,qinyouName,jiatingName,jiazuName,newPassword);
		
		response.getWriter().write(result);
		
		//手机存在的话就
	
		

	}

}
