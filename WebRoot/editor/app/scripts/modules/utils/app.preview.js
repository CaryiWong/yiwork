angular.module('app.preview', [])
    .factory('isAnimating', function () {
        var animating = false;
        return {
            get: function () {
                return animating;
            },
            set: function (v) {
                animating = v;
            }
        }
    })
    .factory('transitionAnimate', function (isAnimating) {
        function getDuration($page) {
            return parseFloat($page.css('transition-duration')) + parseFloat($page.css('transition-delay'));
        }

        return function ($active, $next) {
            var hidingDuration,
                showDuration;
            isAnimating.set(true);
            $active.addClass('hiding');
            hidingDuration = getDuration($active);
            setTimeout(function () {
                $active.removeClass('hiding active');
            }, hidingDuration * 1000);

            $next.css('transition-duration', '0').addClass('showing');
            setTimeout(function () {
                $next.css('transition-duration', '');
                showDuration = getDuration($next);
                $next.addClass('active').removeClass('showing');
                isAnimating.set(false);
                setTimeout(function () {

                }, showDuration * 1000);
            }, 0);
        }
    })
    .factory('formatDom', function () {
        var $loadDom = $('.loadDom');
        return function ($origins) {
            var $pages = $origins.clone().first().addClass('active').end();
            $pages.find('.page-image').addClass('active').find('.page-image-tip, .progress').remove();
            $pages.find('input').not('form input').remove();
            $pages.find('[editable-text] input').remove();
            $pages.find('.dragArea').remove();
            $pages.find('.dragPage-buttons').remove();
            $pages.find('.dragSwitch').removeAttr('draggable');
            $pages.find('.page-radio-add').remove();
            $pages.find('.page-radio-remove').remove();
            $pages.find('.page-checkbox-add').remove();
            $pages.find('.page-checkbox-remove').remove();
            $loadDom.append($pages);
            return $pages;
        }
    })
    .directive('copyCode', function (formatDom) {
        return {
            link: function (scope, ele) {
                var checkStyle = ['margin', 'padding', 'font', 'text-align', 'color', 'background'];

                function addStyle($doms) {
                    $doms.each(function () {
                        var $this = $(this),
                            dom = this;
                        $.each(checkStyle, function (index, style) {
                            dom.style[style] = $this.css(style);
                        })
                    })
                }

                ele.on('click', function () {
                    var range, selection;
                    var $editor = $('.editorArea-article');
                    ele.find('span').html('按下Ctrl/Command+C复制代码');
                    if (document.body.createTextRange) {
                        range = document.body.createTextRange();
                        range.moveToElementText(ele[0]);
                        range.select();
                    } else if (window.getSelection) {
                        selection = window.getSelection();
                        range = document.createRange();
                        range.selectNodeContents(ele[0]);
                        selection.removeAllRanges();
                        selection.addRange(range);
                    }
                })
                    .on('copy', function (event) {
                        event.preventDefault();
                        var clipboard = event.originalEvent.clipboardData;
                        //var $pages = formatDom($('.editorArea-article .page')),
                        //    html;
                        //addStyle($pages.find('[ng-html-compile] > *'));
                        //html = $pages[0].outerHTML;
                        var html = $('.summernote').code();
                        clipboard.setData('text/html', html);
                        ele.find('span').html('复制到剪贴板');
                    })
            }
        }
    })
    .factory('previewClass', function () {
        var type = '';
        return {
            get: function () {
                return type;
            },
            set: function (v) {
                type = v;
            }
        }
    })
    .directive('previewShow', function (formatDom, previewClass) {
        return {
            link: function (scope, ele, attrs) {
                // 预览处理
                var $preview = $('.preview'),
                    $previewPageArea = $preview.find('.preview-pageArea');
                ele.on('click', function () {
                    var $pages = formatDom($('.editorArea:visible .page')),
                        previewType = attrs['label'];
                    $previewPageArea.empty().append($pages);
                    previewClass.set(previewType);
                    dialog.show($preview.addClass(previewType));
                    console.log('----------------------------\n' + $pages.parent().html());
                })
            }
        }
    })
    .directive('previewHide', function (previewClass) {
        return {
            link: function (scope, ele, attr) {
                var $preview = $('.preview');
                ele.on('click', function () {
                    $preview.removeClass(previewClass.get());
                    dialog.close();
                })
            }
        }
    })
    .directive('previewSwitch', function (transitionAnimate, isAnimating) {
        return {
            link: function (scope, ele, attr) {
                var $preview = $('.preview'),
                    $previewPageArea = $preview.find('.preview-pageArea'),
                    buttonType = attr['previewSwitch'];
                ele.on('click', function () {
                    if (!isAnimating.get()) {
                        isAnimating.set(true);
                        var $activePage = $previewPageArea.find('.page.active');
                        if (buttonType === 'up') {
                            var $prevPage = $activePage.prev('.page');
                            if ($prevPage.length) {
                                transitionAnimate($activePage, $prevPage);
                            } else {
                                isAnimating.set(false);
                            }
                        } else if (buttonType === 'down') {
                            var $nextPage = $activePage.next('.page').not('.page-main');
                            if ($nextPage.length) {
                                transitionAnimate($activePage, $nextPage);
                            } else {
                                isAnimating.set(false);
                            }
                        }
                    }
                })
            }
        }
    })
    .factory('exportImage', function ($location) {
        return function () {
            var $editor,
                $canvas,
                $exportImg = $('.export-img');

            window.location.hash = '#/article';
            $editor = $('.editorArea-article').addClass('exportImage');
            html2canvas($editor[0], {
                height: 9999,
                onrendered: function (canvas) {
                    $editor.removeClass('exportImage');
                    window.location.hash = '#/export';
                    $canvas = $(canvas);
                    if (canvas.height > document.body.clientHeight) {
                        $canvas.css('zoom', document.body.clientHeight / canvas.height - .05);
                    }
                    $exportImg.append(canvas);
                    dialog.show($exportImg, function () {
                        $('.export-img-text .iconfont-remove').on('click', function () {
                            dialog.close();
                            $canvas.remove();
                        })
                    });
                }
            });
        }
    });
