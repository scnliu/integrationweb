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
function openWindow(elem, isMaxWindow) {
	var title = $(elem).attr('title');
	var href_ = $(elem).attr('url');
	var windowId = 'window_' + $(elem).attr('id');
	if (top)
		top.showContent(title, href_, windowId, isMaxWindow);
	else
		showContent(title, href_, windowId, isMaxWindow);
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