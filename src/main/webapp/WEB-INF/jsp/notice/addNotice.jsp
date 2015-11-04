<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>中舜充值系统</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${ctx}/bootstrap/css/bootstrap.css" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${ctx}/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>

<script type="text/javascript">
	var editor ;
	var api = frameElement.api, W = api.opener;

	function saveNotice() {
		var stem = editor.getData();
		editor.updateElement();
		$.ajax({
			type : "POST",
			url : "${ctx}/notice/addNotice",
			data : {title:$("#noticeTitle").val(),content:stem,needShow:$("#needShow").val()},
			success : function(msg) {
				if (msg == 'success') {
					alert('提交成功！');
					W.noticeDialog.time(0.1);
				} else {
					alert(msg);
				}

			}
		});
		
	}
	function updateNotice() {
		var stem = editor.getData();
		var id = "${notice.id}"
		if(id==""){
			alert("通知id不能为空！");
			return;
		}
		editor.updateElement();
		$.ajax({
			type : "POST",
			url : "${ctx}/notice/updateNotice",
			data : {id:id,title:$("#noticeTitle").val(),content:stem,needShow:$("#needShow").val()},
			success : function(msg) {
				if (msg == 'success') {
					alert('提交成功！');
					W.noticeDialog.time(0.1);
				} else {
					alert(msg);
				}

			}
		});
	}

	$(document).ready(function() {
		if('${notice.needShow}'=='need'){
			$("#needShow").attr("checked",'true');
		}
	});
	

</script>
</head>

<body>
<div class="container">
	<h3>通知 <br/> <small></small></h3>
	标题：<input type="text" id="noticeTitle" value="${notice.title }"/>
	是否前台显示：<input type="checkbox" value="need" id="needShow" />
 <textarea cols="80" id="editor1" name="editor1" rows="10">${notice.content }</textarea>
     <script type="text/javascript">
      editor = CKEDITOR.replace( 'editor1',{allowedContent: true,language : 'zh-cn'});
   </script>
</div>
	
</body>

</html>