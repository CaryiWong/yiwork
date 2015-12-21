/**
 * Created by Caryi on 2015/12/21.
 */
var localOriginal= require('localOriginal');
//var localOriginal= "http://" + window.location.host;
$.ajax( localOriginal + '/v20/eventlog/trends',{
    dataType: 'json',
    type: 'POST',
    data: {
        type: 'web',
        page: 1,
        size: 10,
        eventtype: 'activity'
    }
}).success(function(data){
}).fail();