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
 * 评论随机
 * @author Administrator
 *
 */
public class RemarkDiaryServlet extends Servlet {

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
		String diaryId  = mapContent.get("diaryId");
		String remarkText = mapContent.get("remarkText");
		String remarkImgUrl = mapContent.get("remarkImgUrl");
		String remarkImgNum = mapContent.get("remarkImgNum");
		String mark = mapContent.get("mark");   //是否是隐私的

		String result = service.reamarkDiary(id, password, diaryId, remarkText,
				remarkImgUrl, remarkImgNum, mark);
		response.getWriter().write(result);

	}

}
