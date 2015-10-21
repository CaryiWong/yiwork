angular.module('app.server', [])
    .factory('getFormTemplates', function () {
        return function () {
            return [
                {
                    name: 'label'
                },
                {
                    name: 'text'
                },
                {
                    name: 'checkbox'
                },
                {
                    name: 'radio'
                }
            ]
        }
    })
    .factory('getLabels', function ($http, labelsPath) {
        return function (cb) {
            $http.get(labelsPath, {
                params: {
                    page: 0,
                    size: 999,
                    pid: 0
                }
            }).success(function (data) {
                if (data.cord === 0) {
                    var result = [];
                    $.each(data.data, function () {
                        result.push({
                            name: this.zname,
                            value: this.id
                        });
                    });
                    cb && cb(result);
                } else {
                    console.error('获领域失败');
                }
            }).error(function () {
                console.error('获领域失败');
            })
        };
    })
    .factory('getAddress', function ($http, addressPath) {
        return function (cb) {
            $http.get(addressPath).success(function (data) {
                if (data.cord === 0) {
                    var result = [];
                    $.each(data.data, function () {
                        result.push({
                            name: this.spacename,
                            value: this.id
                        });
                    });
                    cb && cb(result);
                } else {
                    console.error('获地址失败');
                }
            }).error(function () {
                console.error('获地址失败');
            })
        };
    })
    .factory('getConnectResource', function ($http, activityPath, coursePath) {
        return function (type, filter, cb) {
            var data = {page: 0, size: 60},
                path;
            if (type === 'activity') {
                path = activityPath;
            } else if (type === 'course') {
                path = coursePath;
            }
            if (filter === 'blog') {
                data.listtype = 'needreview';
            } else if (filter === 'form') {
                data.listtype = 'needform';
            }
            $.ajax(path, {
                type: 'post',
                dataType: 'json',
                data: data
            })
                .success(function (data) {
                    if (data.cord === 0) {
                        var results = [];
                        $.each(data.data, function () {
                            var resource = this;
                            if (this.h5url && this.h5objid) {
                                var aData = {
                                    title: resource.title,
                                    address: resource.address,
                                    time: resource.opendate,
                                    id: resource.id,
                                    h5id: resource.h5objid,
                                    cost: resource.cost,
                                    pricetype: resource.pricetype,
                                    pricekey: resource.pricekey1,
                                    pricevalue: resource.pricevalue1
                                };
                                results.push(aData);
                            }
                        });
                        cb && cb(results);
                    }
                })
                .fail(function (e) {
                    console.error('获取' + type + '列表失败');
                })
        }
    })
    .factory('sendOptions', function ($rootScope, articleInfo, interactInfo, saveOptionPath) {
        // 图文 h5 各自的objectId
        return function (optionId, templateId, value) {
            var editorInfo,
                editorType = $rootScope.label,
                objId;
            if (editorType === 'article') {
                editorInfo = articleInfo;
            } else if (editorType === 'interact') {
                editorInfo = interactInfo;
            } else {
                console.log('无opid传输');
                return;
            }
            objId = editorInfo.getObjectId();
            sendObj = {
                "type": 'web',
                "options.id": optionId,
                "template.id": templateId,
                texts: value,
                objtype: 'activity'
            };
            if (objId) {
                sendObj.objid = objId;
            }
            $.ajax(saveOptionPath, {
                data: sendObj,
                dataType: 'json',
                type: 'post'
            }).success(function (data) {
                if (data.cord === 0) {
                    editorInfo.setObjectId(data.data.objid);
                }
            });
        }
    })
    .factory('sendBasic', function ($q, createActivityPath, createCoursePath, userId) {
        return function (type, basic, cb) {
            var path,
                data = {
                    "type": 0,
                    "title": basic.title,
                    "summary": basic.introduce || '',
                    "cover": basic.cover || '',
                    "open": basic.startTime || '',
                    "end": basic.endTime || '2015-01-23 12:00:00',
                    "isvip": basic.isVip,
                    "cost": basic.pay, // 0免费  1收费
                    "pricetype": 1, // 0是单一  1是多项收费 因为是非会员+会员价 所以必然是1
                    "price": basic.moneyPair[0].value,
                    "vprice": basic.moneyPair[1].value,
                    "pricekey": '非会员价,会员价', // 多收费键值
                    "pricevalue": basic.moneyPair[0].value.toString() + ','
                    + basic.moneyPair[1].value.toString(), // 多收费钱值
                    "address": basic.address.name,
                    "spaceInfo.id": basic.address.value,
                    "tel": basic.phone || '',
                    "maxnum": basic.people || '50', // 最多人数
                    "user.id": userId.get(),
                    "userid": userId.get(),
                    "labels": basic.domain.value,
                    "activityType": basic.activityType,
                    "imgtexturl": basic.imgtexturl || '',
                    "h5url": basic.h5url || '',
                    "huiguurl": basic.huiguurl || ''
                };
            if (data.cost.toString() === '0') {
                data.price = 0;
                data.vprice = 0;
                data.pricevalue = '0,0';
            }
            if (type === 'activity') {
                path = createActivityPath;
            } else if (type === 'course') {
                path = createCoursePath;
            }

            $.ajax(path, {
                type: 'post',
                dataType: 'json',
                data: data
            })
                .success(function (da) {
                    if (da.cord === 0) {
                        var d = da.data;
                        basic.id = d.id;
                        basic.objtype = type;
                        basic.userid = d.user.id;
                        console.log(type + '父内容保存成功');

                        if (basic.pay === '1' && basic.activityType === 'set') {
                            async.eachSeries(basic.childActivity, function (child, ecb) {
                                var activityClone = _.clone(data);
                                activityClone.price = child.moneyPair[0].value;
                                activityClone.vprice = child.moneyPair[1].value;
                                activityClone.pricevalue = activityClone.price.toString() + ','
                                + activityClone.vprice.toString();
                                activityClone.title = child.name;
                                activityClone.maxnum = child.people;
                                activityClone.activityType = child.activityType;
                                activityClone.pid = basic.id;
                                if (activityClone.price === '0' && activityClone.vprice === '0') {
                                    activityClone.cost = '0';
                                }
                                $.ajax(path, {
                                    type: 'post',
                                    dataType: 'json',
                                    data: activityClone
                                })
                                    .success(function (data) {
                                        if (data.cord === 0) {
                                            console.log('子活动' + child.name + '创建成功');
                                            child.id = data.data.id;
                                            ecb();
                                        } else {
                                            ecb('创建子活动集合失败 ' + data.msg);
                                        }
                                    })
                                    .error(function (e) {
                                        ecb('创建子活动集合失败 ' + e);
                                    })
                            }, function (err) {
                                if (err) {
                                    console.error(err);
                                } else {
                                    cb && cb();
                                }
                            });
                        } else {
                            cb && cb();
                        }
                    } else {
                        console.error(type + '父内容保存失败');
                    }
                })
                .error(function () {
                    console.error(type + '父内容保存失败');
                })
        }
    })
    .factory('getCode', function () {
        return function ($doms) {
            var result = '';
            $doms.each(function () {
                result += this.outerHTML;
            });
            return result;
        }
    })
    .factory('sendHTML', function (formatDom, saveAndBindPath, getCode, articleInfo, interactInfo, userId) {
        return function (type, cb) {
            var code,
                basic;
            if (type === 'interact') {
                var $forms = formatDom($('.editorCopy .editorArea-interact .page'));
                basic = interactInfo.getBasic();
                $forms.filter('.activityInfo').attr('data-activity-id', basic.id);
                if (basic.pay === '1' && basic.activityType === 'set') {
                    $forms.find('.price-checkbox').each(function (index) {
                        var $this = $(this);
                        this.value = _.find(basic.childActivity, function (c) {
                            return c.name === $this.attr('data-activity-name');
                        }).id;
                    });
                }
                code = getCode($forms);
            }
            $.ajax(saveAndBindPath, {
                dataType: 'json',
                type: 'post',
                data: {
                    htmltype: 'h5',
                    pagecode: code,
                    actid: basic.id,
                    userid: basic.userid
                }
            }).success(function (data) {
                if (data.cord === 0) {
                    console.log(type + '代码发送成功');
                    basic.h5url = data.data;
                    cb && cb(undefined, data);
                } else {
                    console.error(type + '代码发送失败');
                    cb && cb('代码发送失败');
                }
            }).fail(function (data) {
                console.error(type + '代码发送失败');
                cb && cb('代码发送失败');
            })
        }
    })
    .factory('updateHTML', function (updatepagePath, getCode, formatDom, activityInfo, courseInfo, interactInfo) {
        function addMultiplePrice(resource, code) {
            var $code = $(code);
            if (resource.cost === 1 && resource.pricetype === 1) {
                var $control = $('<div class="form-group interact-form interact-form-select ' +
                'interact-form-multiplePrice">' +
                '<label>请选择你要购买的类型</label>' +
                '<select name="price" class="form-control"></select>' +
                '</div>');
                var $select = $control.find('select');
                _.each(resource.pricekey, function (key, index) {
                    var $option = $('<option data-index="' + index + '" >' + key + ':'
                    + resource.pricevalue[index] + '</option>');
                    $select.append($option);
                });
                $code.find('.page-form-default').prepend($control);
            }
            return $code[0].outerHTML;
        }

        return function (objid, objtype, t, cb) {
            var code = getCode(formatDom($('.editorArea .page').not('.page-main'))),
                basic = interactInfo.getBasic(),
                type,
                resource;
            if (objtype === 'activity') {
                resource = _.find(activityInfo.get(), function (t) {
                    return t.id === objid;
                });
            } else if (objtype === 'course') {
                resource = _.find(courseInfo.get(), function (t) {
                    return t.id === objid;
                });
            }
            if (t === 'interact') {
                code = addMultiplePrice(resource, code);
                type = 'h5';
            }

            $.ajax(updatepagePath, {
                type: 'post',
                dataType: 'json',
                data: {
                    objid: resource.h5id,
                    pagecode: code,
                    htmltype: type
                }
            }).success(function (data) {
                if (data.cord === 0) {
                    basic.id = resource.id;
                    basic.objtype = objtype;
                    cb && cb(data);
                } else {
                    console.error('更新网页失败');
                }
            }).fail(function () {
                console.error('更新网页失败');
            })
        }
    })
    .factory('bindReview', function (bindReviewPath, sendHTML, userId, articleInfo) {
        return function (objid, objtype, cb) {
            var basic = articleInfo.getBasic();
            sendHTML('article', function (err, data) {
                if (!err) {
                    basic.imgtexturl = data.data;
                    basic.imgtextobjid = articleInfo.getObjectId();
                    $.ajax(bindReviewPath, {
                        type: 'post',
                        dataType: 'json',
                        data: {
                            url: data.data2,
                            idtype: objtype,
                            id: objid,
                            userid: userId.get()
                        }
                    }).success(function (data) {
                        if (data.cord === 0) {
                            cb && cb(data);
                        } else {
                            console.error('绑定回顾失败');
                        }
                    }).fail(function () {
                        console.error('绑定回顾失败');
                    })
                }
            });
        }
    });
