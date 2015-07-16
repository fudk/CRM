<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>志晴CRM</title>

<%@ include file="../common/head.jsp"%>

<script type="text/javascript" src="${ctx}/js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${ctx}/js/validationEngine/jquery.validationEngine-zh_CN.js"></script>
<link rel="stylesheet" href="${ctx}/js/validationEngine/css/validationEngine.jquery.css" type="text/css">

<script type="text/javascript">

	var api = frameElement.api, W = api.opener;

	function consume() {
		$("#consumeForm").submit();
		/* if($("#consumeForm").validationEngine("validate")){
			saveConsume();
		} */
	}
	function saveConsume() {
		$.ajax({
			type : "POST",
			url : "${ctx}/rechargeConsume/addConsume",
			data : $("#consumeForm").serialize(),
			success : function(msg) {
				if (msg == 'success') {
					alert('充值成功！');
				}else{
					alert('充值失败！'+msg);
				}
				W.rechargeDialog.time(0.1);
			}
		});
	}

	$(document).ready(function() {
		$("#consumeForm").validationEngine({
			scroll : false,
			//binded:false,
			showArrow : false,
			showOneMessage : true
		//addPromptClass:"formError-noArrow formError-text"
		});
	});
	

</script>
<style> 
.div{margin-left: 30px;} 
/* css注释：设置使用对象选择器名为“.div”的字体大小为14px */ 
.span{font-size:16px;margin-left: 10px;color: blue;} 
.span1{font-size:14px;margin-left: 30px;} 
.span2{font-size:18px;color: red;} 
</style>

</head>

<body>
	<form id="consumeForm" action="${ctx}/rechargeConsume/addConsume" method="POST">
	<div class="div" >
	<span >产品名称:</span><span class="span">${product.productName }</span>
	<span class="span1">运营商:</span><span class="span"><c:if test="${product.isp == 'CM'}"> 中国移动</c:if>
	<c:if test="${product.isp == 'CU'}"> 中国联通</c:if>
	<c:if test="${product.isp == 'CT'}"> 中国电信</c:if></span>
	<span class="span1">类型:</span><span class="span"><c:if test="${product.productType == 'phone'}"> 话费</c:if>
	<c:if test="${product.productType == 'flow'}"> 流量</c:if></span>
	</div>
	<div class="div" >
		<span >有效期:</span><span class="span">${product.productValidity }</span>
		<span class="span1">价格:</span>
		<span class="span2" style="text-decoration:line-through">${product.productPrice}元</span>/
		<span class="span2">
		<c:if test="${product.productType == 'phone'}"><fmt:formatNumber value="${product.productPrice*sessionScope.user.discountPhone/100}" /></c:if>
				<c:if test="${product.productType == 'flow'}"><fmt:formatNumber value="${product.productPrice*sessionScope.user.discountFlow/100}" /></c:if>
				元</span>
	</div>
	<div class="div" >
		<span >详细说明:</span><span class="span">${product.remark }</span>
	</div>
	<br>
	<div class="div" >请输入手机号，多个手机号可用逗号、句号、空格、回车符等符号分隔：</div> 
	<div class="div" >如：13812348888,13912348888</div> 
	<input type="hidden" name="productId" id="productId" value="${product.productId }"/>
	<textarea name="consumePhones" id="consumePhones" class="validate[required]" style="margin-left:50px" rows="7" cols="50"></textarea>
	</form>
	
</body>

</html>