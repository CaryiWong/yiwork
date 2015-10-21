function getArgs() {
    var args = {},
        query = location.search.substring(1),
        pairs = query.split("&");
    for (var i = 0; i < pairs.length; i++) {
        var pos = pairs[i].indexOf('=');
        if (pos == -1) continue;
        var argname = pairs[i].substring(0, pos),
            value = pairs[i].substring(pos + 1);
        value = decodeURIComponent(value);
        args[argname] = value;
    }
    return args;
}

$(function() {
    function getImage(path) {
        return serverPath + '/download/img?path=' + path + '&type=web';
    }

    function getTime(s) {
        function getMin(t) {
            return t < 10 ? '0' + t : t;
        }

        var d = s.replace(/\s|:/g, '-').split('-');
        var t = new Date(d[0], d[1] - 1, d[2], d[3], d[4], d[5]);
        return t.getFullYear() + '年' +
            (t.getMonth() + 1) + '月' +
            t.getDate() + '日 ' +
            t.getHours() + ':' +
            getMin(t.getMinutes());
    }

    function getText(text) {
        return text.replace(/\n/g, '<br/>');
    }

    var id = getArgs()['id'];
    var $head = $('.head img'),
        $title = $('.article-title'),
        $content = $('.article-content'),
        $name = $('.info-name'),
        $job = $('.info-job'),
        $time = $('.article-time span'),
        $address = $('.article-address span');


    var serverPath = window.location.origin + '/v20';
    //var serverPath = 'http://192.168.1.30' + '/v20';

    $.ajax(serverPath + '/activity/viewgathering', {
        data: {
            type: 'web',
            id: id
        },
        type: 'post',
        dataType: 'json'

    }).success(function(data) {
        if (data.cord === 0) {
            var info = data.data;
            var user = info.user;
            user.minimg && $head.attr('src', getImage(user.minimg));
            user.nickname && $name.html(user.nickname);
            user.job[0] && $job.html(user.job[0].zname);
            info.opendate && $time.html(getTime(info.opendate));
            info.address && $address.html(info.address);
            info.title && $title.html(info.title);
            info.summary && $content.html(getText(info.summary));
        } else {
            console.error('获取数据出错')
        }
    }).error(function() {
        console.error('获取数据出错')
    })
});