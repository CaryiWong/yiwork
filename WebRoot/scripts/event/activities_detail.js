var isJoin=0; //是否报名0未报名，1已报名

$(document).ready(function(e) {	 
	loadEventMainInfo();
	//loadReplies(0);//加载评论列表
	//ifJoind();
	//loadMenberList(); //加载已报名用户列表
	//loadMenber(); //加载已报用户
	//$("#event_join").click(function(){joinEvent()})  //报名活动 暂时屏蔽
	//$("#confirmButton").click(function(){window.location.href=root+"apply/activity.jsp?event_id="+event_id})
	//$("#kcm").timeago();
	//var $body1=$('body');
	//$body1.on('click','.page-item',function(){(loadReplies($(this).attr("value")))})
	
});

/*统一前缀*/
var rootjs= root+"v20/";

/**
 * 加载已报名用户列表
 * @return {TypeName} 
 */
function loadMenberList()
{
	var tmp=rootjs+"activity/getjoinslist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"page":0, "userid":session_loginId,"id":event_id,"size":99},
		dataType:"json",
		success: function(json){
			window.setTimeout("util.loading.close()", 100);// 加载完成之后取消loading
			$("#jionNum").text("已报名人数"+json.data.length+"人")//已经报名人数
			for(var i=0;i<json.data.length;i++)
			{
				loadMenber(json.data[i].id)
			}
		}
		});
}


/**
 * 是否加入了活动
 */
function ifJoind()
{
	var tmp=rootjs+"activity/checksignforuser";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type": 1, "userid":session_loginId,"activityid":event_id},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				$("input[name='acttype']").val(2);  //2不可点
				$("#event_join").text("已报名");
			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
	});
	 
}
 
/**
 * 检测活动是否到期
 */
function checkDateOut()
{
	
}


/**
 * 加载活动详情不包括评论
 */
function loadEventMainInfo()
{
	util.loading.show();
	var tmp=rootjs+"activity/getactivity";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		data:{"type": 'web', "id":event_id},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				$("#huiguiframe").attr("src",root+json.data.imgtexturl);
				util.loading.close()
				return;
				var time="";
				var oldTime = (new Date(json.data.opendate)); //得到毫秒数  
				var newTime = new Date(oldTime); 		//得到普通的时间
				var second1=newTime.getMinutes();
				if(newTime.getMinutes()==0)
					second1="00"
				time=newTime.getFullYear()+"年"+(newTime.getMonth()+1)+"月"+newTime.getDate()+"日 "+newTime.getHours()+":"+second1;
			
				
				var oldTime2 = (new Date(json.data.enddate)); //得到毫秒数  
				var newTime2 = new Date(oldTime2); 		//得到普通的时间
				var second=newTime2.getMinutes();
				if(newTime2.getMinutes()==0)
					second="00"
				endtime=newTime2.getFullYear()+"年"+(newTime2.getMonth()+1)+"月"+newTime2.getDate()+"日 "+newTime2.getHours()+":"+second;
				 
				var cost=json.data.price+"元"; //0 免费 , 1 收费
				if(json.data.cost==0) cost="免费";
				$("#event_title").text(json.data.title); //标题
				$("#event_summary").html(json.data.summary.replace(/\n/g,'<br/>')); //简介 $("#event_summary").text().replace(/\n/g,'<br/>')
				$("#event_time").text("");$("#event_time").append('<i class="add-icon-clock"></i> 时间：'+time+"~"+endtime+'')  //时间
				$("#event_address").text("");$("#event_address").append('<i class="add-icon-place"></i> 地点：'+json.data.address+'')  //地点
				$("#event_num").text("");$("#event_num").append('<i class="add-icon-smile"></i> 人数：'+json.data.maxnum+'')  //人数
				$("#event_cost").text("");$("#event_cost").append('<i class="add-icon-money"></i> 费用：'+cost+'')  //收费
				$("#event_tel").text("");$("#event_tel").append('<i class="add-icon-phone"></i> 电话：'+json.data.tel+'')  //电话
				$("#jionNum").text("已报名人数"+json.data.signnum+"人")//已经报名人数
				$("#event_mainImg").attr("src",rootjs+'download/img?type=web&path='+json.data.titleimg)
				ifEventHuigu(json.data.huiguurl)  //是否有回顾
				
				//人数的判断
				if(json.data.createdate<1399637410000)
				{
					//$("#ifshowMore").hide();
					$("#join_userList").hide();
					$("#jionNum").text("已报名人数"+json.data.signnum+"人")//已经报名人数
					window.setTimeout("util.loading.close()", 100);// 加载完成之后取消loading
				}else
				{
					loadMenberList(); //获取用户的报名列表
				}
				
				//alert(json.data.enddate+"==="+json.nowtime)
				//判断是否个性化  优先级 结束时间>自定义活动类型>报名>已报名
				if(json.data.enddate<json.nowtime)
				{
					$("input[name='acttype']").val(2);
					$("#event_join").text("已结束");
				}else{
					if(json.data.acttype==1){  //自定义
						$("#event_join").text(json.data.acttypetitle);
						$("input[name='acttype']").val(1);
						$("input[name='acttypeurl']").val(json.data.acttypeurl);
					}else  //普通类型
					{
						ifJoind();																		
					}
				}
				
				
				
				
				
				
				//如果是当前用户且报名则不可点报名按钮
				var str="";
				//$("#join_userList").text("");  //先情况报名人员数据
// 				for(var i=0;i<json.data.signpeoples.length;i++)
//				{
// 					 loadMenber(json.data.signpeoples[i]);
//					//str+=loadMenber(json.data.signpeoples[i]); //加载已报名的用户头像，昵称等
////					if(json.data.signpeoples[i]==session_loginId)
////					{
////						isJoin=1;
////						$("#event_join").text("已报名");
////					}
//				}
 				//lables "event_labels"
 				if(json.data.label.length>0)
 				{
 					var lables='<span id="" class="info-tags-text">标签：</span>';
 					for(var i=0;i<json.data.label.length;i++)
 					{
 						lables+='<div id='+json.data.label[i].id+' class="info-tag">'+json.data.label[i].zname+'</div>';	
 					}
 					$("#event_labels").text("");
 					$("#event_labels").append(lables);
 				}else
 				{
 						var lables='<span id="" class="info-tags-text">标签：</span>';
 						$("#event_labels").text("");
 						$("#event_labels").append(lables);
 				}
				 
			}else
			{
				alert("404错误");
				//出现404.或者统一页面.服务器出错
			}
		}
	});
	
}

