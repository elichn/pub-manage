(function (d) {
    d['okValue'] = '确定';
    d['cancelValue'] = '取消';
    d['title'] = '消息';
})($.dialog.defaults);

// 实例化编辑器
var um = UM.getEditor('content', {
    imagePath: "",
    textarea: 'content',
    toolbar: [
        'source | undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |',
        'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize',
        '| justifyleft justifycenter justifyright justifyjustify |',
        'link unlink | emotion ',
        '| horizontal preview fullscreen', 'drafts'
//            , 'formula'
    ]
});

$(function () {
    $("#type").change(function () {
        var type = $(this).val();
        if (type == 1) {
            $("div.link").show();
            $("div.content").hide();
        } else {
            $("div.link").hide();
            $("div.content").show();
        }
    });
    initRoleTree();
});

function initRoleTree() {
    $.ajax({
        type: "get",
        url: "/role/getRoleTree.json",
        success: function (data) {
            var setting = {
                check: {
                    enable: true,
                    chkboxType: {"Y": "", "N": ""}
                },
                view: {
                    selectedMulti: false
                },
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "parentId"
                    },
                    key: {
                        name: "roleName"
                    }
                },
                callback: {
                    beforeClick: beforeClick,
                    onCheck: onCheck
                }
            };

            $.fn.zTree.init($("#roleTree"), setting, data.list);
        }
    });
}

function addNotice() {
    var type = $("#type").val();
    if (type == 1) {
        var url = $("#url").val();
        if (url == null || url.trim() == '') {
            $.alert("请填写链接地址！").time(3000);
            return;
        }
    } else {
        if (!um.hasContents()) {
            $.alert("请填写通知内容！").time(3000);
            um.focus();
            return;
        }
    }

    var zTree = $.fn.zTree.getZTreeObj("roleTree");
    var nodes = zTree.getCheckedNodes(true);

    var v = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += "&roleIds=" + nodes[i].id;
    }

    $.ajax({
        url: "/homePageNotice/add.json",
        type: "POST",
        data: $("#form").serialize() + v,
        success: function (data) {
            if (data && data.msg == "SUCCESS") {
                $.alert("提交成功").time(2000);
                resetForm();
            } else if (data && data.msg == "SESSION_TIME_OUT") {
                $.alert("登陆超时，请重新登录").time(3000);
            } else if (data && data.msg == "INVALID_PARAM") {
                $.alert("参数错误").time(3000);
            } else {
                $.alert("提交失败").time(3000);
            }
        }
    });
}

function beforeClick(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("roleTree");
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;
}

function onCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("roleTree"),
        nodes = zTree.getCheckedNodes(true),
        v = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += nodes[i].roleName + ",";
    }
    if (v.length > 0) v = v.substring(0, v.length - 1);
    var roleDiv = $("#roleDiv");
    roleDiv.attr("value", v);
}

function showRoles() {
    var roleTreeObj = $("#roleDiv");
    var offset = $("#roleDiv").offset();
    $("#treeContent").css({
        left: offset.left + "px",
        top: offset.top + roleTreeObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

function hideMenu() {
    $("#treeContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "roleDiv"
            || event.target.id == "treeContent" || $(event.target).parents("#treeContent").length > 0)) {
        hideMenu();
    }
}

function resetForm() {
    $("form")[0].reset();
    um.setContent("");
    $("#type").change();
}