package ssoft.servlet;

import java.io.IOException;
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
 * 检查手机绑定状态
 * @author Administrator
 *
 */
public class CheckBindServlet extends Servlet {

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
		Map<String, String> mapCheck = new HashMap<String, String>();
		Gson gson = new Gson();
		mapCheck = gson.fromJson(content, Map.class);
		//请求检查时检查
		if (null != mapCheck.get("phone")) {
			int day = service.getBindTimeByPhone(mapCheck.get("phone"));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("day", String.valueOf(day));
			System.out.println(day+"");
			response.getWriter().write(gson.toJson(map));
		}else{
			return;
		}

	}



}
