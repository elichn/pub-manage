/**
 *  一个table模版  ajax异步获取并展示数据
 */
;
(function ($) {
    $.fn.bsTable = function (settings) {
        var _ = this;
        var defaults = {
            url: null,                 // ajax请求数据的地址
            ajaxType: "post",          // ajax 提交方式 post 或者 get
            param: null,               // ajax 提交时的附加参数
            pageNo: 1,                 // 当前页码
            pageNoAlias: "pageNo",     // ajax 提交时page参数的别名
            pageSize: 10,              // 显示时每页的大小
            pageSizeAlias: "pageSize", // ajax 提交时pageSize参数的别名
            dataRoot: "list",          // json数据返回时root节点的名字
            isPaging: true,            // 是否显示分页
            pagingAlign: "right",      // 分页对齐方式 “right”/"left"
            countRoot: "count",        // json数据返回时数据条数节点的名字
            count: 0,                  // 数据总条数
            datas: null,               // 当前的列表数据
            template: null,            // html模版
            onStartLoad: null,         // ajax开始加载的回调函数, beforeSend 时调用
            onLoadSuccess: null,       // ajax请求成功时调用（success）,参数为ajax请求返回的参数
            onDataShowComplete: null,  // 数据列表生成成功时调用
            onLoadFail: null,          // ajax请求失败（error）时调用
            onInit: null,              // 组件初始化是调用（init）
            // onSort: null,           // 排序
            bindForm: null             // 排序绑定的form Id
        };

        // 初始化table
        _.init = function (el, s) {
            _.o = $.extend({}, defaults, s);
            var o = _.o;
            o.template = o.template || $("tbody script[type='text/html']", el);
            if (typeof o.onInit == "function") {
                o.onInit();
            }

            registSort(o);
            generateTable();
        };

        function registSort(o) {
            // if (typeof o.onSort == 'function') {
            var thead = $("thead", $(_));
            $("tr > th", thead).each(function (k, v) {
                var thiz = $(this);
                var col = thiz.attr("data-key");
                if (col != null) {
                    thiz.on("click", function () {
                        var order = thiz.attr("data-order");
                        if (order == 'desc') {
                            order = 'asc';
                        } else {
                            order = 'desc';
                        }

                        // o.onSort(col, order);
                        _.reload("orderBy=" + col + "&sortType=" + order + "&" + $("#" + o.bindForm).serialize());
                        $("tr > th > i").remove();
                        if (order == 'desc') {
                            thiz.append("<i class='icon-angle-down'></i>");
                        } else {
                            thiz.append("<i class='icon-angle-up'></i>");
                        }
                        thiz.attr("data-order", order);
                    });
                }
            });
            // }
        }

        // 根据参数重新加载table
        _.reload = function (param) {
            if (param != null) {
                _.o.param = param;
                _.gotoPage(1);
            }
        };

        // 获取数据 生成table
        function generateTable(pageNo, pageSize) {
            var o = _.o;
            if (o.url != null && typeof o.url == 'string' && o.url.trim() != '') {
                pageNo = pageNo || o.pageNo || 1;
                pageSize = pageSize || o.pageSize || 10;
                o.pageNo = pageNo;

                var param = o.param || {};
                if (typeof param == 'string') {
                    param += "&" + o.pageNoAlias + "=" + pageNo
                    + "&" + o.pageSizeAlias + "=" + pageSize;
                } else {
                    param[o.pageNoAlias] = pageNo;
                    param[o.pageSizeAlias] = pageSize;
                }

                $.ajax({
                    url: o.url,
                    type: o.ajaxType.toUpperCase(),
                    async: true,
                    data: param || {},
                    beforeSend: function (e) {
                        if (typeof  o.onStartLoad == "function") {
                            o.onStartLoad(e);
                        }
                        var el = $(_);
                        $("tbody", el).html("<tr><td colspan='99'>正在查询……</td></tr>");
                    },
                    success: function (data) {
                        if (typeof o.onLoadSuccess == 'function') {
                            o.onLoadSuccess(data);
                        }

                        _.load(data);
                    },
                    error: function (e) {
                        if (typeof  o.onLoadFail == "function") {
                            o.onLoadFail(e);
                        }
                    }
                });
            }
        }

        // 生成li元素
        function li(parentEl, cls, clickFunc, content, paging) {
            var li = $("<li></li>");
            if (cls != null) {
                li.addClass(cls);
            }

            if (typeof clickFunc == 'function') {
                li.on("click", clickFunc);
            }

            if (paging != null) {
                li.attr("data-page", paging);
            }

            $("<a href='javascript:void(0);'>" + content + "</a>").appendTo(li);
            li.appendTo(parentEl);
        }

        // 生成分页组件
        function generatePagination() {
            var o = _.o;
            if (!o.isPaging) return;

            var el = $(_);
            var foot = $("tfoot", el);
            if (foot.length == 0) {
                foot = $("<tfoot></tfoot>");
                foot.appendTo(el);
            }

            var max = 99;
            var row = $("<tr><td colspan='" + max + "'></td></tr>").appendTo(foot);
            var td = $("td", row);
            $(".pagination", foot).parents("tr:first").remove();

            var ul = $("<ul></ul>").addClass("pagination");
            o.pagingAlign == "right" ? ul.addClass("pull-right") : true;
            ul.appendTo(td);

            var pageCount = Math.ceil(o.count / o.pageSize);
            if (o.pageNo <= 1) {
                li(ul, "disabled", null, "&laquo;&nbsp;上一页");
            } else {
                li(ul, null, function () {
                    _.previousPage();
                }, "&laquo;&nbsp;上一页");
            }

            var start = 1;
            if (o.pageNo >= 4) {
                start = o.pageNo - 1;
                li(ul, null, function () {
                    _.gotoPage(1);
                }, "1", 1);

                li(ul, "disabled", null, "...");
            }

            var end = o.pageNo + 1;
            end > pageCount ? end = pageCount : true;
            for (var i = start; i <= end; i++) {
                if (o.pageNo == i) {
                    li(ul, "active", null, i, null);
                } else {
                    li(ul, null, function () {
                        _.gotoPage(parseInt($(this).attr("data-page")));
                    }, i, i);
                }
            }

            if (end < pageCount - 1) {
                li(ul, "disabled", null, "...", null);
            }

            if (end < pageCount) {
                li(ul, null, function () {
                    _.gotoPage(parseInt($(this).attr("data-page")));
                }, pageCount, pageCount);
            }

            if (o.pageNo >= pageCount) {
                li(ul, "disabled", null, "下一页&nbsp;&raquo;");
            } else {
                li(ul, null, function () {
                    _.nextPage();
                }, "下一页&nbsp;&raquo;");
            }

            var div = $("<div></div>").css("display", "inline-block");
            $("<label style='margin-left: 5px;'>跳转到：</label>").appendTo(div);
            $("<input type='text' class='form-control' style='width: 60px;display: inline; margin-right: 5px' value='" + o.pageNo + "'>").appendTo(div);
            $("<input type='button' style='margin-top: -7px' value='GO'>").addClass("btn btn-primary").on("click", function () {
                _.gotoPage(parseInt($("input:first", div).val()));
            }).css("width", "60px").appendTo(div);
            $('<label>共' + o.count + '条记录</label>').appendTo(div);
            div.appendTo(ul);
        }

        function getData(data, s) {
            var arr = s.trim().split(".");
            for (var k in arr) {
                if (data != null) {
                    data = data[arr[k].trim()];
                } else {
                    return null;
                }
            }
            return data;
        }

        function handle(data, s) {
            var index = s.indexOf("!");
            if (index > 0) {
                var d = getData(data, s.substring(0, index));
                if (d == null) {
                    return eval(s.substring(index + 1));//转换为值
                } else {
                    return d;
                }
            } else {
                index = s.indexOf("?");
                if (index > 0) {
                    var d = [];
                    var argList = s.substring(0, index).split('|');
                    for (var i in argList) {
                        d.push(getData(data, argList[i]));
                    }
                    var _tempFunc = eval(s.substring(index + 1));//获取方法
                    if (typeof _tempFunc == 'function') {
                        return _tempFunc.apply(null, d);
                        // return _tempFunc(d);
                    } else {
                        return "";
                    }
                } else {
                    return getData(data, s);
                }
            }
        }

        _.load = function (data) {
            var o = _.o;
            o.count = getData(data, o.countRoot) || 0;
            o.datas = {};
            var arr = o.dataRoot.split(",");

            var output = "";
            o.template.each(function (i) {
                var datas = getData(data, arr[i]) || [];
                // 转换string类型的json数据为object
                if (typeof  datas == 'string') {
                    datas = eval("(" + datas + ")");
                }

                o.datas[arr[i]] = datas;
                for (var d in datas) {
                    var dat = datas[d];
                    if (dat !== null) {
                        dat._auto_increment_index = (o.pageNo - 1) * o.pageSize + 1 + parseInt(d);
                        dat._self_data = dat;
                        output += $(this).html().replace(/\{\{.+\}\}/g, function (word) {
                            return handle(dat, word.substr(2, word.length - 4));
                        });
                    }
                }
            });

            var el = $(_);
            $("tbody", el).html(output);
            generatePagination();
            if (typeof o.onDataShowComplete == 'function') {
                o.onDataShowComplete();
            }
        };

        // 下一页
        _.nextPage = function () {
            var pageNo = _.o.pageNo + 1;
            var maxPage = Math.ceil(_.o.count / _.o.pageSize);
            pageNo > maxPage ? pageNo = maxPage : true;
            generateTable(pageNo);
        };

        // 改变分页大小
        _.changePageSize = function (n) {
            n = parseInt(n);
            if (n <= 0) {
                return;
            }
            _.o.pageSize = n;
            _.gotoPage(1);
        };

        // 前一页
        _.previousPage = function () {
            var pageNo = _.o.pageNo - 1;
            pageNo < 1 ? pageNo = 1 : true;
            generateTable(pageNo);
        };

        // 跳转到指定的页
        _.gotoPage = function (n) {
            if (n == null) return;
            var maxPage = Math.ceil(_.o.count / _.o.pageSize);
            n > maxPage ? n = maxPage : true;
            n < 1 ? n = 1 : true;
            generateTable(n);
        };

        // 获取列表数据
        _.getDatas = function (root) {
            if (root == null) {
                var arr = _.o.dataRoot.split(",");
                if (arr.length == 1) {
                    return _.o.datas[arr[0]];
                }
            } else {
                return _.o.datas[root];
            }
        };

        // 获取记录总条数
        _.getCount = function () {
            return _.o.count;
        };

        // 获取页码
        _.getPageNo = function () {
            return _.o.pageNo;
        };

        _.refresh = function () {
            return generateTable(_.o.pageNo);
        }

        // 获取页面显示数据条数
        _.getPageSize = function () {
            return _.o.pageSize;
        };

        _.each(function (k, v) {
            _.init($(v), settings);
        });
        return _;
    };
})(jQuery);