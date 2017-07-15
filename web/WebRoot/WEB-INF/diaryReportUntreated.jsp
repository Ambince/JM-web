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
					<td width="40%" class="font_red">【随记 - 举报 - 未处理列表】</td>
					<td width="30%" align="center">随记总数：<span class="font_red">${diarysum}</span></td>
					<td width="30%" align="center">点评总数：<span class="font_red">${commentsum}</span></td>
				</tr>
			</tbody>
		</table>
		&nbsp;
		<table>
			<tbody>
				<tr class="font_b">
					<td width="50%">内容</td>
					<td width="10%">举报理由</td>
					<td width="10%">举报者</td>
					<td width="10%">发布者</td>
					<td width="20%">操作</td>
				</tr>
				<c:forEach items="${requestScope.reportsinfo}" var="reportinfo">
					<tr>
						<td>
							<p>${reportinfo.get("content")}</p>
						</td>
						<td>${reportinfo.get("reason")}</td>
						<td>${reportinfo.get("reportPersion")}</td>
						<td>${reportinfo.get("publishPersion")}</td>
						<td>
							<a href="HandleReportServlet?type=ignore&diaryid=${reportinfo.get('diaryId')}">忽略</a>
							<a href="HandleReportServlet?type=delete&diaryid=${reportinfo.get('diaryId')}">取消此分享</a>
							<a href="HandleReportServlet?type=delete&diaryid=${reportinfo.get('diaryId')}">删除随记</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table>
			<tbody>
				<tr class="fanye">
					<td width="25%" align="center"><a href="AdminServlet?type=diaryReportUntreated&page=1">首页</a></td>
					<c:if test="${requestScope.curpage == 1}">
						<td width="25%" align="center"><a>上一页</a><span>(已经是第一页了)</span></td>
					</c:if>
					<c:if test="${requestScope.curpage > 1}">
						<td width="25%" align="center"><a href="AdminServlet?type=diaryReportUntreated&page=${requestScope.curpage - 1}">上一页</a></td>
					</c:if>
					<c:if test="${requestScope.curpage == requestScope.pagenum}">
						<td width="25%" align="center"><a>下一页</a><span>(已经是最后一页了)</span></td>
					</c:if>
					<c:if test="${requestScope.curpage < requestScope.pagenum}">
						<td width="25%" align="center"><a href="AdminServlet?type=diaryReportUntreated&page=${requestScope.curpage + 1}">下一页</a></td>
					</c:if>
					<td width="25%" align="center"><a href="AdminServlet?type=diaryReportUntreated&page=${pagenum}">末页</a></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
