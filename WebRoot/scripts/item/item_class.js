function modifyItemClass(item_class_id){
    $('#id_modify_item_class_id').val(item_class_id);
    $('#id_modify_item_class_modal').modal();
}

function doModifyItemClass(){
     $.ajax({
         url:"modify_item_class",
         data:{
             'id': $('#id_modify_item_class_id').val(),
             'name':$('#id_modify_item_class_name').val()
             },
         method:"post",
         dataType:"json",
         success:function(recv_data){
             $('#id_modify_item_class_modal').modal("hide");
             location.href="item_class_list";
         },
         error:function(){
             alert("出错了，请稍后再试");
         }
     });
}

function deleteItemClass(item_class_id){
    $('#id_delete_item_class_id').val(item_class_id);
    $('#id_delete_item_class_modal').modal();
}

function doDeleteItemClass(){
    $.ajax({
        url:"delete_item_class",
        method:"post",
        data:{'id':$("#id_delete_item_class_id").val()},
        dataType:"json",
        success:function(recv_data)
        {
            $('#id_delete_item_class_modal').modal("hide");
            location.href="item_class_list";
        },
        error:function()
        {
            alert("出错了，请稍后再试");
        }             
    });
}

$().ready(function(){
    $('#id_btn_modify').bind('click', function(e){
        e.preventDefault();
        doModifyItemClass();
    });
    $('#id_btn_delete').bind('click', function(e){
        e.preventDefault();
        doDeleteItemClass();
    });
});

