function cancelOrder(order_id){
    $('#id_order_id').val(order_id);
    $('#id_cancel_order_modal').modal();
}

function doCancelOrder(){
    $.ajax({
        url:"cancel_order",
        method:"post",
        data:{'order_id':$("#id_order_id").val()},
        dataType:"json",
        success:function(recv_data)
        {
            $('#id_cancel_order_modal').modal("hide");
            location.href="order?order_id="+$("#id_order_id").val();
        },
        error:function()
        {
            alert("出错了，请稍后再试");
        }             
    });
}

$().ready(function(){
    $('#id_btn_cancal_order').bind('click', function(e){
        e.preventDefault();
        doCancelOrder();
    });
});

