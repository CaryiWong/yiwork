<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/res/common/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新增hugo</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>res/js/ajaxfileupload.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
function uploadimg(){
	$.ajaxFileUpload( 
     { 
     	//用于文件上传的服务器端请求的Action地址 
     	url:"<%=basePath%>upload/uploadimg",
     	type:"post",//请求方法 
     	secureuri:false,//一般设置为false 
     	fileElementId:'imgfile',//文件id属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
     	success:function(json)  { 
     		var result = JSON.parse(json);
     		if(result.cord==0){
     			$("#iuserimg").val(result.data);
     			$("#hugoicon").attr("src","<%=basePath %>download/img?type=1&path="+result.data);
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
     	url:"<%=basePath%>upload/uploadimg",
     	type:"post",//请求方法 
     	secureuri:false,//一般设置为false 
     	fileElementId:'maximgfile',//文件id属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
     	success:function(json)  { 
     		var result = JSON.parse(json);
     		if(result.cord==0){
     			$("#iusermaximg").val(result.data);
     			$("#hugocover").attr("src","<%=basePath %>download/img?type=1&path="+result.data);
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
		alert("标签为1～3个");
		return false;
	}else if(name==""||job==""||icon==""||cover==""||summery==""){
		alert("请正确填写");
		return false;
	}else{
		return true;
	}
}
</script>
  </head>
  
<body background="<%=basePath%>res/images/index_background.jpg">
	<div id="head">
		<jsp:include page="/res/common/header.jsp" />
	</div>
	<div id="main">
		<div id="left" style="display: inline-block;float: left;">
			<jsp:include page="/res/common/left.jsp" />
		</div>
		<div id="right"
			style="display: inline-block;float: left;position: absolute;width: 1200px;height: 620px;background-image: url('res/images/mainbox.png');">
			<form name="mainform" id="mainform" action="admin/display/savehugo"
				method="post" onsubmit="return checkform(this);">
				<div style="padding-left: 150px;padding-top: 20px;">
					<label>标签</label>
					<c:forEach items="${labels }" var="label" varStatus="vs">
						<c:if test="${vs.index mod 10 eq 0 }"><br></c:if>
						<c:set var="la" value="${label.id}"></c:set>
						<input id="groups" name="groups" type="checkbox"  value="${label.id}" <c:if test="${fn:contains(labelList,la) }">checked="checked"</c:if> />${label.zname }
					</c:forEach>
				</div>
				<table align="center" >
					<tr>
						<td><label>名字<font color="red">*</font></label></td>
						<td><input id="iusernickname" name="iusernickname"/></td>
						<td><label>职业<font color="red">*</font></label></td>
						<td><input id="iuserjobinfo" name="iuserjobinfo"></td>
					</tr>
					<tr style="height: 200px;">
						<td>
							<label>头像<font color="red">*</font></label>
							<input type="file" id="imgfile" name="img" onchange="uploadimg();" style="width: 150px;"/>
							<input type="hidden" name="iuserimg" id="iuserimg">
						</td>
						<td>
							<img alt="hugo头像" id="hugoicon" src="" style="width: 180px;height: 180px;">
						</td>
						<td>
							<label>封面<font color="red">*</font></label>
							<input type="file" id="maximgfile" name="img" onchange="uploadmaximg();" style="width: 150px;"/>
							<input type="hidden" name="iusermaximg" id="iusermaximg">
						</td>
						<td>
							<img alt="hugo封面" id="hugocover" src="" style="width: 180px;height: 180px;">
						</td>
					</tr>
					<tr>
						<td><label>简介<font color="red">*</font></label></td>
						<td colspan="3">
							<textarea rows="10" cols="100" id="iusersummery" name="iusersummery"></textarea>
						</td>
					</tr>
					<tr align="center">
						<td colspan="2"><input type="submit" value="保存" /></td>
						<td colspan="2"><input type="button" value="取消" onclick="history.back(-1);" /></td>
					</tr>
					<c:if test="${tips ne null }">
						<tr align="center">
							<td colspan="4">${tips }</td>
						</tr>
					</c:if>
				</table>
			</form>
		</div>
	</div>
	<div id="foot" style="padding-top: 5px;float: left;">
		<jsp:include page="/res/common/footer.jsp" />
	</div>
</body>
</html>
