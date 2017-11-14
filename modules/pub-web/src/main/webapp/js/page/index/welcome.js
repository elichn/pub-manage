$(function () {
    $.ajax({
        url: "/homePageNotice/getNotice.json",
        type: "get",
        success: function (data) {
            if (data.hn != null) {
                if (data.hn.type == 1) {
                    $("div.container").html('<iframe id="noticeFrame" style="border: 0" src="' + data.hn.url + '"></iframe>');

                    $("#noticeFrame").load(function () {
                        var h = $(this).contents().find("body").height();
                        $(this).width($("div.container").width());
                        $(this).height(h);
                    });
                } else {
                    $("div.container").html(data.hn.content);
                }
            } else {
                $("div.container").html("暂无通知");
            }
        }
    });
});
