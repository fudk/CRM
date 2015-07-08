<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="base.jsp"%>
<script type="text/javascript">

	$(document).ready(function() {
		showMenu();
	});
	
	function showMenu(){
		var reslist = ${resList};
		var i=0;
		while (i<5){//最多递归5次
			reslist = getMenu(reslist,i);
			if($(reslist).length<=1){
				i=5;
			}
		  	i++;
		}
	}
	function getMenu(reslist,level){
		var arr = new Array();
		$(reslist).each(function(k,n){
			if(n){
				if(n.level == 0){
					$(".left_menu").append(" <dl id='dl"+n.resId+"'><dt>"+n.resName+"</dt></dl>");
				}else{
					if($("#dl"+n.parentId).length>0){
						$("#dl"+n.parentId).append(" <dd ><a href='${ctx}"+n.resUrl+"'>"+n.resName+"</a></dd>");
					}else{
						arr.push(n);
					}
				}
			}
		});
		return arr;
	}
    </script>

</head>

<div class="left_side_bar">
	<div class="msg_box">
		话费余额：<span class="blue_num">${sessionScope.user.balance }</span>元<br /> 
		流量余额：<span class="blue_num">${sessionScope.user.flowBalance }</span>元<br /> 
		<%-- 话费折扣：<span class="blue_num"><fmt:formatNumber value="${sessionScope.user.discountPhone/10}" /></span>折<br /> 
		流量折扣：<span class="blue_num"><fmt:formatNumber value="${sessionScope.user.discountFlow/10}" /></span>折<br />  --%>
		<div class="left_button">
			<a href="#"><img
				src="${ctx}/images/Renew.jpg" alt="软件续费" width="73" height="28" /></a>
		</div>
	</div>
	<div class="left_menu"></div>
</div>




