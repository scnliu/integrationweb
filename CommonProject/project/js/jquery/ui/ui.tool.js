var jqTransformPreloadHoverFocusImg = function(strImgUrl) {
		//guillemets to remove for ie
		strImgUrl = strImgUrl.replace(/^url\((.*)\)/,'$1').replace(/^\"(.*)\"$/,'$1');
		var imgHover = new Image();
		imgHover.src = strImgUrl.replace(/\.([a-zA-Z]*)$/,'-hover.$1');
		var imgFocus = new Image();
		imgFocus.src = strImgUrl.replace(/\.([a-zA-Z]*)$/,'-focus.$1');				
	};

	
	/***************************
	  Labels
	***************************/
	var jqTransformGetLabel = function(objfield){
		var selfForm = $(objfield.get(0).form);
		var oLabel = objfield.next();
		if(!oLabel.is('label')) {
			oLabel = objfield.prev();
			if(oLabel.is('label')){
				var inputname = objfield.attr('id');
				if(inputname){
					oLabel = selfForm.find('label[for="'+inputname+'"]');
				} 
			}
		}
		if(oLabel.is('label')){return oLabel.css('cursor','pointer');}
		return false;
	};
	
	/* Hide all open selects */
	var jqTransformHideSelect = function(oTarget){
		var ulVisible = $('.jqTransformSelectWrapper ul:visible');
		ulVisible.each(function(){
			var oSelect = $(this).parents(".jqTransformSelectWrapper:first").find("select").get(0);
			//do not hide if click on the label object associated to the select
			if( !(oTarget && oSelect.oLabel && oSelect.oLabel.get(0) == oTarget.get(0)) ){$(this).hide();}
		});
	};
	/* Check for an external click */
	var jqTransformCheckExternalClick = function(event) {
		if ($(event.target).parents('.jqTransformSelectWrapper').length === 0) { jqTransformHideSelect($(event.target)); }
	};

	/* Apply document listener */
	var jqTransformAddDocumentListener = function (){
		$(document).mousedown(jqTransformCheckExternalClick);
	};	
(function($) {
		$.fn.active= function() {
			$(this).trigger('setActive');
		};
})(jQuery);
jQuery.extend({
deparam:function(str){
	var obj={};
	var params=str.split('&');
	$.each(params,function(i,value){
		var p=value.split('=');
		obj[p[0]]=p[1];
	});
	return obj;
}});

var r20 = /%20/g,
rbracket = /\[\]$/;
/**
 *序列化
 *jQuery.param方法序列化{}时当作数组处理，后台绑定参数时出错
 */
var param=function(a,traditional){
	var s = [],
		add = function( key, value ) {
			value = jQuery.isFunction( value ) ? value() : value;
			s[ s.length ] = encodeURIComponent( key ) + "=" + encodeURIComponent( value );
		},buildParams=function( prefix, obj, traditional, add ) {
			if ( jQuery.isArray( obj ) ) {
				// Serialize array item.
				jQuery.each( obj, function( i, v ) {
					if ( traditional || rbracket.test( prefix ) ) {
						// Treat each array item as a scalar.
						add( prefix, v );

					} else {
						// If array item is non-scalar (array or object), encode its
						// numeric index to resolve deserialization ambiguity issues.
						// Note that rack (as of 1.0.0) can't currently deserialize
						// nested arrays properly, and attempting to do so may cause
						// a server error. Possible fixes are to modify rack's
						// deserialization algorithm or to provide an option or flag
						// to force array serialization to be shallow.
						buildParams( prefix + "[" + ( typeof v === "object" || jQuery.isArray(v) ? i : i ) + "]", v, traditional, add );
					}
				});

			} else if ( !traditional && obj != null && typeof obj === "object" ) {
				// Serialize object item.
				for ( var name in obj ) {
					buildParams( prefix + "." + name + "", obj[ name ], traditional, add );
				}

			} else {
				// Serialize scalar item.
				add( prefix, obj );
			}
		  };
	if ( traditional === undefined ) {
		traditional = jQuery.ajaxSettings.traditional;
	}
	if ( jQuery.isArray( a ) || ( a.jquery && !jQuery.isPlainObject( a ) ) ) {
		// Serialize the form elements
		jQuery.each( a, function() {
			add( this.name, this.value );
		});

	} else {
		for ( var prefix in a ) {
			buildParams( prefix, a[ prefix ], traditional, add );
		}
	}
	return s.join( "&" ).replace(r20, "+" );
};

//doubleSelect 左右移动值
function moveToRight(doubleSelect1,doubleSelect2){
		var sel2=$('#'+doubleSelect2);
	$('option',$('#'+doubleSelect1)).each(function(){
			if(this.selected){
					var opt=$(this);
					opt.appendTo(sel2);
				}
		});
	}
function moveToLeft(doubleSelect2,doubleSelect1){
		var sel1=$('#'+doubleSelect1);
	$('option',$('#'+doubleSelect2)).each(function(){
			if(this.selected){
					var opt=$(this);
					opt.appendTo(sel1);
				}
		});
}
////////////停止事件冒泡
function stopEvent(event,_allowDefault){
	var allowDefault=_allowDefault?_allowDefault:false;
	 if (!allowDefault) { 
            if (event.preventDefault) {
                event.preventDefault();
            } else {
                event.returnValue = false;
            }
        }
                
        if (event.stopPropagation) {
            event.stopPropagation();
        } else {
            event.cancelBubble = true;
        }
}