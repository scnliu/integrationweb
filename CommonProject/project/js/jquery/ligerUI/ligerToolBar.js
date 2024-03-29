﻿/**
* jQuery ligerUI 1.0.1
* 
* Author leoxie [ gd_star@163.com ] 
* 
*/
if(typeof (LigerUIManagers) == "undefined") LigerUIManagers = {};
(function($)
{
    ///	<param name="$" type="jQuery"></param>

    $.fn.ligerGetToolBarManager = function()
    {
        return LigerUIManagers[this[0].id + "_ToolBar"];
    }; 
    $.fn.ligerToolBar = function(p)
    { 
        return this.each(function()
        {
            if (this.usedToolBar) return;
            var g ={
                addItem :function(item){
                    var ditem = $('<div class="l-toolbar-item l-panel-btn"><span></span><div class="l-panel-btn-l"></div><div class="l-panel-btn-r"></div></div>');
                    g.toolBar.append(ditem);
                    item.id && ditem.attr("toolbarid",item.id);
                    item.text && $("span:first",ditem).html(item.text);
                    item.disable && ditem.addClass("l-toolbar-item-disable");
                    item.click && ditem.click(function(){ item.click(item);}); 
                    ditem.hover(function ()
                    {
                        $(this).addClass("l-panel-btn-over");
                    }, function ()
                    {
                        $(this).removeClass("l-panel-btn-over");
                    });
                }
            };
            g.toolBar = $(this);
            if(!g.toolBar.hasClass("l-toolbar")) g.toolBar.addClass("l-toolbar"); 
            if(p.items)
            {
                
                $(p.items).each(function(i,item){
                    g.addItem(item); 
                });
            } 
            if (this.id == undefined) this.id = "LigerUI_" + new Date().getTime();
            LigerUIManagers[this.id + "_ToolBar"] = g;
            this.usedToolBar = true;
        });
    };

})(jQuery);