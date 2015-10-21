var lableid="";

$(document).ready(function(e) {
	 window.setTimeout("util.loading.show()", 100)
	 loadActities(0); //加载首页活动数据并且分页 
	 loadLables(); //加载活动标签分类
	 loadBannerEvents();
//	 loadBannerEvents(); //加载banner推荐
	 $(".panel-header-button.add").click(function(){allowCreate()});
	 //$(".activities-nav-link").click(function(){loadByLables(this)})
	 var $body1=$('body');
	$body1.on('click','.page-item',function(){(loadActities($(this).attr("value")))})
});


/*统一前缀*/
var rootjs= root+"v20/";

/**
 * 加载首页活动数据并且分页
 */
function loadActities(num)
{
	var tmp=rootjs+"activity/webactivitylist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type": 1, "page":num, "size":24,"listtype":-1},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				if(num>=json.pagecount || num<0)
				{
					return;
				}
				
				setPage(json.pagenum,json.pagecount,"pagesMain"); //加载分页列表
				//加载列表数据
				var str="";
			
				for(var i=0;i<json.data.length;i++){
					var d = new Date(str);
					if(json.data[i].opendate!='' &&json.data[i].opendate.length>0){
						 str1 = json.data[i].opendate.replace(/-/g,"/");
						 d = new Date(str1);
					}
					 
					 str+='<div class="activity"  onclick="showDetails(this)" id="'+json.data[i].id+'">'
                     str+='<img src="'+rootjs+'download/img?type=web&path='+json.data[i].cover+'" class="activity-img"/>'
                     str+='<div class="activity-info">'
                     str+='<time class="activity-time">'
                     str+='<span class="activity-time-year">'+(d.getYear()+1900)+'.'+(d.getMonth()+1)+'</span>'
                     str+='<span class="activity-time-date">'+d.getDate()+'</span>'
                     str+='</time>'
                     str+='<div class="activity-title">'+json.data[i].title+'</div>'
                     str+='</div>'
                     str+='<div class="activity-apply"><i class="yiqi-icon-smile"></i> 已报名'+json.data[i].joinnum+'人</div>'
                     str+='</div>'
				}
				jQuery("#activityList").text("");
				jQuery("#activityList").append(str);

			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
	});
	util.loading.close();
}

/**
 * 获取标签数据
 */
function loadLables()
{
	//var tmp=root+"labels/getlabellist";
	var tmp=rootjs+"labels/getalllabel"
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
				var str="";
				str+='<a id="0" class="activities-nav-link" href="javascript:void(0)">全部</a>';
				for(var i=0;i<json.data.length;i++){
				 	str+='<a id='+json.data[i].id+' class="activities-nav-link" href="javascript:void(0)">'+json.data[i].zname+'</a>';
				}
				jQuery("#event_lables").text("");
				jQuery("#event_lables").append(str);
			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}


/**
 * 加载banner推荐活动列表
 */
function loadBannerEvents()
{
	var arrayObj = new Array("#E0E0E0","#E0E0E0","#E0E0E0","#E0E0E0","#E0E0E0","#E0E0E0","#E0E0E0","#E0E0E0");
	var tmp=rootjs+"activity/getbanneractlist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				window.setTimeout("util.loading.close()", 100)
				//加载列表数据
				var str="";
				var smallStr="";
				for(var i=0;i<json.data.length;i++){
				 	str+='<li class="slide-item" data-slide-bgColor="'+arrayObj[i]+'" >'
                    str+='<img src="'+rootjs+'download/img?type=web&path='+json.data[i].titleimg+'" class="slide-item-img"/>'
                    str+='<div class="slide-item-info">'
                    str+='<div class="slide-item-text">'
                    str+=json.data[i].title  //+"- 透镜之下－Kreuzzz帽子展"	
                    str+='</div>'
                    str+='<a id='+json.data[i].id+' onclick="showDetails(this)" class="slide-item-button">点击报名</a>'
                    str+='</div>'
                	str+='</li>'
                		
                	smallStr+='<div class="slide-button active">'
                    smallStr+='<img src="'+rootjs+'download/img?type=web&path='+json.data[i].titleimg+'" class="slide-button-img"/>'
                    smallStr+='<div class="slide-button-mask"></div>'
                	smallStr+='</div>'
				}
				jQuery("#bannerList").text("");
				jQuery("#bannerList").append(str);
				
				jQuery("#bannerListSmall").text("");
				jQuery("#bannerListSmall").append(smallStr);
			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
	});
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

/**
 * 根据标签ID拉数据
 * @param {Object} obj
 */
function loadByLables(obj)
{
	if(obj.id!="")
	{
		if(obj.id==0)  //0代表是全部分类
		{
			loadActities(0);
			return false;
		}
	 
		//var tmp=root+"activity/getactivitylistbylabel";
		var tmp=rootjs+"activity/getactivitylistbylabel";
		
		jQuery.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"page":0,"size":24,"type":1,"label":obj.id},
			dataType:"json",
			success: function(json){
				if(json.cord==0){
						//加载列表数据
						 
						var str="";
						setPage(json.pagenum,json.pagecount,"pagesMain"); //加载分页列表
						for(var i=0;i<json.data.length;i++){
							 var d=new Date(json.data[i].opendate);
							 str+='<div class="activity" onclick="showDetails(this)" id="'+json.data[i].id+'">'
		                     str+='<img src="'+root+'download/img?type=web&path='+json.data[i].img+'_300X420" class="activity-img"/>'
		                     str+='<div class="activity-info">'
		                     str+='<time class="activity-time">'
		                     str+='<span class="activity-time-year">'+(d.getYear()+1900)+'.'+(d.getMonth()+1)+'</span>'
		                     str+='<span class="activity-time-date">'+d.getDate()+'</span>'
		                     str+='</time>'
		                     str+='<div class="activity-title">'+json.data[i].title+'</div>'
		                     str+='</div>'
		                     str+='<div class="activity-apply"><i class="yiqi-icon-smile"></i> 已报名'+json.data[i].signnum+'人</div>'
		                     str+='</div>'
						}
					jQuery("#activityList").text("");	
					jQuery("#activityList").append(str);
				}else
				{
					//出现404.或者统一页面.服务器出错
				}
			}
		});
	}
	
	//alert(obj.id+"name="+obj.text)
	$("#"+obj.id).addClass("active")
	
}


/**
 * 点击创建活动，必须要用权限，且登录0 系统 1 管理员 2 会员 3普通用户
 */
function allowCreate()
{
	if(session_loginId=="")
	{
		 $('.header-status-link.status-link-sign').click();
		return;
	}
	 
	if(user_root>2)
	{
		alert("只有收费会员才能创建活动")
		return;	
	}
	window.location.href=root+"events/newActivity.jsp";
}



