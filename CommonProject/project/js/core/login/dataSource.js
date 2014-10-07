/*******************************************************************************
 * 数据源JS *
 ******************************************************************************/
// *****************************************************************
function checkDB(ds) {
	// trim();
	var dbType = dojo.byId(ds + ".type");
	if (dbType.getValue() == "1") {
		return true;
	}
	var userName = document.getElementById(ds + ".userName");
	if (userName.value == "") {
		alert("用户名不能为空");
		return false;
	}
	var maxActive = document.getElementById(ds + ".maxActive").value;
	if (maxActive.length != 0 && parseInt(maxActive) != maxActive) {
		alert("最快启动时间必须为一个数字或为空");
		return false;
	}
	var maxWait = document.getElementById(ds + ".maxWait").value;
	if (maxWait.length != 0 && parseInt(maxWait) != maxWait) {
		alert("最长等待时间必须为一个数字或为空");
		return false;
	}
	return true;
}

/*******************************************************************************
 * 查看主页面 *
 ******************************************************************************/

function saveDs() {
	if (checkDB('coreDs') && checkDB('sysDs')) {
		document.forms[0].action = _appContext + '/ds/dsCfg.do?action=startSaveDs';
		document.forms[0].submit();
	}
}

function dsReset() {
	document.forms[0].reset();

	var dsType = dojo.byId("coreDs.type");
	dsType.formReset();
	coreTypeVC(dsType.getValue());

	dsType = dojo.byId("sysDs.type");
	dsType.formReset();
	sysTypeVC(dsType.getValue());

	var dbType = dojo.byId("coreDs.dbType");
	dbType.formReset();

	dbType = dojo.byId("sysDs.dbType");
	dbType.formReset();
}

function typeVC(ds, value) {
	if (value == 1) {
		document.getElementById(ds + '_tr2').style.display = '';

		document.getElementById(ds + '_tr3').style.display = 'none';
		document.getElementById(ds + '_tr4').style.display = 'none';
		document.getElementById(ds + '_tr5').style.display = 'none';
		document.getElementById(ds + '_tr6').style.display = 'none';
		document.getElementById(ds + '_tr7').style.display = 'none';
		document.getElementById(ds + '_tr8').style.display = 'none';
		document.getElementById(ds + '_tr9').style.display = 'none';
		document.getElementById(ds + '_tr10').style.display = 'none';
	} else {
		document.getElementById(ds + '_tr2').style.display = 'none';

		document.getElementById(ds + '_tr3').style.display = '';
		document.getElementById(ds + '_tr4').style.display = '';
		document.getElementById(ds + '_tr5').style.display = '';
		document.getElementById(ds + '_tr6').style.display = '';
		document.getElementById(ds + '_tr7').style.display = '';
		document.getElementById(ds + '_tr8').style.display = '';
		document.getElementById(ds + '_tr9').style.display = '';
		document.getElementById(ds + '_tr10').style.display = '';
	}
}

function coreTypeVC(value) {
	typeVC('core', value);
}

function sysTypeVC(value) {
	typeVC('sys', value);
}
