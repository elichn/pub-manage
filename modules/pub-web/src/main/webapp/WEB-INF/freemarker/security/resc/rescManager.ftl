<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"]>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>资源管理</title>
    <!-- Le styles -->
    <link rel="stylesheet" type="text/css" href="/common/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/common/ztree/css/metroStyle/metroStyle.css"/>
    <link rel="stylesheet" type="text/css" href="/common/artDialog/skins/default.css"/>
    <link rel="stylesheet" type="text/css" href="/common/jquery-plugin/validate/style.css"/>
    <link rel="stylesheet" type="text/css" href="/css/security/resc/rescManager.css"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div id="rescTree" style="float: left;" class="ztree col-md-4">
        </div>

        <div style="float: left;" class="col-md-7">
            <table class="table table-bordered">
                <caption><h3>资源详情</h3></caption>
                <tbody>
                <tr>
                    <td style="width: 100px">资源ID:</td>
                    <td id="rescId"></td>
                </tr>
                <tr>
                    <td>资源名称:</td>
                    <td id="rescName"></td>
                </tr>
                <tr>
                    <td>地址:</td>
                    <td id="rescUrl"></td>
                </tr>
                <tr>
                    <td>权限控制:</td>
                    <td id="rescResString" style="word-break: break-all"></td>
                </tr>
                <tr>
                    <td>优先级:</td>
                    <td id="rescPriority"></td>
                </tr>
                <tr>
                    <td>是否显示:</td>
                    <td id="isShowMenu"></td>
                </tr>
                <tr>
                    <td width="100px;">资源描述:</td>
                    <td id="rescDescn" width="500px;"></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!--鼠标右键功能菜单 -->
<#--<#if (flag??&& flag == true)>-->
<div id="rMenu">
    <table style="border: 0">
    <@shiro.hasPermission name="/resc:addResc">
        <tr>
            <td id="m_add" onclick="addTreeNode();">增加资源</td>
        </tr>
    </@shiro.hasPermission>

    <@shiro.hasPermission name="/resc:updateResc">
        <tr>
            <td id="m_edit" onclick="editTreeNode();">编辑资源</td>
        </tr>
    </@shiro.hasPermission>

    <@shiro.hasPermission name="/resc:deleteResc">
        <tr>
            <td id="m_del" onclick="removeTreeNode();">删除资源</td>
        </tr>
    </@shiro.hasPermission>
    </table>
</div>

<div id="editWin" style="display: none; width: 600px">
    <form id="form" method="post" class="form-horizontal">
        <input type="hidden" name="id" id="id"/>
        <input type="hidden" name="fatherId" id="pid"/>

        <div class="form-group">
            <label class="col-sm-2 control-label" for="name">资源名称<span class="required">*</span></label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="name" name="name">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label" for="showUrl">资源地址<span class="required">*</span></label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="showUrl" name="showUrl" placeholder="例如：/role/roleManage">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label" for="resString">权限控制<span class="required">*</span></label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="resString" name="resString" placeholder="例如：/role:add,update">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label" for="value">优先级<span class="required">*</span></label>
            <div class="col-sm-10">
                <input type="number" class="form-control" id="priority" name="priority" placeholder="数字，用于确定在左侧菜单的显示顺序">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label" for="showMenu">是否显示</label>
            <div class="col-sm-10">
                <div class="checkbox">
                    <input type="checkbox" id="showMenu" style="margin-left: 0"/>
                    <input type="hidden" id="showMenuVal" name="showMenu"/>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label" for="descn">备注</label>
            <div class="col-sm-10">
                <textarea class="form-control" id="descn" name="descn"></textarea>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-4">
                <button style="width: 80px;" type="submit" class="btn btn-info"><i class="glyphicon glyphicon-ok"></i>&nbsp;提 交</button>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript" src="/common/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.exedit.min.js"></script>
<script type="text/javascript" src="/common/artDialog/jquery.artDialog.min.js"></script>
<script type="text/javascript" src="/common/artDialog/artDialog.plugins.min.js"></script>
<script type="text/javascript" src="/common/jquery-plugin/validate/jquery.validate.1.11.1.min.js"></script>
<script type="text/javascript" src="/js/page/resc/rescManager.js"></script>
</body>
</html>