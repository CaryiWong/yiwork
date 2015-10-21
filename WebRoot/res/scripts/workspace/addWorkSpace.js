$(document).ready(function(e){
	/* loadByType(); //根据类型设置表单的隐藏
	 $("select[name='demandstype']").change(function(){var ischeckType=$("#ischeckType").val(); if(ischeckType!=0){return;}  $("#kcmType").val($("select[name='demandstype']").val());loadByType()})
	 $("#videoType").change(function(){
			loadByType()
	  })*/
	
})

 
function setonsale(id,onsale,o){
	var actid = id;
	var submitdata = {
		type:1,
		id:actid,
		onsale:onsale
	};
	$.ajax({
     type: "post",
     url: root+"v20/admin/article/setonsale",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	alert(result.msg);
     	if(result.result=="s"){
     		if(onsale==1){
     			$(o).html("上架");
     			$(o).attr("onclick","setonsale('"+id+"',0,this);");
     		}else if(onsale==0){
     			$(o).html("下架");
     			$(o).attr("onclick","setonsale('"+id+"',1,this);");
     		}
     	}
     }
    });
}


function sendFile()
{
  	$.ajaxFileUpload( 
     { 
    	url:root+"v20/upload/uploadimg",//用于文件上传的服务器端请求的Action地址 
     	type:"post",//请求方法 
     	secureuri:false,//一般设置为false 
     	fileElementId:'coverImg',//文件id属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
     	success:function(json)  { 
    	 	 	   json=JSON.parse(json)  //手动转json
    	 	 	   	//successNum=successNum+1;
    	 	 	   if(json.cord==0){
    	 	 		   $("#spacelogo").val(json.data);
    	 	 		   $("#coversrc").attr("src",root+"v20/download/img?type=web&path="+json.data);
    	 	 	   }
		 } 
     }); 
}







