require('../../sass/components/_pulldown_refresh.scss');
var IScroll = require('../lib/iscroll');
var generatedCount = 0;

var IScrollPullUpDown = function (wrapperName, iScrollConfig, pullDownActionHandler, pullUpActionHandler) {
    var iScrollConfig, pullDownEl, pullDownOffset, pullUpEl, scrollStartPos;
    var pullThreshold = 5;
    var me = this;

    function showPullDownElNow(className) {
        pullDownEl.style.transitionDuration = '';
        pullDownEl.style.marginTop = '';
        pullDownEl.className = 'pullDown ' + className;
    }

    var hidePullDownEl = function (time, refresh) {
        pullDownEl.style.transitionDuration = (time > 0 ? time + 'ms' : '');
        pullDownEl.style.marginTop = '';
        pullDownEl.className = 'pullDown scrolledUp';
        if (refresh) setTimeout(function () {
            pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新';
            me.myScroll.refresh();
        }, time + 10);
    }

    function init() {
        var wrapperObj = document.querySelector(wrapperName);
        var scrollerObj = wrapperObj.children[0];
        var listObj = scrollerObj.children[0];
        wrapperObj.classList.add('pulldown-refresh-area');
        scrollerObj.classList.add('refresh-scroller');

        if (pullDownActionHandler) {
            pullDownEl = document.createElement('div');
            pullDownEl.className = 'pullDown scrolledUp';
            pullDownEl.innerHTML = '<span class="pullDownIcon"></span>' +
                '<span class="pullDownLabel">下拉刷新</span>';
            scrollerObj.insertBefore(pullDownEl, scrollerObj.firstChild);
            pullDownOffset = pullDownEl.offsetHeight;
        }
        if (pullUpActionHandler) {
            pullUpEl = document.createElement('div');
            pullUpEl.className = 'pullUp';
            pullUpEl.innerHTML = '<span class="pullUpIcon"></span>' +
                '<span class="pullUpLabel">正在加载...</span>';
            scrollerObj.appendChild(pullUpEl);
        }

        listObj.addEventListener('DOMSubtreeModified', function () {
            me.myScroll.refresh();
        });

        me.myScroll = new IScroll(wrapperObj, iScrollConfig);
        me.myScroll.on('refresh', function () {
            if ((pullDownEl) && (pullDownEl.className.match('loading'))) {
                pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载成功';
                if (this.y >= 0) {
                    hidePullDownEl(1000, true);
                } else if (this.y > -pullDownOffset) {
                    pullDownEl.style.marginTop = this.y + 'px';
                    pullDownEl.offsetHeight;
                    var animTime = (250 * (pullDownOffset + this.y) / pullDownOffset);
                    this.scrollTo(0, 0, 0);
                    setTimeout(function () {
                        hidePullDownEl(animTime, true);
                    }, 0);
                } else {
                    hidePullDownEl(0, true);
                    this.scrollBy(0, pullDownOffset, 0);
                }
            }
            if ((pullUpEl) && (pullUpEl.className.match('loading'))) {
                //setTimeout(function () {
                    pullUpEl.className = 'pullUp';
                    pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载成功';
                //}, 1000)
            }
        });

        me.myScroll.on('scrollStart', function () {
            scrollStartPos = this.y;
        });

        me.myScroll.on('scroll', function () {
            if (pullDownEl || pullUpEl) {
                if ((scrollStartPos == 0) && (this.y == 0)) {
                    this.hasVerticalScroll = true;
                    scrollStartPos = -1000;
                } else if ((scrollStartPos == -1000) &&
                    (((!pullUpEl) && (!pullDownEl.className.match('flip')) && (this.y < 0)) ||
                    ((!pullDownEl) && (!pullUpEl.className.match('flip')) && (this.y > 0)))) {
                    this.hasVerticalScroll = false;
                    scrollStartPos = 0;
                    this.scrollBy(0, -this.y, 0);
                }
            }

            if (pullDownEl) {
                if (this.y > pullDownOffset + pullThreshold && !pullDownEl.className.match('flip')) {
                    showPullDownElNow('flip');
                    this.scrollBy(0, -pullDownOffset, 0);
                    pullDownEl.querySelector('.pullDownLabel').innerHTML = '松开刷新';
                } else if (this.y < 0 && pullDownEl.className.match('flip')) {
                    hidePullDownEl(0, false);
                    this.scrollBy(0, pullDownOffset, 0);
                    pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新';
                }
            }
            if (pullUpEl) {
                if (this.y <= (this.maxScrollY) && !pullUpEl.className.match('flip')) {
                    pullUpEl.className = 'pullUp flip';
                    pullUpEl.querySelector('.pullUpLabel').innerHTML = '正在加载...';
                }
                //else if (this.y > (this.maxScrollY + pullThreshold) && pullUpEl.className.match('flip')) {
                //    pullUpEl.className = 'pullUp';
                //    pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Pull up to load more...';
                //}
            }
        });

        me.myScroll.on('scrollEnd', function () {
            if ((pullDownEl) && (pullDownEl.className.match('flip'))) {
                showPullDownElNow('loading');
                pullDownEl.querySelector('.pullDownLabel').innerHTML = '正在加载';
                pullDownActionHandler(this);
            }
            if ((pullUpEl) && (pullUpEl.className.match('flip'))) {
                pullUpEl.className = 'pullUp loading';
                //pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Loading...';
                pullUpActionHandler(this);	// Execute custom function (ajax call?)
            }
            if (scrollStartPos = -1000) {
                this.hasVerticalScroll = this.options.scrollY && this.maxScrollY < 0;
            }
        });

        me.setPullUpText = function (text) {
            pullUpEl.querySelector('.pullUpLabel').innerHTML = text;
        }

        me.refresh = me.myScroll.refresh.bind(me.myScroll);
    }

    init();
};

module.exports = IScrollPullUpDown;
