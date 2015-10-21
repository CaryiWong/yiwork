$(document).ready(function(e) {
	 loadMsg();
})

/**
 * 阅读广播
 */
function loadMsg()
{
	var tmp=root+"user/readbroadcast";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"id":msgId,"type":1,"userid":session_loginId},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
				var str="";
				var oldTime = (new Date(json.data.createdate)); //得到毫秒数  
			    var newTime = new Date(oldTime); 		//得到普通的时间
				var time=newTime.getFullYear()+"-"+(newTime.getMonth()+1)+"-"+newTime.getDate();
				//记录全部数目和已读数目
				jQuery("#msgTitle").text(json.data.title);
				jQuery("#msgSummary").text(json.data.texts);
				jQuery("#msgTime").text(time);
			}else
			{
				alert("404错误")
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}
 