var icon = {
	root		: _img + '/tree/web.gif',
	open		: _img + '/tree/open.gif',
	close		: _img + '/tree/close.gif',
	file		: _img + '/tree/file.gif',
	join		: _img + '/tree/T.gif',
	joinbottom	: _img + '/tree/L.gif',
	plus		: _img + '/tree/Tplus.gif',
	plusbottom	: _img + '/tree/Lplus.gif',
	minus		: _img + '/tree/Tminus.gif',
	minusbottom	: _img + '/tree/Lminus.gif',
	blank		: _img + '/tree/empty.gif',
	line		: _img + '/tree/I.gif'
}

function PreLoad(n){
	for(i in icon){
		var tem = n ? icon[i] : icon[i].src;
		icon[i] = new Image();
		icon[i].src = tem;
	}
	setTimeout("PreLoad()",20000);
}

PreLoad(true)

function TreeView(obj,hasCheckBox,target,openImg,closeImg,leafImg,checkBoxName,cbDisabled,inputType,tabType){
	this.selectedNode = null;
	this.obj = obj;
	this.Tree = new Node("com.yydt.gddc2.shareManage");
	this.Root = null;
	this.Nodes = new Array();
	this.target = target ? target : "mainFrame";
	this.hasCheckBox = hasCheckBox ? hasCheckBox : false;
	this.checkBoxName = checkBoxName ? checkBoxName : 'checkBoxName';
	this.cbDisabled = cbDisabled ? cbDisabled : false;
	this.inputType = inputType ? inputType : 'checkbox';
	this.tabType=tabType;
	icon.open.src = openImg ? openImg : icon.open.src;
	icon.close.src = closeImg ? closeImg : icon.close.src;
	icon.file.src = leafImg ? leafImg : icon.file.src;
}

function Node(id, pid, txt, link, title, key, check, target, xicon, hasInput){
	this.id = id;
	this.pid = pid
    this.key = key
    this.check = check
	this.txt = txt ? txt : "New Item"
	this.link = link
	this.title = title ? title:this.txt
	this.target = target 
	this.xicon = xicon
	this.indent = ""
	this.hasChild = false;
	this.lastNode = false;
	this.open = false;
	this.hasInput = typeof hasInput == 'undefined' || hasInput == null ? true : hasInput;
}

TreeView.prototype.add = function(id, pid, txt, link, title, key, check, xicon, hasInput){
	//target = target ? target : this.target;
	
	if(this.Nodes.length==0){
		pid="com.yydt.gddc2.shareManage";
	}
	this.Nodes[this.Nodes.length] = new Node(id, pid, txt, link, title, key, check, this.target, xicon, hasInput);
	if(pid == this.Tree.id){
		this.Nodes[this.Nodes.length-1].open = true;
	}
}

TreeView.prototype.InitNode = function(oNode){
	var last;
	for(i=0; i< this.Nodes.length; i++){
		if(this.Nodes[i].pid == oNode.id){
			oNode.hasChild = true;
		}
		if(this.Nodes[i].pid == oNode.pid){
			last = this.Nodes[i].id;
		}
	}
	if(last==oNode.id){
		oNode.lastNode = true;
	}
}

TreeView.prototype.DrawLine = function(pNode,oNode){
	oNode.indent = pNode.indent+(oNode.pid!=this.Tree.id&&oNode.pid!=this.Root.id?("<img align='top' src='"+(pNode.lastNode?icon.blank.src:icon.line.src)+"'>"):'');
	//alert(oNode.indent);
}


