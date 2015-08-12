<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>志晴CRM</title>

<%@ include file="../../common/head.jsp"%>

<link rel="stylesheet" href="${ctx}/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<link rel="stylesheet" href="${ctx}/css/pagination.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/jquery.autocomplete.css" type="text/css">
<script type="text/javascript" src="${ctx}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/js/pagination.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.autocomplete.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/js/lhgdialog/lhgdialog.min.js?skin=blue"></script>

<script type="text/javascript">

	$(document).ready(function() {
		searchPage("${ctx}/system/user/getUsers");
		trCss();
		autoUser();
	});

	function searchUser() {
		//var searchData = {loginName:$("#loginName").val()};
		searchPage("${ctx}/system/user/getUsers",$("#userForm").serialize());
	}

	function trCss() {
		$(".list_table tr").live("mouseover", function() {
			$(this).addClass("tr3");
		});
		$(".list_table tr").live("mouseout", function() {
			$(this).removeClass("tr3");
		});
	}

	function changeRole(userId) {
		$.dialog({
			title : '用户角色分配',
			content : 'url:' + ctx + '/system/role/roleUserAssociation/'
					+ userId
		});
	}
	
	function disableUser(userId){
		$.post("/system/user/disableUser", { userId: userId },
			function(data){
			if(data == 'success'){
				alert("删除成功");
			}else{
				alert(data);
			}
		});
	}

	var rechargeDialog;
	function recharge(userId, loginName) {
		rechargeDialog = $.dialog({
			title : '充值',
			width : '430px',
			height : '180px',
			content : 'url:' + ctx + '/rechargeConsume/initAddRecharge/'
					+ userId + '/' + loginName,
			button : [ {
				name : '确定',
				callback : function() {
					rechargeDialog.content.recharge();
					return false;
				},
				focus : true
			} ],
			cancelVal : '关闭',
			cancel : true
		});
	}

	var userDialog;
	function addOrUpdateUser(method) {
		userDialog = $.dialog({
			title : '新增用户',
			width : '600px',
			height : '370px',
			content : 'url:' + ctx + '/system/user/' + method,
			button : [ {
				name : '确定',
				callback : function() {
					if ('addUserInit' == method) {
						userDialog.content.saveUser();
					} else {
						userDialog.content.updateUser();
					}
					return false;
				},
				focus : true
			} ],
			cancelVal : '关闭',
			cancel : true
		});
	}
	
	function autoUser(){
		$("#login").autocomplete("/system/user/searchUsers", {
			minChars: 1,
			width: 310,
			dataType: "json",
			parse: function(data) {
				if(data){
					$("#userId").val("");
				}
				return $.map(data, function(row) {
					return {
						data: row,
						value: row.userName,
						result: row.userName + "[" + row.loginName + "]"
					}
				});
			},
			formatItem: function(row, i, max) {
				return row.userName + "\" [" + row.loginName + "]";
			},
			formatMatch: function(row, i, max) {
				return row.userId;
			},
			formatResult: function(row) {
				return row.userName;
			}
		}).result(function(e, item) {
			$("#userId").val(item.userId);
		});
	}
</script>

</head>

<body>
	<div class="top">
		<%@ include file="../../common/top.jsp"%>
		<div class="main">
			<div class="page_title">用户管理</div>
			<form id="userForm" action="">
			<div id="Search">
				登录名：<input type="text" name="login" id="login" value=""
					style="border:1px solid #ccc;"> <input type="hidden" name="userId" id="userId"/>
					<input type="button" onclick="searchUser()" value="查询" /> <input type="button"
					onclick="addOrUpdateUser('addUserInit')" value="新增" />
			</div>
			</form>
			<div id="Searchresult"></div>
			<div id="Pagination"></div>

			
		</div>
	</div>
</body>

</html>