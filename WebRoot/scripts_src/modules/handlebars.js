var Handlebars = require('../lib/handlebars.runtime');
Handlebars.registerHelper('ifCond', function (v1, v2, options) {
    if (v1 === v2) {
        return options.fn(this);
    }
    return options.inverse(this);
});

Handlebars.registerHelper('desc', function (text) {
    return text.trim().slice(0, 48) + '...';
});

Handlebars.registerHelper('wrap', function (text) {
    if(text.constructor === Handlebars.SafeString){
        text = text.string;
    }
    return text.trim().replace(/\n/g, '<br/>');
});

Handlebars.registerHelper('contentLink', function (text) {
    var encodeText = Handlebars.escapeExpression(text.trim());
    encodeText = encodeText.replace(/(http\S*)/g, function (a, url) {
        return '<a class="_content_link" href="'+ url +'">'+ url +'</a>';
    });
    return new Handlebars.SafeString(encodeText);
});

Handlebars.registerHelper('defaultCover', function (text) {
    return text.length ? text : window.location.origin + '/images/group/activity_default_banner.png';
});

function getTextLength(str){
    var char = str.replace(/[^\x00-\xff]/g, '**');
    return char.length;
}

Handlebars.registerHelper('textLimit', function (text, num) {
    text = text.trim();
    if(getTextLength(text) > num){
        text = text.slice(0, num/2) + '...'
    }
    return text;
});

Handlebars.registerHelper('date', function (text) {
    return text ? text.trim().split(' ')[0] : text;
});

function urlHandle(url) {
    return url && url.replace(/_600/, '');
}

function isNeedPress(url) {
    return url && url.match(/path=/) && !url.match(/_\d*X\d*/);
}

Handlebars.registerHelper('thumbImg', function (url) {
    url = urlHandle(url);
    if (isNeedPress(url)) {
        return url += '_160X160';
    } else {
        return url;
    }
});

Handlebars.registerHelper('normalImg', function (url) {
    url = urlHandle(url);
    if (isNeedPress(url)) {
        return url += '_300X420';
    } else {
        return url;
    }
});

Handlebars.registerHelper('bigImg', function (url) {
    url = urlHandle(url);
    if (isNeedPress(url)) {
        return url += '_640X640';
    } else {
        return url;
    }
});

Handlebars.registerHelper('commDate', function (text) {
    var match = text.match(/^(\d+)-(\d+)-(\d+) (\d+)\:(\d+)\:(\d+)$/);
    var date = new Date(match[1], match[2] - 1, match[3], match[4], match[5], match[6]);
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    month = month<10 ? '0' + month : month.toString();
    day = day<10 ? '0' + day : day.toString();
    hour = hour<10 ? '0' + hour : hour.toString();
    minute = minute<10 ? '0' + minute : minute.toString();
    return month + '-' + day + ' ' + hour + ':' + minute;
})

module.exports = Handlebars;
