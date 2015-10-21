angular.module('article', [])
    .value('appPath', window.location.origin+'/' || 'http://192.168.1.30/')
    .factory('articleOperate', function ($http, $q, appPath, userid) {
        function errorHandle(err) {
            alert(err);
        }

        return {
            create: function (title, code, id) {
                if(!title || title.trim().length === 0){
                    alert('标题不得为空');
                    return false;
                }
                var defer = $q.defer();
                $.ajax(appPath + 'comm/createhtml', {
                    type: 'post',
                    dataType: 'json',
                    data: {
                        title: title,
                        htmlcode: code,
                        id: id,
                        userid: userid()
                    }
                }).done(function (data) {
                    if (data.cord === 0) {
                        data.url = appPath + data.data.url;
                        defer.resolve(data);
                    } else {
                        errorHandle('创建失败');
                    }
                }).fail(function (data) {
                    errorHandle('创建失败');
                });
                return defer.promise;
            },
            update: function (id, title, code) {
                return this.create(title, code, id);
            },
            find: function () {
                var defer = $q.defer();
                $http.get(appPath + 'comm/findall')
                    .success(function (data) {
                        if (data.cord === 0) {
                            var result = [];
                            _.each(data.data, function (d) {
                                d.url = appPath + d.url;
                                result.push(d);
                            });
                            defer.resolve(result);
                        } else {
                            errorHandle('查询失败');
                        }
                    })
                    .error(function (data) {
                        errorHandle('查询失败');
                    });
                return defer.promise;
            },
            findOne: function (id) {
                var defer = $q.defer();
                $http.get(appPath + 'comm/findone', {
                    params: {
                        id: id
                    }
                })
                    .success(function (data) {
                        if (data.cord === 0) {
                            var result = data.data;
                            result.url = appPath + result.url;
                            defer.resolve(result);
                        } else {
                            errorHandle('查询失败');
                        }
                    })
                    .error(function (data) {
                        errorHandle('查询失败');
                    });
                return defer.promise;
            }
        }
    })
    .factory('imageUpLoad', function ($q, appPath) {
        return function (files) {
            var defer = $q.defer();
            var xhr = new XMLHttpRequest();
            var formdata = new FormData();
            formdata.append('img ', files[0]);
            xhr.open('POST', appPath + '/v20/upload/uploadimg');
            xhr.send(formdata);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    var data = JSON.parse(xhr.responseText);
                    if (data.cord === 0) {
                        data.url = appPath + '/v20/download/img?path=' + data.data;
                        defer.resolve(data);
                    } else {
                        alert('上传图片失败');
                        defer.reject();
                    }
                }
            }
            return defer.promise;
        }
    })
