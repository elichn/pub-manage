// 格式化输出日期
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(),      // 日
        "h+": this.getHours(),     // 小时
        "m+": this.getMinutes(),   // 分
        "s+": this.getSeconds(),   // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()//毫秒
    };

    fmt = fmt || 'yyyy-MM-dd hh:mm:ss';
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

// 返回某一天是一周的周几（1-7）
Date.prototype.getDayOfWeek = function () {
    // 1970年一月一日为周四
    var o = new Date(1970, 0, 1);
    var days = parseInt((this.getTime() - o.getTime()) / 24 / 3600 / 1000) % 7 + 4;
    if (days > 7) {
        days = days % 7;
    }
    return days;
};

// 当前日期 前移或后移days天
Date.prototype.plusDays = function (days) {
    return new Date(this.getTime() + days * 24 * 3600 * 1000);
};

// 当前日期 前移或后移days天
Date.prototype.minusDays = function (days) {
    return new Date(this.getTime() - days * 24 * 3600 * 1000);
};

function parseDate(date, pattern) {
    if (date == null || date == '') {
        return "";
    }
    return new Date(date).format(pattern);
}

function parseDateYMD(date) {
    return parseDate(date, "yyyy-MM-dd")
}

function parseDateYM(date) {
    return parseDate(date, "yyyy-MM")
}