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
import ssoft.service.UserBaseInfoService;

import com.google.gson.Gson;

/**
 * 保存机器人的信息
 *
 * @author Administrator
 */
public class SaveRobotInfoServlet extends Servlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取服务
        UserBaseInfoService service = BasicFactory.getFactory().getService(
                UserBaseInfoService.class);

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
        String robot_icall = mapContent.get("robot_icall");
        String robot_callme = mapContent.get("robot_callme");
        String robot_name = mapContent.get("robot_name");
        String soundplaytext = mapContent.get("soundplaytext");
        System.out.println(robot_name + "---" + robot_callme + "---" + robot_icall + "---" + soundplaytext);

        String result = service.saveRobotInfo(id, password, robot_icall, robot_callme, robot_name, soundplaytext);
        response.getWriter().write(result);

    }

}
