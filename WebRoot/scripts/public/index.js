$(document).ready(function(e) {
	 loadActities(0); //加载首页活动数据并且分页
	 loadUserList(0); //加载首页会员列表并且分页
	 
    // 活动内容hover状态展开
    var $titles = $('.activity-item');
    $titles.hover(function (){
        $(this).find('.activity-title').height(80)
    } , function (){
        $(this).find('.activity-title').height(15)
    })

});

/**
 * 加载首页活动数据并且分页
 */
function loadActities(num)
{
	var tmp=root+"/activity/getactivitylist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type": 1, "page":num, "size":4},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				if(num>=json.pagecount || num<0)
				{
					return;
				}
				//加载列表数据
				var str="";
				for(var i=0;i<json.data.length;i++){
					var d=new Date(json.data[i].createdate);
					str+='<div id="'+i+'" class="activity-item">';
					str+='<img class="activity-img"  src="'+root+'download/img?type=1&path='+json.data[i].img+'"/>';
					str+='<div class="activity-title">'+json.data[i].title+'';
					str+='<small class="activity-title-small" title='+json.data[i].summary+'>'+json.data[i].summary+'</small></div>';
					str+='<div class="activity-time">';
					str+='<div class="activity-time-year">'+(d.getYear()+1900)+'.'+(d.getMonth()+1)+'</div>';
					str+=' <div class="activity-time-date">'+d.getDate()+'</div>';
					str+='</div></div>';
				}
				jQuery("#activityList").html(str);
				//处理分页 是否有分页
				var pages="";
				pages+='<button class="panel-header-button left" onclick="loadActities('+(json.pagenum-2)+')">&lt;</button>&nbsp;';
				pages+='<button class="panel-header-button right" onclick="loadActities('+(json.pagenum)+')">&gt;</button>';
				jQuery("#activityListpage").html(pages);
			}
		}
	});
}

/**
 * 加载首页会员列表并且分页
 */
function loadUserList(num)
{
	var tmp=root+"/user/getuserinfolist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type": 1, "page":num, "size":4},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				if(num>=json.pagecount || num<0)
				{
					return;
				}
				//加载列表数据
				var str="";
				for(var i=0;i<json.data.length;i++){
					var introduction='';
					if(json.data[i].introduction=='')
					{
						introduction="这家伙很懒,什么都没有留下..";
					}else{
						introduction=json.data[i].introduction
					}
					str+='<div id="'+i+'" class="member">';
					str+='<img class="member-img"  src="'+root+'download/img?type=1&path='+json.data[i].basicinfo.img+'"/>';
					str+='<div class="member-info">';
					str+='<div class="member-name">'+json.data[i].basicinfo.nickname+'</div>';
					str+='<div class="member-job">'+json.data[i].jobinfo.job+'</div>';
					str+=' <div class="member-introduction">'+introduction+'</div>'
					str+='</div></div>';
				}
				jQuery("#userList").html(str);
				//处理分页 是否有分页
				var pages="";
				pages+='<button class="panel-header-button left" onclick="loadUserList('+(json.pagenum-2)+')">&lt;</button>&nbsp;';
				pages+='<button class="panel-header-button right" onclick="loadUserList('+(json.pagenum)+')">&gt;</button>';
				jQuery("#UserListpage").html(pages);
			}
		}
	});
	
}








