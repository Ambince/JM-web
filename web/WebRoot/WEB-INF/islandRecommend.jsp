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
					<td class="font_red">【萌岛 - 推荐列表】</td>
				</tr>
			</tbody>
		</table>

		&nbsp;

		<table>
			<tbody>
				<tr class="font_b">
					<td width="10%">ID</td>
					<td width="50%">岛名</td>
					<td width="10%">创建日期</td>
					<td width="10%">贝壳数</td>
					<td width="10%">渔民数</td>
					<td width="10%">操作</td>
				</tr>
				<c:forEach items="${requestScope.islandsinfo}" var="islandinfo">
					<tr>
						<td>${islandinfo.id}</td>
						<td>${islandinfo.name}</td>
						<td>${islandinfo.createTime}</td>
						<td>${islandinfo.diaryNum}</td>
						<td>${islandinfo.userNum}</td>
						<td>
							<a href="IslandServlet?type=cancelRecommend&id=${islandinfo.id}">取消推荐</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table>
			<tbody>
				<tr class="fanye">
					<td width="25%" align="center"><a
						href="AdminServlet?type=islandRecommend&page=1">首页</a></td>
					<c:if test="${requestScope.curpage == 1}">
						<td width="25%" align="center"><a>上一页</a><span>(已经是第一页了)</span></td>
					</c:if>
					<c:if test="${requestScope.curpage > 1}">
						<td width="25%" align="center"><a
							href="AdminServlet?type=islandRecommend&page=${requestScope.curpage - 1}">上一页</a></td>
					</c:if>
					<c:if test="${requestScope.curpage == requestScope.pagenum}">
						<td width="25%" align="center"><a>下一页</a><span>(已经是最后一页了)</span></td>
					</c:if>
					<c:if test="${requestScope.curpage < requestScope.pagenum}">
						<td width="25%" align="center"><a
							href="AdminServlet?type=islandRecommend&page=${requestScope.curpage + 1}">下一页</a></td>
					</c:if>
					<td width="25%" align="center"><a
						href="AdminServlet?type=islandRecommend&page=${pagenum}">末页</a></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
