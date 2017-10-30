package com.elichn.mybatis.generator.api;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

import java.sql.Types;

/**
 * <p>Title: IntrospectedColumn</p>
 * <p>Description: 针对jodatime增加typehandlder的配置</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class IntrospectedColumn extends org.mybatis.generator.api.IntrospectedColumn {

    @Override
    public void setFullyQualifiedJavaType(FullyQualifiedJavaType fullyQualifiedJavaType) {
        super.setFullyQualifiedJavaType(fullyQualifiedJavaType);
        if (Types.DATE == this.getJdbcType() && "org.joda.time.DateTime".equals(fullyQualifiedJavaType.getFullyQualifiedName())) {
            this.typeHandler = "com.elichn.mybatis.typehandler.JodaDateTime2DateTypeHandler";
        } else if (Types.TIMESTAMP == this.getJdbcType() && "org.joda.time.DateTime".equals(fullyQualifiedJavaType.getFullyQualifiedName())) {
            this.typeHandler = "com.elichn.mybatis.typehandler.JodaDateTime2TimestampTypeHandler";
        } else if (Types.TIME == this.getJdbcType() && "org.joda.time.DateTime".equals(fullyQualifiedJavaType.getFullyQualifiedName())) {
            this.typeHandler = "com.elichn.mybatis.typehandler.JodaDateTime2TimeTypeHandler";
        }
    }
}
