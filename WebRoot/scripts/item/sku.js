function doAddSku(){
     $.ajax({
         url:"add_sku",
         data:{
            'item_class_id': $('#id_item_class_id').val(),
            'sku_name':  $('#new_sku_name').val(),
            'default_price':  $('#new_default_price').val(),
            'member_price':  $('#new_member_price').val(),
            'is_unlimited':  $('#new_is_unlimited').val()
         },
         method:"post",
         dataType:"json",
         success:function(recv_data){
             location.href="skulist?pageSize=99";
         },
         error:function(){
             alert("出错了，请稍后再试");
         }
     });
}

function gotoInventory(sku_id) {
    location.href="inventory?pageSize=20&sku_id="+sku_id;
}

function modifySku(sku_id, item_class_id, name, default_price, member_price){
    $('#id_modify_sku_id').val(sku_id);
    $('#id_modify_sku_name').val(name);
    $('#id_modify_sku_item_class_id').val(item_class_id);
    $('#id_modify_sku_price').val(default_price);
    $('#id_modify_sku_member_price').val(member_price);
    $('#id_modify_sku_modal').modal();
}

function doModifySku(){
     $.ajax({
         url:"modify_sku",
         data:{
             'sku_id': $('#id_modify_sku_id').val(),
             'sku_name':$('#id_modify_sku_name').val(),
             'item_class_id':$('#id_modify_sku_item_class_id').val(),
             'default_price':$('#id_modify_sku_price').val(),
             'member_price':$('#id_modify_sku_member_price').val()
             },
         method:"post",
         dataType:"json",
         success:function(recv_data){
             $('#id_modify_item_class_modal').modal("hide");
             location.href="skulist?pageSize=99";
         },
         error:function(){
             alert("出错了，请稍后再试");
         }
     });
}

function deleteSku(sku_id){
    $('#id_delete_sku_id').val(sku_id);
    $('#id_delete_sku_modal').modal();
}

function doDeleteSku(){
    $.ajax({
        url:"delete_sku",
        method:"post",
        data:{'sku_id':$("#id_delete_sku_id").val()},
        dataType:"json",
        success:function(recv_data)
        {
            $('#id_delete_sku_modal').modal("hide");
            location.href="skulist?pageSize=99";
        },
        error:function()
        {
            alert("出错了，请稍后再试");
        }             
    });
}

function putOnShelves(sku_id){
    $.ajax({
        url:"put_on_shelves",
        method:"post",
        data:{'sku_id':sku_id},
        dataType:"json",
        success:function(recv_data)
        {
            location.href="skulist?pageSize=99";
        },
        error:function()
        {
            alert("出错了，请稍后再试");
        }             
    });
}

function getOffShelves(sku_id){
    $.ajax({
        url:"get_off_shelves",
        method:"post",
        data:{'sku_id':sku_id},
        dataType:"json",
        success:function(recv_data)
        {
            location.href="skulist?pageSize=99";
        },
        error:function()
        {
            alert("出错了，请稍后再试");
        }             
    });
}

$().ready(function(){
    $('#id_add_sku_btn').bind('click', function(e){
        e.preventDefault();
        doAddSku();
    });
    
    $('#id_btn_modify').bind('click', function(e){
        e.preventDefault();
        doModifySku();
    });
    $('#id_btn_delete').bind('click', function(e){
        e.preventDefault();
        doDeleteSku();
    });
});

