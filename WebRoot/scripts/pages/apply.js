$(function () {
    //  apply-form-member
    var $memberForm = $('.apply-form-member').submit(function (event) {
        $memberForm.valid(function (pass) {
            if (!pass) {
                event.preventDefault();
            } else {
                event.preventDefault();//edit by kcm@4.14
                //saveMenber();
                showUserName(); //告诉用户登录名什么的
            }
        })
    });


    // 星座
    var $startValue = $('.constellation .form-control-value');
    if ($startValue.length) {
        var birthPicker = customUI.datePicker.pickadate('picker');
        birthPicker.on('close', function () {
            var month = birthPicker.get('select', 'mm') - 0,
                day = birthPicker.get('select', 'dd') - 0,
                start = util.start(month, day);
            $startValue.html(start);
        });
    }


    //  apply-form-enter
    var $enterForm = $('.apply-form-enter').submit(function (event) {
        $enterForm.valid(function (pass) {
            if (!pass) {
                event.preventDefault();
            } else {
                event.preventDefault();
                util.confirm.show("是否确认提交团队入驻申请?", function () {
                    saveEnterMenber()
                })
            }
        })
    });


    //  apply-form-activity
    var $activityForm = $('.apply-form-activity').submit(function (event) {
        $activityForm.valid(function (pass) {
            if (!pass) {
                event.preventDefault();
            } else {
                event.preventDefault();  //edit by kcm @0414 18.43
                util.confirm.show("确认报名该活动?", function () {
                    joinEvent()
                })
                //joinEvent();	 //报名
            }
        })
    });

    util.device({ app: function () {
        $('.header-xs').hide();
        $('.apply-form').css({marginTop: 0, paddingTop: 0});
    } });
});
