var map;
var cellMap;
var format;
var gmap1;
var gmap2;
var gmap3;
var currMap = null;
var selectedDiv = null;
var vector;//显示后台查找地图数据的图层，小区，频点，连线
var gridVector;
var scanMap;
var style_mark;//红色点和面
var style_cell;//主小区红色点和面
var style_cellr;//邻区红色点和面
var style_line;//红线
var style_line2;//蓝线
var styleMap;
var showGGlMap = true;//显示GOOGLE地图
var drawLayer;//
var drawControls;
var boxes;//画正方形的图层
var popup;
var features=[];
function initBaseMap() {
	if (typeof (google) == "undefined"||typeof(google.maps) == "undefined"){
		alert("网络连接断开，不能显示google地图");
		showGGlMap = false;
	}
	var layerPre="";
	try{
		if(_orgId){
			layerPre=_orgId;
		}
	}catch(e){
		
	}
	var _tileSize=new OpenLayers.Size(256,256);
	format = 'image/jpeg';
	var bounds = new OpenLayers.Bounds(115.6404, 22.8982, 117.1603012, 24.22722244);
	var tempScales = [ 200000, 100000, 50000, 20000, 10000, 4000 ];
	var options = {
		controls:[new OpenLayers.Control.Navigation(),
				new OpenLayers.Control.PanZoomBar(),
				new OpenLayers.Control.Pan(),
				new OpenLayers.Control.ArgParser(),
				new OpenLayers.Control.Attribution()],
		// maxExtent: bounds,   
		//maxResolution: 1/100000000,
		//	scales : tempScales,
		//	panTween:tween,
		numZoomLevels : 18,
		//  fractionalZoom: true,
		projection : "EPSG:4326",
		//units : 'degrees',
		allOverlays : true,
		div : 'map',
		fractionalZoom : false,
		panMethod: OpenLayers.Easing.Quad.easeInOut,
		_tileSize:_tileSize

	};
	map = new OpenLayers.Map(options);
	var maxBounds= new OpenLayers.Bounds(
			   70,
	           5,
	           150,
	           60
	        );
	cellMap = new 	OpenLayers.Layer.TileCache("MAP_CDD_CELL",
			[_appContext + "/gis/index.do?action=index"], 
			"cell",
			{
				tileSize:_tileSize,
				maxExtent:maxBounds,
				transitionEffect:'resize'
			   }
		);
	scanMap = new 	OpenLayers.Layer.TileCache("scanLayer",
			[_appContext + "/gis/index.do?action=index"], 
			_layerName,
			{
				tileSize:_tileSize,
				maxExtent:maxBounds,
				transitionEffect:'resize'
			}
	);
	//街道
	if (showGGlMap) {
		gmap1 = new OpenLayers.Layer.Google("Google Streets", // the default
		{
			sphericalMercator : false
		});
		currMap = gmap1;
		//卫星矢量
		gmap2 = new OpenLayers.Layer.Google("Google Hybrid", {
			sphericalMercator : false,
			type : google.maps.MapTypeId.HYBRID,
			numZoomLevels : 20
		});
		//地形
		gmap3 = new OpenLayers.Layer.Google("Google Physical", {
			sphericalMercator : false,
			type : google.maps.MapTypeId.TERRAIN,
			numZoomLevels : 20
		});
	}
	vector = new OpenLayers.Layer.Vector("Points");
	gridVector = new OpenLayers.Layer.Vector("gridVector");
	drawLayer = new OpenLayers.Layer.Vector("Polygon Layer");
	boxes = new OpenLayers.Layer.Boxes("Boxes");
	if (showGGlMap)
		map.addLayers([cellMap,scanMap,gmap1]);
	else
		map.addLayers([scanMap,cellMap]);
//	map.addLayer(scanMap);
	//map.addLayer(gridMap);
	  map.setCenter(new OpenLayers.LonLat(113.2, 25.01).transform(
  	        new OpenLayers.Projection("EPSG:4326"),
  	        map.getProjectionObject()
  	    ), 15);
	map.events.register("move", map, function(e) { 
			var lonlat=popup.data('lonlat');
			if(lonlat){
				var xy=map.getPixelFromLonLat(popup.data('lonlat'));
				popup.move(xy.x,xy.y);
			}
        });
//	map.events.register("click", map, function(e) { 
//		var xy=map.getLonLatFromViewPortPx(e.xy);
//		$('#lonlat').html(xy.lon+":"+xy.lat);
////		map.setCenter(xy);
//	});
	map.addLayer(vector);
	    $('#map').mousedown(function(){
	 	    	$(this).addClass('map_move');
	 	    });
	    $('#map').mouseup(function(){
	 	    	$(this).removeClass('map_move');
	 	    });
}
function initStyle() {
	var sketchSymbolizers = {
		"Polygon" : {
			strokeWidth : 2,
			strokeOpacity : 1,
			strokeColor : "#666666",
			fillColor : "white",
			fillOpacity : 0.3
		},
        "Line": {
            strokeWidth: 3,
            strokeOpacity: 1,
            strokeColor: "#666666",
            strokeDashstyle: "dash"
        }
	};
	var style = new OpenLayers.Style();
	style.addRules([ new OpenLayers.Rule({
		symbolizer : sketchSymbolizers
	}) ]);
	styleMap = new OpenLayers.StyleMap({
		"default" : style
	});
	//选中小区扇形样式
	style_cell = OpenLayers.Util.extend({},
			OpenLayers.Feature.Vector.style['default']);
	style_cell.graphicWidth = 1;
	style_cell.fillColor = "#ff0000";
	style_cell.fillOpacity = 0.9;
	//邻区
	style_cellr = OpenLayers.Util.extend({},
			OpenLayers.Feature.Vector.style['default']);
	style_cellr.graphicWidth = 1;
	style_cellr.fillColor = "#FFFF11";
	style_cellr.fillOpacity = 0.9;
	//扫频点样式
	style_mark = OpenLayers.Util.extend({},
			OpenLayers.Feature.Vector.style['default']);
	style_mark.graphicWidth = 4;
	style_mark.fillColor = "#6CE263";
	style_mark.fillOpacity = 0.8;
	//扫频点到小区的连线着色
	style_line = OpenLayers.Util.extend({},
			OpenLayers.Feature.Vector.style['default']);
	style_line.strokeWidth = 1;
	style_line.strokeColor = "#0055ff";
	style_line.strokeOpacity = 0.8;
	//扫频点到选中主小区的连线着色
	style_line2 = OpenLayers.Util.extend({},
			OpenLayers.Feature.Vector.style['default']);
	style_line2.strokeWidth = 1;
	style_line2.strokeColor = "#ee0011";
	style_line2.strokeOpacity = 0.8;
}
function initCtrl() {
	drawControls = {
//		pan : new OpenLayers.Control.Pan(),
		cellInfo : new OpenLayers.Control.DrawFeature(drawLayer,
				OpenLayers.Handler.Point, {
					callbacks : {
						'done' : function(point) {
							queryCellInfoByPoint(point.x, point.y);
						}
					}
				}),
		line : new OpenLayers.Control.DrawFeature(drawLayer,
				OpenLayers.Handler.Path),
		polygonMeasure : new OpenLayers.Control.Measure(
				OpenLayers.Handler.Polygon, {
					persist : true,
					handlerOptions : {
						layerOptions : {
							styleMap : styleMap
						}
					}
				}),
		  distance: new OpenLayers.Control.Measure(
		                    OpenLayers.Handler.Path, {
		                        persist: true,
		                        handlerOptions: {
		                            layerOptions: {styleMap : styleMap}
		                        },
		                        callbacks:{
		                        	modify:measureDist
		                        }
		                    }
		                )
	};
	drawControls['distance'].events.on({
        "measure": handleMeasureComplete,
        "measurepartial": handleMeasurements,
        "deactivate": function(){if(distanceDiv)distanceDiv.hide();}
    });
	for ( var key in drawControls) {
		map.addControl(drawControls[key]);
	}
}