/**
 * 是否有活动回顾
 * @param {Object} url
 */
function ifEventHuigu(url)
{
	if(url.length>0)
	{
		$("#eventHuiGu").show();
		$("#eventIframe").attr("src",root+url);
		//$("#eventIframe1").attr("src",root+url);
	}else
	{
		$("#eventHuiGu").hide();
	}
}


/**
 * 加载评论列表
 */
function loadReplies(num)
{
	//var testid="89d9b1455c514878b3a81394162228597";
	//var tmp=root+"comment/getcommentlist";
	var tmp=rootjs+"comment/findcommentlist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1,"id":event_id,"page":num,"size":12,"listtype":0,"orderby":-1},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				$("#event_commentList").text(""); //清空子元素
				setPage(json.pagenum,json.pagecount,"pages"); //加载分页列表
				for(var i=0;i<json.data.length;i++)
				{
					if(json.data[i].imgs=="")
					{
						addNoPicComment(json.data[i]);	
					}else
					{
						addPicComment(json.data[i]);	
					}
				}
			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}

/**
 * 填充没有图片的评论
 */
function addNoPicComment(json)
{	
	
		  var newTime=new Date(json.createdate); //时间处理
		  var time=getTimeAgo(json.createdate);
	  	  var str='<article class="comment" >'
          str+='<img class="comment-head" src="'+rootjs+'download/img?type=web&path='+json.user.minimg+'"/>' 
          str+='<div class="comment-content" id='+json.id+'>'
          str+='<a class="comment-name" id=commentName'+json.id+'>'+json.user.nickname+'</a>'
          str+='<div class="comment-text">'+json.texts+'</div>' 
          str+='<div class="comment-operate">'
          str+='<button class="comment-reply"><i class="yiqi-icon-comment"></i>回复</button>'
          str+='<time id="kcm" class="comment-time" title='+getLocalTime(json.createdate)+' datetimes='+newTime+'>'+time+'</time>'
          str+='</div>'
          str+='<div class="comment-control clearfix">'
          str+='<input class="comment-reply-text text"/>'
          str+='<button class="comment-reply-button button ">评论</button>'
          str+='</div>'
          str+=addReplies(json.replycomments);
          str+='</div>'
          str+='</article>'
          $("#event_commentList").append(str);
         
}

/**
 * 填充带图片的评论	
 */
