$(document).ready(function(e){
	 loadByType(); //根据类型设置表单的隐藏
	 $("select[name='demandstype']").change(function(){var ischeckType=$("#ischeckType").val(); if(ischeckType!=0){return;}  $("#kcmType").val($("select[name='demandstype']").val());loadByType()})
	 $("#videoType").change(function(){
			loadByType()
	  })
	
})

/**
 * 0寻人，1视频，2活动，3其他
 */
function loadByType()
{
	var ischeckType=$("#ischeckType").val();
	var demandType=$("#kcmType").val();
	$("#videoDiv").show();$("#huodongxingshi").show();$("#mianxiangrenqun").show();$("#joinNum").show();$("#plantime").show();$("#videoImg").show();$("#findsDiv").show();$("#videoUrl").show();
	if(demandType==0)
	{
		$("#videoImg").hide();$("#videoDiv").hide();$("#huodongxingshi").hide();$("#mianxiangrenqun").hide();$("#joinNum").hide();$("#plantime").hide();$("#videoUrl").hide();
	}else if(demandType==1)
	{
		$("#huodongxingshi").hide();$("#mianxiangrenqun").hide();$("#joinNum").hide();$("#plantime").hide();$("#findsDiv").hide();$("#videoUrl").hide();$("#videoImg").hide()
		if($("#videoType").val()==1398221464213)
		{
				$("#videoUrl").show();$("#videoImg").show()
		}
		
	}else if(demandType==2){
		$("#videoDiv").hide();$("#videoImg").hide();$("#findsDiv").hide();$("#videoUrl").hide();
	}
}

/**
 * 审核活动，如果是视频的话，需要传文件名
 */
function verify(){
	var id = $("#id").val();
	var submitdata = {
		id:id,
	};
	$.ajax({
     type: "post",
     url: root+"admin/demand/verifydemand",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord!=0){
     		alert("审核失败");
     	}else{
     		alert("审核成功");
     		window.location.href=root+"admin/demand/demandlist?pageSize=12";
     	}
     }
    });
	
}



function sendFile()
{
  	$.ajaxFileUpload( 
     { 
    	url:root+"/upload/uploadimg",//用于文件上传的服务器端请求的Action地址 
     	type:"post",//请求方法 
     	secureuri:false,//一般设置为false 
     	fileElementId:'coverImg',//文件id属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
     	success:function(json)  { 
    	 	 	   json=JSON.parse(json)  //手动转json
    	 	 	   	//successNum=successNum+1;
    	 	 	   if(json.cord==0)$("#cover").val(json.data);
    	 	 	   	// 	alert("发表评论成功，上传成功"+($(".activity-comment-file").length-1)+"张图片");
					//window.location.reload();
					//alert(json.cord);//从服务器返回的json中取出message中的数据,其中message为在struts2中定义的成员变量	
					//$.each(msg,function(k,y){ 
					//$("#id").append("&amp;lt;option  &amp;gt;"+y+" &amp;lt;/option&amp;gt;"); 
			//}); 
		 } 
     }); 
}







