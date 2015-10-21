$(document).ready(function(e){
	 
})

root=root+"v20";
function saveVersion(){
		var platform = $("#platform").val();
		var version = $("#version").val();
		var ver = $("#ver").val();
		var url = $("#url").val();
		var description = $("#description").val();
		
		if(platform=="" ||version=="" ||ver =="" ||url=="" ||description==""){	
			alert("表单不完整")
			return;
		}
		
	var tmp=root+"/version/createnewversion";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"platform":platform,"version":version,"ver":ver,"url":url,"description":description},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
			 	 alert("保存成功");
			 	 window.location.href=root+"version/versionlist?pageSize=99";
			}else
			{
				alert(json.msg)
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}