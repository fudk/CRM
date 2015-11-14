<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>中舜充值系统</title>
<%@ include file="common/head.jsp"%>
<link rel="stylesheet" href="${ctx}/css/mainstyle.css" type="text/css">
<body>
	<div class="top">
		<%@ include file="common/top.jsp"%>
		<div class="main">
			<%-- <%@ include file="common/left.jsp"%> --%>
			<div class="content">
				<div class="title">
					<h4>
						充值入口 <i></i><b></b><i></i>
					</h4>
				</div>
				<form action="">
				<div class="charge">
					<div>
						<div class="select" style="height:50px;">
							<p>
								<input type="radio" value="phone" name="type" checked="checked"/> 话费充值
							</p>
							<p>
								<input type="radio" value="flow" name="type" /> 流量充值
							</p>
						</div>
						<div class="tel-num" style="height:50px;">
							<p>
								手机号码<input type="tel" name="tel" id="tel" value=""/>
								<input type="hidden" name="sendfile" id="sendfile" value="">
							</p>
						</div>
						<div class="Par" style="height:120px;">
						<input type="hidden" name="productValue" id="productValue" value="" />
							<p>面值</p>
							<ul id="phoneul">
								<li value="10">10.00元</li>
								<li value="20">20.00元</li>
								<li value="30">30.00元</li>
								<li value="50">50.00元</li>
								<li value="100">100.00元</li>
								<li value="200">200.00元</li>
								<li value="300">300.00元</li>
								<li value="500">500.00元</li>
							</ul>
							<ul id="flowul" style="display: none;">
								<li value="10">10M</li>
								<li value="20">20M</li>
								<li value="30">30M</li>
								<li value="50">50M</li>
								<li value="100">100M</li>
								<li value="200">200M</li>
								<li value="300">300M</li>
								<li value="500">500M</li>
								<li value="1024">1024M</li>
							</ul>
						</div>
						<div class="true-num" style="height:50px;">
							<p>
								实付金额<span id="amount">0.00</span> 元
							</p>
						</div>
						<div  style="height:50px;display: none;" id="errorMsg">
						</div>
					</div>

				</div>
				<div class="button-foot">
					<button type="button" onClick="saveConsumes()">充值</button>
					<button type="button" id="upload">批量导入</button>
					<input id="fileToUpload" style="display: none" type="file" name="upfile">
					<a href="javascript:showSendFile()">批量导入记录</a>
				</div>
				</form>
			</div>
		</div>

		<!-- 代码 结束 -->
	</div>
	
	<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgdialog.min.js?skin=blue"></script>
	<script type="text/javascript">
	
	var dialogObj;
	function showSendFile() {
		dialogObj = $.dialog({
			title : '批量导入记录',
			width : '750px',
			height : '500px',
			content : 'url:' + ctx + '/rechargeConsume/showSendFile?path=' + encodeURIComponent($("#sendfile").val()),
			cancelVal : '关闭',
			cancel : true
		});
	}
	
	function saveConsumes(){
		$("#errorMsg").hide();
		if($("input[name='type']:checked").val()==''){
			alert("充值类型不能为空！");
			return;
		}
		if($("#productValue").val()==''){
			alert("面值不能为空！");
			return;
		}
		if($("#tel").val()==''){
			alert("电话不能为空！");
			return;
		}
		$.ajax({
			type : "POST",
			url : "${ctx}/rechargeConsume/addConsumes",
			data : $("form").serialize(),
			success : function(data) {
				console.log(data);
				if (data.msg == 'success') {
					$("#amount").text(data.amount);
					alert('充值成功！');
				}else{
					$("#errorMsg").html(data.msg+"<br/>");
					if(data.errorMsgs != undefined){
						var jsondatas = eval(data.errorMsgs);
						$.each(jsondatas, function(i, n) {
							$("#errorMsg").append(n+"<br/>")					
							//alert("name:" + n.channelId + " value:" + n.isp);
						});
					}
					$("#errorMsg").show();
					alert('充值失败！'+data.msg);
				}
			}
		});
	}
	
	
	$(function(){  
		$("#tel").val("");
		$("#sendfile").val("");
		$("#productValue").val("");
		$("input[value='phone']").attr("checked","checked");
		
		$("input[type='radio']").click(function(){
			if($(this).val() == "flow"){
				$("#phoneul").hide();
				$("#flowul").show();
			}else{
				$("#flowul").hide();
				$("#phoneul").show();
			}
			
		});
		
		$("li").click(function(){
			$("li").removeClass("active");
			$(this).addClass("active");
			$("#productValue").val($(this).val());
		});
	    //点击打开文件选择器  
	    $("#upload").on("click", function() {  
	        $("#fileToUpload").click();  
	    });  
	      
	    //选择文件之后执行上传  
	    $('#fileToUpload').on('change', function() {  
	        $.ajaxFileUpload({  
	            url:"${ctx}/con/upload",  
	            secureuri:false,  
	            fileElementId:'fileToUpload',//file标签的id  
	            dataType: 'json',//返回数据的类型  
	            data:{fileType:"sendfile"},//一同上传的数据  
	            success: function (data, status) {  
	                //把图片替换  
	                var obj = jQuery.parseJSON(data);  
	                if(typeof(obj.status) != "undefined") {  
	                    if(obj.status == "success") {  
	                    	$("input[name='tel']").val("文件批量导入");
	                    	$("input[name='tel']").attr("disabled","disabled");
	                    	$("input[name='sendfile']").val(obj.filePath);
	                    } else {  
	                        alert(obj.msg);  
	                    }  
	                }
	               
	            },  
	            error: function (data, status, e) {  
	                alert(e);  
	            }  
	        });  
	    });  
	      
	});  
    </script>
</body>
</html>