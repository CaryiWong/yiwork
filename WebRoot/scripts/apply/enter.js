$(document).ready(function(e) {
	 
});

/**
 * 保存入驻申请
 */
function saveEnterMenber(){
	util.loading.show(); //打开loading层
	var oldTime=$("#teamArrTime").val();
	var d =oldTime.split("-");
	var time=new Date(d[0],d[1],d[2])
	time=time.getTime();

	var tmp=root+"website/addjoinapplication";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1,"teamname":$("#teamName").val(),"teamintroduce":$("#teamSummary").val(),"teampeople":$("#teamPelNum").val(),"teamtype":$("#teamJob").val(),"settleddate":time},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
			 	util.loading.close();
			 	alert("提交入驻申请成功,我们会尽快和你联系");
			 	//window.location.reload();
			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}