<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="base.jsp"%>
<script type="text/javascript">
$(document).ready(function(){  
	$("div[data-type=flowbox]").mouseout(function(){
		$(this).removeClass();
		$(this).addClass("flow_box1");
	}).mouseover(function(){
		$(this).removeClass();
		$(this).addClass("flow_box2");
	});
	//flow_box3
	var flowbox = ${flowbox}
	$("div[data-type=flowbox]:eq("+flowbox+")").addClass("flow_box3");
	$("div[data-type=flowbox]:eq("+flowbox+")").mouseout(function(){
		$(this).removeClass();
		$(this).addClass("flow_box3");
	});
	
});

</script>
	<div class="flow_box">
        <div class="flow_box1" data-type="flowbox">
          <div class="flow_img"><a href="#"><img src="${ctx}/images/index_ad1.jpg" width="68" height="68" /></a></div>
          <a href="${ctx}/sms/reminder/remindernotices">催付款</a></div>
        <div class="flow_box1" data-type="flowbox">
          <div class="flow_img"><a href="#"><img src="${ctx}/images/index_ad2.jpg" width="68" height="68" /></a></div>
          <a href="${ctx}/sms/reminder/hdcfnotices">          活动催付</a></div>
        <div class="flow_box1"  data-type="flowbox">
          <div class="flow_img"><a href="#"><img src="${ctx}/images/index_ad3.jpg" width="68" height="68" /></a></div>
          <a href="${ctx}/sms/reminder/fkggnotices">          付款关怀</a></div>
        <div class="flow_box1"  data-type="flowbox">
          <div class="flow_img"><a href="#"><img src="${ctx}/images/index_ad4.jpg" width="68" height="68" /></a></div>
          <a href="${ctx}/sms/reminder/fhtxnotices">          发货提醒</a></div>
        <div class="flow_box1"  data-type="flowbox">
          <div class="flow_img"><a href="#"><img src="${ctx}/images/index_ad5.jpg" width="68" height="68" /></a></div>
          <a href="${ctx}/sms/reminder/ddtxnotices">          到达提醒</a></div>
        <div class="flow_box1"  data-type="flowbox">
          <div class="flow_img"><a href="#"><img src="${ctx}/images/index_ad6.jpg" width="68" height="68" /></a></div>
          <a href="${ctx}/sms/reminder/qstxnotices">          签收提醒</a></div>
        <div class="flow_box1"  data-type="flowbox">
          <div class="flow_img"><a href="#"><img src="${ctx}/images/index_ad7.jpg" width="68" height="68" /></a></div>
          <a href="${ctx}/sms/reminder/tkggnotices">          退款关怀</a></div>
        <div class="flow_box1"  data-type="flowbox">
          <div class="flow_img"><a href="#"><img src="${ctx}/images/index_ad8.jpg" width="68" height="68" /></a></div>
          <a href="${ctx}/sms/reminder/pjggnotices">          评价关怀</a></div>
      </div>



