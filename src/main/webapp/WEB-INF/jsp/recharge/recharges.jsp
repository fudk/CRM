<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>

<table width="100%" border="0" class="list_table" cellpadding="0" cellspacing="0" totalCount="${page.totalCount }" pageSize="${page.pageSize }">
	<tr>
    <th>序号</th>
    <th>登录账号</th>
    <th>充值用户名</th>
    <th>充值类型</th>
    <th>充值金额</th>
    <th>操作员</th>
    <th>充值时间</th>
    <th width="180px">说明</th>
  </tr>
	<c:forEach items="${recharges}" var="recharge" varStatus="status">
		<tr>
			<td><c:out value="${status.count+page.pageSize*(page.currentPage-1)}"/></td>
			<td>${recharge.loginName}</td>
			<td>${recharge.userName}</td>
			<td><c:if test="${recharge.type == 'phone' }">话费</c:if><c:if test="${recharge.type == 'flow' }">流量</c:if></td>
			<td>${recharge.rechargeAmount}</td>
			<td>${recharge.operatorName}</td>
			<td><fmt:formatDate value="${recharge.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${recharge.remark}</td>
		</tr>
	</c:forEach>
</table>
