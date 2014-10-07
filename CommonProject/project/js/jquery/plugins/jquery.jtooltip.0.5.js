/*
 * jQuery Tooltip widget 0.2
 *
 *
 * Copyright (c) 2009 Anton Kolodii
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 */

;(function($) {


	// the tooltip element variants
	var // the current tooltipped element
		current,
		// the title of the current element, used for restoring
		//title,
		// timeout id for delayed tooltips
		tID,
		// IE 5.5 or 6
		IE = $.browser.msie && /MSIE\s(5\.5|6\.)/.test(navigator.userAgent),
		// flag for mouse tracking
		track = false;

	var distance = 2;
    var time = 250;
    var hideDelay = 500;
    var loadingstring = 'Loading...'; //appear when tooltip was showed but it wait for JSON data load

    $.widget("ui.jtooltip", {
    	options: {
    		disabled: false,
    		msg:''
    	},
        _init: function() { 
    		// create the helper, h3 for title, div for url
		var elemID = this.element.attr('id');
		if (!elemID) {
			var a = new Date();
			elemID = a.getMilliseconds();
		}			
    		var tooltipid = elemID + this._getData('id');
			this.element.after('<div id="' + tooltipid + '" class="ui-widget ui-tooltip ui-tooltip-arrow-lc"><div class="tooltipContent"></div> ' +
				'<div class="arrow ui-tooltip-pointer ui-widget-content"> ' +
				'	<div class="ui-tooltip-pointer-inner"/>' +
				' </div>' +
				' </div>');
			var curwidget = $('#'+tooltipid, this.element.parent());
			// hide it at first
			curwidget.hide();
			
		// apply bgiframe if available
		if ( $.fn.bgiframe ) {}
			curwidget.bgiframe();
		
		// save references to title and url elements
		$.data(this.element[0], "tooltip.widget", curwidget);
		$.data(this.element[0], "tooltip.body", $('.tooltipContent', curwidget));
		var $this=this;
		this.element.each(function() {
			this.tOpacity = curwidget.css("opacity");
			// copy tooltip into its own expando and remove the title
			this.tooltipText =$this.options.msg;
			$(this).removeAttr("title");
			// also remove alt attribute to prevent default tooltip in IE
			alt = "";
		})
		.mouseover(this._save)
		.mouseout(this._whide)
		.click(this._whide);
	},
	_getData:function(key){
		return this.options[key];
	},
	// main event handler to start showing tooltips
	/*_handle: function (event) {
		// show helper, either with timeout or on instant
		if( this._getdata.delay )
			tID = setTimeout(_show, this._getdata('delay'));
		else
			this._show();
		
		// if selected, update the helper position when the mouse moves
		track = !!this._getdata('track');
		$(document.body).bind('mousemove', _update);
			
		// update at least once
		_update(event);
	},*/
	
	getdata: function (key) {
		return this._getData(key);
	},
	
	show: function () {
		// save current
		var self = this.element[0];
		var title = self.tooltipText;
		var helper = {};
		helper.parent = $.data(self, "tooltip.widget"); 
		//alert(helper.parent);
		helper.body = $.data(self, "tooltip.body"); 
		
		if ( helper.parent.is(":visible") ) { 
			helper.parent.stop();
		}
		if ( this.options.loadURL ) {
			helper.body.text ( loadingstring );
		} else if (this.options.template) {
			if (this.options.getJSONparams) {
				var JSONparameters = this.options.getJSONparams.call(this);
			} else 
				var JSONparameters = { Title: title };
			var str = $.stringformat(this.options.template, JSONparameters);
			helper.body.html(str);			
		} else if ( this.options.bodyHandler ) {
			var bodyContent = this.options.bodyHandler.call(this);
			if (bodyContent.nodeType || bodyContent.jquery) {
				helper.body.empty().append(bodyContent);
			} else {
				helper.body.html( bodyContent );
			}
		} else {
			helper.body.html(title);
		}
		// add an optional class for this tip
		helper.parent.addClass(this.options.tooltipClass);
		this._appear();

		// fix PNG background for IE
		if (this.options.fixPNG )
			helper.parent.fixPNG();
			
		if ( this.options.loadURL ) {
			if (this.options.getJSONparams) {
				var JSONparameters = this.options.getJSONparams.call(this);
			} else 
				var JSONparameters = null;
			var curwidget = this;
			$.getJSON ( this.options.loadURL, JSONparameters, 
				function (data) {
					var str = $(self).jtooltip('option', 'template');
					if (!str) {
						if ($(self).jtooltip('option', 'bodyHandler')) {
							var bodyContent = $(self).jtooltip('option', 'bodyHandler').call(self, data);
							if (bodyContent.nodeType || bodyContent.jquery) {
								helper.body.empty().append(bodyContent);
							} else {
								helper.body.html( bodyContent );
							}
						}
						return;
					}
					var resultstr = $.stringformat(str, data);
					helper.body.empty().append( resultstr );
					curwidget._appear();
				}
			);
		}
	},
	
	// save elements title before the tooltip is displayed
	_save: function () {
		$(this).jtooltip("showwithtrigger"); 
		$(this).jtooltip("show"); 
	},
	
	showwithtrigger: function () {
        this.element.trigger('onshow', null, null);
	},
	
	repaint: function () {
		this._appear();
	},
	
	// delete timeout and show tooltip
	_appear: function () {
		var widget = $.data(this.element[0], "tooltip.widget"); 
		var widgetbody = $.data(this.element[0], "tooltip.body");
		if (!widgetbody.html()) return;
		if (this.options.alwaysHide && current) {
			if (this.element[0] != current) $.data(current, "tooltip.widget").stop().hide();
		}
		tID = null;
		if ((!IE || !$.fn.bgiframe) && this.options.fade) {
			if (widget.is(":animated")) {
				widget.stop().show().fadeTo(this.options.fade, this.element[0].tOpacity);
		    } else
		    	widget.is(':visible') ? widget.stop().fadeTo(this.options.fade, this.element[0].tOpacity) : widget.stop().fadeIn(this.options.fade);
		} else {
			widget.show();
		}
		if (this.options.alwaysHide) 
			current = this.element[0];
		this._computeposition(this.element[0], widget, null);
	},

	/*_update: function (event)	{
		if($.tooltip.blocked)
			return;
		
		if (event && event.target.tagName == "OPTION") {
			return;
		}
		
		// stop updating when tracking is disabled and the tooltip is visible
		if ( !track && helper.parent.is(":visible")) {
			$(document.body).unbind('mousemove', update)
		}
		
		// if no current element is available, remove this listener
		if( current == null ) {
			$(document.body).unbind('mousemove', update);
			return;	
		}
		
		if (event) {
			this._computeposition(current, helper.parent, event);
		}
	},*/

	_computeposition: function (elem, toolElem, e) {

		function getdata(elem, key) {
			return $(elem).jtooltip('option', key);
		}

		// refactor: merge getY and getX into one function - the calculations are the same, just t/b vs. l/r
	function getY(y1, y1u, y2, y2u){
		var retVal,	ori;
		
		if(y2 > y1 && y2u < y1u){
			retVal = y1 + (y2-y1) + 0.5*(y2u-y2);
			ori = "c";
		}else if(y2 < y1 && y2u > y1u) {
			retVal = y1 + 0.5*(y1u - y1);
			ori = "c";
		}else if(y2 > y1 && y2u > y1u){
			retVal = y1u - 0.5*(y1u - y2);
			ori = "b";
		}else{
			retVal = y1 + 0.5*(y2u-y1);
			ori = "t";
		}
		return ({
			cutY : retVal,
			orientation: ori
		});
	};
	
	function getX(x1, x1r, x2, x2r){
		var retVal, ori;
		
		if(x2 > x1 && x2r < x1r){
			retVal = x1 + (x2-x1) + 0.5*(x2r-x2);
			ori = "c";
		}else if(x2 < x1 && x2r > x1r) {
			retVal = x1 + 0.5*(x1r - x1);
			ori = "c";
		}else if(x2 > x1 && x2r > x1r){
			retVal = x1r - 0.5*(x1r - x2);
			ori = "r";
		}else{
			retVal = x1 + 0.5*(x2r-x1);
			ori = "l";
		}
		return ({
			cutX : retVal,
			orientation: ori
		});
	};

    function calc(x1, y1, w1, h1, x2, y2, w2, h2, offsetX, offsetY) {
	 // corners
		var cutX = 0,
		cutY = 0,
		orientation = "lt";

		var x1r = x1 + w1;
		var y1u = y1 + h1;

		var x2r = x2 + w2;
		var y2u = y2 + h2;
		
		// right
		if(x2 > x1r){
		// below --> cornerarrow
			if(y2 > y1u){
				orientation = "br";
				offsetX = 0;
				offsetY += 4;
				cutX = x1r - 4;
				cutY = y1u;
			}
		
		// above --> cornerarrow
			else if(y1 > y2u){
				orientation = "tr";
				offsetX = 0;
				offsetY -= 26;
				cutX = x1r - 10;
				cutY = y1;
			}
		// right --> centerarrow
			else{
				offsetX += 4;
				offsetY = 0;
				cutX = x1r;
				var vals = getY(y1,y1u,y2,y2u);
				cutY = vals.cutY;
				orientation = "r" + vals.orientation;
			}
		}else
		
		// left
		if(x1 > x2r){
		// below --> cornerarrow
			if(y2 > y1u){
				if((y1 - y2u) < (x1 - x2r)){
					orientation = "lb";
					offsetX -= 26;
					offsetY = 0;
					cutX = x1;
					cutY = y1u - 4;
				}else{
					orientation = "bl";
					offsetX = 0;
					offsetY += 4;
					cutX = x1 + 10;
					cutY = y1u;
				}
			}
		// above --> cornerarrow
			else if(y1 > y2u){
				if((y1 - y2u) < (x1 - x2r)){
				orientation = "lt";
					offsetX -= 26;
					offsetY = 0;
					cutX = x1;
					cutY = y1 + 10;
				}else{
					orientation = "tl";
					offsetX = 0;
					offsetY -= 26;
					cutX = x1 + 10;
					cutY = y1;
				}
			}
		// left center --> centerarrow
			else{
				offsetX -= 26;
				offsetY = 0;
				cutX = x1;
				var vals = getY(y1,y1u,y2,y2u);
				cutY = vals.cutY;
				orientation = "l" + vals.orientation;
			}
		}else
		
		// above center
		if(y1 > y2u && (x1 < x2r && x2 < x1r)){
				offsetX = 0;
				offsetY -= 26;
				cutY = y1;
				vals = getX(x1,x1r,x2,x2r);
				cutX = vals.cutX;
				orientation = "t" + vals.orientation;
			
		}else
		
		// below center
		if(y1u < y2 && (x1 < x2r && x2 < x1r)){
				offsetX = 0;
				offsetY += 4;
				cutY = y1u;
				vals = getX(x1,x1r,x2,x2r);
				cutX = vals.cutX + 10;
				orientation = "b" + vals.orientation;
		}else{
			// no arrow
			cutX = -100;
			cutY = -100;
			orientation = "bl";
		}
		
        return ({
            x: cutX,
            y: cutY,
			offsetX: offsetX,
			offsetY: offsetY,
			orientation: orientation
        });
    };
    	toolElem.position({
			 of: $(elem),
			 my: getdata(elem, 'position_my')+'',
			 at: getdata(elem, 'position_at')+'', 
			 offset: getdata(elem, 'left') + ' ' + getdata(elem, 'top')
	    });

		var tip1 = toolElem,
		tip1offset = tip1.offset(),
		tip2 = $(elem),
		tip2offset = tip2.offset(),
		spot1 = $('div.arrow', toolElem);
		var cut1 = calc(tip1offset.left, tip1offset.top, tip1.width(), tip1.height(), tip2offset.left, tip2offset.top, tip2.width(), tip2.height(), spot1.outerWidth(), spot1.outerHeight());
		var tooltipoffset = spot1.offset();
		var offsetX = cut1.x - spot1.width() / 2 + cut1.offsetX; 
		var offsetY = cut1.y - spot1.height() / 2 + cut1.offsetY;
		if (Math.abs(tooltipoffset.left - offsetX) < 1) {
			offsetX = tooltipoffset.left;
		}
		if (Math.abs(tooltipoffset.top - offsetY) < 1) {
			offsetY = tooltipoffset.top;
		}
		spot1.offset({
			left: offsetX,
			top: offsetY
		});
		tip1.attr("class", function() {
			return tip1.attr("class").replace(/(ui-tooltip-arrow-)../, "$1" + cut1.orientation);
		});
	    
		// fix inner borders
		$('.ui-tooltip-pointer-inner').each(function(){
			var pt = $(this).parents('.ui-tooltip:eq(0)');
			var bColor = pt.css('backgroundColor');
			$(this).css({
				borderLeftColor: '',
				borderRightColor: '',
				borderTopColor: '',
				borderBottomColor: ''
			});
			if(pt.is('.ui-tooltip-arrow-rb,.ui-tooltip-arrow-rc,.ui-tooltip-arrow-rt')){ $(this).css('border-left-color', bColor); }
			else if(pt.is('.ui-tooltip-arrow-br,.ui-tooltip-arrow-bc,.ui-tooltip-arrow-bl')){ $(this).css('border-top-color', bColor); }
			else if(pt.is('.ui-tooltip-arrow-lb,.ui-tooltip-arrow-lc,.ui-tooltip-arrow-lt')){ $(this).css('border-right-color', bColor); }
			else { $(this).css('border-bottom-color', bColor);  }
		});	
	},
	
	_whide: function (event) {
		$(this).jtooltip("triggerhideevent");
		$(this).jtooltip("hide");
	},
	
	triggerhideevent: function () {
        this.element.trigger('onhide', null, null);
	},
	
	// hide tooltip and restore added classes and the title
	hide: function () {

		// clear timeout if possible
		if (tID)
			clearTimeout(tID);
		// no more current element
		if (this.options.alwaysHide) { current = null; }
		var widget = $.data(this.element[0], "tooltip.widget"); 
		function complete() {
			widget./*removeClass( oldClass ).*/hide().css("opacity", "");
		}

		if ((!IE || !$.fn.bgiframe) && this.options.fade) {
			if (widget.is(':animated')) {
				widget.stop().fadeTo(this.options.fade, 0, complete);
			} else
				widget.stop().fadeOut(this.options.fade, complete);
		} else
			complete();

		if( this.options.fixPNG )
			widget.unfixPNG();
	},
	
	setTooltipTitle: function (str) {
		this.element[0].tooltipText = str;
	}

	
    });
	
	$.extend($.ui.jtooltip, {
        version: '1.7.2',
        getter: 'length',
		defaults: {
			delay: 100,
			fade: 200,
			tooltipClass: "ui-widget-content ui-corner-all",
			template: "{Title}", //template string that help convert JSON data to user friendly view
			loadURL: "", //url to download JSON data for display tooltip content
			top: 10, //offset top
			left: 15, //offset left
			position_at: "right top", //see position plugin for more information
			position_my: "right top", //see position plugin for more information
			showBody: true, // deprecated please dont use
			alwaysHide: true,
			id: "tooltip"
				/* Some tooltip functions:
				 *  bodyhandler (el): if set you can handle tooltip body content as you want
				 *  getJSONparams (el): if set and loadURL parameter has been set call before tooltip show to get parameters which were send to server for getting JSON data callback 
				 */
		}
    });

	$.fn.extend({
		/*tooltip: function(settings) {
			settings = $.extend({}, $.tooltip.defaults, settings);
			createHelper(settings);
			return this.each(function() {
					$.data(this, "tooltip", settings);
					this.tOpacity = helper.parent.css("opacity");
					// copy tooltip into its own expando and remove the title
					this.tooltipText = this.title;
					$(this).removeAttr("title");
					// also remove alt attribute to prevent default tooltip in IE
					this.alt = "";
				})
				.mouseover(save)
				.mouseout(hide)
				.click(hide); 
		},*/
		fixPNG: IE ? function() {
			return this.each(function () {
				var image = $(this).css('backgroundImage');
				if (image.match(/^url\(["']?(.*\.png)["']?\)$/i)) {
					image = RegExp.$1;
					$(this).css({
						'backgroundImage': 'none',
						'filter': "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=crop, src='" + image + "')"
					}).each(function () {
						var position = $(this).css('position');
						if (position != 'absolute' && position != 'relative')
							$(this).css('position', 'relative');
					});
				}
			});
		} : function() { return this; },
		unfixPNG: IE ? function() {
			return this.each(function () {
				$(this).css({'filter': '', backgroundImage: ''});
			});
		} : function() { return this; }
		});
	
	
})(jQuery);
