
/**
 * 创建层
 * @param _width
 * @param _height
 */
function createDiv(_width,_height,_middleHeight){
	var dataDiv=$('<div style="display: none; position: absolute;z-index: 2000;background-color: white;border: 1px solid;"></div>');
	  dataDiv.attr({'id':"dataDiv"});
	     
	   dataDiv.css('width',_width);
	   dataDiv.css('height',_height);
	 
      $("body").append(dataDiv);
	   
	var topDiv=$('<div style="overflow: hidden;"></div>');
	var topTable=$('<table style="width: 100%; background-color: #deecfd;border: 1px solid #8db2e3; height: 20px;"></table>');
	var toptr=$('<tr></tr>');
	var toptd0=$('<td style="width: 20px;"></td>').appendTo(toptr);
	var checkbox=$('<input type="checkbox" name="mrrAllFile" value="全选"></input>');
	    checkbox.attr({'id':'mrrAllFile'});
	    checkbox.bind('click',selectAll);
	    toptd0.append(checkbox);
  	 
  	 var toptd1=$('<td></td>').appendTo(toptr);
  	 	 toptd1.html("全选");
  	 
  	 var toptd2=$('<td></td>').appendTo(toptr);
  	 	  toptd2.attr({'align':"right"});
  	    var img=$('<img ></img>').appendTo(toptd2);
  	    img.attr({'src':_appContext+'/js/jquery/themes/img/TabPanel/closeother.png'});
  	    img.bind('click',closeWindow);
	  
  	 topTable.append(toptr);
  	topDiv.append(topTable);
  	
  	
  	var middleDiv=$('<div style="overflow-y: auto;" id="mrrDiv"></div>');
  	    middleDiv.css("height",_middleHeight);
  	var middleTable=$('<table></table>');
  	    middleTable.attr({'id':'mrrTable'});
  	
  	  middleDiv.append(middleTable);
  	  
  	  
	
  	  var footDiv=$('<div style="overflow: hidden;"></div>');
	  var footTable=$('<table style="width: 100%; background-color: #deecfd; '+
			 ' border: 1px solid #8db2e3; height: 38px;"></table>');
	  var foottr=$('<tr></tr>').appendTo(footTable);
	  var foottd=$('<td align="center"></td>').appendTo(foottr);
	  var btn= $('<input id="butt" iconCls="icon-save" type="button" value="确定" />');
          
	      btn.bind('click',function() { 
	    	  selectMrrFile();
	      });
	   foottd.append(btn);
	   footDiv.append(footTable);
	   

	   dataDiv.append(topDiv);
	   dataDiv.append(middleDiv);
	   dataDiv.append(footDiv);


	 $("#butt").button({label:"确定"});
	 
}


//checkbox全选或反选
function selectAll(){
	if($("#mrrAllFile").attr('checked')){
		setSelect(true);
		}else {
			setSelect(false);
			}
	

	}
function setSelect(isSelected){
	 $("[name='mrrFile']").each(function(){
			$(this).attr('checked',isSelected);
    }); 	    
}

function closeWindow(){
	  var dataDiv=$('#dataDiv');
        dataDiv.css("display","none");
	}
