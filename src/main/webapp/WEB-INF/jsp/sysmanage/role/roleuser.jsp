<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>中舜充值系统</title>
<%@ include file="../../common/checktreehead.jsp"%>

<script type="text/javascript">
var zNodes = ${roletree};			
var dValueKey_=${userId };
var dType_ = 'user',sType_ = 'role',objTree_='roletree',alertValue_='请选择用户！';

</script>
</head>

<body>
	<div>
		<ul id="roletree" class="ztree"></ul>
	</div>
</body>

</html>