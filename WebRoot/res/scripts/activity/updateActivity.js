$(document).ready(function(e){
	showDiv();  //自定义价格，统一价格
	showFree(); //免费,收费
	$("select[name='pricetype']").change(function(){
		showDiv();
	})
	$("select[name='cost']").change(function(){
		showFree();
	})
})
/**
 * 控制自定义价格，统一价格显示隐藏
 */
function showDiv()
{
	var type= $("select[name='pricetype']").val();
	if(type==0)
	{
		$("#zidingyi").hide();$("#tongyi").show();
	}else
	{
		$("#tongyi").hide();$("#zidingyi").show();
	}
}

/**
 * 控制收费，免费层隐藏
 */
function showFree()
{
	var type= $("select[name='cost']").val();
	if(type==0) //免费0 收费1
	{
		$("#shoufei").hide();$("#tongyi").hide();$("#zidingyi").hide();
	}else
	{
		$("#shoufei").show();showDiv();
	}
	 
}

/**
 * 保存活动
 * @param obj
 * @returns {Boolean}
 */
function validateformActivity(obj){
	var type= $("select[name='cost']").val();    //0免费 1收费
	var pricetype= $("select[name='pricetype']").val(); //收费类型 0统一 1自定义
	
	if(type==0)
	{
		$("input[name='pricekey']").val("");
		$("input[name='pricevalue']").val("");
	}else
	{
		if(pricetype==0)  //统一价
		{
			$("input[name='pricekey']").val("1");
			var pri=$("input[name='pricevalueSingle']").val()
			$("input[name='pricevalue']").val(pri);
		}else
		{
			var price="";
			var key="";
			$("input[name='pkey']").each(function(){
				if(key=="")
					key=$(this).val();
				else
				{
					key=key+","+$(this).val();
				}
			})
			
			$("input[name='pval']").each(function(){
				if(price=="")
					price=$(this).val();
				else
				{
					price=price+","+$(this).val();
				}
			})
			$("input[name='pricekey']").val(key);
			$("input[name='pricevalue']").val(price);
		}
	}
	
	return true;
} 

