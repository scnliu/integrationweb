var style_enCell;//�����������
var style_line3;//����
var style_line4;//����

//�����������
style_enCell = OpenLayers.Util.extend({},
		OpenLayers.Feature.Vector.style['default']);
style_enCell.graphicWidth = 1;
style_enCell.fillColor = "#ffa500";
style_enCell.fillOpacity = 0.9;

style_line3 = OpenLayers.Util.extend({},
		OpenLayers.Feature.Vector.style['default']);
style_line3.strokeWidth = 1;
style_line3.strokeColor = "#ff0000";
style_line3.strokeOpacity = 0.8;
//ɾ��������������ɫ

style_line4 = OpenLayers.Util.extend({},
		OpenLayers.Feature.Vector.style['default']);
style_line4.strokeWidth = 1;
style_line4.strokeColor = "#000000";
style_line4.strokeOpacity = 0.8;
style_line4.strokeDashstyle = "dash";

//�������ӻ�ɾ��������
function queryOptCellr(cellid,kind) {
	//queryCellr(cellid);//�Ȼ�����
	jQuery.ajax({
		type: "post",      //�ύ��ʽ
		url : _appContext + "/views/npi/ncs/analysis/NcsOptCtrl.do?action=optCellr",
		data :{'cellid' : cellid,'kind' : kind}, 
		dateType: "json",  
		cache:false,
		success:function(data){
			if(data){
				initVectorHoLayer();
				vector.removeAllFeatures();
				var mcell=data.mcell;
				if(!mcell)return;
				var cellPoint=new OpenLayers.LonLat(mcell.points[0], mcell.points[1]);
				map.setCenter(cellPoint);
				var xy=map.getPixelFromLonLat(cellPoint);
				style_cell.fillColor="#"+mcell.fillColorStr;
				style_cell.strokeColor="#"+mcell.strokeColorStr;
				
				var cellrs=data.cellr;
				for(var k=0;k<cellrs.length;k++){
					style_cellr.fillColor="#"+cellrs[k].fillColorStr;
					style_cellr.strokeColor="#"+cellrs[k].strokeColorStr;
					drawCellShape(cellrs[k].points,style_cellr);//����С��
				}
				drawCellShape(mcell.points,style_cell);//����С��
				var optNcells = data.optNcellList;
				for(var k=0;k<optNcells.length;k++){
					style_enCell.fillColor="#"+optNcells[k].fillColorStr;
					style_enCell.strokeColor="#"+optNcells[k].strokeColorStr;
					
					drawLinkLine(new OpenLayers.Geometry.Point(mcell.lon, mcell.lat) ,new OpenLayers.Geometry.Point(optNcells[k].lon, optNcells[k].lat),kind);//������
					drawCellShape(optNcells[k].points,style_enCell);//�������������
					if(kind==0)drawHo24(mcell,optNcells[k]);//���л���
					
				}
				
				//showCellInfo(mcell,xy,cellPoint);
			}
			
		},
		error: function(request, error, status){ 
		}
	});

}

/*
 *�����ߣ�Ĭ��Ϊ�ղ��û���
 **/
function drawLinkLine(p1,p2,type){
	var styleLinkLine;
	if(type=='0'){
		styleLinkLine = style_line4;//ɾ������������
	}else if(type=='1'){
		styleLinkLine = style_line3;//©����С��������
	}
	var line=new OpenLayers.Geometry.LinearRing([p1,p2]);
	var lineFeature = new OpenLayers.Feature.Vector(line, null, styleLinkLine);
	vector.addFeatures([lineFeature]);
}
/*
 *������������л���
 **/
var vectorHoLayer;
function initVectorHoLayer(){
	var minRes=map.getResolutionForZoom(15);
	if(!vectorHoLayer){
		var sketchSymbolizers={
		        	 strokeColor: "#00FF00",
		        	    strokeWidth: 1,
		        	    hoverFillColor: "#FFFF00",
		        	    fillColor: "#FF5500",
		        	    fillOpacity: 0.7,
		        	    pointRadius:1,
		        	    pointerEvents: "visiblePainted",
		        	    label : "${featureLabel}",
		        		fontColor: "#ff0000",
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
					fontColor: "#ff0000",
					fontSize: "12px",
					fontFamily: "Courier New, monospace",
					labelAlign: "lc",
					labelXOffset: "6",
					 fontOpacity:0.8,
					 fontBackGroundColor:'${fontBackColor}'
					 
		};
		var ho_style = new OpenLayers.Style('default');
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
			ho_style.addRules([
		                dchnoRule,
		                dchnoRule2
		    ]);
		}
		vectorHoLayer = new OpenLayers.Layer.Vector("�л���", {
		    styleMap: new OpenLayers.StyleMap({'default':ho_style}),
		    renderers: ["Canvas", "SVG","VML"]
		});
		map.addLayer(vectorHoLayer);
		map.zoomTo(15);
	}
	vectorHoLayer.removeAllFeatures();
}
function drawHo24(p1,delNcell){
	//var hoText = new OpenLayers.Geometry.Point((p1.lon+delNcell.lon)/2,(p1.lat+delNcell.lat)/2);
	var hoFeature = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Point(delNcell.lon,delNcell.lat),{'fontBackColor':'#ffffaa','featureLabel':delNcell.ho24,'labelOffsetX':10});
	vectorHoLayer.addFeatures([hoFeature]);
}