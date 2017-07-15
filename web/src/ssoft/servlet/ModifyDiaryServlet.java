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
/**
 * 修改随记
 * @author Administrator
 *
 */
public class ModifyDiaryServlet extends Servlet {

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
		String diaryText = mapContent.get("diaryText");
		String imgUrl = mapContent.get("imgUrl");
		String imgNum = mapContent.get("imgNum");
		String myPhoto = mapContent.get("myPhoto");
		String friends = mapContent.get("friends");
		String groupIds = mapContent.get("groupIds");
		String OfficialIds = mapContent.get("OfficialIds");
		String groupPhotoIds = mapContent.get("groupPhotoIds");
		String islandIds = mapContent.get("islandIds");

		String result = service.modifyDiary(id, password,diaryId, diaryText, imgUrl,
				imgNum, friends, groupIds, OfficialIds, islandIds, myPhoto,groupPhotoIds);
		response.getWriter().write(result);
	}

}
