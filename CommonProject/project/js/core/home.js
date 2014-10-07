var idx = 1;
$(function() {
	$("<div id='window_booker' class='window_booker'></div>").appendTo(
			document.body);
});
function showContent(title, href, windowId, isMaxWindow) {	
	if (!windowId)
		windowId = 'notify_wd' + idx;
	var dg = $('#window_booker').window( {
		id : windowId,
		title : title,
		width : 880,
		height : 520,
		url : href, 
		z : 100,
		withinBrowserWindow : true,
		checkBoundary : true,
		isMax : isMaxWindow ? true : false,
		animationSpeed : 800
	});
	idx++;
	return dg;
}
function openWindow2(elem, isMaxWindow) {
	var title = $(elem).attr('title2');
	if(!title){
		title = $(elem).attr('title');
	}
	var href_ = $(elem).attr('url');
	var icon = $(elem).attr('bottomIcon');
	
	if("" == href_){
		notify("该功能正在开发中...");				
		return;
	}
	
	var windowId = 'window_' + $(elem).attr('id');	
	
	if (top){	
		var wind = top.showContent(title, href_, windowId, isMaxWindow);	
		top.$("#bottom_" + windowId).find("a").css("background-image","url("+icon+")"); 
	} else{
		showContent(title, href_, windowId, isMaxWindow);
	} 	   	 
}
function changeUrl(elem) {
	var url = $(elem).attr('url');
	if (url) {
		$(
				"<form id='forwardto' name='forwardto' action='" + url
						+ "' method='post' target='_self'></form>").appendTo(
				document.body);
		document.getElementById('forwardto').action = url;
		var d = document.getElementById('forwardto').action;
		document.getElementById('forwardto').submit();
		// window.location.href=url;
		// $('#forwardto').get().submit();
	}
}
function closeIframeDlg() {
	if (parent.iframeDlg)
		parent.iframeDlg.hide();
}

function returnTop(ele) {
	// window.console.log(parent.mainIframe);
	// alert(parent.$('#mainIframe').attr('src'));
	// alert(parent.mainIframe.src);
	var url = $(ele).attr('url');
	if (top)
		top.$('#mainIframe').attr('src', url);
	else
		$('#mainIframe').attr('src', url);
}

function expand(context) {
	if (!$('#leftTd').attr('collapse')) {
		$('#leftTd').hide();
		document.getElementById('expandpic').src = context + "/head/a2.gif";
		$('#splitA').width(9);
		$('#leftTd').attr('collapse', true);
	} else {
		$('#leftTd').removeAttr('collapse');
		$('#leftTd').show();
		$('#splitA').width(9);
		document.getElementById('expandpic').src = context + "/head/a1.gif";
		// $('#leftTd').css('width','70px');
	}
}

/**
 * 系统初始化
 */
$(document).ready(function() {
	$('#bottomBarM').css('width', $(document).width() - 202);

	//setInterval("showTime()", 1000);

	initDeskTop();

	$('.start').bind('click', function() {
		$('.startMenu').show();
	});
	
	$('.monitor').bind('click', serviceMsg);

	$('.theme').bind('click', theme);
	$('.scene').bind('click', scene);

	$('.status').click(showDesk);

	// logo位置
		logoRes();

	});

function changeWin() {
	var width = $(document).width();
	$('#bottomBarM').css('width', width - 202);

	logoRes();
}

window.onbeforeunload = doBeforeUnLoad;

function doBeforeUnLoad() {
	// jQuery.get(_appContext + "/login/login.do?action=logout&exit=1");
}

function logoRes() {
	$('#logo').css( {
		left : $(document).width() - 330,
		top : 100
	});
}

function initDeskTop() {
	deskTop(1);
	deskTop(2);
	deskTop(3);
	deskTop(4);
	deskTop(5);
	deskTop(6);
}

