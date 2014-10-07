var popup2;
var dashLineStyle={
			strokeWidth:2,
			strokeColor:"#ee0011",
			strokeOpacity:0.8,
			strokeDashstyle:"dash"};
specifyLayers=['cell'];
function initMapAfter(){
	     showLayer();
};
function showAreaInfo(data,xy,lonlat){
		 	if(!popup2){
		 		popup2=$('#areaInfo').anchored({title:'区域信息',parentId:'map',show:false});
		 		map.events.register("move", map, function(e) { 
					var lonlat=popup2.data('lonlat');
					if(lonlat){
						var xy=map.getPixelFromLonLat(lonlat);
						popup2.move(xy.x,xy.y);
					}
		        });
		 	}
			$('#areaId').html(data.CLUSTERID);
			$('#isHs').html(isType(data.IS_HIGH_REPEATER));
			$('#isHfn').html(isType(data.IS_HIGH_DCHNO));
			$('#isHHs').html(isType(data.IS_HIGH_SITE));
			popup2.data('lonlat',lonlat);
			popup2.showInfo(xy.x,xy.y);
			
		}
function isType(t){
			return t>0?"是":"否";
		 }
function showLayer(){
		 jQuery.ajax({ 
				type: "GET",      //提交方式
				url: _appContext+"/views/npi/sc/analysis/AreaStatCtrl.do?action=showData",  //
				dateType: "json",  
				data:{clusterid:encodeURI(areaId)}, 
				success: function(data){ 
					var points=data.points;
					if(points.length>0){
						var np=new OpenLayers.LonLat(points[0].lon,points[0].lat);
						map.setCenter(np);
						var xy=map.getPixelFromLonLat(np);
						showAreaInfo(data.area,xy,np);
						var linePoints=[];
						jQuery.each(points,function(idx,p){
										linePoints[idx]=new OpenLayers.Geometry.Point(p.lon,p.lat);
							 });
						linePoints[linePoints.length]=linePoints[0];
						var line = new OpenLayers.Geometry.LineString(linePoints);
						var lv= new OpenLayers.Feature.Vector(line, null,dashLineStyle);
						drawLayer.addFeatures([lv]);
					 }
				   },
			       error: function(request, error, status){  
					}
			});
	
		}
	