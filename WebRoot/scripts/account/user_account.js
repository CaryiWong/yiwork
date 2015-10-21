function freezeAccount(user_id){
    $('#id_user_id').val(user_id);
    $('#id_freeze_user_account_modal').modal();
}

function doFreezeAccount(){
     $.ajax({
         url:"freeze_account",
         data:{"user_id": $('#id_user_id').val()},
         method:"post",
         dataType:"json",
         success:function(recv_data){
             $('#id_freeze_user_account_modal').modal("hide");
             location.href="info?user_id="+$('#id_user_id').val();
         },
         error:function(){
             alert("出错了，请稍后再试");
         }
     });
}

function unfreezeAccount(user_id){
    $.ajax({
        url:"unfreeze_account",
        method:"post",
        data:{"user_id": user_id},
        dataType:"json",
        success:function(recv_data)
        {
            location.href="info?user_id="+user_id;
        },
        error:function()
        {
            alert("出错了，请稍后再试");
        }             
    });
}

function destroyItem(item_instance_id) {
    $('#delete_item_instance_id').val(item_instance_id);
    $('#id_delete_item_modal').modal();
}

function doDestroyItem(){
     $.ajax({
         url:"../item/destroy_item",
         data:{"item_instance_id": $('#delete_item_instance_id').val()},
         method:"post",
         dataType:"json",
         success:function(recv_data){
             $('#id_delete_item_modal').modal("hide");
             $('#mainForm').submit();
         },
         error:function(){
             alert("出错了，请稍后再试");
         }
     });
}

function editBalance(user_id, money_type) {
    $('#edit_user_id').val(user_id);
    $('#edit_money_type').val(money_type);
    $('#id_edit_balance_modal').modal();
}

function doEditBalance() {
    $.ajax({
        url:"edit_user_balance",
        method:"post",
        data:{
            "user_id": $('#edit_user_id').val(),
            "money_type": $('#edit_money_type').val(),
            "action_type": $('#edit_action').val(),
            "money": $('#edit_money').val(),
            "reason": $('#edit_reason').val()
        },
        dataType:"json",
        success:function(recv_data)
        {
            $('#id_edit_balance_modal').modal("hide");
            location.href="info?user_id="+$('#edit_user_id').val();
        },
        error:function()
        {
            alert("出错了，请稍后再试");
        }             
    });
}

$().ready(function(){
    $('#id_freeze_btn').bind('click', function(e){
        e.preventDefault();
        doFreezeAccount();
    });
    $('#id_destroy_item_btn').bind('click', function(e){
        e.preventDefault();
        doDestroyItem();
    });
    $('#id_edit_btn').bind('click', function(e){
        e.preventDefault();
        doEditBalance();
    });
});

