$(function () {
    $.validator.setDefaults({
        submitHandler: function (form) {
            if (form.id == 'form') {
                changePassword();
            }
        }
    });

    $.validator.addMethod("complexPwd", function (value, element) {
        return isComplexPassword(value) >= 3;

    }, "密码中必须包含数字，小写字母，大写字母，特殊字符中的三种以上");


    $("#form").validate({
        rules: {
            oldPassword: {
                required: true,
                rangelength: [6, 16]
            },
            newPassword: {
                required: true,
                rangelength: [6, 16],
                complexPwd: true
            },
            newPassword2: {
                required: true,
                rangelength: [6, 16],
                equalTo: '#newPassword'
            }
        },
        messages: {
            oldPassword: {
                required: "请输入原始密码",
                rangelength: "长度必须介于{0} 和 {1} 之间的字符串"
            },
            newPassword: {
                required: "请输入新密码",
                rangelength: "长度必须介于{0} 和 {1} 之间的字符串"
            },
            newPassword2: {
                required: "请再次输入新密码",
                rangelength: "长度必须介于{0} 和 {1} 之间的字符串",
                equalTo: '两次新密码不一致'
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').removeClass('success').addClass('error');
        },
        unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('error').addClass('success');
        }
    });
});

function changePassword() {
    var oldPassword = $.md5($("#oldPassword").val());
    var newPassword = $.md5($("#newPassword").val());
    $.ajax({
        type: "post",
        url: '/user/updatePassword.json',
        data: {
            oldPassword: oldPassword,
            newPassword: newPassword
        },
        success: function (data) {
            $("#form div.alert").remove();
            if (data.success == 'SUCCESS') {
                var div = $("<div></div>").addClass("alert alert-success fade in");
                $('<button class="close" aria-hidden="true" data-dismiss="alert" type="button">×</button>').appendTo(div);
                $('<p></p>').html(data.msg).appendTo(div);
                $("#form .form-group:first").before(div);
                $("#form")[0].reset();
            } else {
                var div = $("<div></div>").addClass("alert alert-warning fade in");
                $('<button class="close" aria-hidden="true" data-dismiss="alert" type="button">×</button>').appendTo(div);
                $('<p></p>').html(data.msg).appendTo(div);
                $("#form .form-group:first").before(div);
            }
        }
    });
}