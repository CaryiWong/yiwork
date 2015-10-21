<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
String htmlData = request.getParameter("content1") != null ? request.getParameter("content1") : "";
String path = request.getContextPath();
String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8" />
	<title>KindEditor JSP</title>
	<link rel="stylesheet" href="../themes/default/default.css" />
	<link rel="stylesheet" href="../plugins/code/prettify.css" />
	<script charset="utf-8" src="../kindeditor.js"></script>
	<script charset="utf-8" src="../lang/zh_CN.js"></script>
	<script charset="utf-8" src="../plugins/code/prettify.js"></script>
	<script>
		KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="content1"]', {
				themeType : 'simple',
				items : [
					'bold','italic','underline','fontsize','forecolor','emoticons','link','multiimage','image'
				],
				cssPath : '../plugins/code/prettify.css',
				filePostName : 'upload',
				uploadJson : '<%=basePath%>/api/filesUpload',
				allowFileManager : true,
				extraFileUploadParams : {  //上传图片时，支持添加别的参数一并传到服务器。
					"comment.text":"1234567",
					"comment.actid":"20e1a04825fb85079f5b9c60c0288156activity1390641960976",
					"comment.uid":"20e1a04825fb85079f5b9c60c0288156",
					crcvl:"yqjh"
				},
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						//self.sync();
						alert(document.getElementById("content1").value);
						document.forms['example'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						//self.sync();
						alert(document.getElementById("content1").value);
						document.forms['example'].submit();
					});
				}
			});
			prettyPrint();
		});
		
		
	 
	</script>
</head>
<body>
	<%=htmlData%>
	<form name="example" method="post" action="">
		<textarea name="content1" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;"><%=htmlspecialchars(htmlData)%></textarea>
		<br />
		<input type="submit" onclick="javascript:alert(document.getElementById('content1').value)" name="button" value="提交内容" /> (提交快捷键: Ctrl + Enter)
	</form>
</body>
</html>
<%!
private String htmlspecialchars(String str) {
	str = str.replaceAll("&", "&amp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\"", "&quot;");
	return str;
}
%>