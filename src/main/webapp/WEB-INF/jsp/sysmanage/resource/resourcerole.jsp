<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>志晴CRM</title>
<%@ include file="../../common/checktreehead.jsp"%>

<script type="text/javascript">
var zNodes = ${restree};			
var dValueKey_=${roleId};
var dType_ = 'role',sType_ = 'resource',objTree_='restree',alertValue_='请选择角色！';

</script>
</head>

<body>
	<div>
		<ul id="restree" class="ztree"></ul>
	</div>
</body>

</html>