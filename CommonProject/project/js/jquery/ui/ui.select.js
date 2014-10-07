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
$.widget("ui.select", {
	options: {
		disabled: false,
		text: true,
		label: null,
		initial:false,
		checked:false,
		icons: {
			primary: null,
			secondary: null
		}
	},
	_init: function() {
			var ele = this.element;
			if (ele.hasClass('jqTransformHidden')) {
				return;
			}
			var $input = ele;
			var $this = this;
			$this.check(this.options.checked);
			// set the click on the label
			var oLabel = jqTransformGetLabel($input);
			oLabel && oLabel.click(function() {
				aLink.trigger('click');
			});
			var aLink = $('<a href="#" class="jqTransformCheckbox"></a>');
			// wrap and add the link
			$input.addClass('jqTransformHidden').wrap(
					'<span class="jqTransformCheckboxWrapper"></span>')
					.parent().prepend(aLink);
			// on change, change the class of the link
			$input.bind('change', function() {
				$input.attr('checked') && aLink.addClass('jqTransformChecked')
						|| aLink.removeClass('jqTransformChecked');
				return true;
			});
			aLink.click(function() {
				// do nothing if the original input is disabled
				if ($input.attr('disabled')) {
					return false;
				}
				$this.check(!$input.attr('checked'));
				return false;
			});
			// set the default state
			$input.attr('checked') && aLink.addClass('jqTransformChecked');	
			
	},
	check:function(checked){
			this.element.attr('checked',checked);
			this._setOption("checked", checked);
			this.element.trigger("change");	
	},
	enable: function() {
		this.element.next('a').removeClass("btn-disabled");
		this._initEvent();
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
