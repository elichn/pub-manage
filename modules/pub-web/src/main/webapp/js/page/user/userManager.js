// document.domain="yourwebsite.com";

$(function () {
    (function (d) {
        d['okValue'] = '确定';
        d['cancelValue'] = '取消';
        d['title'] = '消息';
        // [more..]
    })($.dialog.defaults);

    var param = $("#form").serialize();
    var s = $("#resultTable").bsTable({
        url: '/user/viewUsers.json',
        ajaxType: "POST",  //ajax 提交方式 post 或者 get
        pageNo: 1,
        pageNoAlias: 'page',
        pageSize: 10,
        pagingAlign: "right",
        param: param,
        countRoot: "total",
        dataRoot: "userRoleBvoList"
    });

    $("#searchBtn").on('click', function () {
        var param = $("#form").serialize();
        s.reload(param);
    });

    $("#userName").on('keydown', function (event) {
        if (event.keyCode == 13) {
            search();
        }
    });
});

function search() {
    $("#searchBtn").click();
}


function renderStatus(status) {
    if (status == 1) {
        return "启用";
    } else {
        return "冻结";
    }
}

function getOperate(row) {
    if (row) {
        var html = "<a href='javascript:void(0);' onclick='editUser(" + row.userId + ")' class='btn btn-info btn-xs'>编辑</a>";
        if (row.userType == 2) {
            html += "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='resetPassword(" + row.userId + ")' class='btn btn-warning btn-xs'>重置密码</a>";
            if (row.status == 1) {
                html += "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='setStatus(" + row.userId + ")' class='btn btn-danger btn-xs'>禁用</a>";
            } else {
                html += "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='setStatus(" + row.userId + ")' class='btn btn-success btn-xs'>启用</a>";
            }
        }

        return html;
    } else {
        return "";
    }
}

function resetPassword(userId) {
    $.confirm("是否重置密码？", function () {
        $.ajax({
            url: "/user/resetPassword.json",
            type: "post",
            data: {id: userId},
            success: function (data) {
                if (data.msg == "FAIL") {
                    $.alert("重置失败！").time(3000);
                } else if (data.msg == "LDAP") {
                    $.alert("LDAP用户不能重置密码！").time(3000);
                } else {
                    $.alert("新密码:" + data.msg).time(3000);
                }
            }
        });
    });
}

function setStatus(userId) {
    $.ajax({
        url: "/user/setStatus.json",
        type: "post",
        data: {id: userId},
        success: function (data) {
            if (data.msg == "USER_NOT_EXIST") {
                $.alert("用户不存在！").time(3000);
            } else if (data.msg == "FAIL") {
                $.alert("操作失败！").time(3000);
            } else {
                $.alert("操作成功！").time(2000);
                search();
            }
        }
    });
}

function renderUserType(userType) {
    switch (userType) {
        case 1:
            return "LDAP";
        case 2:
            return "标准用户";
        default :
            return "";
    }
}

function pullUser() {
    var userNameEdit = $("#userNameEdit");
    userNameEdit.html("");

    var editWin = $.dialog({
        title: "LDAP用户拉取",
        lock: true,
        content: document.getElementById('pullUserWin'),
        okValue: "拉取",
        ok: function () {
            var userName = userNameEdit.val();
            $.ajax({
                type: "GET",
                url: "/user/pullUser.json",
                data: {userName: userName},
                success: function (data) {
                    if (data && data.msg == "SUCCESS") {
                        $.alert("拉取成功").time(2000);
                        $("#userName").val(userName);
                        search();
                    } else if (data.msg == "EXIST") {
                        $.alert("用户已存在").time(3000);
                    } else {
                        $.alert("拉取失败").time(3000);
                    }
                }
            });
        }
    })
}