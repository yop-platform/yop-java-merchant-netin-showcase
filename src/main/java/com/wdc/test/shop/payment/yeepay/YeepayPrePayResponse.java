package com.wdc.test.shop.payment.yeepay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 易宝支付-聚合支付统一下单响应参数
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YeepayPrePayResponse {

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 商户收款请求号
     */
    private String orderId;

    /**
     * 易宝收款订单号
     */
    private String uniqueOrderNo;

    /**
     * 渠道侧商户请求号
     * 支付机构在微信侧的外部商户订单号，用于服务商用于点金计划商户小票功能
     */
    private String bankOrderId;

    /**
     * 预支付标识信息
     */
    private String prePayTn;

    /**
     * 判断请求是否成功
     * @return 成功返回true，失败返回false
     */
    public boolean isSuccess() {
        return "00000".equals(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUniqueOrderNo() {
        return uniqueOrderNo;
    }

    public void setUniqueOrderNo(String uniqueOrderNo) {
        this.uniqueOrderNo = uniqueOrderNo;
    }

    public String getBankOrderId() {
        return bankOrderId;
    }

    public void setBankOrderId(String bankOrderId) {
        this.bankOrderId = bankOrderId;
    }

    public String getPrePayTn() {
        return prePayTn;
    }

    public void setPrePayTn(String prePayTn) {
        this.prePayTn = prePayTn;
    }
} 