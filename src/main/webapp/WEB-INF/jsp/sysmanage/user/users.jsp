<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>

<table width="100%" border="0" class="list_table" cellpadding="0" cellspacing="0" totalCount="${page.totalCount }" pageSize="${page.pageSize }">
	<tr>
    <th>序号</th>
    <th>登录账号</th>
    <th>用户名</th>
    <th>邮箱</th>
    <th>话费余额</th>
    <th>流量余额</th>
    <th>创建时间</th>
    <th>用户类型</th>
    <th>最后登录时间</th>
    <th>操作</th>
  </tr>
	<c:forEach items="${users}" var="user" varStatus="status">
		<tr>
			<td><c:out value="${status.count+page.pageSize*(page.currentPage-1)}"/></td>
			<td><c:out value="${user.loginName}"/></td>
			<td><c:out value="${user.userName}"/></td>
			<td><c:out value="${user.userEmail}"/></td>
			<td><c:out value="${user.balance}"/></td>
			<td><c:out value="${user.flowBalance}"/></td>
			<td><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>
			<c:if test="${user.userType == '1'}">启用</c:if>
			<c:if test="${user.userType == '0'}">停用</c:if>
			</td>
			<td><fmt:formatDate value="${user.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>
			<c:if test="${fn:contains(roleIds,'1,') || fn:contains(roleIds,'2,') }">
   				<a href="javascript:addOrUpdateUser('updateUserInit/${user.userId}')">编辑</a>
   				<a href="javascript:recharge('${user.userId}','${user.loginName}')">充值</a>	
			</c:if>
			<c:if test="${fn:contains(roleIds,'1,') }">
   				<a href="javascript:changeRole('${user.userId}')">授权</a>
			</c:if>
			</td>
		</tr>
	</c:forEach>
</table>
