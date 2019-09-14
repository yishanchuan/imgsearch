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
