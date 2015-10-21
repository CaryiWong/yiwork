$(document).ready(function(e) {
	//弹出登录层
	$("#indexLogin").click(function(){loadLogin();});
});

var all_root="";
var root="";
//动态创建登录层	
function loadLogin()
{
	$("body").append(
	"<div id='baidu' class='hide' style='width:420px;height:360px; border:0px solid red; display:none;background-color:#EDEDED;' >" +
	"	<table>" +
		"	<tr>" +
			"	<td style='padding:10px 0 0 26px;'> <font class='font' style='font-size:24px; font-weight:700' color='#333333'>登录</font></td>" +
			"</tr>" +
			"<tr> " +
				"<td  style='padding:28px 0 0 26px;line-height:35px'>" +
					"<input  class='logo_input' type='text' onblur='verifiUserName()' placeholder='用户名/邮箱' id='inputUserName'  value='385835523@qq.com'/>" +
				"</td>" +
			"</tr>" +
			"<tr>" +
				"<td  style='padding:16px 0 0 26px;line-height:35px'> " +
					"<input class='logo_input' type='password' onblur='verifiPassWord()' placeholder='密码' id='inputPassword' />" +
				"</td>" +
			"</tr>" +
			"<tr>" +
				"<td style='padding:16px 0 0 26px;'>" +
					"<input style='zoom:130%' type='checkbox'  /> " +
					"<font  class='font' style=' padding:0 0 0 5px; font-size:14px; font-weight:700'>记住我</font>" +
					"<a href='javascript:void(0);' style='padding:0 0 0 230px;font-size:14px;font-weight:700; color:#9DC6ED'>忘记密码</a>" +
				"</td>" +
			"</tr>" +
			"<tr>" +
				"<td style='padding:0px 0 0px 240px;'>" +
					"<input type='button' id='loginSubmit' onclick='subLogin()' style='width: 160px; height: 50px; border: 0px; background-image:url(images/log_on_normal.png); margin:20px 0 20px 0;'  value='登录'/>" +
				"</td>" +
			"</tr>" +
		"</table>" +
		"<div  style='background-color:#DFDEDE; margin:0px 0 0 0; border:0px solid red;'>" +
			 "<div id='otherUser' class='third_back' >" +
			   " <table width='100%' style='padding:10px 0 0 0'>" +
					"<td width='50%'> <font class='font' style='font-size:14px; color:gray; margin:0 0 0 28px;'>用其他账号登录</font></td>" +
					"<td width='10%'> <img src='images/weibo_normal.png' /></td>" +
					"<td width='10%'> <img src='images/QQ_normal.png' /></td>" +
					"<td width='10%'> <img src='images/weibo_normal.png' /></td>" +
					"<td width='10%'> <img src='images/QQ_normal.png' /></td>" +
				"</table>" +
			 "</div>" +
		"</div>" +
	"</div>"
	);
	
	var i = $.layer({
    type : 1,
    title : false,
    fix : false,
    offset:['50px' , ''],
	border : [0],
    area : ['420px','400px'],
    page : {dom : '#baidu'}
	});  
	 
}


/**
 * 提交登录
 */
function subLogin(){
	verifiUserName();
	if(pwd && userNametrue){
		var tmp=root+"/api/userLogin";
		$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"user.userName":$("#inputUserName").val(),"user.password": $("#inputPassword").val(),"type":1},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-失败
				var type = parseInt(json.cord);
				switch (type){
					case 0: 
						 //alert("登录成功!");
						 window.location.reload();
						return;
					case 1: 
						 alert("账号或密码错误，请稍后再试!")
						break;
					case 2: 
						$("#inputNickName_info").attr("class","text-error");
						$("#inputNickName_info").html('昵称重复，请重新输入昵称');
						break;
					case 3: 
						alert("服务器忙，请重新注册");
						break;
				}
			}
		});
	}
}



var userNametrue=false;
//账号验证
function verifiUserName(){
	var str = $("#inputUserName").val();
	if(str=='' || str.length<1){
		//$("#inputUserName").attr("color","text-error");
		$("#inputUserName").css({"color":"red"});
		$("#inputUserName").val('账号不能为空');
		return userNametrue;
	}
	var patrn=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	if (!patrn.exec(str)){
		//$("#inputUserName").attr("class","text-error");
		$("#inputUserName").css({"color":"red"});
		$("#inputUserName").val('请填写正确的邮箱账号格式');
		return userNametrue;
	}else
	{
		//successInfo = '<span style="float: right;"><img src="'+root+'/register/images/success.png" style="margin-top: 20px;" /></span>';
		//var html = successInfo+'<span style="float: right; margin-top: 0px;">账号填写成功</span>';
		$("#inputUserName").css({"color":"gray"});
		userNametrue = true;
		return userNametrue;
	}
}


var pwd=false;
//密码验证
function verifiPassWord(){
	var str = $("#inputPassword").val();
	if(str=='' || str.length<1){
		//$("#inputPassword_info").show();
		//$("#inputPassword_info").attr("class","text-error");
		$("#inputPassword").css({"color":"gray"});
		$("#inputPassword").val('密码不能为空，请重新填写');
		return pwd;
	}
	var patrn=/^[a-zA-Z0-9]{6,15}$/; 
	if (!patrn.exec(str)){
		//$("#inputPassword_info").attr("class","text-error");
		$("#inputPassword").css({"color":"gray"});
		$("#inputPassword").val('密码填写错误，请输入6-15位字符');
		return pwd;
	}else{
		$("#inputPassword").css({"color":"gray"});
		pwd = true;
		return pwd;
	}
}