function deskTop(idx) {
	//var iconPath = "/images/desktop1/sts/";
	// alert(iconPath);
	try {
		
		// alert(desk0DivArr.length);
	} catch (e) {
		// alert(e);
	}
	var deskDivArr = $("div[id^='desk"+idx+"Div']");
	for ( var i = 0; i < deskDivArr.length; i++) {
		var deskDiv = deskDivArr[i];
		
		$("#" + deskDiv.id).click(function() {
			var url = $(this).attr('url');
			//var title = $(this).attr('title');
			//var icon = getIconPath2(iconPath, "bottomIcon_1_0" + i);
			/*
			var dg = $('#desktop').window( {
				id : $(this).attr('id'),
				title : title,
				width : 1000,
				height : 600,
				bottomIcon : icon,
				url : url
			});*/
			
			if("" == url){
				notify("该功能正在开发中...");				
				return;
			}			
			if("ok" == url){				
				notify("该功能为独立运行的小系统，若有需要，请联系我们！");
				return;
			}
			
			var beta = $(this).attr('beta');
			if("http://www.eshinetech.com" == url || beta){
				var ths = this;
				askFunc("该功能正在整合中...",function(){
					openWindow2(ths,true);
				});
			}else{			 
				openWindow2(this,true);
			}
			 
		});
	}
}

/**
 * 窗口选择
 */

$(document).bind("mouseup", function() {
	$('.startMenu').hide();
});

var isOpen = true;

/**
 * 显示桌面
 */
function showDesk() {
	if (isOpen) {
		$('.window_panel').hide();
		isOpen = false;
	} else {
		$('.window_panel').show();
		isOpen = true;
	}
}

/**
 * 时间显示
 */
function showTime() {
	/*var timeDiv = $('#time');
	var dateDiv = $('#date');
	var myDate = new Date();
	var date = myDate.getFullYear();
	var month = myDate.getMonth() + 1;
	var day = myDate.getDate();
	var time = myDate.toLocaleTimeString();
	var num = time.lastIndexOf(":");
	time = time.substring(0, num);
	timeDiv.html(time);
	dateDiv.html(date + "/" + month + "/" + day);*/
}

function _help(context) {
	document.getElementById('_testd_iframe').src = context
			+ "/login/login.do?action=help";
	$.prompt("", {
		title : '新手指导',
		width : 430,
		height : 280,
		prefix : 'jqismooth',
		href : context + '/login/login.do?action=help',
		buttons : {
			ok : {
				value : '确定',
				iconCls : 'icon-ok'
			}
		}
	});
}

function _about(context) {
	document.getElementById('_testd_iframe').src = context
			+ "/login/login.do?action=about";
	$.prompt("", {
		title : '关于 系统',
		width : 430,
		height : 280,
		prefix : 'jqismooth',
		href : context + "/login/login.do?action=about",
		buttons : {
			ok : {
				value : '确定',
				iconCls : 'icon-ok'
			}
		}
	});
}
function _contact(context) {
	document.getElementById('_testd_iframe').src = context
			+ "/login/login.do?action=contact";
	$.prompt("", {
		title : '联系我们',
		width : 600,
		height : 400,
		prefix : 'jqismooth',
		href : context + "/login/login.do?action=contact",
		buttons : {
			ok : {
				value : '确定',
				iconCls : 'icon-ok'
			}
		}
	});
}

function _bookmark() {
	var ua = navigator.userAgent.toLowerCase();
	var title = "index.html";
	var url = "http://192.168.0.31:8088/webeshine";
	if ($.browser.mozilla && window.sidebar) {
		window.sidebar.addPanel(title, url, "");
	} else if ($.browser.msie && window.external) {
		window.external.AddFavorite(url, title);
	} else if (ua.indexOf("chrome") >= 0) { // Chrome
		notify("此浏览器不支持该功能");
	} else if ($.browser.safari || ua.indexOf("safari") >= 0) { // Safari
		notify("此浏览器不支持该功能");
	} else if ($.browser.opera || ua.indexOf("opera") >= 0) { // Opera Hotlist
		notify("此浏览器不支持该功能");
	}
}

