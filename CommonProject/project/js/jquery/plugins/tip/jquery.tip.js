/*
 *	$.fn.anchored С��
 *		var searchDchnoTip=$("<div style='text-align:center;color:red'>���������֣�</div>").anchored({title:'�������',parentId:'map',show:true});
 *			
 *	$.fn.anchored2  ���
 *		var popup=$('#cellInfo').anchored2({title:'С����Ϣ',parentId:'map',show:false}); 
 */
(function($) {
	$.fn.tip = function(options_){
		var options = $.extend({}, $.fn.tip.defaults, options_);
		return $(this)
				.each(function() {
					var $this=$(this);
					var content=options.content;
					var $pop=$("<div class='etip'><div class='tip-head'></div>" +
							"<div  class='tip-body'>" +
							"</div><div  class='tip-foot'></div></div>").appendTo(document.body);
					content.appendTo($pop.find('.tip-body'));
					$pop.find('div.anchored-body').mousedown(function(evt){evt.stopPropagation();});
					$this.hover(
						function(){
							var offset=$(this).offset();
							var h=options.offsetTop||$(this).height();
							var w=$(this).width();
							$pop.fadeIn(options.duration);
							$pop.css('left',offset.left-98+w/2);
							$pop.css('top',offset.top+h);
						},function(){
							$pop.hide();
						}
					);
				});
	};
	$.fn.tip.defaults = {
			duration:400
	};
})(jQuery);