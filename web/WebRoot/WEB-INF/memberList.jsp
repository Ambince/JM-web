<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<table bgcolor="#F0F0F0">
			<tbody>
				<tr>
					<td width="10%" class="font_red">【会员列表】</td>
					<td width="30%" align="center">注册总数：<span class="font_red">${memsum}</span></td>
					<td width="30%" align="center">女：<span class="font_red">${memsumf}</span></td>
					<td width="30%" align="center">男：<span class="font_red">${memsumm}</span></td>
				</tr>
			</tbody>
		</table>
		&nbsp;
		<table>
			<tbody>
				<tr class="font_b">
					<td width="8%">ID</td>
					<td width="4%">性别</td>
					<td width="10%">注册时间</td>
					<td width="14%">最近登录时间</td>
					<td width="8%">家族数</td>
					<td width="8%">家庭数</td>
					<td width="8%">小区数</td>
					<td width="8%">城村数</td>
					<td width="8%">班级数</td>
					<td width="8%">学校数</td>
					<td width="6%">萌岛数</td>
					<td width="5%">好友数</td>
					<td width="5%">随记数</td>
				</tr>
				<c:forEach items="${requestScope.membersinfo}" var="memberinfo">
					<tr>
						<td>${memberinfo.id}</td>
						<td>${memberinfo.sex}</td>
						<td>${memberinfo.registTime}</td>
						<td>${memberinfo.lastLoginTime}</td>
						<td>${memberinfo.jz}</td>
						<td>${memberinfo.jt}</td>
						<td>${memberinfo.xq}</td>
						<td>${memberinfo.cc}</td>
						<td>${memberinfo.bj}</td>
						<td>${memberinfo.xx}</td>
						<td>${memberinfo.md}</td>
						<td>${memberinfo.hy}</td>
						<td>${memberinfo.sj}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table>
			<tbody>
				<tr class="fanye">
					<td width="25%" align="center"><a href="AdminServlet?type=memberlist&page=1">首页</a></td>
					<c:if test="${requestScope.curpage == 1}">
						<td width="25%" align="center"><a>上一页</a><span>(已经是第一页了)</span></td>
					</c:if>
					<c:if test="${requestScope.curpage > 1}">
						<td width="25%" align="center"><a href="AdminServlet?type=memberlist&page=${requestScope.curpage - 1}">上一页</a></td>
					</c:if>
					<c:if test="${requestScope.curpage == requestScope.pagenum}">
						<td width="25%" align="center"><a>下一页</a><span>(已经是最后一页了)</span></td>
					</c:if>
					<c:if test="${requestScope.curpage < requestScope.pagenum}">
						<td width="25%" align="center"><a href="AdminServlet?type=memberlist&page=${requestScope.curpage + 1}">下一页</a></td>
					</c:if>
					<td width="25%" align="center"><a href="AdminServlet?type=memberlist&page=${pagenum}">末页</a></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
