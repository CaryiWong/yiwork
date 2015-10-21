$(document).ready(function(e) {
	loadUserMessage(0);
	 var $body1=$('body');
	$body1.on('click','.page-item',function(){(loadUserMessage($(this).attr("value")))})
})


 

/**
 * 加载用户的系统消息
 */
function loadUserMessage(num)
{
	var tmp=root+"user/getmybroadcastlist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1,"page":num,"size":12,"read":-1,"order":-1,"userid":session_loginId},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
				setPage(json.pagenum,json.pagecount,"pagesNotice"); //加载分页列表
				var str="";
				for(var i=0;i<json.data.length;i++){
					 var oldTime = (new Date(json.data[i].createdate)); //得到毫秒数  
					 var newTime = new Date(oldTime); 		//得到普通的时间
					 var time=newTime.getFullYear()+"-"+(newTime.getMonth()+1)+"-"+newTime.getDate();
				 	 str+='<li class="myNotice-list-item resultList-item">'
                     str+='<div class="myNotice-item-header">'
                     str+='<div id="'+json.data[i].id+'" class="myNotice-item-title" style="cursor:pointer" onclick="readMsg(this)">'+json.data[i].title+'</div>'
                     str+='<time class="myNotice-item-time">'+time+'</time>'
                     str+='</div>'
                     str+='<div class="myNotice-item-content">'+json.data[i].texts;
                     str+='</div>'
               		 str+='</li>'
				}
				//记录全部数目和已读数目
				$("#totalMsg").text(json.total)
				$("#unReadMsg").text(json.data2)
				jQuery("#msgList").text("");
				jQuery("#msgList").append(str);
			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}



/**
 * 阅读广播
 */
function readMsg(obj)
{
	if(obj.id=="" || obj.id==null )
	{
		return;
	}else
	{
		window.location.href=root+"my/noticeDetail.jsp?msgId="+obj.id;	
	}
}

