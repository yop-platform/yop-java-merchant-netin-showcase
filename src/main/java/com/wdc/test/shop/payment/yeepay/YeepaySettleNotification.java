package com.wdc.test.shop.payment.yeepay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 易宝清算结果通知模型
 * 参考：https://open.yeepay.com/docs-v3/product/fwssfk/2276.md
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YeepaySettleNotification {
    private String parentMerchantNo;
    private String merchantNo;
    private String orderId;
    private String uniqueOrderNo;
    private String status;
    private String orderAmount;
    private String csSuccessDate;
    private String merchantFee;
    private String ypSettleAmount;
    private String feeMerchantNo;
    private String feeType;
    private String unSplitAmount;

    public String getParentMerchantNo() { return parentMerchantNo; }
    public void setParentMerchantNo(String parentMerchantNo) { this.parentMerchantNo = parentMerchantNo; }
    public String getMerchantNo() { return merchantNo; }
    public void setMerchantNo(String merchantNo) { this.merchantNo = merchantNo; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getUniqueOrderNo() { return uniqueOrderNo; }
    public void setUniqueOrderNo(String uniqueOrderNo) { this.uniqueOrderNo = uniqueOrderNo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getOrderAmount() { return orderAmount; }
    public void setOrderAmount(String orderAmount) { this.orderAmount = orderAmount; }
    public String getCsSuccessDate() { return csSuccessDate; }
    public void setCsSuccessDate(String csSuccessDate) { this.csSuccessDate = csSuccessDate; }
    public String getMerchantFee() { return merchantFee; }
    public void setMerchantFee(String merchantFee) { this.merchantFee = merchantFee; }
    public String getYpSettleAmount() { return ypSettleAmount; }
    public void setYpSettleAmount(String ypSettleAmount) { this.ypSettleAmount = ypSettleAmount; }
    public String getFeeMerchantNo() { return feeMerchantNo; }
    public void setFeeMerchantNo(String feeMerchantNo) { this.feeMerchantNo = feeMerchantNo; }
    public String getFeeType() { return feeType; }
    public void setFeeType(String feeType) { this.feeType = feeType; }
    public String getUnSplitAmount() { return unSplitAmount; }
    public void setUnSplitAmount(String unSplitAmount) { this.unSplitAmount = unSplitAmount; }
} 