package com.wdc.test.shop.payment.yeepay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 易宝退款结果通知模型
 * 对应退款结果通知接口参数
 * 参考：https://open.yeepay.com/docs-v3/product/fwssfk/2084.md
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YeepayRefundNotification {
    private String parentMerchantNo;
    private String merchantNo;
    private String orderId;
    private String uniqueOrderNo;
    private String refundAmount;
    private String realRefundAmount;
    private String residualAmount;
    private String returnMerchantFee;
    private String returnCustomerFee;
    private String realDeductAmount;
    private String settlementRefundFee;
    private String refundRequestId;
    private String uniqueRefundNo;
    private String refundRequestDate;
    private String status;
    private String refundSuccessDate;
    private String errorMessage;
    private String cashRefundFee;
    private String description;
    private String channelPromotionInfo;
    private String ypPromotionInfoDTOList;

    public String getParentMerchantNo() { return parentMerchantNo; }
    public void setParentMerchantNo(String parentMerchantNo) { this.parentMerchantNo = parentMerchantNo; }
    public String getMerchantNo() { return merchantNo; }
    public void setMerchantNo(String merchantNo) { this.merchantNo = merchantNo; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getUniqueOrderNo() { return uniqueOrderNo; }
    public void setUniqueOrderNo(String uniqueOrderNo) { this.uniqueOrderNo = uniqueOrderNo; }
    public String getRefundAmount() { return refundAmount; }
    public void setRefundAmount(String refundAmount) { this.refundAmount = refundAmount; }
    public String getRealRefundAmount() { return realRefundAmount; }
    public void setRealRefundAmount(String realRefundAmount) { this.realRefundAmount = realRefundAmount; }
    public String getResidualAmount() { return residualAmount; }
    public void setResidualAmount(String residualAmount) { this.residualAmount = residualAmount; }
    public String getReturnMerchantFee() { return returnMerchantFee; }
    public void setReturnMerchantFee(String returnMerchantFee) { this.returnMerchantFee = returnMerchantFee; }
    public String getReturnCustomerFee() { return returnCustomerFee; }
    public void setReturnCustomerFee(String returnCustomerFee) { this.returnCustomerFee = returnCustomerFee; }
    public String getRealDeductAmount() { return realDeductAmount; }
    public void setRealDeductAmount(String realDeductAmount) { this.realDeductAmount = realDeductAmount; }
    public String getSettlementRefundFee() { return settlementRefundFee; }
    public void setSettlementRefundFee(String settlementRefundFee) { this.settlementRefundFee = settlementRefundFee; }
    public String getRefundRequestId() { return refundRequestId; }
    public void setRefundRequestId(String refundRequestId) { this.refundRequestId = refundRequestId; }
    public String getUniqueRefundNo() { return uniqueRefundNo; }
    public void setUniqueRefundNo(String uniqueRefundNo) { this.uniqueRefundNo = uniqueRefundNo; }
    public String getRefundRequestDate() { return refundRequestDate; }
    public void setRefundRequestDate(String refundRequestDate) { this.refundRequestDate = refundRequestDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRefundSuccessDate() { return refundSuccessDate; }
    public void setRefundSuccessDate(String refundSuccessDate) { this.refundSuccessDate = refundSuccessDate; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public String getCashRefundFee() { return cashRefundFee; }
    public void setCashRefundFee(String cashRefundFee) { this.cashRefundFee = cashRefundFee; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getChannelPromotionInfo() { return channelPromotionInfo; }
    public void setChannelPromotionInfo(String channelPromotionInfo) { this.channelPromotionInfo = channelPromotionInfo; }
    public String getYpPromotionInfoDTOList() { return ypPromotionInfoDTOList; }
    public void setYpPromotionInfoDTOList(String ypPromotionInfoDTOList) { this.ypPromotionInfoDTOList = ypPromotionInfoDTOList; }
} 