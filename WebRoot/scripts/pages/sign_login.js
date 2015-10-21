$(function () {

    var $baseStep = $('.sign-form-step.base') ,
        $interestStep = $('.sign-form-step.interest') ,
        $signForm = $('.sign-form') ,
        $stepItems = $('.sign-step-item'),
        $kcm=false,
        $baseButton = $('.sign-form-step.base .form-button').click(function () {
            function stepSwitch(){
            
                $stepItems.filter('.base').removeClass('active');
                $stepItems.filter('.interest').addClass('active');
            }
            $baseStep.valid(function (pass) {
            	verifiUserName();  //邮箱验证检测
            	verifiNickName(); //昵称验证
                if (pass &&userNametrue &&nick) {
                    $baseStep.hide(0);
                    $interestStep.show(0);
                    stepSwitch();
                }
            });
        });

   $("#regSubmit").click(function (event) {
        $interestStep.valid(function (pass) {
        	if (pass) {
                //alert('验证通过');
            	submitReg(pass);
            }else{
                event.preventDefault();
            }
        })
    })

});