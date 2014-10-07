var cellMap;
var format;
var selectedDiv = null;
var gridVector;
var scanMap;
var features = [];
var maxBounds;
/*
 * initMapAfter() �����ڵ�ͼ��ʼ������� 
 * 
 */

function initMapAfter(){
	
}

var allErrorCell = [];
var isSearchCellr = false;//�Ƿ��������
//ͨ����γ�Ȳ���С��
function queryCellByLonLat(lonlat) {
	isSearchCellr = false; 
	$.ajax({
		url : _appContext + "/gis/selectCell.do?action=queryCell",
		dateType : "json",
		type:'POST',
		data : {'lon' : lonlat.lon,'lat':lonlat.lat, 'toolCode':getToolCode()},
		success:function(data){
			//���ҽ����������
			queryCellrCallback(data,false);
		}
	});
}



//ͨ��С��ID����С��
/**
 * cellid  ����
 * searchByCNName  �Ƿ�ͨ��С���������Ʋ���
 */
function queryCell(cellid,searchByCNName) {
	isSearchCellr = false; 
	var params={};
	var val=encodeURI(cellid);
	 params=!searchByCNName?{'cellid': val}:{'cellName': val};
	 params['toolCode']=getToolCode();
	$.ajax({
		url : _appContext + "/gis/selectCell.do?action=selectCell",
		dateType : "json",
		type:'get',
		data :params,
		success:function(data){
			if(data){
				vector.removeAllFeatures();
				var cellPoint=new OpenLayers.LonLat(data.lon, data.lat);
				var xy=map.getPixelFromLonLat(cellPoint);
				style_cell.fillColor="#"+data.fillColorStr;
				style_cell.strokeColor="#"+data.strokeColorStr;
				drawCellShape(data.points,style_cell);
				showCellInfo(data,xy,cellPoint);
				map.zoomTo(15);
			}
		}
	});
}

//��������
function queryCellr(cellid,searchByCNName) {
	isSearchCellr = true;
	var params={};
	var val=encodeURI(cellid);
	 params=!searchByCNName?{'cellid': val}:{'cellName': val};
	 params['toolCode']=getToolCode();
	jQuery.ajax({
		type: "get",      //�ύ��ʽ
		url : _appContext + "/gis/selectCell.do?action=cellR",
		data :params, 
		dateType: "json",  
		cache:false,
		success:function(data){
			queryCellrCallback(data);
			
		},
		error: function(request, error, status){ 
		}
	});
}

/**
 * ���������ص�����
 * @param data
 * @param changeZoom
 */
function queryCellrCallback(data,changeZoom){
	if(data){
		if(typeof changeZoom=='undefined'){
			changeZoom=true;
		}
		vector.removeAllFeatures();
		var mcell=data.mcell;
		if(!mcell)return;
		var cellPoint=new OpenLayers.LonLat(mcell.lon, mcell.lat);
		var xy=map.getPixelFromLonLat(cellPoint);
		style_cell.fillColor="#"+mcell.fillColorStr;
		style_cell.strokeColor="#"+mcell.strokeColorStr;
		var cellrs=data.cellr;
		
		for(var k=0;k<cellrs.length;k++){
			style_cellr.fillColor="#"+cellrs[k].fillColorStr;
			style_cellr.strokeColor="#"+cellrs[k].strokeColorStr;
			drawCellShape(cellrs[k].points,style_cellr);
			//����
			if(linkLineVisibily){
			drawLinkLine(new OpenLayers.Geometry.Point(mcell.lon, mcell.lat) ,new OpenLayers.Geometry.Point(cellrs[k].lon, cellrs[k].lat));
			}
		}
		drawCellShape(mcell.points,style_cell);
		showCellInfo(mcell,xy,cellPoint);
		if(changeZoom)map.zoomTo(15);
	}
}

