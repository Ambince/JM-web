package ssoft.servlet;

import com.google.gson.Gson;
import ssoft.factory.BasicFactory;
import ssoft.service.UserBaseInfoService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amence on 2017/6/23.
 */
public class SetBaseInfoServlet extends Servlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取服务
        UserBaseInfoService service = BasicFactory.getFactory().getService(
                UserBaseInfoService.class);

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
        String strangerfind = mapContent.get("strangerfind");
        String result = service.updateBaseInfo(id,strangerfind);
        response.getWriter().write(result);


    }
}
