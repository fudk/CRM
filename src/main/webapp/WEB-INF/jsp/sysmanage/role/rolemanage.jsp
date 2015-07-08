<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>志晴CRM</title>

<%@ include file="../../common/head.jsp"%>

<link rel="stylesheet" href="${ctx}/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/pagination.css"	type="text/css">
<script type="text/javascript" src="${ctx}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgdialog.min.js?skin=blue"></script>

<script type="text/javascript">
function pageselectCallback(page_index, jq){
	getRolePage(page_index);
    return false;
}
var pageOpt={
		callback: pageselectCallback,
		items_per_page:10 ,
		num_display_entries:10,
		num_edge_entries:2,
		prev_text:"上一页",
		next_text:"下一页"
	}
	
function initPagination() {
	var num_entries = $('#hiddenresult').text();
    $("#Pagination").pagination(num_entries, pageOpt);
 }
 
 function getRolePage(page_index){
	 $.ajax({
		type : "POST",
		url : "${ctx}/system/role/getRoles",
		data : {currentPage:(page_index+1)} ,
		dataType : "html",
		success : function(data) {
			$('#Searchresult').empty().append(data);
			$("tr:even").addClass("tr2");
		} 
	});
 }

 function trCss(){
	//$("tr:odd").addClass("tr1");
	$(".list_table tr").live("mouseover",function(){
		$(this).addClass("tr3");
	});
	$(".list_table tr").live("mouseout",function(){
		 $(this).removeClass("tr3");  
	});
	/* $("tr").live("click",function(){
		cancelCheck();
		var roleId = $(this).find('td:first').text();
		$("#roleId").val(roleId);
		getAssociations(roleId);
		//getTaoUser($(this).find('td:first').text());
	}); */
 }
 
$(document).ready(function(){  
	$('#hiddenresult').load("${ctx}/system/role/getRoleCount", null, initPagination);
	trCss();
	
});

function changeResource(roleId){
	$.dialog({
		title:'角色资源分配',
		content: 'url:'+ctx+'/system/resource/getRoleResources/'+roleId}
	);
	
}

</script>
</head>

<body>
	<div class="top">
		<%@ include file="../../common/top.jsp"%>
		<div class="main">
			<div class="page_title">角色管理</div>
			<div id="Search"></div>
			<div id="Searchresult"></div>
			<div id="Pagination"></div>
			<div id="hiddenresult" style="display: none;"></div>
		</div>
	</div>
</body>

</html>