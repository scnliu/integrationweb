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
  	 	{id:1, pId:0, name:"北京"},
  	 	{id:2, pId:0, name:"天津"},
  	 	{id:3, pId:0, name:"上海"},
  	 	{id:6, pId:0, name:"重庆"},
  	 	{id:4, pId:0, name:"河北省", open:true},
  	 	{id:41, pId:4, name:"石家庄"},
  	 	{id:42, pId:4, name:"保定"},
  	 	{id:43, pId:4, name:"邯郸"},
  	 	{id:44, pId:4, name:"承德"},
  	 	{id:5, pId:0, name:"广东省", open:true},
  	 	{id:51, pId:5, name:"广州"},
  	 	{id:52, pId:5, name:"深圳"},
  	 	{id:53, pId:5, name:"东莞"},
  	 	{id:54, pId:5, name:"佛山"},
  	 	{id:6, pId:0, name:"福建省", open:true},
  	 	{id:61, pId:6, name:"福州"},
  	 	{id:62, pId:6, name:"厦门"},
  	 	{id:63, pId:6, name:"泉州"},
  	 	{id:64, pId:6, name:"三明"}
  	 	
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
		if (!check) alert("只能选择城市...");
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