<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>中舜充值系统</title>

<%@ include file="../common/head.jsp"%>

<link rel="stylesheet" href="${ctx}/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<link rel="stylesheet" href="${ctx}/css/pagination.css" type="text/css">
<script type="text/javascript" src="${ctx}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/js/pagination.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/js/lhgdialog/lhgdialog.min.js?skin=blue"></script>

<script type="text/javascript">

	$(document).ready(function() {
		var searchData = {state:'enable'};
		searchPage("${ctx}/product/getProducts",searchData);
		trCss();
	});

	function search() {
		//var searchData = {loginName:$("#loginName").val()};
		searchPage("${ctx}/product/getProducts",$("#productForm").serialize());
	}

	function trCss() {
		$(".list_table tr").live("mouseover", function() {
			$(this).addClass("tr3");
		});
		$(".list_table tr").live("mouseout", function() {
			$(this).removeClass("tr3");
		});
	}
	
	var productDialog;
	function addOrUpdateProduct(method) {
		productDialog = $.dialog({
			title : '新增产品',
			width : '380px',
			height : '350px',
			content : 'url:' + ctx + '/product/' + method,
			button : [ {
				name : '确定',
				callback : function() {
					if ('addProductInit' == method) {
						productDialog.content.saveProduct();
					} else {
						productDialog.content.updateProduct();
					}
					return false;
				},
				focus : true
			} ],
			cancelVal : '关闭',
			cancel : true
		});
	}


</script>

</head>

<body>
	<div class="top">
		<%@ include file="../common/top.jsp"%>
		<div class="main" >
			<div class="page_title">产品管理</div>
			<form id="productForm" action="">
			<div id="Search">
				充值类型：<select name="productType">
				<option value="">全部</option>
				<option value="phone">话费</option>
				<option value="flow">流量</option>
				</select>
				运营商：<select name="isp">
				<option value="">全部</option>
				<option value="CM">中国移动</option>
				<option value="CU">中国联通</option>
				<option value="CT">中国电信</option>
				</select>
				产品状态：<select name="state">
				<option value="enable">有效</option>
				<option value="">全部</option>
				<option value="disable">停用</option>
				</select>
				 <input type="button" onclick="search()" value="查询" /> <input type="button"
					onclick="addOrUpdateProduct('addProductInit')" value="新增" />
			</div>
			</form>
			<div id="Searchresult"></div>
			<div id="Pagination"></div>

			
		</div>
	</div>
</body>

</html>