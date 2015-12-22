var $ = require('jquery');
var cookie = require('expose?_cookie!../lib/js.cookie');
var server = require('./yiqi_server');
var quickTip = require('../components/yiqi_quicktip');
var util = require('./../modules/util');
var device = require('../modules/device');
var args = util.getArgs();
var user = {};
var readyAction = [];
var isSign;
var id = cookie.get('userid');
if(device.yiqi && !id){
    alert('请先登录 ' + document.cookie);
}
function clearCache() {
    cookie.remove('userid', {path: '/'});
    cookie.remove('signOpenid', {path: '/'});
    cookie.remove('signUnionid', {path: '/'});
    cookie.remove('signHead', {path: '/'});
    cookie.remove('signNick', {path: '/'});
    cookie.remove('signProvince', {path: '/'});
    cookie.remove('signCity', {path: '/'});
    id = cookie.get('userid');
}

function login() {
    // justLogin 1普通登录 2微信登录
    quickTip.loading();
    window.localStorage.setItem('loginBackUrl', window.location.href);
    if (device.weixin) {
        setTimeout(function () {
            window.localStorage.setItem('justLogin', 2);        
            window.location.replace('/pages/members/weixin_bind.html');
        }, 0);
    } else {
        setTimeout(function () {
            window.localStorage.setItem('justLogin', 1);
            window.location.replace('/pages/members/login.html');
        }, 0);
    }
}

function setUser(data) {
    user = data;
    id = data.id;
    isSign = true;
    cookie.set('userid', data.id);
}

function ready(cb) {
    readyAction.push(cb);
}

function isBasicUser(user) {
    var result;
    if (user.id) {
        if (user.telnum) {
            result = 1; // 标准用户
        } else {
            window.localStorage.setItem('before_optimize', window.location.href);
            setTimeout(function () { // 以防director冲突修改url
                window.location.href = '/pages/members/optimize.html'; //非完整用户跳转到补全表
            }, 0)
            result = 2; // 非标准用户，正在跳转补全页
        }
    } else {
        result = 0; // 压根没登录
    }
    return result;
}

function resetReady() {
    readyAction.forEach(function (cb) {
        cb(isSign);
    });
    ready = function (cb) {
        cb && cb(isSign);
    };
}

if (id) {
    server.post('v20/user/getuser', {
        userid: id
    }).done(function (data) {
        setUser(data);
    }).fail(function () {
        console.log('获取用户信息失败');
        isSign = false;
    }).always(function () {
        resetReady();
    })
} else {
    isSign = false;
    resetReady();
}

isDev ? console.log('now userid is ' + id) : null;
module.exports = {
    userid: id,
    getEntity: function () {
        return user;
    },
    validUser: function () {
        // 验证手机号码 不通过的话是否微信登录
        if (user.id && user.telnum) {
            return true;
        } else if (device.yiqi) {
            alert('请登录后重试');
            window.history.go(-1);
        } else {
            login();
        }
        return false;
    },
    ready: function (cb) {
        ready(cb);
    },
    isJustLoginAndReset: function () {
        var result = window.localStorage.getItem('justLogin') - 0;
        window.localStorage.setItem('justLogin', 0);
        return result;
    }
};
