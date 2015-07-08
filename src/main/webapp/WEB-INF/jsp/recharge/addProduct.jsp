<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>志晴CRM</title>

<%@ include file="../common/head.jsp"%>

<script type="text/javascript" src="${ctx}/js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${ctx}/js/validationEngine/jquery.validationEngine-zh_CN.js"></script>
<link rel="stylesheet" href="${ctx}/js/validationEngine/css/validationEngine.jquery.css" type="text/css">

<script type="text/javascript">

	var api = frameElement.api, W = api.opener;

	function saveProduct() {
		if(!$("#productForm").validationEngine("validate")){
			return;
		}
		$("#productForm").submit();
	}
	function updateProduct() {
		$("#productForm").attr("action",ctx+"/product/updateProduct");
		if(!$("#productForm").validationEngine("validate")){
			return;
		}
		$("#productForm").submit();
	}

	$(document).ready(function() {
		$("#productForm").validationEngine({
			scroll : false,
			//binded:false,
			showArrow : false,
			showOneMessage : true
		//addPromptClass:"formError-noArrow formError-text"
		});
	});
	

</script>
</head>

<body>
<div >
<form id="productForm" action="${ctx}/product/addProduct" method="POST" enctype="multipart/form-data">
		<table style="margin-left: 20Px;">
			<tr>
				<td align="right">产品名称:</td>
				<td><input type="text" id="productName" name="productName" 
					value="${product.productName }" />
					<input type="hidden" name="productId" id="productId" value="${product.productId }"/></td>
			</tr>
			<tr>
				<td align="right">产品价值:</td>
				<td><input type="text" id="productValue" name="productValue" value="${product.productValue }"/></td>
			</tr>
			<tr>
				<td align="right">产品价格:</td>
				<td><input type="text" id="productPrice" 
					name="productPrice" value="${product.productPrice }" class="validate[required,maxSize[10],custom[number]]" />
			</tr>
			<tr>
				<td align="right">产品状态:</td>
				<td><select name="state" id="state" class="validate[required]">
					<option value="enable" <c:if test="${product.state == 'enable'}">selected="selected"</c:if> >启动<ption>
					<option value="disable" <c:if test="${product.state == 'disable'}">selected="selected"</c:if> >停用</option>
				</select>
				</td>
			</tr>
			<tr>
				<td align="right">运营商:</td>
				<td><select name="isp" id="isp" class="validate[required]">
					<option value="">请选择</option>
					<option value="CM" <c:if test="${product.isp == 'CM'}">selected="selected"</c:if>>中国移动</option>
					<option value="CU" <c:if test="${product.isp == 'CU'}">selected="selected"</c:if>>中国联通</option>
					<option value="CT" <c:if test="${product.isp == 'CT'}">selected="selected"</c:if>>中国电信</option>
				</select>
			</tr>
			<tr>
				<td align="right">产品类型:</td>
				<td><select name="productType" id="productType" class="validate[required]">
					<option value="">请选择</option>
					<option value="phone" <c:if test="${product.productType == 'phone'}">selected="selected"</c:if>>话费</option>
					<option value="flow" <c:if test="${product.productType == 'flow'}">selected="selected"</c:if>>流量</option>
				</select>
				</td>
			</tr>
			<tr>
				<td align="right">有效期:</td>
				<td><input type="text" id="productValidity"
					name="productValidity" value="${product.productValidity}" class="validate[required,maxSize[100]]" /></td>
			</tr>
			<tr>
				<td align="right">显示图片:</td>
				<td><input type="file" id="imgfile"
					name="imgfile" value=""  /></td>
			</tr>
			<tr>
				<td align="right">详细说明:</td>
				<td><textarea rows="3" cols="20" name="remark" id="remark" class="validate[maxSize[200]]">${product.remark}</textarea>
				</td>
			</tr>
		</table>
	</form>

</div>
	
	
</body>

</html>