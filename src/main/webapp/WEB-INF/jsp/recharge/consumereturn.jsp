<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>志晴CRM</title>

<%@ include file="../common/head.jsp"%>

<script type="text/javascript">

	var api = frameElement.api, W = api.opener;

	$(document).ready(function() {
		if('success'=='${msg}'){
			alert('提交成功');
			W.consumeDialog.time(0.1);
		}
	});
	

</script>
</head>

<body>
<c:if test="${msg != 'success'}">${msg}</c:if>

<c:if test="${!empty errorMsgs}">
<table width="100%" border="0" class="list_table" cellpadding="0" cellspacing="0">
	<tr>
    <th>序号</th>
    <th>错误原因</th>
  </tr>
	<c:forEach items="${errorMsgs}" var="errorMsg" varStatus="status">
		<tr>
			<td><c:out value="${status.count}"/></td>
			<td><c:out value="${errorMsg}"/></td>
		</tr>
	</c:forEach>
</table>
</c:if>	
</body>

</html>