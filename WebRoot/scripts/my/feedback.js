$(document).ready(function(e){
	checkIfAllow();  // 非法访问
	$(".form-control-button-submit").click(function(){submitFeedBack()}) //提交
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
 


function submitFeedBack()
{
	var str=$("#feedBackText").val();
	if(session_loginId==""){
		alert("请先登录");
		showTopLogin();
		return;
	}
	
	if(str==""){
		alert("内容不能为空");
		return;
	}
	var tmp=root+"user/feedback";
	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"user.id":session_loginId,"content":str,"type":1},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-用户名重复
				var type = parseInt(json.cord);
				switch (type){
					case 0: 
						 alert("感谢你宝贵的意见。");
						 window.location.href=root;
						 break;
					 case -1:
						 alert("提交出错,请稍后再试");
						 break
				}
			}
		});
}