function initMap() {
	initBaseMap();
	initStyle();
	initCtrl();
	var mapCtlDiv = document.getElementById("mapTypeCtl");
	var toolbarDiv = document.getElementById("mapToolbar");
	OpenLayers.Event.observe(mapCtlDiv, "mousedown", OpenLayers.Function
			.bindAsEventListener(OpenLayers.Event.stop));
	//停止toolbarDiv点击事件冒泡 
	OpenLayers.Event.observe(toolbarDiv, "mousedown", OpenLayers.Function
			.bindAsEventListener(OpenLayers.Event.stop));
	OpenLayers.Event.observe(toolbarDiv, "dblclick", OpenLayers.Function
			.bindAsEventListener(OpenLayers.Event.stop));
	initSearchDlg();
	//查找小区地理数据
	popup=$('#cellInfo').anchored({show:false});
//    popup = new OpenLayers.Popup.Anchored("cellInfo", 
//            new OpenLayers.LonLat(5,40));
//    map.addPopup(popup);
};
/////  测距  ////////
var distanceDiv;
function measureDist(point,line){
	var  measure=this.getBestLength(line.geometry);
	 showDist(measure[0],measure[1],line.geometry);
}
function handleMeasureComplete(event) {
	 var geometry = event.geometry;
	    var units = event.units;
	    var measure = event.measure;
	    showDist(measure,units,geometry);
}
function handleMeasurements(event) {
    var geometry = event.geometry;
    var units = event.units;
    var measure = event.measure;
    showDist(measure,units,geometry);
}
/**
 * 显示距离
 * @param measure
 * @param units
 * @param geometry
 */
