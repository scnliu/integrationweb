/**
 * 跳转到指定的页

 */
function toPage(toPage, action, currentPageProp, totalPage, displayCount){
	var _form = document.getElementById("_to_page_prop_").form;
	if(toPage == null){
		toPage = _form._to_page_prop_.value;
	}
	
	if(toPage == ''){
		return;
	}
	
	if( isNaN(toPage) || toPage < 1 || ( displayCount && totalPage && toPage > totalPage)){
		alert('调转页超出范围');
		_form._to_page_prop_.value = "";
		return;
	}
	_form[currentPageProp].value = toPage;
	_form.action = action;
	_form.submit();
}