/**
 *  基于bootstrap的搜索下拉框
 */
;
(function ($) {
    $.fn.ss = function (settings) {
        var _ = this;
        var KEY_UP = 38;
        var KEY_DOWN = 40;
        var KEY_ENTER = 13;

        var defaults = {
            keyEl: null,      // 键值存放的元素
            data: null,       // 可遍历的数据
            getKey: null,     // 获取数据项的key
            getValue: null,   // 获取数据项的key
            defaultKey: null,
            defaultVal: null,
            onChange: null,   // 值改变的事件
            zIndex: 100,
            showAll:true      // 展示“全部”字样，选中该值为空
        };

        // 初始化组件
        _.init = function (el, s) {
            _.o = $.extend({}, defaults, s);
            var o = _.o;
            var div = $("<div></div>");

            div.css({
                position: "absolute",
                border: "1px solid #CCCCCC",
                "min-height": "50px",
                "max-height": "300px",
                "min-width": "200px",
                width: el.outerWidth(),
                overflow: "hidden",
                display: "none",
                "z-index": o.zIndex,
                background: "#f0f6e4", "border-radius": "4px"
            }).attr("data-type", "ss-body");

            div.appendTo($("body"));
            o.body = div;

            var input = $("<input type='text' class='form-control' style='width: 100%'>");
            input.appendTo(div);
            o.searcher = input;

            var selectDiv = $("<div></div>");
            selectDiv.css({
                "overflow-y": "auto",
                "min-height": "50px",
                "max-height": "250px"
            });
            selectDiv.appendTo(div);
            o.selectDiv = selectDiv;
            fillData(o.data);

            input.on("keyup", searchInStore);
            el.on("focus", showBody);

            $(window).resize(resize);
        };

        /**
         * 显示下拉框
         */
        function showBody() {
            var o = _.o;
            o.searcher.val("");
            fillData(o.data);
            // 重新初始化
            o.focusItem = null;

            // 定位下拉框位置
            resize();

            // 显示 绑定事件
            o.body.show();
            $(_).attr("readonly", "readonly");
            var body = $("body");
            body.bind("mousedown", hideBody);
            body.bind("keydown", onKeyDown);
            o.searcher.focus();
        }

        /**
         * 隐藏下拉框
         * @param event 事件来源
         * @param force 强制隐藏
         */
        function hideBody(event, force) {
            var o = $(event.target);
            if (force || (!(o.parents("div[data-type='ss-body']").length > 0) && o.attr("data-type") != 'ss-body')) {
                _.o.body.hide();
                var body = $("body");
                body.unbind("mousedown", hideBody);
                body.unbind("keydown", onKeyDown);
                $(_).removeAttr("readonly");
            }
        }

        /**
         * 方向键事件
         * @param event
         */
        function onKeyDown(event) {
            var o = $(event.target);
            if (o.parents("div[data-type='ss-body']").length > 0) {
                switch (event.keyCode) {
                    case KEY_UP :
                        scrollUp();
                        return;
                    case KEY_DOWN:
                        scrollDown();
                        return;
                    case KEY_ENTER:
                        var item = _.o.focusItem;
                        item && item.click();
                        return;
                    default :
                        return;
                }
            }
        }

        /**
         * 滚动条向上滚动
         */
        function scrollUp() {
            var o = _.o;
            if (o.focusItem == null) {
                o.items[o.items.length - 1].focus();
            } else {
                var focusIndex = o.focusItem.index;
                if (focusIndex > 0) {
                    var item = o.items[focusIndex - 1];
                    item.focus();

                    var itemY = item.offset().top;
                    // var divH = o.selectDiv.height();
                    var divY = o.selectDiv.offset().top;
                    // var itemH = item.height();
                    if (itemY < divY) {
                        var y = o.selectDiv.scrollTop();
                        o.selectDiv.scrollTop(y - item.height());
                    }
                }
            }
        }

        /**
         * 滚动条向下滚动
         */
        function scrollDown() {
            var o = _.o;
            if (o.focusItem == null) {
                o.items[0].focus();
            } else {
                var focusIndex = o.focusItem.index;
                var size = o.items.length;
                if (focusIndex < size - 1) {
                    var item = o.items[focusIndex + 1];
                    item.focus();

                    var itemY = item.offset().top;
                    var divH = o.selectDiv.height();
                    var divY = o.selectDiv.offset().top;
                    var itemH = item.height();
                    if (itemY + itemH > divY + divH) {
                        var y = o.selectDiv.scrollTop();
                        o.selectDiv.scrollTop(y + item.height());
                    }
                }
            }
        }

        /**
         * 查找满足条件的结果项
         * @param e
         */
        function searchInStore(e) {
            if (e.keyCode == 40 || e.keyCode == 38) {
                return;
            }

            var key = $(this).val().trim();
            var re = {};
            $.each(_.o.data, function (k, v) {
                var val = v;
                if (typeof _.o.getValue == 'function') {
                    val = _.o.getValue(k, v);
                }

                if (val == key) {
                    re[k] = v;
                }
            });

            $.each(_.o.data, function (k, v) {
                var val = v;
                if (typeof _.o.getValue == 'function') {
                    val = _.o.getValue(k, v);
                }

                if (val.indexOf(key) >= 0 && val != key) {
                    re[k] = v;
                }
            });

            fillData(re);
        }

        /**
         * 填充到下拉框中
         * @param data
         */
        function fillData(data) {
            if (data != null) {
                var table = $("<table class='table' style='margin-bottom: 0'></table>");
                var tbody = $("<tbody></tbody>");
                tbody.appendTo(table);

                _.o.items = [];
                if(_.o.showAll){
                    row(tbody, _.o.defaultKey || "", _.o.defaultVal || "全部", true);
                }
                $.each(data, function (k, v) {
                    row(tbody, k.replace("o_", ""), v);
                });

                $("table", _.o.body).remove();
                table.appendTo(_.o.selectDiv);
                resize();
            }
        }

        /**
         * 新建一行
         * @param tbody
         * @param k
         * @param v
         * @param flag 不经过转换
         */
        function row(tbody, k, v, flag) {
            var tr = $("<tr></tr>");
            var td = $("<td></td>");

            var key = k;
            var val = v;
            if (typeof _.o.getKey == 'function' && !flag) {
                key = _.o.getKey(k, v);
            }

            if (typeof _.o.getValue == 'function' && !flag) {
                val = _.o.getValue(k, v);
            }

            td.html(val);
            if (null != val) {
                td.appendTo(tr);
            }

            tr.on("click", function (e) {
                $(_).val(val);
                if (_.o.keyEl != null) {
                    var e = $(_.o.keyEl);
                    var ov = e.val();
                    e.val(key);
                    if (typeof _.o.onChange == 'function' && ov != key) {
                        _.o.onChange(ov, key);
                    }
                }
                hideBody(e, true);
            });
            tr.appendTo(tbody);
            tr.index = _.o.items.length;
            _.o.items.push(tr);

            // 注册focus和loseFocus事件
            tr.focus(function () {
                if (_.o.focusItem) {
                    _.o.focusItem.loseFocus();
                }
                _.o.focusItem = tr;
                tr.css({"background": "#33ccff"});
            });

            tr.loseFocus = function () {
                tr.css({"background": "#f0f6e4"});
            };

            tr.mouseenter(function () {
                tr.focus();
            });
        }

        /**
         * 重新布局
         */
        function resize() {
            var o = _.o;
            var el = $(_);
            var ot = el.offset();
            var wh = $(window).height();
            var th = o.body.height();
            var y = ot.top + el.outerHeight();
            if (y + th > wh) {
                y = y - th - el.outerHeight();
            }

            o.body.css({
                left: ot.left + "px",
                top: y + "px",
                width: el.outerWidth()
            });
        }

        _.each(function (k, v) {
            _.init($(v), settings);
        });

        return _;
    };
})(jQuery);