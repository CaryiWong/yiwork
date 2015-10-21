$(function () {
    var $dom = {
            $slide: $('.slide'),
            $list: $('.slide-list'),
            $items: $('.slide-item'),
            $buttons: $('.slide-button'),
            $switchs: $('.slide-switch-button')
        },
        param = {
            activeIndex: 0,
            autoTime: 5000,
            animateTime: 1000,
            itemLength: $dom.$items.length,
            itemWidth: $dom.$items.width(),
            autoAnimate: null,
            buttonChange: false,
            orderSwitch: false
        },
        action = {
            changeBgColor: function (bgColor) {
                $dom.$slide.css('backgroundColor', bgColor);
            },
            buttonChange: function () {
                if (param.buttonChange) {
                    $dom.$switchs.show(0);
                    if (param.activeIndex === 0) {
                        $dom.$switchs.filter('.left').hide()
                    }
                    if (param.activeIndex === param.itemLength - 1) {
                        $dom.$switchs.filter('.right').hide()
                    }
                }
            },
            listMove: function (newIndex, oldIndex, callback) {
                if(newIndex === oldIndex) return;
                if (param.orderSwitch) {
                    var $oldItem = $dom.$items.eq(oldIndex),
                        $newItem = $dom.$items.eq(newIndex);
                    $dom.$list.css('left',0);
                    $dom.$list.prepend($oldItem);
                    $oldItem.after($newItem);
                    $dom.$list.animate({
                        left: -param.itemWidth
                    }, param.animateTime, function () {
                        callback && callback();
                    });
                } else {
                    $dom.$list.animate({
                        left: -(newIndex * param.itemWidth)
                    }, param.animateTime, function () {
                        callback && callback();
                    });
                }
            },
            isChanging: function (){
                return $dom.$list.is(':animated');
            },
            change: function (index, callback) {

                // animate stop
                $dom.$list.stop(true, false);

                var oldIndex = param.activeIndex ,
                    bgColor = $dom.$items.eq(index).attr('data-slide-bgColor');

                action.listMove(index, oldIndex, callback);
                $dom.$buttons.filter('.active').removeClass('active');
                $dom.$buttons.eq(index).addClass('active');
                param.activeIndex = index;
                if (bgColor) {
                    action.changeBgColor(bgColor);
                }
                action.buttonChange();
            },
            autoChange: function () {
                if (param.autoTime > 0) {
                    return setInterval(function () {
                        var index = param.activeIndex;
                        if (++index > param.itemLength - 1) {
                            index = 0;
                        }
                        action.change(index);
                    }, param.autoTime);
                } else {
                    return null;
                }
            },
            setParam: function (custom) {
                param = $.extend(param, custom);
            }
        };

    // event
    $dom.$buttons.hover(function () {
        clearInterval(param.autoAnimate);
        action.change($dom.$buttons.index(this));
    }, function () {
        param.autoAnimate = action.autoChange();
    });

    $dom.$items.hover(function () {
        clearInterval(param.autoAnimate);
    }, function () {
        param.autoAnimate = action.autoChange();
    });

    $dom.$switchs.click(function () {
        clearInterval(param.autoAnimate);
        var index = param.activeIndex;
        if ($(this).hasClass('left')) {
            index = --index < 0 ? param.itemLength - 1 : index;
        } else {
            index = ++index > param.itemLength - 1 ? 0 : index;
        }
        action.change(index, function () {
            param.autoAnimate = action.autoChange();
        });
    });


    // init
    action.setParam(JSON.parse($dom.$slide.attr('data-slide') || '{}'));
    param.autoAnimate = action.autoChange();
    action.change(0);
});