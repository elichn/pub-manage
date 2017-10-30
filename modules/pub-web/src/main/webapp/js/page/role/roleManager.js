(function (d) {
    d['okValue'] = '确定';
    d['cancelValue'] = '取消';
    d['title'] = '提示';
})($.dialog.defaults);

var rMenu;
$(function () {
    $.ajax({
        type: "get",
        url: "/role/getRoleTree.json",
        success: function (data) {
            var setting = {
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
                    onClick: function (event, treeId, treeNode) {
//                        $("#selectRoleName").val(treeNode.roleName);
                        buildRescTree(treeNode.id);
                    },
                    onCheck: function (event, treeId, treeNode) {
                        var treeObj = $.fn.zTree.getZTreeObj("roleTree");
                        var nodes = treeObj.getNodesByFilter(function () {
                            return true;
                        }, false, treeNode);

                        for (var k in nodes) {
                            if (treeNode.checked) {
                                treeObj.checkNode(nodes[k], false);
                            }

                            treeObj.setChkDisabled(nodes[k], treeNode.checked);
                        }
                    },
                    onRightClick: onRightClick,
                    beforeRename: beforeRename
                }
            };

            $.fn.zTree.init($("#roleTree"), setting, data.list);
        }
    });

    $("#form").validate({
        rules: {
            roleName: {
                required: true,
                rangelength: [2, 12],
                remote: {
                    url: "/role/isRole.json",
                    type: "get",
                    data: {
                        id: function () {
                            return encodeURI($("#id").val().trim());
                        },
                        roleName: function () {
                            return encodeURI($("#roleName").val().trim());
                        }
                    }
                }
            },
            code: {
                rangelength: [4, 20],
                remote: {
                    url: "/role/isRoleExist.json",
                    type: "get",
                    data: {
                        id: function () {
                            return encodeURI($("#id").val().trim());
                        },
                        code: function () {
                            return encodeURI($("#code").val().trim());
                        }
                    }
                }
            }
        },
        messages: {
            roleName: {
                required: "请输入角色名",
                rangelength: "长度必须介于{0} 和 {1} 之间的字符串",
                remote: "角色名已存在"
            },
            code: {
                rangelength: "长度必须介于{0} 和 {1} 之间的字符串",
                remote: "唯一标识已经存在"
            }
        },
        highlight: function (element) {
            $(element).closest("div").removeClass("has-success has-feedback");
            $(element).closest("div").addClass("has-error has-feedback");
            $(element).nextAll("span").remove();
            $(element).closest("div").append('<span class="glyphicon glyphicon-remove form-control-feedback"></span>');
        },
        unhighlight: function (element) {
            $(element).closest("div").removeClass("has-error has-feedback");
            $(element).closest("div").addClass("has-success has-feedback");
            $(element).nextAll("span").remove();
            $(element).closest("div").append('<span class="glyphicon glyphicon-ok form-control-feedback"></span>');
        },
        submitHandler: function (form) {
            modifyRole();
        }
    });

    rMenu = $("#rMenu");
});

function buildRescTree(roleId) {
    $("#rescSaveBtn").css("display", "none");
    $("#rescTree").css("display", "block");
    $("#rescDiv").css("display", "block");
    $.ajax({
        type: "get",
        url: "/role/getRescByRole.json",
        data: {
            id: roleId
        },
        success: function (data) {
            var setting = {
                check: {
                    enable: !data.isDirect,
                    chkboxType: {"Y": "ps", "N": "s"}
                },
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "fatherId"
                    },
                    key: {
                        name: "name",
                        checked: "checked"
                    }
                },
                callback: {
                    onClick: function (event, treeId, treeNode) {
                        viewResc(treeNode);
                    },
                    onCheck: showSaveBtn
                }
            };

            $.fn.zTree.init($("#rescTree"), setting, data.list);
        }
    });
}

// 显示保存按钮
function showSaveBtn(event, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("rescTree");
    var nodes = treeObj.getChangeCheckedNodes();
    if (nodes.length > 0) {
        $("#rescSaveBtn").css("display", "block");
    } else {
        $("#rescSaveBtn").css("display", "none");
    }
}

