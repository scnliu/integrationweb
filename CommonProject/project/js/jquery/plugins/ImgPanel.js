 (function ($) {
    $.fn.imgpanel =function(option){
        var dfop ={
               items:[], //ѡ������� {}
               txtname:"imgclassname",
               width:1024,
               height:600,
               imgNums:50,
               imgUrl:""
       }; 	
 	     $.extend(dfop, option); 
 	     var me =$(this).addClass("faceTitle");
 	     var bodywrap = $("<div id='faceContent' class='faceContent'/>");
       me.append(bodywrap);
       //���λ�
       $(this).click(function(e){
       	 $("#faceContent").toggle();
       	 showimglist();
       	 $("#faceContent img").click(function(){
					  $("#"+dfop.txtname).val($(this).attr('fn'));
	  			})
	  			e.stopPropagation();
	  			$("body").click(function(){
								$("#faceContent").hide();
					});
       });
       //�����б�
       
       function showimglist(){
			    $("#faceContent").html("");
					for(var i=0;i<dfop.items.length;i++){
						
						var item =dfop.items[i];
						str=dfop.imgUrl+item.text;
						$("#faceContent").append("<a href='javascript:'><img src="+str+" fn="+item.id+" /></a>");
					}       	
       }       
     }; 	 	
})(jQuery);