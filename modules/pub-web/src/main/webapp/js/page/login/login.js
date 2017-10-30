// document.domain="yourwebsite.com";

if (window.top.location.href != window.location.href) {
    window.top.location.replace(window.location.href);
}

function reCaptcha() {
    var rdm = Math.random();
    $("#changeCheckCode").attr("src", "/checkCode.json?rdm=" + rdm);
}

function cleanErrorTips() {
    $('#errorKey').html("");
}

$(function () {

    $("#form").validate({
        rules: {
            userName: {
                required: true
            },
            password: {
                required: true
            },
            checkCode: {
                rangelength: [4, 4],
                required: true,
                remote: {
                    url: "/checkCodeVerify.json",
                    type: "get",
                    data: {
                        checkCode: function () {
                            return encodeURI($("#checkCode").val().trim());
                        }
                    }
                }
            }
        },
        messages: {
            userName: {
                required: "请输入用户名"
            },
            password: {
                required: "请输入密码"
            },
            checkCode: {
                rangelength: "验证码错误",
                required: "请输入验证码",
                remote: "验证码错误"
            }
        },
        errorPlacement: function (error, element) { // 指定错误信息位置
                   // error.insertAfter(element);
                   // $(element).closest("div").addClass("has-error");
        },
        highlight: function (element) {
            $(element).closest("div").removeClass("has-success has-feedback");
            $(element).closest("div").addClass("has-error has-feedback");
            $(element).next("span").remove();
            $(element).closest("div").append('<span class="glyphicon glyphicon-remove form-control-feedback"></span>');
        },
        unhighlight: function (element, errorClass) { // element通过验证时触发
            $(element).closest("div").removeClass("has-error has-feedback");
            $(element).closest("div").addClass("has-success has-feedback");
            $(element).next("span").remove();
            $(element).closest("div").append('<span class="glyphicon glyphicon-ok form-control-feedback"></span>');
        },
        submitHandler: function (form) {
            form.submit();
        }
    });
});