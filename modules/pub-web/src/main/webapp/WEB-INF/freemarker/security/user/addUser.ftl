<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"]>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>新增用户</title>
    <!-- Le styles -->
    <link rel="stylesheet" type="text/css" href="/common/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/common/ztree/css/metroStyle/metroStyle.css"/>
    <link rel="stylesheet" type="text/css" href="/common/jquery-plugin/validate/style.css"/>
    <link rel="stylesheet" type="text/css" href="/common/artDialog/skins/default.css"/>
    <link rel="stylesheet" type="text/css" href="/css/security/user/user.css"/>
</head>
<body>
<div class="container">
    <form id="form" method="post" class="well form-horizontal">
        <fieldset>
            <legend>新增用户</legend>

            <div class="form-group">
                <label class="col-md-2 control-label" for="userName">用户名:</label>
                <div class="col-md-4">
                    <input type="text" id="userName" class="form-control" name="userName" required="required" placeholder="4-16个字符">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label" for="cnName">姓名:</label>
                <div class="col-md-4">
                    <input type="text" id="cnName" class="form-control" name="cnName">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label" for="password">密码:</label>
                <div class="col-md-4">
                    <input type="password" id="password" name="password" class="form-control" placeholder="请输入密码">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label" for="password2">重复密码:</label>
                <div class="col-md-4">
                    <input type="password" id="password2" name="password2" class="form-control" placeholder="请再次输入密码">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label" for="email">邮箱:</label>
                <div class="col-md-4">
                    <input type="text" id="email" name="email" class="form-control" placeholder="请输入邮箱">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label" for="roleDiv">角色:</label>
                <div class="col-md-4">
                    <div id="roleDiv" class="ztree">

                    </div>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label" for="descn">描述:</label>
                <div class="col-md-4">
                    <textarea id="descn" class="form-control" name="descn" placeholder="可以填写昵称"></textarea>
                </div>
            </div>

            <div class="form-group">
                <div class="col-md-offset-2 col-md-4">
                    <button class="btn btn-info" style="width: 80px;" type="submit"><span class="glyphicon glyphicon-ok"></span>&nbsp;保 存</button>&nbsp;
                    <button class="btn btn-warning" style="width: 80px;" onclick="window.close();"><span class="glyphicon glyphicon-remove"></span>&nbsp;取 消</button>
                </div>
            </div>
        </fieldset>
    </form>
</div>
<script type="text/javascript" src="/common/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="/common/jquery-plugin/validate/jquery.validate.1.11.1.min.js"></script>
<script type="text/javascript" src="/common/artDialog/jquery.artDialog.min.js"></script>
<script type="text/javascript" src="/common/artDialog/artDialog.plugins.min.js"></script>
<script type="text/javascript" src="/common/common.js"></script>
<script type="text/javascript" src="/js/page/user/addUser.js"></script>
</body>
</html>