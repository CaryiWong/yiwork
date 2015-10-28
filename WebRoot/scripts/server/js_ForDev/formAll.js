require('jquery.cookie');
require('./validation.js');

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
        var $this = $(this);
        chooseType = $this.attr("id");
        $chooseButton.removeClass('on');
        $this.addClass('on');
        if (chooseType === 'individual') {
            $showForm = $individualForm;
        }
        else if (chooseType === 'team') {
            $showForm = $teamForm;
        }
        else {
            $showForm = $companyForm;
        }
        $showForm.show();
    });

    function submitForm(form, type) {
        form.valid(function (pass) {
            if (pass && imgIsUploaded === true) {
                $(".server-form").append("<div class='loading'></div>");
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
                            servicesupplier: form.find("input[name='servicesupplier']").val()
                        }
                    }).success(function (data) {
                        if (data.cord === 0) {
                            $('.loading').remove();
                            alert('发送成功');
                            form[0].reset();
                            $allLocalImg.hide();
                            $uploadImg.replaceWith($uploadImg.val('').clone(true));
                            $('.valid-error').remove();
                        } else {
                            $loading.remove();
                            alert('发送失败 ' + data.msg);
                        }
                    }).fail(function () {
                        $loading.remove();
                        alert('发送失败');
                    });
            } else { imgIsUploaded === false ? alert('形象照片为必填项！') : alert(form.find('.valid-error').first().html());}


        })
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
            $(".server-form").append("<div class='loading'></div>");
            var $loading = $('.loading');
            request.onreadystatechange = function () {
                $loading.remove();
                if (request.readyState === 4) {
                    if (request.status === 200) {
                        var data = JSON.parse(request.responseText);
                        if (data.cord === 0) {
                            imgIsUploaded = true;
                            titleImgURL = locationOriginalURL + 'download/img?path=' + data.data + '&type=web';
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

