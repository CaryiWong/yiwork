define(['jquery'], function ($) {
    var path = window.location.origin + '/';
    //var path = 'http://www.yi-gather.com/';
    //var path = 'http://192.168.1.30:8080/';


    var server = {
        getWeixinConfig: function () {
            var defer = $.Deferred();
            $.ajax(path + 'yg/wxpay/wxjs_sha1', {
                dataType: 'json',
                type: 'POST',
                data: {
                    url: window.location.href.split('#')[0]
                }
            }).success(function (data) {
                if (data.cord === 0) {
                    defer.resolve(data.data);
                } else {
                    alert(data.msg);
                }
            }).fail(function () {
                console.error('获取js api config 失败');
            });
            return defer.promise();
        },
        getUser: function (code) {
            var defer = $.Deferred();
            $.ajax(path + 'yg/wxpay/wx_login', {
                dataType: 'json',
                type: 'POST',
                data: {
                    code: code,
                    state: 'login'
                }
            }).success(function (data) {
                if (data.cord === 0) {
                    defer.resolve(data.data);
                } else {
                    alert(data.msg);
                }
            }).fail(function () {
                console.error('获取用户信息失败');
            });
            return defer.promise();
        },
        bindPhone: function (openid, phone) {
            var dtd = $.Deferred();
            $.ajax(path + 'yg/wxpay/wx_bd_tel', {
                dataType: 'json',
                type: 'POST',
                data: {
                    openid: openid,
                    tel: phone
                }
            }).success(function (data) {
                if (data.cord === 0) {
                    dtd.resolve(data.data);
                } else {
                    alert(data.msg);
                }
            }).fail(function () {
                console.error('bing phone fail');
            });
            return dtd.promise();
        },
        getHouse: function () {
            var defer = $.Deferred();
            $.ajax(path + 'yg/goods/getyginn', {
                dataType: 'json'
            })
                .success(function (data) {
                    if (data.cord === 0) {
                        defer.resolve(data.data);
                    } else {
                        console.error('get house error');
                    }
                })
                .fail(function () {
                    console.error('get house error');
                });
            return defer.promise();
        },
        buy: function (obj) {
            obj.type = 'web';
            var defer = $.Deferred();
            $.ajax(path + 'yg/order/buyyginn', {
                dataType: 'json',
                type: 'POST',
                data: obj
            }).success(function (data) {
                if (data.cord === 0) {
                    defer.resolve(data.data);
                } else {
                    console.error('buy fail');
                }
            }).fail(function () {
                console.error('buy fail');
            });
            return defer.promise();
        },
        pay: function (openid, orderid, code) {
            var dtd = $.Deferred();
            $.ajax(path + 'yg/wxpay/wx_unifiedorder', {
                dataType: 'json',
                type: 'POST',
                data: {
                    openid: openid,
                    orderid: orderid,
                    vcode: code
                }
            }).success(function (data) {
                if (data.cord === 0) {
                    dtd.resolve(data.data);
                } else {
                    alert(data.msg);
                    dtd.reject();
                }
            }).fail(function () {
                console.error('发起微信支付失败');
                dtd.reject();
            });
            return dtd.promise();
        },
        list: function (openid, tel) {
            var obj = {
                openid: openid,
                tel: tel,
                type: 'web',
                page: 0,
                size: 99
            }
            var defer = $.Deferred();
            $.ajax(path + 'yg/order/orderlist', {
                dataType: 'json',
                type: 'POST',
                data: obj
            }).success(function (data) {
                if (data.cord === 0) {
                    defer.resolve(data.data);
                } else {
                    console.error('get list fail');
                }
            }).fail(function () {
                console.error('get list fail');
            });
            return defer.promise();
        },
        getOrder: function (orderid) {
            var defer = $.Deferred();
            if (orderid && orderid.length) {
                $.ajax(path + 'yg/order/yg_orderinfo', {
                    dataType: 'json',
                    type: 'POST',
                    data: {
                        id: orderid
                    }
                }).success(function (data) {
                    if (data.cord === 0) {
                        defer.resolve(data.data)
                    } else {
                        alert('获取订单详情失败');
                        defer.reject();
                    }
                }).fail(function () {
                    alert('获取订单详情失败');
                    defer.reject();
                });
            }
            return defer.promise();
        },
        sendSms: function (phone) {
            var defer = $.Deferred();
            phone = phone.trim();
            if (phone && phone.length && phone.length === 11) {
                $.ajax(path + 'v20/sms/sendvalidatesms', {
                    dataType: 'json',
                    type: 'POST',
                    data: {
                        type: 'web',
                        telnum: phone
                    }
                }).success(function (data) {
                    if (data.cord === 0) {
                        defer.resolve();
                    } else {
                        alert(data.msg);
                    }
                }).fail(function () {
                    alert('发送短信失败');
                })
            } else {
                alert('请输入正确的手机号码');
            }
            return defer.promise();
        },
        validSms: function (phone, code) {
            if (!(phone && phone.trim().length)) {
                alert('请输入正确的手机号');
                return;
            }
            if (!(code && code.trim().length)) {
                alert('请输入正确的验证码');
                return;
            }
            var defer = $.Deferred();
            $.ajax(path + 'v20/sms/validatecode', {
                dataType: 'json',
                type: 'POST',
                data: {
                    telnum: phone,
                    code: code
                }
            }).success(function (data) {
                if (data.cord === 0) {
                    defer.resolve();
                } else {
                    alert(data.msg);
                }
            }).fail(function () {
                alert('验证短信失败');
            })

            // 模拟验证成功
            //setTimeout(function (){
            //    defer.resolve();
            //}, 1000);

            return defer.promise();
        }
    };
    return server;
})
