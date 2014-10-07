var style_mark_green;
var style_mark_grassgreen;
var style_mark_blue;
var style_mark_yellow;
var style_mark_purple;
var style_mark_red;

var markers;

var createShowCellsDlg;
var createShowListDlg;

var tableName;
var LayerType;

/*
 * initMapAfter() 方法在地图初始化后调用 
 * 
 */

function initMapAfter(){
//	  alert("after");
	$('#mapToolbar').append('<img width="24" height="22" onClick="createCell();toggleControl(\'viewCellGrid\');" title="生成小区" src="'+_appContext+'/gis/toolbar/createcell.png"/>');
	$('#mapToolbar').append('<img width="24" height="22" onClick="rectangleSelect();toggleControl(\'rectangle\')" title="选择扫频点" src="'+_appContext+'/images/d11/gis/selectscan.png"/>');
	$('#mapToolbar').append('<img width="24" height="22" onClick="td_showScan();toggleControl(\'cellscan\');" title="显示最强扫频点" src="'+_appContext+'/images/d11/gis/showscan.png"/>');
	$('#mapToolbar').append('<img width="24" height="22" onClick="toggleControl(\'cellInfo\');" title="邻区显示" src="'+_appContext+'/images/d11/gis/showcellr.png"/>');
	$('#mapToolbar').append('<img width="24" height="22" onClick="exportSelect();" title="结果导出" src="'+_appContext+'/images/d11/gis/export.png"/>');
	$('#mapToolbar').append('<img width="24" height="22" onClick="showCell();" title="小区列表" src="'+_appContext+'/images/d11/gis/celllist.png"/>');
	
	var layer_style = OpenLayers.Util.extend({}, OpenLayers.Feature.Vector.style['default']);
	layer_style.graphicWidth = 10;
	layer_style.graphicHeight = 10;
    
	style_mark_green = OpenLayers.Util.extend({},layer_style);
    style_mark_green.externalGraphic = _appContext + "/images/d11/points/t1.png";
    
    style_mark_grassgreen = OpenLayers.Util.extend({},layer_style);
    style_mark_grassgreen.externalGraphic = _appContext + "/images/d11/points/t2.png";
    
    style_mark_blue = OpenLayers.Util.extend({},layer_style);
    style_mark_blue.externalGraphic = _appContext + "/images/d11/points/t3.png";
    
    style_mark_yellow = OpenLayers.Util.extend({},layer_style);
    style_mark_yellow.externalGraphic =_appContext + "/images/d11/points/t4.png";
    
    style_mark_purple = OpenLayers.Util.extend({},layer_style);
    style_mark_purple.externalGraphic = _appContext + "/images/d11/points/t5.png";
    
    style_mark_red = OpenLayers.Util.extend({},layer_style);
    style_mark_red.externalGraphic = _appContext + "/images/d11/points/t6.png";
   
    var layerNames = ['scan_wcover', 'scan_over_point','scan_cover_point','scan_leak_point', 'scan_pollute_poin'];
	var toolCode=getToolCode(window);
	for(var i=0;i<layerNames.length;i++){
		 editLayer.hideLayer(layerNames[i],toolCode);
	}
	LayerType='scan_td';
	
	$.ajax({
		url : _appContext + "/views/dnpi/gis/tdColor.do?action=getColor",
		dateType : "json",
		type:'POST',
		success:function(data){
			$(".tuli-content").empty();
			$('.tuli-content').append("<div style='font-size:12px;padding-left: 5px;padding-top:3px;padding-bottom:1px;'>图例:</div>")
			for(var i=0;i<data.length;i++){
				var dd=$('<div class="color_value">');
				dd.appendTo($(".tuli-content"));
				dd.append("<div  class='color' style='background:"+data[i].color+";'></div>");
				dd.append("<div class='value'>"+data[i].value+"</div>");
				
			}
		}
	});
   
}

/**
 * 显示邻区
 */
/*function td_showCellr(){
	showCellr=new OpenLayers.Control.DrawFeature(vector,
			OpenLayers.Handler.Click,{
				callbacks : {
					click : function(b){
						var c =map.getLonLatFromPixel(new OpenLayers.Pixel(b.x,b.y));
						td_queryCellByLonLat(c,"cellr");
					},
					stopSingle : true
				}
			});
		drawControls['showCellr']=showCellr;
		map.addControl(drawControls['showCellr']);
	
}
*/

