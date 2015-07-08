<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>

<table width="100%" border="0" class="list_table" cellpadding="0" cellspacing="0" totalCount="${page.totalCount }" pageSize="${page.pageSize }">
	<tr>
    <th>序号</th>
    <th>手机号</th>
    <th>产品名称</th>
    <th>折扣</th>
    <th>单价</th>
    <th>数量</th>
    <th>金额</th>
    <th>实际支付</th>
    <th>余额</th>
    <th>运营商</th>
    <th>类型</th>
    <th>消费账户</th>
    <th>状态</th>
    <th>消费时间</th>
  </tr>
	<c:forEach items="${consumes}" var="consume" varStatus="status">
		<tr>
			<td><c:out value="${status.count+page.pageSize*(page.currentPage-1)}"/></td>
			<td><c:out value="${consume.consumePhone}"/></td>
			<td><c:out value="${consume.productName}"/></td>
			<td><c:out value="${consume.discount}"/></td>
			<td><c:out value="${consume.consumePrice}"/></td>
			<td><c:out value="${consume.consumeNum}"/></td>
			<td><c:out value="${consume.consumeAmount}"/></td>
			<td><c:out value="${consume.payment}"/></td>
			<td><c:out value="${consume.balance}"/></td>
			<td>
			<c:if test="${consume.isp == 'CM'}">中国移动</c:if>
			<c:if test="${consume.isp == 'CU'}">中国联通</c:if>
			<c:if test="${consume.isp == 'CT'}">中国电信</c:if>
			</td>
			<td>
			<c:if test="${consume.consumeType == 'phone'}">话费</c:if>
			<c:if test="${consume.consumeType == 'flow'}">流量</c:if>
			</td>
			<td><c:out value="${consume.userName}"/></td>
			<td><c:out value="${consume.state}"/></td>
			<td><fmt:formatDate value="${consume.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	</c:forEach>
</table>
