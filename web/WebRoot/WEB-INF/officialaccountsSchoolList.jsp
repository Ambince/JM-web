<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
					<td width="50%" class="font_red">【公众号 - 学校列表】</td>
					<td width="50%" align="center">学校总数：<span class="font_red">${schoolNum }</span></td>
				</tr>
			</tbody>
		</table>


		&nbsp;

		<table>
			<tbody>
				<tr class="font_b">
					<td width="10%">ID</td>
					<td width="50%">名称</td>
					<td width="10%">创建者</td>
					<td width="10%">创建日期</td>
					<td width="10%">关注数</td>
					<td width="10%">帖子数</td>
				</tr>
				<c:forEach items="${requestScope.schoolsInfo}" var="schoolInfo">
					<tr>
						<td>${schoolInfo.id}</td>
						<td>${schoolInfo.name}</td>
						<td>${schoolInfo.createPeople}</td>
						<td>${schoolInfo.createTime}</td>
						<td>${schoolInfo.attentionNum}</td>
						<td>${schoolInfo.postNum}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table>
			<tbody>
				<tr class="fanye">
					<td width="25%" align="center"><a
						href="AdminServlet?type=officialaccountsSchoolList&page=1">首页</a></td>
					<c:if test="${requestScope.curpage == 1}">
						<td width="25%" align="center"><a>上一页</a><span>(已经是第一页了)</span></td>
					</c:if>
					<c:if test="${requestScope.curpage > 1}">
						<td width="25%" align="center"><a
							href="AdminServlet?type=officialaccountsSchoolList&page=${requestScope.curpage - 1}">上一页</a></td>
					</c:if>
					<c:if test="${requestScope.curpage == requestScope.pagenum}">
						<td width="25%" align="center"><a>下一页</a><span>(已经是最后一页了)</span></td>
					</c:if>
					<c:if test="${requestScope.curpage < requestScope.pagenum}">
						<td width="25%" align="center"><a
							href="AdminServlet?type=officialaccountsSchoolList&page=${requestScope.curpage + 1}">下一页</a></td>
					</c:if>
					<td width="25%" align="center"><a
						href="AdminServlet?type=officialaccountsSchoolList&page=${pagenum}">末页</a></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
