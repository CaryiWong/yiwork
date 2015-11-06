$(function () {
    //var locationOriginalURL = window.location.origin,
    var locationOriginalURL = 'http://test.yi-gather.com:1717',
        userid = $.cookie('userid'),
        $individualForm = $("#individualForm"),
        $teamForm = $("#teamForm"),
        $companyForm = $("#companyForm"),
        $showForm = $individualForm,
        chooseType = 'individual',
        $chooseButton = $('.choose-btn-group button'),
        $uploadImg = $(".uploadImage"),
        $allLocalImg = $(".localImage"),
        imgIsUploaded = false,
        $loading = $('.loading'),
        contexturl = locationOriginalURL + '/pages/server/personalServer.html',
        titleImgURL = '';
    $('.form-button').on('touchstart', function (event) {
        event.preventDefault();
        var $thisForm = $(this),
            submitType = $thisForm.attr('data-submit'),
            $submitForm = $thisForm.parent("form");
        submitForm($submitForm, submitType);
    });
    $chooseButton.on('touchstart', function () {
        $showForm.hide();
        $allLocalImg.hide();
        $uploadImg.replaceWith($uploadImg.val('').clone(true));
        imgIsUploaded = false;
        var $this = $(this);
        chooseType = $this.attr("id");
        $chooseButton.removeClass('on');
        $this.addClass('on');
        if (chooseType === 'individual') {
            $showForm = $individualForm;
            contexturl = locationOriginalURL + '/pages/server/personalServer.html'
        }
        else if (chooseType === 'team') {
            $showForm = $teamForm;
            contexturl = locationOriginalURL + '/pages/server/teamServer.html'
        }
        else {
            $showForm = $companyForm;
            contexturl = locationOriginalURL + '/pages/server/teamServer.html'
        }
        $showForm.show();
    });
    var $warn = $('.warn');
    function submitForm(form, type) {
        form.valid(function (pass) {
            if (pass && imgIsUploaded === true) {
                $loading.removeClass('hidden');
                $.ajax(
                    locationOriginalURL + '/v20/yqservice/apply_service', {
                        dataType: 'json',
                        type: 'POST',
                        data: {
                            type: 'web',
                            userid: userid,
                            servicetype: type,
                            name: form.find("input[name='name']").val(),
                            city: form.find("input[name='city']").val(),
                            titleimg: titleImgURL,
                            context: form.find("textarea[name='context']").val(),
                            email: form.find("input[name='email']").val(),
                            tel: form.find("input[name='tel']").val(),
                            contexturl : contexturl,
                            servicesupplier: form.find("input[name='servicesupplier']").val()
                        }
                    }).success(function (data) {
                        $loading.addClass('hidden');
                        if (data.cord === 0) {
                            $warn.find('p').html('已提交成为雁行者的申请，请耐心等候!');
                            $warn.find('img').attr('src','/images/pages/server/icon_succeed@2x.png');
                            form[0].reset();
                            setTimeout(function(){appLocation();},4200);
                        } else {
                            $warn.find('p').html('发送失败 ' + data.msg);
                            $warn.find('img').attr('src','/images/pages/server/icon_attention@2x.png');
                        }
                    }).fail(function () {
                        $loading.addClass('hidden');
                        $warn.find('p').html('发送失败 !');
                        $warn.find('img').attr('src','/images/pages/server/icon_attention@2x.png');
                    });
                $warn.fadeIn(800);
                setTimeout(function(){ $warn.fadeOut(800); }, 3000);
            } else { imgIsUploaded === false ? alert('形象照片为必填项！') : alert(form.find('.valid-error').first().html());}


        })
    }

    var Ua = navigator.userAgent;
    var ua = Ua.toLocaleLowerCase();
    var action = {
                ios :function(method,params){
                    window.iosWebParams = function () {
                        return params;
                    };
                    window.location.href = method ;
                },
                android : function(method,params){
                    if(window.yiqi && window.yiqi[method]){
                        window.yiqi[method](params);
                    }
                }
    };
    function appLocation() {
        if (ua.match('yiqi') && !ua.match('micromessenger')) {
            if (ua.match('iphone' || 'ipod' || 'ipad')) {
                action.ios('myServices', userid)
            } else if (ua.match('android')) {
                action.android('myServices', userid)
            }
        }
    }
//利用userid获取用户信息
    $.ajax(
        locationOriginalURL + '/v20/user/getuser', {
            dataType: 'json',
            type: 'POST',
            data: {
                type: 'web',
                userid: userid
            }
        }).success(function (data) {
            if (data.cord === 0) {
                $(".individual-nickname").attr({value: data.data["nickname"], 'readonly': 'readonly'});
                $(".individual-tel").attr({value: data.data["telnum"], 'readonly': 'readonly'});
            } else {
                alert('获取用户信息失败 ' + data.msg);
            }
        }).fail(function () {
            alert('获取用户信息失败');
        });


//上传图片
    $uploadImg.on('change', function () {
        var $thisInput = $(this),
            $localImg = $thisInput.parent().next('.localImage'),
            $showImg = $localImg.find("#blah");
        readURL(this);
        if ($thisInput.val()) {
            if (typeof FormData === "undefined")
                throw new Error("FormData is not implemented");
            var request = new XMLHttpRequest();
            request.open('POST', locationOriginalURL + '/v20/upload/uploadimg');
            imgIsUploaded = false;
            $loading.removeClass('hidden');
            request.onreadystatechange = function () {
                $loading.addClass('hidden');
                if (request.readyState === 4) {
                    if (request.status === 200) {
                        var data = JSON.parse(request.responseText);
                        if (data.cord === 0) {
                            imgIsUploaded = true;
                            titleImgURL = locationOriginalURL + '/v20/download/img?path=' + data.data + '&type=web';
                        } else {
                            $localImg.hide();
                            alert('图片上传失败,请重新上传' + data.msg);
                        }
                    } else {
                        $localImg.hide();
                        alert("上传出错 " + request.status);
                    }
                }

            };
            var formdata = new FormData();
            formdata.append('img', $thisInput[0].files[0]);
            request.send(formdata);
        }
        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $showImg.attr('src', e.target.result);
                    $localImg.show();
                };
                reader.readAsDataURL(input.files[0]);
            }
        }
    });
});

