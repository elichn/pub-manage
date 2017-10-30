package com.elichn.pub.service.pub;


import com.elichn.pub.core.model.pub.bvo.Captcha;

/**
 * <p>Title: CaptchaService</p>
 * <p>Description: CaptchaService</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface CaptchaService {

    /**
     * generateCaptcha 生成验证码
     *
     * @return Captcha
     */
    Captcha generateCaptcha();
}
