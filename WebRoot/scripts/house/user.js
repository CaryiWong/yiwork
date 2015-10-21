define(['jquery', 'server'], function ($, server) {
    var ua = window.navigator.userAgent.toLowerCase();
    var appId = 'wxa7f35f92e66817e5';
    var backUrl = encodeURIComponent(window.location.href);
    var args = (function getArgs() {
        var args = {},
            query = location.search.substring(1),
            pairs = query.split("&");
        for (var i = 0; i < pairs.length; i++) {
            var pos = pairs[i].indexOf('=');
            if (pos == -1) continue;
            var argname = pairs[i].substring(0, pos),
                value = pairs[i].substring(pos + 1);
            value = decodeURIComponent(value);
            args[argname] = value;
        }
        return args;
    })();
    var device = {
        android: false,
        ios: false,
        weixin: false
    };
    var user = {};
    var isWeixinSign = false;
    var $body = $('body');

    if (ua.search(/android/) >= 0) {
        device.android = true;
        $body.addClass('device-android');
    }
    if (ua.search(/iphone|ipod|ipad/) >= 0) {
        device.ios = true;
        $body.addClass('device-ios');
    }
    if (ua.search(/micromessenger/) >= 0) {
        device.weixin = true;
        $body.addClass('device-weixin');
    }

    return {
        weixinSign: function () {
            isWeixinSign = true;
            window.location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?' +
            'appid=' + appId +
            '&redirect_uri=' + backUrl + '&response_type=code' +
            '&scope=snsapi_userinfo&state=login#wechat_redirect';
        },
        getServerUser: function () {
            var dtd = $.Deferred(),
                self = this,
                code = args['code'],
                state = args['state'];
            if (state !== 'login') {
                dtd.resolve(); // 不是刚微信登录完毕
            } else if (code) {
                server.getUser(code) // 向服务器取回用户信息
                    .done(function (u){
                        user = u;
                        self.setLocalUser(user);
                        dtd.resolve();
                    });
            } else {
                alert('请完成微信登录');
                dtd.reject(); // 不授权微信登录
            }
            return dtd.promise();
        },
        getLocalUser: function () {
            var self = this;
            user = JSON.parse(localStorage.getItem('yguser') || '{}');
            user.openid ? null : self.weixinSign();
            return user;
        },
        setLocalUser: function (obj){
            window.localStorage.setItem('yguser', JSON.stringify(obj));
        },
        hasPhone: function () {
            if (!user.tel && !window.location.href.match(/bind/) && !isWeixinSign ) {
                window.localStorage.setItem('backUrl', window.location.href);
                window.location.href = 'bind.html';
            }
        },
        sign: function () {
            // 临时用户设置
            //localStorage.setItem('yguser', '{"createtime":"2015-08-19 11:29:42","headimgurl":"http://wx.qlogo.cn/mmopen/1XID7kpw5kBlsR1rhdHtL9U0FdQ5Ey454mropZElkiaicbUx1uJcpHxyHnrssA0EIZqk6dMPSr4yvNfyvshgZIV7V14RicdlR33/0","id":"wx1440394329397","nickname":"叶+","openid":"oBMJ0tw3vpLr7WLCwpL_XpVgzdyQ","tel":"13430301258","unionid":"oghRluC3n-x_iL4RdvXC8xWRJQms"}');
            var self = this;
            var dtd = $.Deferred();
            if (device.weixin) {
                self.getServerUser().done(function () {
                    self.getLocalUser();
                    self.hasPhone();
                    dtd.resolve(user);
                })
            }
            return dtd.promise();
        },
        getUserInfo: function () {
          return user;
        }
    }
})
