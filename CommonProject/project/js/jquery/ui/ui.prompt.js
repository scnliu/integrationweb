/*
 * jQuery Impromptu
 * By: Trent Richardson [http://trentrichardson.com]
 * Version 3.1
 * Last Modified: 3/30/2010
 * 
 * Copyright 2010 Trent Richardson
 * Dual licensed under the MIT and GPL licenses.
 * http://trentrichardson.com/Impromptu/GPL-LICENSE.txt
 * http://trentrichardson.com/Impromptu/MIT-LICENSE.txt
 * 
 */
 
(function($) {
	$.prompt = function(message, options) {
		options = $.extend({},$.prompt.defaults,options);
		$.prompt.currentPrefix = options.prefix;
		var ie6		= ($.browser.msie && $.browser.version < 7);
		var $body	= $(document.body);
		var $window	= $(window);
		
		options.classes = $.trim(options.classes);
		if(options.classes != '')
			options.classes = ' '+ options.classes;
			
		//build the box and fade
		var msgbox = '<div tabIndex=1000 class="'+ options.prefix +'box'+ options.classes +'">';
		if(options.useiframe && (($('object, applet').length > 0) || ie6)) {
			msgbox += '<iframe src="javascript:false;" style="display:block;position:absolute;z-index:-1;" class="'+ options.prefix +'fade"></iframe>';
		} else {
			msgbox +='<div class="'+ options.prefix +'fade"></div>';
		}
		var title=options.title?options.title:"";
		var width=options.width;
		var height=options.height;
		if(typeof width!="string"){
			width=width+'px';
		}
		msgbox += '<div  class="'+ options.prefix +'"><div class="'+ options.prefix +'container"  style="width:'+width+';height:'+height+';">'+
					'<div class="title"><span class="title_c">'+title+'</span><a class="';
		msgbox += options.prefix +'close">&nbsp;&nbsp;</a></div><div class="'+ options.prefix +'states"></div>';
		msgbox += '</div></div></div>';

		var $jqib	= $(msgbox).appendTo($body);
		var $jqi	= $jqib.children('.'+ options.prefix);
		var $jqif	= $jqib.children('.'+ options.prefix +'fade');
		//build the states
		var states = "";
		states += '<div class="'+ options.prefix + '_state"><div class="content"><div id="_message" class="'+ options.prefix +'message"></div></div><div class="'+ options.prefix +'buttons">';
		states += '</div></div>';
		//insert the states...
		$jqi.find('.'+ options.prefix +'states').html(states).children('.'+ options.prefix +'_state:first').css('display','block');
		if(options.href){
			var h=height-60;
			$jqi.find('.'+options.prefix +'message')
			.append('<iframe src="'+options.href+'" id="_${id}_iframe"  frameborder="no" height="'+h+'" width="100%" noresize marginwidth="0"'+
					'marginheight="0" framespacing="0"></iframe>');
		}else{
			$jqi.find('.'+options.prefix +'message').append(message);
		}
		if(options.cls){
			$jqi.find('.'+options.prefix +'message').removeClass();
			$jqi.find('#_message').addClass(options.cls);
		}

		if(options.buttons.ok){
			 var v=options.buttons.ok;
			  var id=v.id;
			  if(!id)id=getId('dlgButtonOk');
			  $btnPane=$jqi.find('.'+options.prefix +'buttons');
			  $btnPane.append('<button style="display:none;" tabIndex="21" class="dlg_btn_ok" iconCls="'+(v.iconCls?v.iconCls:"")+'" name="' +(v.name?v.name:'') + '" id="' +id + '" value="' + v.value + '">' + v.value + '</button>');
			  $('#'+id).bind('click',function(){removePrompt();}).click(v.click).button();
			  if($.browser.msie&&$.browser.version>=9.0){
			//	  $('#'+id).button('widget').css('float','right');
			  }else{
			//	 $('#'+id).button('widget').css('float','');
			  }
		}
		if(options.buttons.cancel){
			var v=options.buttons.cancel;
			var id=v.id;
			if(!id)id=getId('dlgButtonCancel');
			  $btnPane=$jqi.find('.'+options.prefix +'buttons');
			  $btnPane.append('<button class="dlg_btn_cancel" index="21" iconCls="'+(v.iconCls?v.iconCls:"")+'" name="' +(v.name?v.name:'') + '" id="' +id + '" value="' + v.value + '">' + v.value + '</button>');
			 $('#'+id).bind('click',function(){removePrompt();}).click(v.click).button();
			//   $('#'+id).button('widget').css('float','right');
		}
		

		$jqi.find('.' + options.prefix + 'buttons:empty').css('display', 'none');

		// Events
		var $state = $jqi.find('.' + options.prefix + '_state');


		var ie6scroll = function(){
			$jqib.css({ top: $window.scrollTop() });
		};

		var fadeClicked = function(){
			if(options.persistent){
				var i = 0;
				$jqib.addClass(options.prefix +'warning');
				var w=$jqib.find('.'+options.prefix+'container').width();
				$jqib.find('.'+options.prefix).width(w);
				var intervalid = setInterval(function(){
					$jqib.toggleClass(options.prefix +'warning');
					if(i++ > 1){
						clearInterval(intervalid);
						$jqib.removeClass(options.prefix +'warning');
					}
				}, 100);
			}
			else {
				removePrompt();
			}
		};
		
		var keyPressEventHandler = function(e){
			var key = (window.event) ? event.keyCode : e.keyCode; // MSIE or Firefox?
			//escape key closes
			if(key==27) {
				//fadeClicked();	
				var btn=$jqi.find('.'+options.prefix +'buttons').find(".dlg_btn_cancel");
				btn.click();
				return false;
			}
			if(key==13){
				var btn=$jqi.find('.'+options.prefix +'buttons').find(".dlg_btn_ok");
				btn.click();
				return false;
			}
			//constrain tabs
			if (key == 9){
				var $inputels = $(':input:enabled:visible',$jqib);
				var fwd = !e.shiftKey && e.target == $inputels[$inputels.length-1];
				var back = e.shiftKey && e.target == $inputels[0];
				if (fwd || back) {
				setTimeout(function(){ 
					if (!$inputels)
						return;
					var el = $inputels[back===true ? $inputels.length-1 : 0];

					if (el)
						el.focus();						
				},10);
				return false;
				}
			}
		};
		
		var positionPrompt = function(){
			$jqib.css({
				position: (ie6) ? "absolute" : "fixed",
				height: $window.height(),
				width: "100%",
				top: (ie6)? $window.scrollTop() : 0,
				left: 0,
				right: 0,
				bottom: 0
			});
			$jqif.css({
				position: "absolute",
				height: $window.height(),
				width: "100%",
				top: 0,
				left: 0,
				right: 0,
				bottom: 0
			});
			var bwidth=$(window).width();
			if(bwidth<10)bwidth=400;
			var l=(bwidth-options.width)/2;
			var top=$(window).height()*0.15;
			$jqi.css({
				position: "absolute",
				top: top,
				left: l
			});
		};

		var stylePrompt = function(){
			$jqif.css({
				zIndex: options.zIndex,
				display: "none",
				opacity: options.opacity
			});
			$jqi.css({
				zIndex: options.zIndex+1,
				display: "none"
			});
			$jqib.css({
				zIndex: options.zIndex
			});
		};

		var removePrompt = function(callCallback, clicked, msg, formvals){
			$jqi.remove();
			//$jqi.hide('fast');
			//ie6, remove the scroll event
			if(ie6) {
				$body.unbind('scroll',ie6scroll);
			}
			$window.unbind('resize',positionPrompt);
			$jqif.fadeOut(options.overlayspeed,function(){
				$jqif.unbind('click',fadeClicked);
				$jqif.remove();
				//$jqib.css('display','none');
				$jqib.unbind('keypress',keyPressEventHandler);
				$jqib.remove();
				//$jqib.css('display','none');
			});
		};

		positionPrompt();
		stylePrompt();
		
		//ie6, add a scroll event to fix position:fixed
		if(ie6) {
			$window.scroll(ie6scroll);
		}
		$jqif.click(fadeClicked);
		$window.resize(positionPrompt);
		$jqib.bind("keydown keypress",keyPressEventHandler);
		$jqi.find('.'+ options.prefix +'close').click(removePrompt);

		//Show it
		$jqif.fadeIn(options.overlayspeed);
		$jqi[options.show](options.promptspeed,options.loaded);
		$jqi.find('.'+ options.prefix +'states .'+ options.prefix +'_state:first .'+ options.prefix +'defaultbutton').focus();
		if(options.timeout > 0)
			setTimeout($.prompt.close,options.timeout);
		$jqi.focus();
		return $jqib;
	};
	
	$.prompt.defaults = {
		prefix:'brownJqi',
		classes: '',
		buttons: {
			Ok: {value:"确定",iconCls:'icon-ok'}
		},
	 	loaded: function(){

	 	},
	  	submit: function(){
	  		return true;
		},
	 	callback: function(){

	 	},
		opacity: 0.6,
	 	zIndex: 2000,
	 	width:360,
	 	height:'auto',
	 	href:null,
	  	overlayspeed: 200,
	   	promptspeed: 150,
   		show: 'fadeIn',
	   	focus: 0,
	   	useiframe: true,
	 	top: "15%",
	  	persistent: true,
	  	timeout: 0,
	  	title:"温馨提示",
	  	state: {
			html: '',
		 	buttons: {
		 		Ok: true
		 	},
		  	focus: 0,
		   	submit: function(){
		   		return true;
		   }
	  	}
	};
	
	$.prompt.currentPrefix = $.prompt.defaults.prefix;
    
	$.prompt.setDefaults = function(o) {
		$.prompt.defaults = $.extend({}, $.prompt.defaults, o);
	};
	
	$.prompt.setStateDefaults = function(o) {
		$.prompt.defaults.state = $.extend({}, $.prompt.defaults.state, o);
	};
	
	$.prompt.getStateContent = function(state) {
		return $('#'+ $.prompt.currentPrefix +'_state_'+ state);
	};
	
	$.prompt.getCurrentState = function() {
		return $('.'+ $.prompt.currentPrefix +'_state:visible');
	};
	
	$.prompt.getCurrentStateName = function() {
		var stateid = $.prompt.getCurrentState().attr('id');
		
		return stateid.replace($.prompt.currentPrefix +'_state_','');
	};
	
	$.prompt.goToState = function(state, callback) {
		$('.'+ $.prompt.currentPrefix +'_state').slideUp('slow');
		$('#'+ $.prompt.currentPrefix +'_state_'+ state).slideDown('slow',function(){
			$(this).find('.'+ $.prompt.currentPrefix +'defaultbutton').focus();
			if (typeof callback == 'function')
				callback();
		});
	};
	
	$.prompt.nextState = function(callback) {
		var $next = $('.'+ $.prompt.currentPrefix +'_state:visible').next();

		$('.'+ $.prompt.currentPrefix +'_state').slideUp('slow');
		
		$next.slideDown('slow',function(){
			$next.find('.'+ $.prompt.currentPrefix +'defaultbutton').focus();
			if (typeof callback == 'function')
				callback();
		});
	};
	
	$.prompt.prevState = function(callback) {
		var $next = $('.'+ $.prompt.currentPrefix +'_state:visible').prev();

		$('.'+ $.prompt.currentPrefix +'_state').slideUp('slow');
		
		$next.slideDown('slow',function(){
			$next.find('.'+ $.prompt.currentPrefix +'defaultbutton').focus();
			if (typeof callback == 'function')
				callback();
		});
	};
	
	$.prompt.close = function() {
        		$(this).remove();
	};
	$.prompt.show= function() {
		$jqib.fadeIn(options.overlayspeed);
	};
	
	$.fn.prompt = function(options){
		if(options == undefined)options = {};
		if(options.withDataAndEvents == undefined)
			options.withDataAndEvents = false;
		$.prompt($(this),options);
	};
  	var date=new Date();
	var getId=function(idPrex){
		return idPrex+date.getTime();
	}
})(jQuery);

