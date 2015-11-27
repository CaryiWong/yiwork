$(function () {
    var userId = $.cookie('userid'),
        locationOriginalURL = 'http://test.yi-gather.com:1717',
        isVIP = false;
    var $alert = $('.alert');
    var Ua = navigator.userAgent;
    var ua = Ua.toLocaleLowerCase();
    var action = {
        ios :function(method,params){
            window.iosWebParams = function () {
                return params;
            };
            window.location.href = method ;
        },
        android : function(method,params){
            if(window.yiqi && window.yiqi[method]){
                window.yiqi[method](params);
            }
        }
    };
    var uaNow = function(){
        if (ua.match('yiqi') && !ua.match('micromessenger')) {
            if (ua.match('iphone' || 'ipod' || 'ipad')) {
                return 'ios';
            } else if (ua.match('android')) {
                return 'android';
            }
        }
    };

    action[uaNow()]('isShare', 0);
    $('.btn-introduce').on('touchstart',function(event){
        var $this = $(this);
        if(isVIP){
            $this.attr('href','/pages/server/applyForms.html');
        }else{
            event.preventDefault();
            $alert.fadeIn(800);
            setTimeout(function(){ $alert.fadeOut(800); }, 2000);
        }
    });
    //利用userid获取用户信息
    $.ajax(
        locationOriginalURL + '/v20/user/getuser', {
            dataType: 'json',
            type: 'POST',
            data: {
                type: 'web',
                userid: userId
            }
        }).success(function (data) {
            if (data.cord === 0) {
                 if(data.data.root < 3) {
                     isVIP = true;
                 }
            } else {
                alert('获取用户信息失败，请检查您是否已登录');
            }
        }).fail(function () {
            alert('获取服务信息失败，请检查您是否已登录');
        });
});