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
    
    <title>录入会员</title>
    
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
function selectindustry(o){
	var pid = o.value;
	var submitdata = {
		pid:pid
	};
	$.ajax({
     type: "post",
     url: "<%=basePath%>user/getclasssortlist",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     		if(result.cord==0){
     			 jQuery("#job").empty();
     			var jobs = result.data;
     			for(var i=0;i<jobs.length;i++){
     				$("#job").append("<option value='"+jobs[i].id+"'>"+jobs[i].zname+"</option>"); 
     			}
     		}
     	}
    });
}

function checkunum(o){
	var unum = o.value;
	var submitdata = {
		unum:unum
	};
	$.ajax({
     type: "post",
     url: "<%=basePath%>user/checkunum",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord!=0){
     		alert("此会员号已存在，请重新输入");
			$("#unum").val("");
     	}
     }
    });
}

function selecedate(obj){
	var charge = obj.value;
	$("#chargetype").val(charge);
	if(charge==1){
		$("#chargetr").show(1000);
	}else{
		$("#chargetr").hide(1000);
	}
}
	function validateform(obj) {
		var name = obj.name.value;
		var email = obj.email.value;
		var tel = obj.tel.value;
		var charge = $("#chargetype").val();
		if (name == "") {
			alert("请输入用户姓名");
			return false;
		}
		if(email == ""){
			alert("请输入邮箱");
			return false;
		}
		if (tel == "") {
			alert("请输入手机号");
			return false;
		}
		if(charge==1){
			if($("#unum").val()==""){
				alert("请输入会员号");
				return false;
			}
		}
		return true;
	}
	
	function changexz(obj){
	var d=new Date(obj.value);
	var m=d.getMonth()+1;
	var day=d.getDate();
	var xz=document.getElementById("xz");
	if(m==1){
		if(day<20){
			xz.value="摩羯座";
		}else{
			xz.value="水瓶座";
		}
	}else if(m==2){
		if(day<19){
			xz.value="水瓶座";
		}else{
			xz.value="双鱼座";
		}
	}else if(m==3){
		if(day<21){
			xz.value="双鱼座";
		}else{
			xz.value="白羊座";
		}
	}else if(m==4){
		if(day<20){
			xz.value="白羊座";
		}else{
			xz.value="金牛座";
		}
	}else if(m==5){
		if(day<21){
			xz.value="金牛座";
		}else{
			xz.value="双子座";
		}
	}else if(m==6){
		if(day<22){
			xz.value="双子座";
		}else{
			xz.value="巨蟹座";
		}
	}else if(m==7){
		if(day<23){
			xz.value="巨蟹座";
		}else{
			xz.value="狮子座";
		}
	}else if(m==8){
		if(day<23){
			xz.value="狮子座";
		}else{
			xz.value="处女座";
		}
	}else if(m==9){
		if(day<23){
			xz.value="处女座";
		}else{
			xz.value="天秤座";
		}
	}else if(m==10){
		if(day<24){
			xz.value="天秤座";
		}else{
			xz.value="天蝎座";
		}
	}else if(m==11){
		if(day<23){
			xz.value="天蝎座";
		}else{
			xz.value="射手座";
		}
	}else if(m==12){
		if(day<22){
			xz.value="射手座";
		}else{
			xz.value="摩羯座";
		}
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
		<div id="right"
			style="display: inline-block;float: left;position: absolute;width: 1200px;height: 620px;background-image: url('res/images/mainbox.png');">
			<form name="mainform" id="mainform" action="admin/user/saveapplyvip"
				method="post" onsubmit="return validateform(this);">
				<table align="center" style="margin-top: 30px;">
					<tr>
						<td><label>姓名<font color="red">*</font></label></td>
						<td><input id="name" name="name" style="width: 160"/></td>
						<td><label>邮箱<font color="red">*</font></label></td>
						<td>
							<input id="email" name="email" type="text" style="width: 160"/>
						</td>
						<td><label>手机<font color="red">*</font></label></td>
						<td>
							<input id="tel" name="tel" type="text" style="width: 160" />
						</td>
					</tr>
					<tr>
						<td><label>出生日期</label></td>
						<td>
							<input name="birth" value="${birth }" type="text" onClick="WdatePicker();" placeholder="出生日期" style="width: 160" onchange="changexz(this);"/>
						</td>
						<td><label>星座</label></td>
						<td><input type="text" id="xz" disabled="disabled"/>
						
						</td>
						<td><label>活动频率</label></td>
						<td>
							<select name="actnum" style="width: 160">
								<option value="-1">未知</option>
								<option value="0">1-2次/月</option>
								<option value="1">3-4次/月</option>
								<option value="2">5-6次/月</option>
								<option value="3">6次以上/月</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label>行业</label></td>
						<td>
							<select name="industry.id" onchange="selectindustry(this);">
								<c:forEach items="${industrys }" var="industry" varStatus="vs">
									<option value="${industry.id }">${industry.zname }</option>
								</c:forEach>
							</select>
						</td>
						<td><label>职业</label></td>
						<td>
							<select id="job" name="job.id">
							</select>
							<input id="duties" name="duties" style="width: 100px;" value=""/>
						</td>
						<td><label>性别</label></td>
						<td>
							<select name="sex" style="width: 160">
								<option value="0" selected="selected">男</option>
								<option value="1">女</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label>工作年限</label></td>
						<td>
							<select name="jobyear" style="width: 160">
								<option value="-1">未知</option>
								<option value="0">1-2年</option>
								<option value="1">3-4年</option>
								<option value="2">5-6年</option>
								<option value="3">6年以上</option>
							</select>
						</td>
						<td><label>办公需求</label></td>
						<td>
							<select name="jobdemand" style="width: 160">
								<option value="-1">未知</option>
								<option value="0">是</option>
								<option value="1">否</option>
							</select>
						</td>
						<td><label>喜欢活动类型</label></td>
						<td>
							<select name="acttype" style="width: 160">
								<option value="-1">未知</option>
								<option value="0">科技与互联网</option>
								<option value="1">艺术与设计</option>
								<option value="2">社会创新</option>
								<option value="3">文化生活</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label>了解渠道</label></td>
						<td>
							<select name="channel" style="width: 160">
								<option value="0" selected="selected">同事/朋友</option>
								<option value="1">媒体报道</option>
								<option value="2">参加活动</option>
							</select>
						</td>
						<td><label>身份证</label></td>
						<td colspan="3">
							<input id="idnum" name="idnum" type="text" style="width: 100%;" />
						</td>
					</tr>
					<tr>
						<td><label>兴趣爱好</label></td>
						<td colspan="5">
							<textarea rows="5" cols="100" id="interests" name="interests"></textarea>
						</td>
					</tr>
					<tr>
						<td><label>对一起的理解</label></td>
						<td colspan="5">
							<textarea rows="5" cols="100" id="understand" name="understand"></textarea>
						</td>
					</tr>
					<tr>
						<td><label>希望获得</label></td>
						<td colspan="5">
							<textarea rows="5" cols="100" id="result" name="result"></textarea>
						</td>
					</tr>
					<tr>
						<td><label>建议</label></td>
						<td colspan="5">
							<textarea rows="5" cols="100" id="proposal" name="proposal"></textarea>
						</td>
					</tr>
					<tr>
						<td><label>缴费</label></td>
						<td>
							<input type="radio" id="charge" name="charge" value="1" onclick="selecedate(this);" checked="checked">是
						</td>
						<td><input type="hidden" id="chargetype" name="chargetype" value="1"/></td>
						<td>
							<input type="radio" id="charge" name="charge" value="0" onclick="selecedate(this);">否
						</td>
					</tr>
					<tr id="chargetr">
						<td><label>会员号<font color="red">*</font></label></td>
						<td>
							<input id="unum" name="unum" style="width: 160" onchange="checkunum(this);"/>
						</td>
						<td><label>起:</label></td>
						<td>
							<input name="begin" type="text" onClick="WdatePicker()" placeholder="起始日期" style="width: 160" onchange="addOneyear(this,1)" />
						</td>
						<td><label>止:</label></td>
						<td>
							<input id="end" name="end" type="text" onClick="WdatePicker()" placeholder="截止日期" style="width: 160" />
						</td>
					</tr>
					<tr align="center">
						<td colspan="3"><input type="submit" value="保存" /></td>
						<td colspan="3"><input type="button" value="取消" onclick="history.back(-1);" /></td>
					</tr>
					<c:if test="${tips ne null }">
						<tr align="center">
							<td colspan="6">${tips }</td>
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
