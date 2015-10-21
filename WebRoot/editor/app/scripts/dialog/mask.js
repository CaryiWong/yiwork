window.mask = {
    set: function ($dom) {
        var $mask = $('<div class="dialog-mask"></div>');
        $mask.css({
            zIndex: 990,
            width: '100%',
            height: $body[0].scrollHeight,
            top: 0,
            left: 0,
            position: 'absolute',
            backgroundColor: $dom.data('maskBackground') || '#000',
            opacity: $dom.data('maskOpacity') || '.6'
        }).appendTo($dom);
    }};