(function (d) {
    d['okValue'] = '确定';
    d['cancelValue'] = '取消';
    d['title'] = '提示';
})($.dialog.defaults);

var rMenu;
$(function () {
    rMenu = $("#rMenu");

    $.ajax({
        type: "get",
        url: "/resc/getResourceListByRole.json",
        success: function (data) {
            var setting = {
                view: {
                    addHoverDom: addHoverDom
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
                    onClick: function (event, treeId, treeNode) {
                        viewResc(treeNode);
                    },
                    onRightClick: onRightClick
                }
            };

            $.fn.zTree.init($("#rescTree"), setting, data.list);
        }
    });

    $.validator.setDefaults({
        submitHandler: function () {
            modifyResc();
        }
    });

    $("#form").validate({
        rules: {
            name: {
                required: true,
                rangelength: [1, 20]
            },
            showUrl: {
                rangelength: [1, 100]
            },
            resString: {
                rangelength: [1, 300]
            },
            priority: {
                required: true,
                digits: true
            }
        },
        messages: {
            name: {
                required: "请输入资源名称",
                rangelength: "长度必须介于{0} 和 {1} 之间的字符串"
            },
            showUrl: {
                rangelength: "长度必须介于{0} 和 {1} 之间的字符串"
            },
            resString: {
                rangelength: "长度必须介于{0} 和 {1} 之间的字符串"
            },
            priority: {
                required: "请输入优先级",
                digits: "请输入正整数"
            }
        },
        highlight: function (element) {
            $(element).closest("div").removeClass("has-success");
            $(element).closest("div").addClass("has-error");
        },
        unhighlight: function (element) {
            $(element).closest("div").removeClass("has-error");
            $(element).closest("div").addClass("has-success");
        }
    });
});

function onRightClick(event, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("rescTree");
    if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
        treeObj.cancelSelectedNode();
        showRMenu("root", event.clientX, event.clientY);
    } else if (treeNode && !treeNode.noR) {
        treeObj.selectNode(treeNode);
        showRMenu("node", event.clientX, event.clientY);
    }
}

// 菜单鼠标提示
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_a");
    sObj.attr("title","右键进行操作");
}

function showRMenu(type, x, y) {
    if (type == "root") {
        $("#m_del").hide();
    } else {
        $("#m_del").show();
    }

    if ($("#rMenu").height() > 0) {
        $("#rMenu table").show();
        rMenu.css({"top": y + "px", "left": x + "px", "display": "block"});
        $("body").bind("mousedown", onBodyMouseDown);
    }
}

// 资源信息
function viewResc(node) {
    $("#rescId").html(node.id);
    $("#rescName").html(node.name);
    $("#rescUrl").html(node.showUrl);
    $("#rescResString").html(node.resString);
    $("#rescPriority").html(node.priority);
    $("#rescDescn").html(node.descn);
    $("#isShowMenu").html(node.showMenu == 1 ? "是" : "否");
}

// 新增当前节点
function addNewNode(currentNode) {
    var treeObj = $.fn.zTree.getZTreeObj("rescTree");
    treeObj.addNodes(treeObj.getSelectedNodes()[0], currentNode);
}

// 更新当前节点
function updateCurrentNode(currentNode) {
    var treeObj = $.fn.zTree.getZTreeObj("rescTree");
    var node = treeObj.getSelectedNodes()[0];
    node.name = currentNode.name;
    node.showUrl = currentNode.showUrl;
    node.resString = currentNode.resString;
    node.priority = currentNode.priority;
    node.showMenu = currentNode.showMenu;
    node.descn = currentNode.descn;
    treeObj.updateNode(node);
    viewResc(currentNode);
}

// 增加资源
function addTreeNode() {
    var treeObj = $.fn.zTree.getZTreeObj("rescTree");
    var fatherId;
    hideRMenu();
    if (treeObj.getSelectedNodes()[0]) {
        fatherId = treeObj.getSelectedNodes()[0].id;
    } else {
        fatherId = 0;
    }
    showEditWin(null, fatherId);
}

