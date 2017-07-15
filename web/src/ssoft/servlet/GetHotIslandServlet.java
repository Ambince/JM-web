package ssoft.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ssoft.domain.Island;
import ssoft.factory.BasicFactory;
import ssoft.service.DiaryService;
import ssoft.service.IslandService;

import com.google.gson.Gson;
/**
 * 获取推荐的萌岛
 * @author Administrator
 *
 */
public class GetHotIslandServlet extends Servlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//get用于测试
		// 获取服务
		IslandService service = BasicFactory.getFactory().getService(
				IslandService.class);

		// 设置请求的编码格式
		super.setEncoding(request,response);
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String start = request.getParameter("start");
		String offset = request.getParameter("offset");

		String result = service.getHotIsland(id, password, start, offset);
		response.getWriter().write(result);

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
		String start = mapContent.get("start");
		String offset = mapContent.get("offset");

		String result = service.getHotIsland(id, password, start, offset);
		System.out.println(result);
		response.getWriter().write(result);
	}

}
