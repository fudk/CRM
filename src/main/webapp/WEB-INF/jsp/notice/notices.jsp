<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>

<table width="100%" border="0" class="list_table" cellpadding="0" cellspacing="0" totalCount="${page.totalCount }" pageSize="${page.pageSize }">
	<tr>
    <th>序号</th>
    <th>通知名称</th>
    <th>创建时间</th>
    <th>是否显示</th>
    <th>操作</th>
  </tr>
	<c:forEach items="${notices}" var="notice" varStatus="status">
		<tr>
			<td><c:out value="${status.count+page.pageSize*(page.currentPage-1)}"/></td>
			<td><c:out value="${notice.title}"/></td>
			<td><fmt:formatDate value="${notice.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><c:out value="${notice.needShow}"/></td>
			<td>
   				<a href="javascript:addOrUpdate('updateNoticeInit',${notice.id})">编辑</a>
			</td>
		</tr>
	</c:forEach>
</table>
