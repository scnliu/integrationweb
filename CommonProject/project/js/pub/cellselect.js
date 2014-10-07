      var listArray=new Array();
		 var selectedIndex=-1;
		 var temps="";  
		 
		/* $(document).ready(function(){
			  init(10,2,1.4);
		  });*/
		  function init(topSize,leftSize,widthSize){
			//	selectedIndex=-1;
			  var objInput=document.getElementById('_cellid');
			  var objDiv=document.getElementById('_divId');
			  $(objInput).keyup(function(){
				  
				  checkKeyCode(objInput,objDiv);
				  
			  });
			  $(objInput).blur(function (){
				        if(objInput==null){
					         event.srcElement;
					        }
				        if(objDiv.contains(document.activeElement)){
					          objInput.focus();
					        }else{
				               objDiv.style.display='none';
					        }
					      
					   });
				//设置div显示位置
	           objDiv.style.top=getAbsoluteTop(objInput)+getAbsoluteHeight(objInput)+topSize+"px";
	           objDiv.style.left=getAbsoluteLeft(objInput)-leftSize+"px"; 
	           objDiv.style.width=getAbsoluteWidth(objInput)-widthSize+"px";
	         
			 }

		  //添加键盘控制
			function checkKeyCode(objInput,objDiv){
				var divChildrenLen=objDiv.children.length;
		         var ie=(document.all)?true:false;
				 if(ie){
					 var keyCode=event.keyCode;
					 if(keyCode==40||keyCode==38){
						 var isUp=false;
						 if(keyCode==40) {
							 isUp=true; 
						  }
						  if(divChildrenLen>1){
						
						   chageSelection(isUp,objDiv);
						   divScroll(objDiv);
						  }
						 }else if(keyCode==13){
							  outSelection(selectedIndex,objDiv);
					    }else{
						   
					    	divShow(objInput,objDiv);
						    }
					 }else{
						 divShow(objInput,objDiv);
						}
				}

			function divShow(objInput,objDiv){
				   
				objDiv.innerHTML="";
				objDiv.style.height='auto';
				if(objInput.value.replace(/(^\s*)|(\s*$)/g, "")!=""){
				
				  $(objInput).attr({'value':objInput.value.toUpperCase()});
				 
				   jQuery.ajax({
					     type:"POST",
					     url:_appContext+"/views/ul/ericsson/faultCell.do?action=queryCells",
					     data:{"cellid":objInput.value},
					     dataType:"json",						 
						 success:function(data){
					   
					       if(data.length>0){
					    	 
		 	 		    	  objDiv.style.display='';
				    	      for(var i=0;i<data.length;i++){
					    	   // listArray[i]=data[i];
					    	  
				    	        objDiv.innerHTML +="<div onmouseover=\"this.className='sman_selectedStyle'\" onmouseout=\"this.className=''\" onmousedown=\"getValue('"+ data[i]+"')\">" + data[i] + "</div>"    
					    	    }
					    	
				    	      if(objDiv.offsetHeight>200){
				    	    	 
							    	objDiv.style.height=200+"px";
								    }else{
								    objDiv.style.height='auto';
									 }
		 	 		             }
			  					
			 				}
					     });
				
				  }
				}

		   function getValue(data){
			    $('#_cellid').attr({'value':data});
			  }
	       
	       function chageSelection(isUp,objDiv){
	            if(isUp){
	                selectedIndex++;
	                }else{
	                   selectedIndex--;
	                }
	              var maxIndex=objDiv.children.length-1;
	              if(selectedIndex<0){
	            	  selectedIndex=maxIndex;
	                  }
	              if(selectedIndex>maxIndex){
	            	  selectedIndex=0;
	                  }
	              for(var tmp=0;tmp<=maxIndex;tmp++){
	                   if(tmp==selectedIndex){
	                       objDiv.children[tmp].className='sman_selectedStyle';
	                       }else{
	                        objDiv.children[tmp].className='';
	                     }
	               }
	              $('#_cellid').attr({'value':objDiv.children[selectedIndex].innerText});
	       }
	       
	     function outSelection(index,objDiv){
	    	 if(index>=0){
	    	 $('#_cellid').attr({"value":objDiv.children[index].innerText});
	    	// objDiv.innerHTML="";
	    	 objDiv.style.display='none';
	    	 }
	      }

	      //divScroll
	      function divScroll(objDiv){
	    	  if ( objDiv==null||objDiv.selectedItemIndex==-1||objDiv.children.length==0) return ; 

	    	  if((objDiv.children[selectedIndex].offsetTop<objDiv.scrollTop)||(objDiv.children[selectedIndex].offsetTop>objDiv.scrollTop+170)) 
	    	   //objDiv.children[selectedIndex].scrollIntoView(); 
		    	 objDiv.scrollTop=objDiv.children[selectedIndex].offsetTop-60; 
		      }
	     
	      //设置objDiv位置
	      function getAbsoluteHeight(objInput){
	        return objInput.offsetHeight
	       }
	     function getAbsoluteWidth(objInput){
			    return objInput.offsetWidth;
		    }
		 function getAbsoluteLeft(objInput){
	    	 var mendingLeft = objInput.offsetLeft;
	         if(objInput!=null&&objInput.offsetParent!=null&& objInput.offsetParent.tagName!= "BODY" ){
	             mendingLeft += objInput.offsetParent.offsetLeft;
	         }
	        return mendingLeft ;
		  }
	     function getAbsoluteTop(objInput){
	    	 var mendingTop = objInput.offsetTop;
	         if(objInput!= null && objInput.offsetParent != null && objInput.offsetParent.tagName != "BODY" ){
	             mendingTop += objInput.offsetParent.offsetTop;
	         }
	        return mendingTop ;
	     }