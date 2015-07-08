<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>

<table width="100%" border="0" class="list_table" cellpadding="0" cellspacing="0" totalCount="${page.totalCount }" pageSize="${page.pageSize }">
	<tr>
    <th>序号</th>
    <th>产品名称</th>
    <th>产品价值</th>
    <th>产品单价</th>
    <th>运营商</th>
    <th>产品状态</th>
    <th>产品类型</th>
    <th>有效期</th>
    <th>创建时间</th>
    <th>操作</th>
  </tr>
	<c:forEach items="${products}" var="product" varStatus="status">
		<tr>
			<td><c:out value="${status.count+page.pageSize*(page.currentPage-1)}"/></td>
			<td><c:out value="${product.productName}"/></td>
			<td><c:out value="${product.productValue}"/></td>
			<td><c:out value="${product.productPrice}"/></td>
			<td>
			<c:if test="${product.isp == 'CM'}">中国移动</c:if>
			<c:if test="${product.isp == 'CU'}">中国联通</c:if>
			<c:if test="${product.isp == 'CT'}">中国电信</c:if>
			</td>
			<td>
			<c:if test="${product.state == 'enable'}">启动</c:if>
			<c:if test="${product.state == 'disable'}">停用</c:if>
			</td>
			<td>
			<c:if test="${product.productType == 'phone'}">话费</c:if>
			<c:if test="${product.productType == 'flow'}">流量</c:if>
			</td>
			<td><c:out value="${product.productValidity}"/></td>
			<td><fmt:formatDate value="${product.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>
			<c:if test="${fn:contains(roleIds,'1,') }">
   				<a href="javascript:addOrUpdateProduct('updateProductInit/${product.productId}')">编辑</a>
			</c:if>
			</td>
		</tr>
	</c:forEach>
</table>