/**
 * 切换图层
 */
function clickRadio(radioid){
	document.getElementById(radioid).click();
	var layerNames = [ 'scan_td' , 'scan_wcover', 'scan_over_point','scan_cover_point','scan_leak_point', 'scan_pollute_poin'];
	 var toolCode=getToolCode();
	 for(var i=0;i<layerNames.length;i++){
		 if(radioid==layerNames[i]){
			 editLayer.showLayer(layerNames[i],toolCode);
			 LayerType = layerNames[i];
			 
		 }else{
			 editLayer.hideLayer(layerNames[i],toolCode);
		 }
	 } 
}


//获取颜色值
function queryColor() {
	$(".tuli-content").toggle();
}

/**
 * 通过小区id查找小区
 */
function queryCellByCellid(cellid) {
	parent.createShowCellsDlg.hide();
	parent.createShowListDlg.hide();
	isSearchCellr = false; 
	var params={};
	var val=encodeURI(cellid);
	 params={'cellid': val};
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
				parent.drawCellShape(data.points,style_cell);
				parent.showCellInfo(data,xy,cellPoint);
				parent.map.zoomTo(15);
			}
		}
	});
}

/**
 * 显示某个小区的扫频点
 */
function td_showScan(){
	if(!drawControls['cellscan']){
		cellscan=new OpenLayers.Control.DrawFeature(vector,
			OpenLayers.Handler.Click,{
				callbacks : {
					click : function(b){
						var c =map.getLonLatFromPixel(new OpenLayers.Pixel(b.x,b.y));
						td_queryCellByLonLat(c);
					},
					stopSingle : true
				}
			});
		drawControls['cellscan']=cellscan;
		map.addControl(drawControls['cellscan']);
	}
}


//通过经纬度查找小区
function td_queryCellByLonLat(lonlat) {
	$.ajax({
		url : _appContext + "/gis/selectCell.do?action=queryCell",
		dateType : "json",
		type:'POST',
		data : {'lon' : lonlat.lon,'lat':lonlat.lat, 'toolCode':getToolCode()},
		success:function(data){
			vector.removeAllFeatures();
			var mcell = data.mcell;
			if (!mcell)return;
			style_cell.fillColor = "#" + mcell.fillColorStr;
			style_cell.strokeColor = "#" + mcell.strokeColorStr;
			drawCellShape(mcell.points, style_cell);
			queryScanByCellid(data.mcell.cellid,lonlat.lon,lonlat.lat);
			// alert("scan");
		}
	});
}

//根据小区id查找扫频点
function queryScanByCellid(cellid,celllon,celllat){
	
	$.ajax({
		url : _appContext + "/views/dnpi/gis/queryCellScan.do?action=queryCellScan",
		dateType : "json",
		type : 'POST',
		data : {'cellid' : cellid},
		success : function(data){
			for(var i=0;i<data.length;i++){
				var lon=data[i].scan.lon;
				var lat=data[i].scan.lat;
				var PCCPCHRSCP=data[i].PCCPCHRSCP;
				if(PCCPCHRSCP>-70){
					drawPoint(lon,lat,style_mark_green);
					drawCellLink(lon,lat,celllon,celllat);
				}else if(PCCPCHRSCP>-80 && PCCPCHRSCP<-70){
					drawPoint(lon,lat,style_mark_grassgreen);
					drawCellLink(lon,lat,celllon,celllat);
				}else if(PCCPCHRSCP>-85 && PCCPCHRSCP<-80){
					drawPoint(lon,lat,style_mark_blue);
					drawCellLink(lon,lat,celllon,celllat);
				}else if(PCCPCHRSCP>-90 && PCCPCHRSCP<-85){
					drawPoint(lon,lat,style_mark_yellow);
					drawCellLink(lon,lat,celllon,celllat);
				}else if(PCCPCHRSCP>-95 && PCCPCHRSCP<-90){
					drawPoint(lon,lat,style_mark_purple);
					drawCellLink(lon,lat,celllon,celllat);
				}else if(PCCPCHRSCP>-116 && PCCPCHRSCP<-95){
					drawPoint(lon,lat,style_mark_red);
					drawCellLink(lon,lat,celllon,celllat);
				}else{
					
				}
			}
		}
	});
}


