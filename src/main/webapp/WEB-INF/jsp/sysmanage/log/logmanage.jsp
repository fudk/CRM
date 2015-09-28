<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>中舜充值系统</title>

<%@ include file="../../common/head.jsp"%>

<link rel="stylesheet" href="${ctx}/css/pagination.css"	type="text/css">
<script type="text/javascript" src="${ctx}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/js/pagination.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	searchPage("${ctx}/system/log/getLogs");
});

function searchUser() {
	searchPage("${ctx}/system/log/getLogs",$("form").serialize());
}

</script>
</head>

<body>
	<div class="top">
		<%@ include file="../../common/top.jsp"%>
		<div class="main">
			<div class="page_title">日志查询</div>
			<div id="Search"></div>
			<div id="Searchresult"></div>
			<div id="Pagination"></div>
			<div id="hiddenresult" style="display: none;"></div>
		</div>
	</div>
</body>

</html>