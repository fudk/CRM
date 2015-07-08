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
		if('${msg}'=='success'){
			alert('保存成功！');
		}else{
			alert('保存失败！${msg}');
		}
		W.productDialog.time(0.1);
	});
	

</script>
</head>

<body>

</body>

</html>