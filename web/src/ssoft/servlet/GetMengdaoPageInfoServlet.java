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
import ssoft.service.IslandService;

import com.google.gson.Gson;

/**
 * 获取萌岛页的信息包括热点萌岛 我的萌岛 最萌贝壳
 * 
 * @author Administrator
 * 
 */
public class GetMengdaoPageInfoServlet extends Servlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取服务
		IslandService service = BasicFactory.getFactory().getService(
				IslandService.class);

		// 设置请求的编码格式
		super.setEncoding(request,response);
		String content = request.getParameter("content");
		if (null == content) {
			return;
		}
		Map<String, String> mapContent = new HashMap<String, String>();
		Gson gson = new Gson();
		mapContent = gson.fromJson(content, Map.class);
		String id = mapContent.get("id");
		String password = mapContent.get("password");
		String mark = "0";//获取公开的评论

		String result = service.getMengdaoPageInfo(id, password,mark);
		response.getWriter().write(result);
	}

}