function say(msg){
	return jQuery.prompt(msg,{
				title:"确认",
				prefix:'brownJqi',
				persistent:true,
				persistent:false,
				zIndex: 200000,
				buttons:{ok:{value:'确定',
							iconCls:'icon-ok'}
						}
	}).focus();
}
function sayFunc(content,executeFunc){
	return jQuery.prompt(msg,{
				title:"确认",
				prefix:'brownJqi',
				persistent:true,//fadeclick hide
				zIndex: 200000,
				buttons:{ok:{value:'确定',
							iconCls:'icon-ok',
							click:executeFunc}
						}
	}).focus();
}
function notify(msg){
	return jQuery.prompt(msg,{
				title:"温馨提示",
				prefix:'brownJqi',
				persistent:false,//fadeclick hide
				zIndex: 200000,
				buttons:{ok:{value:'确定',
							iconCls:'icon-ok'}
						}
	}).focus();
}
function notifyFunc(content,executeFunc){
		return jQuery.prompt(content,{
				title:"温馨提示",
				prefix:'brownJqi',
				zIndex: 200000,
				persistent:true,//fadeclick hide
				buttons:{ok:{value:'确定',
							iconCls:'icon-ok',
							click:executeFunc}
						}
	}).focus();
}

function askFunc(content,executeFunc,target_,cancelFunc){
	return jQuery.prompt(content,{
						title:"温馨提示",
						prefix:'brownJqi',
						persistent:true,//fadeclick hide
						zIndex: 200000,
						buttons:{ok:{value:'确定',
									iconCls:'icon-ok',
									click:executeFunc},
								cancel:{value:'取消',
									iconCls:'icon-cancel',
									click:cancelFunc}
								}
			}).focus();
}

function error(content){
	return jQuery.prompt(content,{
						title:"温馨提示",
						prefix:'brownJqi',
						zIndex: 200000,
						persistent:true,//fadeclick hide
						buttons:{ok:{value:'确定',
									iconCls:'icon-ok'}
								}
			}).focus();
}