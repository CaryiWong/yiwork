;
var picture = {
    $body: $('body'),
    $picture: $('.picture').css('cursor', 'pointer'),
    $list: $('<div class="picture-dialog">' +
        '<div class="picture-dialog-remove"><i class="add-icon-lClose"></i></div>' +
        '<div class="picture-switch">' +
        '   <button class="left" ><i class="add-icon-left"></i></button>' +
        '   <button class="right" ><i class="add-icon-right "></i></button>' +
        '</div>' +
        '<div class="picture-imgs">' +
        '</div>'),
    getOriginImage: function (src, callback) {
        var $img = $('<img src="' + src + '">').load(function () {
            callback && callback($img);
        });
        return $img;
    },
    prevImg: function ($imgArea, $img) {
        var $prevImg = $img.prev('.picture'),
            self = this;
        $prevImg = $prevImg.length ? $prevImg : $img.prev().find('.picture');
        if ($prevImg.length) {
            this.getOriginImage($prevImg.attr('data-picture-src'), function ($originImg) {
                $imgArea.prepend($originImg);
                self.prevImg($imgArea, $prevImg);
            })
        }
    },
    nextImg: function ($imgArea, $img) {
        var $nextImg = $img.next('.picture'),
            self = this;
        $nextImg = $nextImg.length ? $nextImg : $img.next().find('.picture');
        if ($nextImg.length) {
            this.getOriginImage($nextImg.attr('data-picture-src'), function ($originImg) {
                $imgArea.append($originImg);
                self.nextImg($imgArea, $nextImg);
            })
        }
    },
    makeList: function ($img) {
        var $imgArea = this.$imgArea,
            $list = this.$list;
        if ($img.is('[data-picture-parent]')) {
            var parentClass = $img.attr('data-picture-parent');
            $img = $img.parents('.' + parentClass);
        }
        if ($img.nextAll('.picture').length || $img.next().find('.picture').length
            || $img.prevAll('.picture').length || $img.prev().find('.picture').length) {
            this.prevImg($imgArea, $img);
            this.nextImg($imgArea, $img);
            $list.removeClass('single');
        } else {
            $list.addClass('single');
        }
    },
    pictureClick: function () {
        var $this = $(this),
            src = $this.attr('data-picture-src'),
            $list = picture.$list;
        if (!!src === false) return;
        picture.$imgArea = $list.find('.picture-imgs');
        util.loading.show();
        picture.getOriginImage(src, function ($img) {
            util.loading.close();
            var $imgArea = $list.find('.picture-imgs');
            $imgArea.find('img').remove();
            $imgArea.append($img);
            picture.makeList($this);
            util.dialog.show($list, function () {
                $img.addClass('active');
                util.dialog.setStyle($list);
                $('#dialog-mask').one('click', util.dialog.close);
            });
        });
    },
    switchImg: function ($old, $new) {
        $old.removeClass('active');
        $new.addClass('active');
        util.dialog.setStyle(picture.$list);
    },
    switchLeftClick: function () {
        var $activeImg = picture.$list.find('img.active');
        var $newImg = $activeImg.prev('img');
        if ($newImg.length) {
            picture.switchImg($activeImg, $newImg);
        }
    },
    switchRightClick: function () {
        var $activeImg = picture.$list.find('img.active');
        var $newImg = $activeImg.next('img');
        if ($newImg.length) {
            picture.switchImg($activeImg, $newImg);
        }
    },
    init: function () {
        this.$body.on('click', '.picture', this.pictureClick);
        this.$list.on('click', '.picture-dialog-remove', util.dialog.close);
        this.$list.on('click', '.picture-switch button.left', this.switchLeftClick);
        this.$list.on('click', '.picture-switch button.right', this.switchRightClick);
    }
};
picture.init();

