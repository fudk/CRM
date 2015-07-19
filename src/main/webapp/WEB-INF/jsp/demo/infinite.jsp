<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="common/base.jsp"%>

<!DOCTYPE html>
<html>
 <head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

<title>iScroll demo: infinite scrolling</title>
<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/iscroll-infinite.js"></script>

<script type="text/javascript">
/******************************************************************************
 *
 * For the sake of keeping the script dependecy free I'm including a custom
 * AJAX function. You should probably use a third party plugin
 *
 */
function ajax (url, parms) {
	parms = parms || {};
	var req = new XMLHttpRequest(),
		post = parms.post || null,
		callback = parms.callback || null,
		timeout = parms.timeout || null;

	req.onreadystatechange = function () {
		if ( req.readyState != 4 ) return;

		// Error
		if ( req.status != 200 && req.status != 304 ) {
			if ( callback ) callback(false);
			return;
		}

		if ( callback ) callback(req.responseText);
	};

	if ( post ) {
		req.open('POST', url, true);
		req.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	} else {
		req.open('GET', url, true);
	}

	req.setRequestHeader('X-Requested-With', 'XMLHttpRequest');

	req.send(post);

	if ( timeout ) {
		setTimeout(function () {
			req.onreadystatechange = function () {};
			req.abort();
			if ( callback ) callback(false);
		}, timeout);
	}
}
/*
 *****************************************************************************/

var myScroll;

function loaded () {
	myScroll = new IScroll('#wrapper', {
		mouseWheel: true,
		infiniteElements: '#scroller .row',
		//infiniteLimit: 2000,
		dataset: requestData,
		dataFiller: updateContent,
		cacheSize: 1000
	});
}

function requestData (start, count) {
	$.ajax({
		type : "POST",
		url : "${ctx}/rechargeConsume/addConsume",
		data : {start:start,count:count},
		success : function(data) {
			data = JSON.parse(data);
			myScroll.updateCache(start, data);
		}
	});
	
	/* ajax('dataset.php?start=' + +start + '&count=' + +count, {
		callback: function (data) {
			data = JSON.parse(data);
			myScroll.updateCache(start, data);
		}
	}); */
}

function updateContent (el, data) {
	el.innerHTML = data;
}

document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

</script>

<style type="text/css">
* {
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

html {
	-ms-touch-action: none;
}

body,ul,li {
	padding: 0;
	margin: 0;
	border: 0;
}

body {
	font-size: 12px;
	font-family: ubuntu, helvetica, arial;
	overflow: hidden; /* this is important to prevent the whole page to bounce */
}

#header {
	position: absolute;
	z-index: 2;
	top: 0;
	left: 0;
	width: 100%;
	height: 45px;
	line-height: 45px;
	background: #CD235C;
	padding: 0;
	color: #eee;
	font-size: 20px;
	text-align: center;
	font-weight: bold;
}

#footer {
	position: absolute;
	z-index: 2;
	bottom: 0;
	left: 0;
	width: 100%;
	height: 48px;
	background: #444;
	padding: 0;
	border-top: 1px solid #444;
}

#wrapper {
	position: absolute;
	z-index: 1;
	top: 45px;
	bottom: 48px;
	left: 0;
	width: 100%;
	background: #ccc;
	overflow: hidden;
}

#scroller {
	position: absolute;
	z-index: 1;
	-webkit-tap-highlight-color: rgba(0,0,0,0);
	width: 100%;
	-webkit-transform: translateZ(0);
	-moz-transform: translateZ(0);
	-ms-transform: translateZ(0);
	-o-transform: translateZ(0);
	transform: translateZ(0);
	-webkit-touch-callout: none;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	-webkit-text-size-adjust: none;
	-moz-text-size-adjust: none;
	-ms-text-size-adjust: none;
	-o-text-size-adjust: none;
	text-size-adjust: none;
}

#scroller ul {
	list-style: none;
	padding: 0;
	margin: 0;
	width: 100%;
	text-align: left;
	position: relative;
}

#scroller li {
	position: absolute;
	width: 100%;
	top: 0;
	left: 0;
	-webkit-transform: translateZ(0);
	-moz-transform: translateZ(0);
	-ms-transform: translateZ(0);
	-o-transform: translateZ(0);
	transform: translateZ(0);
	padding: 0 10px;
	height: 40px;
	line-height: 40px;
	border-bottom: 1px solid #ccc;
	border-top: 1px solid #fff;
	background-color: #fafafa;
	font-size: 16px;
}

</style>
</head>
<body onload="loaded()">
<div id="header">iScroll</div>

<div id="wrapper">
	<div id="scroller">
		<ul>
			<li class="row">Row 1</li>
			<li class="row">Row 2</li>
			<li class="row">Row 3</li>
			<li class="row">Row 4</li>
			<li class="row">Row 5</li>
			<li class="row">Row 6</li>
			<li class="row">Row 7</li>
			<li class="row">Row 8</li>
			<li class="row">Row 9</li>
			<li class="row">Row 10</li>
			<li class="row">Row 11</li>
			<li class="row">Row 12</li>
			<li class="row">Row 13</li>
			<li class="row">Row 14</li>
			<li class="row">Row 15</li>

			<li class="row">Row 16</li>
			<li class="row">Row 17</li>
			<li class="row">Row 18</li>
			<li class="row">Row 19</li>
			<li class="row">Row 20</li>
			<li class="row">Row 21</li>
			<li class="row">Row 22</li>
			<li class="row">Row 23</li>
			<li class="row">Row 24</li>
			<li class="row">Row 25</li>
			<li class="row">Row 26</li>
			<li class="row">Row 27</li>
			<li class="row">Row 28</li>
			<li class="row">Row 29</li>
			<li class="row">Row 30</li>
		</ul>
	</div>
</div>

<div id="footer"></div>

</body>
</html>