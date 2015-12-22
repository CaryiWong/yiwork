require('../../sass/components/yiqi_quicktip.scss');
require('../modules/jquery.animate.callback');
var $ = require('jquery');
require('../modules/jquery.touchclick');
var $body = $('body');
var $tip = $();

$body.livetouchclick('.yiqi-quicktip-area.quicktip-confirm .cancel-button', function () {
    hide();
});

function show(type, text, time) {
    hide(true);
    $tip = $('<div class="yiqi-quicktip-area"><div class="yiqi-quicktip">' +
        '<div class="quicktip-icon"></div>' +
        '<div class="quicktip-text">'+ text +'</div>' +
        '</div></div>')
        .appendTo($body)
        .addClass(type)
        .addClass('shown-animate');
    setTimeout(function () {
        hide();
    }, time || 2500);
}

function confirm(text, title) {
    var defer = $.Deferred();
    title = title || '温馨提示';
    hide(true);
    $tip = $('<div class="yiqi-quicktip-area quicktip-confirm"><div class="yiqi-quicktip">' +
        '<div class="quicktip-title">'+ title +'</div>' +
        '<div class="quicktip-desc">'+ text +'</div>' +
        '<div class="quicktip-buttons">' +
        '<button class="cancel-button">取消</button>' +
        '<button class="ok-button">确定</button>' +
        '</div>' +
        '</div></div>')
        .appendTo($body)
        .addClass('shown-animate')
        .find('.ok-button').touchclick(function (event) {
            event.preventDefault();
            event.stopPropagation();
            defer.resolve();
        })
        .end();
    return defer.promise();
}

function hide(now) {
    if(now){
        $tip && $tip.remove();
    } else {
        $tip && $tip.length && $tip.cssAnimate('hidden-animate', function () {
            $tip.remove();
        });
    }
}

module.exports = {
    success: function (text, time) {
        show('quicktip-success', text, time);
    },
    fail: function (text, time) {
        show('quicktip-fail', text, time);
    },
    loading: function (text, time) {
        show('quicktip-loading', text || '正在加载', time || 600000)
    },
    hide: function () {
        hide($tip);
    },
    confirm: confirm
}