// 编辑资源
function editTreeNode() {
    var treeObj = $.fn.zTree.getZTreeObj("rescTree");
    var fatherId;
    hideRMenu();
    if (treeObj.getSelectedNodes()[0]) {
        fatherId = treeObj.getSelectedNodes()[0].fatherId;
        if (null == fatherId) {
            fatherId = 0;
        }
        showEditWin(treeObj.getSelectedNodes()[0], fatherId);
    } else {
        $.alert("请选择父节点").time(3000);
    }
}

// 删除资源
function removeTreeNode() {
    var treeObj = $.fn.zTree.getZTreeObj("rescTree");
    hideRMenu();
    var nodes = treeObj.getSelectedNodes();
    var flag = false;
    var msg = "";
    if (nodes && nodes.length > 0) {
        if (nodes[0].children && nodes[0].children.length > 0) {
            msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
            if (confirm(msg) == true) {
                flag = true;
            }
        } else {
            msg = "要删除资源:" + nodes[0].name + "。\n\n请确认！";
            if (confirm(msg) == true) {
                flag = true;
            }
        }

        if (flag) {
            treeObj.removeNode(nodes[0]);
            $.ajax({
                type: "get",
                url: "/resc/deleteResc.json",
                data: {
                    rescId: nodes[0].id,
                    rescName: nodes[0].name
                },
                success: function (data) {
                    if (data.msg) {
                        $.alert("删除成功").time(2000);
                    } else {
                        $.alert("删除失败").time(3000);
                    }
                }, error: function () {
                    $.alert("提交失败").time(3000);
                }
            });
        }
    }
}

var editWin;
function showEditWin(node, pid) {
    $("#form span").remove();
    $("#form label.error").remove();
    $("#form div").removeClass("has-error has-success has-feedback");

    var title = "增加资源";
    if (node) {
        title = "编辑资源";
        $("#id").val(node.id);
        $("#pid").val(node.fatherId == null ? 0 : node.fatherId);
        $("#name").val(node.name);
        $("#showUrl").val(node.showUrl);
        $("#resString").val(node.resString);
        $("#priority").val(node.priority);
        $("#showMenu").attr("checked", node.showMenu == 1);

        $("#descn").val(node.descn);
        $("#form").attr("action", "/resc/updateResc.json");
    } else {
        $("#id").val("");
        $("#pid").val(pid);
        $("#name").val("");
        $("#showUrl").val("");
        $("#resString").val("");
        $("#priority").val("");
        $("#showMenu").attr("checked", false);
        $("#descn").val("");
        $("#form").attr("action", "/resc/addResc.json");
    }

    $("#showMenu").change(function () {
        if ($(this).attr("checked")) {
            $("#showMenuVal").val(1);
        } else {
            $("#showMenuVal").val(0);
        }
    });

    $("#showMenu").change();

    editWin = $.dialog({
        title: title,
        width: 600,
        lock: true,
        content: document.getElementById('editWin')
    });
}

function hideRMenu() {
    if (rMenu) rMenu.css({"display": "none"});
    $("body").unbind("mousedown", onBodyMouseDown);
}

function onBodyMouseDown(event) {
    if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
        rMenu.css({"display": "none"});
    }
}

function modifyResc() {
    $.ajax({
        type: "post",
        url: $("#form").attr("action"),
        data: $("#form").serialize(),
        success: function (data) {
            if (data.msg) {
                $.alert("操作成功").time(2000);
                if ($("#form").attr("action").indexOf("update") > 0) {
                    updateCurrentNode(data.currentResc);
                } else {
                    addNewNode(data.currentResc);
                }
                editWin.close();
            } else {
                $.alert("提交失败").time(3000);
            }
        }, error: function () {
            $.alert("提交失败").time(3000);
        }
    });
}