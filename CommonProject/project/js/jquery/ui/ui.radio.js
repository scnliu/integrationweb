/*
 * jQuery UI Checkbox 0.1
 *
 * Copyright (c) 2009 Jeremy Lea <reg@openpave.org>
 * Dual licensed under the MIT and GPL licenses.
 *
 * http://docs.jquery.com/Licensing
 *
 * Based loosely on plugin by alexander.farkas.
 * http://www.protofunc.com/scripts/jquery/checkbox-radiobutton/
 */

(function($){
var label='';
$.widget("ui.radio", {
	test:function(){
		return "test button";
	},
	_create: function() {
		if($(this).hasClass('jqTransformHidden')) {return;}
		var $input = this.element;
		var inputSelf = this;
		oLabel = jqTransformGetLabel($input);
		oLabel && oLabel.click(function(){aLink.trigger('click');});
		var aLink = $('<a href="#" class="jqTransformRadio" rel="'+ this.name +'"></a>');
		$input.addClass('jqTransformHidden').wrap('<span class="jqTransformRadioWrapper"></span>').parent().prepend(aLink);
		var $this = this;
		$input.change(function(){
			$input.attr('checked') && aLink.addClass('jqTransformChecked_radio') || aLink.removeClass('jqTransformChecked_radio');
			return true;
		});
		// Click Handler
		aLink.click(function(){
			if($input.attr('disabled')){return false;}
			if($input.attr('checked'))return false;
			$this.check(!$input.attr('checked'));
			// uncheck all others of same name input radio elements
			$('input[name="'+$input.attr('name')+'"]',inputSelf.form).not($input).each(function(){
				if($(this).attr('type')=='radio') $(this).attr('checked',false).parent().children().first().removeClass('jqTransformChecked_radio');
			});
			
			return false;					
		});
		// set the default state
		$input.attr('checked')&& aLink.addClass('jqTransformChecked_radio');
	},
	check:function(checked){
		this.element.attr('checked',checked);
		this._setOption("checked", checked);
		this.element.trigger("change"); 
	},
	isCheck:function(){
		return this.element.attr('checked');
	},
	enable: function(){
		var $this = this;
		var $input = this.element;
		this.element.next('a').removeClass("btn-disabled");
		$input.parent().click(function(){
			if($input.attr('disabled')){return false;}
			$this.check(!$input.attr('checked'));
			// uncheck all others of same name input radio elements
			$('input[name="'+$input.attr('name')+'"]',inputSelf.form).not($input).each(function(){
				if($(this).attr('type')=='radio') $(this).attr('checked',false).parent().children().first().removeClass('jqTransformChecked_radio');
			});
		});
		return this._setOption( "disabled", false );
	},
	disable: function() {
		this.element.next('a').addClass("btn-disabled");
		this.element.next('a').unbind("click");
		return this._setOption( "disabled", true );
	
	},
	toggle: function() {
		this._setData("checked", !this._getData("checked"));
	}
});
})(jQuery);
