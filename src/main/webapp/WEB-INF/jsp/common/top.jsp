<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="base.jsp"%>
<script type="text/javascript">
	var timeout = 500;
	var menuitem;
	var closetimer;
	$(document).ready(function() {
		showTopMenu();
		
		$("#sddm div").mouseover(function() {
			mcancelclosetime();
		}).mouseout(function() {
			mclosetime();
		});
		$("a[atype=menu]").mouseover(function() {
			if(closetimer){
				mcancelclosetime();
				mclose();
			}
			menuitem = $(this).parent().find("div");
			menuitem.css("visibility","visible");
		}).mouseout(function() {
			mclosetime();
		});
		
	});
	
	function mclosetime()
	{
		closetimer = window.setTimeout(mclose, timeout);
	}
	function mclose()
	{
		if(menuitem) menuitem.css("visibility","hidden");
	}
	function mcancelclosetime()
	{
		if(closetimer)
		{
			window.clearTimeout(closetimer);
			closetimer = null;
		}
	}
	
	function showTopMenu(){
		var resTopList = ${resTopList};
		var i=0;
		while (i<5){//最多递归5次
			resTopList = getTopMenu(resTopList);
			if($(resTopList).length<1){
				i=5;
			}
		  	i++;
		}
	}
	
	function getTopMenu(topreslist){
		var arr = new Array();
		$(topreslist).each(function(i,n){
			if(n.parentId == 0){
				if($("#top"+n.resId).length==0){
					$("#sddm").append("<li><a href='${ctx}"+n.resUrl+"' class='drop' atype='menu'>"+n.resName+"</a><div id='top"+n.resId+"'></div></li>");
				}
			}else{
				if($("#top"+n.parentId).length>0){
					$("#top"+n.parentId).append(" <a href='${ctx}"+n.resUrl+"'>"+n.resName+"</a>");
				}else{
					arr.push(n);
				}
			}
			
		});
		return arr;
	}
</script>
<div class="top_content">
	<div class="logo">
		<img src="${ctx}/images/logo.jpg" width="202" height="66" alt="志晴CRM" />
	</div>
	<div class="top_contain">
		<img src="${ctx}/images/top_ad.jpg" width="246" height="63" />
	</div>
	<div class="login">${sessionScope.user.userName }，欢迎您
		<a href="${ctx }/logout">注销</a> <a href="#">帮助</a><br />
	</div>
</div>

<div class="menu_bar">
	<div class="nav-box">

		<ul id="sddm">
			<li><a href="${ctx}/main" class="select" atype="menu">首页</a></li>
		</ul>

		<div class="nav-line" style="left: 92px; width: 64px;"></div>
	</div>
	<div class="search_bar">
		<!-- <form action="#" method="get" class="search_form">
			站内搜索： <input type="text" class="search_input"
				value="请输入买家昵称\收货人姓名\手机号码"
				onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
				onBlur="if(!value){value=defaultValue;this.style.color='#999'}"
				style="color: #999999" /> <input type="submit" value="搜索"
				class="sbtn">
		</form> -->
	</div>
</div>




