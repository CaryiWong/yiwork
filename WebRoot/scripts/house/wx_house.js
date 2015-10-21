// 针对weixin.js 的二次封装
define(['server', 'weixin'], function (server, weixin) {
    server.getWeixinConfig().done(function (data) {
        weixin.config({
            debug: false,
            appId: 'wxa7f35f92e66817e5',
            timestamp: data.timestamp,
            nonceStr: data.noncestr,
            signature: data.signature,
            jsApiList: ['chooseWXPay']
        });
    });

    weixin.error(function (err) {
        //alert('weixin error ' + JSON.stringify(err));
    });

    weixin.quickPay = function (openid, orderid, config) {
        config = config || {};
        server.pay(openid, orderid)
            .done(function (data) {
                weixin.chooseWXPay({
                    timestamp: data.timestamp,
                    nonceStr: data.nonceStr,
                    package: data.package.package,
                    signType: data.package.signType,
                    paySign: data.sign,
                    success: config.success,
                    fail: config.fail,
                    cancel: config.cancel
                })
            })
    }

    weixin.simplePay = function (obj){
        weixin.chooseWXPay({
            timestamp: obj.timestamp,
            nonceStr: obj.nonceStr,
            package: obj.package.package,
            signType: obj.signType,
            paySign: obj.paySign,
            success: obj.success,
            fail: obj.fail,
            cancel: obj.cancel
        })
    }

    return weixin;
})
