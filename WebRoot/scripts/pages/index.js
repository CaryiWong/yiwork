$(function (){

    // 活动内容hover状态展开
    var $titles = $('.activity-item');
    $titles.hover(function (){
        $(this).find('.activity-title').height(80)
    } , function (){
        $(this).find('.activity-title').height(15)
    })

});