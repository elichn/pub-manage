<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>用户管理</title>
    <!-- Le styles -->
    <link href="/common/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/common/artDialog/skins/default.css" rel="stylesheet">
    <link href="/css/common.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <form id="form" method="post" class="well form-horizontal">
        <fieldset>
            <legend>用户管理</legend>

            <div class="form-group">
                <div class="col-md-2">
                    <label class="col-md-5 control-label" for="userName">用户名</label>
                    <div class="col-md-7">
                        <input id="userName" name="userName" type="text" class="form-control"/>
                    </div>
                </div>

                <div class="col-md-2">
                    <label class="col-md-4 control-label" for="roleName">角色</label>
                    <div class="col-md-8">
                        <select id="roleName" name="roleName" class="form-control">
                            <option value="">全部</option>
                        <#if roleList??>
                            <#list roleList as role>
                                <option value="${role.roleName}">${role.roleName}</option>
                            </#list>
                        </#if>
                        </select>
                    </div>
                </div>

                <div class="col-md-2">
                    <label class="col-md-4 control-label" for="status">状态</label>
                    <div class="col-md-8">
                        <select id="status" name="status" class="form-control">
                            <option value="">全部</option>
                            <option value="1">启用</option>
                            <option value="0">冻结</option>
                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <label class="col-md-4 control-label" for="userType">用户类型</label>
                    <div class="col-md-8">
                        <select id="userType" name="userType" class="form-control">
                            <option value="">全部</option>
                            <option value="1">LDAP</option>
                            <option value="2">标准账号</option>
                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="col-md-12 text-right">
                        <a style="width: 90px;" href="javascript:void(0)" onclick="addUser();" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加用户</a>&nbsp;
                        <#--<a style="width: 80px;" href="javascript:void(0)" onclick="pullUser();" class="btn btn-warning">拉取用户</a>-->   <#--ldap拉取用户开启-->
                        <button type="button" id="searchBtn" style="width: 80px;" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;查 询</button>
                    </div>
                </div>
            </div>
        </fieldset>
    </form>

    <table id="resultTable" class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>用户名</th>
            <th>用户类型</th>
            <th>姓名</th>
            <th>邮箱</th>
            <th>角色</th>
            <th>状态</th>
            <th>描述</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <script type="text/html">
            <tr>
                <td>{{userId}}</td>
                <td>{{userName}}</td>
                <td>{{userType?renderUserType}}</td>
                <td>{{cnName!''}}</td>
                <td>{{email!''}}</td>
                <td>{{roleName!''}}</td>
                <td>{{status?renderStatus}}</td>
                <td>{{userDescn!''}}</td>
                <td>{{_self_data?getOperate}}</td>
            </tr>
        </script>
        </tbody>
    </table>
</div>

<div id="pullUserWin" style="display: none">
    <div class="form-group">
        <label class="col-md-4 control-label" for="userName">用户名</label>
        <div class="col-md-8">
            <input id="userNameEdit" name="userName" type="text" class="form-control"/>
        </div>
    </div>
</div>

<script type="text/javascript" src="/common/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/common/bootstrap-table.js"></script>
<script type="text/javascript" src="/common/artDialog/jquery.artDialog.min.js"></script>
<script type="text/javascript" src="/common/artDialog/artDialog.plugins.min.js"></script>
<script type="text/javascript" src="/js/page/user/userManager.js"></script>
<script type="text/javascript">
    function addUser() {
        var style = ${style};
        if (style == 0) {
            parent.addTab(230, "添加用户", "/user/addUserPage");
        } else if (style == 1) {
            window.location.href = "/user/addUserPage";
        }
    }

    function editUser(id) {
        var style = ${style};
        if (style == 0) {
            parent.addTab(19, "编辑用户[" + id + "]", "/user/editUser?id=" + id);
        } else if (style == 1) {
            window.location.href = "/user/editUser?id=" + id;
        }
    }
</script>
</body>
</html>