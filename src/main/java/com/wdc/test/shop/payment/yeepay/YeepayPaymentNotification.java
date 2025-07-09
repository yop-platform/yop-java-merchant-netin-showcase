package com.wdc.test.shop.payment.yeepay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 易宝支付结果通知模型
 * 对应/rest/v1.0/aggpay/pre-pay接口的支付结果通知
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YeepayPaymentNotification {
    
    /**
     * 渠道订单号
     * 该笔订单银网联生成的唯一订单号
     */
    private String channelOrderId;
    
    /**
     * 子单列表信息
     * 存在拆单交易时返回
     */
    private Object subOrderInfoList;
    
    /**
     * 商户收款请求号
     * 交易下单传入的商户收款请求号
     */
    private String orderId;
    
    /**
     * 支付成功时间
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    private String paySuccessDate;
    
    /**
     * 渠道类型
     * WECHAT：微信
     * ALIPAY：支付宝
     * OPEN_UPOP: 银联
     * APPLEPAY：苹果支付仅聚合支付返回该参数
     * NCPAY：无卡支付
     * ACCOUNTPAY: 账户支付
     * MEMBERPAY: 会员支付
     * NET: 网银
     * DCEP: 数字人民币
     */
    private String channel;
    
    /**
     * 支付方式
     * USER_SCAN：用户扫码
     * MERCHANT_SCAN：商家扫码
     * JS_PAY：JS支付
     * MINI_PROGRAM：小程序支付
     * WECHAT_OFFIACCOUNT：微信公众号支付
     * ALIPAY_LIFE：生活号支付
     * FACE_SCAN_PAY：刷脸支付
     * SDK_PAY：SDK支付
     * H5_PAY：H5支付
     * ONEKEYPAY：一键支付
     * BINDCARDPAY：绑卡支付
     * E_BANK：网银支付
     * ENTERPRISE_ACCOUNT_PAY：企业账户支付
     * STATICQR：静态台牌
     * BANKTRANSFERPAY：银行转账支付
     * DIRECT_PAY:云微直接支付
     */
    private String payWay;
    
    /**
     * 易宝订单号
     */
    private String uniqueOrderNo;
    
    /**
     * 易宝营销信息
     */
    private Object ypPromotionInfo;
    
    /**
     * 订单金额
     * 单位：元
     */
    private String orderAmount;
    
    /**
     * 支付金额
     * 单位：元
     * 商户承担手续费时，支付金额=订单金额
     * 用户承担手续费时，支付金额=订单金额+手续费金额
     */
    private String payAmount;
    
    /**
     * 付款方信息
     */
    private Object payerInfo;
    
    /**
     * 渠道优惠信息
     */
    private Object channelPromotionInfo;
    
    /**
     * 用户实际支付金额
     */
    private String realPayAmount;
    
    /**
     * 发起方商编
     * 交易发起方的商编
     */
    private String parentMerchantNo;
    
    /**
     * 收款商户编号
     */
    private String merchantNo;
    
    /**
     * 支付结果
     * SUCCESS（订单支付成功）
     */
    private String status;
    
    /**
     * 分期信息
     */
    private Object installmentInfo;
    
    /**
     * 支付失败的code码
     */
    private String failCode;
    
    /**
     * 支付失败的失败原因
     */
    private String failReason;
    
    /**
     * 终端信息
     */
    private Object terminalInfo;
    
    /**
     * 资金到账类型
     * REALTIME:实时
     * PREAUTH:预授权
     */
    private String tradeType;
    
    /**
     * 预授权状态
     * WAITPREAUTH:等待预授权
     * PREAUTH:预授权
     * PREAUTHREPEAL:预授权撤销
     * PREAUTHCOMPLETE:预授权完成
     */
    private String preAuthStatus;
    
    /**
     * 预授权金额
     */
    private String preAuthAmount;
    
    /**
     * 微信/支付宝订单号
     * 该笔订单在微信支付宝侧的唯一订单号，微信交易单号/支付宝订单号
     */
    private String channelTrxId;
    
    /**
     * 银行订单号
     * 支付机构在渠道侧的外部商户订单号，微信商户单号/支付宝商家订单号
     */
    private String bankOrderId;
    
    /**
     * 订单管控状态
     * INIT: 处理中
     * FROZEN: 已冻结
     * UN_FROZEN: 已解冻
     */
    private String fundControlCsStatus;
    
    /**
     * 管控订单解冻时间
     */
    private String csUnFrozenCompleteDate;
    
    /**
     * 收方一级基础产品码
     */
    private String basicsProductFirst;
    
    /**
     * 收方二级基础产品码
     */
    private String basicsProductSecond;
    
    /**
     * 收方三级基础产品码
     */
    private String basicsProductThird;
    
    /**
     * 对账备注
     * 商户自定义参数，会展示在交易对账单中
     */
    private String memo;
    
    /**
     * 子商户名称
     */
    private String merchantName;
    
    /**
     * 清算核验渠道
     * NUCC-网联
     * UP-银联
     * OTHER-三方
     */
    private String outClearChannel;

    public String getChannelOrderId() {
        return channelOrderId;
    }

    public void setChannelOrderId(String channelOrderId) {
        this.channelOrderId = channelOrderId;
    }

    public Object getSubOrderInfoList() {
        return subOrderInfoList;
    }

    public void setSubOrderInfoList(Object subOrderInfoList) {
        this.subOrderInfoList = subOrderInfoList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaySuccessDate() {
        return paySuccessDate;
    }

    public void setPaySuccessDate(String paySuccessDate) {
        this.paySuccessDate = paySuccessDate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getUniqueOrderNo() {
        return uniqueOrderNo;
    }

    public void setUniqueOrderNo(String uniqueOrderNo) {
        this.uniqueOrderNo = uniqueOrderNo;
    }

    public Object getYpPromotionInfo() {
        return ypPromotionInfo;
    }

    public void setYpPromotionInfo(Object ypPromotionInfo) {
        this.ypPromotionInfo = ypPromotionInfo;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public Object getPayerInfo() {
        return payerInfo;
    }

    public void setPayerInfo(Object payerInfo) {
        this.payerInfo = payerInfo;
    }

    public Object getChannelPromotionInfo() {
        return channelPromotionInfo;
    }

    public void setChannelPromotionInfo(Object channelPromotionInfo) {
        this.channelPromotionInfo = channelPromotionInfo;
    }

    public String getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(String realPayAmount) {
        this.realPayAmount = realPayAmount;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getInstallmentInfo() {
        return installmentInfo;
    }

    public void setInstallmentInfo(Object installmentInfo) {
        this.installmentInfo = installmentInfo;
    }

    public String getFailCode() {
        return failCode;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public Object getTerminalInfo() {
        return terminalInfo;
    }

    public void setTerminalInfo(Object terminalInfo) {
        this.terminalInfo = terminalInfo;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getPreAuthStatus() {
        return preAuthStatus;
    }

    public void setPreAuthStatus(String preAuthStatus) {
        this.preAuthStatus = preAuthStatus;
    }

    public String getPreAuthAmount() {
        return preAuthAmount;
    }

    public void setPreAuthAmount(String preAuthAmount) {
        this.preAuthAmount = preAuthAmount;
    }

    public String getChannelTrxId() {
        return channelTrxId;
    }

    public void setChannelTrxId(String channelTrxId) {
        this.channelTrxId = channelTrxId;
    }

    public String getBankOrderId() {
        return bankOrderId;
    }

    public void setBankOrderId(String bankOrderId) {
        this.bankOrderId = bankOrderId;
    }

    public String getFundControlCsStatus() {
        return fundControlCsStatus;
    }

    public void setFundControlCsStatus(String fundControlCsStatus) {
        this.fundControlCsStatus = fundControlCsStatus;
    }

    public String getCsUnFrozenCompleteDate() {
        return csUnFrozenCompleteDate;
    }

    public void setCsUnFrozenCompleteDate(String csUnFrozenCompleteDate) {
        this.csUnFrozenCompleteDate = csUnFrozenCompleteDate;
    }

    public String getBasicsProductFirst() {
        return basicsProductFirst;
    }

    public void setBasicsProductFirst(String basicsProductFirst) {
        this.basicsProductFirst = basicsProductFirst;
    }

    public String getBasicsProductSecond() {
        return basicsProductSecond;
    }

    public void setBasicsProductSecond(String basicsProductSecond) {
        this.basicsProductSecond = basicsProductSecond;
    }

    public String getBasicsProductThird() {
        return basicsProductThird;
    }

    public void setBasicsProductThird(String basicsProductThird) {
        this.basicsProductThird = basicsProductThird;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getOutClearChannel() {
        return outClearChannel;
    }

    public void setOutClearChannel(String outClearChannel) {
        this.outClearChannel = outClearChannel;
    }

    @Override
    public String toString() {
        return "YeepayPaymentNotification{" +
                "orderId='" + orderId + '\'' +
                ", uniqueOrderNo='" + uniqueOrderNo + '\'' +
                ", status='" + status + '\'' +
                ", paySuccessDate='" + paySuccessDate + '\'' +
                ", orderAmount='" + orderAmount + '\'' +
                ", payAmount='" + payAmount + '\'' +
                ", channel='" + channel + '\'' +
                ", payWay='" + payWay + '\'' +
                '}';
    }
} 