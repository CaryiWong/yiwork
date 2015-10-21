
/**
 * 获取标签数据
 */
function loadLables(id)
{
	var tmp=root+"labels/getlabellist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
				var str="";
				for(var i=0;i<json.data.length;i++){
				 	str+='<div class="checkbox">'
                    str+='<label>'
                    str+='<input id='+json.data[i].id+' type="checkbox">'+json.data[i].zname
                    str+='</label>'
                    str+='</div>'
				}
				jQuery("#"+id).text("");
				jQuery("#"+id).append(str);
			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}
