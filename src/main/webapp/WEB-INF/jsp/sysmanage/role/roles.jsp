<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>

<table width="100%" border="0" class="list_table" cellpadding="0"
	cellspacing="0">
	<tr>
		<th>角色编码</th>
		<th>角色名</th>
		<th>角色描述</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
		<c:forEach items="${roles}" var="role">
			<tr>
				<td><c:out value="${role.roleId}" /></td>
				<td><c:out value="${role.roleName}" /></td>
				<td></td>
				<td><a href="javascript:changeResource('${role.roleId}')">角色分配</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
