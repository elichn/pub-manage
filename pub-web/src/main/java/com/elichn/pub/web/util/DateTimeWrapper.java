package com.elichn.pub.web.util;

import freemarker.template.*;
import org.joda.time.base.AbstractInstant;

/**
 * <p>Title: DateTimeWrapper</p>
 * <p>Description: DateTimeWrapper</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class DateTimeWrapper extends DefaultObjectWrapper {

    @Override
    public TemplateModel wrap(final Object obj)
            throws TemplateModelException {
        if (obj instanceof AbstractInstant) {
            return new SimpleDate(((AbstractInstant) obj).toDate(), TemplateDateModel.DATETIME);
        }
        return super.wrap(obj);
    }
}
