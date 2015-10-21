$(document).ready(function(e) {
	$("a[name='setTuiJian']").click(function(){recommend(this.id,this)})
	$("select[name='acttype']").change(function(){changeSpecialType()});
	changeSpecialType();
	//$(".spaceChecked").change(function(){changeSpaceView(this)})
	//$(".vipChecked").change(function(){changeUserVier(this)})
});
 

var rootjs= root+"v20/";

function changeSpecialType()
{
	var val=$("select[name='acttype']").val();
		$("#specialType").show();
		if(val==0)
			$("#specialType").hide();
}

function recommend(id,o){
	var actid = id;
	var submitdata = {
		actid:actid
	};
	if(id=="")
	{
		return;	
	}
	
	$.ajax({
     type: "post",
     url: rootjs+"admin/activity/recommend",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.result=="s"){
     		alert("推荐成功");
     		//window.location.reload();
     		$(o).html("取消推荐");
     		$(o).removeAttr("href");
     		$(o).removeAttr("name");
     		$(o).removeAttr("id");
     		$(o).removeClass("btn-link")
     		$(o).removeClass("btn-sm")
     		$(o).css({"color":"black"});
     	}else{
     		alert("推荐失败");
     	}
     }
    });
}

function setbanner(id,banner,o){
	var actid = id;
	var submitdata = {
		type:1,
		actid:actid,
		banner:banner
	};
	$.ajax({
     type: "post",
     url: rootjs+"admin/activity/setbanner",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	alert(result.msg);
     	if(result.result=="s"){
     		if(banner==1){
     			$(o).html("取消banner");
     			$(o).attr("onclick","setbanner('"+id+"',0,this);");
     		}else if(banner==0){
     			$(o).html("设置banner");
     			$(o).attr("onclick","setbanner('"+id+"',1,this);");
     		}
     	}
     }
    });
}




/**
 * 审核，驳回活动
 * @param {Object} v
 */
function verify(v){
	var checktype = v;
	var id = $("#id").val();
	var submitdata = {
		id:id,
		checktype:checktype
	};
	$.ajax({
     type: "post",
     url: rootjs+"admin/activity/verifyactivity",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord!=0){
     		alert("审核失败");
     	}else{
     		alert("审核成功");
     		window.location.href=rootjs+"admin/activity/activitylist?pageSize=15";
     	}
     }
    });
	
}
function validateform(obj){
	var nick = $("#nick").val();
	if(nick==1){
		alert("此昵称已被使用，请重新输入一个昵称");
		$("#nick").focus();  //获取焦点
		return false;
	}
	return true;
}

function checknickname(o){
	var nick = o.value;
	var submitdata = {
		nickname:nick
	};
	$.ajax({
     type: "post",
     url: rootjs+"user/checknickname",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord!=0){
     		$("#nick").val(1);
     		alert("此昵称已被使用，请重新输入一个昵称");
			$("#nick").focus(); //获取焦点
     	}
     }
    });
}

function setonsale(id,onsale,o){
	var actid = id;
	var submitdata = {
		type:1,
		id:actid,
		onsale:onsale
	};
	$.ajax({
     type: "post",
     url: rootjs+"admin/activity/setonsale",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	alert(result.msg);
     	if(result.result=="s"){
     		if(onsale==1){
     			$(o).html("上架");
     			$(o).attr("onclick","setonsale('"+id+"',0,this);");
     		}else if(onsale==0){
     			$(o).html("下架");
     			$(o).attr("onclick","setonsale('"+id+"',1,this);");
     		}
     	}
     }
    });
}

function pushactivity(id){
	var actid = id;
	var submitdata = {
		type:1,
		id:actid
	};
	$.ajax({
     type: "post",
     url: rootjs+"admin/activity/pushactivity",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	alert(result.msg);
     }
    });
}
