var device = require('../modules/device');

function sign() {
    if (device.weixin) {
        var url;
        if(location.host.match(/test/)){
            url = encodeURIComponent('http://www.yi-gather.com/dispatch/test'
                + location.pathname + location.hash);
        } else {
            url = encodeURIComponent(window.location.href);
        }
        console.log(url)
        setTimeout(function () { // 以防director冲突修改url
            window.location.replace('https://open.weixin.qq.com/connect/oauth2/authorize?' +
                'appid=wxa7f35f92e66817e5' +
                '&redirect_uri=' + url +
                '&response_type=code' +
                '&scope=snsapi_userinfo' +
                '&state=login#wechat_redirect');
        }, 0);
    } else {
        return false;
    }
}

module.exports = {
    sign: sign
}
