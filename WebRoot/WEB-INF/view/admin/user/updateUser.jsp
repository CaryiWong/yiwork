<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/res/common/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>更新用户信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
function validateform(obj){
	var nick = $("#nick").val();
	var root = $("input[name=root]:checked").val();
	var unum = $("#unum").val();
	var begin = $("#begin").val();
	var end = $("#end").val();
	if(nick==1){
		alert("此昵称已被使用，请重新输入一个昵称");
		$("#nick").focus();  //获取焦点
		return false;
	}
	if(root==1){
		if(unum==""||begin==""||end==""){
			alert("请输入会员号与会员的起、止日期");
			return false;
		}
	}
	return true;
}

function checknickname(o){
	var nick = o.value;
	var submitdata = {
		nickname:nick
	};
	$.ajax({
     type: "post",
     url: "<%=basePath%>user/checknickname",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord!=0){
     		$("#nick").val(1);
     		alert("此昵称已被使用，请重新输入一个昵称");
			$("#nickname").focus(); //获取焦点
     	}
     }
    });
}

function checkemail(o){
	var email = o.value;
	var submitdata = {
		email:email
	};
	$.ajax({
     type: "post",
     url: "<%=basePath%>user/checkemail",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord!=0){
     		$("#mail").val(1);
     		alert("此邮箱已被使用，请重新输入一个邮箱");
			$("#email").focus(); //获取焦点
     	}
     }
    });
}

function checktel(o){
	var tel = o.value;
	var submitdata = {
		tel:tel
	};
	$.ajax({
     type: "post",
     url: "<%=basePath%>user/checktel",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord!=0){
     		$("#tel").val(1);
     		alert("此手机号已被使用，请重新输入一个手机号");
			$("#telephone").focus(); //获取焦点
     	}
     }
    });
}

function charge(obj){
	var charge = obj.value;
	if(charge==1){
		$("#unum").removeAttr("readonly");
		$("#chargetr").show(1000);
	}else{
		$("#unum").attr("readonly","readonly");
		$("#chargetr").hide(1000);
	}
}

function addOneyear(obj,n){
	var d=new Date(obj.value);
	var year=d.getYear()+n+1900;
	var m=d.getMonth()+1;
	var day=d.getDate();
	var str=year+"-";
	if(m<10){
		str+="0"+m+"-";
	}else{
		str+=m+"-";
	}
	if(day<10){
		str+="0"+day;
	}else{
		str+=day;
	}
	document.getElementById("end").value=str;
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
		<div id="right" style="display: inline-block;float: left;position: absolute;width: 1200px;height: 620px;background-image: url('../../res/images/mainbox.png');">
			<form name="mainform" id="mainform" action="<%=basePath%>admin/user/saveuser"
				method="post" onsubmit="return validateform(this);">
				<input type="hidden" value="${user.uuid }" name="uuid">
				<table align="center" style="margin-top: 50px;">
					<tr>
						<td colspan="4">
							<label>标签</label>
							<c:forEach items="${labels }" var="label" varStatus="vs">
								<c:if test="${vs.index mod 10 eq 0 }"><br></c:if>
								<c:set var="la" value="${label.id}"></c:set>
								<input name="groups" type="checkbox" value="${label.id}" <c:if test="${fn:contains(labelList,la) }">checked="checked"</c:if> />${label.zname }
							</c:forEach>						
						</td>
					</tr>
					<tr>
						<td><label>会员号</label></td>
						<td><input style="width: 200;" id="unum" name="unum" value="${user.unum }" <c:if test="${user.root eq 2}">readonly="readonly"</c:if> /></td>
						<td><label>身份证</label></td>
						<td>
							<input style="width: 200;" id="idnum" name="idnum" value="${user.idnum }" />
						</td>
					</tr>
					<tr>
						<td><label>姓名</label></td>
						<td>
							<input style="width: 200;" id="realname" name="basicinfo.realname" value="${user.basicinfo.realname }">
						</td>
						<td><label>昵称</label></td>
						<td>
							<input style="width: 200;" type="text" id="nickname" name="basicinfo.nickname" value="${user.basicinfo.nickname }" maxlength="20" onchange="checknickname(this);" />
							<input type="hidden" id="nick" value="0">
						</td>
					</tr>
					<tr>
						<td><label>邮箱</label></td>
						<td><input style="width: 200;" type="text" id="email" name="basicinfo.email" value="${user.basicinfo.email}" maxlength="20" onchange="checkemail(this);"/>
							<input type="hidden" id="mail" value="0" />
						</td>
						<td><label>电话</label></td>
						<td><input style="width: 200;" type="text" id="telephone" name="basicinfo.tel" value="${user.basicinfo.tel }" maxlength="20" onchange="checktel(this);"/>
							<input type="hidden" id="tel" value="0" />
						</td>
					</tr>
					<tr>
						<td><label>微信</label></td>
						<td><input style="width: 200;" type="text" id="telephone" name="basicinfo.weixin" value="${user.basicinfo.weixin }" maxlength="20" /></td>
						<td><label>性别</label></td>
						<td>
							<select style="width: 200;" name="basicinfo.sex">
								<option value="0" <c:if test="${user.basicinfo.sex eq 0 }">selected="selected"</c:if>>男</option>
								<option value="1" <c:if test="${user.basicinfo.sex eq 1 }">selected="selected"</c:if>>女</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label>行业</label></td>
						<td><input style="width: 200;" type="text" id="industry" name="jobinfo.industry.zname" value="${user.jobinfo.industry.zname }" maxlength="20" /></td>
						<td><label>职业</label></td>
						<td><input style="width: 200;" type="text" id="job" name="jobinfo.job.zname" value="${user.jobinfo.job.zname }" maxlength="20" /></td>
					</tr>
					<tr>
						<td><label>公司</label></td>
						<td><input style="width: 200;" type="text" id="company" name="jobinfo.company" value="${user.jobinfo.company}" maxlength="20" /></td>
						<td><label>网址</label></td>
						<td><input type="text" id="weburl" name="jobinfo.weburl" value="${user.jobinfo.weburl}" maxlength="200" style="width: 100%;" /></td>
					</tr>
					<tr>
						<td><label>个人简介</label></td>
						<td colspan="3">
							<textarea rows="5" cols="20" name="introduction" style="width: 100%;" >${user.introduction }</textarea>
						</td>
					</tr>
					<tr>
						<td><label>收费</label></td>
						<td>
							<input type="radio" value="1" name="root" id="root" <c:if test="${user.root ne 2}">checked="checked"</c:if> onchange="charge(this);" />是
						</td>
						<td>
							<input type="radio" value="2" name="root" id="root" <c:if test="${user.root eq 2}">checked="checked"</c:if> onchange="charge(this);" />否
						</td>
					</tr>
					<tr id="chargetr"  <c:if test="${user.root eq 2}">style="display: none;"</c:if>>
						<td><label>起:</label></td>
						<td>
							<input id="begin" name="begin" value="<fmt:formatDate value="${user.basicinfo.vipdate1 }" pattern="yyyy-MM-dd"/>" type="text" onClick="WdatePicker()" placeholder="起始日期" style="width: 160" onchange="addOneyear(this,1)" />
						</td>
						<td><label>止:</label></td>
						<td>
							<input id="end" name="end" value="<fmt:formatDate value="${user.basicinfo.vipenddate1 }" pattern="yyyy-MM-dd"/>" type="text" onClick="WdatePicker()" placeholder="截止日期" style="width: 160" />
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
