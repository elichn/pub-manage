<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>添加首页通知</title>
    <!-- Le styles -->
    <link href="/common/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/common/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <link href="/common/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet">
    <link href="/common/artDialog/skins/default.css" type="text/css" rel="stylesheet">
    <link href="/css/security/notice/add.css" type="text/css" rel="stylesheet">
</head>
<body>
<div class="container">
    <form id="form" method="post" class="well form-horizontal">
        <fieldset>
            <legend>添加首页通知</legend>

            <div class="form-group">
                <div class="col-md-6">
                    <label class="col-md-4 control-label" for="type">类型:</label>
                    <div class="col-md-8">
                        <select id="type" name="type" class="form-control">
                            <option value="1">链接</option>
                            <option value="2" selected>富文本</option>
                        </select>
                    </div>
                </div>

                <div class="col-md-6">
                    <label class="col-md-4 control-label" for="status">发布:</label>
                    <div class="col-md-8">
                        <select id="status" name="status" class="form-control">
                            <option value="1">是</option>
                            <option value="2">否</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="col-md-12">
                    <label class="col-md-2 control-label" for="roleDiv">展示给:</label>
                    <div class="col-md-10">
                        <input type="text" id="roleDiv" class="form-control" readonly onclick="showRoles();"/>
                    </div>
                </div>
            </div>

            <div class="form-group link" style="display: none">
                <div class="col-md-12">
                    <label class="col-md-2 control-label" for="url">链接地址:</label>
                    <div class="col-md-10">
                        <input type="text" id="url" class="form-control" name="url"/>
                    </div>
                </div>
            </div>

            <div class="form-group content">
                <div class="col-md-12">
                    <label class="col-md-2 control-label" for="content">通知内容:</label>
                    <div class="col-md-10">
                        <script type="text/plain" id="content" style="width:100%;padding-right:15px;"></script>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="col-md-12">
                    <div class="col-md-12 text-center">
                        <button type="button" style="width: 80px;" class="btn btn-info" onclick="addNotice()"><span class="glyphicon glyphicon-ok"></span>&nbsp;保 存</button>
                        <button type="button" style="width: 80px;margin-left: 50px" class="btn btn-success" onclick="resetForm();"><span class="glyphicon glyphicon-remove"></span>&nbsp;重 置
                        </button>
                    </div>
                </div>
            </div>
        </fieldset>
    </form>
</div>

<div id="treeContent" class="ztree" style="display:none; position: absolute;z-index: 1000">
    <ul id="roleTree" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
</div>

<script type="text/javascript" src="/common/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="/common/umeditor/umeditor.config.js" charset="utf-8"></script>
<script type="text/javascript" src="/common/umeditor/umeditor.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/common/umeditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="/common/artDialog/jquery.artDialog.min.js"></script>
<script type="text/javascript" src="/common/artDialog/artDialog.plugins.min.js"></script>
<script type="text/javascript" src="/js/page/notice/add.js"></script>
</body>
</html>