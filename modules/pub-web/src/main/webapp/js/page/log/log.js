$(function () {
    var param = $("#form").serialize();
    var s = $("#resultTable").bsTable({
        url: '/log/loglist.json',
        ajaxType: "POST",  // ajax 提交方式 post 或者 get
        pageNo: 1,
        pageSize: 10,
        pagingAlign: "right",
        param: param,
        countRoot: "datas.total",
        dataRoot: "datas.list"
    });

    $("#searchBtn").on('click', function () {
        var param = $("#form").serialize();
        s.reload(param);
    });
});

/**
 * 下载统计表
 */
function downLoadTableData() {
    var fileName = "操作日志.xlsx";
    var templateName = "se_log.xlsx";
    $.confirm('点击确定开始后台导出数据?', function () {
        var url = "/log/exportExcel.json?"
            + "&fileName=" + fileName
            + "&templateName=" + templateName;
        window.location.href = url;
    });
}