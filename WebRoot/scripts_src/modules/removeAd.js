var $ = require('jquery');
var $body = $('body').on('DOMSubtreeModified', function () {
    var $frame = $body.find('iframe').not('.normal-iframe');
    if($frame.length){
        $frame.remove();
    }
});
