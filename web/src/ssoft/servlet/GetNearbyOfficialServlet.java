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

import com.google.gson.Gson;

public class GetNearbyOfficialServlet extends Servlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取服务
        OfficialaccountService officialaccountService = BasicFactory.getFactory().getService(
                OfficialaccountService.class);

        // 设置请求的编码格式
        super.setEncoding(request, response);
        String content = request.getParameter("content");
        if (null == content) {
            return;
        }
        Map<String, Object> mapContent = new HashMap<String, Object>();
        Gson gson = new Gson();
        mapContent = gson.fromJson(content, Map.class);
        String userid = mapContent.get("id") + "";
        String password = mapContent.get("password") + "";
        String latitude = mapContent.get("latitude") + "";
        String longitude = mapContent.get("longitude") + "";
        String type = mapContent.get("type") + "";

        String result = officialaccountService.getNearbyOfficialaccounts(userid, password, latitude, longitude, type);
        response.getWriter().write(result);

    }

}
