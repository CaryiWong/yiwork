$(document).ready(function(e){
	loaddata();
})

/*统一前缀*/
var rootjs= root+"v20/";
/**
 * 加载空间区域的数据，自动分组三四楼
 */
function loaddata(){
	var third=["move","read","product","meeting","talk","client","office","toilet","draw","vip","front","door"]
	var fouth=["balcony","client1","client2","resource","bar","stairs","blackboard"]
	var first=["first"]
	var second=["second"]
		
	jQuery(".list-group").text("");
	var tmp=rootjs+"website/getspaceshowlist"
	var str="";
	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"type":1},
			dataType:"json",
			success: function(json){
				if(json.data.length>0){
					for(var i=0;i<json.data.length;i++){
						var divId="fouth";
						str+='<li class="list-group-item space-item">'
	                    str+='<div class="form-group col-xs-12">'+json.data[i].title+':'+json.data[i].name+'<input  name="img" class="activity-comment-file" multiple id="img'+json.data[i].id+'" type="file" >'
	                    str+='</div>'
	                    str+='<div class="clearfix">'
	                    if(json.data[i].images.length>0)
	                    {
	                    	str+='<ul id="ul'+json.data[i].id+'" class="list-inline space-imgList pull-left">'
	                    	for(var a=0;a<json.data[i].images.length;a++)	
	                    	{
	                    		str+='<li id="'+json.data[i].images[a]+'" onclick="delImg(this)">'
	                    		str+='<input type="hidden"  value="'+json.data[i].id+'" id="kcm'+json.data[i].images[a]+'">'
                                str+='<div class="space-imgList-item-remove">'
                                str+='<i class="glyphicon glyphicon-remove"></i>'
                                str+='</div>'
                                str+='<img src="'+rootjs+'download/img?path='+json.data[i].images[a]+'"/>'
                            	str+='</li>'
	                    		//str+='<li><img src="'+root+'download/img?path='+json.data[i].images[a]+'"/></li>'
                      			//str+='<li>'
	                    	}
	                    	str+='</ul>'
	                    }
						str+='<div class="pull-left button-group col-xs-2">'
                        str+='<button id="'+json.data[i].id+'" onclick="saveImg(this)" class="btn btn-primary col-xs-12">保存图片</button>'
                        str+='</div>'
                        str+='</div>'
                		str+='</li>'
						for(var j=0;j<third.length;j++)
						{
							if(third[j]==json.data[i].title)	
								divId="third";
							else if(json.data[i].title=="first")
								divId="first";
							else if(json.data[i].title=="second")
								divId="second";
						}
                		jQuery("#"+divId).append(str);
                		str="";
					}
				}
			}
		});
}


function saveImg(obj){	
	if(jQuery("#img"+obj.obj).val()==""||jQuery("#img"+obj.id).val().length<1){
		alert("未选择图片");
		return;
	}
	var tmp=rootjs+"upload/uploadimg"
	var img="img"+obj.id;
	//alert($("input[name='img']").length)
 	jQuery.ajaxFileUpload({
			url: tmp,
			type: "POST",
			async:false,
			fileElementId:img,
			dataType:"json",
			success: function(json){
		 		if(json.cord==0){
					$.ajax({
						url: rootjs+"admin/display/updatespaceshow",
						type: "POST",
						async:false,
						data:{"id":obj.id,"img":json.data,"type":1,"flag":0},
						dataType:"json",
						success: function(json){
							if(json.cord==0){
								alert("新增成功")
								window.location.reload();
								//addImgShow(obj.id,json.data)
							}else{
								alert("新增失败");
							}
						}
					});
		 		}else{
		 			alert("新增失败");
		 		}
			}
		});
}

function addImgShow(id,img)
{
	var ulObj=$("#ul"+id);
	str+='<li id="'+img+'" onclick="delImg(this)">'
	str+='<input id="kcm'+img+'" type="hidden" value="1397533050861af4b3373e88644c8a1233a1544731805">'
	str+='<div class="space-imgList-item-remove">'
	str+='<i class="glyphicon glyphicon-remove"></i>'
	str+='</div>'
	str+='<img src="'+rootjs+'download/img?path='+img+'"/>'
	str+='</li>'
	alert(str)
	ulObj.append(str);
}


function delImg(obj){
	if(confirm("确定要删除这个图片嘛？"))
    {
		var imgurl=obj.id;
		var id=$("#kcm"+obj.id).val();
		$.ajax({
			url: rootjs+"admin/display/updatespaceshow",
			type: "POST",
			async:false,
			data:{"id":id,"img":imgurl,"type":1,"flag":1},
			dataType:"json",
			success: function(json){
				if(json.cord==0){
					obj.remove();
				}else{
					alert("删除失败");
				}
			}
		});
	}
}


