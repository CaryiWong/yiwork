$(document).ready(function(e) {
	searchByWord();
	$("#hrefEventSearch").click(function(){window.location.href=root+"search/search.jsp?keyword="+keyword})
	//回车搜索
	$("#userSearchText").keypress(function(event){
		if(event.keyCode==13)
		{
			window.location.href=root+"search/searchByUser.jsp?keyword="+$("#userSearchText").val();
		}
	})
	//点击搜索
	$("#userSearchIcon").click(function(){window.location.href=root+"search/searchByUser.jsp?keyword="+$("#userSearchText").val();})
})

/**
 * 根据关键字搜索User
 */
function searchByWord()
{
	
	if(keyword.length<=0)
	{
		jQuery("#userTotalNum").text("共有0条结果");
		jQuery("#userSearchList").text("");
		return false;	
	}
	var tmp=root+"display/searchhugo";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1,"page":0,"size":12,"keyword":keyword},
		dataType:"json",		
		success: function(json){
			if(json.cord==0){
				//加载列表数据
				var str="";
				for(var i=0;i<json.data.length;i++){
					 str+='<li id="'+json.data[i].id+'" class="search-result-list-item resultList-item ">'
                     str+='<img class="search-result-img picture" src="'+root+'download/img?type=1&path='+json.data[i].iuserimg+'" data-picture-src="'+root+'download/img?type=1&path='+json.data[i].iusermaximg+'"/>'
                     str+='<div class="search-result-info">'
                     str+='<div class="search-result-title">'+json.data[i].iusernickname+'</div>'
                     str+='<div class="search-result-content">'+json.data[i].iusersummery
                     str+='</div>'
                     str+='</div>'
				}
				$("#loadMoreUser").hide();
				if(json.data.pagecount>1)
				{
					$("#loadMoreUser").show();
				}
				jQuery("#userTotalNum").text("共有"+json.total+"条结果");
				jQuery("#userSearchList").text("");
				jQuery("#userSearchList").append(str);
			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
		});
}


/**
 * 跳转到活动详情页面
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