function showDist(measure,units,geometry){
    if(!distanceDiv){
    	distanceDiv=$('<div class="head" style="border:1px solid #ad11aa;padding:3px;height:22px;z-index:10000;position:absolute;left:0px;top:0px;background:#ffffff;"></div>').appendTo(document.body);
    }
    var comps=geometry.components;
    var xy=comps[comps.length-1];
    var xy2=comps[0];
    var pixel=map.getPixelFromLonLat(new OpenLayers.LonLat((xy.x+xy2.x)/2,(xy.y+xy2.y)/2));
    distanceDiv.show();
    distanceDiv.html("距离:"+measure.toFixed(3)+ units);
    distanceDiv.css('top',pixel.y-30);
    distanceDiv.css('left',pixel.x-distanceDiv.width()-10);
}
//查找小区
function queryCellByLonLat(lonlat) {
	isSearchCellr = false; 
	$.ajax({
		url : _appContext + "/gis/selectCell.do?action=queryCell",
		dateType : "json",
		type:'POST',
		data : {'lon' : lonlat.lon,'lat':lonlat.lat},
		success:function(data){
			if(data){
				map.setCenter(new OpenLayers.LonLat(data.points[0], data.points[1]));
				drawCellShape(data.points,style_cell);
			}
		}
	});
}
var allErrorCell = [];
var isSearchCellr = false;//是否查找邻区

