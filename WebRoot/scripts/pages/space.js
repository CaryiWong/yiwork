$(function () {

    // 地图切换
    var $address = $('.address-map');
    $('.address-button.walk').click(function () {
        $address.removeClass('car').addClass('walk');
    });
    $('.address-button.car').click(function () {
        $address.removeClass('walk').addClass('car');
    });


    // 活动内容hover状态展开
    var $titles = $('.activity-item');
    $titles.hover(function () {
        $(this).find('.activity-title').height(80)
    }, function () {
        $(this).find('.activity-title').height(15)
    })

});