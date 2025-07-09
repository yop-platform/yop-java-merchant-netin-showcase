package com.wdc.test.shop.payment.yeepay;

/**
 * 易宝支付-订单查询请求参数
 */
public class YeepayOrderQueryRequest {
    /**
     * 发起方商编（可选）
     * 发起方商户编号。与交易下单传入的保持一致
     */
    private String parentMerchantNo;

    /**
     * 商户编号（必填）
     * 收款商户编号
     */
    private String merchantNo;

    /**
     * 商户收款请求号（必填）
     * 交易下单传入的商户收款请求号（合单收款场景请传入子单的商户收款请求号）
     */
    private String orderId;

    public String getParentMerchantNo() {
        return parentMerchantNo;
    }

    public void setParentMerchantNo(String parentMerchantNo) {
        this.parentMerchantNo = parentMerchantNo;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
} 