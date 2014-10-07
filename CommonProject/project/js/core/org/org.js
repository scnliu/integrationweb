/**
 * 部门管理模块js

 */

/**********
 * 主页面  *
 **********/

//查询按钮
function mainQuery(){
	//document.forms[0]['tableModel.page.currentPage'].value = 1;
	//pageSetFirst();
	document.forms[0].action= _appContext + '/org/orgManager.do?action=queryOrg';
	document.forms[0].submit();
}

//新增按钮
function mainAdd(){
	document.forms[0].action= _appContext + '/org/orgManager.do?action=addOrg';
	document.forms[0].submit();
}

//编辑按钮
function mainEdit(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('请选择一条记录');
		return;
	}
	if(selectCount > 1){
		notify('只能选择一条记录');
		return;
	}
	document.forms[0].action= _appContext + '/org/orgManager.do?action=editOrg';
	document.forms[0].submit();
}

//删除按钮
function mainDelete(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('请选择要删除的记录');
		return;
	}
	askFunc('是否删除所选记录？',function(){
		document.forms[0].action= _appContext + '/org/orgManager.do?action=deleteOrg';
		document.forms[0].submit();
	});	
}

//启用
function mainStart(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('请选择要启用的记录');
		return;
	}
	askFunc('是否启用所选记录？',function(){
		document.forms[0].action= _appContext + '/org/orgManager.do?action=start';
		document.forms[0].submit();
	});	
}

//启用
function mainStop(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('请选择要停用的记录');
		return;
	}
	askFunc('是否停用所选记录？',function(){
		document.forms[0].action= _appContext + '/org/orgManager.do?action=stop';
		document.forms[0].submit();
	});	
}

//保存顺序
function mainSaveOrder(){
	setSelect(true);
	document.forms[0].action= _appContext + '/org/orgManager.do?action=saveOrder';
	document.forms[0].submit();
}

function mainReset(){
	var id = dojo.byId("id");
	id.textbox.value = "";
	var orgName = dojo.byId("orgName");
	orgName.textbox.value = "";
	var type = dojo.byId("org.type");
	type.resetValue("-1", "");
	var stop = dojo.byId("org.isStop");
	stop.resetValue("-1", "");
	var tel = dojo.byId("org.tel");
	tel.textbox.value = "";
	var descript = dojo.byId("descript");
	descript.textbox.value = "";
}

function up(e, id){
	var target = dojo.html.getEventTarget(e);
	var tr = target.parentNode.parentNode;
	var tBody = tr.parentNode;
	var preTr = tr.previousSibling;
	
	while(preTr != null && typeof preTr.tagName == 'undefined'){
		preTr = preTr.previousSibling;
	} 
	if(!preTr){
		alert('已经是第一行');
		return false;
	}
	//if(confirm('是否向上移动?')){	
		dojo.dom.insertBefore(tr,preTr);
	//}	
}

function down(e, id){
	var target = dojo.html.getEventTarget(e);
	var tr = target.parentNode.parentNode;
	var tBody = tr.parentNode;
	var nextTr = tr.nextSibling;
	while(nextTr != null && typeof nextTr.tagName == 'undefined'){
		nextTr = nextTr.nextSibling;
	} 
	
	if(!nextTr){
		alert('已经是最后一行');
		return false;
	}
	//if(confirm('是否向下移动?')){	
		dojo.dom.insertAfter(tr,nextTr);
	//}
}

/**
 * 检查部门ID
 */
function checkId(eventSrc){
	var lable = document.getElementById("L_ID");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*部门ID不能为空';
		return;
	}else{
		lable.innerHTML = '';
	}
	
	var url = _appContext + '/org/orgManager.do?action=exist&field=id&value=' + eventSrc.value;
	dojo.io.bind({
			url : url,
			preventCache : true, 
			load : function(type,data,e){
				var msg = '';
				if('true' == data){
					msg = '部门ID已经存在';
				}
				var lable = document.getElementById("L_ID");
				lable.innerHTML = msg;
				
			},
			error : function(type,data,e){
				
			},
			mimetype : "text/html",
			sync : false	
		});
}

/**
 * 检查组织机构代码
 */
function checkZzjgdm(eventSrc){
	var lable = document.getElementById("L_Zzjgdm");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*组织机构代码不能为空';
	}else{
		lable.innerHTML = '';
	}
}

/**
 * 检查部门简码
 */
function checkOrgCode(eventSrc){
	var lable = document.getElementById("L_OrgCode");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*部门简码不能为空';
	}else{
		lable.innerHTML = '';
	}	
}

/**
 * 检查部门简称
 */
function checkOrgName(eventSrc){
	var lable = document.getElementById("L_OrgName");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*部门简称不能为空';
	}else{
		lable.innerHTML = '';
	}
}

/**
 * 检查部门全称
 */
function checkOrgFullName(eventSrc){
	var lable = document.getElementById("L_OrgFullName");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*部门全称不能为空';
	}else{
		lable.innerHTML = '';
	}
}

/**
 * 检查部门编号
 */
function checkOrgRuleCode(eventSrc){
	var lable = document.getElementById("L_OrgRuleCode");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*部门编号不能为空';
		return;
	}else{
		lable.innerHTML = '';
	}
	var url = _appContext + '/org/orgManager.do?action=exist&field=orgRuleCode&value=' + eventSrc.value;
	dojo.io.bind({
			url : url,
			preventCache : true, 
			load : function(type,data,e){
				var msg = '';
				if('true' == data){
					msg = '部门编号已经存在';
				}
				var lable = document.getElementById("L_OrgRuleCode");
				lable.innerHTML = msg;
				
			},
			error : function(type,data,e){
				
			},
			mimetype : "text/html",
			sync : false	
		});
}

