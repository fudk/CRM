<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新增用户</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<%@ include file="../../common/head.jsp"%>

<script type="text/javascript"
	src="${ctx}/js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${ctx}/js/UUID.js"></script>
<script type="text/javascript"
	src="${ctx}/js/validationEngine/jquery.validationEngine-zh_CN.js"></script>
<link rel="stylesheet"
	href="${ctx}/js/validationEngine/css/validationEngine.jquery.css"
	type="text/css" />

<script type="text/javascript">
	$(document).ready(function() {
		$("#addUserForm").validationEngine({
			scroll : false,
			//binded:false,
			showArrow : false,
			showOneMessage : false
		//addPromptClass:"formError-noArrow formError-text"
		});

		if('${isp}'!=''){
			show();
		}
		
	});

	function show() {
		var jsondatas = eval('${isp}');
		$.each(jsondatas, function(i, n) {
			if(n.type!='flow'){
				$("#" + n.isp.toLowerCase()).val(n.channelId);
			}else{
				$("#" + n.isp.toLowerCase()+"flow").val(n.channelId);
			}
			
			//alert("name:" + n.channelId + " value:" + n.isp);
		});
	}

	var api = frameElement.api, W = api.opener;

	function saveUser() {
		if ($("#addUserForm").validationEngine("validate")) {
			saveOrUdateUser("addUser");
		}
	}
	function updateUser() {
		if ($("#addUserForm").validationEngine("validate")) {
			saveOrUdateUser("updateUser");
		}
	}

	function saveOrUdateUser(method) {
		$.ajax({
			type : "POST",
			url : "${ctx}/system/user/" + method,
			data : $("#addUserForm").serialize(),
			success : function(msg) {
				if (msg == 'success') {
					alert('提交成功！');
					W.userDialog.time(0.1);
				} else {
					alert(msg);
				}

			}
		});
	}

	function creatUUID() {
		var uuid = UUID.prototype.createUUID();
		$("#sessionKey").val(uuid);
	}
</script>
</head>
<body>
	<form id="addUserForm" action="${ctx}/system/user/addUser"
		method="post">
		<table>
			<tr>
				<td align="right">企业名:</td>
				<td colspan="3"><input type="text" id="userName"
					name="userName" value="${updateUser.userName }" size="52"
					class="validate[required,maxSize[50]]" /> <input type="hidden"
					name="userId" value="${updateUser.userId }" /></td>
			</tr>
			<tr>
				<td align="right">登录名(手机):</td>
				<td><input type="text" id="loginName" name="loginName"
					value="${updateUser.loginName }" class="validate[required,custom[phone]]" /></td>
				<td align="right">密码:</td>
				<td><input type="password" id="userPas" name="userPas"
					value="${updateUser.userPas }"
					class="validate[required,minSize[6],maxSize[50]]" /></td>
			</tr>
			<tr>
				<td align="right">平台用户KEY:</td>
				<td><input type="text" id="platId" name="platId"
					value="${updateUser.platId }"
					class="validate[required,minSize[4],maxSize[50]]" /></td>
				<td align="right">KEY:</td>
				<td><input type="text" id="sessionKey" name="sessionKey"
					value="${updateUser.sessionKey }" class="validate[required]" /> <input
					type="button" onclick="creatUUID()" value="生成KEY" /></td>
			</tr>
			<tr>
				<td align="right">邮箱:</td>
				<td><input type="text" id="userEmail" name="userEmail"
					value="${updateUser.userEmail }" class="validate[custom[email]]" /></td>
				<td align="right">QQ:</td>
				<td><input type="text" id="userQq" name="userQq"
					value="${updateUser.userQq}" class="validate[maxSize[50]]" /></td>
			</tr>
			<tr>
				<td align="right">地址:</td>
				<td colspan="3"><input type="text" id="userAddress"
					name="userAddress" size="52" value="${updateUser.userAddress}"
					class="validate[maxSize[500]]" /></td>
			</tr>
			<tr>
				<td align="right">绑定IP:</td>
				<td><input type="text" id="permitIp" name="permitIp"
					value="${updateUser.permitIp}"
					class="validate[required,maxSize[100]]" /></td>
				<td align="right"><c:if test="${empty updateUser }">角色:</c:if></td>
				<td><c:if test="${empty updateUser && fn:contains(roleIds,'1,') }">
						<select name="roleId" id="roleId">
							<option value="3">普通用户</option>
							<option value="2">代理商</option>
							<option value="1">管理员</option>
						</select>
					</c:if> <c:if
						test="${empty updateUser && fn:contains(roleIds,'2,') && !fn:contains(roleIds,'1,')}">
						<select name="roleId" id="roleId">
							<option value="3">普通用户</option>
						</select>
					</c:if></td>
			</tr>
			<tr>
				<td align="right">备注:</td>
				<td colspan="3"><textarea name="remark" rows="3" cols="53"
						class="validate[maxSize[500]]">${updateUser.remark}</textarea></td>
			</tr>
			<tr>
				<td align="right">话费折扣:</td>
				<td><input type="text" id="discountPhone" name="discountPhone"
					value="${updateUser.discountPhone }" class="validate[required,maxSize[3],custom[integer]]" /></td>
				<td align="right">流量折扣:</td>
				<td><input type="text" id="discountFlow" name="discountFlow"
					value="${updateUser.discountFlow}" class="validate[required,maxSize[3],custom[integer]]" /></td>
			</tr>
			<c:if test="${fn:contains(roleIds,'1,') }">
			<tr>
				<td align="right">话费通道：</td>
				<td colspan="3">移动：<select name="cm" id="cm"
					class="validate[required]">
						<option value="">请选择</option>
						<c:forEach items="${CM }" var="channel">
							<option value="${channel.channelId }">${channel.channelName }</option>
						</c:forEach>
				</select> 联通： <select name="cu" id="cu" class="validate[required]">
						<option value="">请选择</option>
						<c:forEach items="${CU }" var="channel">
							<option value="${channel.channelId }">${channel.channelName }</option>
						</c:forEach>
				</select> 电信： <select name="ct" id="ct" class="validate[required]">
						<option value="">请选择</option>
						<c:forEach items="${CT }" var="channel">
							<option value="${channel.channelId }">${channel.channelName }</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td align="right">流量通道：</td>
				<td colspan="3">移动：<select name="cmflow" id="cmflow"
					class="validate[required]">
						<option value="">请选择</option>
						<c:forEach items="${CMflow }" var="channel">
							<option value="${channel.channelId }">${channel.channelName }</option>
						</c:forEach>
				</select> 联通： <select name="cuflow" id="cuflow" class="validate[required]">
						<option value="">请选择</option>
						<c:forEach items="${CUflow }" var="channel">
							<option value="${channel.channelId }">${channel.channelName }</option>
						</c:forEach>
				</select> 电信： <select name="ctflow" id="ctflow" class="validate[required]">
						<option value="">请选择</option>
						<c:forEach items="${CTflow }" var="channel">
							<option value="${channel.channelId }">${channel.channelName }</option>
						</c:forEach>
				</select></td>
			</tr>
			</c:if>
		</table>
	</form>

</body>
</html>