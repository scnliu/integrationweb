/**
 * 角色管理模块js

 */

/**********
 * 主页面  *
 **********/

//查询按钮
function mainQuery(){
	//document.forms[0]['tableModel.page.currentPage'].value = 1;
	pageSetFirst();
	document.forms[0].action= _appContext + '/role/roleManager.do?action=queryRole';
	document.forms[0].submit();
}

//新增按钮
function mainAdd(){
	window.location= _appContext + '/role/roleManager.do?action=addRole';
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
	document.forms[0].action= _appContext + '/role/roleManager.do?action=editRole';
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
		document.forms[0].action= _appContext + '/role/roleManager.do?action=deleteRole';
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
		document.forms[0].action= _appContext + '/role/roleManager.do?action=start';
		document.forms[0].submit();	
	});
}

function mainStop(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('请选择要停用的记录');
		return;
	}
	askFunc('是否启用所选记录？',function(){
		document.forms[0].action= _appContext + '/role/roleManager.do?action=stop';
		document.forms[0].submit();
	});
}
//分配权限
function mainPurview(){
	document.forms[0].action= _appContext + '/role/roleManager.do?action=assignPurview';
	document.forms[0].submit();
}

//保存权限
function savePurview(){
	document.forms[0].action= _appContext + '/role/roleManager.do?action=savePurview';
	document.forms[0].submit();
}

function mainReset(){
	var roleCode = dojo.byId("roleCode");
	roleCode.textbox.value = "";
	var roleName = dojo.byId("roleName");
	roleName.textbox.value = "";
	var type = dojo.byId("role.roleProp");
	type.resetValue("-1", "");
	var stop = dojo.byId("role.isStop");
	stop.resetValue("-1", "");
	var tel = dojo.byId("role.tel");
	tel.textbox.value = "";
	var descript = dojo.byId("descript");
	descript.textbox.value = "";
}

/***********
 * 公共方法 *
 ***********/
 //返回主页面

function returnMain(){
	window.location= _appContext + '/role/roleManager.do?action=queryRole';
}

//重设
function resetValue(){
	document.forms[0].reset();
}

//关闭窗口
function closeWin(){
	window.close();
}

/**
 * 选择部门
 */
function selectOrg(){
	openWin(_appContext + '/org/orgManager.do?action=selectOrg');
}
