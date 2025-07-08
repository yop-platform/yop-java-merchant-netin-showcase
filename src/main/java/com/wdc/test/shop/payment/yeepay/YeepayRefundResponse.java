package com.wdc.test.shop.payment.yeepay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 易宝支付退款响应模型
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YeepayRefundResponse {
    
    /**
     * 返回码
     * OPR00000表示易宝受理成功，退款状态请以"status"为准
     */
    private String code;
    
    /**
     * 返回信息
     * 信息描述，对应code的中文信息
     */
    private String message;
    
    /**
     * 发起方商编
     */
    private String parentMerchantNo;
    
    /**
     * 商户编号
     */
    private String merchantNo;
    
    /**
     * 商户收款订单号
     */
    private String orderId;
    
    /**
     * 商户退款请求号
     */
    private String refundRequestId;
    
    /**
     * 易宝退款订单号
     * 商户退款请求对应在易宝的退款单号
     */
    private String uniqueRefundNo;
    
    /**
     * 退款订单状态
     * PROCESSING：退款处理中
     * SUCCESS：退款成功
     * FAILED：退款失败
     * CANCEL: 退款关闭
     * SUSPEND: 退款中断
     */
    private String status;
    
    /**
     * 退款申请金额
     */
    private String refundAmount;
    
    /**
     * 剩余可退款金额
     */
    private String residualAmount;
    
    /**
     * 退款受理时间
     */
    private String refundRequestDate;
    
    /**
     * 退还商户手续费
     * 在退款成功时返回
     */
    private String refundMerchantFee;
    
    /**
     * 退款资金来源信息
     * 格式：[{"accountType":"FUND_ACCOUNT","debitAmount":6.00}]
     */
    private String refundAccountDetail;
    
    /**
     * 扣账时间
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    private String refundCsFinishDate;
    
    /**
     * 判断退款是否成功
     * 
     * @return 成功返回true，失败返回false
     */
    public boolean isSuccess() {
        // 根据错误码判断请求是否成功受理
        boolean requestSuccess = "OPR00000".equals(code);
        
        // 再根据status判断退款实际状态
        if (requestSuccess) {
            return "SUCCESS".equals(status);
        }
        
        return false;
    }
    
    /**
     * 判断退款是否正在处理中
     * 
     * @return 处理中返回true，否则返回false
     */
    public boolean isProcessing() {
        return "OPR00000".equals(code) && "PROCESSING".equals(status);
    }
    
    /**
     * 判断退款是否失败
     * 
     * @return 失败返回true，否则返回false
     */
    public boolean isFailed() {
        // 如果请求未成功受理，或退款状态为FAILED，都认为是失败
        return !"OPR00000".equals(code) || "FAILED".equals(status);
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

    public String getUniqueRefundNo() {
        return uniqueRefundNo;
    }

    public void setUniqueRefundNo(String uniqueRefundNo) {
        this.uniqueRefundNo = uniqueRefundNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }
    
    public String getResidualAmount() {
        return residualAmount;
    }

    public void setResidualAmount(String residualAmount) {
        this.residualAmount = residualAmount;
    }

    public String getRefundRequestDate() {
        return refundRequestDate;
    }

    public void setRefundRequestDate(String refundRequestDate) {
        this.refundRequestDate = refundRequestDate;
    }

    public String getRefundMerchantFee() {
        return refundMerchantFee;
    }

    public void setRefundMerchantFee(String refundMerchantFee) {
        this.refundMerchantFee = refundMerchantFee;
    }

    public String getRefundAccountDetail() {
        return refundAccountDetail;
    }

    public void setRefundAccountDetail(String refundAccountDetail) {
        this.refundAccountDetail = refundAccountDetail;
    }

    public String getRefundCsFinishDate() {
        return refundCsFinishDate;
    }

    public void setRefundCsFinishDate(String refundCsFinishDate) {
        this.refundCsFinishDate = refundCsFinishDate;
    }
} 