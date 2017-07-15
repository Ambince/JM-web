package ssoft.servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ssoft.domain.Admin;
import ssoft.factory.BasicFactory;
import ssoft.service.AdminService;
import ssoft.utils.MD5Utils;

public class AdminLoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("AdminLoginServletdoGet");
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("AdminLoginServletdoPost");
		// 获取服务
		AdminService as = BasicFactory.getFactory().getService(AdminService.class);
		// 设置请求的编码格式
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		//获取处理类型
		String type = request.getParameter("type");
		if("login".equals(type)){//管理员登陆
			//获取数据
			String username = request.getParameter("username");
			String password = MD5Utils.md5(request.getParameter("password"));
			String autologin = request.getParameter("autoligin");
			Admin admin = as.getAdminByNameAndPsw(username, password);
			//判断用户
			if(admin == null){
				request.setAttribute("msg", "用户名密码不正确!");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			//用户存在写入session
			request.getSession().setAttribute("admin", username);
			//--处理30天内自动登陆
			if("true".equals(autologin)){
				Cookie autologinC = new Cookie("autologin",URLEncoder.encode(admin.getUsername()+":"+admin.getPassword(),"utf-8"));
				autologinC.setPath("/");
				autologinC.setMaxAge(3600*24*30);
				response.addCookie(autologinC);
			}
			response.sendRedirect("AdminServlet?type=index");
		}
	}

}