//ͨ��Ƶ�����ͬƵС��
function queryCellByDchno() {
	var dchno=$('#search_dchno').val();
	if(!blurSearchDchno($('#search_dchno')[0])){
		return;
	}
	$('#searchDchnoBtn').button('busy');
	var zoom=this.map.getZoom()||1;
	var bounds=map.getExtent();
	isSearchCellr = true;
	jQuery.ajax({
		type: "post",      //�ύ��ʽ
		url : _appContext + "/gis/selectCell.do?action=queryCellsByDchno",
		data :{'toolCode':getToolCode(),'dchno':dchno,'zoom':zoom,'bounds[0]':bounds.left,'bounds[1]':bounds.bottom,'bounds[2]':bounds.right,'bounds[3]':bounds.top}, 
		dateType: "json",  
		cache:false,
		success:function(data){
			queryDchnoCallback(data);
		},
		error: function(request, error, status){ 
			$('#searchDchnoBtn').button('free');
		}
	});
}
//��Ƶ�����С���Ļص�����
var vectorDchnoLayer;
function queryDchnoCallback(data){
	var minRes=map.getResolutionForZoom(15);
	if(data){
		/////////��ʼ��VectorDchnoLayer///////////
		if(!vectorDchnoLayer){
			var sketchSymbolizers={
			        	 strokeColor: "#00FF00",
			        	    strokeWidth: 1,
			        	    hoverFillColor: "#FFFF00",
			        	    fillColor: "#FF5500",
			        	    fillOpacity: 0.7,
			        	    pointRadius:1,
			        	    pointerEvents: "visiblePainted",
			        	    label : "${featureLabel}",
			        		fontColor: "#002222",
			        		fontSize: "15px",
			        	    fontFamily: "Courier New, monospace",
			        	    fontWeight:"normal",
			        	    labelAlign: "lc",
			        	    labelXOffset: "8",
			        	    fontOpacity:0.8,
			        	    fontBackGroundColor:'${fontBackColor}'
			    };
			var sketchSymbolizers2={
						strokeColor: "#00FF00",
						strokeWidth: 1,
						fillColor: "#FF5500",
						fillOpacity: 0.7,
						pointRadius:1,
						pointerEvents: "visiblePainted",
						label : "${featureLabel}",
						fontColor: "#002222",
						fontSize: "12px",
						fontFamily: "Courier New, monospace",
						labelAlign: "lc",
						labelXOffset: "6",
						 fontOpacity:0.8,
						 fontBackGroundColor:'${fontBackColor}'
						 
			};
			var dchno_style = new OpenLayers.Style('default');
			var maxRes = map.getResolutionForZoom(15);
			var maxRes2 = map.getResolutionForZoom(13);
			var maxScale = null;
			var maxScale2 = null;
			var units =map.getUnits();
			if(units){
				maxScale = OpenLayers.Util.getScaleFromResolution(maxRes, units);
				maxScale2 = OpenLayers.Util.getScaleFromResolution(maxRes2,units);
				var dchnoRule=new OpenLayers.Rule({symbolizer: sketchSymbolizers,maxScaleDenominator:maxScale});
				var dchnoRule2=new OpenLayers.Rule({symbolizer: sketchSymbolizers2,minScaleDenominator:maxScale,maxScaleDenominator:maxScale2});
				dchno_style.addRules([
			                dchnoRule,
			                dchnoRule2
			    ]);
			}
			vectorDchnoLayer = new OpenLayers.Layer.Vector("ͬƵС��", {
			    styleMap: new OpenLayers.StyleMap({'default':dchno_style}),
			    renderers: ["Canvas", "SVG","VML"]
			});
			map.addLayer(vectorDchnoLayer);
			map.zoomTo(15);
		}
		////////////////////////////
		vectorDchnoLayer.removeAllFeatures();
		   if(data[0]){
		 			var lon=data[0].lon;
		 			var lat=data[0].lat;
		 			map.moveTo(new OpenLayers.LonLat(lon,lat));
		   }
		   var dchnoFeatures=[];
		   var fontBackColors=['#ffffaa','#aaffff','#ffaaff'];
		   for(var i=0;i<data.length;i++){
			   var cell=data[i];
//			   var labelXOffset=cell.name.length*11;
			   var cellDchno = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Point(cell.lon,cell.lat),{'fontBackColor':fontBackColors[i%3],'featureLabel':cell.dchno,'labelOffsetX':1});
//			   var points=[];
//			   var lonlats=cell.points;
//			    for(var k=0;k<lonlats.length;k+=2){
//				    	var destPoint=new OpenLayers.Geometry.Point(lonlats[k],lonlats[k+1]);
//						points[points.length]=destPoint;
//				  }
//				var linear=new OpenLayers.Geometry.LinearRing(points);
////				//��С��������
//				var cellPolygon = new OpenLayers.Geometry.Polygon(linear);
//				var cellShape = new OpenLayers.Feature.Vector(linear,{'featureLabel':cell.dchno,'labelOffsetX':1});
//			   dchnoFeatures[i]=cellShape;
			   dchnoFeatures[i]=cellDchno;
		   }
		   vectorDchnoLayer.addFeatures(dchnoFeatures);
	           $('#searchDchnoBtn').button('free');
	           searchDchnoDlg.hide();
	}
}

function clickToolbar() {

}
function showScan() {

}
/**
 * 
 * @param cellData  С������
 * @param xy		ҳ��xyֵ
 * @param lonlat	������γ��
 */
