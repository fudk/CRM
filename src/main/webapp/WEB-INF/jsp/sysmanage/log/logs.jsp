<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>

<table width="100%" border="0" class="list_table" cellpadding="0" cellspacing="0"  
 totalCount="${page.totalCount }" pageSize="${page.pageSize }">
	<tr>
		<th>序号</th>
		<th>日志参数</th>
		<th>日志名称</th>
		<th>日志类别</th>
		<th>日志时间</th>
		<th>访问IP</th>
		<th>耗时</th>
		<th>日志内容</th>
	</tr>
	</thead>
	<tbody>
		<c:forEach items="${logs}" var="logb" varStatus="status">
			<tr>
				<td><c:out value="${status.count+page.pageSize*(page.currentPage-1)}" /></td>
				<td><c:out value="${logb.logName}" /></td>
				<td><c:if test="${logb.logName == 'liul'}">流量接口1</c:if>
				<c:if test="${logb.logName == 'recharge'}">接口充值</c:if>
				<c:if test="${logb.logName == 'resultQuery'}">状态查询</c:if>
				<c:if test="${logb.logName == 'flowCallbackNotify'}">流量回调</c:if>
				<c:if test="${logb.logName == 'callbackNotify'}">话费回调</c:if>
				<c:if test="${logb.logName == 'shunpay'}">舜联接口</c:if>
				</td>
				<td>${logb.logType}</td>
				<td style="width: 50px;"><fmt:formatDate value="${logb.logTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${logb.fromIp }</td>
				<td>${logb.timeConsuming }</td>
				<td><div style="width:600px;overflow:hidden;">${logb.remark }</div> </td>
			</tr>
		</c:forEach>
	</tbody>
</table>
