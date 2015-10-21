var keyword;

/*统一前缀*/
var rootjs= root+"v20/";

$(document).ready(function(e) {
	 $(".header-nav-item").each(function(){
		var word=$(this).children("a").text();
		switch(word)
		{
			case "空间": $(this).children("a").click(/*function(){window.location.href=root+"space/space_new.jsp";}*/); return;
			case "活动":  $(this).children("a").click(/*function(){window.location.href=root+"events/activities.jsp";}*/);return;
			case "会员": $(this).children("a").click(/*function(){window.location.href=root+"vip/vip.jsp";}*/);return;
		}
	})
	//$(".icon.fl").click(function(){window.location.href=root+"index.jsp"});//返回首页logo
	$("#user_center").click(/*function(){window.location.href=root+"my/userCenter.jsp"}*/)
	$("#user_msg").click(/*function(){window.location.href=root+"my/notice.jsp"}*/)
	//ifUnRead();
	
	 //弹出登录层

//	$(".header-status-link.login").click(function(){

//		$("body").append("<div id='baidu' class='hide' style='width:400px;height:350px; border:0px solid red; display:none;background-color:#EDEDED;'>")
//		$("#baidu").load(root+"pages/login.jsp");
//		var i = $.layer({
//    		type : 1,
//    		title : false,
//    		fix : false,
//    		offset:['50px' , ''],
//			border : [0],
//    		area : ['400px','350px'],
//    		page : {dom : '#baidu'}
//		});  
//		 
//	})
	
	//跳注册页面
	$("#index_reg").click(/*function(){window.location.href=root+"register/register.jsp"}*/);
	//退出
	$("#index_exit").click(/*function(){index_exit()}*/);
	//搜索 
	$(".header-search-button").click(function(){
			keyword=$(".header-search-text").val();
			if(keyword.length>0)
				{
				//var a=encodeURIComponent(keyword);
				//keyword=decodeURI(keyword);
				/*window.location.href=root+"search/search.jsp?keyword="+keyword;*/
			}
	})
	$(".header-search-text").keypress(function(event){
		if(event.keyCode==13)
		{
			keyword=$(".header-search-text").val();
				if(keyword.length>0)
				{
					//var a=encodeURIComponent(keyword);
					//keyword=decodeURI(keyword);
				/*window.location.href=root+"search/search.jsp?keyword="+keyword;	*/
				}
		}
	});
	
	showBanner();
});

/**
 * 控制每个分类的下划线
 */
function showBanner(){
	
var str=window.location+"";
var eventUrl=str.indexOf("/events/");
var spaceUrl=str.indexOf("/space/")
var vipUrl=str.indexOf("/vip/")
var my=str.indexOf("/my/")
	if(eventUrl>0)
	{
		$("#eventsMain").addClass("active")
	}else if(spaceUrl>0)
	{
		$("#spaceMain").addClass("active")
	}else if(vipUrl>0)
	{
		$("#anoutMain").addClass("active")
	}else if(my>0)
	{
		
	}
	else
	{
		$("#spaceMain").addClass("active")
	}
}

function index_exit()
{
	 var tmp=root+"user/logout";
		$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"type":1,"userid":session_loginId},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-失败
				var type = parseInt(json.cord);
				switch (type){
					case 0: 
						window.location.href=root;
						return;
				}
			}
		});
}

/**
 * 是否有未读系统消息
 */
function ifUnRead()
{
	return;
	var tmp=rootjs+"user/getmybroadcastlist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1,"page":0,"size":100,"read":-1,"order":-1,"userid":session_loginId},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//记录全部数目和已读数目
				//$("#totalMsg").text(json.total)
				//$("#unReadMsg").text(json.data2)
				if(json.data2<=0) //有未读消息
					$("#headerUnRead").hide()
				//jQuery("#msgList").append(str);
			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}

/**
 * 弹出登录层
 * @return {TypeName} 
 */
function showTopLogin()
{
	/* $('.header-status-link.status-link-sign').click();*/
		return;
}
