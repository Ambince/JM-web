package ssoft.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

public class AdminValidateFilter implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession(true);

		// 从session里取的用户名信息
		String admin = (String) session.getAttribute("admin");

		// 判断如果没有取到用户信息,就跳转到登陆页面
		if (admin == null || "".equals(admin)) {
			// 跳转到登陆页面
			request.setAttribute("message", "请登陆后再进行操作");
			request.setAttribute("url", "login.jsp");
			request.setAttribute("wait", "3");
			request.getRequestDispatcher("WEB-INF/message.jsp").forward(request, response);
		} else {
			// 已经登陆,继续此次请求
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
	}
}