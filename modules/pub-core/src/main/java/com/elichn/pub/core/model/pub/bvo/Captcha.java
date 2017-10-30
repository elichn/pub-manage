package com.elichn.pub.core.model.pub.bvo;

/**
 * <p>Title: Captcha</p>
 * <p>Description: Captcha</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class Captcha {

    private String code;
    private byte[] jpegBytes;

    public Captcha() {
    }

    public Captcha(String code, byte[] jpegBytes) {
        this.code = code;
        this.jpegBytes = jpegBytes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getJpegBytes() {
        return jpegBytes;
    }

    public void setJpegBytes(byte[] jpegBytes) {
        this.jpegBytes = jpegBytes;
    }
}
