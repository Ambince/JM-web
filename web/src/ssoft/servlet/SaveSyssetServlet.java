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
import ssoft.service.UserBaseInfoService;

import com.google.gson.Gson;
/**
 * 保存系统信息
 * @author Administrator
 *
 */
public class SaveSyssetServlet extends Servlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取服务
		UserBaseInfoService service = BasicFactory.getFactory().getService(
				UserBaseInfoService.class);

		// 设置请求的编码格式
		super.setEncoding(request,response);
		String content = request.getParameter("content");
		if (null == content) {
			return;
		}
		Map<String, String> mapContent = new HashMap<String, String>();
		Gson gson = new Gson();
		mapContent = gson.fromJson(content, Map.class);
		String user_id = mapContent.get("id");
		String password = mapContent.get("password");
		String soundplay_mode = mapContent.get("soundplay_mode");
		String message_sound = mapContent.get("message_sound");
		String message_shake = mapContent.get("message_shake");
		String result = service.saveSysset(user_id,password,soundplay_mode,message_sound,message_shake);
		response.getWriter().write(result);

	}

}
