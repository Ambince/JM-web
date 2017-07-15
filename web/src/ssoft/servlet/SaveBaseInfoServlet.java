package ssoft.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ssoft.domain.UserBaseInfo;
import ssoft.factory.BasicFactory;
import ssoft.service.UserBaseInfoService;

import com.google.gson.Gson;
/**
 * 保存基本信息
 * @author Administrator
 *
 */
public class SaveBaseInfoServlet extends Servlet {

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
		String name = mapContent.get("name");
		String literaryname = mapContent.get("literaryname");
		String sockpuppet = mapContent.get("sockpuppet");  //马甲
		String strangerfind = mapContent.get("strangerfind");
//		String saveBaseInfo(String user_id, String password, String name,
//				String literaryname,String realname, String strangerfind, String birthday,
//				String liveplace, String nativeplace, String userintroduce,
//				String skill ,String sockpuppet);
		System.out.println(strangerfind+"=====");
		String result = service.saveBaseInfo(user_id, password, name, literaryname,
				 "0", strangerfind, "0",
				"0", "0", "0", "0",sockpuppet);
		response.getWriter().write(result);

	}

}
