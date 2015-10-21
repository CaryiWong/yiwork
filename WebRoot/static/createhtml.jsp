<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
request.setCharacterEncoding("UTF-8");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id=request.getParameter("activityid");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript">var root="<%=basePath%>";</script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<link rel="stylesheet" href="<%=basePath%>plug/kindeditor-4.1.10/themes/default/default.css" />
	<link rel="stylesheet" href="<%=basePath%>plug/kindeditor-4.1.10/plugins/code/prettify.css" />
	<script charset="utf-8" src="<%=basePath%>plug/kindeditor-4.1.10/kindeditor.js"></script>
	<script charset="utf-8" src="<%=basePath%>plug/kindeditor-4.1.10/lang/zh_CN.js"></script>
	<script charset="utf-8" src="<%=basePath%>plug/kindeditor-4.1.10/plugins/code/prettify.js"></script>	
	<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/js/ajaxfileupload.js"></script>
 
<script type="text/javascript">
KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="textarea"]', {
				themeType : 'simple',
				items : [
					'bold','italic','underline','fontsize','forecolor','link','image','quickformat','clearhtml','unlink',
					'formatblock','justifyfull','justifyright','justifycenter','justifyleft','source','media'
				],
				useContextmenu:false,
				shadowMode:true,
			    syncType:'',
				cssPath : '<%=basePath%>plug/kindeditor-4.1.10/plugins/code/prettify.css',
				formatUploadUrl:false,
				filePostName:'img',
				uploadJson : '<%=basePath%>upload/uploadimg', 
                fileManagerJson : '', 
                allowFileManager : true,
                allowImageUpload:true,
                
			});
			prettyPrint();
		});


var id="<%=id%>";
function savehuigu(){
	KindEditor.sync('#textarea'); 
	var tmp=root+"admin/activity/createhtmlpage"
 	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"texts":jQuery("#textarea").val(),"id":id},
			dataType:"json",
			success: function(json){
				alert("发布成功");
			}
		});
}

</script>

</head>
  
  <body>
	<form name="example" method="post">
		<textarea id="textarea" name="textarea" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;"></textarea>
		<br />
		<input type="button" name="button" onclick="savehuigu()" value="提交内容" /> 
	</form>
  </body>
</html>
