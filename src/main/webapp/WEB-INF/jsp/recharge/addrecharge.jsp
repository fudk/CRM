<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>中舜充值系统</title>

<%@ include file="../common/head.jsp"%>

<script type="text/javascript" src="${ctx}/js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${ctx}/js/validationEngine/jquery.validationEngine-zh_CN.js"></script>
<link rel="stylesheet" href="${ctx}/js/validationEngine/css/validationEngine.jquery.css" type="text/css">

<script type="text/javascript">

	var api = frameElement.api, W = api.opener;

	function recharge() {
		if($("#rechargeForm").validationEngine("validate")){
			saveRecharge();
		}
	}
	function saveRecharge() {
		$.ajax({
			type : "POST",
			url : "${ctx}/rechargeConsume/addRecharge",
			data : $("#rechargeForm").serialize(),
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
		$("#rechargeForm").validationEngine({
			scroll : false,
			//binded:false,
			showArrow : false,
			showOneMessage : true
		//addPromptClass:"formError-noArrow formError-text"
		});
	});
	

</script>
</head>

<body>
	<form id="rechargeForm" action="${ctx}/rechargeConsume/addRecharge" method="POST">
		<table>
			<tr>
				<td align="right">用户编码:</td>
				<td><input type="text" id="userId" name="userId" readonly="readonly"
					value="${userId }" /></td>
			</tr>
			<tr>
				<td align="right">用户名:</td>
				<td><input type="text" id="loginName" value="${loginName }" disabled="disabled"/></td>
			</tr>
			<tr>
				<td align="right">充值类型:</td>
				<td><select name="type" id="type">
				<option value="phone">话费充值</option>
				<option value="flow">流量充值</option>
				</select></td>
			</tr>
			<tr>
				<td align="right">充值金额:</td>
				<td><input type="text" id="rechargeAmount"
					name="rechargeAmount" value="" class="validate[required,maxSize[10],custom[number]]" /></td>
			</tr>
			
		</table>
	</form>
	
</body>

</html>