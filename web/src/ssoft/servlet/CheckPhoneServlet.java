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
 * 检查手机是否存在
 * @author Administrator
 *
 */
public class CheckPhoneServlet extends Servlet {

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
		System.out.println(content);
		Map<String, String> mapContent = new HashMap<String, String>();
		Gson gson = new Gson();
		mapContent = gson.fromJson(content, Map.class);
		String phone = mapContent.get("phone");
		
		boolean result = service.checkPhone(phone);
		Map<String, Object> map = new HashMap<String, Object>();
		if (result) {
			map.put("flag", "true");
		}else{
			map.put("flag", "false");
		}
		
	
		response.getWriter().write(gson.toJson(map));
		
	
		
	}

}
