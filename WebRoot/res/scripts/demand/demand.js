$(document).ready(function(e) {
	//$("a[name='setTuiJian']").click(function(){recommend(this.id,this)})
	//$("select[name='acttype']").change(function(){changeSpecialType()});
	//changeSpecialType();
	//$(".spaceChecked").change(function(){changeSpaceView(this)})
	//$(".vipChecked").change(function(){changeUserVier(this)})
});
 
var rootjs= root+"v20/";

function setonsale(id,onsale,o){
	var actid = id;
	var submitdata = {
		type:1,
		id:actid,
		onsale:onsale
	};
	$.ajax({
     type: "post",
     url: rootjs+"admin/demand/onsaleDemand",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	alert(result.msg);
     	if(result.cord==0){
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