function showCellInfo(cellData,xy,lonlat){
	var $body=$(document.body);
	var offsetTop=popup.height();
	var offsetLeft=popup.width()/2;
	if(xy.x<offsetLeft||xy.y<offsetTop||($body.width()-xy.x)<offsetLeft){
		map.moveTo(lonlat);
		xy=map.getPixelFromLonLat(lonlat);
	}
//	$('#cellId').html(cellData.cellid);
//	$('#cellName').html(cellData.name);
//	$('#dchno').html(cellData.dchno);
//	$('#cellLonLat').html(cellData.lon+" ,"+cellData.lat);
//	$('#cellbearing').html(cellData.bearing);
//	$('#ant_height').html(cellData.antHeight);
	var eles=$('#cellInfo [id]');
	$.each(eles,function(idx,ele){
		var val=cellData[$(ele).attr('id')];
		if($(ele).attr('id')=='lon'||$(ele).attr('id')=='lat'){
			val=Math.floor(val*10000)/10000;
		}
		$(ele).html(val?val:'');
	});
	popup.data('lonlat',lonlat);
	popup.showInfo(xy.x,xy.y);
}
/**
 * ��С������
 * @param lonlats
 * @param _style
 */
function drawCellShape(lonlats,_style){
    var points=[];
    for(var i=0;i<lonlats.length;i+=2){
	    	var destPoint=new OpenLayers.Geometry.Point(lonlats[i],lonlats[i+1]);
			points[points.length]=destPoint;
	  }
	var linear=new OpenLayers.Geometry.LinearRing(points);
	//��С��������
	var cellPolygon = new OpenLayers.Geometry.Polygon(linear);
	var cellShape = new OpenLayers.Feature.Vector(linear, null, _style);
	vector.addFeatures([cellShape]);
 }
/*
 *�����ߣ�Ĭ��Ϊ�ղ��û���
 **/

function drawLinkLine(p1,p2){
		var line=new OpenLayers.Geometry.LinearRing([p1,p2]);
		var lineFeature = new OpenLayers.Feature.Vector(line, null, style_line);
		vector.addFeatures([lineFeature]);
}
//��С��
function drawCell(cellShape, cellStyle) {
	var shape = new Array();
	for ( var i = 0; i < cellShape.length; i++) {
		var p = new OpenLayers.Geometry.Point(cellShape[i].lon,
				cellShape[i].lat);
		shape.push(p);
	}
	var linear = new OpenLayers.Geometry.LinearRing(shape);
	//��С��������
	var scanPolygon = new OpenLayers.Geometry.Polygon([ linear ]);
	var _cellStyle = cellStyle ? cellStyle : style_cell;
	var cellShape = new OpenLayers.Feature.Vector(scanPolygon, null, _cellStyle);
	return cellShape;
}



/**
 * ͼ��ת��
 */
function switchLayer() {
	changelayer(cellLayerCbox.attr('checked'), 'cellMap', cellMap);
	changelayer(scanLayerCbox.attr('checked'), 'scanMap', scanMap);
	 //map.raiseLayer(vector,map.layers.length-1);
	// map.raiseLayer(scanMap,4);
}
function changelayer(show, id, layer) {
	try {
		if (show) {
			map.addLayer(layer);
		} else {
			map.removeLayer(layer);
		}
	} catch (e) {
	}
}
function showSwitcher() {
	switcherDlg.show();
}
function resetMap(){
//	cellInfoPopup.close();
}
/**
 * ����С������������
 */
