<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登录</title> 
<#include "/common/meta.html"/>
<link rel="stylesheet" type="text/css" media="screen" href="${ctx}/styles/screen.css" />
<script src="${ctx}/scripts/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${ctx}/scripts/jquery.validate.js" type="text/javascript"></script>
<script type="text/javascript">
		
	$(function() {
		// highlight 
		var elements = $("input[type!='submit'], textarea, select");
		elements.focus(function(){
			$(this).parents('li').addClass('highlight');
		});
		elements.blur(function(){
			$(this).parents('li').removeClass('highlight');
		});
		
		$("#forgotpassword").click(function() {
			$("#password").removeClass("required");
			$("#login").submit();
			$("#password").addClass("required");
			return false;
		});
		
		$("#login").validate()
	});
	</script>

</head>
<body>
	<div id="page">

		<div id="header">
			<h1>Login</h1>
		</div>

		<div id="content">
			<p id="status"></p>
			<form action="loginmain" method="post" id="login">
				<fieldset>
					<legend>User details</legend>
					<ul>
						<li><label for="loginName"><span class="required">username
						</span></label> <input id="loginName" name="loginName"
							class="text required" type="text" value="${(loginName)!''}"/> <label for="email"
							class="error">This must be a valid email address</label></li>

						<li><label for="password"><span class="required">Password</span></label>
							<input name="password" type="password" class="text required"
							id="password" minlength="4" maxlength="20" /></li>

						<li><label class="centered info"><a
								id="forgotpassword" href="#">Email my password...</a></label></li>
					</ul>
				</fieldset>

				<fieldset class="submit">
					<input type="submit" class="button" value="Login..." />
				</fieldset>

				<div class="clear"></div>
			</form>

		</div>
	</div>

</body>
</html>