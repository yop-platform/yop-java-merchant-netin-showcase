package com.wdc.test.shop.payment.yeepay.merchant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 易宝商户入网响应类
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YeepayMerchantRegisterResponse {

    /**
     * 响应编码，成功为NIG00000
     */
    private String returnCode;
    
    /**
     * 响应信息
     */
    private String returnMsg;
    
    /**
     * 入网请求号
     */
    private String requestNo;
    
    /**
     * 申请单编号
     */
    private String applicationNo;
    
    /**
     * 申请状态
     * REVIEWING:申请审核中
     * REVIEW_BACK:申请已驳回
     * AUTHENTICITY_VERIFYING:真实性验证中
     * AGREEMENT_SIGNING:协议待签署
     * BUSINESS_OPENING:业务开通中
     * COMPLETED:申请已完成
     */
    private String applicationStatus;
    
    /**
     * 商户编号
     */
    private String merchantNo;
    
    /**
     * 是否成功
     */
    public boolean isSuccess() {
        return "NIG00000".equals(returnCode);
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    @Override
    public String toString() {
        return "YeepayMerchantRegisterResponse{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                ", requestNo='" + requestNo + '\'' +
                ", applicationNo='" + applicationNo + '\'' +
                ", applicationStatus='" + applicationStatus + '\'' +
                ", merchantNo='" + merchantNo + '\'' +
                '}';
    }
} 