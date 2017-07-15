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
 * 登陆接口
 * @author Administrator
 *
 */
public class LoginServlet extends Servlet {

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
		if (null == content) {
			return;
		}
		Map<String, String> mapContent = new HashMap<String, String>();
		Gson gson = new Gson();
		mapContent = gson.fromJson(content, Map.class);

		String number = mapContent.get("number");
		String password = mapContent.get("password");
		String type = mapContent.get("type");
		if (null == number || null == password || null == type) {
			return;
		}

		if (type.equals("id")) {
			String result = service.checkLoginById(number, password);
			System.out.println("==id==");
			response.getWriter().write(result);
		} else if (type.equals("phone")) {
			String result = service.checkLoginByPhone(number, password);
			System.out.println("==phone==");
			response.getWriter().write(result);
		}

	}

}
