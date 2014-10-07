/**
 * 用户管理模块js

 */

/**********
 * 主页面  *
 **********/

//查询按钮
function mainQuery(){
	//document.forms[0]['tableModel.page.currentPage'].value = 1;
	pageSetFirst();
	document.forms[0].action= _appContext + '/user/userManager.do?action=queryUser';
	document.forms[0].submit();
}

//新增按钮
function mainAdd(){
	window.location= _appContext + '/user/userManager.do?action=addUser';
}

//查看按钮
function mainView(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('请选择一条记录');
		return;
	}
	if(selectCount > 1){
		notify('只能选择一条记录');
		return;
	}
	$('form').first().attr('action', _appContext + '/user/userManager.do?action=viewUser');
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
	$('form').first().attr('action', _appContext + '/user/userManager.do?action=editUser');
	document.forms[0].submit();
}

//分配角色
function mainAssignRole(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('请选择一条记录');
		return;
	}
	if(selectCount > 1){
		notify('只能选择一条记录');
		return;
	}
	document.forms[0].action= _appContext + '/user/userManager.do?action=assignRole';
	document.forms[0].submit();
}

//保存角色
function saveRole(){
	document.forms[0].action= _appContext + '/user/userManager.do?action=saveRole';
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
		document.forms[0].action= _appContext + '/user/userManager.do?action=deleteUser';
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
		document.forms[0].action= _appContext + '/user/userManager.do?action=start';
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
		document.forms[0].action= _appContext + '/user/userManager.do?action=stop';
		document.forms[0].submit();
	});
}

/**
 * 检查确认密码
 * 增加 YKChoi
 */
function _checkPassword(){
	
	var password = dojo.byId("addpassword");
	var cPassword = dojo.byId("addcPassword");
	//alert(cPassword.value+"-"+password.value);
	if(password.value != cPassword.value){
		password.focus();
		notify('密码与确认密码不一致');
		return;
	}
}
function mainReset(){
	var userCode = dojo.byId("userCode");
	userCode.value = "";
	var userName = dojo.byId("userName");
	userName.textbox.value = "";
	var type = dojo.byId("user.userProp");
	type.resetValue("-1", "");
	var stop = dojo.byId("user.isStop");
	stop.resetValue("-1", "");
	var tel = dojo.byId("user.tel");
	tel.textbox.value = "";
	var descript = dojo.byId("descript");
	descript.textbox.value = "";
}

/***********
 * 新增页面 *
 ***********/
//保存按钮

function _checkInput(){
	var lable = document.getElementById("L_ID");
	var userCode = dojo.byId('userCode');
	if(dojo.string.trim(userCode.value) == ''){
		notify("用户编号不能为空");
		lable.innerHTML = '*用户编号不能为空';
		userCode.focus();
		return false;
	}else if(dojo.string.trim(lable.innerHTML) != ''){
		notify(lable.innerHTML);
		userCode.focus();
		return false;
	}
	lable = document.getElementById("L_UserName");
	var userName = dojo.byId("userName");
	if(userName.value == ""){
		notify("用户名称不能为空");
		lable.innerHTML = '*用户名称不能为空';
		userName.focus();
		return false;
	}
	lable = document.getElementById("L_Password");
	var password = dojo.byId("password");
	if(password.value == ""){
		notify("密码不能为空");
		lable.innerHTML = '*密码不能为空';
		password.focus();
		return false;
	}
	lable = document.getElementById("L_CPassword");
	var cPassword = dojo.byId("cPassword");
	if(dojo.string.trim(cPassword.value) == ''){
		lable.innerHTML = '*确认密码不能为空';
		notify("确认密码不能为空");
		return;
	}else{
		lable.innerHTML = '';
	}
	if(password.value != cPassword.value){
		notify("密码与确认密码不一致");
		lable.innerHTML = '密码与确认密码不一致';
		cPassword.focus();
		return false;
	}
	
	var type = dojo.byId("user.userProp");
	var orgId = dojo.byId("orgId");
	lable = document.getElementById("L_OrgId");
	if(type.getValue() != "3" && dojo.string.trim(orgId.value) == ""){
		notify("*非超级管理员,部门不能为空");
		lable.innerHTML = "*非超级管理员,部门不能为空";
		return false;
	}
	
	return true;
}

