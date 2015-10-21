<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="description" content="hugo列表">
	<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/js/ajaxfileupload.js"></script>
<script type="text/javascript">
var root="<%=basePath%>";
function delimg(obj,imgurl,id){
	$.ajax({
		url: root+"admin/display/updatespaceshow",
		type: "POST",
		async:false,
		data:{"id":id,"img":imgurl,"type":1,"flag":1},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				getspaceinfo(id);
			}else{
				alert("删除失败");
			}
		}
	});
}

function addimg(id){
	if(jQuery("#fileimgid").val()==""||jQuery("#fileimgid").val().length<1){
		alert("未选择图片");
		return;
	}
	var tmp=root+"upload/uploadimg"
 	jQuery.ajaxFileUpload({
			url: tmp,
			type: "POST",
			async:false,
			fileElementId:"fileimgid",
			dataType:"json",
			success: function(json){
		 		if(json.cord==0){
					$.ajax({
						url: root+"admin/display/updatespaceshow",
						type: "POST",
						async:false,
						data:{"id":id,"img":json.data,"type":1,"flag":0},
						dataType:"json",
						success: function(json){
							if(json.cord==0){
								getspaceinfo(id);
							}else{
								alert("新增失败");
							}
						}
					});
		 		}else{
		 			alert("新增失败");
		 		}
			}
		});
}

function addspace(){
	var tmp=root+"admin/display/createspaceshow"
	if(jQuery("#title").val()==""||jQuery("#title").val().length<1){
		alert("请填写区名");
		return;
	}
	if(jQuery("#name").val()==""||jQuery("#name").val().length<1){
		alert("请填写简介");
		return;
	}
 	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"title":jQuery("#title").val(),"name":jQuery("#name").val(),"type":1},
			dataType:"json",
			success: function(json){
				if(json.cord==0){
					alert("新增成功");
				}else{
					alert("新增失败");
				}
			}
		});
 	loaddata();
}
function loaddata(){
	var tmp=root+"website/getspaceshowlist"
	var str="";
	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"type":1},
			dataType:"json",
			success: function(json){
				if(json.data.length>0){
					for(var i=0;i<json.data.length;i++){
						str+='<div style="width: 580px; height: 40px; float: left; border: 1px solid yellow;">';
						str+='title：<input disabled="disabled" value="'+json.data[i].title+'">';
						str+='name：<input disabled="disabled" value="'+json.data[i].name+'">';
						str+='<input type="button" value="查看图" onclick="getspaceinfo(';
						str+="'"+json.data[i].id+"'";
						str+=')">';
						str+='</div>';
					}
				}
			}
		});
	str+='<div style="width: 580px; height: 40px; float: left; border: 1px solid yellow;">';
	str+='title：<input id="title">';
	str+='name：<input id="name">';
	str+='<input type="button" value="新增" onclick="addspace()">';
	str+='</div>';
	jQuery("#namelist").html(str);
}

function getspaceinfo(id){
	var tmp=root+"website/getspaceshow"
	var str="";
	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"type":1,"id":id},
			dataType:"json",
			success: function(json){
				if(json.data.images!=null&&json.data.images.length>0){
					for(var i=0;i<json.data.images.length;i++){
						str+='<div style="width: 150px;height: 150px;float: left; margin-top: 5px; margin-left: 5px; border: 1px solid red;">';
						str+='<img src="'+root+'/download/img?path='+json.data.images[i]+'" width="148" height="100" border="1">';
						str+='<input type="button" value="删除" onclick="delimg(this,';
						str+="'"+json.data.images[i]+"',";
						str+="'"+id+"'";
						str+=')">';
						str+='</div>';
					}
				}
			}
	});
	str+='<div style="width: 150px;height: 150px;float: left; margin-top: 5px; margin-left: 5px; border: 1px solid red;">';
	str+='<img src="" width="150px" height="100px">';
	str+='<input type="file" id="fileimgid" name="img"><input type="button" value="新增" onclick="addimg(';
	str+="'"+id+"'";
	str+=')">';	
	str+='</div>';
	jQuery("#showimg").html(str);
}
</script>
  </head>
  
  <body background="<%=basePath%>res/images/index_background.jpg" onload="loaddata()">
  	<div id="head">
  		<jsp:include page="/res/common/header.jsp" />
  	</div>
<div id="main">
<div id="left" style="display: inline-block;float: left;">
	  		<jsp:include page="/res/common/left.jsp" />
</div>
<div id="right" style="display: inline-block;float: left;position: absolute;width: 1210px;height: 625px;">
<div style="border: 1px solid black; height: 260px; overflow: auto;" align="center" id="namelist">
	
</div>
 <div id="showimg" style="border: 1px solid blue;height: 340; margin-top: 20px;overflow: auto;">
	
</div>

</div>
</div>
  	<div id="foot" style="padding-top: 5px;float: left;">
		<jsp:include page="/res/common/footer.jsp" />
	</div>
  </body>
</html>
