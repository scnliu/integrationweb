/**
 * ��ɫ����ģ��js

 */

/**********
 * ��ҳ��  *
 **********/

//��ѯ��ť
function mainQuery(){
	//document.forms[0]['tableModel.page.currentPage'].value = 1;
	pageSetFirst();
	document.forms[0].action= _appContext + '/role/roleManager.do?action=queryRole';
	document.forms[0].submit();
}

//������ť
function mainAdd(){
	window.location= _appContext + '/role/roleManager.do?action=addRole';
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
	document.forms[0].action= _appContext + '/role/roleManager.do?action=editRole';
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
		document.forms[0].action= _appContext + '/role/roleManager.do?action=deleteRole';
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
		document.forms[0].action= _appContext + '/role/roleManager.do?action=start';
		document.forms[0].submit();	
	});
}

function mainStop(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('��ѡ��Ҫͣ�õļ�¼');
		return;
	}
	askFunc('�Ƿ�������ѡ��¼��',function(){
		document.forms[0].action= _appContext + '/role/roleManager.do?action=stop';
		document.forms[0].submit();
	});
}
//����Ȩ��
function mainPurview(){
	document.forms[0].action= _appContext + '/role/roleManager.do?action=assignPurview';
	document.forms[0].submit();
}

//����Ȩ��
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
 * �������� *
 ***********/
 //������ҳ��

function returnMain(){
	window.location= _appContext + '/role/roleManager.do?action=queryRole';
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
 * ѡ����
 */
function selectOrg(){
	openWin(_appContext + '/org/orgManager.do?action=selectOrg');
}
