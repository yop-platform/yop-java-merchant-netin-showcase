package com.wdc.test.shop.payment.yeepay;

/**
 * 易宝支付-聚合支付统一下单请求参数
 */
public class YeepayPrePayRequest {

    /**
     * 发起方商户编号
     * 发起方商户编号。
     * *标准商户收付款方案中此参数与收款商户编号一致；
     * *平台商户收付款方案中此参数为平台商商户编号；
     * *服务商解决方案中，①标准商户收款时，该参数为标准商户商编 ②平台商收款或平台商入驻商户收款时，该参数为平台商商编。
     */
    private String parentMerchantNo;

    /**
     * 商户编号
     * 商户编号 收单主体商编，平台商或服务商下的子商户，普通特约商户
     */
    private String merchantNo;

    /**
     * 商户收款请求号
     * 商户系统内部生成的订单号，需要保持在同一个商户下唯一
     */
    private String orderId;

    /**
     * 订单金额
     * 业务上是必须参数，单位： 元， 两位小数， 最低 0.01
     */
    private String orderAmount;

    /**
     * 订单截止时间
     * 格式"yyyy-MM-dd HH:mm:ss"不传默认一天
     */
    private String expiredTime;

    /**
     * 支付结果通知地址
     * 接收支付结果的通知地址
     */
    private String notifyUrl;

    /**
     * 页面回调地址
     * 页面回调地址。如使用易宝收银台需要上送前端页面回调地址
     */
    private String redirectUrl;

    /**
     * 对账备注
     * 商户自定义参数，会展示在交易对账单中,支持85个字符（中文或者英文字母）
     */
    private String memo;

    /**
     * 商品名称
     * 商品名称，简单描述订单信息或商品简介，用于展示在收银台页面或者支付明细中
     */
    private String goodsName;

    /**
     * 分账订单标记
     * 可选项如下:
     * DELAY_SETTLE:需要分账
     * REAL_TIME:不需要分账
     * REAL_TIME_DIVIDE:实时分账;需同时传入divideDetail
     */
    private String fundProcessType;

    /**
     * 支付方式
     * 可选项如下:
     * USER_SCAN:用户扫码
     * MINI_PROGRAM:小程序支付
     * WECHAT_OFFIACCOUNT:微信公众号
     * ALIPAY_LIFE:支付宝生活号
     * JS_PAY:JS支付
     * SDK_PAY:SDK支付
     * H5_PAY:H5支付
     */
    private String payWay;

    /**
     * 渠道类型
     * 可选项如下:
     * WECHAT:微信
     * ALIPAY:支付宝
     * UNIONPAY:银联云闪付
     * DCEP:数字人民币
     */
    private String channel;

    /**
     * 场景
     * channel为WECHAT时，可选值ONLINE/OFFLINE等；
     * channel为ALIPAY时，可选值OFFLINE/LARGE/REGISTRATION等
     */
    private String scene;

    /**
     * 用户真实IP地址
     */
    private String userIp;

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

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getFundProcessType() {
        return fundProcessType;
    }

    public void setFundProcessType(String fundProcessType) {
        this.fundProcessType = fundProcessType;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }
} 