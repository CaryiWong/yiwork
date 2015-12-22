require('../../sass/modules/_image.display.scss');
var $ = require('jquery');
require('../modules/jquery.animate.callback');
var imagesLoaded = require('imports?define=>false&this=>window!imagesloaded');
var displayAction = {
    opacity: function ($element) {
        $element.removeClass('is-loading');
    },
    pic: function ($element) {
        $element.cssTransitionClass('is-loadingPic-hide', function () {
            $element.removeClass('is-loading is-loadingPic-hide')
                .removeAttr('data-display-method');
        });
    }
}

$.fn.displayLoaded = function () {
    this.imagesLoaded({background: '.image-display.is-loading'})
    .progress(function (instance, img) {
        if(img.isLoaded && img.element){
            var $element = $(img.element),
                displayMethod = $element.attr('data-display-method') || 'opacity';
            displayAction[displayMethod]($element);
        }
    });
    return this;
}
