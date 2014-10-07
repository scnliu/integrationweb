	var zTree1;
	var setting;

	setting = {
		isSimpleData: true,
		treeNodeKey: "id",
		treeNodeParentKey: "pId",
		fontCss: setFont,
		callback: {
			beforeExpand: function(){return false;},
			beforeCollapse: function(){return false;},
			beforeClick: zTreeOnBeforeClick,
			click: zTreeOnClick
		}
		
	};

	var zNodes =[
  	 	{id:1, pId:0, name:"����"},
  	 	{id:2, pId:0, name:"���"},
  	 	{id:3, pId:0, name:"�Ϻ�"},
  	 	{id:6, pId:0, name:"����"},
  	 	{id:4, pId:0, name:"�ӱ�ʡ", open:true},
  	 	{id:41, pId:4, name:"ʯ��ׯ"},
  	 	{id:42, pId:4, name:"����"},
  	 	{id:43, pId:4, name:"����"},
  	 	{id:44, pId:4, name:"�е�"},
  	 	{id:5, pId:0, name:"�㶫ʡ", open:true},
  	 	{id:51, pId:5, name:"����"},
  	 	{id:52, pId:5, name:"����"},
  	 	{id:53, pId:5, name:"��ݸ"},
  	 	{id:54, pId:5, name:"��ɽ"},
  	 	{id:6, pId:0, name:"����ʡ", open:true},
  	 	{id:61, pId:6, name:"����"},
  	 	{id:62, pId:6, name:"����"},
  	 	{id:63, pId:6, name:"Ȫ��"},
  	 	{id:64, pId:6, name:"����"}
  	 	
  	 ];
	function setFont(treeId, treeNode) {
		if (treeNode && treeNode.isParent) {
			return {color: "blue"};
		} else {
			return null;
		}
	}

	function showMenu() {
		var cityObj = $("#citySel");
		var cityOffset = $("#citySel").offset();
		$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
		
	}
	function hideMenu() {
		$("#DropdownMenuBackground").fadeOut("fast");
	}

	function zTreeOnBeforeClick(treeId, treeNode) {
		var check = (treeNode && !treeNode.isParent);
		if (!check) alert("ֻ��ѡ�����...");
		return check;
	}
	
	function zTreeOnClick(event, treeId, treeNode) {
		if (treeNode) {
			var cityObj = $("#citySel");
			cityObj.attr("value", treeNode.name);
			hideMenu();
		}
	}

	function reloadTree() {
		hideMenu();
		zTree1 = $("#dropdownMenu").zTree(setting, clone(zNodes));
	}	