var searchDlg;//����С��������������
var searchDchnoDlg;//����ͬƵС��
var cellLayerCbox;//
var scanLayerCbox;//
var searchType = 1;// searchCell=1,searchCellr=2;
function openSearchDlg(_search) {
	searchType = _search;
	toggleControl(1);
	if (_search== 1) {
		searchDlg.setTitle('����С��');
	}
	if (_search== 2) {
		searchDlg.setTitle('��������');
	}
	searchDlg.show();
	$('#_cellid').val('');
	$('#_cellid').focus();
}
function openSearchDchnoDlg(){
	searchDchnoDlg.show();
	$('#search_dchno').focus();
}
function searchCell() {
		var cellname = $('#_cellid').val();
		if ($('#searchCellCnRadio').prop('checked')) {
			cellname=$('#search_cellnameCn').val();
		}
		searchDlg.hide();
		if (searchType == 1) {
			queryCell(cellname,$('#searchCellCnRadio').prop('checked'));
		}
		if (searchType == 2) {
			queryCellr(cellname,$('#searchCellCnRadio').prop('checked'));
		}
}
function initSearchDlg(){
	searchDlg = new Dialog('dlgSearch',{
		title : "����С��",
		width:300,
		height:160,
		draggable:false,
		buttons:{ok:{value:'����',iconCls:'icon-search',click:function(){searchCell();}}}
	});
	searchDchnoDlg = new Dialog('dlgDchnoSearch',{
		title : "����ͬƵС��",
		width:300,
		height:160,
		draggable:false,
		buttons:{ok:{id:'searchDchnoBtn',value:'����',iconCls:'icon-search',click:function(){queryCellByDchno();}}}
	});

	cellLayerCbox = $('#cellLayer').checkbox({
		name : "cellLayer",
		checked : true,
		"value" : "cellMap"
	});
	scanLayerCbox =$('#scanLayer').checkbox({
		name : "scanLayer",
		checked : true,
		"value" : "scanMap"
	});
}
//С������������
function autoCompleteCell(){
	 var url=_appContext+"/gis/selectCell.do?action=queryCellids&toolCode="+getToolCode();
//	 $('#_cellid').jqInputText();
       $('#_cellid').autocomplete(url,{
      	width: 156,
      	minChars:1,          //������������ַ���ʼ��ѯ
      	max:200,             //������ʾ���ٸ�
    		highlight: false,
    		matchContains: true,  //�����Ƚ�ʱ�Ƿ�Ҫ���ַ����ڲ��鿴ƥ��
    		matchSubset:true,     //�Ƿ�ʹ�û���
    		matchCase:true ,      //�Ƿ���������ҵĴ�Сд
    		cacheLength :2000,     //���泤��
    		multiple: false,      //input���Ƿ�֧����ѡ��
    		multipleSeparator:" ",
    		scroll: true,
    		scrollHeight: 150,
    		matchCase :true,    //�Ƿ�����Сд
    		extraParams : {     //���ݲ���
			  cellid : function() {
				return encodeURI($("#_cellid").val());
			  }
		    },
		   formatItem: function(row) { return row; }  //��������ʾ���� 		
         });
       
       ///����С������
       var url=_appContext+"/gis/selectCell.do?action=queryCellNames&toolCode="+getToolCode();
//	 $('#_cellid').jqInputText();
       $('#search_cellnameCn').autocomplete(url,{
    	   width: 156,
    	   minChars:1,          //������������ַ���ʼ��ѯ
    	   max:200,             //������ʾ���ٸ�
    	   highlight: false,
    	   matchContains: true,  //�����Ƚ�ʱ�Ƿ�Ҫ���ַ����ڲ��鿴ƥ��
    	   matchSubset:true,     //�Ƿ�ʹ�û���
    	   matchCase:true ,      //�Ƿ���������ҵĴ�Сд
    	   cacheLength :2000,     //���泤��
    	   multiple: false,      //input���Ƿ�֧����ѡ��
    	   multipleSeparator:" ",
    	   scroll: true,
    	   scrollHeight: 150,
    	   matchCase :true,    //�Ƿ�����Сд
    	   extraParams : {     //���ݲ���
    		   cellid : function() {
    			   return encodeURI($("#search_cellnameCn").val());
    		   }
    	   },
    	   formatItem: function(row) { return row; }  //��������ʾ���� 		
       });
       
	}
//����Ƶ�������ʾ
var searchDchnoTip;
function blurSearchDchno(ele){
		if($(ele).val().length>0&&/^(\d)|([1-9]\d+)$/.test($(ele).val())){
			if(searchDchnoTip)searchDchnoTip.fadeOut(300);
			return true;
		}else{
			if(!searchDchnoTip){
				searchDchnoTip=$("<div style='text-align:center;color:red'>������������</div>").anchored({title:'�������',parentId:'map',show:true});
				var offset2=$(ele).offset();
				searchDchnoTip.css('left',offset2.left-30);
				searchDchnoTip.css('top',offset2.top-108);
				searchDchnoTip.css('z-index',5000);
			}
			if(!searchDchnoTip.attr('closed')){
				searchDchnoTip.attr('closed','1');
				searchDchnoTip.fadeIn(300).delay(3000).fadeOut(1000,function(){
					searchDchnoTip.removeAttr('closed');
				});
			}
			return false;
		}
}
var uploadMapInfoDlg;
function showUploadMapInfoDlg(){
	if(!uploadMapInfoDlg){
		$('#uploadMapInfoDlg').show();
		var h=$(document).height();
		 if(h>440||h==0)h=420;
		uploadMapInfoDlg=new Dialog("uploadMapInfoDlg",
								{
									title : "�ϴ�MapInfo����",
									width:520,
									height:460,
									top:'1%'
								}
		);
		//#springUrl('')/views/ns/gis/GeoDataCtrl.do?action=uploadMain
		$('#uploadMapInfoDlgIframe').attr('src',_appContext+'/gis/GeoDataCtrl.do?action=uploadMain&toolCode='+getToolCode());
	}
	$('#uploadMapInfoDlgIframe').attr('src',_appContext+'/gis/GeoDataCtrl.do?action=uploadMain&toolCode='+getToolCode());
	uploadMapInfoDlg.show();
}
