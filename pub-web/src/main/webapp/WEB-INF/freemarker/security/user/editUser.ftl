<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"]>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>编辑用户</title>
    <!-- Le styles -->
    <link rel="stylesheet" type="text/css" href="/common/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/common/ztree/css/metroStyle/metroStyle.css"/>
    <link rel="stylesheet" type="text/css" href="/common/jquery-plugin/validate/style.css"/>
    <link rel="stylesheet" type="text/css" href="/common/artDialog/skins/default.css"/>
    <link rel="stylesheet" type="text/css" href="/css/security/user/user.css"/>
</head>
<body>
<#if user??>
<div class="container">
    <form id="form" method="post" class="well form-horizontal">
        <fieldset>
            <legend>编辑用户</legend>

            <input type="hidden" id="id" name="id" value="${user.id}">

            <div class="form-group">
                <label class="col-md-2 control-label" for="userName">用户名:</label>
                <div class="col-md-4">
                    <input type="text" id="userName" class="form-control" name="userName" required="required"
                           placeholder="4-16个字符"
                           <#if user??>disabled="disabled" </#if>
                           value="${(user.userName)!''}">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label" for="cnName">姓名:</label>
                <div class="col-md-4">
                    <input type="text" id="cnName" class="form-control" name="cnName" value="${(user.cnName)!''}"
                           required="required">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label" for="email">邮箱:</label>
                <div class="col-md-4">
                    <input type="text" id="email" name="email" class="form-control" value="${(user.email)!''}"
                           placeholder="请输入邮箱">
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
                    <textarea id="descn" name="descn" class="form-control">${(user.descn)!''}</textarea>
                </div>
            </div>

            <div class="form-group">
                <div class="col-md-offset-2 col-md-4">
                    <button style="width: 80px" class="btn btn-info" type="submit"><span class="glyphicon glyphicon-ok"></span>&nbsp;保 存</button>
                </div>
            </div>
        </fieldset>
    </form>
</div>
<#else>
<div class="container">
    <hr>
    用户不存在
</div>
</#if>
<script type="text/javascript" src="/common/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="/common/jquery-plugin/validate/jquery.validate.1.11.1.min.js"></script>
<script type="text/javascript" src="/common/artDialog/jquery.artDialog.min.js"></script>
<script type="text/javascript" src="/common/artDialog/artDialog.plugins.min.js"></script>
<script type="text/javascript" src="/js/page/user/editUser.js"></script>
<script type="text/javascript">
    $(function () {
        $.ajax({
            type: "get",
            url: "/user/getRoleTree.json",
            data: {id:${user.id}},
            success: function (data) {
                var setting = {
                    check: {
                        enable: true,
                        chkboxType: {"Y": "", "N": ""}
                    },
                    view: {
                        selectedMulti: false
                    },
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId"
                        },
                        key: {
                            name: "roleName"
                        }
                    },
                    callback: {
                        onCheck: function (event, treeId, treeNode) {
                            var treeObj = $.fn.zTree.getZTreeObj("roleDiv");
                            var nodes = treeObj.getNodesByFilter(function () {
                                return true;
                            }, false, treeNode);

                            for (var k in nodes) {
                                if (treeNode.checked) {
                                    treeObj.checkNode(nodes[k], false);
                                }
                                treeObj.setChkDisabled(nodes[k], treeNode.checked);
                            }
                        }
                    }
                };
                var obj = $.fn.zTree.init($("#roleDiv"), setting, data.list);
                var nodes = obj.getCheckedNodes(true);
                disableNodes(obj, nodes);
            }
        });
    });
</script>
</body>
</html>