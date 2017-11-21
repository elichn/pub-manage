function initDate(days) {
    var longTime = new Date().getTime() - 1000 * 3600 * 24;
    var oneMonthBeforeLongTime = new Date().getTime() - days * 1000 * 3600 * 24;
    $("#startDate").val(new Date(oneMonthBeforeLongTime).format("yyyy-MM-dd"));
    $("#endDate").val(new Date(longTime).format("yyyy-MM-dd"));
}

// formatFen 格式化分成元显示 用千分符分割（从个位向高位,每三位加英文,） 保留2位小数
function formatFen(value) {
    if (value == null || value == "") {
        return value;
    }
    try {
        var num = eval(value) / 100;
        return formatNumber(num, 2);
    } catch (e) {

    }
    return value;
}

function formatNumber(num, precision, separator) {
    var parts;
    // 判断是否为数字
    if (!isNaN(parseFloat(num)) && isFinite(num)) {
        num = Number(num);
        // 处理小数点位数
        var numStr = (typeof precision !== 'undefined' ? num.toFixed(precision) : num).toString();
        // 分离数字的小数部分和整数部分
        parts = numStr.split('.');
        // 整数部分加[separator]分隔, 借用一个著名的正则表达式
        parts[0] = parts[0].toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1' + (separator || ','));
        return parts.join(".");
    }
    return num;
}

// isComplexPassword 密码匹配
function isComplexPassword(password) {
    var n = 0;
    if (/\d/.test(password)) n++;    // 包含数字
    if (/[a-z]/.test(password)) n++; // 包含小写字母
    if (/[A-Z]/.test(password)) n++; // 包含大写字母
    if (/\W/.test(password)) n++;    // 包含其他字符
    return n;
}
