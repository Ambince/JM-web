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

import com.google.gson.Gson;

public class TransmitDiaryServlet extends Servlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

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
		String diaryId = mapContent.get("diaryId");
		String forward = mapContent.get("forward");//给转发的随记的评论
		String myPhoto = mapContent.get("myPhoto");
		String friends = mapContent.get("friends");
		String groupIds = mapContent.get("groupIds");
		String officialIds = mapContent.get("officialIds");
		String islandIds = mapContent.get("islandIds");
		String groupphotoIds = mapContent.get("groupphotoIds");

		String result = service.transmitDiary(id, password, diaryId,forward,myPhoto,friends,groupIds,officialIds,islandIds,groupphotoIds);
		response.getWriter().write(result);
	}

}
