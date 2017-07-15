package ssoft.servlet;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;

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

import ssoft.domain.GroupChat;
import ssoft.factory.BasicFactory;
import ssoft.global.Global;
import ssoft.service.GroupMemberService;

import com.google.gson.Gson;

/**
 * 添加群成员
 * @author Administrator
 *
 */
public class AddGroupMemberServlet extends Servlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Gson gson = new Gson();
		// 5在融云添加成员
				try {
					
					
					SdkHttpResult removeRet = ApiHttpClient.joinGroup(
							Global.Rong_App_Key, Global.Rong_App_Secret, "152",
							"54", "熊氏家族", FormatType.json);
					Map<String, String> mapCode = new HashMap<String, String>();

					mapCode = gson.fromJson(removeRet.getResult(), Map.class);

					if (mapCode.get("code") != "200") {
						System.out.println("成功");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取服务
		GroupMemberService service = BasicFactory.getFactory().getService(
				GroupMemberService.class);

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
		String groupId = mapContent.get("groupId");
		String strIds = mapContent.get("memberIds");
		List<String> memberIds = new ArrayList<String>();
		
		memberIds = gson.fromJson(strIds, List.class);
		String result = service.addMembers(id, password, groupId, memberIds);
		response.getWriter().write(result);

	}

}
