<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>修改密码</title>
    <!-- Le styles -->
    <link rel="stylesheet" type="text/css" href="/common/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/common/jquery-plugin/validate/style.css"/>
    <link rel="stylesheet" type="text/css" href="/css/common.css"/>
</head>
<body>
<div class="container">
    <form id="form" method="post" class="well form-horizontal">
        <fieldset>
            <legend>修改密码</legend>

            <div class="form-group">
                <label class="col-md-2 control-label" for="oldPassword">原始密码:</label>
                <div class="col-md-4">
                    <input type="password" id="oldPassword" class="form-control" name="oldPassword"
                           required="required">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label" for="newPassword">新密码:</label>
                <div class="col-md-4">
                    <input type="password" id="newPassword" class="form-control" name="newPassword"
                           required="required">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label" for="newPassword2">再次输入新密码:</label>
                <div class="col-md-4">
                    <input type="password" id="newPassword2" class="form-control" name="newPassword2"
                           required="required">
                </div>
            </div>

            <div class="form-group">
                <div class="col-md-offset-2 col-md-4">
                    <button class="btn btn-info" type="submit" style="width: 80px;"><span class="glyphicon glyphicon-ok"></span>&nbsp;保 存</button>&nbsp;
                    <button class="btn btn-warning" type="reset" style="width: 80px;"><span class="glyphicon glyphicon-remove"></span>&nbsp;重 置</button>
                </div>
            </div>
        </fieldset>
    </form>
</div>
<script type="text/javascript" src="/common/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/common/jquery-plugin/validate/jquery.validate.1.11.1.min.js"></script>
<script type="text/javascript" src="/common/jquery.md5.js"></script>
<script type="text/javascript" src="/common/common.js"></script>
<script type="text/javascript" src="/js/page/user/password.js"></script>
</body>
</html>