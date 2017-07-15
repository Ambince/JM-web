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
import ssoft.service.OfficialaccountService;
import ssoft.service.GroupMemberService;

import com.google.gson.Gson;
/**
 * 删除群成员
 * @author Administrator
 *
 */
public class RemoveGroupMemberServlet extends Servlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//get方法用于测试
		// 获取服务
		GroupMemberService service = BasicFactory.getFactory()
				.getService(GroupMemberService.class);

		// 设置请求的编码格式
		super.setEncoding(request,response);
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String groupId = request.getParameter("groupId");
		String removeId = request.getParameter("removeId");
		String result = service.removeMember(id,password,groupId,removeId);
		response.getWriter().write(result);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取服务
		GroupMemberService service = BasicFactory.getFactory()
				.getService(GroupMemberService.class);

		// 设置请求的编码格式
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String content = request.getParameter("content");
		if (null == content) {
			return;
		}
		Map<String, String> mapContent = new HashMap<String, String>();
		Gson gson = new Gson();
		mapContent = gson.fromJson(content, Map.class);
		String id = mapContent.get("id");
		String password = mapContent.get("password");
		String groupId = mapContent.get("groupId");
		String removeId = mapContent.get("removeId");
		String result = service.removeMember(id,password,groupId,removeId);
		response.getWriter().write(result);

	}

}
