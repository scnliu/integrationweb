function selectItems(id){
	var unselected = document.getElementById(id + '_avail');
	var selected = document.getElementById(id);
	transferSelected(unselected, selected);
	changImg(unselected, selected);
	sortItem(unselected);
	sortItem(selected);
}


function unselectItems(id){
	var unselected = document.getElementById(id + '_avail');
	var selected = document.getElementById(id);
	transferSelected(selected, unselected);
	changImg(unselected, selected);
	sortItem(unselected);
	sortItem(selected);
}

function transferSelected(src, target){
	var srcOptions = src.options;
	var targetOptions = target.options;
	for(var i = 0; i < srcOptions.length; i++){
		var option = srcOptions[i];
		if(option.selected){
			srcOptions[i] = null;
			targetOptions[targetOptions.length] = option;
			i--;
		}
	}
	for(var i = 0; i < targetOptions.length; i++){
		targetOptions[i].selected = false;
	}
}

function sortItem(sel)
{
    var selLength = sel.options.length;
    var arr = new Array();
    var arrLength;

    //将所有Option放入array
    for(var i=0;i<selLength;i++)
    {
        arr[i] = sel.options[i];
    }
    arrLength = arr.length;

    arr.sort(fnSortByValue);//排序
    //先将原先的Option删除
    while(selLength--)
    {
        sel.options[selLength] = null;
    }
    //将经过排序的Option放回Select中
    for(i=0;i<arrLength;i++)
    {
        fnAdd(sel,arr[i].text,arr[i].value);
        //sel.add(new Option(arr[i].text,arr[i].value));
    }
}
function fnSortByValue(a,b)
{
	var aComp = parseInt(a.value.toString());
    var bComp = parseInt(b.value.toString());
    
    if (aComp < bComp)
        return -1;
    if (aComp > bComp)
        return 1;
    return 0;
}

function fnAdd(oListbox, sName, sValue) 
{
    var oOption = document.createElement("option");
    oOption.appendChild(document.createTextNode(sName));

    if (arguments.length == 3) 
    {
        oOption.setAttribute("value", sValue);
    }

    oListbox.appendChild(oOption);
}


function changImg(unselected, selected){
	var options1 = unselected.options;
	var options2 = selected.options;
	
	var img = document.getElementById('_2leftImg');
	var imgSrc = img.src;
	if(options1.length == 0){
		img.src = imgSrc.substring(0, imgSrc.length - 5) + "3.gif";
	}else{
		img.src = imgSrc.substring(0, imgSrc.length - 5) + "1.gif";
	}
	
	var img = document.getElementById('_2rightImg');
	var imgSrc = img.src;
	
	if(options2.length == 0){
		img.src = imgSrc.substring(0, imgSrc.length - 5) + "4.gif";
	}else{
		img.src = imgSrc.substring(0, imgSrc.length - 5) + "2.gif";
	}
}

//以下均为拖拽框选所所用的方法
//要使用必须声明已下的全局变量，同时也必须声明所用的DIV数组
//var move=false; 
//var onMove=false;
//var tmpDivs;
//var x = 0;
//var y = 0;
//var divId = "";
//var selDivId = "";
//var curDiv="";
function O(div) {
	this.id = div.id;
	this.x = div.style.pixelLeft;
	this.y = div.style.pixelTop;
	this.width = div.style.pixelWidth;
	this.height = div.style.pixelHeight;
}

function initDivs(startNum,endNum,s,divArray) {
	for(var i = startNum; i <= endNum; i++) {
		var div =dojo.byId(s+i);
		if(div!=null){
			obj = new O(div);
			divArray.push(obj);
		}
	}
}

function isTouch(dragObj, tmpObj) {
	var lx = parseInt(dragObj.x) - parseInt(tmpObj.width);
	var rx = parseInt(dragObj.x) + parseInt(dragObj.width);
	var ty = parseInt(dragObj.y) - parseInt(tmpObj.height);
	var by = parseInt(dragObj.y) + parseInt(dragObj.height);
	//alert(lx + "=" + rx + "=" + ty + "=" + by + "\n" + dragObj.x + "=" + dragObj.y);
	if((tmpObj.x > lx && tmpObj.x < rx) && (tmpObj.y > ty && tmpObj.y < by)) {
		return true;
	} else {
		return false;
	}
}

