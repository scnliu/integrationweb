/**
 * 功能管理模块js

 */

/**********
 * 主页面  *
 **********/

//查询按钮
function mainQuery(){
	pageSetFirst();
	document.forms[0].action= _appContext + '/func/funcManager.do?action=queryFunc';
	document.forms[0].submit();
}

//新增按钮
function mainAdd(){
	var _level = jQuery("#func_level").val();
	if(_level==0){
		notify('此为系统的根目录，无法添加，请选中具体的功能菜单！');
	}
	else{
		document.forms[0].action= _appContext + '/func/funcManager.do?action=addFunc';
		document.forms[0].submit();				
	}

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
	document.forms[0].action= _appContext + '/func/funcManager.do?action=editFunc';
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
		document.forms[0].action= _appContext + '/func/funcManager.do?action=deleteFunc';
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
		document.forms[0].action= _appContext + '/func/funcManager.do?action=start';
		document.forms[0].submit();
	});	
}

//停用
function mainStop(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('请选择要停用的记录');
		return;
	}
	askFunc('是否停用所选记录？',function(){
		document.forms[0].action= _appContext + '/func/funcManager.do?action=stop';
		document.forms[0].submit();
	});
}

//保存顺序
function mainSaveOrder(){
	setSelect(true);
	document.forms[0].action= _appContext + '/func/funcManager.do?action=saveOrder';
	document.forms[0].submit();
}

function mainReset(){
	var funcCode = dojo.byId("funcCode");
	funcCode.textbox.value = "";
	var funcName = dojo.byId("funcName");
	funcName.textbox.value = "";
	var type = dojo.byId("func.funcProp");
	type.resetValue("-1", "");
	var stop = dojo.byId("func.isStop");
	stop.resetValue("-1", "");
	var tel = dojo.byId("func.tel");
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
	if(confirm('是否向上移动?')){	
		dojo.dom.insertBefore(tr,preTr);
	}	
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
	if(confirm('是否向下移动?')){	
		dojo.dom.insertAfter(tr,nextTr);
	}
}

/***********
 * 新增页面 *
 ***********/
//保存按钮

function _checkInput(){
	var funcCode = dojo.byId("funcCode");
	if(funcCode.value == ""){
		alert("功能编号不能为空");
		return false;
	}
	var funcName = dojo.byId("funcName");
	if(funcName.value == ""){
		alert("功能名称不能为空");
		return false;
	}
	
	var context = dojo.byId("context");
	if( context != null && dojo.string.trim(context.value) == ""){
		alert("系统上下文不能为空");
		return false;
	}
	
	return true;
}

function addSave(){
	if(_checkInput()){
		document.forms[0].action= _appContext + '/func/funcManager.do?action=saveFunc';
		document.forms[0].submit();
	}
}

function addReset(){
	var funcCode = dojo.byId("funcCode");
	funcCode.value = "";
	var funcName = dojo.byId("funcName");
	funcName.value = "";
	var type = dojo.byId("func.funcProp");
	type.resetValue("1", "普通功能");
	var stop = dojo.byId("func.isStop");
	stop.resetValue("0", "否");
	var des = document.forms[0]["func.descript"].value = "";
}

/***********
 * 修改页面 *
 ***********/
//保存按钮
function editSave(){
	if(_checkInput()){
		document.forms[0].action= _appContext + '/func/funcManager.do?action=updateFunc';
		document.forms[0].submit();
	}
}

function editReset(){
	document.forms[0].reset();
	var type = dojo.byId("func.funcProp");
	type.formReset();
	var stop = dojo.byId("func.isStop");
	stop.formReset();
	
}
/***********
 * 公共方法 *
 ***********/
 //返回主页面

function returnMain(){
	document.forms[0].action= _appContext + '/func/funcManager.do?action=queryFunc';
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

