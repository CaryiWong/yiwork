require('../../sass/components/yiqi_app_download.scss');
var html = require('html!../../templates/components/yiqi_app_download.html');
var $ = require('jquery');
var device = require('../modules/device');

if(!device.yiqi){
    var $download = $(html);
    var $body = $('body').addClass('body-app-download').append($download);
    $download.find('.download-close').on('touchstart', function (event) {
        event.preventDefault();
        $download.remove();
        $body.removeClass('body-app-download');
    })
}
