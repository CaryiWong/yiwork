$(document).ready(function(e){
	 
})

//var rootjs= "http://www.yi-gather.com/"+"v20/";

 

//var rootjs= "http://127.0.0.1:8080/jumpingsale/web/downloadimg?type=web&path=f_img4e0bd4328bae4119b7bb1434009632384";  下载地址


function uploadtx(id){
	$.ajaxFileUpload( 
		     { 
		     	//用于文件上传的服务器端请求的Action地址 
		     	url:root+"/web/upload",
		     	type:"post",//请求方法 
		     	secureuri:false,//一般设置为false 
		     	fileElementId:id,//文件id属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
		     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
		     	success:function(json)  { 
		     		var result=JSON.parse(json);
		     		if(result.cord==0){
		     			//$("#teamimageId").text(root+"web/downloadimg?type=web&path="+result.data);
		     			//$("#teamimageName").attr("src",root+"web/downloadimg?type=web&path="+result.data);
		     			$("#"+id+"Id").text(root+"web/downloadimg?type=web&path="+result.data);
		     			$("#"+id+"Name").attr("src",root+"web/downloadimg?type=web&path="+result.data);
		     			alert("图片上传成功，必须点击保存才能更新!")
		     		}else{
		     			alert(result.msg);
		     		}
		     	
				}
		     });
}


function getPhotoSize(obj,type){
    photoExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名

    if(photoExt!='.jpg' && photoExt!='.png' && photoExt!='.bmp'){
        alert("请上传后缀名为jpg/png/bmp的照片!");
        return false;
    }
    var fileSize = 0;
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;            
    if (isIE && !obj.files) {          
         var filePath = obj.value;            
         var fileSystem = new ActiveXObject("Scripting.FileSystemObject");   
         var file = fileSystem.GetFile (filePath);               
         fileSize = file.Size;         
    }else {  
         fileSize = obj.files[0].size;     
    } 
    fileSize=Math.round(fileSize/1024*100)/100; //单位为KB
     
    if(fileSize>=100){
        alert("文件大小"+fileSize+"kb,照片最大尺寸为100KB，请重新上传!");
        return false;
    }else{
    	switch (type){
    	case 1:  //产品图片
    		uploadtx('proimage');break;
    	case 2:  //产品描述图片
    		uploadtx('prointroduceimg');break;
    	case 3:  //描述图片
    		uploadtx('teamtroduceimg');break;
    	}
    }
}


 