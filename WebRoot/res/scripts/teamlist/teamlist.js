$(document).ready(function(e){
	loadTeamList();
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
		window.location.href=root+"admin/display/teaminfodetail?id="+id;	
	})
	
})

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
   
	var tmp=root+"display/saveorderlist"
 	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"ids":arr,"idtype":1,"type":1},
			dataType:"json",
			success: function(json){
				if(json.cord==0){
					alert("保存成功！");
				}else{
					alert("保存失败！");
				}
				loadTeamList();  //重新加载数据刷新
				$('.enter-list-group').sortable(); //调用排序
			}
		});
}

/**
 * 加载hugo列表
 */
function loadTeamList()
{
	var tmp=root+"display/getteamlist";
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
                    str+='<img src="'+root+'download/img?path='+json.data[i].teamminim+'" class="col-xs-4 col-xs-offset-2">'
                    str+='<div class="col-xs-6">'
                    str+='<h5>'+json.data[i].teamname+'</h5>'
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



function uploadimg(){
	$.ajaxFileUpload( 
     { 
     	//用于文件上传的服务器端请求的Action地址 
     	url:root+"upload/uploadimg",
     	type:"post",//请求方法 
     	secureuri:false,//一般设置为false 
     	fileElementId:'imgfile',//文件id属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
     	success:function(json)  { 
     		var result = JSON.parse(json);
     		if(result.cord==0){
     			$("#teamminim").val(result.data);
     			$("#yixuantouxian").show();
     			$("#hugoicon").attr("src",root+"download/img?type=1&path="+result.data);
     		}else{
     			alert("上传失败");
     		}
		}
     });
}

function uploadmaximg(){
	$.ajaxFileUpload( 
     { 
     	//用于文件上传的服务器端请求的Action地址 
     	url:root+"upload/uploadimg",
     	type:"post",//请求方法 
     	secureuri:false,//一般设置为false 
     	fileElementId:'maximgfile',//文件id属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
     	success:function(json)  { 
     		var result = JSON.parse(json);
     		if(result.cord==0){
     			$("#teamimg").val(result.data);
     			$("#yixuanfengmian").show();
     			$("#hugocover").attr("src",root+"download/img?type=1&path="+result.data);
     		}else{
     			alert("上传失败");
     		}
		}
     });
}



function checkform(form){
	var len = $("input[name=groups]:checked").length;
	var name = $("#teamname").val();
	var icon = $("#teamminim").val();
	var cover = $("#teamimg").val();
	var summery = $("#teamtitle").val();
	if(len>3||len==0){
		alert("标签为1～3个");
		return false;
	}else if(name=="" ||icon==""||cover==""||summery==""){
		alert("请正确填写");
		return false;
	}else{
		return true;
	}
}
