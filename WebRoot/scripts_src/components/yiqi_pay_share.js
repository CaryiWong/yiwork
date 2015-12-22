var $ = require('jquery');
var server = require('./yiqi_server');
var wx = require('imports?this=>window!../lib/weixin');
var device = require('../modules/device');
var readyObj = {
    actions: [],
    setState: function (state) {
        this.handle = function (cb) {
            cb && cb(state);
        };
        this.actions.forEach(function (cb) {
            cb.call(state);
        });
    },
    resetState: function () {
        var self = this;
        this.handle = function (cb) {
            self.actions.push(cb);
        }
    },
    handle: function (cb) {
        this.actions.push(cb);
    }
}

var isConfigTrue = true;
function initConfig() {
    if (device.weixin && !device.yiqi) {
        var url = isDev ? 'http://test.yi-gather.com:1717' + window.location.pathname + window.location.hash
            : window.location.href;
        server.post('v20/user/get_config_data', {
            url: url
        }).done(function (data) {
            wx.config({
                debug: isDev,
                appId: 'wxa7f35f92e66817e5',
                timestamp: data.timestamp,
                nonceStr: data.noncestr,
                signature: data.signature,
                jsApiList: ['chooseWXPay', 'onMenuShareTimeline', 'onMenuShareAppMessage', 'hideOptionMenu', 'showOptionMenu']
            });
        }).fail(function (err) {
            alert('获取微信配置失败,' + JSON.stringify(err));
        });
        wx.ready(function () {
            readyObj.setState(isConfigTrue);
        });
        wx.error(function (err) {
            isConfigTrue = false;
        });
    } else {
        readyObj.setState(true);
    }
}
initConfig();

module.exports = {
    ready: function (cb) {
        readyObj.handle(cb);
    },
    weixinPayProxy: function (orderids) {
        window.sessionStorage.setItem('weixin_orders', JSON.stringify(orderids));
        window.location.href = '/pages/pay/weixin_proxy.html';
    },
    weixinShare: function (title, desc, img, link) {
        var defer = $.Deferred();
        wx.onMenuShareTimeline({
            title: title,
            link: link || window.location.href,
            imgUrl: img || window.location.origin + '/images/public/logo_icon_share.png',
            success: function () {
                defer.resolve();
            },
            fail: function () {
                defer.reject();
            },
            cancel: function () {
                defer.reject();
            }
        });
        wx.onMenuShareAppMessage({
            title: title,
            desc: desc,
            link: link || window.location.href,
            imgUrl: img || window.location.origin + '/images/public/logo_icon_share.png'
        });
        return defer.promise();
    },
    weixinHideMenu: function () {
        wx.hideOptionMenu();
    },
    weixinShowMenu: function () {
        wx.showOptionMenu();
    },
    weixinPay: function (orderid, userid) {
        var defer = $.Deferred();
        server.post('v20/order/pay_order_wxpay_web', {
            orderid: orderid,
            userid: userid
        }).done(function (data) {
            wx.chooseWXPay({
                timestamp: data.timestamp,
                nonceStr: data.nonceStr,
                package: data.package.package,
                signType: 'MD5',
                paySign: data.sign,
                success: function () {
                    defer.resolve();
                },
                fail: function (err) {
                    defer.reject(err);
                },
                cancel: function () {
                    defer.reject();
                }
            })
        }).fail(function (err) {
            defer.reject('创建微信支付订单失败,' + err);
        });
        return defer.promise();
    },
    alipay: function (orderid, userid) {
        return server.post('v20/order/pay_order_alipay', {
            type: device.name,
            orderid: orderid,
            userid: userid
        });
    }
}
