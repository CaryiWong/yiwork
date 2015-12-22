require('../../sass/components/yiqi_picture.scss');
require('../lib/bootstrap_carousel');
require('../lib/jquery.mobile.event');
require('../modules/jquery.touchclick');
var $lastImages;
var isSame;

function handleImg($image) {
    var url = $image.attr('src') || $image.css('backgroundImage').replace(/(url\(|\)|'|")/gi, '');
    if (url.match(/path=/)) {
        // return url.replace(/\d*X\d*/, '640X640');
        return url.replace(/_\d*X\d*/, '');
    } else {
        return url;
    }
}

var $ = require('jquery');
var pictureHtml = require('html!../../templates/components/yiqi_picture.html');
var $html = $('html');
var replyTop;
var $body = $('body').livetouchclick('.yiqi-picture img, .yiqi-picture .picture-img', function (event) {
    event.preventDefault();
    event.stopPropagation();
    replyTop = $body[0].scrollTop;
    var $pictures = $(this).parents('.yiqi-picture'),
    $images = $pictures.find('img, .picture-img'),
    index = $images.index(this),
    cloneImg = '';
    if($lastImages){
        $images.each(function (i) {
            isSame = $lastImages[i] === $images[i];
            return isSame;
        });
    }
    if(!isSame){
        $inner.empty();
        $images.each(function () {
            var src = handleImg($(this));
            cloneImg += '<div class="img-area"><img src="' + src + '" /></div>';
        });
        $(cloneImg).appendTo($inner)
        .wrap(function (i) {
            return i === index ? '<div class="item active"><div class="img-scroll"></div></div>'
            : '<div class="item"><div class="img-scroll"></div></div>';
        });
    } else {
        $inner.find('.item').removeClass('active')
            .eq(index).addClass('active');
    }
    $lastImages = $images;
    $picture.fadeIn(200, function () {
        $body[0].scrollTop = 0;
        $html.addClass('fixed');
    });
});

$body.append(pictureHtml);
var $picture = $('.picture-area').hide().quicktouchclick(function (event) {
    event.preventDefault();
    event.stopPropagation();
    $html.removeClass('fixed');
    $body[0].scrollTop = replyTop;
    // $picture.fadeOut(200);
    $picture.hide();    
    // $inner.empty();
});

var $carousel = $('#picture-carousel').carousel({interval: false});
$carousel.carousel('pause')
.swipeleft(function () {
    $carousel.carousel('next');
})
.swiperight(function () {
    $carousel.carousel('prev');
})
.on('slid.bs.carousel', function (event) {
    //setCarouselWay($(event.relatedTarget));
})
var $inner = $carousel.find('.carousel-inner');