TreeView.prototype.DrawNode = function(nid,pNode){
	var str = "";
	var indents = "";
	var nNode = this.Nodes[nid];
	this.DrawLine(pNode,nNode);
	if(nNode.hasChild){
		indents = nNode.indent + "<a href='javascript:void(0)' onmouseover='setStatus(\"" + nNode.title + "\")' onmouseout='initStatus()' onclick='" + this.obj + ".Click(" + nid +",this)' >" + (this.Tree.id!=nNode.pid ? ("<img align='top' src='" + (nNode.lastNode ? (nNode.open ? icon.minusbottom.src : icon.plusbottom.src) : (nNode.open ? icon.minus.src : icon.plus.src)) + "' id='" + this.obj + "icon" + nid + "' border=0>") : "");
		indents += "<img id='" + this.obj+ "folder" + nid + "' align='top' src='" + ( typeof nNode.xicon != "undefined" && nNode.xicon != null && nNode.xicon != '' ? nNode.xicon : (nNode.open ? icon.open.src : icon.close.src)) + "'></a>";	
	} else{
		indents = nNode.indent+"<a href='javascript:void(0)' onmouseover='setStatus(\""+nNode.title+"\")' onmouseout='initStatus()' >"+(nNode.pid==this.Tree.id?'':("<img align='top' src='"+(nNode.lastNode?icon.joinbottom.src:icon.join.src)+"'>"));
		indents += "<img align='top' src='" + (nNode.xicon != "undefined" && nNode.xicon != null && nNode.xicon != '' ? nNode.xicon : icon.file.src) + "'></a>";
	}
	str+="<div class='node'><nobr>"+indents+this.DrawLink(nid)+"</nobr></div>";
	if(nNode.hasChild){
		str += "<div id='" + this.obj +  "Child"+nid+"' style='display:"+(nNode.open?"":"none")+"'>";
		str += this.DrawTree(nNode);
		str += "</div>";
	}
	return str;
}

TreeView.prototype.DrawTree = function(pNode){
	var str = "";
	for(var i=0; i < this.Nodes.length; i++){
		if(this.Nodes[i].pid == pNode.id){
			str += this.DrawNode(i,pNode);
		}
	}
	return str
}


TreeView.prototype.DrawLink = function(nid){
	var str = "";
	var oNode = this.Nodes[nid];

	if(this.hasCheckBox && oNode.hasInput){
		/*if(nid>0){*/
			if(this.inputType == 'checkbox'){
				str+=" <input type='checkbox' " + (this.cbDisabled == true ? 'disabled' : '') +" name='" + this.checkBoxName +"' " + ( oNode.check =='1' ? " checked='checked' " : "") + " value='"+oNode.key + "' nodeTitle='" + oNode.title + "' isLeaf='" + !oNode.hasChild +  "' id='" + this.obj + "check" + oNode.id + "' tabindex='1' title='"+oNode.title+"' onmouseover='setStatus(\""+oNode.title+"\")' onmouseout='initStatus()' onclick=' "+this.obj+".clickBox(" + nid +"); "+this.obj+".NClick("+nid+",this); "+this.obj+".select(this)'>"+oNode.txt+"</input>";
			}else if(this.inputType == 'radio'){
				str+=" <input type='radio' " + (this.cbDisabled == true ? 'disabled' : '') +" name='" + this.checkBoxName +"' " + ( oNode.check =='1' ? " checked='checked' " : "") + " value='"+oNode.key + "' nodeTitle='" + oNode.title + "' isLeaf='" + !oNode.hasChild +  "' id='" + this.obj + "check" + oNode.id + "' tabindex='1' title='"+oNode.title+"' onmouseover='setStatus(\""+oNode.title+"\")' onmouseout='initStatus()' onclick='"+this.obj+".NClick("+nid+",this); "+this.obj+".select(this)'>"+oNode.txt+"</input>";
			}
		/*}else{
			str+=" <span id='span" + nid + "' tabindex='1' title='"+oNode.title+"' onmouseover='setStatus(\""+oNode.title+"\")' onmouseout='initStatus()' onclick='"+this.obj+".NClick("+nid+",this); "+this.obj+".select(this)'>" + oNode.txt + "</span>";
		}*/
	}else{
		if(this.tabType=="tab"){
			str+= oNode.link? (" <a href=\"javascript:parent.Home_Main_Frame.makeTab('"+oNode.id+"','"+oNode.title+"','"+oNode.link+"');\" id='span" 
					+ nid + "' tabindex='1' title='"+oNode.title
					+"' onmouseover='setStatus(\""+oNode.title+"\")' onmouseout='initStatus()'"
					+" onclick='javascript: " + this.obj + ".s(" + nid + ");'"+">"+oNode.txt+"</a>"):oNode.txt;
		}else if(this.tabType=="notab"){
			str+= oNode.link? (" <a href='" + oNode.link + "'target='" + this.target + "' id='span" 
					+ nid + "' tabindex='1' title='"+oNode.title
					+"' onmouseover='setStatus(\""+oNode.title+"\")' onmouseout='initStatus()'"
					+" onclick='javascript: " + this.obj + ".s(" + nid + ");'"+">"+oNode.txt+"</a>"):oNode.txt;	
					
			
		}
		//alert(str);
	}
	return str;
}

