<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>志晴CRM</title>

<%@ include file="../common/head.jsp"%>

<link rel="stylesheet" href="${ctx}/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<link rel="stylesheet" href="${ctx}/css/pagination.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/jquery.autocomplete.css" type="text/css">
<script type="text/javascript" src="${ctx}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/js/pagination.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.autocomplete.js"></script>
<script type="text/javascript"
	src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/js/lhgdialog/lhgdialog.min.js?skin=blue"></script>

<script type="text/javascript">

	$(document).ready(function() {
		searchPage("${ctx}/rechargeConsume/getConsumes");
		trCss();
		
		operatorUser();
	});

	function search() {
		//var searchData = {loginName:$("#loginName").val()};
		searchPage("${ctx}/rechargeConsume/getConsumes",$("#consumeForm").serialize());
	}

	function trCss() {
		$(".list_table tr").live("mouseover", function() {
			$(this).addClass("tr3");
		});
		$(".list_table tr").live("mouseout", function() {
			$(this).removeClass("tr3");
		});
	}

	function operatorUser(){
		$("#operatorName").autocomplete("/system/user/searchUsers", {
			minChars: 0,
			width: 310,
			dataType: "json",
			parse: function(data) {
				return $.map(data, function(row) {
					return {
						data: row,
						value: row.userName,
						result: row.userName + "[" + row.loginName + "]"
					}
				});
			},
			formatItem: function(row, i, max) {
				return row.userName + "[" + row.loginName + "]";
			},
			formatMatch: function(row, i, max) {
				return row.userId;
			},
			formatResult: function(row) {
				return row.userName;
			}
		}).result(function(e, item) {
			$("#operator").val(item.userId);
		});
	}
	
	function startDate(_obj) {
		WdatePicker({
			onpicked : function() {
				var d5222 = $dp.$('endTime');
				d5222.focus();
			},
			dateFmt : 'yyyy-MM-dd'
		})
	}
	function endDate() {
		WdatePicker({
			minDate : '#F{$dp.$D(\'startTime\')}',
			dateFmt : 'yyyy-MM-dd '
		})
	}
</script>

</head>

<body>
	<div class="top">
		<%@ include file="../common/top.jsp"%>
		<div class="main" >
			<div class="page_title">消费记录查询</div>
			<form action="" id="consumeForm">
			<div id="Search">
				手机号：<input type="text" name="consumePhone" id="consumePhone" value=""
					style="border:1px solid #ccc;"> 
				消费账户：<input type="text" name="operatorName" id="operatorName" value=""
					style="border:1px solid #ccc;"><input type="hidden" name="userId" id="operator"/>
				运营商：<select name="isp">
				<option value="">全部</option>
				<option value="CM">中国移动</option>
				<option value="CU">中国联通</option>
				<option value="CT">中国电信</option>
				</select>
				消费类型：<select name="consumeType">
				<option value="">全部</option>
				<option value="phone">话费</option>
				<option value="flow">流量</option>
				</select>	
					 消费时间：<input type="text" size="12" readonly="readonly"
					id="startTime" name="startTime" class="Wdate"
					onFocus="startDate(this)"   value="" /> 
					 至<input type="text" size="12" readonly="readonly"
					id="endTime" name="endTime" class="Wdate"
					onFocus="endDate()" value="" /> 

 			<input type="button" onclick="search()" value="查询" /> 
			</div>
			</form>
			<div id="Searchresult"></div>
			<div id="Pagination"></div>

			
		</div>
	</div>
</body>

</html>