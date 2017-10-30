<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${misName!''}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache, must-revalidate">
    <meta http-equiv="Expires" content="0">
    <!-- Le styles -->
    <link href="/common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="/common/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="/common/jquery-easyui/themes/metro/easyui.css">
    <link rel="stylesheet" type="text/css" href="/common/jquery-easyui/themes/icon.css">
    <link href="/common/artDialog/skins/default.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/security/index.css">
</head>

<body class="easyui-layout" fit="true">
<div id="easyui-layout-north" class="easyui-panel" data-options="region:'north',split:false" style="height:53px;padding:2px;background:#000000;">
    <span style="font-size: 30px;color:#ffffff;font-weight: bolder;margin-left: 20px;">${misName!''}</span>&nbsp;
    <a href="#" class="easyui-splitbutton" style="color: #ffffff" data-options="menu:'#mm1'">${userName}</a>
</div>

<div id="mm1" style="width:150px;">
    <div data-options=""><span class="glyphicon glyphicon-transfer"></span>&nbsp;&nbsp;&nbsp;&nbsp;<a href="/index4horizontally">切换风格</a></div>
    <div class="menu-sep"></div>
    <div data-options=""><span class="glyphicon glyphicon-log-out"></span>&nbsp;&nbsp;&nbsp;&nbsp;<a href="/logout">注销</a></div>
</div>

<div data-options="region:'west',split:true" title="功能菜单" style="width:200px;">
    <input class="easyui-searchbox" data-options="prompt:'搜索菜单',searcher:searchMenu" style="width:195px"/>

    <div id="menu" class="ztree">

    </div>
</div>

<div id="easyui-layout-center" data-options="region:'center'" style="margin-top: -5px">
    <div class="easyui-tabs" id="centerTab" fit="true">
    </div>
</div>

<script type="text/javascript" src="/common/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/common/jquery.cookie.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="/common/ztree/js/jquery.ztree.exhide.min.js"></script>
<script type="text/javascript" src="/common/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/common/artDialog/jquery.artDialog.min.js"></script>
<script type="text/javascript" src="/common/artDialog/artDialog.plugins.min.js"></script>
<script type="text/javascript" src="/common/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/page/index/index.js"></script>
</body>
</html>