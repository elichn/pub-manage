(function (d) {
    d['okValue'] = '确定';
    d['cancelValue'] = '取消';
    d['title'] = '消息';
    // [more..]
})($.dialog.defaults);

$.validator.setDefaults({
    submitHandler: function () {
        saveModify();
    }
});

jQuery.validator.addMethod("regex", //addMethod第1个参数:方法名称
    function (value, element, params) {     //addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数）
        var exp = new RegExp(params);     //实例化正则对象，参数为传入的正则表达式
        return exp.test(value);                  //测试是否匹配
    }, "格式错误");    //addMethod第3个参数:默认错误信息

$(function () {
    $("#form").validate({
        rules: {
            userName: {
                required: true,
                rangelength: [4, 20],
//                    regex: "^[_a-zA-Z0-9]*$",
                remote: {
                    url: "/user/isUser.json",
                    type: "get",
                    data: {
                        userName: function () {
                            return encodeURI($("#userName").val().trim());
                        }
                    }
                }
            },
            email: {
                required: true,
                email: true
            }
        },
        messages: {
            userName: {
                required: "请输入用户名",
                rangelength: "长度必须介于{0} 和 {1} 之间的字符串",
//                    regex: "用户名只能是数字、字母与下划线",
                remote: "用户名已存在"
            },
            email: {
                required: "请输入邮箱",
                email: "邮箱格式不正确"
            }
        },
        highlight: function (element) {
            $(element).closest("div").removeClass("has-success has-feedback");
            $(element).closest("div").addClass("has-error has-feedback");
            $(element).next("span").remove();
            $(element).closest("div").append('<span class="glyphicon glyphicon-remove form-control-feedback"></span>');
        },
        unhighlight: function (element) {
            $(element).closest("div").removeClass("has-error has-feedback");
            $(element).closest("div").addClass("has-success has-feedback");
            $(element).next("span").remove();
            $(element).closest("div").append('<span class="glyphicon glyphicon-ok form-control-feedback"></span>');
        }
    });
});


// 禁用子节点
function disableNodes(treeObj, nodes) {
    for (var k in nodes) {
        var treeNode = nodes[k];
        var subNodes = treeObj.getNodesByFilter(function () {
            return true;
        }, false, treeNode);

        for (var m in subNodes) {
            treeObj.setChkDisabled(subNodes[m], true);
        }
    }
}

// 保存修改
function saveModify() {
    var param = $("#form").serialize();

    var treeObj = $.fn.zTree.getZTreeObj("roleDiv");
//    var nodes = treeObj.getChangeCheckedNodes();
    var nodes = treeObj.getNodesByFilter(function (node) {
        return node.checked != node.checkedOld;
    }, false);

    if (nodes.length > 0) {
        var list = "[";
        for (var i = 0; i < nodes.length; i++) {
            if (i > 0) {
                list += ",";
            }
            list += "{\"id\":" + nodes[i].id + ",\"checked\":" + nodes[i].checked + "}"
        }
        list += "]";
        param += "&roleList=" + list;
    }

    var url = "/user/updateUser.json";
    $.post(url, param, function (data) {
        if (data) {
            $.alert("操作成功").time(2000);
            if (window.opener && window.opener.search) {
                window.opener.search();
            }
            window.close();
        } else {
            $.alert("操作失败").time(3000);
        }
    });
}