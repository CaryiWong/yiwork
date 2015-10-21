$(function () {

    var $body = $('body');

    function isWeixin() {
        var userAgent = navigator.userAgent.toLocaleLowerCase();
        return userAgent.search(/micromessenger/) > -1;
    }

    function getDevice() {
        var userAgent = navigator.userAgent.toLocaleLowerCase(),
            result = null;
        if (userAgent.search(/iphone|ipad/) > -1) {
            result = 'ios';
        } else if (userAgent.search(/android/) > -1) {
            result = 'android';
        }
        return result;
    }

    function isAndroid() {
        return getDevice() === 'android';
    }

    function isUCBrower() {
        var userAgent = navigator.userAgent.toLocaleLowerCase();
        return userAgent.search(/uc/) > -1
    }

    function isTencentVideo(frame) {
        return frame.src.search(/v\.qq\.com/) > -1;
    }

    function addLink(url) {
        var $appLink = $('<div style="margin-top: 15px"><a href="' + url + '">一起App下载</a></div>');
        $body.append($appLink);
        $body.prepend($appLink.clone());
    }

    if (isWeixin()) {
        $.get('/version/checkupdate',
            { platform: getDevice() },
            function (data) {
                data = JSON.parse(data);
                if (data.cord === 0) {
                    var url = data.data.url;
                    addLink(url);
                }
            }
        );
    }

    if (isAndroid()) {
        function setVideoLinkStyle($link) {
            $link.addClass('tencentVideo').css({
                width: '100%',
                height: '200px',
                backgroundColor: '#000',
                display: 'block',
                color: '#fff'
            })
                .attr('target', '_blank');
        }

        $('iframe').each(function () {
            if (isTencentVideo(this)) {
                var src = this.src;
                var $link = $('<a href="' + src + '" ><span></span></a>');
                setVideoLinkStyle($link);
                $(this).after($link).remove();

            }
        })
    }
});