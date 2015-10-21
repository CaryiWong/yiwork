<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/res/common/taglib.jsp"%>
<%@ page import="cn.yi.gather.v20.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String webBasePath=basePath+"v20/";
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>标签管理</title>
<script type="text/javascript">
function showlabels(){
	document.getElementById("id").value='';
	document.getElementById("zname").value='';
	document.getElementById("pid").value='';
	document.showfrom.submit();
}

function showLabelsBy(pid){
	document.getElementById("pid").value=pid;
	document.getElementById("id").value='';
	document.getElementById("zname").value='';
	document.showfrom.submit();
}

function showUserBy(id,zname) {
	document.getElementById("id").value=id;
	document.getElementById("zname").value=zname;
	document.showfrom.submit();
}

function updateLabelBy(){
	var zname= document.getElementById("zname").value;
	var str= window.prompt("正在执行将标签【"+zname+"】修改名字操作。请输入新标签名字:");
	if(str!=null&&str.length>0){
		document.getElementById("labzname").value=str;
		document.uplables.submit();
	}else{
		event.preventDefault();
	}
}

function delLabelBy(){
	var zname= document.getElementById("zname").value;
	var str="是否确定删除标签【"+zname+"】？删除后所有关联用户自动取消该标签!";
	if(window.confirm(str)){
		document.delables.submit();
	}else{
		event.preventDefault();
	}
	
}

function updateUserLabelBy(n){
	var zname= document.getElementById("zname").value;
	if(n==0){
		var str="是否确定修改用户标签【"+zname+"】为【其他】？";
		if(window.confirm(str)){
			document.getElementById("ifqt").value=1;
			document.upallform.submit();
		}else{
			event.preventDefault();
		}
	}else{
		var str="是否确定取消用户标签【"+zname+"】";
		if(window.confirm(str)){
			document.getElementById("ifqt").value=0;
			document.upallform.submit();
		}else{
			event.preventDefault();
		}
	}
}

function updateOneUserLabel(obj,uid){
	document.getElementById("uid").value=uid;
	document.getElementById("lid").value=obj.value;
	document.uponefrom.submit();
}

function showaddlabel(num){
	document.getElementById("ifaddlabel").style.display="";
	if(num==1){
		document.getElementById("npid").value="";
	}else{
		var npid=document.getElementById("npid").value;
		if(npid==null||npid==""){
			document.getElementById("npid").value=document.getElementById("pid").value;
		}
	}
	event.preventDefault();
}

function closeaddlabel(){
	document.getElementById("ifaddlabel").style.display="none";
	event.preventDefault();
}

function addlabels(){
	var zname= document.getElementById("nzname").value;
	if(zname!=null&&zname.length>1){
		document.addlabelform.submit();
	}else{
		alert("标签不能为空");
		event.preventDefault();
	}
}
</script>




