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
import ssoft.service.MessageSoundService;

import com.google.gson.Gson;
/**
 * 获取声音消息提醒
 * @author Administrator
 *
 */
public class GetMessageSoundServlet extends Servlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取服务
		MessageSoundService service = BasicFactory.getFactory().getService(
				MessageSoundService.class);
		// 设置请求的编码格式
		super.setEncoding(request,response);
		String content = request.getParameter("content");
		Map<String, String> mapContent = new HashMap<String, String>();
		Gson gson = new Gson();
		mapContent = gson.fromJson(content, Map.class);
		String id = mapContent.get("id");
		String password = mapContent.get("password");
		String type = mapContent.get("type");
		String targetId = mapContent.get("targetId");
		String result = service.getSet(id, password, type, targetId);
		response.getWriter().write(result);
	}

}