/**
 * 检查排列顺序
 */
function checkOrder(eventSrc){
	var lable = document.getElementById("L_Order");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*排列顺序不能为空';
	}else{
		lable.innerHTML = '';
	}
}

/***********
 * 新增页面 *
 ***********/
//保存按钮

function _checkInput(){
	var lable = document.getElementById("L_ID");
	var id = dojo.byId('id');
	if(dojo.string.trim(id.value) == ''){
		lable.innerHTML = '*部门ID不能为空';
		id.focus();
		return false;
	}else if(dojo.string.trim(lable.innerHTML) != ''){
		alert(lable.innerHTML);
		id.focus();
		return false;
	}
	
	lable = document.getElementById("L_Zzjgdm");
	var zzjgdm = dojo.byId("zzjgdm");
	if(dojo.string.trim(zzjgdm.value) == ''){
		alert("组织机构代码不能为空");
		lable.innerHTML = '*组织机构代码不能为空';
		zzjgdm.focus();
		return false;
	}
	
	lable = document.getElementById("L_OrgCode");
	var orgCode = dojo.byId("orgCode");
	if(dojo.string.trim(orgCode.value) == ''){
		alert("部门简码不能为空");
		lable.innerHTML = '*部门简码不能为空';
		orgCode.focus();
		return false;
	}
	
	lable = document.getElementById("L_OrgName");
	var orgName = dojo.byId("orgName");
	if(dojo.string.trim(orgName.value) == ''){
		alert("部门简称不能为空");
		lable.innerHTML = '*部门简称不能为空';
		orgName.focus();
		return false;
	}
	
	lable = document.getElementById("L_OrgFullName");
	var orgFullName = dojo.byId("orgFullName");
	if(dojo.string.trim(orgFullName.value) == ''){
		alert("部门全称不能为空");
		lable.innerHTML = '*部门全称不能为空';
		orgFullName.focus();
		return false;
	}
	
	lable = document.getElementById("L_OrgRuleCode");
	var orgRuleCode = dojo.byId("orgRuleCode");
	if(dojo.string.trim(orgRuleCode.value) == ''){
		alert("部门编号不能为空");
		lable.innerHTML = '*部门编号不能为空';
		orgRuleCode.focus();
		return false;
	}else if(dojo.string.trim(lable.innerHTML) != ''){
		alert(lable.innerHTML);
		orgRuleCode.focus();
		return false;
	}	
	
	lable = document.getElementById("L_Order");
	var order = dojo.byId("order");
	if(dojo.string.trim(order.value) == ''){
		alert("排列顺序不能为空");
		lable.innerHTML = '*排列顺序不能为空';
		order.focus();
		return false;
	}
	
	return true;
}

function addSave(){
	if(_checkInput()){
		document.forms[0].action= _appContext + '/org/orgManager.do?action=saveOrg';
		document.forms[0].submit();
	}
}

function addReset(){
	var id = dojo.byId("id");
	id.value = "";
	var orgName = dojo.byId("orgName");
	orgName.value = "";
	var type = dojo.byId("org.type");
	type.resetValue("1", "普通部门");
	var stop = dojo.byId("org.isStop");
	stop.resetValue("0", "否");
	var des = document.forms[0]["org.descript"].value = "";
}

/***********
 * 修改页面 *
 ***********/
//保存按钮
function editSave(){
	if(_checkInput()){
		document.forms[0].action= _appContext + '/org/orgManager.do?action=updateOrg';
		document.forms[0].submit();
	}
}

function editReset(){
	document.forms[0].reset();
	var type = dojo.byId("org.type");
	type.formReset();
	var stop = dojo.byId("org.isStop");
	stop.formReset();
	
}
/***********
 * 公共方法 *
 ***********/
 //返回主页面

function returnMain(){
	document.forms[0].action= _appContext + '/org/orgManager.do?action=queryOrg';
	document.forms[0].submit();
}

//重设
function resetValue(){
	document.forms[0].reset();
}

//关闭窗口
function closeWin(){
	window.close();
}

function sSelectOrg(){
	var orgName = window.opener.document.getElementById("orgName");
	var orgId = window.opener.document.getElementById("orgId");
	var orgIds = document.forms[0].orgIds;
	
	if(typeof orgIds.length != "undefined"){
		for(var i = 0; orgIds != null && i < orgIds.length; i++){	
			if(orgIds[i].checked == true){
				
				orgName.value = orgIds[i].getAttribute("nodeTitle");
				orgId.value = orgIds[i].value;
				window.close();
				return;
			}
		}
	}else{
		if(orgIds.checked == true){
			orgName.value = orgIds.getAttribute("nodeTitle");
			orgId.value = orgIds.value;
			
			window.close();
			return;
		}
	}
	
	alert('请选择部门');
}

function mSelectOrg(){
	var orgName = window.opener.document.getElementById("orgName");
	var orgId = window.opener.document.getElementById("orgId");
	var orgIds = document.forms[0].orgIds;
	var n = '';
	var v = '';
	
	for(var i = 0; orgIds != null && i < orgIds.length; i++){
		if(orgIds[i].checked == true){
			var isLeaf = orgIds[i].getAttribute("isLeaf");
			if(isLeaf == "true"){
				n += orgIds[i].getAttribute("nodeTitle") + ";";
				v += orgIds[i].value + ";";
			}
		}
	}
	if(n == ''){
		alert('请选择部门');
	}else{
		orgName.value = n;
		orgId.value = v;
		window.close();
	}
}
