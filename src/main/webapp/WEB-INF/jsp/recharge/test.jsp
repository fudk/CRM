<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>中舜充值系统</title>

<%@ include file="../common/head.jsp"%>


<link rel="stylesheet" href="${ctx}/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/pagination.css"	type="text/css">
<script type="text/javascript" src="${ctx}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>

<script type="text/javascript" src="${ctx}/js/zDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/zDrag.js"></script>

<script type="text/javascript">
function open1(){
	Dialog.alert("提示：你点击了一个按钮");
}
</script>
</head>

<body>
	<div class="top">
		<%@ include file="../common/top.jsp"%>
		<div class="main">
			<div class="page_title">用户管理</div>
			<div id="Search"><input type="hidden" id="userId" value="" />
			<input type="button" id="i" onclick="open1()" value="充值"/>
			</div>
			<div id="Searchresult"></div>
			<div id="Pagination"></div>
			<div id="hiddenresult" style="display: none;"></div>

			<div id="labelresult"></div>
			<div>
				<ul id="roletree" class="ztree"></ul>
			</div>
		</div>
	</div>
</body>


</html>