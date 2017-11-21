// document.domain="yourwebsite.com";
(function (d) {
    d['okValue'] = '确定';
    d['cancelValue'] = '取消';
    d['title'] = '消息';
})($.dialog.defaults);

var bsTable;
$(function () {
    bsTable = $("#resultTable").bsTable({
        url: '/homePageNotice/list.json',
        ajaxType: "post",     // ajax 提交方式 post 或者 get
        pageNo: 1,
        pageSize: 20,
        pagingAlign: "right",
        param: $("#form").serialize(),
        countRoot: "datas.total",
        dataRoot: "datas.list"
    });

    $("#searchBtn").on('click', search);
});

var um = UM.getEditor('content', {
    imagePath: "",
    textarea: 'content',
    initialFrameWidth: 600,   // 初始化编辑器宽度,默认500
    initialFrameHeight: 400,  // 初始化编辑器高度,默认500
    toolbar: [
        'source | undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |',
        'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize',
        '| justifyleft justifycenter justifyright justifyjustify |',
        'link unlink | emotion ',
        '| horizontal preview', 'drafts'
    ]
});

function search() {
    var param = $("#form").serialize();
    bsTable.reload(param);
}

function getOperate(row) {
    var html = "";
    if (row.status == 1) {
        html += "<a href='javascript:void(0);' onclick='undeploy(" + row.id + ")' class='btn btn-danger btn-xs'>不发布</a>";
    } else {
        html += "<a href='javascript:void(0);' onclick='deploy(" + row.id + ")' class='btn btn-danger btn-xs'>发布</a>";
    }
    if (row.type == 1) {
        html += "&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='updateLink(" + row.id + ")' class='btn btn-info btn-xs'>编辑</a>";
    } else {
        html += "&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='updateContent(" + row.id + ")' class='btn btn-info btn-xs'>编辑</a>";
    }
    html += "&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='updateRn(" + row.id + ")' class='btn btn-success btn-xs'>展示给</a>";
    html += "&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='updateAsNew(" + row.id + ")' class='btn btn-warning btn-xs'>设为最新通知</a>";
    return html;
}

function deploy(id) {
    changeStatus(id, 1);
}

function undeploy(id) {
    changeStatus(id, 0);
}

function changeStatus(id, status) {
    $.ajax({
        url: "/homePageNotice/changeStatus.json",
        type: "post",
        data: {id: id, status: status},
        success: function (data) {
            if (data && data.msg == "SUCCESS") {
                $.alert("操作成功").time(2000);
                search();
            } else {
                $.alert("提交失败").time(3000);
            }
        }
    });
}

function renderType(data) {
    return data == 1 ? "链接" : "富文本";
}

function renderStatus(data) {
    return data == 1 ? "发布" : "未发布";
}

function renderContent(row) {
    return row.type == 1 ? row.url : row.content;
}

function updateLink(id) {
    var data = bsTable.getDatas();
    for (var i in data) {
        if (data[i].id == id) {
            $("#url").val(data[i].url);
            break;
        }
    }
    var editWin = $.dialog({
        title: "编辑",
        lock: true,
        content: document.getElementById('linkDiv'),
        ok: function () {
            var close = true;
            $.ajax({
                url: "/homePageNotice/edit.json",
                type: "post",
                async: false,
                data: {id: id, url: $("#url").val(), type: 1},
                success: function (data) {
                    if (data && data.msg == 'SUCCESS') {
                        $.alert("操作成功").time(2000);
                    } else {
                        editWin.title("编辑-<span style='color: red'>保存失败！" + data.msg + "</span>");
                        editWin.shake();
                        close = false;
                    }
                }
            });
            return close;
        },
        okValue: "保存"
    });
}

function updateContent(id) {
    var data = bsTable.getDatas();
    for (var i in data) {
        if (data[i].id == id) {
            um.setContent(data[i].content);
            break;
        }
    }
    var editWin = $.dialog({
        title: "编辑",
        lock: true,
        content: document.getElementById('contentDiv'),
        ok: function () {
            var close = true;
            $.ajax({
                url: "/homePageNotice/edit.json",
                type: "post",
                async: false,
                data: {id: id, content: um.getContent(), type: 2},
                success: function (data) {
                    if (data && data.msg == 'SUCCESS') {
                        $.alert("操作成功").time(2000);
                        search();
                    } else {
                        editWin.title("编辑-<span style='color: red'>保存失败！" + data.msg + "</span>");
                        editWin.shake();
                        close = false;
                    }
                }
            });
            return close;
        },
        okValue: "保存"
    });
}

function updateRn(id) {
    $.ajax({
        type: "get",
        url: "/homePageNotice/getRelationRole.json",
        data: {hnId: id},
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
                }
            };
            $.fn.zTree.init($("#roleTree"), setting, data.list);
        }
    });
    var editWin = $.dialog({
        title: "展示给",
        lock: true,
        content: document.getElementById('treeContent'),
        ok: function () {
            var close = true;
            var zTree = $.fn.zTree.getZTreeObj("roleTree");
            var nodes = zTree.getCheckedNodes(true);
            var param = "";
            for (var i = 0, l = nodes.length; i < l; i++) {
                param += "&roleId=" + nodes[i].id;
            }
            param = "noticeId=" + id + param;
            $.ajax({
                url: "/homePageNotice/updateRoleNotice.json",
                type: "post",
                async: false,
                data: param,
                success: function (data) {
                    if (data && data.msg == 'SUCCESS') {
                        $.alert("操作成功").time(2000);
                    } else {
                        editWin.title("编辑-<span style='color: red'>保存失败！" + data.msg + "</span>");
                        editWin.shake();
                        close = false;
                    }
                }
            });
            return close;
        },
        okValue: "保存"
    });
}

function updateAsNew(id) {
    $.confirm("需要将此通知设为最新通知吗？", function () {
        $.ajax({
            url: "/homePageNotice/updateAsNew.json",
            type: "post",
            data: {id: id},
            success: function (data) {
                if (data && data.msg == "SUCCESS") {
                    $.alert("操作成功").time(2000);
                    search();
                } else {
                    $.alert("提交失败").time(3000);
                }
            }
        });
    });
}