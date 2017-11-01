package com.elichn.pub.web.util;

import com.elichn.pub.core.util.DateTimeUtil;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.beans.PropertyEditorSupport;

/**
 * <p>Title: DateTimeEditor</p>
 * <p>Description: DateTimeEditor</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class DateTimeEditor extends PropertyEditorSupport {

    private final DateTimeFormatter ymsFormatter = DateTimeFormat.forPattern(DateTimeUtil.YMD_HMS);

    private final DateTimeFormatter ymdFormatter = DateTimeFormat.forPattern(DateTimeUtil.YMD);

    private final boolean allowEmpty;

    public DateTimeEditor(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    /**
     * setAsText setAsText
     *
     * @param text text
     * @throws IllegalArgumentException illegalArgumentException
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && StringUtils.isBlank(text)) {
            setValue(null);
        } else {
            if (text != null && (text.length() != DateTimeUtil.YMD.length()) && (text.length() != DateTimeUtil.YMD_HMS.length())) {
                throw new IllegalArgumentException(
                        "Could not parse date: format:" + DateTimeUtil.YMD_HMS + "  or " + DateTimeUtil.YMD);
            }
            if (text != null && text.length() == DateTimeUtil.YMD.length()) {
                setValue(ymdFormatter.parseDateTime(text));
            }
            if (text != null && text.length() == DateTimeUtil.YMD_HMS.length()) {
                setValue(ymsFormatter.parseDateTime(text));
            }
        }
    }

    /**
     * getAsText getAsText
     *
     * @return String
     */
    @Override
    public String getAsText() {
        DateTime value = (DateTime) getValue();
        return ((value != null) ? value.toString(DateTimeUtil.YMD_HMS) : "");
    }
}