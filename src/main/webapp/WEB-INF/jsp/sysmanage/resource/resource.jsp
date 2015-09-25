<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>中舜充值系统</title>

<%@ include file="../../common/head.jsp"%>

<link rel="stylesheet" href="${ctx}/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/lhgdialog/lhgdialog.min.js?skin=blue"></script>

<script type="text/javascript">
var setting = {
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		selectedMulti: false
	},
	edit: {
		enable: true,
		showRenameBtn: false,
		editNameSelectAll: true
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeClick: beforeClick,
		onClick: onClick,
		beforeDrag: beforeDrag,
		onDrop: onDrop,
		beforeDrop: beforeDrop,
		beforeEditName: beforeEditName,
		beforeRemove: beforeRemove,
		beforeRename: beforeRename,
		onRemove: onRemove,
		onRename: onRename
	}
};

var zNodes = ${restree};

var log, className = "dark";
function beforeClick(treeId, treeNode, clickFlag) {
	className = (className === "dark" ? "":"dark");
	return (treeNode.click != false);
}
function onClick(event, treeId, treeNode, clickFlag) {
	getRes(treeNode.id);
}
function beforeDrag(treeId, treeNodes) {
	for (var i=0,l=treeNodes.length; i<l; i++) {
		if (treeNodes[i].drag === false) {
			return false;
		}
	}
	return true;
}

var move ={
	TYPE_INNER: "inner",
	TYPE_PREV: "prev",
	TYPE_NEXT: "next"
}
function beforeDrop(treeId, treeNodes, targetNode, moveType) {
	
	/* for (var i=0,l=treeNodes.length; i<l; i++) {
		//alert(treeNodes[i].id)
		var index = zTree.getNodeIndex(treeNodes[i]);
		alert(index)
		//alert(treeNodes[i].isFirstNode)
	} */
	return targetNode ? targetNode.drop !== false : true;
}
function onDrop(event, treeId, treeNodes, targetNode, moveType){
	var zTree = $.fn.zTree.getZTreeObj("restree");
	
	var tId,pId,resorder;
	if(moveType == move.TYPE_INNER){
		pId = targetNode.id;
	}else {
		pId = targetNode.pId;
	}
	if(treeNodes.length>0){
		tId = treeNodes[0].id;
		resorder = zTree.getNodeIndex(treeNodes[0]);
	}
	if(!pId){
		pId = 0;
	}
	updateResPid(tId,pId,resorder)
}
function beforeEditName(treeId, treeNode) {
	className = (className === "dark" ? "":"dark");
	var zTree = $.fn.zTree.getZTreeObj("restree");
	zTree.selectNode(treeNode);
	return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
}
function beforeRemove(treeId, treeNode) {
	className = (className === "dark" ? "":"dark");
	var zTree = $.fn.zTree.getZTreeObj("restree");
	zTree.selectNode(treeNode);
	return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
}
function onRemove(e, treeId, treeNode) {
	delRes(treeNode.id)
}
function beforeRename(treeId, treeNode, newName, isCancel) {
	className = (className === "dark" ? "":"dark");
	if (newName.length == 0) {
		alert("节点名称不能为空.");
		var zTree = $.fn.zTree.getZTreeObj("restree");
		setTimeout(function(){zTree.editName(treeNode)}, 10);
		return false;
	}
	return true;
}
function onRename(e, treeId, treeNode, isCancel) {
	return true;
}
function getTime() {
	var now= new Date(),
	h=now.getHours(),
	m=now.getMinutes(),
	s=now.getSeconds(),
	ms=now.getMilliseconds();
	return (h+":"+m+":"+s+ " " +ms);
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		//增加是在本节点下增加节点，需要加1
		addRes(treeNode,treeNode.id,"new node"+(newCount++),(treeNode.level+1));
		return false;
	});
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};
function selectAll() {
	var zTree = $.fn.zTree.getZTreeObj("restree");
	zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
}

$(document).ready(function(){
	$.fn.zTree.init($("#restree"), setting, zNodes);
	$("#selectAll").bind("click", selectAll);
});

	function addRes(treeNode,pId,name,level) {
		$.ajax({
			type : "POST",
			url : "${ctx}/system/resource/addResource",
			data : {
				pId : pId,
				level : level,
				resName : name
			},
			dataType : "json",
			success : function(data) {
				var zTree = $.fn.zTree.getZTreeObj("restree");
				zTree.addNodes(treeNode, {id:data, pId:pId, name:name});
			}
		});
		
	}
	
	var apiDialog = null;
	function getRes(resId) {
		if(apiDialog){
			if(!apiDialog.closed){
				apiDialog.close();
			}
		}
		apiDialog = $.dialog({
			title : '修改节点',
			content : 'url:' + ctx + '/system/resource/getResource/'+ resId,
			button : [ {
				name : '确定',
				callback : function() {
					apiDialog.content.updateRes();
					return false;
				},
				focus : true
			} ],
			cancelVal : '关闭',
			cancel : true
		});
		
	}
	
	function updateResPid(tId,pId,resorder){
		$.ajax({
			type : "POST",
			url : "${ctx}/system/resource/updateResourceParent",
			data : {resId:tId,parentId:pId,resOrder:resorder} ,
			dataType : "html",
			success : function(data) {
			} 
		});
		
	}
	
	function updateResSuc(tid,resName){
		var zTree = $.fn.zTree.getZTreeObj("restree");
		var node = zTree.getNodeByParam("id", tid, null);
		zTree.editName(node);
		zTree.cancelEditName(resName);
	}
	
	function delRes(resId){
		$.ajax({
			type : "POST",
			url : "${ctx}/system/resource/delResource",
			data : {resId : resId},
			dataType : "html",
			success : function(data) {
				if (msg == 'success') {
					alert('充值成功！');
				}else{
					alert('充值失败！');
				}
			}
		});
	}
	
	
</SCRIPT>
<style type="text/css">
.ztree li span.button.add {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle
}
</style>
</HEAD>

<BODY>
	<div class="top">
		<%@ include file="../../common/top.jsp"%>
		<div class="main">
			<div class="page_title">资源管理</div>
			<div class="left">
				<ul id="restree" class="ztree"></ul>
			</div>
		</div>
	</div>
</body>
</html>