// 保存权限修改
function saveRescModify() {
    var roleTree = $.fn.zTree.getZTreeObj("roleTree");
    var roleNodes = roleTree.getSelectedNodes();
    if (roleNodes.length > 0) {
        var treeObj = $.fn.zTree.getZTreeObj("rescTree");
        var nodes = treeObj.getChangeCheckedNodes();

        if (nodes.length > 0) {
            var list = "[";
            for (var i = 0; i < nodes.length; i++) {
                if (i > 0) {
                    list += ",";
                }
                list += "{\"id\":" + nodes[i].id + ",\"checked\":" + nodes[i].checked + "}"
            }
            list += "]";

            $.ajax({
                type: "post",
                url: "/role/updateRoleResc.json",
                data: {
                    roleId: roleNodes[0].id,
                    list: list
                },
                success: function (data) {
                    if (data.msg && data.msg == "SUCCESS") {
                        $.alert("修改成功").time(2000);
                        treeObj.refresh();
                        showSaveBtn();
                    } else {
                        $.alert("修改失败").time(3000);
                    }
                }
            });
        } else {
            $.alert("无修改!").time(3000);
        }
    } else {
        $.alert("未指定角色").time(3000);
    }
}

function beforeRename(treeId, treeNode, newName) {
    var treeObj = $.fn.zTree.getZTreeObj("roleTree");
    if (newName.trim().length == 0) {
        $.alert("节点名称不能为空.").time(3000);
        setTimeout(function () {
            treeObj.editName(treeNode)
        }, 10);
        return false;
    } else {
        var oldName = treeNode.roleName;
        $.ajax({
            type: "post",
            url: "/role/updateRoleName.json",
            data: {
                roleName: newName.trim(),
                roleId: treeNode.id
            },
            success: function (data) {
                if (data.msg == "SUCCESS") {
                    return true;
                } else {
                    $.alert("修改角色名失败").time(3000);
                    treeNode.roleName = oldName;
                    treeObj.updateNode(treeNode);
                    return false;
                }
            }
        });
    }
}

// 显示资源信息
function viewResc(node) {
    $("#rescView").css("display", "table");
    $("#rescId").html(node.id);
    $("#rescName").html(node.name);
    $("#rescUrl").html(node.showUrl);
    $("#rescResString").html(node.resString);
    $("#rescPriority").html(node.priority);
}

// 右键事件
function onRightClick(event, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("roleTree");

    if(treeNode.id != 2) {
        if (treeNode && !treeNode.getParentNode()) {
//        treeObj.cancelSelectedNode();
            treeObj.selectNode(treeNode);
            showRMenu("root", event.clientX, event.clientY);
        } else if (treeNode && !treeNode.noR) {
            treeObj.selectNode(treeNode);
            showRMenu("node", event.clientX, event.clientY);
        }
    }
}

// 显示菜单
function showRMenu(type, x, y) {
    if (type == "root") {
        $("#m_mod").hide();
        $("#m_del").hide();
        $("#m_set").hide();
    } else {
        $("#m_mod").show();
        $("#m_del").show();
        $("#m_set").show();
    }
    //   alert($("#tb tr").length);
    if ($("#rMenu").height() > 0) {
        $("#rMenu table").show();
        rMenu.css({"top": y + "px", "left": x + "px", "display": "block"});
        $("body").bind("mousedown", onBodyMouseDown);
    }
}

// 隐藏菜单
function hideRMenu() {
    if (rMenu) rMenu.css({"display": "none"});
    $("body").unbind("mousedown", onBodyMouseDown);
}

//
function onBodyMouseDown(event) {
    if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
        rMenu.css({"display": "none"});
    }
}

// 新增节点
function addTreeNode() {
    var treeObj = $.fn.zTree.getZTreeObj("roleTree");
    var fatherId;
    hideRMenu();
    var nodes = treeObj.getSelectedNodes();
    if (nodes.length == 0) {
        $.alert("请选择一个角色").time(3000);
        return;
    }
    var treeNode = nodes[0];
    if (treeNode) {
        fatherId = treeNode.id;
        showEditWin(null, fatherId);
    }
}

