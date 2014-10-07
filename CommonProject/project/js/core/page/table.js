/**
 * 选择所有

 */
function selectAll(){
//	var selectAll = dojo.byId("list_selectAll");
	setSelect($("#list_selectAll").checkbox('isCheck'));
}

function setSelect(isSelected){
	for(var i = 1; i<=checkCount; i++){
			$('#list_cb_' + i).checkbox('check',isSelected);
	}
}

/**
 * 获得选中记录数

 */
 function getSelectCount(){
	var count = 0;
	for(var i = 1; i<=checkCount; i++){
		var dc=$('#list_cb_' + i).attr('checked');
			if(dc){
				count++;
			}
	}		
	return count;
}


/**
 * 选中所点击行

 */
function rowClick(tr){
	var id = 'list_cb_' + tr.id.substring(3);
	var cb = dojo.byId(id);
	if(cb){
		dojo.attr(cb,"checked",!dojo.attr(cb,"checked"));
	}
}
function page(method){
	 $('#pageMethod').val(method);
	 document.getElementById('searchForm').submit();
	}
function gotoPage(){
	 var p=document.getElementById('to_page_prop_').value;
	 if(/\d+/.test(p)){
		 document.getElementById('currentPage').value=p;
		 document.getElementById('searchForm').submit();
	 }else{
		 document.getElementById('to_page_prop_').value="";
	  }
	}
function outRow(r){
	$(r).removeClass("rowOver");
}
function overRow(r){
	$(r).addClass("rowOver");
}
function changeOrder(){
		window.location="#springUrl('')/sys/workSceneCtrl.do?action=order";
	}
function submitForm(){
		if(actionMsg){
			notify(actionMsg);
			actionMsg=null;
			return false;
		 }else{
			  actionMsg=null;
			  return true;
		 }
	}
var actionMsg;
function deleteSelected(){
	var count=getSelectCount();
	if(count>0){
		actionMsg=null;
	}else{
		actionMsg="请选择记录!";
	}
	
}
function editSelected(){
	var count=getSelectCount();
	if(count>0){
		if(count>1){
			actionMsg="只能选择一条记录!";
		}else{
			actionMsg=null;
			}
	}else{
		actionMsg="请选择记录!";
	}
}
