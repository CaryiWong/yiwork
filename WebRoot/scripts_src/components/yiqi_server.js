var $ = require('../../scripts/lib/jquery');
var errorAction;
// var server = 'http://www.yi-gather.com/';
var server = isDev ? 'http://test.yi-gather.com:1717/' : window.location.origin + '/';
var defaultData = {
    type: 'web'
};
var imageDomain = 'http://7xngit.com1.z0.glb.clouddn.com/';
var token = '_tIcUz_GL4rHsQ7SVfZwhEIecD4jA4t02e21lG3H:9s-YhradXRGqt6t-lZp7RpEdKy8=:eyJzY29wZSI6InlpLWdhdGhlciIsImRlYWRsaW5lIjoxNDQ0ODEwMDI3fQ==';

module.exports = {
    setErrorAction: function (action) {
        errorAction = action;
    },
    post: function (interface, data) {
        var defer = $.Deferred();
        var postData = $.extend({}, defaultData, data);
        $.ajax(server + interface, {
            data: postData,
            type: 'POST',
            dataType: 'json'
        }).success(function (data) {
            if (data.cord === 0) {
                defer.resolve(data.data, data.data2);
            } else {
                errorAction && errorAction(data.msg);
                defer.reject(data.msg);
            }
        }).fail(function (error) {
            errorAction && errorAction(error);
            defer.reject(error);
        });
        return defer.promise();
    },
    get: function (interface, data) {
        var defer = $.Deferred();
        var postData = $.extend({}, defaultData, data);
        $.ajax(server + interface, {
            data: postData,
            type: 'GET',
            dataType: 'json'
        }).success(function (data) {
            if (data.cord === 0) {
                defer.resolve(data.data, data.data2);
            } else {
                errorAction && errorAction(data.msg);
                defer.reject(data.msg);
            }
        }).fail(function (error) {
            errorAction && errorAction(error);
            defer.reject(error);
        });
        return defer.promise();
    },
    uploadImageCode: function (img) {
        var self = this;
        var promise;
        if(img.length / 1024 / 1024 > 2.8){ // 限制上传图片大小3m
            var defer = $.Deferred();
            defer.reject('上传图片太大');
            promise = defer.promise();
        } else {
            promise = self.post('v20/upload/upload_base64', {
                img: img.replace(/data:.*base64,/, '')
            }).then(function (data) {
                var url = server + 'v20/download/img?type=web&path=' + data;
                return url;
            });
        }
        return promise;
    },
    uploadImage: function (file) {
        var defer = $.Deferred();
        if (file) {
            var xhr = new XMLHttpRequest();
            var formData = new FormData();
            xhr.open('POST', server + 'v20/upload/uploadimg');
            formData.append('img', file);
            xhr.send(formData);

            xhr.upload.addEventListener("progress", function (evt) {
                console.log('progress ' + evt)
            }, false);
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 && xhr.responseText != "") {
                        var data = JSON.parse(xhr.responseText);
                        var url = server + 'v20/download/img?type=web&path=' + data.data;
                        console.log(url);
                        defer.resolve(url);
                    }
                    else {
                        defer.reject('upload error');
                    }
                }
            }
        } else {
            alert('没有上传文件 ╮(╯_╰)╭');
            defer.reject();
        }
        return defer.promise();
    },
    //uploadImage: function (file) {
    //    var defer = $.Deferred();
    //    if (file) {
    //        var xhr = new XMLHttpRequest();
    //        var formData = new FormData();
    //        xhr.open('POST', 'http://upload.qiniu.com/');
    //        formData.append('key', file.name);
    //        formData.append('token', token);
    //        formData.append('file', file);
    //        xhr.send(formData);
    //        xhr.onreadystatechange = function () {
    //            if(xhr.readyState == 4){
    //                if(xhr.status == 200 && xhr.responseText != "")
    //                {
    //                    var data = JSON.parse(xhr.responseText);
    //                    data.key = imageDomain + data.key;
    //                    defer.resolve(data.key);
    //                }
    //                else{
    //                    defer.reject('upload error');
    //                }
    //            }
    //        }
    //    } else {
    //        alert('没有上传文件 ╮(╯_╰)╭');
    //        defer.reject();
    //    }
    //    return defer.promise();
    //}
}
