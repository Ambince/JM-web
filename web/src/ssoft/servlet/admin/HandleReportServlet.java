package ssoft.servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ssoft.factory.BasicFactory;
import ssoft.service.DiaryService;

public class HandleReportServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DiaryService ds = BasicFactory.getFactory().getService(DiaryService.class);
		// 设置请求的编码格式
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//获取类型
		String type = request.getParameter("type");
		if ("ignore".equals(type)) {//忽略举报
			String diaryId = request.getParameter("diaryid");
			String message = ds.ignoreReport(diaryId);
			request.setAttribute("message", message);
			request.setAttribute("url", "AdminServlet?type=diaryReportUntreated");
			request.setAttribute("wait", "3");
			request.getRequestDispatcher("WEB-INF/message.jsp").forward(request, response);
		}
		if ("delete".equals(type)) {//忽略举报
			String diaryId = request.getParameter("diaryid");
			String message = ds.deleteReport(diaryId);
			request.setAttribute("message", message);
			request.setAttribute("url", "AdminServlet?type=diaryReportUntreated");
			request.setAttribute("wait", "3");
			request.getRequestDispatcher("WEB-INF/message.jsp").forward(request, response);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
