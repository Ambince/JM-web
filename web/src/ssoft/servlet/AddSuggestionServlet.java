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
import ssoft.service.DiaryService;
import ssoft.service.UserRelationService;

import com.google.gson.Gson;
/**
 * 添加意见
 * @author Administrator
 *
 */
public class AddSuggestionServlet extends Servlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//get方法用于测试
		// 获取服务
		DiaryService service = BasicFactory.getFactory().getService(
				DiaryService.class);
		// 设置请求的编码格式
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String suggestion = request.getParameter("suggestion");
		String result = service.addSuggestion(id,password,suggestion);
		response.getWriter().write(result);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取服务
		DiaryService service = BasicFactory.getFactory().getService(
				DiaryService.class);

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
		String suggestion = mapContent.get("suggestion");
		String result = service.addSuggestion(id,password,suggestion);
		response.getWriter().write(result);
	}

}