//查找小区
function queryCell(cellid) {
	var tname=_layerName?_layerName:"";
	isSearchCellr = false; 
	$.ajax({
		url : _appContext + "/views/tk/scan/ScanCtrl.do?action=queryCellScan&name="+tname,
		dateType : "json",
		type:'post',
		data : {'cellid' : encodeURI(cellid)},
		success:queryCellBack
	});
}
//查找小区回调方法
function queryCellBack(data){
	if(data){
		if (features.length > 0) {
			vector.removeFeatures(features);
			features = [];
		}
//		vector.removeAllFeatures();
		var mcell=data.cell;
		var scans=data.scans;
		if(!mcell.lon)return;
		var cellPoint=new OpenLayers.LonLat(mcell.lon, mcell.lat);
		map.setCenter(cellPoint);
		var xy=map.getPixelFromLonLat(cellPoint);
		drawCellShape(mcell.points,style_cell);
		showCellInfo(mcell,xy,cellPoint);
		drawScan(scans,mcell,style_mark);
		try{
			delete scans;
			delete mcell;
		}catch(e){
		}
	}
}
function drawScan(scans,mcell,_style){
	
	var cellPoint=new OpenLayers.Geometry.Point(mcell.lon, mcell.lat);
    _style.fillColor="#ffff00";
    style_line.strokeColor="#ff8800";
    var j=features.length;
    var k=scans.length;
    var points=[];
    var lines=[];
    //alert("频点数量:"+k);
    for(var i=0;i<k;i++){
	    	var p=new OpenLayers.Geometry.Point(scans[i].lon,scans[i].lat);
	    	points[i]=p;
			var li=new OpenLayers.Geometry.LineString([cellPoint,p]);
			lines[i]=li;
    }
    var multiLine=new OpenLayers.Geometry.MultiLineString(lines);
    var multip=new OpenLayers.Geometry.MultiPoint(points);
    features[j]=new OpenLayers.Feature.Vector(multiLine, null,style_line);
    j++;
    features[j]=new OpenLayers.Feature.Vector(multip, null, _style);
	vector.addFeatures(features);
	try{
		delete lines;
		delete points;
	}catch(e){
	}
	
}
function showCellInfo(cellData,xy,lonlat){
	$('#cellId').html(cellData.cellid);
	$('#cellName').html(cellData.name);
	$('#dchno').html(cellData.dchno);
	popup.data('lonlat',lonlat);
	popup.showInfo(xy.x,xy.y);
}
//查找邻区
function queryCellr(cellid) {
	isSearchCellr = true;
	jQuery.ajax({
		type: "post",      //提交方式
		url : _appContext + "/gis/selectCell.do?action=cellR",
		data :{'cellid' : cellid}, 
		dateType: "json",  
		cache:false,
		success:function(data){
			if(data){
				vector.removeAllFeatures();
				var mcell=data.mcell;
				//alert(mcell);
				if(!mcell)return;
				var cellPoint=new OpenLayers.LonLat(mcell.points[0], mcell.points[1]);
				map.setCenter(cellPoint);
				var xy=map.getPixelFromLonLat(cellPoint);
//				style_cell.fillColor="#"+mcell.fillColorStr;
//				style_cell.strokeColor="#"+mcell.strokeColorStr;
				var cellrs=data.cellr;
				for(var k=0;k<cellrs.length;k++){
//					style_cellr.fillColor="#"+cellrs[k].fillColorStr;
//					style_cellr.strokeColor="#"+cellrs[k].strokeColorStr;
					drawCellShape(cellrs[k].points,style_cellr);
				}
				drawCellShape(mcell.points,style_cell);
				showCellInfo(mcell,xy);
			}
			
		},
		error: function(request, error, status){ 
		}
	});

}
function callback(data) {
	if (data && data.length > 0) {
		if (data.length && data.length > 1) {
			for ( var i = 0; i < data.length; i++) {
				if (i != data.length - 1) {
					var scanCellftr = data[i].cell.scellFeature;
					var style = style_cell;
					if (isSearchCellr)
						style = style_cellr;
					allErrorCell[i] = drawCell(scanCellftr, style);
					drawGrid(data[i].grid);
				}
			}
			var p = data[data.length - 1].cell.scellFeature;
			map.setCenter(new OpenLayers.LonLat(p[0].lon, p[0].lat))
			allErrorCell[allErrorCell.length] = drawCell(p, style_cell);
			vector.addFeatures(allErrorCell);
		} else {
			showScanPoits(data[0].cell);
			try {
				var p = data[0].cell.scellFeature[0];
				map.setCenter(new OpenLayers.LonLat(p.lon, p.lat));
			} catch (e) {
			}
		}
	}
}

function showErrorCell(data) {
	//选中小区显示为红色
	if (features.length > 0) {
		features = [];
	}

}
/**
 * 查找小区信息
 * @param lon
 * @param lat
 */
