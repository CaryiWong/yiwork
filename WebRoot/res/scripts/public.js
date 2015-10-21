var $contentFrame = $('.content-frame');
var $iframes = $('iframe').load(function () {
    $iframes.each(function () {
        var $this = $(this);
        var height = this.contentDocument.body.clientHeight;
        $this.height(height + 100);
    });
});

var $nav = $('.nav-list .panel-body li').click(function (event) {
    event.preventDefault();
    var $this = $(this),
        href = $this.find('a').attr('href');
    $contentFrame.attr('src', href);
    $nav.removeClass('active');
    $this.addClass('active');
});

$('.btn-back').click(function (event) {
    event.preventDefault();
    window.history.go(-1);
});


var $needVideoSelectGroup = $('.need-video-select-group');
var $needActivitySelectGroup = $('.need-activity-select-group');
$('.need-type-select').change(function () {
    var $this = $(this);
    $needActivitySelectGroup.addClass('hide');
    $needVideoSelectGroup.addClass('hide');
    if ($this.val() - 0 === 1) {
        $needVideoSelectGroup.removeClass('hide');
    } else if ($this.val() - 0 === 2) {
        $needActivitySelectGroup.removeClass('hide');
    }
});

$('.space .space-imgList li').click(function (){
    var $this = $(this);
    $this.remove();
});


var pay = {
    $add: $('.pay .pay-add'),
    $rows: $('.pay .pay-rows'),
    itemTemplate: '<div class="row pay-item">'+
        '         <div class="col-xs-2 ">'+
        '             <input class="form-control valid-input" name="pkey" type="text" data-valid-rule="required"/>' +
        '         </div>' +
        '         <span class="pull-left">:</span>' +
        '         <div class="col-xs-2">' +
        '             <input class="form-control valid-input" name="pval" type="text" data-valid-rule="required,number"/>' +
        '         </div>' +
        '     <button class=" btn btn-link text-danger btn-sm pay-remove">' +
        '         <i class="glyphicon glyphicon-remove text-danger"></i>' +
        '     </button>' +
        ' </div>',
    addClick: function (event){
        event.preventDefault();
        var $item = $(pay.itemTemplate);
        pay.$rows.append($item);
        $.addValid( $item.find('input') );
    },
    removeClick: function (event){
        event.preventDefault();
        $(this).parents('.pay-item').remove();
    },
    init: function (){
        this.$add.on('click', this.addClick);
        $('body').on('click', '.pay .pay-remove', this.removeClick)
    }
};
pay.init();