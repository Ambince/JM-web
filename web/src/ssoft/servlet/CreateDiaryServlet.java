package ssoft.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ssoft.factory.BasicFactory;
import ssoft.service.DiaryService;
import ssoft.service.GroupMemberService;

import com.google.gson.Gson;

/**
 * 添加随记接口
 * @author Administrator
 *
 */
public class CreateDiaryServlet extends Servlet {

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
		String diaryText = mapContent.get("diaryText");
		String imgUrl = mapContent.get("imgUrl");
		String imgNum = mapContent.get("imgNum");
		String myPhoto = mapContent.get("myPhoto");
		String groupPhotoIds = mapContent.get("groupPhotoIds");
		String friends = mapContent.get("friends");
		String groupIds = mapContent.get("groupIds");
		String OfficialIds = mapContent.get("OfficialIds");
		String islandIds = mapContent.get("islandIds");
		
		String result = service.createDiary(id,password,diaryText,imgUrl,imgNum,friends,groupIds,OfficialIds,islandIds,myPhoto,groupPhotoIds);
		response.getWriter().write(result);
	
	}

}
