function doTab(id,name,url,type){
	if(type==1){
	   parent.makeTab(id,name,url);
	}else if(type==2){
		openWin(url);
	}
}

function doTab_2(id,name,url,type){
	if(type==1){
		top.makeTab(id,name,url);
	}else if(type==2){
		openWin(url);
	}
}
/**
 * 打开窗口
 */
function openWin(link, width, height, name, scrollbars, resizable , toolbar, location,menubar){
  var width = width ? width : 800 ;
  var height = height ? height : 600 ;
  var name = name ? name : "OpenWin";
  var scrollbars = scrollbars ? scrollbars : 'yes';
  var resizable = resizable ? resizable : 'yes';
  var toolbar = toolbar ? toolbar : 'no';
  var location = location ? location : 'no';
  var menubar = menubar ? menubar : 'no';
  var xposition = 0 ;
  var yposition = 0 ;
  
  if ( ( parseInt ( navigator.appVersion ) >= 4 ) ) {
    xposition = ( screen.width - width ) / 2;
    yposition = ( screen.height - height ) / 2;
  }

  var newWindow = window.open ( link , name ," scrollbars = " + scrollbars + "  , resizable = " + resizable + " , width = " + width + " , "
              + " height = " + height + " , left = " + xposition + ", top = " + yposition + " , "
              + " toolbar = " + toolbar + " , location = " + location + " , menubar = " + menubar );
  newWindow.focus();
}


// 关闭窗口
function closeWin(){
	window.close();
}

/**
 * 是否是正确的email
 */
function isEmail(email){
	var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
	if (!pattern.test(email)){
		return false;
	}
  	return true;
}

// 联系电话
function isPhone(phone){
	var pattern = /(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/
	// /^(\d){6,30}$/;
  	if (!pattern.test(phone)){
  		return false;
  	}
  	return true;
}

// 手机
function isMoble(mobleNumber){
	var pattern = /^[1][3|5](\d){9}$/;
  	if (!pattern.test(mobleNumber)){
  		return false;
  	}
  	return true;
}

// 邮编
function isPostCode(code){
	var pattern = /^(\d){6}$/;
	if (!pattern.test(code)){
		return false;
	}
  	return true;
}

// 创建弹出窗口
function createWindow(width,height,title,content,drag,showbg){
	 $("#windown-box").remove();     // 清除内容
	 // $("#windownbg").remove();
	 var windownbg=$('<div id="windownbg"></div>');
	     windownbg.css('height',$(document).height());
	     windownbg.addClass('windownbg');
	    
	 var winbox=$('<div id="windown-box"></div>');
	 
	 var wintitle=$('<div id="windown-title"></div>');
	 
	 var titleText=$('<div id="win_text"></div>');
	
	    titleText.html(title);
 
	 var winclose=$('<span id="windown-close"></span>');
	 winclose.attr({'title':'关闭'});
	 wintitle.append(titleText);
	 wintitle.append(winclose);
	 
    var wincontent=$('<div id="windown-content" class="no_drag"></div>');
    
    var contentType = content.substring(0,content.indexOf(":"));
	 var content = content.substring(content.indexOf(":")+1,content.length);
    
	 switch(contentType) {
	    case "text":
	    	 wincontent.html(content);
	      break;
	   case "id":
           wincontent.html($('#'+content).html());
       break;
       
	  case "url":
		    
		    wincontent.load(content,'',function(){});
		break;
	  case "src":
		 wincontent.append("<iframe  src='"+content+"' width='100%' height='"+parseInt(height)+"px' frameborder='0'></iframe>");
	}
       
    winbox.append(wintitle);
    winbox.append(wincontent);
    
    $("body").append(windownbg);
    $("body").append(winbox);
  
    
   if(showbg) {$("#windownbg").show();}else {$("#windownbg").remove();};
    
   $("#windownbg").animate({opacity:"0.5"},"normal");// 设置透明度
	$("#windown-box").show();
	
	$("#windown-title").css({width:(parseInt(width)+10)+"px"});
	$("#windown-content").css({width:width+"px",height:height+"px"});
	
	var	cw = document.documentElement.clientWidth,ch = document.documentElement.clientHeight,est = document.documentElement.scrollTop; 
	var _version = $.browser.version;
	if ( _version == 6.0 ) {
		$("#windown-box").css({left:"50%",top:(parseInt((ch)/2)+est)+"px",marginTop: -((parseInt(height)+53)/2)+"px",marginLeft:-((parseInt(width)+32)/2)+"px",zIndex: "999999"});
	}else {
		$("#windown-box").css({left:"50%",top:"50%",marginTop:-((parseInt(height)+53)/2)+"px",marginLeft:-((parseInt(width)+32)/2)+"px",zIndex: "999999"});
	 };
	
	  $('#windown-box').draggable({
		// containment: 'parent',
		cancel:".no_drag",
		start: function() {
			
		},
		stop: function() {
			
		}
	});
	
 $("#windown-close").click(function() {
			$("#windownbg").remove();
			$("#windown-box").fadeOut("slow",function(){$(this).remove();});
			
		});
}
//////////////////////////////////////////////////正在加载显示和隐藏////////////////////////////
/**
 * 添加遮罩层到body
 */
function addLoadingDiv()
{
	$("body").append($(
			"<div id='win_box'>"+
			"<div id='titleDiv'>"+
			"<img src='"+_appContext+"/images/desktop/loading3_1.gif'/>"+
			"<span id='title'></span>"+
			"</div>"+
			"</div>	"
	));
}
/**
 * 显示遮罩层
 * title:加载图片后面显示的文字<br/>
 * _left:遮罩开始x位置<br/>
 * _top:遮罩开始y位置<br/>
 * _width:遮罩宽度 默认为0则宽度为浏览器或框架宽度<br/>
 * _height:遮罩高度 默认为0则高度为浏览器或框架高度<br/>
 * 调用showLoading("正在加载",0,0,0,0)<br/>
 */
function showLoading(title,_left,_top,_width,_height){  	    	 
	// var title = "正在到处数据，请稍候...";
	$("#win_box").remove();
	addLoadingDiv();
	if(_height==0)
		{
		_height=$(document).height()-_top;
		}
	if(_width==0)
	{
		_width=$(document).width()-_left;
	}
	 $("#windownbg").remove(); 
	 var windownbg=$('<div id="windownbg"></div>');
    windownbg.css({
    	"display": "none",
		"position": "absolute",
		width:_width,
		height: _height,
		"background-color": "black",/*body-bg*/
		top: _top,
		left: _left,
		"filter":"alpha(opacity=0)",
		"opacity":"0",
		"z-index": "100"
    });
    $('#win_box').css({
    	top:_top-7+document.documentElement.clientHeight/2 , //6为字的高度
    	left:_left-75+_width/2, //75*2 =150 为字的宽度
    	position:'absolute',
    	"z-index": '200',
    	display: 'none',
    	width:300,
	    height:50,
	    color: 'black'
    	}); 
    //$('#title').html(title);
    $("body").append(windownbg);
    $('#win_box').show();
    $("#windownbg").show();		         
    $("#windownbg").animate({opacity:"0.3"},"normal");//设置透明度
}
/**
 * 隐藏遮罩层
 */
function hideLoading(){ 
	$("#windownbg").remove();
	$('#win_box').css('display',"none");
}

