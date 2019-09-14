/**
 * 版权所有 (c) 2018，《自制AI图像搜索引擎》作者。

 * 您可以下载和使用该软件。但它仅供个人学习和研究使用，禁止任何商业用途。

 * 您必须在此软件的所有副本（包括任何修改或衍生版本）中保留此许可证文件，且不能对其有任何修改。

 * 欢迎购买《自制AI图像搜索引擎》——一本全面介绍AI图像搜索引擎原理与实现的书。如有任何意见或建议，请发送邮件至imgsearch@126.com。
 */
$(".img_camera").on({
    click: function () {
        $(".img_camera_mouseenter,.img_camera_mouseenter_arrow").hide();
        $("#file").click();
    },
    mouseenter: function () {
        timer = setTimeout(function () {
            $(".img_camera_mouseenter,.img_camera_mouseenter_arrow").slideDown(200);
        }, 200);
    },
    mouseleave: function () {
        clearTimeout(timer);
        $(".img_camera_mouseenter,.img_camera_mouseenter_arrow").slideUp(200);
    }
});

$("#file").on("change", function (event) {
    var file = event.target.files || e.dataTransfer.files;
    if (file) {
        var fileReader = new FileReader();
        fileReader.onload = function () {
            $(".img_preview").attr("src", this.result);
        }
        fileReader.readAsDataURL(file[0]);
        $(".img_preview").show();

    }
});

$(".search_btn").on({
    click: function () {
        $("#file_upload").submit();
    }
});
