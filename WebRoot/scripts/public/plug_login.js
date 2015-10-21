$(document).ready(function(e) {
	var $body = $('body');
	$body.on('click' , '#index_login' , function(){submitLogin()})
	$body.on('blur' , '#login_userName' , function(){verifiUserName()})
	$body.on('blur' , '#login_pwd' , function(){verifiPassWord()})
	$body.on('focus' , '#login_userName' , function(){$("#login_userName").css({"color":"gray"})})
	$body.on('focus' , '#login_pwd' , function(){$("#login_pwd").css({"color":"gray"})})
	$body.on('click' , '#index_register' , function(){window.location.href=root+"register/register.jsp"})
	$body.on('click' , '#forgetPwd' , function(){window.location.href=root+"pages/findPassword.jsp"}) //找回密码
	$body.on('keypress','#login_pwd', function(event){
		
            if(event.keyCode == 13 ||event.keyCode =="13")    
            {
               submitLogin();
            }else
            	{
            	 
            	}
        })
	
	  
	
////	$("#index_login").click();
//	$("#login_userName").blur(function(){verifiUserName()});
//	$("#login_pwd").blur(function(){verifiPassWord()});
//	$("#login_userName").focus(function(){$("#login_userName").css({"color":"gray"})});
//	$("#login_pwd").focus(function(){$("#login_pwd").css({"color":"gray"})});
//	$("#index_register").click(function(){window.location.href=root+"register/register.jsp"});  //进行注册
//	
});


var rootjs= root+"v20/";

/**
 * 
 */
function submitLogin()
{
	 verifiUserName();
	 verifiPassWord();
	
	 if(userNametrue && pwd)
	 {
		 var tmp=rootjs+"user/login";
		$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"username":$("#login_userName").val(),"password": $.md5($("#login_pwd").val()),"type":"web"},
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
					case -1: 
						alert("服务器忙，请稍后重试");
						break;
				}
			}
		});
	}
}




var userNametrue=false;
//账号验证
function verifiUserName(){
	var str = $("#login_userName").val();
	if(str=='' || str.length<1){
		//$("#inputUserName").attr("color","text-error");
		$("#login_userName").css({"color":"red"});
		$("#login_userName").val('账号不能为空');
		return userNametrue;
	}
	//var patrn=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	//var mobile=/^1[3|4|5|8][0-9]\d{8}$/
	//var cn=/^[\u4e00-\u9fa5]+$/
	//if (!patrn.exec(str) && !mobile.exec(str) && cn.exec(str)){
		//$("#inputUserName").attr("class","text-error");
	//	$("#login_userName").css({"color":"red"});
	//$("#login_userName").val('请填写正确帐号,邮箱、手机号、昵称');
	//	return userNametrue;
	//}else
	//{
		//successInfo = '<span style="float: right;"><img src="'+root+'/register/images/success.png" style="margin-top: 20px;" /></span>';
		//var html = successInfo+'<span style="float: right; margin-top: 0px;">账号填写成功</span>';
		$("#login_userName").css({"color":"gray"});
		userNametrue = true;
		return userNametrue;
	//}
}


var pwd=false;
//密码验证
function verifiPassWord(){
	var str = $("#login_pwd").val();
	if(str=='' || str.length<1){
		$("#login_pwd").css({"color":"gray"});
		$("#login_pwd").val('密码不能为空，请重新填写');
		return pwd;
	}
	var patrn=/^[a-zA-Z0-9]{6,15}$/; 
	if (!patrn.exec(str)){
		//$("#inputPassword_info").attr("class","text-error");
		$("#login_pwd").css({"color":"gray"});
		$("#login_pwd").val('密码填写错误，请输入6-15位字符');
		return pwd;
	}else{
		$("#login_pwd").css({"color":"gray"});
		pwd = true;
		return pwd;
	}
}