function touchDiv(div) {
	var dragObj = new O(div);
	for(var i = 0; i < tmpDivs.length; i++) {
		var tmpObj=tmpDivs[i];
		if(isTouch(dragObj,tmpObj)) {
			//alert(nowObj.x + "---" + leftX + "---" + rightX + "|||" + nowObj.y + "---" + topY + "---" + bottomY);
			//alert(tmpObj.id);
			if(selDivId.indexOf(tmpObj.id + ",") == -1) {
				selDivId = selDivId + tmpObj.id + ",";
			}
			//return tmpObj.id;
		} 
		//else {
			// selDivId.replace(tmpObj.id, "");
			//return tmpObj.id;
		//}
	}
	//return 0;
}

function startDrag(e,mainId,divArray) {
	curDiv=mainId.id; 
	tmpDivs = divArray;
	//dojo.connect(mainId, "onmousemove", "drag");	 
	mainId.attachEvent("onmousemove",drag); 
		if(!move) {
			var dragDiv = dojo.byId(curDiv+"_dragging"); 
			x = e.clientX;
			y = e.clientY+document.documentElement.scrollTop;
			dragDiv.style.left = x;
			dragDiv.style.top = y;
			move=true;
			onMove = true;
		}
} 


function drag(e) {
	if(move) {
		var dragDiv = dojo.byId(curDiv+"_dragging");
		if(e.clientX - x >= 0) {
			dragDiv.style.width = e.clientX - dragDiv.style.left.replace("px","");
			dragDiv.style.left = x;
		} else {
			dragDiv.style.width = x - e.clientX;
			dragDiv.style.left = e.clientX;
		}
		if(e.clientY+document.documentElement.scrollTop - y >= 0) {
			dragDiv.style.height = e.clientY+document.documentElement.scrollTop - dragDiv.style.top.replace("px","");
			dragDiv.style.top = y;
		} else {
			dragDiv.style.height = y - e.clientY-document.documentElement.scrollTop;
			dragDiv.style.top = e.clientY+document.documentElement.scrollTop;
		}
		dragDiv.style.display = "block";
//		touchDiv(dragDiv);
	}
}

function stopDrag(e,mainId) {
		if(move) {
			touchDiv(dojo.byId(curDiv+"_dragging"));
			var dragDiv = dojo.byId(curDiv+"_dragging");
			var lt = dragDiv.style.left.replace("px","") + "," + dragDiv.style.top.replace("px","");
			var lb = dragDiv.style.left.replace("px","") + "," + (parseInt(dragDiv.style.top.replace("px","")) + parseInt(dragDiv.style.height.replace("px","")));
			var rt = (parseInt(dragDiv.style.left.replace("px","")) + parseInt(dragDiv.style.width.replace("px",""))) + "," + dragDiv.style.top.replace("px","");
			var rb = (parseInt(dragDiv.style.left.replace("px","")) + parseInt(dragDiv.style.width.replace("px",""))) + "," + (parseInt(dragDiv.style.top.replace("px","")) + parseInt(dragDiv.style.height.replace("px","")));
			//alert("左上:(" + lt + ")右上:(" + rt + ")\n左下:(" + lb + ")右下:(" + rb + ")");
			dragDiv.style.display = "none";
			dragDiv.style.width = "0px";
			dragDiv.style.height = "0px";
			x = 0;
			y = 0;
			move=false;
			onMove = false;
		}
	//dojo.disconnect(mainId, "onmousemove", "drag");  
		mainId.detachEvent("onmousemove",drag); 
	selectNodes(selDivId);
	selDivId="";
	curDiv="";
	
//	selectNode(dojo.byId(tmpObj.id));
}
function selectNodes(selDivId){
	var isDel=true;
	var tmpNode;
	var selectNode=selDivId.split(","); 
	for(var i=0;i<selectNode.length;i++){
		tmpNode=document.getElementById(selectNode[i]);
		if(tmpNode!=null){
			if(tmpNode.className=="dojoDndItem")
			isDel=false;
			tmpNode.className="dojoDndItemSelected";
		}
	}
	if(isDel){
		for(var i=0;i<selectNode.length;i++){
			tmpNode=document.getElementById(selectNode[i]);
			if(tmpNode!=null)tmpNode.className="dojoDndItem";
		}
	}						
	
}