//dojo.require('dijit.Tooltip');
//var cellInfoPopup = new dijit.Tooltip({label:"<div class='tipcontent'>cellid:<span id='cellidInfo'></span> " +
//	"<br />小区名:<span id='cellNameInfo'></span><br/>主频:<span id='bcchInfo'></span> " +
//	"<br />方位角:<span id='bearingInfo'></span><br/>下倾角:<span id='downtiltInfo'></span><br/> " +
//	"天线高度:<span id='antHeightInfo'></span></div>",duration:100,position:['above']});
var anchored=null;
function queryCellInfoByPoint(_lon, _lat) {
	var foo = $.ajax({
		url : _appContext + "/gis/layerCtrl.do?action=queryCell",
		type : "json",
		data : {
			lon : _lon,
			lat : _lat
		},
		success:function(data) {
			if(data){
			    anchored=document.createElement("<span style='position:absolute;'></span>");
			    anchored.style.zIndex=30000;;
			    var body = document.getElementsByTagName("body")[0];
			    body.appendChild(anchored);
			 	var ll=new OpenLayers.LonLat(_lon,_lat);
			 	var position=map.getPixelFromLonLat(ll);
			 	anchored.style.left=position.x-7;
			 	anchored.style.top=position.y+3;
			 	cellInfoPopup.open(anchored);
				dojo.byId('cellidInfo').innerHTML=data.cellid;
				dojo.byId('cellNameInfo').innerHTML=data.cellName;
				dojo.byId('bcchInfo').innerHTML=data.arfcn;
				dojo.byId('bearingInfo').innerHTML=data.bearing;
				dojo.byId('downtiltInfo').innerHTML=data.downtilt;
				dojo.byId('antHeightInfo').innerHTML=data.antHeigh;
				var radius=data.radius*1000;
				var bearing=data.bearing;
				var radian=data.antVbw;
				var points=calculateCellPoints(data,bearing,360,radius);
				//var linear=new OpenLayers.Geometry.LinearRing(points);
				//画小区的扇形
				//var cellPolygon = new OpenLayers.Geometry.Polygon(linear);
				//var cellShape = new OpenLayers.Feature.Vector(linear, null, style_cell);
				//vector.addFeatures([cellShape]);
			}
		},
		preventCache : true
	});
}
/**
 * 计算小区的十八个点
 * @param srcLonLat
 * @param bearing 方位角
 * @param radian  弧度
 * @param radius  半径
 */
function calculateCellPoints(data,bearing,radian,radius){
	var points=[];
	var srcLonLat=new OpenLayers.LonLat(data.lon,data.lat);
	var srcPoint=new OpenLayers.Geometry.Point(data.lon,data.lat);
	points[0]=srcPoint;
	for(var i=1;i<4;i++){
	var destLonLat=OpenLayers.Util.destinationVincenty(srcLonLat,(radian/i),radius);
	var destPoint=new OpenLayers.Geometry.Point(destLonLat.lon,destLonLat.lat);
	points[i]=destPoint;
	}
	return points;
}
//改变地图，卫星、地表、街道
function changeMap(div, type) {
	if (selectedDiv != div) {
		$(div).addClass('typeSelected');
		if (selectedDiv)
			$(selectedDiv).removeClass('typeSelected');
		selectedDiv = div;
	}
	if (type == 1 && gmap1 != currMap) {
		map.addLayer(gmap1);
		map.removeLayer(currMap, true);
		currMap = gmap1;
	}
	if (type == 2 && gmap2 != currMap) {
		map.addLayer(gmap2);
		//	map.setBaseLayer(gmap2);
		map.removeLayer(currMap, true);
		currMap = gmap2;
	}
	if (type == 3 && gmap3 != currMap) {
		map.addLayer(gmap3);
		map.removeLayer(currMap, true);
		currMap = gmap3;
	}
}
function clickToolbar() {

}
function showScan() {

}
function showScanPoits(data) {
	if (data) {
		if (features.length > 0) {
			vector.destroyFeatures(features);
			features = [];
		}
		var j = 0;
		var points = data.scanPoints;
		//选中小区显示为红色
		var scanCellftr = data.scellFeature;
		features[j] = drawCell(scanCellftr);
		j++;
		for ( var i = 0; i < points.length; i++) {
			var p = points[i];
			var scanPoint = new OpenLayers.Geometry.Point(p.lon, p.lat);
			features[j] = new OpenLayers.Feature.Vector(scanPoint, null,
					style_mark);
			j++;
			var cells = p.cellPoints;
			// 扫频点和关联小区连线
			for ( var k = 0; k < cells.length; k++) {
				var cell = cells[k];
				var ar = new Array();
				ar.push(scanPoint);
				var cellPoint = new OpenLayers.Geometry.Point(cell.lon,
						cell.lat);
				ar.push(cellPoint);
				var line = new OpenLayers.Geometry.LineString(ar);
				var lineStyle = (cell.link2mainCell ? style_line2 : style_line);
				features[j] = new OpenLayers.Feature.Vector(line, null,
						lineStyle);
				j++;
			}
		}
		vector.addFeatures(features);
	}
}

