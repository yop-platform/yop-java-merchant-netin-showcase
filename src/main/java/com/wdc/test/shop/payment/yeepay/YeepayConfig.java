package com.wdc.test.shop.payment.yeepay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 易宝支付配置类
 */
@Component
public class YeepayConfig {

    /**
     * 父商户号
     */
    @Value("${yeepay.saas.merchant.no:10080662589}")
    private String saasMerchantNo;

    /**
     * 商户号（标准商）
     */
    @Value("${yeepay.standard.merchant.no:10080086386}")
    private String standardMerchantNo;

    /**
     * 商户号（标准商）
     */
    @Value("${yeepay.appkey.prefix:sandbox_rsa_}")
    private String appKeyPrefix;

    /**
     * 聚合支付统一下单API路径
     */
    @Value("${yeepay.api.prepay.path:/rest/v1.0/aggpay/pre-pay}")
    private String prePayPath;
    
    /**
     * 退款API路径
     */
    @Value("${yeepay.api.refund.path:/rest/v1.0/trade/refund}")
    private String refundPath;

    /**
     * 支付结果通知URL
     */
    @Value("${yeepay.notify.url:http://172.18.168.76:9009/payments/yeepay/notify}")
    private String notifyUrl;
    
    /**
     * 退款结果通知URL
     */
    @Value("${yeepay.refund.notify.url:http://172.18.168.76:9009/payments/refund-callback/YEEPAY}")
    private String refundNotifyUrl;
    
    /**
     * 商户入网资质文件上传API路径
     */
    @Value("${yeepay.api.merchant.qual.upload.path:/yos/v1.0/sys/merchant/qual/upload}")
    private String merchantFileUploadPath;
    
    /**
     * 商户入网API路径
     */
    @Value("${yeepay.api.merchant.register.path:/rest/v2.0/mer/register/saas/merchant}")
    private String merchantRegisterPath;
    
    /**
     * 商户入网结果回调URL
     */
    @Value("${yeepay.merchant.register.notify.url:http://172.18.168.76:9009/merchant/register/callback}")
    private String merchantRegisterNotifyUrl;

    /**
     * 订单查询API路径
     */
    @Value("${yeepay.api.order.query.path:/rest/v1.0/trade/order/query}")
    private String orderQueryPath;

    public String getSaasMerchantNo() {
        return saasMerchantNo;
    }

    public void setSaasMerchantNo(String saasMerchantNo) {
        this.saasMerchantNo = saasMerchantNo;
    }

    public String getStandardMerchantNo() {
        return standardMerchantNo;
    }

    public void setStandardMerchantNo(String standardMerchantNo) {
        this.standardMerchantNo = standardMerchantNo;
    }

    public String getAppKeyPrefix() {
        return appKeyPrefix;
    }

    public void setAppKeyPrefix(String appKeyPrefix) {
        this.appKeyPrefix = appKeyPrefix;
    }

    public String getPrePayPath() {
        return prePayPath;
    }

    public void setPrePayPath(String prePayPath) {
        this.prePayPath = prePayPath;
    }
    
    public String getRefundPath() {
        return refundPath;
    }

    public void setRefundPath(String refundPath) {
        this.refundPath = refundPath;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
    
    public String getRefundNotifyUrl() {
        return refundNotifyUrl;
    }

    public void setRefundNotifyUrl(String refundNotifyUrl) {
        this.refundNotifyUrl = refundNotifyUrl;
    }
    
    public String getMerchantFileUploadPath() {
        return merchantFileUploadPath;
    }

    public void setMerchantFileUploadPath(String merchantFileUploadPath) {
        this.merchantFileUploadPath = merchantFileUploadPath;
    }

    public String getMerchantRegisterPath() {
        return merchantRegisterPath;
    }

    public void setMerchantRegisterPath(String merchantRegisterPath) {
        this.merchantRegisterPath = merchantRegisterPath;
    }

    public String getMerchantRegisterNotifyUrl() {
        return merchantRegisterNotifyUrl;
    }

    public void setMerchantRegisterNotifyUrl(String merchantRegisterNotifyUrl) {
        this.merchantRegisterNotifyUrl = merchantRegisterNotifyUrl;
    }

    public String getOrderQueryPath() {
        return orderQueryPath;
    }

    public void setOrderQueryPath(String orderQueryPath) {
        this.orderQueryPath = orderQueryPath;
    }
}