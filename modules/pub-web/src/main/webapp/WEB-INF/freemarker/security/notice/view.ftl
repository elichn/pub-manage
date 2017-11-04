<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>首页通知</title>
    <!-- Le styles -->
    <link href="/common/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/common/artDialog/skins/default.css" rel="stylesheet">
    <link rel="stylesheet" href="/common/jquery-plugin/validate/style.css" type="text/css">
    <link rel="stylesheet" href="/common/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="/common/umeditor/themes/default/css/umeditor.css" type="text/css">
    <link rel="stylesheet" href="/css/security/notice/view.css" type="text/css">
    <link rel="stylesheet"href="/css/common.css" type="text/css">
</head>
<body>
<div class="container">
    <form id="formQuery" method="post" class="well form-horizontal" style="padding: 0 5px">
        <fieldset>
            <legend>首页通知</legend>
            <div class="form-group">
                <div class="col-md-4">
                    <label class="col-md-4 control-label" for="status">发布状态:</label>
                    <div class="col-md-8">
                        <select id="status" name="status" class="form-control">
                            <option value="">请选择</option>
                            <option value="1">发布</option>
                            <option value="0">未发布</option>
                        </select>
                    </div>
                </div>

                <div class="col-md-4">
                    <label class="col-md-4 control-label" for="type">类型:</label>
                    <div class="col-md-8">
                        <select id="type" name="type" class="form-control">
                            <option value="">请选择</option>
                            <option value="1">链接</option>
                            <option value="2">富文本</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="col-md-12">
                    <div class="col-md-12 text-right">
                        <button type="button" onclick="showEditWin()" style="width: 80px;" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添 加</button>&nbsp;
                        <button type="button" id="searchBtn" style="width: 80px;" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;查 询</button>
                    </div>
                </div>
            </div>
        </fieldset>
    </form>

    <table id="resultTable" class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>编号</th>
            <th>类型</th>
            <th>内容</th>
            <th>状态</th>
            <th>更新时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <script type="text/html">
            <tr>
                <td>{{id}}</td>
                <td>{{type?renderType}}</td>
                <td>
                    {{_self_data?renderContent}}
                </td>
                <td>{{status?renderStatus}}</td>
                <td>{{updateTime?parseDate}}</td>
                <td>{{_self_data?getOperate}}</td>
            </tr>
        </script>
        </tbody>
    </table>

    <div id="contentDiv" style="display: none; width: 600px;height: 450px;">
        <script type="text/plain" id="content" style="width:100%;padding-right:15px;"></script>
    </div>

    <div id="linkDiv" style="display: none; width: 600px;height: 200px;">
        <label class="col-md-2 control-label" for="url">链接地址:</label>
        <div class="col-md-10">
            <input type="text" id="url" class="form-control" name="url"/>
        </div>
    </div>

    <div id="treeContent" class="ztree" style="display:none;">
        <ul id="roleTree" class="ztree" style="margin-top:0; width:300px; height: 350px; overflow-y: auto"></ul>
    </div>
</div>

<script type="text/javascript" src="/common/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/common/bootstrap-table.js"></script>
<script type="text/javascript" src="/common/dateformat.js"></script>
<script type="text/javascript" src="/common/artDialog/jquery.artDialog.min.js"></script>
<script type="text/javascript" src="/common/artDialog/artDialog.plugins.min.js"></script>
<script type="text/javascript" src="/common/umeditor/umeditor.config.js" charset="utf-8"></script>
<script type="text/javascript" src="/common/umeditor/umeditor.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/common/umeditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="/js/page/notice/view.js"></script>
<script type="text/javascript">
function showEditWin() {
    var style = ${style};
    if (style == 0) {
        parent.addTab(18, "添加首页通知", "/homePageNotice/add");
    } else if (style == 1) {
        window.location.href = "/homePageNotice/add";
    }
}
</script>
</body>
</html>