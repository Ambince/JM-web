package ssoft.servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ssoft.factory.BasicFactory;
import ssoft.service.DiaryService;
import ssoft.service.IslandService;

public class IslandServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IslandService is = BasicFactory.getFactory().getService(IslandService.class);
		// 设置请求的编码格式
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//获取类型
		String type = request.getParameter("type");
		if ("recommend".equals(type)) {//忽略举报
			String id = request.getParameter("id");
			String message = is.recommendIsland(id);
			request.setAttribute("message", message);
			request.setAttribute("url", "AdminServlet?type=islandList");
			request.setAttribute("wait", "3");
			request.getRequestDispatcher("WEB-INF/message.jsp").forward(request, response);
		}
		if ("cancelRecommend".equals(type)) {//忽略举报
			String id = request.getParameter("id");
			String message = is.cancleRecommendIsland(id);
			request.setAttribute("message", message);
			request.setAttribute("url", "AdminServlet?type=islandList");
			request.setAttribute("wait", "3");
			request.getRequestDispatcher("WEB-INF/message.jsp").forward(request, response);
		}
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
	}

}
