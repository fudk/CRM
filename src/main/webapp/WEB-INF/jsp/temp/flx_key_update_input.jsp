<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="GBK"%>
<html>
	<head>
		<title>密钥更新</title>
		<link href="css/sdk.css" rel="stylesheet" type="text/css" />
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	</head>

	<body>
		<form name="form1" method="post" action="flx_key_update.jsp">
			<br>
			<center>

				<input type="hidden" name="merCert" value="">
				<font size=2 color=black face=Verdana><b>密钥更新</b> </font>
				<br>
				<br>
				<table class="api">
					<tr>
						<td class="field">
							变更密钥
						</td>
						<td>
							<input type="text" name="newsignKey" maxlength='64' value="">
							<font color="red">*商户自定义密钥，只能以数字、大小写字母组成，长度不超过64位，修改成功后新密钥在1分钟内生效</font>
						</td>
					</tr>
					<tr>
						<td class="field">
						</td>
						<td>

							<input type="Submit" value="提交" id="Submit" name="submit" />
						</td>
					</tr>
				</table>
			</center>
			<a id="HomeLink" class="home" href="index.jsp">首页</a>
		</form>
	</body>
</html>
