require(['jquery', 'server', 'user'], function ($, server, user) {
    var userInfo;
    user.sign().done(function (info) {
        userInfo = info;
    });

    var $phoneInput = $('.phone-input'),
        $phoneButton = $('.phone-button'),
        $codeInput = $('.code-input'),
        $codeButton = $('.code-button');

    $phoneButton.on('touchstart', function () {
        var phone = $phoneInput.val();
        server.sendSms(phone).done(function () {
            alert('发送成功');
            $phoneButton.addClass('disabled').off('touchstart');
        });
    });

    $codeButton.on('touchstart', function () {
        var phone = $phoneInput.val(),
            code = $codeInput.val();
        server.validSms(phone, code)
            .then(function () {
                return server.bindPhone(userInfo.openid, phone);
            })
            .done(function (data){
                // 绑定成功 存储新实体 跳转回原来页
                user.setLocalUser(data);
                window.location.href = window.localStorage.getItem('backUrl');
            })
    });
});
