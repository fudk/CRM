<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>中舜充值系统</title>

<%@ include file="../common/head.jsp"%>
<link rel="stylesheet" href="${ctx}/css/mainstyle.css" type="text/css">

<script type="text/javascript">

	var api = frameElement.api, W = api.opener;

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
	});
	

</script>

</head>

<body>
	<div class="Par" style="height:120px;">
		<ul id="phoneul">
			<c:forEach items="${tels}" var="tel" varStatus="status">
				<li class="active">${tel }</li>
			</c:forEach>
			<!-- <li value="10">10.00元</li>
			<li value="20">20.00元</li>
			<li value="30">30.00元</li>
			<li value="50">50.00元</li>
			<li value="100">100.00元</li>
			<li value="200">200.00元</li>
			<li value="300">300.00元</li>
			<li value="500">500.00元</li> -->
		</ul>
	</div>

</body>

</html>