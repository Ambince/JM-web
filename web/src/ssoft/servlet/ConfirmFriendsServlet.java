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
import ssoft.service.UserRelationService;

import com.google.gson.Gson;

/**
 * 确认好友添加接口
 *
 * @author Administrator
 */
public class ConfirmFriendsServlet extends Servlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取服务
        UserRelationService service = BasicFactory.getFactory().getService(
                UserRelationService.class);

        // 设置请求的编码格式
        super.setEncoding(request, response);
        String content = request.getParameter("content");
        if (null == content) {
            return;
        }
        Map<String, String> mapContent = new HashMap<String, String>();
        Gson gson = new Gson();
        mapContent = gson.fromJson(content, Map.class);
        String id = mapContent.get("id");
        String password = mapContent.get("password");
        String friendId = mapContent.get("friendId");
        String call = mapContent.get("call");
        String remark = mapContent.get("remark");
        String result = service.confirmFriends(id, password, friendId, call, remark);
        response.getWriter().write(result);

    }

}
