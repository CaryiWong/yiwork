$(document).ready(function(e) {
	  loadUserList(0); //加载首页会员列表并且分页
	 // loadTeamList(0); //加载首页入住团队列表并且分页
	  $("#joinMenber").click(function(){window.location.href=root+"apply/menber.jsp"});
	  $("#joinTeam").click(function(){window.location.href=rootjs+"apply/enter.jsp"});
	  
});

/*统一前缀*/
var rootjs= root+"v20/";

/**
 * 加载首页入住团队列表并且分页
 * 
 */
function loadTeamList(num)
{
	var tmp=rootjs+"display/getteamlist";
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
				$("#teamList").text("");
				//加载列表数据
				var str="";
				for(var i=0;i<json.data.length;i++){
					var introduction='';
					if(json.data[i].iusersummery=='')
					{
						introduction="这家伙很懒,什么都没有留下..";
					}else{
						introduction=json.data[i].iusersummery
					}
					
					var title=json.data[i].teamtitle;
					if(json.data[i].teamtitle.length>45)
					{
							title=json.data[i].teamtitle.substring(0,45)+"..."
					}
					
					str+='<div class="member picture" data-picture-src="'+rootjs+'download/img?type=web&path='+json.data[i].teamimg+'_1280X600">'
                    str+='<div class="member-imgArea">'
                    str+='<img class="member-img" src="'+rootjs+'download/img?type=web&path='+json.data[i].teamminim+'"/>';
                    str+='<div class="member-imgMask"></div>'
                    str+='</div>'
                    str+='<div class="member-info">'
                    str+='<div class="member-name">'+json.data[i].teamname+'</div>'
                    str+='<div class="member-introduction" title="'+json.data[i].teamtitle+'">'+title+'</div>'                             
                    str+='</div>'
                    str+='</div>'
				}
				jQuery("#teamList").html(str);
				//处理分页 是否有分页
				var pages="";
				jQuery("#teamListPage").text("");
				
				if(json.pagecount==1)
				{
					pages+='<button class="panel-header-button left disabled" onclick="loadTeamList('+(json.pagenum-2)+')"><i class="yiqi-icon-slLeft"></i></button>&nbsp;';
					pages+='<button class="panel-header-button right disabled" onclick="loadTeamList('+(json.pagenum)+')"><i class="yiqi-icon-slRight"></i></button>';
				}else if(num<json.pagecount-1 &&num!=0)  //可以后翻 前翻
				{
					pages+='<button class="panel-header-button left" onclick="loadTeamList('+(json.pagenum-2)+')"><i class="yiqi-icon-slLeft"></i></button>&nbsp;';
					pages+='<button class="panel-header-button right " onclick="loadTeamList('+(json.pagenum)+')"><i class="yiqi-icon-slRight"></i></button>';
				}else if(num==0 &&num<json.pagecount) //只能后翻
				{
					pages+='<button class="panel-header-button left disabled" onclick="loadTeamList('+(json.pagenum-2)+')"><i class="yiqi-icon-slLeft"></i></button>&nbsp;';
					pages+='<button class="panel-header-button right" onclick="loadTeamList('+(json.pagenum)+')"><i class="yiqi-icon-slRight"></i></button>';
				}else if(num!=0 &&num==json.pagecount-1)// 只能前翻
				{
					pages+='<button class="panel-header-button left" onclick="loadTeamList('+(json.pagenum-2)+')"><i class="yiqi-icon-slLeft"></i></button>&nbsp;';
					pages+='<button class="panel-header-button right disabled" onclick="loadTeamList('+(json.pagenum)+')"><i class="yiqi-icon-slRight"></i></button>';
				}
				
				
				
			
				jQuery("#teamListPage").html(pages);
			}
		}
	});
	
}



/**
 * 加载首页会员列表并且分页
 * vipMain
 */
function loadUserList(num)
{
	var tmp=rootjs+"display/gethugolist";
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
				$("#userList").text("");
				//加载列表数据
				var str="";
				for(var i=0;i<json.data.length;i++){
					var introduction='';
					if(json.data[i].iusersummery=='')
					{
						introduction="这家伙很懒,什么都没有留下..";
					}else{
						introduction=json.data[i].iusersummery
					}
					 str+='<div class="member picture" data-picture-src="'+rootjs+'download/img?type=web&path='+json.data[i].iusermaximg+'_1280X600">'
                     str+='<div class="member-imgArea">'
                     str+='<img class="member-img"  src="'+rootjs+'download/img?type=web&path='+json.data[i].iuserimg+'"/>';
                     str+='<div class="member-imgMask"></div>'
                     str+='</div>'
                     str+='<div class="member-info">'
                     str+='<div class="member-name">'+json.data[i].iusernickname+'</div>'
                     str+='<div class="member-job">'+json.data[i].iuserjobinfo+'</div>'
                     str+='<div class="member-introduction">'+json.data[i].iusersummery+'</div>'
                     str+= '</div>'
                     str+='</div>'		 
				}
				jQuery("#userList").html(str);
				//处理分页 是否有分页
				var pages="";
				jQuery("#UserListpage").text("");
				if(json.pagecount==1)
				{
					pages+='<button class="panel-header-button left disabled" onclick="loadUserList('+(json.pagenum-2)+')"><i class="yiqi-icon-slLeft"></i></button>&nbsp;';
					pages+='<button class="panel-header-button right disabled" onclick="loadUserList('+(json.pagenum)+')"><i class="yiqi-icon-slRight"></i></button>';
				}else if(num<json.pagecount-1 &&num!=0)  //可以后翻 可以前翻
				{
					pages+='<button class="panel-header-button left" onclick="loadUserList('+(json.pagenum-2)+')"><i class="yiqi-icon-slLeft"></i></button>&nbsp;';
					pages+='<button class="panel-header-button right" onclick="loadUserList('+(json.pagenum)+')"><i class="yiqi-icon-slRight"></i></button>';
				}else if(num==0 &&num<json.pagecount) //只能后翻 
				{
					pages+='<button class="panel-header-button left disabled " onclick="loadUserList('+(json.pagenum-2)+')"><i class="yiqi-icon-slLeft"></i></button>&nbsp;';
					pages+='<button class="panel-header-button right" onclick="loadUserList('+(json.pagenum)+')"><i class="yiqi-icon-slRight"></i></button>';
				}else if(num!=0 &&num==json.pagecount-1)// 只能前翻
				{
					pages+='<button class="panel-header-button left" onclick="loadUserList('+(json.pagenum-2)+')"><i class="yiqi-icon-slLeft"></i></button>&nbsp;';
					pages+='<button class="panel-header-button right disabled" onclick="loadUserList('+(json.pagenum)+')"><i class="yiqi-icon-slRight"></i></button>';
				}
				
				jQuery("#UserListpage").html(pages);
			}
		}
	});
	
}








