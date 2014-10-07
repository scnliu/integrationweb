function itemClick(eventSrc, link){
	var table = eventSrc.parentNode;
	var trs = table.getElementsByTagName('tr');
	for(var i = 0; i < trs.length; i++){
		var tr = trs[i];
		if(tr == eventSrc){
			tr.className = "menuItemTRSelected";
			tr.oldClassName = "menuItemTRSelected";
		}else{
			tr.className = "menuItemTR";
			tr.oldClassName = "menuItemTR";
		}
		top.document.getElementById('mainFrame').src = link;
	}
}

function tdOut(eventSrc){
	eventSrc.className = 'menuItemTD';
	eventSrc.parentNode.className = eventSrc.parentNode.oldClassName;
}

function tdOver(eventSrc){
	eventSrc.className = 'menuItemTDOver';
	eventSrc.parentNode.oldClassName = eventSrc.parentNode.className;
	eventSrc.parentNode.className = 'menuItemTROver';
}