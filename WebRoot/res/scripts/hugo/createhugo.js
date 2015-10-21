$(document).ready(function(e){
	 $("#yixuanfengmian").hide();
	 $("#yixuanfengmian").hide();
})

var rootjs= root+"v20/";

function uploadimg(){
	$.ajaxFileUpload( 
     { 
     	//用于文件上传的服务器端请求的Action地址 
     	url:rootjs+"upload/uploadimg",
     	type:"post",//请求方法 
     	secureuri:false,//一般设置为false 
     	fileElementId:'imgfile',//文件id属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
     	success:function(json)  { 
     		var result = JSON.parse(json);
     		if(result.cord==0){
     			$("#iuserimg").val(result.data);
     			$("#yixuantouxian").show();
     			$("#hugoicon").attr("src",rootjs+"download/img?type=web&path="+result.data);
     		}else{
     			alert("上传失败");
     		}
		}
     });
}

function uploadmaximg(){
	$.ajaxFileUpload( 
     { 
     	//用于文件上传的服务器端请求的Action地址 
     	url:rootjs+"upload/uploadimg",
     	type:"post",//请求方法 
     	secureuri:false,//一般设置为false 
     	fileElementId:'maximgfile',//文件id属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
     	success:function(json)  { 
     		var result = JSON.parse(json);
     		if(result.cord==0){
     			$("#iusermaximg").val(result.data);
     			$("#yixuanfengmian").show();
     			$("#hugocover").attr("src",rootjs+"download/img?type=web&path="+result.data);
     		}else{
     			alert("上传失败");
     		}
		}
     });
}

function checkform(form){
	var len = $("input[name=groups]:checked").length;
	var name = $("#iusernickname").val();
	var job = $("#iuserjobinfo").val();
	var icon = $("#iuserimg").val();
	var cover = $("#iusermaximg").val();
	var summery = $("#iusersummery").val();
	if(len>3||len==0){
		//alert("标签为1～3个");
		//return false;
	}else if(name==""||job==""||icon==""||cover==""||summery==""){
		alert("请正确填写");
		return false;
	}else{
		return true;
	}
}