function addPicComment(json)
{
	 	  var str='<article class="comment">'
          str+='<img class="comment-head" src="'+rootjs+'download/img?type=web&path='+json.user.minimg+'"/>' 
          str+='<div class="comment-content" id='+json.id+'>'
          str+='<a class="comment-name" id=commentName'+json.id+'>'+json.user.nickname+'</a>'
          str+='<div class="comment-text">'+json.texts+'</div>' 
          str+='<div class="comment-img">'
          for(var i=0;i<json.imgs.length;i++)
          {
        	  str+='<img class="picture"  data-picture-src="'+rootjs+'download/img?type=web&path='+json.imgs[i]+'"  src="'+root+'download/img?type=1&path='+json.imgs[i]+'"/>' 
          }
	 	  str+="</div>"; //图片遍历结束
          // <img class="picture" src="<%=basePath %>images/demo_imgs/4.jpg"/>
	 	  var time=getTimeAgo(json.createdate);
          str+='<div class="comment-operate">'
          str+='<button class="comment-reply"><i class="yiqi-icon-comment"></i>回复</button>'
          str+='<time class="comment-time" title="'+getLocalTime(json.createdate)+'">'+time+'</time>'
          str+='</div>'
          str+='<div class="comment-control clearfix">'
          str+='<input class="comment-reply-text text"/>'
          str+='<button class="comment-reply-button button ">评论</button>'
          str+='</div>'
          str+=addReplies(json.replycomments);
          str+='</div>'
          str+='</article>'
          $("#event_commentList").append(str);
}

/**
 * 加载已报名用户
 */
function loadMenber(userId)
{
	//join_userList
	var tmp=rootjs+"user/getuser";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"userid":userId,"type":1},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//$("#join_userList").text(""); //清空子元素
				var menber_summary="这家伙很懒.什么都没有留下";//json.data.introduction
				if(json.data.introduction!="")menber_summary=getStr(json.data.introduction,20);
				var str="";
				str+='<li class="member">'
                str+='<img class="member-header" title='+json.data.nickname+' src="'+rootjs+'download/img?type=web&path='+json.data.minimg+'"/>' 
            	str+='</li>'
            	$("#join_userList").append(str);
			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}

/**
 * 获取该评论下的所有回复
 * @param {Object} rep
 * @return {TypeName} 
 */
function addReplies(rep)
{
	var str="";
	if(rep!="" &&rep!=null)
	{
		str+='<ul class="comment-reply-list">';
		for(var i=0;i<rep.length;i++)
		{
			str+='<li class="reply-item"><a class="reply-name" href="#">'+rep[i].user.nickname+'</a><span class="reply-text">：'+rep[i].texts+'</span></li>'
		}
		str+='</ul>'
		return str;
	}else
	{
		return "";
	}
}

/**
 * 报名活动 加入活动
 */
function joinEvent()
{
	if(session_loginId=="" || session_loginId==null)
	{
		$('.header-status-link.status-link-sign').click();
		return;
	}
	
	if(isJoin==1)
	{
		return;	
	}
	
	var tmp=rootjs+"activity/joinactivity";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type": 1, "activityid":event_id,"userid":session_loginId},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				alert("报名成功");
				isJoin=1;
				$("#event_join").text("已报名");
			}else
			{
				//出现404.或者统一页面.服务器出错	
			}
		}
	});
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
					sum -= 1;
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

/**
 * 根据long类型的时间，返回多少天，几分钟前等
 * @param {Object} longTime
 */
function getTimeAgo(longTime)
{
	 var minute = 1000 * 60;
     var hour = minute * 60;
     var day = hour * 24;
     var halfamonth = day * 15;
     var month = day * 30;
     var now = new Date().getTime();
     var diffValue = now-longTime;
     
     var result="";
	if(diffValue < 0){
         return "刚刚";
    }					
	var monthC = diffValue / month;
	var weekC = diffValue / (7 * day);
	var dayC = diffValue / day;
	var hourC = diffValue / hour;
	var minC = diffValue / minute;
          if(monthC >=1){         
			 result=parseInt(monthC) + "个月前";			
        	 //return varparam;
          }
          else if(weekC>=1){
             result=parseInt(weekC) + "个星期前";
          }else if(dayC>=1){
             result=parseInt(dayC) +"天前";									 
          }else if(hourC>=1){
             result=parseInt(hourC) +"个小时前";             									 
          } else if(minC>=1){
             result=parseInt(minC) +"分钟前";
          }else
        	 {
        	  var newTime = new Date(longTime); 		//得到普通的时间
			  time=newTime.getFullYear()+"."+(newTime.getMonth()+1)+"."+newTime.getDate();
        	  result=time;
        	 }
     return result;
}

/**
 * 合并JS  08.15
 */

//活动报名二次确认之前
var $applyButton = $('.info-apply').click(function (){
	if($("input[name=acttype]").val()==1){
		window.location.href=$("input[name=acttypeurl]").val();
		return false;
	}
	 
	if(session_loginId!="" && ($("#event_join").text()!="已报名")  && ($("input[name='acttype']").val()!=2))
	{
		//util.dialog.show($applyDialog); //edie by kcm @0414
		window.location.href=root+"apply/activity.jsp?event_id="+event_id; //0512 @kcm
	}else
	{
		//没登录就弹出登录框
		$('.header-status-link.status-link-sign').click();
	}
});









