package ssoft.servlet;

import com.google.gson.Gson;
import ssoft.factory.BasicFactory;
import ssoft.service.DiaryService;
import ssoft.service.IslandService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取萌岛的随记
 * @author Administrator
 *
 */
public class GetAllIslandServlet extends Servlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取服务
		IslandService service = BasicFactory.getFactory().getService(
				IslandService.class);
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
		int page = Integer.parseInt(mapContent.get("start"));

		String result = service.getIslandsInfo(id, password,page);
		response.getWriter().write(result);

	}

}