</head>
  
  <body>
  <div style=" height: 800px">
   <form id="showfrom" name="showfrom" action="<%=webBasePath%>admin/labels/labels" method="post">
   <div>
   <select name="labeltype" onchange="showlabels()">
		   <option value="job" <c:if test="${labeltype eq 'job' }">selected="selected"</c:if>>职业标签</option>
		   <option value="focus" <c:if test="${labeltype eq 'focus' }">selected="selected"</c:if>>关注领域</option>
		   <option value="favourite" <c:if test="${labeltype eq 'favourite' }">selected="selected"</c:if>>兴趣爱好</option>
   </select>
   <label style="color: red;">注意：所有修改、删除操作不可逆，请三思而行！</label>
    <input type="hidden" name="pid" id="pid" value="${pid}">
   	<input type="hidden" name="id" id="id" value="${id}">
   	<input type="hidden" name="zname" id="zname" value="${zname}">
    </div>
    <hr>
    <!-- 关注 标签  不分级 -->
    
   	<c:if test="${labeltype eq 'focus' }">
   	<div style="height: auto;min-height: 80px">
   		<c:forEach items="${list}" var="lb" varStatus="vs">
   			<c:if test="${lb.id eq id }">
	   		<button type="button" style="margin-top: 10px; margin-right: 20px;background-color: red" onclick="showUserBy(${lb.id},'${lb.zname}')">${lb.zname}</button>
	   		</c:if>
	   		<c:if test="${lb.id ne id }">
	   		<button type="button" style="margin-top: 10px; margin-right: 20px;" onclick="showUserBy(${lb.id},'${lb.zname}')">${lb.zname}</button>
	   		</c:if>
   		</c:forEach>
   		<button type="button" style="background-color: #fff; margin-left: 50px; margin-top: 10px;" onclick="showaddlabel(1)"><lable style="color: red;"> 新增一个标签</lable></button>
   		</div>
   	</c:if>
   	
   	<!-- 爱好 标签  分2级  该区域为1级标签 -->
   	<c:if test="${labeltype eq 'favourite' }">
   		<div style="min-height: 80px; height: auto;">
   		<c:forEach items="${list}" var="lb" varStatus="vs">
	   		<c:if test="${lb.pid eq '0' }">
	   			<c:if test="${lb.id eq pid }">
	   			<button type="button" style=" margin-top: 10px; margin-right: 20px; background-color: red;" onclick="showLabelsBy(${lb.id})">${lb.zname}</button>
	   			</c:if>
	   			<c:if test="${lb.id ne pid }">
	   			<button type="button" style=" margin-top: 10px; margin-right: 20px;" onclick="showLabelsBy(${lb.id})">${lb.zname}</button>
	   			</c:if>
	   		</c:if>
   		</c:forEach>
   		<button type="button" style="background-color: #fff; margin-left: 50px; margin-top: 10px;" onclick="showaddlabel(1)"><lable style="color: red;"> 新增一个标签</lable></button>
   		</div>
   	</c:if>
   	
    <!-- 职业 标签  分2级  该区域为1级标签 -->
   	<c:if test="${labeltype eq 'job' }">
   		<div style="min-height: 80px; height: auto;">
   		<c:forEach items="${list}" var="lb" varStatus="vs">
   		<c:if test="${lb.pid eq '0' }">
   			<c:if test="${lb.id eq pid }">
   			<button type="button" style=" margin-top: 10px; margin-right: 20px; background-color: red;" onclick="showLabelsBy(${lb.id})">${lb.zname}</button>
   			</c:if>
   			<c:if test="${lb.id ne pid }">
   			<button type="button" style=" margin-top: 10px; margin-right: 20px;" onclick="showLabelsBy(${lb.id})">${lb.zname}</button>
   			</c:if>
   		</c:if>
   		</c:forEach>
   		<button type="button" style="background-color: #fff; margin-left: 50px;margin-top: 10px" onclick="showaddlabel(1)"><lable style="color: red;"> 新增一个标签</lable></button>
   		</div>
   	</c:if>
   	
   	<!-- 爱好/职业 标签  分2级  该区域为2级标签 -->
	<c:if test="${pid ne '0' and pid ne null}">
		<div style="min-height: 80px; height: auto;">
		<hr>
   		<c:forEach items="${list}" var="lb" varStatus="vs">
   		<c:if test="${lb.pid eq pid }">
   			<c:if test="${lb.id eq id }">
   			<button type="button" style="background-color: red;" onclick="showUserBy(${lb.id},'${lb.zname}')">${lb.zname}</button>
   			</c:if>
   			<c:if test="${lb.id ne id }">
   			<button type="button" onclick="showUserBy(${lb.id},'${lb.zname}')">${lb.zname}</button>
   			</c:if>
   		</c:if>
   		</c:forEach>
   		<button type="button" style="background-color: #fff; margin-left: 50px;" onclick="showaddlabel(2)"><lable style="color: red;"> 新增一个2级标签</lable></button>
   		</div>
   	</c:if>
   	</form>
   	<div style="text-align: center; display: none;margin-top: 20px;min-height: 200px; height: auto;" id="ifaddlabel">
	   		<form id="addlabelform" name="addlabelform" action="<%=webBasePath%>admin/labels/addlabels"  method="post">
		   		<input type="hidden" name="ntype" id="ntype" value="${labeltype}">
		   		<input type="hidden" name="npid" id="npid" value="${pid}">
		   		<table border="1" style="margin: 0 auto;">
			   		<tr>
			   		<td>中文名：</td><td><input type="text" id="nzname" name="nzname"> </td>
			   		</tr>
			   		<tr>
			   		<td>英文名：</td><td><input type="text" id="nename" name="nename"></td>
			   		</tr>
			   		<tr>
			   		<td colspan="2"><button type="button" onclick="addlabels()">保存标签</button>
			   		<button type="button" onclick="closeaddlabel()">取消新增</button></td>
			   		</tr>
		   		</table>
		   	</form>
   	</div>
   	
   	<div style="min-height: 200px; height: auto; margin-top: 10px;">
   	<div>
   	<c:if test="${zname ne '' and zname ne null}">
   		<hr>
   		<span style="width: 50%; height: 40px; float: left; ">
   		<form id="upallform" name="upallform" action="<%=webBasePath%>admin/labels/updateuserlabelby"  method="post">
   		<input type="hidden" name="ifqt" id="ifqt" >
   		<input type="hidden" name="ltype" id="ltype" value="${labeltype}">
   		<input type="hidden" name="laid" id="laid" value="${id}">
   		<label style="color: red;">对用户的操作：</label>
   		<button onclick="updateUserLabelBy(0)">一键取消【${zname}】改【其他】</button>
   		<button onclick="updateUserLabelBy(1)">直接取消【${zname}】</button>
   		<br><label style="color: red;">单个修改请直接勾选</label>
   		</form>
   		</span>
   		<span style="width: 30%; height: 40px; float: left;">
   		<form id="uplables" name="uplables" action="<%=webBasePath%>admin/labels/updatelabels"  method="post">
   		<label style="color: red;">对标签的操作：</label>
   		<input type="hidden" name="lbtype" id="lbtype" value="${labeltype}">
   		<input type="hidden" name="labid" id="labid" value="${id}">
   		<input type="hidden" name="labzname" id="labzname" >
   		<button onclick="updateLabelBy()">修改标签【${zname}】</button>
   		</form>
   		</span>
   		<span style="width: 20%; height: 40px; float: right;">
   		<form id="delables" name="delables" action="<%=webBasePath%>admin/labels/dellabels"  method="post">
   		<input type="hidden" name="labtype" id="labtype" value="${labeltype}">
   		<input type="hidden" name="lablid" id="lablid" value="${id}">
   		<button onclick="delLabelBy()">删除标签【${zname}】</button>
   		</form>
   		</span>
   	</c:if>
   	</div>
   	<hr>
   	<form id="uponefrom" name="uponefrom" action="<%=webBasePath%>admin/labels/updateuserlabelone"  method="post">
	   	<input type="hidden" name="lid" id="lid" >
	   	<input type="hidden" name="uid" id="uid" >
	   	<input type="hidden" name="labeltype" id="labeltype" value="${labeltype}">
	   	<div>
		   	<c:forEach items="${ulist}" var="user" varStatus="vs">		   	
		   	<span style="float: left; margin-right: 20px; margin-top: 5px; border: 1px solid;">
	   			<table style="width: 480px">
		   			<tr>
			   			<td style="width: 120px"><button type="button">${user.nickname}</button></td>
			   			<c:if test="${labeltype eq 'job' }">
			   			<td style="width: 360px">
				   			<c:forEach items="${user.job}" var="ujob" varStatus="vs">
				   			<input type="checkbox" value="${ujob.id}" checked="checked" onclick="updateOneUserLabel(this,'${user.id}')">${ujob.zname}
				   			</c:forEach>
			   			</td>
			   			</c:if>
			   			
			   			<c:if test="${labeltype eq 'favourite' }">
			   			<td style="width: 360px">
			   				<c:forEach items="${user.favourite}" var="ufavourite" varStatus="vs">
			   				<input type="checkbox" value="${ufavourite.id}" checked="checked"  onclick="updateOneUserLabel(this,'${user.id}')">${ufavourite.zname}
				   			</c:forEach>
			   			</td>
			   			</c:if>
			   			
			   			<c:if test="${labeltype eq 'focus' }">
			   			<td style="width: 360px">
			   				<c:forEach items="${user.focus}" var="ufocus" varStatus="vs">
			   				<input type="checkbox" value="${ufocus.id}" checked="checked"  onclick="updateOneUserLabel(this,'${user.id}')">${ufocus.zname}
				   			</c:forEach>
			   			</td>
			   			</c:if>
		   			</tr>
	   			</table>
   			</span>
		   	</c:forEach>
	   	</div>
	   	</form>
   	</div>
 </div>
  </body>
</html>