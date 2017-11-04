<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"]>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>角色管理</title>
    <!-- Le styles -->
    <link href="/common/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/common/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="/common/artDialog/skins/default.css" type="text/css">
    <link href="/common/artDialog/skins/default.css" rel="stylesheet">
    <link href="/css/security/role/roleManager.css" type="text/css" rel="stylesheet">
</head>
<body style="padding: 20px 0">
<div class="container">
    <div class="row">
        <div class="col-md-4">
            <div id="roleTree" class="ztree">
            </div>
        </div>

        <div class="col-md-4">
            <div id="rescTree" style="display:none;" class="ztree col-md-4">
            </div>
        </div>

        <div id="rescDiv" class="col-md-4" style="display: none;">
            <div class="row">
                <a href="javascript:void(0);" id="rescSaveBtn" style="display:none; margin-right: 5px; width: 100px;"
                   class="btn btn-info"
                   onclick="saveRescModify();"><span class="glyphicon glyphicon-ok"></span>&nbsp;保存修改
                </a>
                <a href="javascript:void(0);" class="btn btn-success" style="margin:10px 0;"
                   onclick="showCheckedNodes();"><span class="glyphicon glyphicon-resize-small"></span>&nbsp;仅显示选中资源</a>&nbsp;
                <a href="javascript:void(0);" class="btn btn-warning" onclick="showAllNodes();"><span class="glyphicon glyphicon-resize-full"></span>&nbsp;显示所有资源</a>
            </div>

            <div class="row" style="margin-top: 10px;">
                <table id="rescView" style="display: none" class="table table-bordered">
                    <tr>
                        <th style="min-width: 100px;">id:</th>
                        <td id="rescId" style="word-break:break-all;"></td>
                    </tr>

                    <tr>
                        <th>资源名称:</th>
                        <td id="rescName" style="word-break:break-all;"></td>
                    </tr>

                    <tr>
                        <th>地址:</th>
                        <td id="rescUrl" style="word-break:break-all;"></td>
                    </tr>

                    <tr>
                        <th>权限控制:</th>
                        <td id="rescResString" style="word-break:break-all;"></td>
                    </tr>

                    <tr>
                        <th>优先级:</th>
                        <td id="rescPriority"></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <div class="row role-tags-div" style="margin-top: 20px">

    </div>
</div>
<!--鼠标右键功能菜单 -->
<div id="rMenu" class="rMenu">
    <table id="tb">
    <@shiro.hasPermission name="/role:addRole">
        <tr>
            <td id="m_add" onclick="addTreeNode();">增加角色</td>
        </tr>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="/role:updateRole">
        <tr>
            <td id="m_mod" onclick="updateTreeNode();">编辑角色</td>
        </tr>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="/role:deleteRole">
        <tr>
            <td id="m_del" onclick="removeTreeNode();">删除角色</td>
        </tr>
    </@shiro.hasPermission>
    </table>
</div>

<div id="editWin" style="display: none; width: 600px">
    <form id="form" method="post" class="form-horizontal">
        <input type="hidden" name="id" id="id"/>
        <input type="hidden" name="pid" id="pid"/>

        <div class="form-group">
            <label class="col-sm-2 control-label" for="roleName">角色名称<span class="required">*</span></label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="roleName" name="roleName">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label" for="code">唯一标识</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="code" name="code"
                       placeholder="唯一标识该角色的值">
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-4">
                <button style="width: 80px;" type="submit" class="btn btn-primary">提 交</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="/common/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.exedit.min.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.exhide.min.js"></script>
<script type="text/javascript" src="/common/artDialog/jquery.artDialog.min.js"></script>
<script type="text/javascript" src="/common/artDialog/artDialog.plugins.min.js"></script>
<script type="text/javascript" src="/common/jquery-plugin/validate/jquery.validate.1.11.1.min.js"></script>
<script type="text/javascript" src="/js/page/role/roleManager.js"></script>
</body>
</html>