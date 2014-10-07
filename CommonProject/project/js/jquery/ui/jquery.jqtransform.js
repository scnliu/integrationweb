/*
 *
 * jqTransform
 * by mathieu vilaplana mvilaplana@dfc-e.com
 * Designer ghyslain armand garmand@dfc-e.com
 *
 *
 * Version 1.0 25.09.08
 * Version 1.1 06.08.09
 * Add event click on Checkbox and Radio
 * Auto calculate the size of a select element
 * Can now, disabled the elements
 * Correct bug in ff if click on select (overflow=hidden)
 * No need any more preloading !!
 * 
 ******************************************** */
(function($){
	var defaultOptions = {preloadImg:true,count:0,initial:false};
	var jqTransformImgPreloaded = false;
	var selectCount=0;
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
			
	/* Add a new handler for the reset action */
	var jqReset = function(f){
		var sel;
		$('.jqTransformSelectWrapper select', f).each(function(){sel = (this.selectedIndex<0) ? 0 : this.selectedIndex; $('ul', $(this).parent()).each(function(){$('a:eq('+ sel +')', this).click();});});
		$('a.jqTransformCheckbox, a.jqTransformRadio', f).removeClass('jqTransformChecked');
		$('input:checkbox, input:radio', f).each(function(){if(this.checked){$('a', $(this).parent()).addClass('jqTransformChecked');}});
	};

	/***************************
	  Buttons
	 ***************************/
	$.fn.jqButton = function(){
			var newBtn = $('<button id="'+ this.id +'" name="'+ this.name +'" type="'+ this.type +'" class="'+ this.className +' jqTransformButton"><span><span>'+ $(this).attr('value') +'</span></span>')
				.hover(function(){newBtn.addClass('jqTransformButton_hover');},function(){newBtn.removeClass('jqTransformButton_hover')})
				.mousedown(function(){newBtn.addClass('jqTransformButton_click')})
				.mouseup(function(){newBtn.removeClass('jqTransformButton_click')})
			;
			$(this).replaceWith(newBtn);
	};
	/***************************
	  Text Fields 
	 ***************************/
	$.fn.jqInputText = function(options){
			var opt = $.extend({},defaultOptions,options);
			var $input = $(this);
			if($input.hasClass('input_normal') || !$input.is('input')) {return;}
			$input.addClass("input_normal");
			$input.attr('tabIndex',20);
			$input
				.focus(function(){$(this).addClass("input_focus");})
				.blur(function(){$(this).removeClass("input_focus");})
				.hover(function(){$(this).addClass("input_hover");},function(){$(this).removeClass("input_hover");})
			;
			return $input;
	};
	
	
	
	/***************************
	  TextArea 
	 ***************************/	
	$.fn.jqTextarea = function(options){
		 var opt = $.extend({},defaultOptions,options);
			var $input = $(this);
			if($input.hasClass('area_normal')) {return;}
			$input.addClass("area_normal");
			$input.attr('tabIndex',20);
			$input
				.focus(function(){$(this).addClass("area_focus");})
				.blur(function(){$(this).removeClass("area_focus");})
				.hover(function(){$(this).addClass("area_hover");},function(){$(this).removeClass("area_hover");})
			;
			return $input;
	};
	
	/***************************
	  Select 
	 ***************************/	
	var hideSelect=function(sel){
		var oSelect = $(sel).parents(".jqTransformSelectWrapper:first").find("select").get(0);
		//do not hide if click on the label object associated to the select
		$(sel).hide();
	};
	$.fn.jqSelect = function(options){
		 var opt = $.extend({},defaultOptions,options);
			selectCount++;
		    var index=selectCount;
			var $select = $(this);
			if($select.hasClass('jqTransformHidden')){return;}
			if($select.attr('multiple')) {return;}
			
			var oLabel  =  jqTransformGetLabel($select);
			/* First thing we do is Wrap it */
			var $wrapper = $select
				.wrap('<div class="jqTransformSelectWrapper"></div>')
				.parent()
				.css({zIndex: 1110-index})
			;
			$wrapper.attr('tabIndex',20);
			$select.css('display','none');
			/* Now add the html for the select */
			$wrapper.prepend('<div><span></span><a href="#" class="jqTransformSelectOpen"></a></div><ul></ul>');
			var $ul = $('ul', $wrapper).css('width',$select.width()).hide();
			/* Now we add the options */
			var h=1;
			$('option', this).each(function(i){
				var txt=$(this).html();
				if(txt=='')txt="&nbsp";
				var oLi = $('<li><span index="'+ i +'">'+ txt +'</span></li>');
				if($.browser.msie&&$.browser.version<8){
					oLi.css('height',24);
				}
				$ul.append(oLi);
				if($select[0].selectedIndex==i){
					$('span:eq(0)', $wrapper).html($(this).html());
					$('span:eq(0)', $wrapper).css('width',$wrapper.width()-10);
				}
				h=i;
				$(this).html('');
			});
			$ul.find("li span").hover(
					  function () {
					    $(this).addClass("over");
					  },
					  function () {
					    $(this).removeClass("over");
					  }
			);
			/* Add click handler to the a */
			$ul.find('span').click(function(){
					$('span.selected', $wrapper).removeClass('selected');
					$(this).addClass('selected');	
					/* Fire the onchange event */
					$ul.hide();
					if ($select[0].selectedIndex != $(this).attr('index') &&($select[0].onchange||$select[0].onChange)) {
							$select[0].selectedIndex = $(this).attr('index'); 
							$select.trigger('change');
						}
					$('span:eq(0)', $wrapper).html($(this).html());
					$select[0].selectedIndex = $(this).attr('index');
					return false;
			});
			$('span:eq('+ this.selectedIndex +')', $ul).addClass('.selected');
			/* Set the default */
			oLabel && oLabel.click(function(){$("a.jqTransformSelectOpen",$wrapper).trigger('click');});
			this.oLabel = oLabel;
			/* Apply the click handler to the Open */
			var oLinkOpen = $('a.jqTransformSelectOpen', $wrapper)
					.click(function(){
						$ul.css('width',$wrapper.width()-2);
					//Check if box is already open to still allow toggle, but close all other selects
					if( $ul.css('display') == 'none' ) {hideSelect($select);} 
					if($select.attr('disabled')){return false;}
					$ul.show();
					$wrapper.focus();
					return false;
				})
			;
			var oSpan = $('span:first',$wrapper);
			oSpan.css({width:$wrapper.width()-10});
			$ul.css({display:'block',visibility:'hidden'});
			var iSelectHeight = ($('li',$ul).length)*24;//+1 else bug ff
			(iSelectHeight < 240) && $ul.css({height:iSelectHeight,'overflow':'auto'});//hidden else bug with ff
			$ul.css({display:'none',visibility:'visible'});
			oSpan.click(function(){$("a.jqTransformSelectOpen",$wrapper).trigger('click');return false;});
			$wrapper.blur(function(event){
					$ul.slideUp('fast');
			});
			this.selectValue=function(value){
				var $sel=$(this);
				$sel.val(value);
				var swrapper=$sel.closest('.jqTransformSelectWrapper');
				var $ul = $('ul', swrapper);
				/* Now we add the options */
				var h=1;
				$('option', this).each(function(i){
					if($sel[0].selectedIndex==i){
						$('span:eq(0)', $wrapper).html($('li:eq('+i+') span',$ul).html());
					}
				});
			};
			return this;
		
	};
	/**
	 * ajax加载的数据添加到下拉框
	 */
	$.fn.addOptions=function($ul,$wrapper){
		var $select=$(this);
		var $wrapper=$select.parent();
		$('li',$ul).remove();
		$('option', this).each(function(i){
			var txt=$(this).html();
			if(txt=='')txt="  ";
			var oLi = $('<li><span index="'+ i +'">'+ txt +'</span></li>');
			if($.browser.msie&&$.browser.version<8){
				oLi.css('height',24);
			}
			$ul.append(oLi);
			if($select[0].selectedIndex==i){
				$('span:eq(0)', $wrapper).html($(this).html());
				$('span:eq(0)', $wrapper).css('width',$wrapper.width()-10);
			}
			$(this).html('');
		});
		$ul.find("li span").hover(
				function () {
					$(this).addClass("over");
				},
				function () {
					$(this).removeClass("over");
				}
		);
		/* Add click handler to the a */
		$ul.find('span').click(function(){
			$('span.selected', $ul).removeClass('selected');
			$(this).addClass('selected');	
			/* Fire the onchange event */
			$ul.hide();
			if ($select[0].selectedIndex != $(this).attr('index') &&($select[0].onchange||$select[0].onChange)) {
				$select[0].selectedIndex = $(this).attr('index'); 
				$select.trigger('change');
			}
			$('input:eq(0)', $wrapper).val($(this).html());
			$select[0].selectedIndex = $(this).attr('index');
			return false;
		});
		$('span:eq('+ this.selectedIndex +')', $ul).addClass('.selected');
		/* Set the default */
	
		/* Apply the click handler to the Open */
		var oLinkOpen = $('a.jqTransformSelectOpen', $wrapper)
		.click(function(){
			$ul.css('width',$wrapper.width()-2);
			//Check if box is already open to still allow toggle, but close all other selects
			if( $ul.css('display') == 'none' ) {hideSelect($select);} 
			if($select.attr('disabled')){return false;}
			$ul.show();
			$wrapper.focus();
			return false;
		})
		;
		var iSelectHeight = ($('li',$ul).length)*24;//+1 else bug ff
		(iSelectHeight < 240) && $ul.css({height:iSelectHeight,'overflow':'auto'});//hidden else bug with ff
		
		$ul.show();
	};
	/**
	 * ajaxSelect
	 * keyName: 键的属性名
	 * labelName:显示值的属性名
	 * url:使用Ajax读取填充下拉的数据
	 * 例:$('#selectId').ajaxSelect({url:'/selectCell.do?action=queryTypes',keyName:'id',labelName:'name'});
	 */
	$.fn.ajaxSelect= function(options){
		var opts= $.extend({},defaultOptions,options);
		selectCount++;
		var index=selectCount;
		var $select = $(this);
		if($select.hasClass('jqTransformHidden')){return;}
		if($select.attr('multiple')) {return;}
		var oLabel  =  jqTransformGetLabel($select);
		/* First thing we do is Wrap it */
		var $wrapper = $select
		.wrap('<div class="jqTransformSelectWrapper"></div>')
		.parent()
		.css({zIndex: 1110-index})
		;
		$select.css('display','none');
		/* Now add the html for the select */
		$wrapper.prepend('<div><input type="text" style="margin:0px;"/><a href="#" class="jqTransformSelectOpen"></a></div><ul></ul>');
		var $ul = $('ul', $wrapper).css('width',$select.width()).hide();
		/*addOptions*/
	    $('input:eq(0)',$wrapper).hover(
		    	function(){$wrapper.addClass("jqTransformInputWrapper_hover");},
		    	function(){$wrapper.removeClass("jqTransformInputWrapper_hover");}
	    	).keyup(function(evt){
		    	var val=$(this).val();
		    	var params={};
		    	var _keyName=opts.keyName;
		    	var _labelName=opts.labelName;
		    	params[_labelName]=val;
		    	$select.find('option').remove();
		    	$.post(opts.url,params,function(listdata){
			    	$.each(listdata,function(i,optData){
						var _label=optData[_labelName];
						var _key=optData[_keyName];
						if(!_label)_label="&nbsp";
						$('<option value="'+_key+'">'+_label+'</option>').appendTo($select);
					});
			    	$select.addOptions($ul,$wrapper);
		    	});
	      });
	    $select.addOptions($ul,$wrapper);
		oLabel && oLabel.click(function(){$("a.jqTransformSelectOpen",$wrapper).trigger('click');});
		this.oLabel = oLabel;
		var oSpan = $('input:first',$wrapper);
		oSpan.css({width:$wrapper.width()-10});
		$ul.css({display:'block',visibility:'hidden'});
		var iSelectHeight = ($('li',$ul).length)*24;//+1 else bug ff
		(iSelectHeight < 240) && $ul.css({height:iSelectHeight,'overflow':'auto'});//hidden else bug with ff
		$ul.css({display:'none',visibility:'visible'});
		$wrapper.click(function(){$("a.jqTransformSelectOpen",$wrapper).trigger('click');return false;});
		$wrapper.blur(function(event){
			$ul.slideUp('fast');
		});
		
	};
	
	////////////////////
	$.fn.jqRadios = function(options){
		var opt = $.extend({},defaultOptions,options);
		$('input:radio', this).jqRadio(opt);
	};
	$.fn.jqCheckBoxs = function(options){
		var opt = $.extend({},defaultOptions,options);
		$('input:checkbox', this).jqCheckBox(opt);
	};
	$.fn.jqform = function(options){
		var opt = $.extend({},defaultOptions,options);
		
		/* each form */
		 return this.each(function(){
			var selfForm = $(this);
			if(selfForm.hasClass('jqtransformdone')) {return;}
			selfForm.addClass('jqtransformdone');
			
			$('input:submit, input:reset, input[type="button"]', this).jqInputButton();			
			$('input:text, input:password', this).jqInputText();			
			$('input:checkbox', this).jqCheckBox();
			$('input:radio', this).jqRadio();
			$('textarea', this).jqTextarea();
			
			if( $('select', this).jqSelect().length > 0 ){jqTransformAddDocumentListener();}
			selfForm.bind('reset',function(){var action = function(){jqReset(this);}; window.setTimeout(action, 10);});
			
			//preloading dont needed anymore since normal, focus and hover image are the same one
			/*if(opt.preloadImg && !jqTransformImgPreloaded){
				jqTransformImgPreloaded = true;
				var oInputText = $('input:text:first', selfForm);
				if(oInputText.length > 0){
					//pour ie on eleve les ""
					var strWrapperImgUrl = oInputText.get(0).wrapper.css('background-image');
					jqTransformPreloadHoverFocusImg(strWrapperImgUrl);					
					var strInnerImgUrl = $('div.jqTransformInputInner',$(oInputText.get(0).wrapper)).css('background-image');
					jqTransformPreloadHoverFocusImg(strInnerImgUrl);
				}
				
				var oTextarea = $('textarea',selfForm);
				if(oTextarea.length > 0){
					var oTable = oTextarea.get(0).oTable;
					$('td',oTable).each(function(){
						var strImgBack = $(this).css('background-image');
						jqTransformPreloadHoverFocusImg(strImgBack);
					});
				}
			}*/
			
			
		}); /* End Form each */
				
	};/* End the Plugin */

})(jQuery);
	