function drawCellShape(lonlats,_style){
    var points=[];
    for(var i=0;i<lonlats.length;i+=2){
	    	var destPoint=new OpenLayers.Geometry.Point(lonlats[i],lonlats[i+1]);
			points[points.length]=destPoint;
	  }
	var linear=new OpenLayers.Geometry.LinearRing(points);
	//画小区的扇形
	var cellPolygon = new OpenLayers.Geometry.Polygon(linear);
	var cellShape = new OpenLayers.Feature.Vector(linear, null, _style);
	features[features.length]=cellShape;
	vector.addFeatures(cellShape);
 }
//画小区
function drawCell(cellShape, cellStyle) {
	var shape = new Array();
	for ( var i = 0; i < cellShape.length; i++) {
		var p = new OpenLayers.Geometry.Point(cellShape[i].lon,
				cellShape[i].lat);
		shape.push(p);
	}
	var linear = new OpenLayers.Geometry.LinearRing(shape);
	//画小区的扇形
	var scanPolygon = new OpenLayers.Geometry.Polygon([ linear ]);
	var _cellStyle = cellStyle ? cellStyle : style_cell;
	var cellShape = new OpenLayers.Feature.Vector(scanPolygon, null, _cellStyle);
	return cellShape;
}
function toggleControl(value) {
	for (key in drawControls) {
		var control = drawControls[key];
		if ( key==value) {
			control.activate();
		} else {
			control.deactivate();
		}
	}
}
/**
 * 图层转换
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
 * 查找小区，查找邻区
 */
var searchDlg;//查找小区和邻区弹出框
var switcherDlg;//图层转换弹出框
var cellLayerCbox;//
var scanLayerCbox;//
var searchType = 1;// searchCell=1,searchCellr=2;
//dojo.require("dijit.form.Button");
//dojo.require("dijit.form.TextBox");
//dojo.require("dijit.form.CheckBox");
//dojo.require("dijit.Dialog");
function openSearchDlg(_search) {
	searchType = _search;
	toggleControl(1);
	if (_search== 1) {
		searchDlg.setTitle('查找小区');
	}
	if (_search== 2) {
		searchDlg.setTitle('查找邻区');
	}
	searchDlg.show();
	$('#_cellid').val('');
	$('#_cellid').focus();
}
function searchCell() {
	searchDlg.hide();
	var cellid = $('#_cellid').val();
	if (searchType == 1) {
		queryCell(cellid);
	}
	if (searchType == 2) {
		queryCellr(cellid);
	}
}
function initSearchDlg(){
	searchDlg = new Dialog('dlgSearch',{
		title : "查找小区",
		width:300,
		height:160,
		buttons:{ok:{value:'查找',iconCls:'icon-search',click:function(){searchCell();}}}
	});
	switcherDlg = new Dialog('switchDlg',{
		title : "图层控制",
		buttons:{ok:{value:'确定',iconCls:'icon-ok',click:function(){switchLayer();}}}
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