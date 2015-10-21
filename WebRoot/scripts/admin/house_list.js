$(function () {
    var path = window.location.origin + '/';
    //var path = 'http://192.168.1.30:8080/';
    var statusObj = {
        submit: '待确定',
        sure: '待付款',
        success: '待消费',
        cancel: '已取消'
    };

    function formatItem(obj) {
        var status = statusObj[obj.ordertatus];
        var html = '';
        var $button = $('<button data-id="'+ obj.orderid +'" class="order-button btn btn-primary btn-sm">发送验证码</button>');

        if(obj.ordertatus !== 'submit'){
            $button.attr('disabled', true);
        }

        html += '<tr class="order-item">'+
            '<td class="order-tel">'+ obj.tel +'</ td>' +
            '<td>'+ obj.peoplename +'</ td>' +
            '<td>'+ obj.name +'</ td>' +
            '<td>'+ obj.buypeople +'</ td>' +
            '<td>'+ obj.buynum +'</ td>' +
            '<td>'+ obj.price +'</ td>' +
            '<td>'+ obj.comeintime.split(' ')[0] +'</ td>' +
            '<td>'+ obj.couponnumber +'</ td>' +
            '<td>'+ status +'</ td>' +
            '<td>'+ obj.orderid +'</ td>' +
            '<td>'+ obj.createtime +'</ td>' +
            '<td>'+ $button[0].outerHTML +'</td>';
        return html;
    }

    // 加载订单列表
    var $table = $('.list-table tbody');
    $.ajax(path + 'yg/order/orderlist', {
        dataType: 'json',
        type: 'POST',
        data: {size: 99, page: 0}
    }).success(function (data) {
        if (data.cord === 0) {
            var html = '';
            data.data.forEach(function (item) {
                html += formatItem(item);
            });
            $table.html(html);
        } else {
            alert(data.msg);
        }
    }).fail(function () {
        alert('获取订单列表失败');
    });

    // 发短信
    $('body').on('click', '.order-button', function () {
        function sendFail() {
            $this.attr('disabled', false).removeClass('disabled');
        }
        var $this = $(this);
        $this.attr('disabled', true).addClass('disabled');
        $.ajax(path + 'yg/order/sendvcode', {
            dataType: 'json',
            type: 'POST',
            data: {
                orderid: $this.attr('data-id')
            }
        }).success(function (data) {
            if(data.cord === 0){
                alert('发送成功');
            } else {
                alert(data.msg);
                sendFail();
            }
        }).error(function () {
            alert('发送短信失败');
            sendFail();
        })
    })


});
