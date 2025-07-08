package com.wdc.test.shop.payment.yeepay.merchant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 易宝商户资质文件上传响应类
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YeepayMerchantFileUploadResponse {

    /**
     * 响应编码，成功为REG00000
     */
    private String returnCode;
    
    /**
     * 响应信息
     */
    private String returnMsg;
    
    /**
     * 商户资质存储url，入网接口需要使用此URL
     */
    private String merQualUrl;
    
    /**
     * 是否成功
     */
    public boolean isSuccess() {
        return "REG00000".equals(returnCode);
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

    public String getMerQualUrl() {
        return merQualUrl;
    }

    public void setMerQualUrl(String merQualUrl) {
        this.merQualUrl = merQualUrl;
    }

    @Override
    public String toString() {
        return "YeepayMerchantFileUploadResponse{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                ", merQualUrl='" + merQualUrl + '\'' +
                '}';
    }
} 