// Highlights the selected node


TreeView.prototype.s = function(id) {

	//if (!this.config.useSelection) return;

	var cn = this.Nodes[id];

	//if (!this.config.folderLinks) return;
	
	if(this.selectedNode==null){
		enode = this.getObj('span'+id);
		enode.className = "nodeSel";
	}else{
		if (this.selectedNode != id) {
			eOld = this.getObj('span'+this.selectedNode);
			eOld.className = "node";
			eNew = this.getObj('span'+id);
			eNew.className = "nodeSel";
		}
	}
	this.selectedNode = id;
}

TreeView.prototype.NClick = function(nid,o){
    var nNode = this.Nodes[nid];
    o.blur();
    if(!nNode.hasChild){
    	return;
    }
    if(nNode.open){
    	return;
    } else{
    	this.expand(nid);
    }
}
TreeView.prototype.toString = function(){
	var str = "";
	for(var i=0;i < this.Nodes.length;i++){
		if(!this.Root){
			if(this.Nodes[i].pid == this.Tree.id){
				this.Root = this.Nodes[i];
			}
		}	
		this.InitNode(this.Nodes[i]);
	}
	str += this.DrawTree(this.Tree);
	return str;
}

TreeView.prototype.Click = function(nid,o){
	var nNode=this.Nodes[nid];
	o.blur();
	if(!nNode.hasChild){
		return;
	}
	if(nNode.open){
		this.collapse(nid);
	} else{
		this.expand(nid);
	}
}

TreeView.prototype.clickBox = function(nid){
	var ischecked = false;
    var nNode = this.Nodes[nid];

	var e = this.getObj(this.obj + 'check' + nNode.id);
    ischecked = e.checked;

	if(ischecked){
		nNode.check = '1';
		for(var i = 0; i < this.Nodes.length; i++){
			if(this.Nodes[i].id == nNode.pid){
				var ee = this.getObj(this.obj + 'check' + this.Nodes[i].id);
                ee.checked = ischecked;
				this.clickUnBox(i);
			}
		}
	}else{
		nNode.check = '0';
	}
	if(nNode.hasChild){
		for(var i = 1; i < this.Nodes.length;i++){
			if(this.Nodes[i].pid == nNode.id){
				var eee = this.getObj(this.obj + 'check' + this.Nodes[i].id);
                eee.checked = ischecked;
				this.clickDnBox(i);
			}
		}
	}
}

TreeView.prototype.clickUnBox = function(nid){
	var ischecked = false;
    var nNode = this.Nodes[nid];
	var e = this.getObj(this.obj + 'check' + nNode.id);
    ischecked = e.checked;
	if(ischecked){
		nNode.check = '1';
	}else{
		nNode.check = '0';
		return;
	}
	for(var i = 0; i < this.Nodes.length; i++){
		if(this.Nodes[i].id == nNode.pid){
			var ee = this.getObj(this.obj + 'check' + this.Nodes[i].id);
            ee.checked = ischecked;
			this.clickUnBox(i);
		}
	}
}

