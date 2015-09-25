<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>中舜充值系统</title>
<%@ include file="common/head.jsp"%>
<link rel="stylesheet" href="${ctx}/css/pagination.css" type="text/css">
<script type="text/javascript" src="${ctx}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/js/pagination.js"></script>
<script type="text/javascript"
	src="${ctx}/js/lhgdialog/lhgdialog.min.js?skin=blue"></script>
<script type="text/javascript">

	$(document).ready(function() {
		var searchData = {pageSize:8};
		searchPage("${ctx}/product/getProductsMain",searchData,"","ul");
		
		$("select").change( function() {
			searchData.isp = $("#isp").val();
			searchData.productType = $("#productType").val();
			searchPage("${ctx}/product/getProductsMain",searchData,"","ul");
		});
	});

	var consumeDialog;
	function addConsume(productId) {
		consumeDialog = $.dialog({
			title : '用户消费',
			width : '530px',
			height : '350px',
			content : 'url:' + ctx + '/rechargeConsume/addConsumeInit/' + productId,
			button : [ {
				name : '确定',
				callback : function() {
					consumeDialog.content.consume();
					this.button({
		                name: '确定',
		                disabled: true
		            });
					return false;
				},
				focus : true
			} ],
			cancelVal : '关闭',
			cancel : true
		});
	}



</script>

<style type="text/css">
.liimg {border:1px solid #CCC; padding:2px;width: 200px}
.lileft {float: left;text-align: center;padding:10px 0 6px 15px;}
.ulmain {padding:10px 0 6px 15px;}
</style>
<body>
	<div class="top">
		<%@ include file="common/top.jsp"%>
		<div class="main">
			<%@ include file="common/left.jsp"%>
			<div class="index_content">
			<div>运营商：<select name="isp" id="isp"><option value="">全部</option>
			<option value="CM">移动</option><option value="CU">联通</option><option value="CT">电信</option></select> 
				类型：<select name="productType" id="productType"><option value="">全部</option>
				<option value="phone">话费</option><option value="flow">流量</option></select> 
			</div>
				<div id="Searchresult"></div>
				<div style="clear:both" id="Pagination"></div>
			</div>
		</div>
		
		<!-- 代码 结束 -->
	</div>
</body>
</html>