$(document).ready(function(e) {
	 eventAllow(); //非法访问除外
});

function eventAllow()
{
	if(session_loginId=="")
	{
		//没登录就弹出登录框
    	$('.header-status-link.status-link-sign').click();
    	return;
	}
	
	var tmp=root+"activity/getactivityinfo";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type": 1, "id":event_id},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				$("#eventTitle").text(json.data.title);// 设置标题
				loadUserInfo();
 				//lables "event_labels"
			}else
			{
				alert("404错误");
				window.location.href=root+"events/activities.jsp";
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}


function loadUserInfo()
{
	 var tmp=root+"user/getuserinfo";
		$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"userid":session_loginId,"type":1},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-失败
				var type = parseInt(json.cord);
				switch (type){
					case 0: 
						$("#userName").val(json.data.basicinfo.realname);  //邮箱
						//var sex="男";
						//if(json.data.basicinfo.sex==1) sex="女";
						////$("#user_sex").text(sex);  //性别
						//$("#user_sex").attr("data-switch-value",json.data.basicinfo.sex);
						//判断是否会员
						if(json.data.root!=1)
						{
							$("#ifVipDiv").hide();
						}else
						{
							$(".form-control-link").hide();	
						}
						$("#userEmail").val(json.data.basicinfo.email);  //job
						$("#userTel").val(json.data.basicinfo.tel);  //联系电话
					 
				}
			}
		});
}

function ifJoind()
{
	var tmp=root+"activity/checksignforuser";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type": 1, "userid":session_loginId,"activityid":event_id},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				alert("你已经报名，无需重复报名");
				window.location.href=root+"events/activities.jsp";
			}else
			{
				alert("404错误");
				window.location.href=root+"events/activities.jsp";
				//出现404.或者统一页面.服务器出错
			}
		}
	});
	 
}

/**
 * 报名活动
 */
function joinEvent()
{
	if(session_loginId=="")
	{
		//没登录就弹出登录框
		$('.header-status-link.status-link-sign').click();
		return;
	}
	
	
	
	
	
	util.loading.show(); //打开loading层
	var tmp=root+"activity/joinactivity";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1,"userid":session_loginId,"activityid":event_id,"name":$("#userName").val(),"sex":$("#userSex").val(),"job":$("#userJob").val(),"tel":$("#userTel").val(),"email":$("#userEmail").val(),"vipflag":$("#ifVip").val(),"reason":$("#joinReason").val()},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
			 	util.loading.close();
			 	alert("活动报名成功");
			 	window.location.href=root+"events/activityDetial.jsp?event_id="+event_id;
			 	//window.location.reload();
			}else
			{
				
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}




