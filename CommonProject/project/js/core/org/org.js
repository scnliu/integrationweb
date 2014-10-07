/**
 * ���Ź���ģ��js

 */

/**********
 * ��ҳ��  *
 **********/

//��ѯ��ť
function mainQuery(){
	//document.forms[0]['tableModel.page.currentPage'].value = 1;
	//pageSetFirst();
	document.forms[0].action= _appContext + '/org/orgManager.do?action=queryOrg';
	document.forms[0].submit();
}

//������ť
function mainAdd(){
	document.forms[0].action= _appContext + '/org/orgManager.do?action=addOrg';
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
	document.forms[0].action= _appContext + '/org/orgManager.do?action=editOrg';
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
		document.forms[0].action= _appContext + '/org/orgManager.do?action=deleteOrg';
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
		document.forms[0].action= _appContext + '/org/orgManager.do?action=start';
		document.forms[0].submit();
	});	
}

//����
function mainStop(){
	var selectCount = getSelectCount();
	if(selectCount == 0){
		notify('��ѡ��Ҫͣ�õļ�¼');
		return;
	}
	askFunc('�Ƿ�ͣ����ѡ��¼��',function(){
		document.forms[0].action= _appContext + '/org/orgManager.do?action=stop';
		document.forms[0].submit();
	});	
}

//����˳��
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
		alert('�Ѿ��ǵ�һ��');
		return false;
	}
	//if(confirm('�Ƿ������ƶ�?')){	
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
		alert('�Ѿ������һ��');
		return false;
	}
	//if(confirm('�Ƿ������ƶ�?')){	
		dojo.dom.insertAfter(tr,nextTr);
	//}
}

/**
 * ��鲿��ID
 */
function checkId(eventSrc){
	var lable = document.getElementById("L_ID");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*����ID����Ϊ��';
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
					msg = '����ID�Ѿ�����';
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
 * �����֯��������
 */
function checkZzjgdm(eventSrc){
	var lable = document.getElementById("L_Zzjgdm");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*��֯�������벻��Ϊ��';
	}else{
		lable.innerHTML = '';
	}
}

/**
 * ��鲿�ż���
 */
function checkOrgCode(eventSrc){
	var lable = document.getElementById("L_OrgCode");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*���ż��벻��Ϊ��';
	}else{
		lable.innerHTML = '';
	}	
}

/**
 * ��鲿�ż��
 */
function checkOrgName(eventSrc){
	var lable = document.getElementById("L_OrgName");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*���ż�Ʋ���Ϊ��';
	}else{
		lable.innerHTML = '';
	}
}

/**
 * ��鲿��ȫ��
 */
function checkOrgFullName(eventSrc){
	var lable = document.getElementById("L_OrgFullName");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*����ȫ�Ʋ���Ϊ��';
	}else{
		lable.innerHTML = '';
	}
}

/**
 * ��鲿�ű��
 */
function checkOrgRuleCode(eventSrc){
	var lable = document.getElementById("L_OrgRuleCode");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*���ű�Ų���Ϊ��';
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
					msg = '���ű���Ѿ�����';
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
 * �������˳��
 */
function checkOrder(eventSrc){
	var lable = document.getElementById("L_Order");
	if(dojo.string.trim(eventSrc.value) == ''){
		lable.innerHTML = '*����˳����Ϊ��';
	}else{
		lable.innerHTML = '';
	}
}

/***********
 * ����ҳ�� *
 ***********/
//���水ť

function _checkInput(){
	var lable = document.getElementById("L_ID");
	var id = dojo.byId('id');
	if(dojo.string.trim(id.value) == ''){
		lable.innerHTML = '*����ID����Ϊ��';
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
		alert("��֯�������벻��Ϊ��");
		lable.innerHTML = '*��֯�������벻��Ϊ��';
		zzjgdm.focus();
		return false;
	}
	
	lable = document.getElementById("L_OrgCode");
	var orgCode = dojo.byId("orgCode");
	if(dojo.string.trim(orgCode.value) == ''){
		alert("���ż��벻��Ϊ��");
		lable.innerHTML = '*���ż��벻��Ϊ��';
		orgCode.focus();
		return false;
	}
	
	lable = document.getElementById("L_OrgName");
	var orgName = dojo.byId("orgName");
	if(dojo.string.trim(orgName.value) == ''){
		alert("���ż�Ʋ���Ϊ��");
		lable.innerHTML = '*���ż�Ʋ���Ϊ��';
		orgName.focus();
		return false;
	}
	
	lable = document.getElementById("L_OrgFullName");
	var orgFullName = dojo.byId("orgFullName");
	if(dojo.string.trim(orgFullName.value) == ''){
		alert("����ȫ�Ʋ���Ϊ��");
		lable.innerHTML = '*����ȫ�Ʋ���Ϊ��';
		orgFullName.focus();
		return false;
	}
	
	lable = document.getElementById("L_OrgRuleCode");
	var orgRuleCode = dojo.byId("orgRuleCode");
	if(dojo.string.trim(orgRuleCode.value) == ''){
		alert("���ű�Ų���Ϊ��");
		lable.innerHTML = '*���ű�Ų���Ϊ��';
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
		alert("����˳����Ϊ��");
		lable.innerHTML = '*����˳����Ϊ��';
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
	type.resetValue("1", "��ͨ����");
	var stop = dojo.byId("org.isStop");
	stop.resetValue("0", "��");
	var des = document.forms[0]["org.descript"].value = "";
}

/***********
 * �޸�ҳ�� *
 ***********/
//���水ť
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
 * �������� *
 ***********/
 //������ҳ��

function returnMain(){
	document.forms[0].action= _appContext + '/org/orgManager.do?action=queryOrg';
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
	
	alert('��ѡ����');
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
		alert('��ѡ����');
	}else{
		orgName.value = n;
		orgId.value = v;
		window.close();
	}
}
