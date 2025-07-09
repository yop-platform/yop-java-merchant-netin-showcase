package com.wdc.test.shop.payment.yeepay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 易宝支付退款请求参数模型
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YeepayRefundRequest {
    
    /**
     * 发起方商编
     * 与交易下单传入的保持一致
     */
    private String parentMerchantNo;
    
    /**
     * 原收款商户编号
     */
    private String merchantNo;
    
    /**
     * 原收款交易对应的商户收款请求号
     */
    private String orderId;
    
    /**
     * 商户退款请求号
     */
    private String refundRequestId;
    
    /**
     * 收款交易对应的易宝收款订单号
     */
    private String uniqueOrderNo;
    
    /**
     * 退款申请金额
     * 单位：元，两位小数，最低0.01，退款金额不能大于原订单金额
     */
    private String refundAmount;
    
    /**
     * 退款原因
     */
    private String description;
    
    /**
     * 对账备注
     * 商户自定义参数，会展示在交易对账单中
     */
    private String memo;
    
    /**
     * 退款资金来源
     * FUND_ACCOUNT：资金账户余额
     * 不传则默认使用未结算资金退款,如未结算资金不足则使用资金账户余额退款
     */
    private String refundAccountType;
    
    /**
     * 退款结果回调url
     * 接收退款结果通知地址，不传则不通知
     */
    private String notifyUrl;
    
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
    
    public String getRefundRequestId() {
        return refundRequestId;
    }
    
    public void setRefundRequestId(String refundRequestId) {
        this.refundRequestId = refundRequestId;
    }
    
    public String getUniqueOrderNo() {
        return uniqueOrderNo;
    }
    
    public void setUniqueOrderNo(String uniqueOrderNo) {
        this.uniqueOrderNo = uniqueOrderNo;
    }
    
    public String getRefundAmount() {
        return refundAmount;
    }
    
    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getMemo() {
        return memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    public String getRefundAccountType() {
        return refundAccountType;
    }
    
    public void setRefundAccountType(String refundAccountType) {
        this.refundAccountType = refundAccountType;
    }
    
    public String getNotifyUrl() {
        return notifyUrl;
    }
    
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
} 