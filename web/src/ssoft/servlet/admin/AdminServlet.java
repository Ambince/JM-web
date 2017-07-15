package ssoft.servlet.admin;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ssoft.domain.Admin;
import ssoft.factory.BasicFactory;
import ssoft.service.AdminService;
import ssoft.service.DiaryService;
import ssoft.service.GroupService;
import ssoft.service.IslandService;
import ssoft.service.OfficialaccountService;
import ssoft.service.UserService;
import ssoft.utils.MD5Utils;

public class AdminServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("AdminServletdoGet");
		UserService us = BasicFactory.getFactory().getService(UserService.class);
		DiaryService ds = BasicFactory.getFactory().getService(DiaryService.class);
		IslandService is = BasicFactory.getFactory().getService(IslandService.class);
		GroupService gs = BasicFactory.getFactory().getService(GroupService.class);
		OfficialaccountService os = BasicFactory.getFactory().getService(OfficialaccountService.class);
		// 设置请求的编码格式
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//获取处理类型
		String type = request.getParameter("type");
		System.out.println(type);
		if ("index".equals(type)) {//主页
			request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request,response);
		}
		if ("top".equals(type)) {//上边页
			request.getRequestDispatcher("/WEB-INF/top.jsp").forward(request,response);
		}
		if ("left".equals(type)) {//左边页
			request.getRequestDispatcher("/WEB-INF/left.jsp").forward(request,response);
		}
		if ("welcome".equals(type)) {//欢迎页
			request.getRequestDispatcher("/WEB-INF/welcome.jsp").forward(request,response);
		}
		if ("updateAndroid".equals(type)) {//升级A应用页
			String version = "";
			String path = request.getContextPath();
			String APKpath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			String versionpath = request.getRealPath("");
			try {
				FileInputStream in = new FileInputStream(versionpath + "/version.txt");
				int size = in.available();
				byte[] buffer = new byte[size];
				in.read(buffer);
	        	version = new String(buffer);
	        	in.close();
	        } catch (IOException e) {
	        	System.out.println("未发布新版本，找不到版本信息文件！");
	        }finally{
	        	if(version.equals("")){
					request.setAttribute("version", "");
			        request.setAttribute("date", "");
			        request.setAttribute("url",  ""); 
					request.getRequestDispatcher("/WEB-INF/updateAndroid.jsp").forward(request,response);
				}else{
					String[] v = version.split("===");
					request.setAttribute("version", v[1]);
			        request.setAttribute("date", v[0]);
			        request.setAttribute("url", APKpath + v[2]); 
					request.getRequestDispatcher("/WEB-INF/updateAndroid.jsp").forward(request,response);
				}
	        }	
		}
		if ("updateIos".equals(type)) {//升级I应用页
			request.getRequestDispatcher("/WEB-INF/updateIos.jsp").forward(request,response);
		}
		if ("membersum".equals(type)) {//用户统计页
			request.setAttribute("memsum", us.getAllMember());
			request.setAttribute("memsumf", us.getAllFemaleMember());
			request.setAttribute("memsumm", us.getAllMaleMember());
			request.setAttribute("todayloginmem", us.getTodayMember());
			request.setAttribute("mouthloginmem", us.getMouthMember());
			request.getRequestDispatcher("/WEB-INF/memberSum.jsp").forward(request, response);
		}
		if ("memberlist".equals(type)) {//用户列表页
			int page = 1;
			if(request.getParameter("page") != null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			Map pageInfo = us.getPageInfo(page);
			request.setAttribute("memsum", us.getAllMember());
			request.setAttribute("memsumf", us.getAllFemaleMember());
			request.setAttribute("memsumm", us.getAllMaleMember());
			request.setAttribute("todayloginmem", us.getTodayMember());
			request.setAttribute("pagenum", pageInfo.get("pagenum"));
			request.setAttribute("curpage", pageInfo.get("curpage"));
			request.setAttribute("membersinfo", us.getMembersInfo(page));
			request.getRequestDispatcher("/WEB-INF/memberList.jsp").forward(request, response);
		}		
		if ("suggestionlist".equals(type)){//未处理意见页
			int page = 1;
			if(request.getParameter("page") != null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			Map<String, Integer> pageInfo = ds.getSuggestionPageInfo(page);
			request.setAttribute("pagenum", pageInfo.get("pagenum"));
			request.setAttribute("curpage", pageInfo.get("curpage"));
			request.setAttribute("suggestioninfo", ds.getSuggestionsInfo(page));
			request.getRequestDispatcher("/WEB-INF/SuggestionList.jsp").forward(request, response);				
		}
		if ("diaryReportUntreated".equals(type)) {//未处理举报页
			int page = 1;
			if(request.getParameter("page") != null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			Map<String, Integer> pageInfo = ds.getPageInfo(page);
			request.setAttribute("diarysum", ds.getAllStartDiary());
			request.setAttribute("commentsum", ds.getAllComment());
			request.setAttribute("pagenum", pageInfo.get("pagenum"));
			request.setAttribute("curpage", pageInfo.get("curpage"));
			request.setAttribute("reportsinfo", ds.getReportsInfo(page));
			request.getRequestDispatcher("/WEB-INF/diaryReportUntreated.jsp").forward(request, response);
		}
		if ("islandList".equals(type)) {//萌岛列表页
			int page = 1;
			if(request.getParameter("page") != null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			Map<String, Integer> pageInfo = is.getPageInfo(page);
			request.setAttribute("islandsum", is.getAllIsland());
			request.setAttribute("pagenum", pageInfo.get("pagenum"));
			request.setAttribute("curpage", pageInfo.get("curpage"));
			request.setAttribute("islandsinfo", is.getIslandsInfo(page));
			request.getRequestDispatcher("/WEB-INF/islandList.jsp").forward(request, response);
		}
		if ("islandRecommend".equals(type)) {//推荐萌岛列表页
			int page = 1;
			if(request.getParameter("page") != null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			Map<String, Integer> pageInfo = is.getRecommendPageInfo(page);
			request.setAttribute("pagenum", pageInfo.get("pagenum"));
			request.setAttribute("curpage", pageInfo.get("curpage"));
			request.setAttribute("islandsinfo", is.getRecommendIslandsInfo(page));
			request.getRequestDispatcher("/WEB-INF/islandRecommend.jsp").forward(request, response);
		}
		if ("groupchatClanList".equals(type)) {//家族列表页
			int page = 1;
			if(request.getParameter("page") != null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			Map<String, Integer> pageInfo = gs.getClanPageInfo(page);
			request.setAttribute("clanNum", gs.getClanNum());
			request.setAttribute("pagenum", pageInfo.get("pagenum"));
			request.setAttribute("curpage", pageInfo.get("curpage"));
			request.setAttribute("clansinfo", gs.getClansInfo(page));
			request.getRequestDispatcher("/WEB-INF/groupchatClanList.jsp").forward(request, response);
		}
		if ("groupchatHomeList".equals(type)) {//家庭列表页
			int page = 1;
			if(request.getParameter("page") != null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			Map<String, Integer> pageInfo = gs.getHomePageInfo(page);
			request.setAttribute("homeNum", gs.getHomeNum());
			request.setAttribute("pagenum", pageInfo.get("pagenum"));
			request.setAttribute("curpage", pageInfo.get("curpage"));
			request.setAttribute("homesInfo", gs.getHomesInfo(page));
			request.getRequestDispatcher("/WEB-INF/groupchatHomeList.jsp").forward(request, response);
		}
		if ("groupchatClassList".equals(type)) {//班级列表页
			int page = 1;
			if(request.getParameter("page") != null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			Map<String, Integer> pageInfo = gs.getClassPageInfo(page);
			request.setAttribute("classNum", gs.getClassNum());
			request.setAttribute("pagenum", pageInfo.get("pagenum"));
			request.setAttribute("curpage", pageInfo.get("curpage"));
			request.setAttribute("classesInfo", gs.getClassesInfo(page));
			request.getRequestDispatcher("/WEB-INF/groupchatClassList.jsp").forward(request, response);
		}
		if ("officialaccountsCommunityList".equals(type)) {//小区列表页
			int page = 1;
			if(request.getParameter("page") != null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			Map<String, Integer> pageInfo = os.getCommunityPageInfo(page);
			request.setAttribute("communityNum", os.getCommunityNum());
			request.setAttribute("pagenum", pageInfo.get("pagenum"));
			request.setAttribute("curpage", pageInfo.get("curpage"));
			request.setAttribute("communitiesInfo", os.getCommunitiesInfo(page));
			request.getRequestDispatcher("/WEB-INF/officialaccountsCommunityList.jsp").forward(request, response);
		}
		if ("officialaccountsVillageList".equals(type)) {//城村列表页
			int page = 1;
			if(request.getParameter("page") != null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			Map<String, Integer> pageInfo = os.getVillagePageInfo(page);
			request.setAttribute("villageNum", os.getVillageNum());
			request.setAttribute("pagenum", pageInfo.get("pagenum"));
			request.setAttribute("curpage", pageInfo.get("curpage"));
			request.setAttribute("villagesInfo", os.getVillagesInfo(page));
			request.getRequestDispatcher("/WEB-INF/officialaccountsVillageList.jsp").forward(request, response);
		}
		if ("officialaccountsSchoolList".equals(type)) {//学校列表页
			int page = 1;
			if(request.getParameter("page") != null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			Map<String, Integer> pageInfo = os.getSchoolPageInfo(page);
			request.setAttribute("schoolNum", os.getSchoolNum());
			request.setAttribute("pagenum", pageInfo.get("pagenum"));
			request.setAttribute("curpage", pageInfo.get("curpage"));
			request.setAttribute("schoolsInfo", os.getSchoolsInfo(page));
			request.getRequestDispatcher("/WEB-INF/officialaccountsSchoolList.jsp").forward(request, response);
		}
		if ("adminPsw".equals(type)) {//管理员密码页
			request.getRequestDispatcher("/WEB-INF/adminPsw.jsp").forward(request, response);
		}
		
		if ("logout".equals(type)) {//管理员注销
			if(request.getSession(false)!=null){
				request.getSession().invalidate();
				//删除自动登陆cookie
				Cookie autologinC = new Cookie("autologin","");
				autologinC.setPath("/");
				autologinC.setMaxAge(0);
				response.addCookie(autologinC);
			}
			response.sendRedirect("AdminServlet?type=index");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("AdminServletdoPost");
		// 获取服务
		AdminService as = BasicFactory.getFactory().getService(AdminService.class);
		// 设置请求的编码格式
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		//获取处理类型
		String type = request.getParameter("type");
		System.out.println(type);
		if ("modify_psd".equals(type)) {//修改管理员密码
			String oldpassword = MD5Utils.md5(request.getParameter("oldpassword"));
			String newpassword = MD5Utils.md5(request.getParameter("newpassword"));
			String message = as.modifyPassword((String)request.getSession().getAttribute("admin"), oldpassword, newpassword);
			request.setAttribute("message", message);
			request.setAttribute("url", "AdminServlet?type=adminPsw");
			request.setAttribute("wait", "3");
			request.getRequestDispatcher("WEB-INF/message.jsp").forward(request, response);
		}
		/*
		if ("regist".equals(type)) {//管理员注册
			//获取值
			String username = request.getParameter("username");
			String password = MD5Utils.md5(request.getParameter("password"));
			String valistr1 = request.getParameter("valistr");
			String valistr2 = (String) request.getSession().getAttribute("valistr");
			System.out.println("username=" + username);
			System.out.println("password=" + password);
			System.out.println("type=" + type);
			//校验输入
			if (null == username || null == password || null == type) {
				System.out.println("post数据错误");
				return;
			}
			//校验验证码
			if(valistr1 == null || valistr2 == null || !valistr1.equals(valistr2)){
				request.setAttribute("msg", "<font color='red'>验证码不正确!</font>");
				request.getRequestDispatcher("/regist.jsp").forward(request, response);
				return;
			}
			try {
				//封装数据
				Admin admin = new Admin();
				admin.setUsername(username);	
				admin.setPassword(password);
				//注册用户
				System.out.println(admin);
				String message = as.regist(admin);
				request.setAttribute("msg", "<font color='red'>"+message+"</font>");
				request.getRequestDispatcher("/regist.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		*/
	}
}
