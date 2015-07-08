<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登录</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript"
	src="${ctx}/js/validationEngine/jquery.validationEngine-zh_CN.js"></script>
<link rel="stylesheet"
	href="${ctx}/js/validationEngine/css/validationEngine.jquery.css"
	type="text/css">
<link href="${ctx}/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(document).ready(function() {
		$("input[type=text],input[type=password]").focusin(function() {
			$(this).addClass('focusInput');
		}).focusout(function() {
			$(this).removeClass('focusInput');
		});
		$("#loginName").focus();

		$("#login").validationEngine({
			showOneMessage : true
		});
		
		if('1'=='${reflag}'){
			alert('${msg}');
		}
		
	});
</script>

</head>
<body>
	<form action="loginmain" method="post" id="login">
		<div id="login">
			<div class="top_name"></div>
			<div class="login_content">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="login_table">
					<tr>
						<th width="110">用户名：</th>
						<td><input type="text" name="loginName" id="loginName" value="${loginName }"
							class="input1 validate[required,maxSize[50]]"></td>
					</tr>
					<tr>
						<th>密 码：</th>
						<td><input type="password" name="password" id="password"
							class="input1 validate[required,maxSize[50]]"></td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td><input name="登录" type="submit" id="登录"
							class="login_button" value="登录" /></td>
					</tr>
				</table>
			</div>
		</div>
	</form>

</body>
</html>