function _checkEditInput(){
/*	var userCode = dojo.byId("userCode");
	if(userCode.value == ""){
		notify("用户编号不能为空");
		return false;
	}*/
	
	var lable = document.getElementById("L_UserName");
	var userName = dojo.byId("userName");
	if(userName.value == ""){
		notify("用户名称不能为空");
		lable.innerHTML = '*用户名称不能为空';
		userName.focus();
		return false;
	}
	var type = dojo.byId("user.userProp");
	var orgId = dojo.byId("orgId");
	lable = document.getElementById("L_OrgId");
	if(type.getValue() != "3" && dojo.string.trim(orgId.value) == ""){
		notify("*非超级管理员,部门不能为空");
		lable.innerHTML = "*非超级管理员,部门不能为空";
		return false;
	}
	return true;
}

function addSave(){
	if(_checkInput()){
		document.forms[0].action= _appContext + '/user/userManager.do?action=saveUser';
		document.forms[0].submit();
	} 
}

function addReset(){
	var userCode = dojo.byId("userCode");
	userCode.value = "";
	var userName = dojo.byId("userName");
	userName.value = "";
	var password = dojo.byId("password");
	password.value = "";
	var cPassword = dojo.byId("cPassword");
	cPassword.value = "";
	var type = dojo.byId("user.userProp");
	type.resetValue("1", "普通用户");
	var stop = dojo.byId("user.isStop");
	stop.resetValue("0", "否");
	var tel = dojo.byId("user.tel");
	tel.value = "";
	var des = document.forms[0]["user.descript"].value = "";
}

/***********
 * 修改页面 *
 ***********/
//保存按钮
function editSave(){
	if(_checkEditInput()){
		document.forms[0].action= _appContext + '/user/userManager.do?action=updateUser';
		document.forms[0].submit();
	}
}

function editReset(){
	document.forms[0].reset();
	var type = dojo.byId("user.userProp");
	type.formReset();
	var stop = dojo.byId("user.isStop");
	stop.formReset();
	
}
/***********
 * 公共方法 *
 ***********/
 //返回主页面

function returnMain(){
	window.location= _appContext + '/user/userManager.do?action=queryUser';
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
 * 检查用户编号是否正确
 */
function checkId(eventSrc){
	var lable = document.getElementById("L_ID");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*用户编号不能为空';
		return;
	}else{
		lable.innerHTML = '';
	}
	
	var url = _appContext + '/user/userManager.do?action=exist&id=' + eventSrc.value;
	dojo.io.bind({
			url : url,
			preventCache : true, 
			load : function(type,data,e){
				var msg = '';
				if('true' == data){
					msg = '用户编号已经存在';
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
 * 检查用户名
 */
function checkUserName(eventSrc){
	var lable = document.getElementById("L_UserName");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*用户名称不能为空';
	}else{
		lable.innerHTML = '';
	}
}

/**
 * 检查密码
 */
function checkPassword(eventSrc){
	var lable = document.getElementById("L_Password");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*密码不能为空';
	}else{
		lable.innerHTML = '';
	}
}

/**
 * 检查确认密码
 */
function checkCPassword(eventSrc){
	var lable = document.getElementById("L_CPassword");
	var password = dojo.byId("password");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*确认密码不能为空';
		return;
	}else{
		lable.innerHTML = '';
	}
	
	if(password.value != eventSrc.value){
		lable.innerHTML = '密码与确认密码不一致';
	}else{
		lable.innerHTML = '';
	}
}

/**
 * 检查固定
 */
function checkTel(eventSrc){
	var lable = document.getElementById("L_Tel");
	if(dojo.string.trim(eventSrc.value) != '' && !isPhone(eventSrc.value)){
		lable.innerHTML = '无效的固定电话';
		return;
	}else{
		lable.innerHTML = '';
	}
}

/**
 * 检查手机
 */
function checkMobile(eventSrc){
	var lable = document.getElementById("L_Mobile");
	if(dojo.string.trim(eventSrc.value) != '' && !isMoble(eventSrc.value)){
		lable.innerHTML = '无效的手机';
		return;
	}else{
		lable.innerHTML = '';
	}
}

/**
 * 检查Email
 */
function checkEmail(eventSrc){
	var lable = document.getElementById("L_Email");
	if(dojo.string.trim(eventSrc.value) != '' && !isEmail(eventSrc.value)){
		lable.innerHTML = '无效的Email';
		return;
	}else{
		lable.innerHTML = '';
	}
}


/**
 * 选择部门
 */
function selectOrg(){
	openWin(_appContext + '/org/orgManager.do?action=selectOrg&inputType=radio');
}