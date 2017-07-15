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
import ssoft.service.GroupService;

import com.google.gson.Gson;

public class GetNationPublicInfoServlet extends Servlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取服务
		GroupService service = BasicFactory.getFactory().getService(
				GroupService.class);

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

		String groupId = mapContent.get("groupId");
		String name = mapContent.get("name");
		String params = "";
		if (groupId == null || groupId == "") {
			params = name;
		}else{
			params = groupId;
		}
		String result = service.getNativePublicInfo(id, password, params);
		response.getWriter().write(result);

	}

}
