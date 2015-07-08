/**
 * 
 */

var paginationOpt = {
		pageflag : true,//是否需要初始化分页标签
		type : "POST",
		dataType : "html",
		searchresult :"Searchresult",
		pageObj:"table"
};
function searchPage(url, searchData,searchresult, pageObj) {
	paginationOpt.searchData = searchData;
	paginationOpt.url = url;
	paginationOpt.pageflag = true;
	if(pageObj){
		paginationOpt.pageObj = pageObj;
	}
	
	getPage(0);
}

function pageselectCallback(page_index, jq) {
	//alert(paginationOpt.pageflag + " " + page_index);
	//只初始化1次
	if (paginationOpt.pageflag) {
		paginationOpt.pageflag = false;
		return;
	}
	getPage(page_index);
}

function initPagination(total, itemsPerPage) {
	var pageOpt = {
		callback : pageselectCallback,
		items_per_page : itemsPerPage,//每页显示数量
		num_display_entries : 10,
		num_edge_entries : 2,
		prev_text : "上一页",
		next_text : "下一页"
	};
	if (paginationOpt.pageflag) {
		$("#Pagination").pagination(total, pageOpt);
	}
}

function getPage(page_index) {
	if(paginationOpt.searchData){
		paginationOpt.searchData.currentPage = (page_index + 1);
		if(!paginationOpt.searchData.currentPage){
			if(paginationOpt.searchData.indexOf("&currentPage")>0){
				paginationOpt.searchData = paginationOpt.searchData.substring(0, paginationOpt.searchData.indexOf("&currentPage"))
			}
			paginationOpt.searchData = paginationOpt.searchData +"&currentPage="+(page_index + 1)
		}
	}else{
		paginationOpt.searchData= {currentPage:(page_index + 1)};
	}
	
	$.ajax({
		type : paginationOpt.type,
		url : paginationOpt.url,
		data : paginationOpt.searchData,
		dataType : paginationOpt.dataType,
		success : function(data) {
			$("#"+paginationOpt.searchresult).empty().append(data);
			var total = $("#"+paginationOpt.searchresult).find(paginationOpt.pageObj).attr(
			"totalCount");
			var itemsPerPage = $("#"+paginationOpt.searchresult).find(paginationOpt.pageObj)
			.attr("pageSize");
			initPagination(total, itemsPerPage);
		}
	});

}