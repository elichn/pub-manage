package com.elichn.mybatis.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.time.DateTime;

import java.sql.*;

/**
 * <p>Title: JodaDateTime2TimestampTypeHandler</p>
 * <p>Description: JodaDateTime2TimestampTypeHandler</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class JodaDateTime2TimestampTypeHandler extends BaseTypeHandler<DateTime> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, DateTime parameter, JdbcType jdbcType) throws SQLException {
        DateTime dateTime = (DateTime) parameter;
        ps.setTimestamp(i, new Timestamp(dateTime.getMillis()));
    }

    @Override
    public DateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        DateTime dateTime = null;
        Timestamp timestamp = rs.getTimestamp(columnName);
        if (timestamp != null) {
            dateTime = new DateTime(timestamp.getTime());
        }
        return dateTime;
    }

    @Override
    public DateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        DateTime dateTime = null;
        Timestamp timestamp = cs.getTimestamp(columnIndex);
        if (timestamp != null) {
            dateTime = new DateTime(timestamp.getTime());
        }
        return dateTime;
    }

    @Override
    public DateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        DateTime dateTime = null;
        Timestamp timestamp = rs.getTimestamp(columnIndex);
        if (timestamp != null) {
            dateTime = new DateTime(timestamp.getTime());
        }
        return dateTime;
    }




}