TreeView.prototype.clickDnBox = function(nid){
	var ischecked = false;
    var nNode = this.Nodes[nid];
	var e = this.getObj(this.obj + 'check' + nNode.id);
    ischecked = e.checked;
	if(ischecked){
		nNode.check = '1';
	}else{
		nNode.check = '0';
	}
	if(nNode.hasChild){
		for(var i = 1; i < this.Nodes.length; i++){
			if(this.Nodes[i].pid == nNode.id){
				var ee = this.getObj(this.obj + 'check' + this.Nodes[i].id);
				ee.checked = ischecked;
				this.clickDnBox(i);
			}
		}
	}
}
TreeView.prototype.expand = function(nid){
	var node = this.getObj(this.obj + 'Child'+nid);
	var oicon = this.getObj(this.obj + 'icon'+nid);
	var nNode = this.Nodes[nid];
	node.style.display = '';
	if(oicon){
		oicon.src = (nNode.lastNode ? icon.minusbottom.src : icon.minus.src);
	}
	if(typeof nNode.xicon == 'undefined' || nNode.xicon == null || nNode.xicon == ''){
		this.getObj(this.obj + "folder" + nid).src = icon.open.src;
	}
	nNode.open = true;
}

TreeView.prototype.collapse = function(nid){
	
	var node = this.getObj(this.obj + 'Child'+nid);
	var oicon = this.getObj(this.obj + 'icon'+nid);
	var nNode = this.Nodes[nid];
	node.style.display = 'none';
	if(oicon){
		oicon.src = (nNode.lastNode ? icon.plusbottom.src : icon.plus.src);
	}
	if(typeof nNode.xicon == 'undefined' || nNode.xicon == null || nNode.xicon == ''){
		this.getObj(this.obj + "folder" + nid).src = icon.close.src;
	}
	nNode.open = false;
}

TreeView.prototype.expandAll = function(){
	for(i=0;i<this.Nodes.length;i++){
		if(this.Nodes[i].hasChild){
			this.expand(i);
		}
	}
}

TreeView.prototype.collapseAll = function(){
	for(i = 0; i < this.Nodes.length; i++){
		if(this.Nodes[i].hasChild&&this.Nodes[i].pid != this.Tree.id && this.Nodes[i] != this.Root){
			this.collapse(i);
		}
	}
}

TreeView.prototype.select = function(o){
	if(!this.current){
		this.current = o;
	}
	this.current.className = "";
	o.className = "highlight";
	this.current = o;
	o.focus();
}

TreeView.prototype.getObj = function(uid){
	return document.all ? document.all(uid) : document.getElementById(uid);
}


TreeView.prototype.FindNodeAsTxt = function(str){
	for(i = 0; i< this.Nodes.length; i++){
		if(this.Nodes[i].txt == str){
			return this.Nodes[i];
		}
	}
	return null;
}


TreeView.prototype.FindNodeAsPid = function(pid){
	for(i=0; i < this.Nodes.length; i++){
		if(this.Nodes[i].id == pid) {
			return this.Nodes[i];
		}
	}
	return null;
}


TreeView.prototype.expandNode = function(txt, root){
  	var tmp = new Array();
  	var node = a.FindNodeAsTxt(txt);
  	if(node != null){
    	var pnode = this.FindNodeAsPid(node.pid);
    	if(pnode != null){
      		while(pnode.id != root){
        		tmp[tmp.length] = pnode.id;
        		pnode = this.FindNodeAsPid(pnode.pid);
      		}
      		for(var i = tmp.length - 1; i>-1; i--){
        		this.expand(tmp[i]-1);
      		}
      		if(node.hasChild){
        		this.expand(node.id - 1);
      		}else{
        		this.select(this.getObj(this.obj + 'check' + (node.id-1)));
      		}
    	}
  	}
}

setStatus = function(stateText){
	window.status = stateText;
}

initStatus = function(){
	window.status = '';
}
