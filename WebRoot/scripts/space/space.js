$(document).ready(function(e) {
	 loadSpaceImg(); //获取空间的图片
});

var rootjs= root+"v20/";

function loadSpaceImg()
{
	var tmp=rootjs+"website/getspaceshowlist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type": 1},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				
			 	for(var i=0;i<json.data.length;i++)
			 	{
			 		
			 		if(json.data[i].images.length>0)  //图片不为空的话，录入
					{
			 			var str="";
			 			var len=json.data[i].images.length;
			 			//alert(json.data[i].images.length)
			 			for(var a=0;a<json.data[i].images.length;a++)
			 			{
			 				if(a>0)
			 				{
			 					str+='<img style="display:none" class="layoutDetail-img picture" data-picture-src="'+rootjs+'download/img?type=web&path='+json.data[i].images[a]+'" src="'+rootjs+'download/img?type=web&path='+json.data[i].images[a]+'" />'	
			 				}else
			 				{
			 					str+='<img class="layoutDetail-img picture" data-picture-src="'+rootjs+'download/img?type=web&path='+json.data[i].images[a]+'" src="'+rootjs+'download/img?type=web&path='+json.data[i].images[a]+'" />'
			 				}
			 			}
			 			var id=json.data[i].title;
			 		 	$("#"+id).text("");
			 			$("#"+id).append(str)
					}else{ //0506没有图片不显示
						$("#"+json.data[i].title).remove()
					}
			 	}
			}
		}
	});
}