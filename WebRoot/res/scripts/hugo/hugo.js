$(document).ready(function(e){
	loadHugoList();
	$("#saveBut").click(function(){saveSort()})
	$('.enter-list-group').sortable(); //调用排序
	//排序变化之后保存
	$('.enter-list-group').sortable().bind('sortupdate', function() {
			var idstr="";
    		$(".enter-list-item").each(function(){
    			idstr += $(this).attr("id") + ",";
    		})
    		  if (idstr.length > 0) {
                    idstr = idstr.substring(0, idstr.length - 1)
    			}
    		//saveSort(idstr);
		});
	//删除元素
	$(".btn-danger.btn-block").click(function(){
		$(this).parent().parent().remove();
	})
	//修改跳转
	$(".btn-default.btn-block").click(function(){
		var id = $(this).parent().parent().attr("id");
		window.location.href=rootjs+"admin/display/hugodetail?id="+id;	
	})
	
	
})

var rootjs= root+"v20/";

/**
 * 保存排序，序列用数组保存
 * @memberOf {TypeName} 
 */
function saveSort()
{	
	var arr=new Array();
	i=0;
	$(".enter-list-item").each(function(){
    	arr[i] = $(this).attr("id")
    	i++;
    })
   
	var tmp=rootjs+"display/saveorderlist"
 	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"ids":arr,"idtype":0,"type":1},
			dataType:"json",
			success: function(json){
				if(json.cord==0){
					alert("保存成功！");
				}else{
					alert("保存失败！");
				}
				loadHugoList();  //重新加载数据刷新
				$('.enter-list-group').sortable(); //调用排序
			}
		});
}

/**
 * 加载hugo列表
 */
function loadHugoList()
{
	var tmp=rootjs+"display/gethugolist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1,"page":0,"size":100},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
				var str="";
				for(var i=0;i<json.data.length;i++){
					str+='<div id="'+json.data[i].id+'" class="col-xs-3 enter-list-item">'
                    str+='<div class="enter-item-index">'+(i+1)+'</div>'
                    str+='<img src="'+rootjs+'download/img?path='+json.data[i].iusermaximg+'" class="col-xs-4 col-xs-offset-2">'
                    str+='<div class="col-xs-6">'
                    str+='<h5>'+json.data[i].iusernickname+'</h5>'
                    str+='<button class="btn btn-sm btn-block btn-default">修改</button>'
                    str+='<button class="btn btn-sm btn-block btn-danger">删除</button>'
                    str+='</div>'
                	str+='</div>'
				 	//str+='<a id='+json.data[i].id+' class="activities-nav-link" href="javascript:void(0)">'+json.data[i].zname+'</a>';
				}
				jQuery(".enter-list-group").text("");
				jQuery(".enter-list-group").append(str);
			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}
