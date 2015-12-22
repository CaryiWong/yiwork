//var EXIF = require('exports?EXIF!../lib/exif.new');
var EXIF = require('../lib/exif');

function drawImage(imgCode, afterWidth, orientation, p, callback) {
    var upImgWidth = p.width,
        upImgHeight = p.height;
    var result = imgCode;
    var hidCanvas = document.createElement('canvas');
    var hidCtx = hidCanvas.getContext('2d');
    var afterHeight;
    if (upImgHeight > upImgWidth) {
        afterHeight = afterWidth;
        afterWidth = afterHeight * upImgWidth / upImgHeight;
    } else {
        afterHeight = afterWidth * upImgHeight / upImgWidth;
    }
    if (upImgWidth > afterWidth || upImgHeight > afterHeight) {
        if (orientation <= 4) {
            hidCanvas.setAttribute("height", afterHeight);
            hidCanvas.setAttribute("width", afterWidth);
            if (orientation == 3 || orientation == 4) {
                hidCtx.translate(afterWidth, afterHeight);
                hidCtx.rotate(180 * Math.PI / 180);
            }
        } else {
            hidCanvas.setAttribute("height", afterWidth);
            hidCanvas.setAttribute("width", afterHeight);
            if (orientation == 5 || orientation == 6) {
                hidCtx.translate(afterHeight, 0);
                hidCtx.rotate(90 * Math.PI / 180);
            } else if (orientation == 7 || orientation == 8) {
                hidCtx.translate(0, afterWidth);
                hidCtx.rotate(270 * Math.PI / 180);
            }
        }
        drawImageIOSFix(hidCtx, p, 0, 0, upImgWidth, upImgHeight, 0, 0, afterWidth, afterHeight);
        result = convertCanvasToImage(hidCanvas).src;
    }
    callback && callback(result);
}

var compressImg = function (imgCode, afterWidth, callback) {
    var p = new Image();
    p.src = imgCode;
    p.onload = function () {
        var orientation;
        try {
            EXIF.getData(p, function () {
                orientation = parseInt(EXIF.getTag(p, "Orientation"));
                orientation = orientation ? orientation : 1;
                drawImage(imgCode, afterWidth, orientation, p, callback);
            });
        } catch (err) {
            orientation = 1;
            drawImage(imgCode, afterWidth, orientation, p, callback);
        }
    }
}

//canvas转图像
function convertCanvasToImage(canvas) {
    var image = new Image();
    image.src = canvas.toDataURL("image/jpeg");
    return image;
}

/**
 * 以下代 是修 canvas ios中显 压    。
 * Detecting vertical squash in loaded image.
 * Fixes a bug which squash image vertically while drawing into canvas for some images.
 * This is a bug in iOS6 devices. This function from https://github.com/stomita/ios-imagefile-megapixel
 *
 */
function detectVerticalSquash(img) {
    var iw = img.naturalWidth, ih = img.naturalHeight;
    var canvas = document.createElement('canvas');
    canvas.width = 1;
    canvas.height = ih;
    var ctx = canvas.getContext('2d');
    ctx.drawImage(img, 0, 0);
    var data = ctx.getImageData(0, 0, 1, ih).data;
    // search image edge pixel position in case it is squashed vertically.
    var sy = 0;
    var ey = ih;
    var py = ih;
    while (py > sy) {
        var alpha = data[(py - 1) * 4 + 3];
        if (alpha === 0) {
            ey = py;
        } else {
            sy = py;
        }
        py = (ey + sy) >> 1;
    }
    var ratio = (py / ih);
    return (ratio === 0) ? 1 : ratio;
}

/**
 * A replacement for context.drawImage
 * (args are for source and destination).
 */
function drawImageIOSFix(ctx, img, sx, sy, sw, sh, dx, dy, dw, dh) {
    var vertSquashRatio = detectVerticalSquash(img);
    // Works only if whole image is displayed:
    // ctx.drawImage(img, sx, sy, sw, sh, dx, dy, dw, dh / vertSquashRatio);
    // The following works correct also when only a part of the image is displayed:
    ctx.drawImage(img, sx * vertSquashRatio, sy * vertSquashRatio,
        sw * vertSquashRatio, sh * vertSquashRatio,
        dx, dy, dw, dh);
}

module.exports = compressImg;
