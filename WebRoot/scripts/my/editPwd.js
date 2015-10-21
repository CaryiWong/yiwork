$(document).ready(function(e){
	checkIfAllow();  // 非法访问
})

/**
 * 非法访问
 */
function checkIfAllow()
{
	if(session_loginId=="")
	{
		showTopLogin();
	}
}
 

function updatePwd(oldPwd,newPwd)
{
	if(oldPwd=="" || newPwd=="")
		return;
	var tmp=root+"user/updatepwd"; //@0605 kcm
	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"id":session_loginId,"oldpwd":oldPwd,"newpwd":newPwd,"type":1},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-用户名重复
				var type = parseInt(json.cord);
				switch (type){
					case -1: 
						 alert(json.msg);
						 break;
					case 0: 
						 alert("修改密码成功,请重新登录");
						 index_exit();
						 break;
					case 1:
						alert(json.msg)
						break;
					case 2:
						alert(json.msg);
						break;
				}
			}
		});
}