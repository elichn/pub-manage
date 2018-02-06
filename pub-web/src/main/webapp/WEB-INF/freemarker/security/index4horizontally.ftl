<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>${misName!''}</title>
    <!-- Le styles -->
    <link rel="stylesheet" type="text/css" href="/common/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/security/submenu.css"/>
    <script type="text/javascript" src="/common/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="/common/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/page/index/index4horizontally.js"></script>
</head>
<body>
<#--<div class="navbar navbar-default navbar-fixed-top" role="navigation"> -->        <#--默认导航栏-->
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">                <#--黑色导航栏-->
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a class="navbar-brand hidden-sm" href="#">${misName!''}</a>
        </div>

        <div id="navBar" class="navbar-collapse collapse in" role="navigation" aria-expanded="true" style="">
            <ul class="nav navbar-nav">
            <#if menuList??>
                <#list menuList as first>
                    <#if first.subMenu?? && first.subMenu?size gt 0>   <#--一级菜单 有子菜单-->
                        <li class="dropdown">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="${first.url!"javascript:void(0);"}">${first.name}<b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <#list first.subMenu as second>
                                    <#if second.subMenu?? && second.subMenu?size gt 0>   <#--二级菜单 有子菜单-->
                                        <li class="dropdown-submenu">
                                            <a href="#">${second.name}</a>
                                            <ul class="dropdown-menu">
                                                <#list second.subMenu as third>
                                                    <#if third.subMenu?? && third.subMenu?size gt 0>   <#--三级菜单 有子菜单-->
                                                    <li class="dropdown-submenu">
                                                        <a href="#">${third.name}</a>
                                                        <ul class="dropdown-menu">
                                                            <#list third.subMenu as fourth> <#--四级菜单-->
                                                                <li><a href="${fourth.url!"javascript:void(0);"}" target="main">${fourth.name}</a></li>
                                                            </#list>
                                                        </ul>
                                                    <#else>    <#--三级菜单 无子菜单-->
                                                        <li><a href="${third.url!"javascript:void(0);"}" target="main">${third.name}</a></li>
                                                    </#if>

                                                </#list>
                                            </ul>
                                        </li>
                                    <#else>    <#--二级菜单 无子菜单-->
                                        <li><a href="${second.url!"javascript:void(0);"}" target="main">${second.name}</a></li>
                                    </#if>
                                </#list>
                            </ul>
                        </li>
                    <#else>  <#--一级菜单 无子菜单-->
                        <li>
                            <a href="${first.url!"javascript:void(0);"}" target="main">${first.name}</a>
                        </li>
                    </#if>
                </#list>
            </#if>
            </ul>

        <#if userName??>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a data-toggle="dropdown" class="btn dropdown-toggle" href="#"><span class="glyphicon glyphicon-user"></span>&nbsp;${userName} <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="/index" target="_self"><span class="glyphicon glyphicon-transfer"></span>&nbsp;切换风格</a></li>
                        <li class="divider"></li>
                        <li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span>&nbsp;注销</a></li>
                    </ul>
                </li>
            </ul>
        </#if>
        </div>
    </div>

    <iframe id="showIFrame" name="main" frameborder="0" width="100%" scrolling="auto" style="overflow: visible;" src="${welcome!''}"></iframe>

    </div>
</div>

</body>
</html>
