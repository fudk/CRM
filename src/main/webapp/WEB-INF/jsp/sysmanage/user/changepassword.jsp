<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>志晴CRM</title>

<%@ include file="../../common/head.jsp"%>
<script type="text/javascript"
	src="${ctx}/js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript"
	src="${ctx}/js/validationEngine/jquery.validationEngine-zh_CN.js"></script>
<link rel="stylesheet"
	href="${ctx}/js/validationEngine/css/validationEngine.jquery.css"
	type="text/css" />

<script type="text/javascript">
	$(document).ready(function() {
		$("#passwordForm").validationEngine({
			scroll : false,
			//binded:false,
			showArrow : false,
			showOneMessage : true
		});
	});

	function updatePassword() {
		$.ajax({
			type : "POST",
			url : "${ctx}/system/user/updatePassword",
			data : $("#passwordForm").serialize(),
			success : function(msg) {
				if (msg == 'success') {
					alert('提交成功！');
					$("input[type=password]").val("");
				} else {
					alert(msg);
				}

			}
		});
	}
</script>

</head>

<body>
	<div class="top">
		<%@ include file="../../common/top.jsp"%>
		<div class="main">
			<%@ include file="../../common/left.jsp"%>
			<form id="passwordForm" action="" method="post">
				<div class="index_content">
					<div class="page_title">修改密码</div>
					<table>
						<tr>
							<td align="right">旧密码：</td>
							<td><input type="password" id="oldPassword"
								name="oldPassword" class="validate[required]" /></td>
						</tr>
						<tr>
							<td align="right">新密码：</td>
							<td><input type="password" id="newPassword"
								name="newPassword" class="validate[required]" /></td>
						</tr>
						<tr>
							<td align="right">确认密码：</td>
							<td><input type="password" id="configPassword"
								class="validate[required],equals[newPassword]" /></td>
						</tr>
					</table>
					<input type="button" value="提交" onclick="updatePassword();"/>
					<input type="reset" value="重置" />
				</div>
			</form>


		</div>
	</div>
</body>

</html>