// document.domain="yourwebsite.com";
$.extend($.messager.defaults, {
    ok: "确定",
    cancel: "取消"
});

var menuList = null;
var setting = {
    view: {
        showLine: false,
        showIcon: false,
        selectedMulti: false,
        dblClickExpand: false,
        addDiyDom: addDiyDom
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "fatherId"
        },
        key: {
            name: "name"
        }
    },
    callback: {
        beforeClick: beforeClick,
        onClick: function (event, treeId, treeNode) {
            if (!treeNode.isParent) {
                addTab(treeNode.id, treeNode.name, treeNode.showUrl);
            }
        }
    }
};

function addDiyDom(treeId, treeNode) {
    var spaceWidth = 5;
    var switchObj = $("#" + treeNode.tId + "_switch"),
        icoObj = $("#" + treeNode.tId + "_ico");
    switchObj.remove();
    icoObj.before(switchObj);

    if (treeNode.level > 1) {
        var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
        switchObj.before(spaceStr);
    }
}

function beforeClick(treeId, treeNode) {
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("menu");
        zTree.expandNode(treeNode);
        return false;
    }
    return true;
}

$(function () {
    $.ajax({
        type: "get",
        url: "/menuList.json",
        success: function (data) {
            menuList = data.list;
            initTree(menuList);
        }
    });

    var o = $('#centerTab');
    addTab(19, "欢迎", "/welcome", false);
});

/**
 * addTab 创建新选项卡
 * @param tabId    选项卡id
 * @param title    选项卡标题
 * @param url      选项卡远程调用路径
 * @param close    是否可关闭
 * @param forceRefresh 强制刷新
 */
function addTab(tabId, title, url, close, forceRefresh) {
    if (close == null) {
        close = true
    }

    // 当选项卡不存在的时候，即第一次打开选项卡
    var o = $('#centerTab');

    var name = 'iframe_' + tabId;
    var content = "<iframe width='100%' height='100%' frameborder='0' scrolling='auto' name='" + name + "' id='" + tabId + "' src='" + url + "'></iframe>";

    if (url != null && url.trim() != '') {
        o.tabs('add', {
            id: tabId,
            title: title,
            closable: close,
            cache: false,
            content: content,
            tools: [{
                iconCls: 'icon-mini-refresh',
                handler: function () {
                    $("iframe[name='" + name + "']").attr("src", url);
                }
            }]
        });
    } else {
        $.alert("未配置页面地址").time(3000);
    }
}

function colseTab(title) {
    $("#centerTab").tabs("close", title);
}

function searchMenu(key) {
    var zTree = $.fn.zTree.getZTreeObj("menu");
    if (key != null && key.trim() != '') {
        var nodes = zTree.getNodesByFilter(function (node) {
            return !(node.name.indexOf(key) > -1)
        });

        zTree.hideNodes(nodes);

        nodes = zTree.getNodesByParam("isHidden", false);
        showChildren(zTree, nodes);
        $.each(nodes, function (k, v) {
            showParent(zTree, v);
        })
    } else {
        var nodes = zTree.getNodesByParam("isHidden", true);
        zTree.showNodes(nodes);
    }
}

function showChildren(treeObj, nodes) {
    if (nodes != null) {
        $.each(nodes, function (k, v) {
            showChildren(treeObj, v.children);
            treeObj.showNodes(v.children);
        });
    }
}

function showParent(treeObj, node) {
    var n = node.getParentNode();
    if (n != null) {
        showParent(treeObj, n);
        treeObj.showNode(n);
    }
}

function initTree(nodes) {
    var treeObj = $("#menu");
    var zTree = $.fn.zTree.init(treeObj, setting, nodes);
    var curMenu = zTree.getNodes()[0];
    zTree.selectNode(curMenu);

    treeObj.hover(function () {
        if (!treeObj.hasClass("showIcon")) {
            treeObj.addClass("showIcon");
        }
    }, function () {
        treeObj.removeClass("showIcon");
    });

    var refer = $.cookie("refer");
    if (refer != null) {
        var url = getUrl(refer);
        var node = zTree.getNodesByFilter(function (n) {
            return getUrl(n.showUrl) == url;
        }, true);

        if (node != null) {
            addTab(node.id, node.name, refer);
            zTree.expandNode(node.getParentNode(), true, true, true);
            zTree.selectNode(node);
        }

        $.cookie('refer', '', {expires: -1});
    }
}

function getUrl(url) {
    if (url != null) {
        var i = url.indexOf("?");
        if (i >= 0) {
            return url.substr(0, i);
        }
    }
    return url;
}