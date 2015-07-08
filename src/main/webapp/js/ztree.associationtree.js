/**
 * 
 */
var setting = {
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeCheck: beforeCheck
	}
};
	
function beforeCheck(treeId, treeNode) {
	if(treeNode.isParent){
		alert("请选择子节点！");
		return false;
	}
	return addOrDelAssociation(treeNode.id,treeNode.checked);
}


$(document).ready(function(){
	$.fn.zTree.init($("#"+objTree_), setting, zNodes);
	getAssociations(dValueKey_);
});

function addOrDelAssociation(sValue,checkType){
	var dValue = dValueKey_;
	if(dValue == ''){
		alert(alertValue_);
		return false;
	}
	var url_ = ctx+"/system/association/addAssociation"
	if(checkType){
		url_ = ctx+"/system/association/delAssociation"
	}
	$.post(url_, 
		{destinationType:dType_,destinationValue:dValue,
		sourceType:sType_,sourceValue:sValue} ,		
		function(data){
		   //alert(data);
	});
}

function getAssociations(dValue,callback){
	$.ajax({
		type : "POST",
		url : ctx+"/system/association/getAssociations",
		data : {destinationType:dType_,destinationValue:dValue,sourceType:sType_} ,
		dataType : "json",
		success : function(data) {
			if(data){
				var treeObj = $.fn.zTree.getZTreeObj(objTree_);
				$.each(data, function(i, association){
					var nodes = treeObj.getNodesByParam("id", association.sourceValue, null);
					if(nodes.length>0){
						for(var i=0;i<nodes.length;i++){
							treeObj.checkNode(nodes[i], true, true );
						}
					}
					if(typeof(callback) == 'function'){
						callback();
					}
				});
			}
		} 
	});
}
function cancelCheck(){
	var zTree = $.fn.zTree.getZTreeObj(objTree_);
	var nodes = zTree.getCheckedNodes(true);
	for (var i=0, l=nodes.length; i<l; i++) {
		zTree.checkNode(nodes[i], false, false);
	}
}