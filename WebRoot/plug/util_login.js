$(document).ready(function(e) {
	if(session_loginId=='' || session_loginNick==null){
		//是否自动登录
		if($.cookie("autoLogin")!=null){
			if($.cookie("autoLogin")=='true'){
				autoSubLogin();
			}
		}
	}
});

/**
 * 自动登录
 */
function autoSubLogin(){
	var tmp=root+"/api/loginYQ";
	$.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"u.u1":$.cookie("userName"), "u.u3": $.cookie("userPassword")},
		dataType:"json",
		success: function(json){
			if(json.code==0){
				$.cookie("jzpwd", true, { path: '/', expires: 30 }); 
				$.cookie("autoLogin", true, { path: '/', expires: 30 }); 
				$.cookie("userName", $.cookie("userName"), { path: '/', expires: 30 }); 
		        $.cookie("userPassword",$.cookie("userPassword"), { path: '/', expires: 30 });
		        $("#loginUserName").html(json.data.u2);
			}
		}
	})
}

/**
 * 手动提交登陆
 */
function subLogin(){
	var userName = $("#userName").val();
	if(userName.length<1 || userName==''){
		alert("账号不能为空！");
		$("#userName").focus();
		return;
	}
	var userPwd = $("#userPwd").val();
	if(userPwd.length<1 || userPwd==''){
		alert("密码不能为空！");
		$("#userPwd").focus();
		return;
	}
	
	var isAutoLogin = $('input:radio[name="autoLoginRadio"]').is(":checked");
	var isJzPwd = $('input:radio[name="jzPwdRadio"]').is(":checked");
	var tmp=root+"/web/login";
	//var dat="u.u1="+$("#userName").val()+"&u.u3="+$("#userPwd").val();
	//window.loginForm.submit();
	//window.location.href="/web/login?"+dat;
	$.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"u.u1":$("#userName").val(), "u.u3": $("#userPwd").val()},
		dataType:"json",
		success: function(json){
			//json.code 0-成功
			if(json.code==0){
				if(isJzPwd){	//是否记住密码
					$.cookie("jzpwd", true, { path: '/', expires: 30 }); 
					$.cookie("userName", userName, { path: '/', expires: 30 }); 
			        $.cookie("userPassword",userPwd, { path: '/', expires: 30 });
				}else{
					$.cookie("jzpwd", false, { path: '/', expires: 30 }); 
				}
				if(isAutoLogin){	//是否自动登录
					$.cookie("autoLogin", true, { path: '/', expires: 30 }); 
			        $.cookie("userName", userName, { path: '/', expires: 30 }); 
			        $.cookie("userPassword", userPwd, { path: '/', expires: 30 });
				}else{
					$.cookie("autoLogin", false, { path: '/', expires: 30 });
				}
				
				
				
				alert("登录成功！");
				window.location.href="/main/maind.jsp";
			}else{
				alert("账号或密码错误！");
			}
		}
	});
	
}
