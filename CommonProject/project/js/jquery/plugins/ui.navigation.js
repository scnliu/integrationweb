if(typeof (ESUiManagers) == "undefined") ESUiManagers = {};
(function ($)
{
    ///	<param name="$" type="jQuery"></param>

    $.fn.esGetNavigationManager = function ()
    {
        return ESUiManagers[this[0].id + "_Navigation"];
    }; 
    $.esUiDefaults = $.esUiDefaults || {};
    $.esUiDefaults.Navigation = {
            attribute : ['id','url'],
            nodeWidth: 90,
            statusName : '__status',
            onSelect :null,
            onClick:null
    };
    $.fn.esNavigation = function (p)
    {
        return this.each(function ()
        {
            p = $.extend({},$.esUiDefaults.Navigation, p || {});
            if(this.used)return;
            var g = {
            	createNavigation:function(){
            		this.navigation.addClass('navigation-ul');
            		$('>li',g.navigation).each(function(){
            			if (this.id == undefined || this.id == "") this.id = "ESUI_Item" + new Date().getTime();
            			var $this=$(this);
            			var icon=$this.attr('icon');
            			var text=$this.html()||$this.attr('text');
            			$this.empty().append("<div class='navigation-li-icon'>"+text+"</div>");
            			if(icon){
            				$this.find('.navigation-li-icon').css('background-image',"url('"+icon+"')");	
            			}
            			var nav=this;
            			$this.click(function(evt){
            				if($(this).attr('selected'))return false;
            				var nodeData={id:this.id,text:$(this).find('.navigation-li').html(),url:$(this).attr('url')};
            				p.onSelect({data:nodeData,target:this});
            				$(this).parent().find('.navigation-li-selected').removeClass('navigation-ul-selected');
            				$(this).find('.navigation-ul').addClass('navigation-ul-selected');
            				$(this).attr('selected',true);
            			});
            		});
            	},
                getSelected: function ()
                {
                },
                remove: function (node)
                {
                },
                //增加节点集合
                append: function (newdata)
                {
                }
            };
            g.navigation=$(this);
            if (this.id == undefined || this.id == "") this.id = "ESUI_" + new Date().getTime();
            g.createNavigation();
            ESUiManagers[this.id + "_Navigation"] = g;
            this.used=true;
        });
    };

})(jQuery);