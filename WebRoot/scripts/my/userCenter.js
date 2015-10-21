$(document).ready(function(e) {
	checkIfAllow();
	loadUserMainInfo();  //加载登录用户的基本信息
	//loadUserAttendEvents(0);   //加载登录用户报名的活动列表  1.2
	//loadUserCreaterEvents();  //创建的活动
	$("#editBasicInfo").click(function(){window.location.href=root+"my/basicInfo.jsp"}); //编辑基本信息
	$("#editeUser").click(function(){window.location.href=root+"my/basicInfo.jsp"}); //编辑基本信息
	var $body1=$('body');
	$body1.on('click','.page-item',function(){(
			loadUserAttendEvents($(this).attr("value")))
			 //showAny();
		})
	
})

/*统一前缀*/
var rootjs= root+"v20/";

function showAny(num)
{
		//if($(".button.black.myActivities-button.apply").val()!="")
		//{
			loadUserAttendEvents(num);
		//}else
		//{
		// 	loadUserCreaterEvents(num);
		//}
}

/**
 * 非法访问
 */
function checkIfAllow()
{
	if(session_loginId=="")
	{
		alert("请先登录");
		window.location.href=root+"events/activities.jsp"
	}
}


/**
 * 加载登录用户的基本信息
 */
function loadUserMainInfo()
{
	 //var tmp=root+"user/getuserinfo";
	 var tmp=rootjs+"user/getuser";
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
						$("#user_nickName").text(json.data.nickname);  //昵称
						//清空职业,防止职业为空
						$("#user_job").text("");
						$("#user_job").append('<i class="yiqi-icon-card"></i>未填写');
						if(json.data.job!=null){
							//$("#user_job").text("");$("#user_job").append('<i class="yiqi-icon-card"></i>'+json.data.job.zname);  //job
							$("#user_job").text("");$("#user_job").append('<i class="yiqi-icon-card"></i>'+'待定');  //job
						}
						$("#user_address").text("");$("#user_address").append('<i class="yiqi-icon-place"></i>'+json.data.city); //地址
						$("#user_img").attr("src",rootjs+'download/img?type=web&path='+json.data.minimg);   //用户头像
						
						var lingyu="";  //领域标签
						
//						for(var i=0;i<json.data.interestlabel.length;i++)
//						{
//							lingyu+='<li class="domain-list-item"><a id='+json.data.interestlabel[i].id+' class="domain-link" href="#">'+json.data.label[i].zname+'</a></li>'
//						}
						$("#user_lingyu").text(""); $("#user_lingyu").append(lingyu); 
						
						return;
				}
			}
		});
	
}


/**
 * 我创建的活动
 */
function loadUserCreaterEvents()
{
	var tmp=root+"activity/getmyactivitylist";
		$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"userid":session_loginId,"type":1,"page":0,"size":6,"flag":1},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-失败
				var type = parseInt(json.cord);
				switch (type){
					case 0: 
						appendEvent("userCreateEvent",json);
						//setPage(json.pagenum,json.pagecount,"createPages"); //加载分页列表
						return;
				}
			}
		});
}



/**
 * 我报名的活动
 */
function loadUserAttendEvents(num)
{
	var tmp=root+"activity/getmyactivitylist";
		$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"userid":session_loginId,"type":1,"page":num,"size":6,"flag":2},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-失败
				var type = parseInt(json.cord);
				switch (type){
					case 0: 
						appendEvent("userAttendEvent",json);
						setPage(json.pagenum,json.pagecount,"createPages"); //加载分页列表
						return;
				}
			}
		});
	
}


function appendEvent(id,json)
{
	var str="";
	$("#"+id).text("");
	//如果没有创建的活动列表，应该加提示
	for(var i=0;i<json.data.length;i++)
	{
	    var d=new Date(json.data[i].createdate);
		str+='<div class="activity" onclick="showDetails(this)" id="'+json.data[i].id+'">'
        str+='<img class="activity-img"  src="'+root+'download/img?type=1&path='+json.data[i].titleimg+'"/>'   //封面
        str+='<div class="activity-info" style="height: 80px;">'
        str+='<time class="activity-time">'
        str+='<span class="activity-time-year">'+(d.getYear()+1900)+'.'+(d.getMonth()+1)+'</span>'
        str+='<span class="activity-time-date">'+d.getDate()+'</span>'
        str+='</time>'
        str+='<div class="activity-title">'+json.data[i].title+"</div>"
        str+='</div>'
        str+='<div class="activity-apply">'
        str+='已报名'+json.data[i].signnum+'人'
        str+='</div>'
        str+='</div>'
	}
	jQuery("#"+id).html(str);
}


/**
 * 条状到活动详情页面
 * @param {Object} obj
 */
function showDetails(obj)
{
	
	if(obj.id=="" || obj.id==null )
	{
		return;
	}else
	{
		window.location.href=root+"events/activityDetial.jsp?event_id="+obj.id;	
	}
}


