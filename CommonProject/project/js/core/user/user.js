/**
 * �û�����ģ��js

 */

/**********
 * ��ҳ��  *
 **********/

//��ѯ��ť
function mainQuery(){
	//document.forms[0]['tableModel.page.currentPage'].value = 1;
	pageSetFirst();
	document.forms[0].action= _appContext + '/user/userManager.do?action=queryUser';
	document.forms[0].submit();
}

//������ť
function mainAdd(){
	window.location= _appContext + '/user/userManager.do?action=addUser';
}

//�鿴��ť
function mainView(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('��ѡ��һ����¼');
		return;
	}
	if(selectCount > 1){
		notify('ֻ��ѡ��һ����¼');
		return;
	}
	$('form').first().attr('action', _appContext + '/user/userManager.do?action=viewUser');
	document.forms[0].submit();
}

//�༭��ť
function mainEdit(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('��ѡ��һ����¼');
		return;
	}
	if(selectCount > 1){
		notify('ֻ��ѡ��һ����¼');
		return;
	}
	$('form').first().attr('action', _appContext + '/user/userManager.do?action=editUser');
	document.forms[0].submit();
}

//�����ɫ
function mainAssignRole(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('��ѡ��һ����¼');
		return;
	}
	if(selectCount > 1){
		notify('ֻ��ѡ��һ����¼');
		return;
	}
	document.forms[0].action= _appContext + '/user/userManager.do?action=assignRole';
	document.forms[0].submit();
}

//�����ɫ
function saveRole(){
	document.forms[0].action= _appContext + '/user/userManager.do?action=saveRole';
	document.forms[0].submit();
}


//ɾ����ť
function mainDelete(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('��ѡ��Ҫɾ���ļ�¼');
		return;
	}
	askFunc('�Ƿ�ɾ����ѡ��¼��',function(){
		document.forms[0].action= _appContext + '/user/userManager.do?action=deleteUser';
		document.forms[0].submit();
	});
}

//����
function mainStart(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('��ѡ��Ҫ���õļ�¼');
		return;
	}
	askFunc('�Ƿ�������ѡ��¼��',function(){
		document.forms[0].action= _appContext + '/user/userManager.do?action=start';
		document.forms[0].submit();
	});
}

//ͣ��
function mainStop(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('��ѡ��Ҫͣ�õļ�¼');
		return;
	}
	askFunc('�Ƿ�ͣ����ѡ��¼��',function(){
		document.forms[0].action= _appContext + '/user/userManager.do?action=stop';
		document.forms[0].submit();
	});
}

/**
 * ���ȷ������
 * ���� YKChoi
 */
