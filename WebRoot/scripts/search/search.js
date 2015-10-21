$(document).ready(function(e) {
	searchByWord();
	//alert(keyword)
	$("#hrefUserSearch").click(function(){window.location.href=root+"search/searchByUser.jsp?keyword="+keyword})
	//回车搜索
	$("#eventSearchText").keypress(function(event){
		if(event.keyCode==13)
		{
			window.location.href=root+"search/search.jsp?keyword="+$("#eventSearchText").val();
		}
	})
	//点击搜索
	$("#eventSearchIcon").click(function(){window.location.href=root+"search/search.jsp?keyword="+$("#eventSearchText").val();})
})

/**
 * 根据关键字搜索
 */
function searchByWord()
{
	if(keyword<0)
	{
		return false;	
	}
	var tmp=root+"activity/searchactivity";
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
					 str+='<li onclick="showDetails(this)" id="'+json.data[i].id+'" class="search-result-list-item resultList-item" style="cursor:pointer">'
                     str+='<img class="search-result-img" src="'+root+'download/img?type=1&path='+json.data[i].titleimg+'"/>'
                     str+='<div class="search-result-info">'
                     str+='<div class="search-result-title">'+json.data[i].title+'</div>'
                     str+='<div class="search-result-content">'+json.data[i].summary
                     str+='</div>'
                     str+='</div>'
				}
				$("#loadMoreEvent").hide();
				if(json.data.pagesum>1)
				{
					$("#loadMoreEvent").show();
				}
				jQuery("#eventTotalNum").text("共有"+json.total+"条结果");
				jQuery("#eventSearchList").text("");
				jQuery("#eventSearchList").append(str);
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


