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
var t=null;
$.widget("ui.button", {
	options: {
		disabled: false,
		text: true,
		label: null,
		iconCls:null
	},
	_create: function() {
		var ele=this.element;
		var options=this.options;
		var self_=this;
		if ( typeof this.options.disabled !== "boolean" ) {
			this.options.disabled = this.element.attr("disabled");
		}

		if ( options.label === null ) {
			options.label =this.element.attr('value');
			if(!options.label)options.label =this.element.html();
		}

		if ( this.element.is( ":disabled" ) ) {
			this.options.disabled= true;
		}
		
		var value =options.label;
         var iconCls=options.iconCls||ele.attr('iconCls');
		 var cls="btn-empty";
		 ele.css('display','none');
		if(iconCls){
			cls=cls+" "+iconCls+" btn-icon";
		}
		var btn=$('<span class="btn-a"><a href="#" tabIndex="20" class="'+(options.disabled?'btn-disabled':'')+'"><span class="btn-r"><span class="btn-l"><span class="'+cls+'" ></span>'+ value +'</span></span></a></span>');
		ele.after(btn);
		this.btn=btn;
		this.btn.bind('hide',function(){self_.hide();});
		this.btn.bind('show',function(){self_.show();});
		if($.browser.msie){
			var w=ele.next(".btn-a").width();
//			var width=100;
			if($.browser.msie&&$.browser.version>=9.0){
				width=value.length*15+(iconCls?32:24);
				if(w<width)ele.next(".btn-a").css('width',width);
			}else if($.browser.msie&&$.browser.version<8.0){
				width=value.length*15+(iconCls?32:24);
				if(width>w)
				  ele.next(".btn-a").css('width',width);
			}
			else if($.browser.msie&&$.browser.version>=8.0&&$.browser.version<9){
				width=value.length*14+(iconCls?32:24);
				if(width>w)
				  ele.next(".btn-a").css('width',width);
			}else{
				width=value.length*16+(iconCls?37:24);
			}
		}
		ele.nextUntil('a').first().children('a').first().active();
		this._initEvent();
	},
	widget:function(){
		return this.btn;
	},
	_initEvent:function(){
			 	var ele = this.element;
				var href = ele.attr('href');
				var clk = ele.attr('onClick');
				var btn = ele;
				var opts=this.options;
				$ne=ele.nextUntil('a').first().children('a').first();
				if(href&&href.length>1&&href!='#'){
					$ne.attr("href", href);
				}else{
					$ne.bind('click', function(){
						if(!opts.disabled){
							ele.click();
						}
						return false;
					});
					$ne.bind('keydown keypress', function(){
						if(!opts.disabled){
							var key = (window.event) ? event.keyCode : e.keyCode; // MSIE or Firefox?
							if(key==13) {
								ele.click();
							}
						}
						return false;
					});
					$ne.attr("href", "javascript:void(0)");
				}
	},
	/**
	 * 设置按钮的显示文字
	 */
	setLabel: function(label) {
		var btnL=this.btn.find('.btn-l');
		var f=btnL.children().first();
		btnL.text(label).prepend(f);
		return this;
	},
	/**
	 * 开启按钮，可点击
	 */
	enable: function() {
		this._setOption( "disabled", false);
		this.element.next('span.btn-a').first().children('a').removeClass("btn-disabled");
//		this.element.next('span.btn-a').first().find('span.btn-l').find('span').first().removeClass("progressTracker2");
		//this._initEvent();
		return this;
	},
	/**
	 * 停用按钮，点击事件无响应
	 */
	disable: function() {
		this.element.next('span.btn-a').first().children('a').addClass("btn-disabled");
//		this.element.next('span.btn-a').first().find('span.btn-l').find('span').first().addClass("progressTracker2");
		//this.element.next('a').unbind("click");
		return this._setOption( "disabled", true );
	
	},
	/**
	 * 隐藏按钮
	 */
	hide: function() {
		this.btn.removeClass("btn-a");
		this.btn.addClass("btn-a-hide");
	},
	/**
	 * 显示按钮
	 */
	show: function() {
		this.btn.addClass("btn-a");
		this.btn.removeClass("btn-a-hide");
	},
	focus: function() {
		this.btn.find('a:eq(0)').focus();
	},
	toggle: function() {
		this._setData("checked", !this._getData("checked"));
	},
	/**
	 * 按钮占用
	 */
	busy: function() {
		this.element.next('span.btn-a').first().find('span.btn-l').find('span').first().addClass("progressTracker2");
		this.disable();
		//this.element.next('a').unbind("click");
	},
	/**
	 * 按钮取消占用
	 */
	free: function() {
		this.element.next('span.btn-a').first().find('span.btn-l').find('span').first().removeClass("progressTracker2");
		this.enable();
		
	},
	setIconClass:function(icon){
		var iconSpan=this.element.next('span.btn-a').first().find('span.btn-l').find('span').first();
		iconSpan.addClass(icon);
	},
	setIconImage:function(icon){
		var iconSpan=this.element.next('span.btn-a').first().find('span.btn-l').find('span').first();
		iconSpan.css('background-image',icon);
	}
});
})(jQuery);
