package com.wdc.test.shop.payment.yeepay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信实名认证申请单申请-响应参数
 */
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class WechatAuthApplyResponse {
    /** 返回码 */
    private String returnCode;
    /** 返回描述 */
    private String returnMsg;
    /** 申请单编号 */
    private String applymentId;
    /** 请求号 */
    private String requestNo;

    /**
     * 是否成功
     */
    public boolean isSuccess() {
        return "BMS00000".equals(returnCode);
    }
} 