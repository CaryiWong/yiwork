;
$(function () {

    // 我的活动
    var $applyActivityButton = $('.myActivities-button.apply') ,
        $createActivityButton = $('.myActivities-button.create') ,
        $applyView = $('.myActivities-view.apply') ,
        $createView = $('.myActivities-view.mine');

    $applyActivityButton.click(function () {
        $applyView.show();
        $createView.hide();
        $applyActivityButton.addClass('black').removeClass('gray');
        $createActivityButton.addClass('gray').removeClass('black');
    });

    $createActivityButton.click(function () {
        $createView.show();
        $applyView.hide();
        $createActivityButton.addClass('black').removeClass('gray');
        $applyActivityButton.addClass('gray').removeClass('black');
    });


    // 基本信息
    function switchEditForm() {
        $buttonsEdit.show();
        $buttonsNow.hide();
        $buttonSubmitArea.show();
        $headArea.show().hover(function () {
            $headContent.show();
        }, function () {
            $headContent.hide();
        })
    }

    function switchNowForm() {
        $buttonsNow.show();
        $buttonsEdit.hide();
        $buttonSubmitArea.hide();
        $headArea.hide();
        $headFile.val('');
    }

    var $form = $('.info-form') ,
        $buttonsNow = $('.panel-header-buttons.now') ,
        $buttonsEdit = $('.panel-header-buttons.edit'),
        $buttonSubmitArea = $('.form-control-submit'),
        $buttonSubmit = $('.form-control-submit .button'),
        $buttonSubmitLink = $('.panel-header-buttonLink.save'),
        $headArea = $('.form-control-fileArea'),
        $headContent = $('.form-control-file-content'),
        $headFile = $('.form-control-file');
    $('.button-link.edit').click(function () {
        $form.switchForm('edit');
        switchEditForm();
    });

    $('.button-link.cancel').click(function () {
        $form.switchForm('now');
        switchNowForm();
    });

    $form.submit(function (event) {
        $form.valid(function (pass) {
            if (!pass) {
                event.preventDefault();
            }else  //add by kcm 0503
            {
            	updatePwd($.md5($("#oldPwd").val()),$.md5($("#newPwd").val()));
            	event.preventDefault();
            }
        })
    });

    $buttonSubmitLink.click(function (){
       $form.submit();
    })
});