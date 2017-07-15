package ssoft.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import ssoft.factory.BasicFactory;
import ssoft.service.UserBaseInfoService;
import ssoft.service.UserRelationService;

public class SetFriendInfoServlet extends Servlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取服务
		UserRelationService userRelationService = BasicFactory.getFactory().getService(
				UserRelationService.class);

		// 设置请求的编码格式
		super.setEncoding(request,response);
		String content = request.getParameter("content");
		if (null == content) {
			return;
		}
		Map<String, String> mapContent = new HashMap<String, String>();
		Gson gson = new Gson();
		mapContent = gson.fromJson(content, Map.class);
		String userid = mapContent.get("id");
		String password = mapContent.get("password");
		String friendId = mapContent.get("friendId");
		//备注
		String remark = mapContent.get("remark");
		//称呼
		String call = mapContent.get("call");
		//是否提醒消息
		String message_sound = mapContent.get("message_sound");
		
		String result = userRelationService.setFriendInfo(userid,password,friendId,remark,call,message_sound);
		response.getWriter().write(result);
	}

}