function _checkPassword(){
	
	var password = dojo.byId("addpassword");
	var cPassword = dojo.byId("addcPassword");
	//alert(cPassword.value+"-"+password.value);
	if(password.value != cPassword.value){
		password.focus();
		notify('������ȷ�����벻һ��');
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
 * ����ҳ�� *
 ***********/
//���水ť

function _checkInput(){
	var lable = document.getElementById("L_ID");
	var userCode = dojo.byId('userCode');
	if(dojo.string.trim(userCode.value) == ''){
		notify("�û���Ų���Ϊ��");
		lable.innerHTML = '*�û���Ų���Ϊ��';
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
		notify("�û����Ʋ���Ϊ��");
		lable.innerHTML = '*�û����Ʋ���Ϊ��';
		userName.focus();
		return false;
	}
	lable = document.getElementById("L_Password");
	var password = dojo.byId("password");
	if(password.value == ""){
		notify("���벻��Ϊ��");
		lable.innerHTML = '*���벻��Ϊ��';
		password.focus();
		return false;
	}
	lable = document.getElementById("L_CPassword");
	var cPassword = dojo.byId("cPassword");
	if(dojo.string.trim(cPassword.value) == ''){
		lable.innerHTML = '*ȷ�����벻��Ϊ��';
		notify("ȷ�����벻��Ϊ��");
		return;
	}else{
		lable.innerHTML = '';
	}
	if(password.value != cPassword.value){
		notify("������ȷ�����벻һ��");
		lable.innerHTML = '������ȷ�����벻һ��';
		cPassword.focus();
		return false;
	}
	
	var type = dojo.byId("user.userProp");
	var orgId = dojo.byId("orgId");
	lable = document.getElementById("L_OrgId");
	if(type.getValue() != "3" && dojo.string.trim(orgId.value) == ""){
		notify("*�ǳ�������Ա,���Ų���Ϊ��");
		lable.innerHTML = "*�ǳ�������Ա,���Ų���Ϊ��";
		return false;
	}
	
	return true;
}

function _checkEditInput(){
/*	var userCode = dojo.byId("userCode");
	if(userCode.value == ""){
		notify("�û���Ų���Ϊ��");
		return false;
	}*/
	
	var lable = document.getElementById("L_UserName");
	var userName = dojo.byId("userName");
	if(userName.value == ""){
		notify("�û����Ʋ���Ϊ��");
		lable.innerHTML = '*�û����Ʋ���Ϊ��';
		userName.focus();
		return false;
	}
	var type = dojo.byId("user.userProp");
	var orgId = dojo.byId("orgId");
	lable = document.getElementById("L_OrgId");
	if(type.getValue() != "3" && dojo.string.trim(orgId.value) == ""){
		notify("*�ǳ�������Ա,���Ų���Ϊ��");
		lable.innerHTML = "*�ǳ�������Ա,���Ų���Ϊ��";
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
	type.resetValue("1", "��ͨ�û�");
	var stop = dojo.byId("user.isStop");
	stop.resetValue("0", "��");
	var tel = dojo.byId("user.tel");
	tel.value = "";
	var des = document.forms[0]["user.descript"].value = "";
}

/***********
 * �޸�ҳ�� *
 ***********/
//���水ť
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
 * �������� *
 ***********/
 //������ҳ��

function returnMain(){
	window.location= _appContext + '/user/userManager.do?action=queryUser';
}

//����
function resetValue(){
	document.forms[0].reset();
}

//�رմ���
function closeWin(){
	window.close();
}

/**
 * ����û�����Ƿ���ȷ
 */
function checkId(eventSrc){
	var lable = document.getElementById("L_ID");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*�û���Ų���Ϊ��';
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
					msg = '�û�����Ѿ�����';
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
 * ����û���
 */
function checkUserName(eventSrc){
	var lable = document.getElementById("L_UserName");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*�û����Ʋ���Ϊ��';
	}else{
		lable.innerHTML = '';
	}
}

/**
 * �������
 */
function checkPassword(eventSrc){
	var lable = document.getElementById("L_Password");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*���벻��Ϊ��';
	}else{
		lable.innerHTML = '';
	}
}

/**
 * ���ȷ������
 */
function checkCPassword(eventSrc){
	var lable = document.getElementById("L_CPassword");
	var password = dojo.byId("password");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*ȷ�����벻��Ϊ��';
		return;
	}else{
		lable.innerHTML = '';
	}
	
	if(password.value != eventSrc.value){
		lable.innerHTML = '������ȷ�����벻һ��';
	}else{
		lable.innerHTML = '';
	}
}

/**
 * ���̶�
 */
function checkTel(eventSrc){
	var lable = document.getElementById("L_Tel");
	if(dojo.string.trim(eventSrc.value) != '' && !isPhone(eventSrc.value)){
		lable.innerHTML = '��Ч�Ĺ̶��绰';
		return;
	}else{
		lable.innerHTML = '';
	}
}

/**
 * ����ֻ�
 */
function checkMobile(eventSrc){
	var lable = document.getElementById("L_Mobile");
	if(dojo.string.trim(eventSrc.value) != '' && !isMoble(eventSrc.value)){
		lable.innerHTML = '��Ч���ֻ�';
		return;
	}else{
		lable.innerHTML = '';
	}
}

/**
 * ���Email
 */
function checkEmail(eventSrc){
	var lable = document.getElementById("L_Email");
	if(dojo.string.trim(eventSrc.value) != '' && !isEmail(eventSrc.value)){
		lable.innerHTML = '��Ч��Email';
		return;
	}else{
		lable.innerHTML = '';
	}
}


/**
 * ѡ����
 */
function selectOrg(){
	openWin(_appContext + '/org/orgManager.do?action=selectOrg&inputType=radio');
}