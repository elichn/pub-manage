<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${misName!''}-登录</title>
    <!-- Le styles -->
    <link href="/common/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/common/jquery-plugin/validate/style.css" type="text/css">
    <link href="/css/security/login.css" rel="stylesheet">
</head>
<body>
<div class="login-div">
    <div class="title" align="center">
    ${misName!''}
    </div>

    <div style="margin-top: 10px;">
        <form id="form" method="post" action="/login" class="form-horizontal">
            <input type="hidden" name="redirectUrl" value='${redirectUrl!''}'/>

            <div class="form-group">
                <label class="col-lg-offset-2 col-sm-offset-2 col-xs-2 col-sm-2 control-label" for="userName">用户名</label>
                <div class="col-xs-4 col-sm-4">
                    <input type="text" class="form-control" id="userName" name="userName" placeholder="请输入用户名" onfocus="javascript:cleanErrorTips();">
                    <span style="font-size:13px;font-weight:bold;color:#CC0000"></span>
                </div>
            </div>

            <div class="form-group">
                <label class="col-lg-offset-2 col-sm-offset-2 col-xs-2 col-sm-2 control-label" for="password">密码</label>
                <div class="col-xs-4 col-sm-4">
                    <input type="password" class="form-control has-error" id="password" name="password" placeholder="请输入密码">
                </div>
            </div>

        <#if needCheckCode?? && needCheckCode>
            <div class="form-group">
                <label class="col-lg-offset-2 col-sm-offset-2 col-xs-2 col-sm-2 control-label" for="checkCode">验证码</label>
                <div class="col-xs-3 col-sm-3">
                    <input type="text" class="form-control" id="checkCode" name="checkCode" maxlength="4">
                </div>

                <div class="col-xs-5 col-sm-5">
                    <img src="/checkCode" id="changeCheckCode" onclick="reCaptcha()" height="32" size="10"/>
                    <a href="#" onclick="reCaptcha()">看不清楚,请点我</a>
                </div>
            </div>
        </#if>

        <#if errorKey??>
            <div class="form-group">
                <div class="col-lg-offset-4 col-sm-offset-4 col-xs-4 col-sm-4">
                    <span style="font-size:13px;font-weight:bold;color:#CC0000" id="errorKey">${errorKey!""}</span>
                </div>
            </div>
        </#if>

            <div class="form-group">
                <div class="col-lg-offset-4 col-sm-offset-4 col-xs-4 col-sm-4">
                    <button style="width: 100%;" type="submit" class="btn btn-info">登 录</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="/common/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/common/jquery-plugin/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="/js/page/login/login.js"></script>
</body>
</html>