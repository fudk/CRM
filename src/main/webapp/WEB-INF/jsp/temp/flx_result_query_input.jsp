<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="GBK"%>
<html>
	<head>
		<title>订单查询</title>
		<link href="css/sdk.css" rel="stylesheet" type="text/css" />
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	</head>

	<body>
		<form name="form1" method="post" action="flx_result_query.jsp">
			<br>
			<center>

				<input type="hidden" name="merCert" value="">
				<font size=2 color=black face=Verdana><b>订单查询</b> </font>
				<br>
				<br>
				<table class="api">
					<tr>
						<td class="field">
							被查询充值记录请求号
						</td>
						<td>
							<input type="text" name="inq_req_id" maxlength='20' value="">
							<font color="red">*需要查询的商户请求交易流水号</font>
						</td>
					</tr>
					<tr>
						<td class="field">
							被查询充值记录请求日期
						</td>
						<td>
							<input type="text" name="inq_req_dt" maxlength='20' value="">
							<font color="red">*需要查询的商户请求交易日期</font>
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
