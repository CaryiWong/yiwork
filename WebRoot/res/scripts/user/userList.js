$(document).ready(function(e) {
	$(".spaceChecked").change(function(){changeSpaceView(this)})
	$(".vipChecked").change(function(){changeUserVier(this)})
});
 

/**
 * 设置是否空间展示
 * @param {Object} obj
 */
function changeSpaceView(obj)
{
	//0取消展示，1展示
	var isspace=0;
	if(obj.checked) isspace=1;
	var tmp=root+"user/updatevipspace";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1,"id":obj.id,"isspace":isspace},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				alert("设置成功")
				//加载列表数据
			} 
		}
	});
 
}

/**
 * 设置是否会员展示
 * @param {Object} obj
 */
function changeUserVier(obj)
{
	//$('#addVipForm').valid(function(pass){})
	//0取消展示，1展示
	var isshow=0;
	if(obj.checked) isshow=1;
	var tmp=root+"user/updatevipshow";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1,"id":obj.id,"isshow":isshow},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				alert("设置成功")
				//加载列表数据
			} 
		}
	});
}


