;
(function () {
    var $doms = {
        $layout: $('.layoutDetail'),
        $map: $('.layoutDetail-map-plane'),
        $img: $('.layoutDetail-img-item')
    };
    var action = {
        getLayoutDetail: function ($dom) {
            return $dom.hasClass('layoutDetail') ? $dom : $dom.parents('.layoutDetail');
        },
        getLayoutDetailLight: function ($dom) {
            return this.getLayoutDetail($dom).find('.layoutDetail-map-light');
        },
        getLayoutDetailImg: function ($dom) {
            return this.getLayoutDetail($dom).find('.layoutDetail-img-item');
        },
        getDetailRule: function ($dom) {
            var $layout = this.getLayoutDetail($dom);
            return JSON.parse($layout.attr('data-layoutDetail-rule'));
        },
        getGroupRange: function (ruleObj, position) {
            var result = {},
                width = position.width,
                height = position.height;

            // 定义横向范围
            if (ruleObj.left !== undefined) {
                // left定位
                result.startX = ruleObj.left;
                result.endX = ruleObj.left + ruleObj.width;
            } else {
                // right定位
                result.startX = width - ruleObj.right - ruleObj.width;
                result.endX = width - ruleObj.right;
            }

            // 定义纵向范围
            if (ruleObj.top !== undefined) {
                // top定位
                result.startY = ruleObj.top;
                result.endY = ruleObj.top + ruleObj.height;
            } else {
                // bottom定位
                result.startY = height - ruleObj.bottom - ruleObj.height;
                result.endY = height - ruleObj.bottom;
            }
            return result;
        },
        getMapImgGroup: function (position, rule) {
            for (var item in rule) {
                var range = this.getGroupRange(rule[item], position);
                if (position.left >= range.startX
                    && position.left <= range.endX
                    && position.top >= range.startY
                    && position.top <= range.endY) {
                    return item;
                }
            }
        },
        setLight: function (lightObj, imgGroup, $dom) {
            if (lightObj === undefined) return;
            var $light = this.getLayoutDetailLight($dom),
                $img = this.getLayoutDetailImg($dom);
            $light.removeAttr('style').css({
                width: lightObj.width,
                height: lightObj.height,
                left: lightObj.left,
                top: lightObj.top,
                right: lightObj.right,
                bottom: lightObj.bottom
            });
            $img.filter('.active').removeClass('active');
            $img.filter('[data-layoutDetail-group="' + imgGroup + '"]').addClass('active');
        }
    };
    var events = {
        imgHover: function () {
            var $this = $(this),
                imgGroup = $this.attr('data-layoutDetail-group'),
                rule = action.getDetailRule($this);
            action.setLight(rule[imgGroup], imgGroup, $this);
        },
        mapMove: function (event) {
            var $this = $(this),
                mapWitdh = $this.width(),
                mapHeight = $this.height(),
                rule = action.getDetailRule($this);
            var position = {
                left: event.offsetX || event.originalEvent.layerX,
                top: event.offsetY || event.originalEvent.layerY,
                width: mapWitdh,
                height: mapHeight
            };
            var imgGroup = action.getMapImgGroup(position, rule);
            action.setLight(rule[imgGroup], imgGroup, $this);
        },
        mapClick: function (event) {
            var $this = $(this),
                mapWitdh = $this.width(),
                mapHeight = $this.height(),
                rule = action.getDetailRule($this);
            var position = {
                left: event.offsetX || event.originalEvent.layerX,
                top: event.offsetY || event.originalEvent.layerY,
                width: mapWitdh,
                height: mapHeight
            };
            var imgGroup = action.getMapImgGroup(position, rule);
            action.getLayoutDetailImg($this)
                .filter('[data-layoutDetail-group="' + imgGroup + '"]')
                .find('img').first().click();
        }
    }

    // init
    $doms.$img.hover(events.imgHover);
    $doms.$map.on('mousemove', events.mapMove)
        .on('click', events.mapClick);

})();