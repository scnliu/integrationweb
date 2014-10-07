function Uploader(id,options){
    this.id=id;
    var self_=this;
    this.dlgId=id+"_files";
    this.dlg=null;
    this.params=options.params||{};
    this.options=options;
//    this.dlg_options={width:540,height:340,
//	    			 buttons:{
//			    				ok:{iconCls:'icon-ok',
//			    					value:"确定上传",
//		    						click:function(){
////		    							$(this).button('disable');
//		    							$("#"+self_.id).uploadifySettings('scriptData',self_.params);
//		    							$("#"+self_.id).uploadifyUpload();
//		    							
//		    						}},
//			    				cancel:{close:true,
//			    						iconCls:'icon-cancel',
//			    						value:'取消',
//			    						click:function(){
//			    							$("#"+self_.id).uploadifyClearQueue();
//			    						}
//			    						}
//	    					}
//    				 };
//    var dlg=$('<div id="'+this.dlgId+'" title="文件上传"></div>').appendTo(document.body);
    
//    $("#"+id).button();
//    $("#"+id).click(function(){
//    	alert(234);
//    	document.getElementById(self_.id + 'Uploader').selectFiles();
//    	return false;
//    });
    $('#'+this.dlgId).addClass("uploadifyFilesContent");
    var split=options.url.split('?');
    var url=split[0];
    var action=split[1];
    this.params=$.deparam(action);
     var setting =$.extend({
		'uploader' :_appContext+'/js/jquery/plugins/uploader/images/uploadify.swf',
		'script' : url,
		'cancelImg' : _appContext+'/js/jquery/plugins/uploader/images/error_fuck.png',
		'okImg' : _appContext+'/js/jquery/plugins/uploader/images/ok.png',
		'buttonImg': _appContext+'/js/jquery/plugins/uploader/images/btn.png',
		'buttonImg2': _appContext+'/js/jquery/plugins/uploader/images/btn2.png',
		'itemImg':_appContext+'/js/jquery/plugins/uploader/images/attach.png',
        'width': 97,
        'height': 26,
		'queueID' : this.dlgId,
		'folder' : 'uploads',
		'auto' : false,
		'multi' : true,
//		'fileExt' : '*.jpg;*.gif;*.png',
//		'fileDesc' : 'Image Files (.JPG, .GIF, .PNG)',
//		'sizeLimit':1024*1024,
		'queueSizeLimit' : 300,
		'simUploadLimit' : 1,
		'hideButton':false,
		'method':'Get',
		'onComplete':function(event,queueID,fileObj,response,data){
						return false;
						},
		'buttonText' : 'SELECT FILES',
//		'checkScript':'<%=basePath %>upload.do?method=check',
		'onError':function(a,b,c,d){
			 if (d.status == 404)
                 alert('Could not find upload script. Use a path relative to: '+'<?= getcwd() ?>');
          	else if (d.type === "HTTP")
                 alert('error '+d.type+": "+d.status);
          	else if (d.type ==="File Size")
                 alert(c.name+' 文件长度: '+Math.round(c.size/1024)+'KB');
         	 else
                 alert('error '+d.type+": "+d.text);
		},
		  onSelectOnce : function(event,data) {
			  $('#'+self_.id +'_progress').hide();
		    },
		    onAllComplete:function(event,data) {
		    	self_.success();
		    }
	},options);
     $("#"+id).uploadify(setting);
    $("#"+id).addClass('uploadifyBtn');
//	this.dlg=new Dialog(this.dlgId,this.dlg_options);
    if(typeof Uploader.initialized=="undefined"){
        Uploader.prototype.success=function(){
        	 $('#'+self_.dlgId).html('上传完成');
        };
        Uploader.prototype.setParams=function(params){
        	self_.params=$.extend(self_.params,params);
        };
        Uploader.prototype.hide=function(){
        	if(this.dlg==null)return;
        	this.dlg.hide();
        };
        Uploader.prototype.upload=function(){
        	$("#"+self_.id).uploadifySettings('scriptData',self_.params);
			$("#"+self_.id).uploadifyUpload();
        };
        /**
         * 隐藏进度条
         */
        Uploader.prototype.hideProgress=function(text){
        	$('#'+self_.id +'_progress').hide();
        };
        /**
         * 添加进度条
         */
        Uploader.prototype.addProgress=function(text){
        		if(!this.existsProgress){
        			$("#"+self_.dlgId).append('<div id="' + self_.id +'_progress" class="uploadifyQueueItem">'+
						'<span class="fileName">' + text + '</span><span class="percentage"></span>'+
						'<div class="uploadifyProgress">'+
							'<div id="' + self_.id+ '_Info_ProgressBar" class="uploadifyProgressBar"><!--Progress Bar--></div>'+
						'</div></div>');
        			this.existsProgress=true;
        		}
        		$('#'+self_.id +'_progress').show();
        };
        /**
         * 设置进度条百分比
         */
        Uploader.prototype.progress=function(size){
        	if(size<100){
	        	jQuery("#" + self_.id+ "_Info_ProgressBar").animate({'width': size + '%'},200,function() {
					if (data.percentage == 100) {
						jQuery("#" + jQuery(event.target).attr('id') + ID).find('img').attr('src',settings.okImg);
						jQuery(this).closest('.uploadifyQueueItem').remove();
						
					}
				});
				var displayData = ' - ' + size + '%';
				jQuery("#" + self_.id +"_progress").find('.percentage').text(displayData);
        	}
        };
        Uploader.prototype.getMovieElement = function () {
        	if (this.movieElement == undefined) {
        		this.movieElement = document.getElementById(this.id+'Uploader');
        	}
        	if (this.movieElement === null) {
        		throw "Could not find Flash element";
        	}
        	return this.movieElement;
        };
       
        Uploader.initialized = true;
    }
};

