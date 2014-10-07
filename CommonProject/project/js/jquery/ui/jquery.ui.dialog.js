/*
 * jQuery UI Dialog 1.8.9
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Dialog
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 *  jquery.ui.button.js
 *	jquery.ui.draggable.js
 *	jquery.ui.mouse.js
 *	jquery.ui.position.js
 *	jquery.ui.resizable.js
 */

function Dialog(id,options){
    this.id=id;
    this.options_={autoOpen:false,
    			  prefix:'jqismooth',
    			  buttons:{ok:{value:"È·¶¨",iconCls:"icon-ok",close:true}}};
    var opt={};
    opt=jQuery.extend(this.options_,options);
    this.options=opt;
    this.dlg=$('#'+this.id).dialog(this.options);
    if(typeof Dialog.initialized=="undefined"){
    	Dialog.prototype.show=function(){
    		 if(this.dlg==null)this.dlg=$('#'+this.id).dialog(this.options);
        	this.dlg.dialog('show');
        };
        Dialog.prototype.hide=function(){
        	if(this.dlg==null)return;
        	this.dlg.dialog('hide');
        };
        Dialog.prototype.close=function(){
        	if(this.dlg==null)return;
        	this.dlg.dialog('close');
        };
        Dialog.prototype.setTitle=function(title){
        	if(this.dlg==null)return;
        	this.dlg.dialog('setTitle',title);
        };
        Dialog.prototype.disableButton=function(index){
        	if(this.dlg==null)return;
        	this.dlg.dialog('disableButton',index);
        };
        Dialog.initialized = true;
    }
};
(function( $, undefined ) {

var uiDialogClasses =
		'ui-dialog ' +
		'ui-widget ' +
		'ui-widget-content ' +
		'ui-corner-all ',
	sizeRelatedOptions = {
		buttons: true,
		height: true,
		maxHeight: true,
		maxWidth: true,
		minHeight: true,
		minWidth: true,
		width: true
	},
	resizableRelatedOptions = {
		maxHeight: true,
		maxWidth: true,
		minHeight: true,
		minWidth: true
	};

$.widget("ui.dialog", {
	options: {
		autoOpen: false,
		buttons: {},
		closeOnEscape: true,
		closeText: 'close',
		dialogClass: '',
		draggable: true,
		hide: null,
		height: 220,
		maxHeight: false,
		maxWidth: false,
		minHeight: 150,
		minWidth: 150,
		modal: true,
		position: {
			my: 'center',
			at: 'center',
			collision: 'fit',
			// ensure that the titlebar is never outside the document
			using: function(pos) {
				var topOffset = $(this).css(pos).offset().top;
				if (topOffset < 0) {
					$(this).css('top', pos.top - topOffset);
				}
			}
		},
		resizable: true,
		show: null,
		stack: true,
		title: '',
		width: 300,
		top: "15%",
		show: 'fadeIn',
	  	overlayspeed: 200,
	   	promptspeed: 150,
	   	opacity: 0.2,
		zIndex: 1000,
		onClose:null
	},

	_create: function() {
		this.originalTitle = this.element.attr('title');
		// #5742 - .attr() might return a DOMElement
		if ( typeof this.originalTitle !== "string" ) {
			this.originalTitle = "";
		}
		var ie6	= ($.browser.msie && $.browser.version < 7);
		var $body	= $(document.body);
		var $window	= $(window);
		var options=this.options;
		this.options.title = this.options.title || this.originalTitle;
		var self_ = this;
		options = self_.options;
		var title = options.title || '&#160;',
		titleId = $.ui.dialog.getTitleId(self_.element);
		var msgbox = '<div class="'+ options.prefix +'box" style="width:100%;height:100%;">';
		if(options.useiframe && (($('object, applet').length > 0) || ie6)) {
			msgbox += '<iframe src="javascript:false;" style="display:block;position:absolute;z-index:-1;" class="'+ options.prefix +'fade"></iframe>';
		} else {
			if($.browser.version<7) {
				$('select').css('visibility','hidden');
			}
			msgbox +='<div  class="'+ options.prefix +'fade" style="width:100%;height:100%;"></div>';
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

		//////////////
			var uiDialog = (self_.uiDialog = $(msgbox))
				.appendTo(document.body)
				.hide()
				.css({
					display:'none',
					zIndex: options.zIndex
				});
			var $jqib=uiDialog;
			this.jqib=$jqib;
			var $jqi=$jqib.children('.'+ options.prefix);
			this.jqi=$jqi;
			var $jqif= $jqib.children('.'+ options.prefix +'fade');
			this.jqif=$jqif;
			var $jqifMsg=$jqib.find('.'+ options.prefix +'container:eq(0)');
			this.jqifMsg=$jqifMsg;
			//build the states
			var states = "";
			states += '<div class="'+ options.prefix + '_state"><div class="content"><div class="'+ options.prefix +'message"></div></div><div class="'+ options.prefix +'buttons">';
			states += '</div></div>';
			//insert the states...
			var h=height-70;
			$jqi.find('.'+ options.prefix +'states').html(states).children('.'+ options.prefix +'_state:first').css('display','block');
			var uiDialogContent = self_.element.css('height',h).appendTo($jqi.find('.'+options.prefix +'message'));
		//handling of deprecated beforeclose (vs beforeClose) option
		//Ticket #4669 http://dev.jqueryui.com/ticket/4669
		//TODO: remove in 1.9pre
//		if ($.isFunction(options.beforeclose) && !$.isFunction(options.beforeClose)) {
//			options.beforeClose = options.beforeclose;
//		}
		if (options.draggable && $.fn.draggable) {
			self_._makeDraggable();
		}
		if (options.resizable && $.fn.resizable) {
			self_._makeResizable();
		}
			
//		$jqi.find('.' + options.prefix + 'buttons:empty').css('display', 'none');
		this._createButtons(options.buttons);
		$jqi.find('.'+options.prefix +'buttons').children('button.close')
		.click(function() {
			$jqi.fadeOut(150);
			$jqif.fadeOut(200);
			$jqib.hide();
	    });
		this.positionPrompt();
		this.stylePrompt();
		$jqi.find('.title').mouseover(function(){
			$(this).attr('drag',true);
		}).mouseout(function(){
			$(this).removeAttr('drag');
		});
//		$jqif.click(fadeClicked);
		$window.resize(function(){self_.positionPrompt();});
//		$jqib.bind("keydown keypress",keyPressEventHandler);
		$jqi.find('.'+ options.prefix +'close').click(function(){self_.hide();});
		
		if(options.autoOpen){
			this.show();
		}
		if(ie6) {
			$window.scroll(ie6scroll);
		}

	},
    show:function(){
    	var options=this.options;
		this.jqib.show();
		this.jqif.fadeIn(options.overlayspeed);
		this.jqi[options.show](options.promptspeed);
		this.jqi.find('.'+ options.prefix +'states .'+ options.prefix +'_state:first .'+ options.prefix +'defaultbutton').focus();
		this._isOpen = true;
    },
    disableButton:function(index){
    	$jqi.find('.'+options.prefix +'buttons').children('button:eq('+index+')').button('disable');
    },
    hide:function(){
    	var options=this.options;
		this.jqi.fadeOut(100);
		this.jqif.fadeOut(200);
		this.jqib.hide();
		this._isOpen = false;
		if(options.onClose){
			options.onClose();
		}
    },
    setTitle:function(title){
       this.jqi.find('.title_c').html(title);
    },
	getId:function(idPrex){
		var date=new Date();
		return idPrex+date.getTime();
	},
	positionPrompt: function(){
		var $window=$(window);
		var ie6=$.browser.msie&&$.browser.version<7;
		var options=this.options;
		var w=options.width;
		var h=options.height;
		if(typeof w!="string"){
			w=w+'px';
		}else{
			w=$window.width()*w.replace('%','')/100-40;
		}
		if(typeof h!="string"){
			h=h+'px';
		}else{
			h=$window.height()*h.replace('%','')/100-80;
		}
		this.jqifMsg.css({
			width:w
		});
		this.jqib.css({
			position: "absolute" ,
			height: $window.height(),
			width: "100%",
			top:0,
			left: 0,
			right: 0,
			bottom: 0
		});
		this.jqif.css({
			position: "absolute",
			height: $window.height(),
			width: "100%",
			top: 0,
			left: 0,
			right: 0,
			bottom: 0
		});
		var bwidth=$(document).width();
		var l=(bwidth-options.width)/2;
		this.jqi.css({
			position: "absolute",
			top: options.top,
			left: l
		});
	},

	stylePrompt:function(){
		var options=this.options;
		this.jqif.css({
			zIndex: options.zIndex,
			display: "none",
			opacity: options.opacity
		});
		this.jqi.css({
			zIndex: options.zIndex+1,
			display: "none"
		});
		this.jqib.css({
			zIndex: options.zIndex
		});
	},
	_init: function() {
		if ( this.options.autoOpen ) {
			this.open();
		}
	},

	destroy: function() {
		var self_ = this;
		
		if (self_.overlay) {
			self_.overlay.destroy();
		}
		self_.uiDialog.hide();
		self_.element
			.unbind('.dialog')
			.removeData('dialog')
			.removeClass('ui-dialog-content ui-widget-content')
			.hide().appendTo('body');
		self_.uiDialog.remove();

		if (self_.originalTitle) {
			self_.element.attr('title', self_.originalTitle);
		}

		return self_;
	},

	widget: function() {
		return this.uiDialog;
	},

	close: function(event) {
		var self_ = this,
			maxZ, thisZ;
		
		if (false === self_._trigger('beforeClose', event)) {
			return;
		}

		if (self_.overlay) {
			self_.overlay.destroy();
		}
		self_.uiDialog.unbind('keypress.ui-dialog');

		self_._isOpen = false;

		if (self_.options.hide) {
			self_.uiDialog.hide(self_.options.hide, function() {
				self_._trigger('close', event);
			});
		} else {
			self_.uiDialog.hide();
			self_._trigger('close', event);
		}

		$.ui.dialog.overlay.resize();

		// adjust the maxZ to allow other modal dialogs to continue to work (see #4309)
		if (self_.options.modal) {
			maxZ = 0;
			$('.ui-dialog').each(function() {
				if (this !== self_.uiDialog[0]) {
					thisZ = $(this).css('z-index');
					if(!isNaN(thisZ)) {
						maxZ = Math.max(maxZ, thisZ);
					}
				}
			});
			$.ui.dialog.maxZ = maxZ;
		}
		return self_;
	},

	isOpen: function() {
		return this._isOpen;
	},

	// the force parameter allows us to move modal dialogs to their correct
	// position on open
	moveToTop: function(force, event) {
		var self_ = this,
			options = self_.options,
			saveScroll;

		if ((options.modal && !force) ||
			(!options.stack && !options.modal)) {
			return self_._trigger('focus', event);
		}

		if (options.zIndex > $.ui.dialog.maxZ) {
			$.ui.dialog.maxZ = options.zIndex;
		}
		if (self_.overlay) {
			$.ui.dialog.maxZ += 1;
			self_.overlay.$el.css('z-index', $.ui.dialog.overlay.maxZ = $.ui.dialog.maxZ);
		}

		//Save and then restore scroll since Opera 9.5+ resets when parent z-Index is changed.
		//  http://ui.jquery.com/bugs/ticket/3193
		saveScroll = { scrollTop: self_.element.attr('scrollTop'), scrollLeft: self_.element.attr('scrollLeft') };
		$.ui.dialog.maxZ += 1;
		self_.uiDialog.css('z-index', $.ui.dialog.maxZ);
		self_.element.attr(saveScroll);
		self_._trigger('focus', event);

		return self_;
	},

	open: function() {
		if (this._isOpen) { return; }

		var self_ = this,
			options = self_.options,
			uiDialog = self_.uiDialog;
return;
		self_.overlay = options.modal ? new $.ui.dialog.overlay(self_) : null;
		self_._size();
		self_._position(options.position);
		uiDialog.show(options.show);
		return;
//		self_.moveToTop(true);

		// prevent tabbing out of modal dialogs
		if (options.modal) {
			uiDialog.bind('keypress.ui-dialog', function(event) {
				if (event.keyCode !== $.ui.keyCode.TAB) {
					return;
				}

				var tabbables = $(':tabbable', this),
					first = tabbables.filter(':first'),
					last  = tabbables.filter(':last');

				if (event.target === last[0] && !event.shiftKey) {
					first.focus(1);
					return false;
				} else if (event.target === first[0] && event.shiftKey) {
					last.focus(1);
					return false;
				}
			});
		}

		// set focus to the first tabbable element in the content area or the first button
		// if there are no tabbable elements, set focus on the dialog itself_
		$(self_.element.find(':tabbable').get().concat(
			uiDialog.find('.ui-dialog-buttonpane :tabbable').get().concat(
				uiDialog.get()))).eq(0).focus();

		self_._isOpen = true;
		self_._trigger('open');
		return self_;
	},

	_createButtons: function(buttons) {
		var self_ = this;
		var hasButtons = false;
		var	$btnPane=this.jqi.find('.'+self_.options.prefix +'buttons');
		// if we already have a button pane, remove it
		if (buttons!=null){
			hasButtons=true;
		}
		if (hasButtons) {
			$.each(buttons, function(name, props) {
//				props = $.isFunction(props) ? {click: props, value: name } : props;
				var v=props;
				var id=v.id;
				if(!v.id)v.id=self_.getId('dlgButtonCancel');
				  $btnPane.append('<button class="dlg_btn_cancel '+(v.close?'close':'')+'" iconCls="'+(v.iconCls?v.iconCls:"")+'" name="' +(v.name?v.name:'') + '" id="' +v.id + '" value="' + v.value + '">' + v.value + '</button>');
				  if($.isFunction(v.click)){
					  $('#'+v.id).button().bind('click',v.click); 
				  }else{
					  $('#'+v.id).button().click(v.click);
				  }
				  
			});
		}
	},

	_makeDraggable: function() {
		var self_ = this,
			options = self_.options,
			doc = $(document),
			heightBeforeDrag;

		function filteredUi(ui) {
			return {
				position: ui.position,
				offset: ui.offset
			};
		}
		self_.jqi.draggable({
			cancel: '.ui-dialog-content, .ui-dialog-titlebar-close',
			handle: '.ui-dialog-titlebar',
			containment: 'document',
			start: function(event, ui) {
				if(!self_.jqi.find('.title').attr('drag'))return false;
				heightBeforeDrag = options.height === "auto" ? "auto" : $(this).height();
				$(this).height($(this).height()).addClass("ui-dialog-dragging");
				self_._trigger('dragStart', event, filteredUi(ui));
			},
			drag: function(event, ui) {
				self_._trigger('drag', event, filteredUi(ui));
			},
			stop: function(event, ui) {
				options.position = [ui.position.left - doc.scrollLeft(),
					ui.position.top - doc.scrollTop()];
				$(this).removeClass("ui-dialog-dragging").height(heightBeforeDrag);
				self_._trigger('dragStop', event, filteredUi(ui));
				$.ui.dialog.overlay.resize();
				self_.jqi.find('.title').removeAttr('drag');
			}
		});
	},

	_makeResizable: function(handles) {
		handles = (handles === undefined ? this.options.resizable : handles);
		var self_ = this,
			options = self_.options,
			// .ui-resizable has position: relative defined in the stylesheet
			// but dialogs have to use absolute or fixed positioning
			position = self_.uiDialog.css('position'),
			resizeHandles = (typeof handles === 'string' ?
				handles	:
				'n,e,s,w,se,sw,ne,nw'
			);

		function filteredUi(ui) {
			return {
				originalPosition: ui.originalPosition,
				originalSize: ui.originalSize,
				position: ui.position,
				size: ui.size
			};
		}

		self_.uiDialog.resizable({
			cancel: '.ui-dialog-content',
			containment: 'document',
			alsoResize: self_.element,
			maxWidth: options.maxWidth,
			maxHeight: options.maxHeight,
			minWidth: options.minWidth,
			minHeight: self_._minHeight(),
			handles: resizeHandles,
			start: function(event, ui) {
				$(this).addClass("ui-dialog-resizing");
				self_._trigger('resizeStart', event, filteredUi(ui));
			},
			resize: function(event, ui) {
				self_._trigger('resize', event, filteredUi(ui));
			},
			stop: function(event, ui) {
				$(this).removeClass("ui-dialog-resizing");
				options.height = $(this).height();
				options.width = $(this).width();
				self_._trigger('resizeStop', event, filteredUi(ui));
				$.ui.dialog.overlay.resize();
			}
		})
		.css('position', position)
		.find('.ui-resizable-se').addClass('ui-icon ui-icon-grip-diagonal-se');
	},

	_minHeight: function() {
		var options = this.options;

		if (options.height === 'auto') {
			return options.minHeight;
		} else {
			return Math.min(options.minHeight, options.height);
		}
	},

	_position: function(position) {
		var myAt = [],
			offset = [0, 0],
			isVisible;

		if (position) {
			// deep extending converts arrays to objects in jQuery <= 1.3.2 :-(
	//		if (typeof position == 'string' || $.isArray(position)) {
	//			myAt = $.isArray(position) ? position : position.split(' ');

			if (typeof position === 'string' || (typeof position === 'object' && '0' in position)) {
				myAt = position.split ? position.split(' ') : [position[0], position[1]];
				if (myAt.length === 1) {
					myAt[1] = myAt[0];
				}

				$.each(['left', 'top'], function(i, offsetPosition) {
					if (+myAt[i] === myAt[i]) {
						offset[i] = myAt[i];
						myAt[i] = offsetPosition;
					}
				});

				position = {
					my: myAt.join(" "),
					at: myAt.join(" "),
					offset: offset.join(" ")
				};
			} 

			position = $.extend({}, $.ui.dialog.prototype.options.position, position);
		} else {
			position = $.ui.dialog.prototype.options.position;
		}

		// need to show the dialog to get the actual offset in the position plugin
		isVisible = this.uiDialog.is(':visible');
		if (!isVisible) {
			this.uiDialog.show();
		}
		this.uiDialog
			// workaround for jQuery bug #5781 http://dev.jquery.com/ticket/5781
			.css({ top: 0, left: 0 })
			.position($.extend({ of: window }, position));
		if (!isVisible) {
			this.uiDialog.hide();
		}
	},

	_setOptions: function( options ) {
		var self_ = this,
			resizableOptions = {},
			resize = false;

		$.each( options, function( key, value ) {
			self_._setOption( key, value );
			
			if ( key in sizeRelatedOptions ) {
				resize = true;
			}
			if ( key in resizableRelatedOptions ) {
				resizableOptions[ key ] = value;
			}
		});

		if ( resize ) {
			this._size();
		}
		if ( this.uiDialog.is( ":data(resizable)" ) ) {
			this.uiDialog.resizable( "option", resizableOptions );
		}
	},

	_setOption: function(key, value){
		var self_ = this,
			uiDialog = self_.uiDialog;

		switch (key) {
			//handling of deprecated beforeclose (vs beforeClose) option
			//Ticket #4669 http://dev.jqueryui.com/ticket/4669
			//TODO: remove in 1.9pre
			case "beforeclose":
				key = "beforeClose";
				break;
			case "buttons":
				self_._createButtons(value);
				break;
			case "closeText":
				// ensure that we always pass a string
				self_.uiDialogTitlebarCloseText.text("" + value);
				break;
			case "dialogClass":
				uiDialog
					.removeClass(self_.options.dialogClass)
					.addClass(uiDialogClasses + value);
				break;
			case "disabled":
				if (value) {
					uiDialog.addClass('ui-dialog-disabled');
				} else {
					uiDialog.removeClass('ui-dialog-disabled');
				}
				break;
			case "draggable":
				var isDraggable = uiDialog.is( ":data(draggable)" );
				if ( isDraggable && !value ) {
					uiDialog.draggable( "destroy" );
				}
				
				if ( !isDraggable && value ) {
					self_._makeDraggable();
				}
				break;
			case "position":
				self_._position(value);
				break;
			case "resizable":
				// currently resizable, becoming non-resizable
				var isResizable = uiDialog.is( ":data(resizable)" );
				if (isResizable && !value) {
					uiDialog.resizable('destroy');
				}

				// currently resizable, changing handles
				if (isResizable && typeof value === 'string') {
					uiDialog.resizable('option', 'handles', value);
				}

				// currently non-resizable, becoming resizable
				if (!isResizable && value !== false) {
					self_._makeResizable(value);
				}
				break;
			case "title":
				// convert whatever was passed in o a string, for html() to not throw up
				$(".ui-dialog-title", self_.uiDialogTitlebar).html("" + (value || '&#160;'));
				break;
		}

		$.Widget.prototype._setOption.apply(self_, arguments);
	},

	_size: function() {
		/* If the user has resized the dialog, the .ui-dialog and .ui-dialog-content
		 * divs will both have width and height set, so we need to reset them
		 */
		var options = this.options,
			nonContentHeight,
			minContentHeight,
			isVisible = this.uiDialog.is( ":visible" );

		// reset content sizing
		this.element.show().css({
			width: 'auto',
			minHeight: 0,
			height: 0
		});

		if (options.minWidth > options.width) {
			options.width = options.minWidth;
		}

		// reset wrapper sizing
		// determine the height of all the non-content elements
		nonContentHeight = this.uiDialog.css({
				height: 'auto',
				width: options.width
			})
			.height();
		minContentHeight = Math.max( 0, options.minHeight - nonContentHeight );
		
		if ( options.height === "auto" ) {
			// only needed for IE6 support
			if ( $.support.minHeight ) {
				this.element.css({
					minHeight: minContentHeight,
					height: "auto"
				});
			} else {
				this.uiDialog.show();
				var autoHeight = this.element.css( "height", "auto" ).height();
				if ( !isVisible ) {
					this.uiDialog.hide();
				}
				this.element.height( Math.max( autoHeight, minContentHeight ) );
			}
		} else {
			this.element.height( Math.max( options.height - nonContentHeight, 0 ) );
		}

		if (this.uiDialog.is(':data(resizable)')) {
			this.uiDialog.resizable('option', 'minHeight', this._minHeight());
		}
	}
});

$.extend($.ui.dialog, {
	version: "1.8.9",

	uuid: 0,
	maxZ: 0,

	getTitleId: function($el) {
		var id = $el.attr('id');
		if (!id) {
			this.uuid += 1;
			id = this.uuid;
		}
		return 'ui-dialog-title-' + id;
	},

	overlay: function(dialog) {
		this.$el = $.ui.dialog.overlay.create(dialog);
	}
});

$.extend($.ui.dialog.overlay, {
	instances: [],
	// reuse old instances due to IE memory leak with alpha transparency (see #5185)
	oldInstances: [],
	maxZ: 0,
	events: $.map('focus,mousedown,mouseup,keydown,keypress,click'.split(','),
		function(event) { return event + '.dialog-overlay'; }).join(' '),
	create: function(dialog) {
		if (this.instances.length === 0) {
			// prevent use of anchors and inputs
			// we use a setTimeout in case the overlay is created from an
			// event that we're going to be cancelling (see #2804)
			setTimeout(function() {
				// handle $(el).dialog().dialog('close') (see #4065)
				if ($.ui.dialog.overlay.instances.length) {
					$(document).bind($.ui.dialog.overlay.events, function(event) {
						// stop events if the z-index of the target is < the z-index of the overlay
						// we cannot return true when we don't want to cancel the event (#3523)
						if ($(event.target).zIndex() < $.ui.dialog.overlay.maxZ) {
							return false;
						}
					});
				}
			}, 1);

			// allow closing by pressing the escape key
			$(document).bind('keydown.dialog-overlay', function(event) {
				if (dialog.options.closeOnEscape && event.keyCode &&
					event.keyCode === $.ui.keyCode.ESCAPE) {
					
					dialog.close(event);
					event.preventDefault();
				}
			});

			// handle window resize
			$(window).bind('resize.dialog-overlay', $.ui.dialog.overlay.resize);
		}

		var $el = (this.oldInstances.pop() || $('<div></div>').addClass('ui-widget-overlay'))
			.appendTo(document.body)
			.css({
				width: this.width(),
				height: this.height()
			});

		if ($.fn.bgiframe) {
			$el.bgiframe();
		}

		this.instances.push($el);
		return $el;
	},

	destroy: function($el) {
		var indexOf = $.inArray($el, this.instances);
		if (indexOf != -1){
			this.oldInstances.push(this.instances.splice(indexOf, 1)[0]);
		}

		if (this.instances.length === 0) {
			$([document, window]).unbind('.dialog-overlay');
		}

		$el.remove();
		
		// adjust the maxZ to allow other modal dialogs to continue to work (see #4309)
		var maxZ = 0;
		$.each(this.instances, function() {
			maxZ = Math.max(maxZ, this.css('z-index'));
		});
		this.maxZ = maxZ;
	},

	height: function() {
		var scrollHeight,
			offsetHeight;
		// handle IE 6
		if ($.browser.msie && $.browser.version < 7) {
			scrollHeight = Math.max(
				document.documentElement.scrollHeight,
				document.body.scrollHeight
			);
			offsetHeight = Math.max(
				document.documentElement.offsetHeight,
				document.body.offsetHeight
			);

			if (scrollHeight < offsetHeight) {
				return $(window).height() + 'px';
			} else {
				return scrollHeight + 'px';
			}
		// handle "good" browsers
		} else {
			return $(document).height() + 'px';
		}
	},

	width: function() {
		var scrollWidth,
			offsetWidth;
		// handle IE 6
		if ($.browser.msie && $.browser.version < 7) {
			scrollWidth = Math.max(
				document.documentElement.scrollWidth,
				document.body.scrollWidth
			);
			offsetWidth = Math.max(
				document.documentElement.offsetWidth,
				document.body.offsetWidth
			);

			if (scrollWidth < offsetWidth) {
				return $(window).width() + 'px';
			} else {
				return scrollWidth + 'px';
			}
		// handle "good" browsers
		} else {
			return $(document).width() + 'px';
		}
	},

	resize: function() {
		/* If the dialog is draggable and the user drags it past the
		 * right edge of the window, the document becomes wider so we
		 * need to stretch the overlay. If the user then drags the
		 * dialog back to the left, the document will become narrower,
		 * so we need to shrink the overlay to the appropriate size.
		 * This is handled by shrinking the overlay before setting it
		 * to the full document size.
		 */
		var $overlays = $([]);
		$.each($.ui.dialog.overlay.instances, function() {
			$overlays = $overlays.add(this);
		});

		$overlays.css({
			width: 0,
			height: 0
		}).css({
			width: $.ui.dialog.overlay.width(),
			height: $.ui.dialog.overlay.height()
		});
	}
});

$.extend($.ui.dialog.overlay.prototype, {
	destroy: function() {
		$.ui.dialog.overlay.destroy(this.$el);
	}
});

}(jQuery));
