<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>日志管理</title>
    <!-- Le styles -->
    <link href="/common/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/common.css" rel="stylesheet">
    <style type="text/css">
        body {
            padding: 10px 0;
        }
    </style>
</head>
<body>
<div class="container">
    <form id="form" method="post" class="well form-horizontal">
        <fieldset>
            <legend>日志管理</legend>
            <div class="form-group">
                <div class="col-md-4">
                    <label class="col-md-4 control-label" for="userName">登录名</label>
                    <div class="col-md-8">
                        <input id="userName" name="userName" type="text" class="form-control"/>
                    </div>
                </div>

                <div class="col-md-4">
                    <label class="col-md-4 control-label" for="logTime">日期</label>
                    <div class="col-md-8">
                        <input id="logTime" name="logTime" class="form-control Wdate"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="text-align: center"/>
                    </div>
                </div>

                <div class="col-md-4">
                    <label class="col-md-4 control-label" for="logType">操作行为</label>
                    <div class="col-md-8">
                        <input id="logType" name="logType" type="text" class="form-control"/>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="col-md-12 text-right">
                    <button type="button" id="searchBtn" style="width: 80px;" class="btn btn-primary">查 询</button>
                </div>
            </div>
        </fieldset>
    </form>

    <table id="resultTable" class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>用户登录名</th>
            <th>IP</th>
            <th>操作时间</th>
            <th>操作行为</th>
            <th>操作内容</th>
        </tr>
        </thead>
        <tbody>
        <script type="text/html">
            <tr>
                <td>{{userName!''}}</td>
                <td>{{ip}}</td>
                <td>{{logTime?parseDate}}</td>
                <td>{{logType}}</td>
                <td style="word-break: break-all">{{logContent!''}}</td>
            </tr>
        </script>
        </tbody>
    </table>
</div>
<script type="text/javascript" src="/common/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/common/bootstrap-table.js"></script>
<script type="text/javascript" src="/common/dateformat.js"></script>
<script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
    $(function () {
        var param = $("#form").serialize();
        var s = $("#resultTable").bsTable({
            url: '/log/loglist.json',
            ajaxType: "POST",  //ajax 提交方式 post 或者 get
            pageNo: 1,
            pageSize: 10,
            pagingAlign: "right",
            param: param,
            countRoot: "total",
            dataRoot: "list"
        });

        $("#searchBtn").on('click', function () {
            var param = $("#form").serialize();
            s.reload(param);
        });

//        window.onresize = function () {
//            window.top.autoIFrame();
//        }
    });
</script>
</body>
</html>