/**
 * 矩形选择
 */
function rectangleSelect(){
	if(!drawControls['rectangle']){
		rectangle = new OpenLayers.Control.DrawFeature(drawLayer,
			OpenLayers.Handler.Box,
			{
				persist : true,
				callbacks : {
					'done' : function(bounds) {
						 var ll = map.getLonLatFromPixel(new OpenLayers.Pixel(bounds.left, bounds.bottom)); 
				          var ur = map.getLonLatFromPixel(new OpenLayers.Pixel(bounds.right, bounds.top)); 
					}
				},
				handlerOptions:{
					startBox: function (xy) {
						vector.removeAllFeatures();
					 	if(this.zoomBox)this.removeBox();
				        this.zoomBox = OpenLayers.Util.createDiv('zoomBox',
				                                                 this.dragHandler.start);
				        this.zoomBox.className = this.boxDivClassName;                                         
				        this.zoomBox.style.zIndex = this.map.Z_INDEX_BASE["popup"] - 1;
				        this.map.viewPortDiv.appendChild(this.zoomBox);

				        OpenLayers.Element.addClass(
				            this.map.viewPortDiv, "olDrawBox"
				        );
				 },
				endBox: function(end) {
			    var result;
			    if (Math.abs(this.dragHandler.start.x - end.x) > 5 ||    
			        Math.abs(this.dragHandler.start.y - end.y) > 5) {   
			        var start = this.dragHandler.start;
			        var top = Math.min(start.y, end.y);
			        var bottom = Math.max(start.y, end.y);
			        var left = Math.min(start.x, end.x);
			        var right = Math.max(start.x, end.x);
			        result = new OpenLayers.Bounds(left, bottom, right, top);
			        var xy1=new OpenLayers.Pixel(left,bottom);
			        var xy2=new OpenLayers.Pixel(left,top);
			        var xy3=new OpenLayers.Pixel(right,top);
			        var xy4=new OpenLayers.Pixel(right,bottom);
			        var l=map.getLonLatFromPixel(xy1);
			        var l2=map.getLonLatFromPixel(xy2);
			        var l3=map.getLonLatFromPixel(xy3);
			        var l4=map.getLonLatFromPixel(xy4);
			        
			        queryScanByRectSelect(l.lon,l.lat,l3.lon,l3.lat);
			       
			        drawDottedBox(l.lon,l.lat,l3.lon,l3.lat);
			       
			    } else {
			       // result = this.dragHandler.start.clone(); // i.e. OL.Pixel
			    } 
			   
			    this.removeBox();
			    this.callback("done", [result]);
				}
			  }
			});
		drawControls['rectangle']=rectangle;
		map.addControl(drawControls['rectangle']);
	}
}

//查找矩形选择区域内的扫频点
function queryScanByRectSelect(minLon,minLat,maxLon,maxLat){
	
	$.ajax({
		url : _appContext + "/views/dnpi/gis/queryCellScan.do?action=getCountByRect&LayerType="+LayerType,
		dateType : "json",
		type : 'POST',
		data : {'scan.minLon' : minLon,'scan.minLat':minLat, 'scan.maxLon':maxLon, 'scan.maxLat': maxLat},
		success : function(data){
			if(data.count>1000){
				askFunc("扫频点个数为:"+data.count+"，是否继续",function(){
					showScanByRectSelect(minLon,minLat,maxLon,maxLat);
					 },null,function(){	
					drawControls['rectangle'].handler.removeBox();	 
				});
				
			}
			else{
				showScanByRectSelect(minLon,minLat,maxLon,maxLat);
			}
		}
	});
}

