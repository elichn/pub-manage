$(function () {
    if (getClientInfo() == "PC") {
        $("#navBar > ul > li.dropdown").each(function () {
            var $obj = $(this);
            $obj.mouseover(function () {
                $("#navBar > ul > li.dropdown").removeClass("open");
                $obj.addClass("open");
            });
            $obj.mouseout(function () {
                $("#navBar > ul > li.dropdown").removeClass("open");
            });
            $obj.find("ul").mouseout(function () {
                $obj.removeClass("open");
            });
        });
    }

    $(window).resize(function () {
        var iframe = document.getElementById("showIFrame");
        iframe.height = $(window).height() - 50;
    });
    $(window).resize();
});

function getClientInfo() {
    var userAgentInfo = navigator.userAgent;
    var agents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"];
    var agentInfo;
    for (var i = 0; i < agents.length; i++) {
        if (userAgentInfo.indexOf(agents[i]) > 0) {
            agentInfo = userAgentInfo;
            break;
        }
    }
    if (agentInfo) {
        return agentInfo;
    } else {
        return "PC";
    }
}