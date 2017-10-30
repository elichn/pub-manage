package com.elichn.pub.core.constant;

/**
 * <p>Title: UserTypeEnum</p>
 * <p>Description: 用户类型枚举</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public enum UserTypeEnum {

    STANDARD(2),
    LDAP(1);

    private int typeValue;

    UserTypeEnum(int typeValue) {
        this.typeValue = typeValue;
    }

    public int getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(int typeValue) {
        this.typeValue = typeValue;
    }
}
