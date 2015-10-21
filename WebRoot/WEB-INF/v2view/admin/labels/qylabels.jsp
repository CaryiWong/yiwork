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
   <script type="text/javascript">
   function gettwolabel(n,id){
	   if(n==1){
		   document.getElementById("fav_pid").value=id;
		   document.getElementById("fav_id").value="";
	   }else if(n==2){
		   document.getElementById("fav_id").value=id;
	   }
	   document.getfavtow.submit();
   }
   
   function delonelabel(n,obj,uid){
	   document.getElementById("new_focus_id").value=obj.value;
	   document.getElementById("u_id").value=uid;
	   document.getElementById("ifdel").value=n;
	   if(obj.value!=-1){
		   document.updatefocus.submit();
	   }else{
		   event.preventDefault();
	   }
   }
   
   </script>
  </head>
  
  <body>
  <center><div style="height: 800px;">
  <!-- top -->
  <div style="height: 240px;">
  <form name="getfavtow" id="getfavtow" action="<%=webBasePath%>admin/labels/qylabels"  method="post">
      <input type="hidden" name="fav_pid" id="fav_pid" value="${fav_pid}">
      <input type="hidden" name="fav_id" id="fav_id" value="${fav_id}">
  <!-- top-left -->
  <div style="height: auto; width: 48%; float: left; margin-right: 10px">
  <!-- 1级 -->
    <label style="color: red">兴趣爱好</label>
     <div style=" text-align: left;">
		<c:forEach items="${list}" var="lb" varStatus="vs">
		<c:if test="${lb.labeltype eq 'favourite' }">
			<c:if test="${lb.pid eq '0' }">
				<c:if test="${lb.id eq fav_pid }">
		 		<button type="button" style="margin-top: 10px; margin-right: 20px;background-color: red" >${lb.zname}</button>
		 		</c:if>
		 		<c:if test="${lb.id ne fav_pid }">
		 		<button type="button" style="margin-top: 10px; margin-right: 20px;" onclick="gettwolabel(1,${lb.id})">${lb.zname}</button>
		 		</c:if>
	 		</c:if>
	 	</c:if>
		</c:forEach>
	
	</div>
	<hr>
	 <!-- 2级 -->
	<div style=" text-align: left;">
	<c:forEach items="${list}" var="lb" varStatus="vs">
		<c:if test="${lb.labeltype eq 'favourite' }">
			<c:if test="${lb.pid eq fav_pid }">
				<c:if test="${lb.id eq fav_id }">
		 		<button type="button" style="margin-top: 10px; margin-right: 20px;background-color: red" >${lb.zname}</button>
		 		</c:if>
		 		<c:if test="${lb.id ne fav_id }">
		 		<button type="button" style="margin-top: 10px; margin-right: 20px;" onclick="gettwolabel(2,${lb.id})">${lb.zname}</button>
		 		</c:if>
	 		</c:if>
	 	</c:if>
		</c:forEach>
	</div>
  </div>
  <!-- top-right -->
  <div style="height: auto; width: 48%; float: right;">
 <label style="color: red">【新版关注领域标签列表】</label>  <br>
	 <div style=" text-align: left;">
		<c:forEach items="${list}" var="lb" varStatus="vs">
		<c:if test="${lb.labeltype eq 'focus' }">
	 		<button type="button" style="margin-top: 10px; margin-right: 20px;" >${lb.zname}</button><!-- onclick="gettwolabel(3,${lb.id})" -->
	 	</c:if>
		</c:forEach>
	</div>
  </div>
  </form>
  </div>
  <hr>
  <!-- main -->
  <div>
  <div style="width: 98%; height: auto;min-height:400px;">
  <label style="color: red">用户【兴趣爱好】整合到【关注领域】</label>
  <div style="text-align: left;">
 <c:if test="${fav_id ne null and fav_id ne ''}">
  <span style="float: left; margin-top: 5px; border: 1px solid;">
  <table >
		<th>
			<td style="width: 120px"><label style="color: red">用户名</label></td>
			<td style="width: 360px">
				<label style="color: red">【兴趣爱好】</label>
			</td>
			<td style="width: 460px">
				<label style="color: red">【关注领域】(注:点击取消单个标签)</label>
			</td>
			<td style="width: 360px;text-align: right;">
			   <label style="color: red">【再关注一个】</label>			
			</td>
		</th>
	</table>
  </span>
 </c:if>
 <form name="updatefocus" id="updatefocus" action="<%=webBasePath%>admin/labels/updateonefouce"  method="post">
 <input type="hidden" name="old_fav_pid" id="old_fav_pid" value="${fav_pid}">
 <input type="hidden" name="old_fav_id" id="old_fav_id" value="${fav_id}">
 <input type="hidden" name="new_focus_id" id="new_focus_id">
 <input type="hidden" name="u_id" id="u_id">
 <input type="hidden" name="ifdel" id="ifdel">
  <c:forEach items="${ulist}" var="user" varStatus="vs">		   	
		   	<span style="float: left; margin-top: 5px; border: 1px solid;">
	   			<table style="width: 1300">
		   			<tr>
			   			<td style="width: 120px"><button type="button">${user.nickname}</button></td>
			   			<td style="width: 360px">
			   				<c:forEach items="${user.favourite}" var="ufavourite" varStatus="vs">
			   				<button type="button" style="margin-right: 5px;" >${ufavourite.zname}</button>
				   			</c:forEach>
			   			</td>
			   			<td style="width: 460px">
			   				<c:forEach items="${user.focus}" var="focus" varStatus="vs">
			   				<input type="checkbox" value="${focus.id}" checked="checked" onclick="delonelabel(0,this,'${user.id}')">${focus.zname}
				   			</c:forEach>
			   			</td>
			   			<td style="width: 360px; text-align: right;">
			   				<select name="newfocus" id="newfocus" onchange="delonelabel(1,this,'${user.id}')">
			   						<option value="-1">--请选择--</option>
								   <c:forEach items="${list}" var="lb" varStatus="vs">
									<c:if test="${lb.labeltype eq 'focus' }">
								 		 <option value="${lb.id}">${lb.zname}</option>
								 	</c:if>
									</c:forEach>
						   </select>
			   			</td>
		   			</tr>
	   			</table>
   			</span>
	  </c:forEach>
	  </form>
	  </div>
	 </div> 
	 
  </div>
  </div>
   </center>
  </body>
</html>
