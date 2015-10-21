$(document).ready(function(e) {
	//$("a[name='setTuiJian']").click(function(){recommend(this.id,this)})
	//$("select[name='acttype']").change(function(){changeSpecialType()});
	//changeSpecialType();
	//$(".spaceChecked").change(function(){changeSpaceView(this)})
	//$(".vipChecked").change(function(){changeUserVier(this)})
});
 

/**
 * 保存活动
 * @param obj
 * @returns {Boolean}
 */
function validateformActivity(obj){
	var address= $("input[name='address']").val(); //地址
	var summary= $("textarea[name='summary']").val(); //简介
	var open= $("input[name='open']").val(); //开始时间
	var title= $("input[name='title']").val(); //标题
	
	if(address=="" && address!=null)
	{
		alert("地点不能为空")
		return false;
	}
	if(summary=="" && summary!=null)
	{
		alert("简介不能为空")
		return false;
	}
	if(open==""  && open!=null)
	{
		alert("开启时间不能为空")
		return false;
	}
	if(title==""  && title!=null)
	{
		alert("标题不能为空")
		return false;
	}
	
	return true;
} 

