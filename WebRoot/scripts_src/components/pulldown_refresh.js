var $ = require('jquery');
require('../modules/jquery.animate.callback');
require('../../sass/components/_pulldown_refresh.scss');
require('../lib/my.xpull');
var $window = $(window);
var screenHeight = $window.height();
$.fn.initPullDownRefresh = function (pulldownHandler, pullupHandler) {
    var $this = this;
    $this.xpull({
        callback: pulldownHandler,
        pullThreshold: 50
    });

    var pullupLoading = false;
    var pullupStartRelativeBottomY;
    var isUp = false;
    function resetPullup() {
        $pullup.addClass('pullup-success');
        setTimeout(function () {
            $pullup.cssAnimate('pullup-hide', function () {
                $pullup.removeClass('pullup-loading pullup-success pullup-hide');
                pullupLoading = false;
            })
        }, 1500)
    }

    // $(document.body).append('<div style="position: fixed;'+
    //                                     'bottom: 0;'+
    //                                     'left: 0;'+
    //                                     'right: 0;'+
    //                                     'background-color: #777;'+
    // 'color: #fff;" class="console"></div>');
    // var $console = $('.console');

    $this.append('<div class="pullup-indicator">' +
    '<div class="pullup-load">' +
    '<span class="load-icon"></span></div>' +
    '</div>')
    .on('touchstart', function (event) {
        pullupStartRelativeBottomY = event.originalEvent.touches[0].pageY - $this.offset().top + $this.prop('scrollTop');
        pullupStartRelativeBottomY = $this.prop('scrollHeight') - pullupStartRelativeBottomY;
        // $console.html('start 距离底部 Y ' + pullupStartRelativeBottomY);
    })
    .on('touchmove', function (event) {
        if(!$pullup.hasClass('pullup-loading') && !pullupLoading ){
            var touchRelativeBottomY = event.originalEvent.touches[0].pageY - $this.offset().top + $this.prop('scrollTop');
            touchRelativeBottomY = $this.prop('scrollHeight') - touchRelativeBottomY;
            // $console.html('move 距离底部 Y ' + touchRelativeBottomY);
            if(touchRelativeBottomY - pullupStartRelativeBottomY > 50){
                pullupLoading = true;
                $pullup.addClass('pullup-loading');
                pullupHandler.call($pullup, resetPullup);
            }
        }
    })
    .on('touchcancel', function (event) {
        // 奇怪安卓暂解决方案
        if(!$pullup.hasClass('pullup-loading') && !pullupLoading){
            var touchRelativeBottomY = event.originalEvent.changedTouches[0].pageY - $this.offset().top + $this.prop('scrollTop');
            touchRelativeBottomY = $this.prop('scrollHeight') - touchRelativeBottomY;
            // $console.html('cancel 距离底部 Y ' + touchRelativeBottomY);
            if(touchRelativeBottomY - pullupStartRelativeBottomY > 10){
                pullupLoading = true;
                $pullup.addClass('pullup-loading');
                pullupHandler.call($pullup, resetPullup);
            }
        }
    });
    var $pullup = $this.find('.pullup-indicator');
}

$.fn.resetPullDownRefresh = function () {
    this.data('plugin_xpull').reset();
}

//module.exports = {
//    init: function (selector, pulldownHandler, pullupHandler) {
//        $(selector).xpull({
//            callback: pulldownHandler
//        })
//    },
//    reset: function ($dom) {
//        $dom.data('plugin_xpull').reset();
//    }
//}
