<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="css.css">


</head>

<body>
	<div>
		<div class="adminlogo">管 理 中 心</div>
		
		<div class="admin">
			<ul>
				<li  style="float:left; color:#0099FF;">${sessionScope.admin}</li>
				<li class="tuichu"><a href="AdminServlet?type=logout" target="_top">退出</a></li>
			</ul>
		</div>
		<div>
			<div class="lanmu">发布新版本</div>
			<div class="gongneng">
				<a target="right" href="AdminServlet?type=updateAndroid">android版</a>
			</div>
			<div class="gongneng">
				<a target="right" href="AdminServlet?type=updateIos">ios版</a>
			</div>
		</div>

		<div>
			<div class="lanmu">会员管理</div>
			<div class="gongneng">
				<a target="right" href="AdminServlet?type=membersum">会员统计</a>
			</div>
			<div class="gongneng">
				<a target="right" href="AdminServlet?type=memberlist">会员列表</a>
			</div>
		</div>

		<div>
			<div class="lanmu">意见管理</div>
			<div class="gongneng">
				<a target="right" href="AdminServlet?type=suggestionlist">未处理意见</a>
			</div>
		</div>
		
		<div>
			<div class="lanmu">随记管理</div>
			<div class="gongneng">
				<a target="right" href="AdminServlet?type=diaryReportUntreated">未处理举报</a>
			</div>
		</div>

		<div>
			<div class="lanmu">萌岛管理</div>
			<div class="gongneng">
				<a target="right" href="AdminServlet?type=islandList">萌岛列表</a>
			</div>
			<div class="gongneng">
				<a target="right" href="AdminServlet?type=islandRecommend">推荐列表</a>
			</div>
		</div>

		<div>
			<div class="lanmu">群聊管理</div>
			<div class="gongneng">
				<a target="right" href="AdminServlet?type=groupchatClanList">家族列表</a>
			</div>
			<div class="gongneng">
				<a target="right" href="AdminServlet?type=groupchatHomeList">家庭列表</a>
			</div>
			<div class="gongneng">
				<a target="right" href="AdminServlet?type=groupchatClassList">班级列表</a>
			</div>
		</div>

		<div>
			<div class="lanmu">公众号管理</div>
			<div class="gongneng">
				<a target="right" href="AdminServlet?type=officialaccountsCommunityList">小区列表</a>
			</div>
			<div class="gongneng">
				<a target="right" href="AdminServlet?type=officialaccountsVillageList">城村列表</a>
			</div>
			<div class="gongneng">
				<a target="right" href="AdminServlet?type=officialaccountsSchoolList">学校列表</a>
			</div>
		</div>

		<div>
			<div class="lanmu">管理员管理</div>
			<!-- 
			<div class="gongneng">
				<a target="right" href="regist.jsp">添加管理员</a>
			</div>
			-->
			<div class="gongneng">
				<a target="right" href="AdminServlet?type=adminPsw">管理员密码</a>
			</div>
		</div>

		<div style="height:50px;"></div>
	</div>
</body>
</html>
