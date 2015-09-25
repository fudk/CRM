<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>中舜充值系统</title>

<%@ include file="../../common/head.jsp"%>
<script type="text/javascript" src="${ctx}/js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${ctx}/js/validationEngine/jquery.validationEngine-zh_CN.js"></script>
<link rel="stylesheet" href="${ctx}/js/validationEngine/css/validationEngine.jquery.css" type="text/css">

<script type="text/javascript">
var api = frameElement.api, W = api.opener;
function updateRes() {
	if($("#resourceForm").validationEngine("validate")){
		$.ajax({
			type : "POST",
			url : "${ctx}/system/resource/updateResource",
			data : $("#resourceForm").serialize() ,
			success : function(msg) {
				if(msg == 'success'){
					alert('保存成功！');
				}
				W.updateResSuc($("#resId").val(),$("#resName").val())
				W.apiDialog.time(0.1);
				//W.apiDialog.close();
			}
		});
	}
}

$(document).ready(function() {
	showSelect();
});

function showSelect(){
	$("#resType").val("${resource.resType }");
	
}
</script>
</head>

<body>
<form id="resourceForm">
	<table>
		<tr>
			<td align="right">资源编码:</td>
			<td><input type="text" name="resId" id="resId" readonly="readonly" value="${resource.resId }"/></td>
		</tr>
		<tr>
			<td align="right">资源名称:</td>
			<td><input type="text" id="resName" name="resName" value="${resource.resName }" class="  "/></td>
		</tr>
		<tr>
			<td align="right">资源地址:</td>
			<td><input type="text" id="resUrl" name="resUrl" value="${resource.resUrl }" class="validate[maxSize[500]]"/></td>
		</tr>
		<tr>
			<td align="right">数据权限:</td>
			<td><input type="text" id="dataAccess" name="dataAccess" value="${resource.dataAccess }" class="validate[maxSize[50]]"/></td>
		</tr>
		<tr>
			<td align="right">资源排序:</td>
			<td><input type="text" id="resOrder" name="resOrder" value="${resource.resOrder }" readonly="readonly" /></td>
		</tr>
		<tr>
			<td align="right">父节点编码:</td>
			<td><input type="text" id="parentId" name="parentId" value="${resource.parentId }" readonly="readonly" /></td>
		</tr>
		<tr>
			<td align="right">层级:</td>
			<td><input type="text" id="level" name="level" value="${resource.level }" /></td>
		</tr>
		<tr>
			<td align="right">资源类型:</td>
			<td><select id="resType" name="resType" class="validate[required]">
			<option value="">请选择</option>
			<option value="left">左边显示</option>
			<option value="top">顶部显示</option>
			<option value="all">全部显示</option></select></td>
		</tr>
		
	</table>
</form>
</body>

</html>