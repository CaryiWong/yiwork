function increase(sku_id){
    $('#id_increase_sku_id').val(sku_id);
    $('#id_increase_amount').val(0);
    $('#id_increase_modal').modal();
}

function doIncrease(){
     $.ajax({
         url:"inventory_increase",
         data:{
             'sku_id': $('#id_increase_sku_id').val(),
             'amount':$('#id_increase_amount').val()
             },
         method:"post",
         dataType:"json",
         success:function(recv_data){
             $('#id_increase_modal').modal("hide");
             location.href="inventory?sku_id="+$('#id_increase_sku_id').val()+"&pageSize=99";
         },
         error:function(){
             alert("出错了，请稍后再试");
         }
     });
}

function reduce(sku_id){
    $('#id_reduce_sku_id').val(sku_id);
    $('#id_reduce_amount').val(0);
    $('#id_reduce_modal').modal();
}

function doReduce(){
    $.ajax({
        url:"inventory_reduce",
        method:"post",
        data:{
            'sku_id':$("#id_reduce_sku_id").val(),
            'amount':$('#id_reduce_amount').val()
        },
        dataType:"json",
        success:function(recv_data)
        {
            $('#id_reduce_modal').modal("hide");
            location.href="inventory?sku_id="+$('#id_reduce_sku_id').val()+"&pageSize=99";
        },
        error:function()
        {
            alert("出错了，请稍后再试");
        }             
    });
}

$().ready(function(){
    $('#id_increase_btn').bind('click', function(e){
        e.preventDefault();
        doIncrease();
    });
    
    $('#id_reduce_btn').bind('click', function(e){
        e.preventDefault();
        doReduce();
    });
});

