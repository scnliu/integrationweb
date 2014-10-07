/*
 *	$.fn.anchored 小框
 *		var searchDchnoTip=$("<div style='text-align:center;color:red'>必须填数字！</div>").anchored({title:'输入错误',parentId:'map',show:true});
 *			
 *	$.fn.anchored2  大框
 *		var popup=$('#cellInfo').anchored2({title:'小区信息',parentId:'map',show:false}); 
 */
(function($) {
	$.anchored = function(content,options_){
		var parentId=options_.parentId;
		var parentElement=document.body;
		if(parentId)parentElement=$('#'+parentId);
		var options=$.extend({show:true},options_);
		var id=content.prop('id');
		if(!id)id="autocreateID";
		var $pop=$("<div class='"+(options.big?"anchored2":"anchored")+"' "+(options.show?"":"style=\"display:none;\"")+"><div class='anchored-head'>" +
				"<table width='100%'><tr><td style='text-align:center;padding-left:20px;'>"+(options.title?options.title:"提示")+"</td><td style='width:30px;'><a class='close'>&nbsp;&nbsp;</a></td></tr></table></div><div  class='anchored-body' id='anchored_"+id+"'>" +
				"</div><div  class='anchored-foot'></div></div>").appendTo(parentElement);
		content.appendTo($("#anchored_"+id));
		$pop.find('a:eq(0)').click(function(){$pop.hide();});
		if(options.big){
			$pop.attr('big',true);
		}
		if(options.lonlat){
			$pop.attr('lon',options.lonlat.lon);
			$pop.attr('lat',options.lonlat.lat);
		}
		$pop.find('div.anchored-body').mousedown(function(evt){evt.stopPropagation();});
		return $pop;
	};

	$.fn.move=function(x,y){
		var $this=$(this);
		var h=$this.height();
		var w=$this.width();
		if($this.attr('big')){
			$this.css('left',x-200);
		}else{
			$this.css('left',x-96);
		}
		$this.css('top',y-h);
	};
	$.fn.showInfo=function(x,y){
		var $this=$(this);
		if(x){
			$this.move(x,y);
		}
		$this.fadeIn(800);
	};
	$.fn.setLonlat=function(lonlat){
		var $this=$(this);
		$this.attr('lon',options.lonlat.lon);
		$this.attr('lat',options.lonlat.lat);
	};
	$.fn.anchored=function(options){
		return $.anchored($(this),options);
	};
	$.fn.anchored2=function(options_){
		var options=$.extend({big:true},options_);
		return $.anchored($(this),options);
	};
})(jQuery);