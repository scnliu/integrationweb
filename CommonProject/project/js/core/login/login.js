/**
 * 
 */

$(document).ready(function() {
	if (document.forms[0]) {
		if (document.forms[0].userCode) {
			document.forms[0].userCode.focus();
		} else {
			if (document.forms[0].oldPassword) {
				document.forms[0].oldPassword.focus();
			}
		}
	}
});

function _loginKeyPress(event, context) {
	if (event.keyCode == 13) {
		document.getElementById('lname').focus();
		// document.forms[0].submit();
	}
}

function loginSubmit() {
	if (document.forms[0].userCode.value == '') {

		notify("�û���Ų���Ϊ��");
		return false;
	}
	if (document.forms[0].password.value == '') {
		notify("���벻��Ϊ��");
		return false;
	}
	return true;
}

function _loginReset() {
	document.forms[0].userCode.value = '';
	document.forms[0].password.value = '';
}

function _saveUserInfo(context) {
	if (_checkInput()) {
		document.forms[0].action = context
				+ "/login/login.do?action=saveUserInfo";
		document.forms[0].submit();
	}
}

function _savePasswordKeyPress(event, context) {
	if (event.keyCode == 13) {
		_savePassword(context);
	}
}

function _saveIndividuation(context) {
	if (document.getElementById('iptFuncCode').checked == true
			&& dojo.string.trim(document.forms[0].funcName.value) == '') {
		alert("��ѡ���ܱ��");
		return;
	}
	document.forms[0].action = context
			+ "/login/login.do?action=saveIndividuation";
	document.forms[0].submit();
}

function _logout(context) {

	askFunc('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�Ƿ����µ�¼?', function() {
		window.location = context + "/login/login.do?action=logout";
	});

}

function _exit(context) {
	askFunc('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�Ƿ��˳�ϵͳ?', function() {
		window.location = context + "/login/login.do?action=logout&exit=1";
	});

}

function topicSelect(topic) {
	document.getElementById(topic).checked = true;
}
/**
 * ����û���
 */
function checkUserName(eventSrc) {
	var lable = document.getElementById("L_UserName");
	if (eventSrc.value.trim() == '') {
		lable.innerHTML = '*�û����Ʋ���Ϊ��';
	} else {
		lable.innerHTML = '';
	}
}

/**
 * ����û�����Ƿ���ȷ
 */
function checkOldPwd() {
	var password = dojo.byId("modifyoPassword");
	var _appContext = dojo.byId("path_appContext");

	var url = _appContext.value + "/login/login.do?action=checkPwd&oldPwd="
			+ password.value;
	alert(url.value);

	document.forms[0].action = url;
	document.forms[0].submit();
}

/**
 * �������
 */
function checkPassword(eventSrc) {
	var lable = document.getElementById("L_Password");
	if (eventSrc.value.trim() == '') {
		lable.innerHTML = '*���벻��Ϊ��';
	} else {
		lable.innerHTML = '';
	}
}

/**
 * ���ȷ������
 */
function _checkPassword() {
	var password = $("#modifypassword");
	var cPassword = $("#modifycPassword");
	if (password.val() != cPassword.val()) {
		password.focus();
		notify('������ȷ�����벻һ��');
		password.val("");
		cPassword.val("");
		return;
	}
}

function _checkInput() {
	var lable = document.getElementById("L_UserName");
	var userName = dojo.byId("userName");
	if (userName.value == "") {
		alert("�û����Ʋ���Ϊ��");
		lable.innerHTML = '*�û����Ʋ���Ϊ��';
		userName.focus();
		return false;
	}

	lable = document.getElementById("L_OldPassword");
	var oldPassword = dojo.byId("oldPassword");

	/*
	 * if(dojo.string.trim(lable.innerHTML) != ''){ alert(lable.innerHTML);
	 * oldPassword.focus(); return false; }
	 */

	if (oldPassword.value == "") {
		alert("�����벻��Ϊ��");
		lable.innerHTML = '*�����벻��Ϊ��';
		oldPassword.focus();
		return false;
	}

	lable = document.getElementById("L_Password");
	var password = dojo.byId("password");
	if (password.value == "") {
		alert("���벻��Ϊ��");
		lable.innerHTML = '*���벻��Ϊ��';
		password.focus();
		return false;
	}
	lable = document.getElementById("L_CPassword");
	var cPassword = dojo.byId("cPassword");
	if (dojo.string.trim(cPassword.value) == '') {
		lable.innerHTML = '*ȷ�����벻��Ϊ��';
		alert("ȷ�����벻��Ϊ��");
		return;
	} else {
		lable.innerHTML = '';
	}
	if (password.value != cPassword.value) {
		alert("������ȷ�����벻һ��");
		lable.innerHTML = '������ȷ�����벻һ��';
		cPassword.focus();
		return false;
	}
	return true;
}

function selectFunc() {
	var funcName = document.getElementById("funcName");
	var funcId = document.getElementById("funcId");
	var funcIds = document.getElementsByName("funcIds");
	for ( var i = 0; funcIds != null && i < funcIds.length; i++) {
		if (funcIds[i].checked == true) {
			funcName.value = funcIds[i].getAttribute("nodeTitle");
			funcId.value = funcIds[i].value;
			var selectFunc = dojo.byId('selectFunc');
			selectFunc.hide();
			return;
		}
	}
	alert('��ѡ���ܲ˵�');
}

function openSelectFunc() {
	var selectFunc = dojo.byId('selectFunc');
	selectFunc.show();
	// openWin(_appContext + '/login/login.do?action=selectFunc', 300, 500,
	// "w1");
}
