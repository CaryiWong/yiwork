//相关绑定事件
$(document).ready(function(){
	//$(document.body).prepend("");
	//绑定登录框显示事件
	$("#loginBtn").bind('click',function(){showLoginDiv();});
	$("#closeLogin").bind('click',function(){showLoginDiv();});
	
	//绑定登录事件
	$("#subLogin").click(function(){subLogin();});
	
	//注册链接
	$("#menu_register").click(function(){window.location.href=root+'/register/register.jsp';});
	//退出登录
	$("#menu_exitLogin").click(function(){exitLogin();});
	
	//自动登录
	if($("#session_loginId").val()!='' && $("#session_loginId").val()!=null){
		//session有值，已经登录
		$("#menu_login").hide();
		$("#menu_register").hide();
		$("#menu_userInfo").show();
		$("#menu_exitLogin").show();
		$("#menu_userInfo").html('<a href="javascript:;">'+$("#session_loginNick").val()+'</a>');
		$("#menu_userInfo").bind('click',function(){
			//链接到个人主页
			
		});
		$("#menu_exitLogin").bind('click',function(){
			//退出登录
			
		});
	}else{
		//判断cookie记录
		if($.cookie("userName")!=null && $.cookie("userPassword")!=null){
			$("#loginUserName").val($.cookie("userName"));
			$("#loginPassWord").val($.cookie("userPassword"));
			//subLogin();
		}else{
			//window.location.href = root+'/register/register.jsp';
		}
	}
})

function writerMenu(){
	
}

var oldMenu = 1;
function updateMenu(index){
	$("#menu"+oldMenu).attr("class","");
	$("#menu"+index).attr("class","active");
	oldMenu = index;
}

/**
搜索文本框change事件
*/
function showFind(){
	$("#loginUl").hide();
	$("#findDiv").show();
	var str = $("#findTxt").val();
	$("#find_quanzi").html('<small>搜\"<span style="color:red;">'+str+'</span>\"相关圈子</small>');
	$("#find_bowen").html('<small>搜\"<span style="color:red;">'+str+'</span>\"相关博文</small>');
	$("#find_user").html('<small>搜\"<span style="color:red;">'+str+'</span>\"相关用户</small>');
}

/**
搜索文本框光标聚集事件
*/
function findFocus(){
	if($("#findTxt").val()!='') showFind();
}

/**
搜索文本光标离开事件
*/
function findBlur(){
	$(".dropdown-menu").hide();
	$("#find_quanzi").html('');
	$("#find_bowen").html('');
	$("#find_user").html('');
}

/**
显示隐藏登录层事件
*/
var loginDivIsShow = false;
function showLoginDiv(){
	if(!loginDivIsShow){
		$("#loginUl").animate({marginTop:"0px"},500);
		$("#loginUserName").focus();
		loginDivIsShow = true;
	}else{
		$("#loginUl").animate({marginTop:"-240px"},500);
		loginDivIsShow = false;
	}
}

/**
 * 登录账号、密码非空验证
 * @return {TypeName} 
 */
function v_loginUser(){
	var str = $("#loginUserName").val();
	if(str=='' || str.length<1){
		$("#loginPwdMsg").show();
		$("#loginPwdMsg").html('<p class="text-error">账号、密码不能为空</p>');
		return false;
	}
	var str = $("#loginPassWord").val();
	if(str=='' || str.length<1){
		$("#loginPwdMsg").show();
		$("#loginPwdMsg").html('<p class="text-error">账号、密码不能为空</p>');
		return false;
	}
	return true;
}

/**
账号登录
*/
function subLogin(){
	if(v_loginUser()){
		var tmp=root+"/api/loginYQ";
		$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"u.u1":$("#loginUserName").val(), "u.u3": $("#loginPassWord").val()},
			dataType:"json",
			success: function(json){
				//json.jr.code 0-成功
				if(json.jr.code==0){
					$("#session_loginId").val(json.u.u0);
					$("#session_loginNick").val(json.u.u2);
					
					$("#menu_login").hide();
					$("#menu_register").hide();
					$("#menu_userInfo").show();
					$("#menu_exitLogin").show();
					$("#menu_userInfo").html('<a href="javascript:;">'+json.u.u2+'</a>');
					$("#menu_userInfo").bind('click',function(){
						//链接到个人主页
						
					});
					if($("#aotuLoginBox").attr('checked')=='checked'){
						//保存账号cookie
						$.cookie("userName", json.u.u1, { path: '/', expires: 30 }); 
				        $.cookie("userPassword", $("#loginPassWord").val(), { path: '/', expires: 30 });
					}
				}else{
					$("#loginPwdMsg").show();
					$("#loginPwdMsg").html('<p class="text-error">账号或密码错误</p>');
				}
			}
		})
	}
}

/**
 * 退出登录
 */
function exitLogin(){
	var tmp=root+"/api/LoginOutYq";
	$.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{},
		dataType:"json",
		success: function(json){
			if(json.jr.code==0){
				//清除账号cookie
				$.cookie("userName", null, { path: '/', expires: 30 }); 
		        $.cookie("userPassword", null, { path: '/', expires: 30 });
		        //清除session
		        $("#session_loginId").val(''); 
				$("#session_loginNick").val('');
				//切换显示菜单
				$("#menu_userInfo").hide();
				$("#menu_exitLogin").hide();
				$("#menu_login").show();
				$("#menu_register").show();
			}
		}
	})
}

/**
 * 字符串替换
 * @param {Object} reallyDo		检索的字符串
 * @param {Object} replaceWith	要替换成的字符串
 * @param {Object} ignoreCase
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
}

/**
 * 截取字符串
 * @param {Object} str	原字符串
 * @param {Object} sum	截取长度
 * @return {TypeName} 
 */
function getStr(str,sum){
try{
	if(str==null||str=="null"){
		return "";
	}else{
		var sstr = "";
	   	if(getBytesCount(str)>sum){	
		   var n = 0;
		   for(i = 0; i < sum; i++){
			   	n ++;
				var c = str.charAt(i);
				if (/^[\u0000-\u00ff]$/.test(c)){
					//sum -= 1;
				}else{
					i += 1;
				}
			}
			str = str.substring(0,n)+"...";
		}
	}
	return str;
	}catch(e){
	return "";
	}
}

/**
 * 获取字符串长度
 * @param {Object} str
 * @return {TypeName} 
 */
function getBytesCount(str){
	var bytesCount = 0;
	if (str != null){
		for (var i = 0; i < str.length; i++){
			var c = str.charAt(i);
			if (/^[\u0000-\u00ff]$/.test(c)){
				bytesCount += 1;
			}else{
				bytesCount += 2;
			}
		}
	}
	return bytesCount;
}