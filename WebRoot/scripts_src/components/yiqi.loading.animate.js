require('../../sass/components/yiqi.loading.animate.scss');
var $ = require('jquery');
$.fn.initLoadAnimate = function () {
    var $animate = this.find('.loading-animate').show();
    if($animate.hasClass('sk-fading-circle')){
        $animate.css('background-color', 'rgba(0, 0, 0, .5)')
            .html('<div class="sk-circle-area">' +
                  '<div class="sk-circle1 sk-circle"><span class="circle-shape"></span></div>' +
                  '<div class="sk-circle2 sk-circle"><span class="circle-shape"></span></div>'+
                  '<div class="sk-circle3 sk-circle"><span class="circle-shape"></span></div>'+
                  '<div class="sk-circle4 sk-circle"><span class="circle-shape"></span></div>'+
                  '<div class="sk-circle5 sk-circle"><span class="circle-shape"></span></div>'+
                  '<div class="sk-circle6 sk-circle"><span class="circle-shape"></span></div>'+
                  '<div class="sk-circle7 sk-circle"><span class="circle-shape"></span></div>'+
                  '<div class="sk-circle8 sk-circle"><span class="circle-shape"></span></div>'+
                  '<div class="sk-circle9 sk-circle"><span class="circle-shape"></span></div>'+
                  '<div class="sk-circle10 sk-circle"><span class="circle-shape"></span></div>'+
                  '<div class="sk-circle11 sk-circle"><span class="circle-shape"></span></div>'+
                  '<div class="sk-circle12 sk-circle"><span class="circle-shape"></span></div>' +
            '</div>');
    }
}

$.fn.hideLoadAnimate = function () {
    this.find('.loading-animate')
        .css('background-color', 'transparent')
        .hide();
}