// 修改角色名称
function updateTreeNode() {
    var treeObj = $.fn.zTree.getZTreeObj("roleTree");
    hideRMenu();
    var nodes = treeObj.getSelectedNodes();
    if (nodes.length == 0) {
        $.alert("请选择一个角色").time(3000);
        return;
    }
    showEditWin(nodes[0], null);
    //treeObj.editName(nodes[0]);
}

// 增加角色
function addRole(roleName, pNode) {
    $.ajax({
        type: "post",
        url: "/role/addRole.json",
        data: {
            roleName: roleName,
            pid: pNode.id
        },
        success: function (data) {
            if (data.role) {
                var treeObj = $.fn.zTree.getZTreeObj("roleTree");
                treeObj.addNodes(pNode, data.role);
            } else {
                $.alert("创建角色失败").time(3000);
            }
        }
    });
}

// 删除角色
function removeTreeNode() {
    var treeObj = $.fn.zTree.getZTreeObj("roleTree");
    hideRMenu();
    var nodes = treeObj.getSelectedNodes();
    if (nodes && nodes.length > 0) {
        if (nodes[0].children && nodes[0].children.length > 0) {
            var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
            if (confirm(msg) == true) {
                deleteNode(nodes[0]);
            }
        } else {
            var msg = "删除角色后不可恢复，确认删除角色'" + nodes[0].roleName + "'？";
            if (confirm(msg) == true) {
                deleteNode(nodes[0]);
            }
        }
    }
}

// 删除角色
function deleteNode(node) {
    $.ajax({
        type: "post",
        url: "/role/deleteRole.json",
        data: {
            roleId: node.id
        },
        success: function (data) {
            if (data && data.msg == "SUCCESS") {
                var treeObj = $.fn.zTree.getZTreeObj("roleTree");
                treeObj.removeNode(node);
            } else {
                $.alert("删除角色失败").time(3000);
            }
        }
    });
}

function showAllNodes() {
    var zTree = $.fn.zTree.getZTreeObj("rescTree"),
        nodes = zTree.getNodesByParam("isHidden", true);
    zTree.showNodes(nodes);
}

function showCheckedNodes() {
    var zTree = $.fn.zTree.getZTreeObj("rescTree"),
        nodes = zTree.getNodesByFilter(function (node) {
            return !node.checked;
        });
    zTree.hideNodes(nodes);
}

function showEditWin(node, pid) {
    $("#form span").remove();
    $("#form label.error").remove();
    $("#form div").removeClass("has-error has-success has-feedback");

    var title = "添加角色";
    if (node) {
        title = "编辑角色";
        $("#id").val(node.id);
        $("#roleName").val(node.roleName);
        $("#code").val(node.code);
        $("#form").attr("action", "/role/updateRole.json");
    } else {
        $("#id").val("");
        $("#pid").val(pid);
        $("#roleName").val("");
        $("#code").val("");
        $("#form").attr("action", "/role/addRole.json");
    }
    editWin = $.dialog({
        title: title,
        width: 600,
        lock: true,
        content: document.getElementById('editWin')
    });
}

function modifyRole() {
    $.ajax({
        type: "post",
        url: $("#form").attr("action"),
        data: $("#form").serialize(),
        success: function (data) {
            var treeObj = $.fn.zTree.getZTreeObj("roleTree");
            var node = treeObj.getSelectedNodes()[0];
            if (data.role) {
                treeObj.addNodes(node, data.role);
            } else if (data.msg == "SUCCESS") {
                node.id = data.currentRole.id;
                node.parentId = data.currentRole.parentId;
                node.roleName = data.currentRole.roleName;
                node.code = data.currentRole.code;
                treeObj.updateNode(node);
            } else if (data.msg == "INVALID_PARAM" || data.msg == "FAIL") {
                $.alert("更新失败！").time(3000);
            } else {
                $.alert("创建角色失败！").time(3000);
            }
            editWin.close();
            $("#from").reset;
        }
    });
}