function showScanByRectSelect(minLon,minLat,maxLon,maxLat){
	$.ajax({
			url : _appContext + "/views/dnpi/gis/queryCellScan.do?action=queryScanByRectSelect&LayerType="+LayerType,
			dateType : "json",
			type : 'POST',
			data : {'scan.minLon' : minLon,'scan.minLat':minLat, 'scan.maxLon':maxLon, 'scan.maxLat': maxLat},
			success : function(data){
				for(var i=0;i<data.length;i++){
					var lon=data[i].scan.lon;
					var lat=data[i].scan.lat;
					var PCCPCHRSCP=data[i].PCCPCHRSCP;
					
					var celllon = data[i].cell.lon;
					var celllat = data[i].cell.lat;
					if(PCCPCHRSCP>-70){
						drawPoint(lon,lat,style_mark_green);
						drawCellLink(lon,lat,celllon,celllat);
					}else if(PCCPCHRSCP>-80 && PCCPCHRSCP<-70){
						drawPoint(lon,lat,style_mark_grassgreen);
						drawCellLink(lon,lat,celllon,celllat);
					}else if(PCCPCHRSCP>-85 && PCCPCHRSCP<-80){
						drawPoint(lon,lat,style_mark_blue);
						drawCellLink(lon,lat,celllon,celllat);
					}else if(PCCPCHRSCP>-90 && PCCPCHRSCP<-85){
						drawPoint(lon,lat,style_mark_yellow);
						drawCellLink(lon,lat,celllon,celllat);
					}else if(PCCPCHRSCP>-95 && PCCPCHRSCP<-90){
						drawPoint(lon,lat,style_mark_purple);
						drawCellLink(lon,lat,celllon,celllat);
					}else if(PCCPCHRSCP>-116 && PCCPCHRSCP<-95){
						drawPoint(lon,lat,style_mark_red);
						drawCellLink(lon,lat,celllon,celllat);
					}else{
						
					}
				}
				drawControls['rectangle'].handler.removeBox();
			}
		});
}

/**
 * //扫频点与小区连线
 * @param p1
 * @param p2
 * @param p3
 * @param p4
 */
function drawCellLink(p1,p2,p3,p4){
	
	drawLinkLine(new OpenLayers.Geometry.Point(p1, p2) ,new OpenLayers.Geometry.Point(p3, p4));
}


//画点
function drawPoint(p1,p2,_style){
	var point = new OpenLayers.Geometry.Point(p1, p2);
    var pointFeature = new OpenLayers.Feature.Vector(point,null,_style);
    vector.addFeatures([pointFeature]);
}

function addPointMarker(p1,p2,icon_style){
	var marker =new OpenLayers.Marker(new OpenLayers.LonLat(p1,p2),icon_style);
	 markers.addMarker(marker);
}

$(function(){
	createShowCellsDlg = new Dialog('showCells',{
		top:"6%",
		title : "小区列表",
		width:880,
		height:500,
		buttons:{ok:{value:'关闭',iconCls:'icon-cancel',click:function(){createShowCellsDlg.hide()}}}
	});
});
function showCell(){
	createShowCellsDlg.show();
}


$(function(){
	createShowListDlg = new Dialog('showList',{
		top:"6%",
		title : "数据列表",
		width:880,
		height:500,
		buttons:{ok:{value:'关闭',iconCls:'icon-cancel',click:function(){createShowListDlg.hide()}}}
	});
});
function showList(tableName){
	var title;
	if(tableName=='D11WCOVER'){
		title="弱覆盖";
	}else if(tableName=='D11OVER_CELL'){
		title="过覆盖";
	}else if(tableName=='D11LCOVER_CELL'){
		title="覆盖不合理";
	}else if(tableName=='D11LEAK_CELL'){
		title="室分信号外泄";
	}else if(tableName=='D11POLLUTE_POINT'){
		title="导频污染";
	}
//	$("#"+tableName).attr("src","_appContext+'/views/dnpi/gis/tdCell.do?action=queryByTableName&tableName='+tableName");
//	document.iframe1.location.href=_appContext+"/views/dnpi/gis/tdCell.do?action=queryByTableName&tableName=D11WCOVER";
	document.getElementById(tableName).src =_appContext+"/views/dnpi/gis/tdCell.do?action=queryByTableName&tableName="+tableName;

	createShowListDlg.setTitle(title+"数据列表");
	createShowListDlg.show();
	$("div").siblings(".down").hide();
	$("div."+tableName).siblings().hide();
	$("."+tableName).show();
	
}




