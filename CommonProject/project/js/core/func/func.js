/**
 * ���ܹ���ģ��js

 */

/**********
 * ��ҳ��  *
 **********/

//��ѯ��ť
function mainQuery(){
	pageSetFirst();
	document.forms[0].action= _appContext + '/func/funcManager.do?action=queryFunc';
	document.forms[0].submit();
}

//������ť
function mainAdd(){
	var _level = jQuery("#func_level").val();
	if(_level==0){
		notify('��Ϊϵͳ�ĸ�Ŀ¼���޷���ӣ���ѡ�о���Ĺ��ܲ˵���');
	}
	else{
		document.forms[0].action= _appContext + '/func/funcManager.do?action=addFunc';
		document.forms[0].submit();				
	}

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
	document.forms[0].action= _appContext + '/func/funcManager.do?action=editFunc';
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
		document.forms[0].action= _appContext + '/func/funcManager.do?action=deleteFunc';
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
		document.forms[0].action= _appContext + '/func/funcManager.do?action=start';
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
		document.forms[0].action= _appContext + '/func/funcManager.do?action=stop';
		document.forms[0].submit();
	});
}

//����˳��
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
		alert('�Ѿ��ǵ�һ��');
		return false;
	}
	if(confirm('�Ƿ������ƶ�?')){	
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
		alert('�Ѿ������һ��');
		return false;
	}
	if(confirm('�Ƿ������ƶ�?')){	
		dojo.dom.insertAfter(tr,nextTr);
	}
}

/***********
 * ����ҳ�� *
 ***********/
//���水ť

function _checkInput(){
	var funcCode = dojo.byId("funcCode");
	if(funcCode.value == ""){
		alert("���ܱ�Ų���Ϊ��");
		return false;
	}
	var funcName = dojo.byId("funcName");
	if(funcName.value == ""){
		alert("�������Ʋ���Ϊ��");
		return false;
	}
	
	var context = dojo.byId("context");
	if( context != null && dojo.string.trim(context.value) == ""){
		alert("ϵͳ�����Ĳ���Ϊ��");
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
	type.resetValue("1", "��ͨ����");
	var stop = dojo.byId("func.isStop");
	stop.resetValue("0", "��");
	var des = document.forms[0]["func.descript"].value = "";
}

/***********
 * �޸�ҳ�� *
 ***********/
//���水ť
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
 * �������� *
 ***********/
 //������ҳ��

function returnMain(){
	document.forms[0].action= _appContext + '/func/funcManager.do?action=queryFunc';
	document.forms[0].submit();
}

//����
function resetValue(){
	document.forms[0].reset();
}

//�رմ���
function closeWin(){
	window.close();
}