/**
 * 系统运行状态
 */
function serviceMsg() {
//	$("<div id='serviceMsgWindow' class='window_booker'></div>").appendTo(
//			document.body);
//	var width=1000;
//	var height=500;
//	var top=(document.documentElement.clientHeight/2-height/2)*2/3;
//	var dg = $('#serviceMsgWindow').window( {
//		id : $(this).attr('id'),
//		title : "系统运行状态",
//		width : width,
//		height : height,
//		y :top,
//		z : 100,
//		showModal : false,
//		showFooter : false,
//		bookmarkable : false,
//		minimizable : false,
//		maximizable : false,
//		showBottomBar : false,
//		url : _appContext + '/views/npi/serviceMsg/serviceMsgCtrl.do?action=querySeviceMsg'
//	});
	showContent("系统运行状态",_appContext + '/views/npi/serviceMsg/serviceMsgCtrl.do?action=querySeviceMsg','serviceMsgWindow');
	//dg.show();
}
/**
 * 工作场景
 */
var sceneWindow;
function scene(){
	var t = '';
	if(userScene == ''){
		t ='切换工作区(共享)';
		createSceneWindow(t);
	}else if(userScene == userCode){
		t ='切换工作区(个人)';
		createSceneWindow(t);
	}else if(userScene != userCode && userScene.length>0){
		jQuery.ajax({
			url:_appContext + '/org/orgManager.do?action=getAjaxOrgName',//请求的action路径
			data : {'orgId':userScene},
			type: 'POST',
			dataType: "json",
			error: function () {//请求失败处理函数
			},
			success:function(data,status){ //请求成功后处理函数。
				t = data.orgName;
				createSceneWindow('切换工作区('+t+')');
			}
		});
	}
	
}

function createSceneWindow(t){
	$("<div id='sceneWindow' class='window_booker'></div>").appendTo(
			document.body);
	var width=560;
	var height=500;
	var top=(document.documentElement.clientHeight/2-height/2)*2/3;
	sceneWindow = $('#sceneWindow').window( {
		id : $(this).attr('id'),
		title : t,
		width : width,
		height : height,
		y :top,
		showModal : false,
		showFooter : false,
		bookmarkable : false,
		minimizable : false,
		maximizable : false,
		showBottomBar : false,
		url : _appContext + '/org/orgManager.do?action=getScene'
	});
}
/**
 * 主题设置
 */
function theme() {
//	$("<div id='themeWindow' class='window_booker'></div>").appendTo(
//			document.body);
//	var width=560;
//	var height=500;
//	var top=(document.documentElement.clientHeight/2-height/2)*2/3;
//	var dg = $('#themeWindow').window( {
//		id : $(this).attr('id'),
//		title : "主题设置",
//		width : width,
//		height : height,
//		y :top,
//		showModal : false,
//		showFooter : false,
//		bookmarkable : false,
//		minimizable : false,
//		maximizable : false,
//		z : 10,
//		url : _appContext + '/login/login.do?action=themes'
//	});
	//dg.show();
	showContent("主题设置",_appContext + '/login/login.do?action=themes','themeWindow');
	
}

function toEditPersonInfo(){
	$("<div id='editPersonInfo_div' class='window_booker'></div>").appendTo(
			document.body);
	var width=560;
	var height=360;
	var top=(document.documentElement.clientHeight/2-height/2)*2/3;
	var dg = $('#editPersonInfo_div').window( {
		id : $(this).attr('id'),
		title : "信息修改",
		width : width,
		height : height,
		y : top,
		showModal : false,
		showFooter : false,
		bookmarkable : false,
		minimizable : false,
		maximizable : false,
		showBottomBar : false,
		url : _appContext + '/user/userManager.do?action=toEditPersonInfo'
	});
	//dg.show();
}

function getIconPath(name) {
	var path = _appContext + "/images/desktop/bottom/" + name + ".png";
	return path;
}

function getIconPath2(path, name) {
	var path = _appContext + path + name + ".png";
	return path;
}
