var loading = {
    _$loading: $('<div class="loading">' +
        '<div class="loading-animate">' +
        '<div class="loading-animate-item"></div> ' +
        '<div class="loading-animate-item"></div> ' +
        '<div class="loading-animate-item"></div> ' +
        '<div class="loading-animate-item"></div> ' +
        '<div class="loading-animate-item"></div> ' +
        '</div>' +
        '</div>'),
    isLoadingExit: function ($dom) {
        $dom = $dom || $body;
        if ($dom.find('.loading').length) {
            return true;
        }
        return false;
    },
    childLoadingStyle: function ($loading, $dom) {
        $loading.find('.loading-animate-item').css('backgroundColor', '#000');
        $dom.data('maskBackground', '#fff');
        $dom.data('maskOpacity', '1');
    },
    show: function ($dom) {
        var $loading = loading._$loading.clone();
        var maskConfig = {};
        if ($dom) {
            loading.childLoadingStyle($loading, $dom);
        }
        if (!loading.isLoadingExit($dom)) {
            dialog.show($loading, $dom);
        }
    },
    hide: function ($dom) {
        dialog.close($dom);
    }
};