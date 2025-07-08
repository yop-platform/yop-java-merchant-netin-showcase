package com.wdc.test.shop.payment.yeepay;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 易宝支付-付款码支付请求参数
 * 对应接口：/rest/v1.0/aggpay/pay
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class YeepayPayRequest {
    /** 发起方商户编号 */
    private String parentMerchantNo;
    /** 商户编号 */
    private String merchantNo;
    /** 商户收款请求号，商户系统内部生成的订单号，需要保持在同一个商户下唯一 */
    private String orderId;
    /** 订单金额，单位：元，两位小数，最低0.01 */
    private BigDecimal orderAmount;
    /** 订单截止时间，格式yyyy-MM-dd HH:mm:ss，不传默认一天 */
    private String expiredTime;
    /** 支付结果通知地址 */
    private String notifyUrl;
    /** 对账备注，商户自定义参数，会展示在交易对账单中,支持85个字符（中文或者英文字母） */
    private String memo;
    /** 商品名称，简单描述订单信息或商品简介 */
    private String goodsName;
    /** 分账订单标记 DELAY_SETTLE/REAL_TIME/REAL_TIME_DIVIDE */
    private String fundProcessType;
    /** 支付方式类型，MERCHANT_SCAN:付款码支付 */
    @NotBlank(message = "支付方式类型payWay必填")
    private String payWay;
    /** 渠道类型 WECHAT/ALIPAY/UNIONPAY/DCEP */
    private String channel;
    /** 场景，详见文档说明 */
    private String scene;
    /** 用户授权码，银联JS支付时，与userId二选一必填 */
    @NotBlank(message = "用户授权码authCode必填")
    private String authCode;
    /** 微信公众号ID */
    private String appId;
    /** 用户真实IP地址 */
    @NotBlank(message = "用户真实IP地址userIp必填")
    private String userIp;
    /** 终端号，线下面对面场景必填 */
    @NotBlank(message = "终端号terminalId必填")
    private String terminalId;
    /** 商家终端场景信息，微信、支付宝需要传入，JSON字符串 */
    private String terminalSceneInfo;
    /** 渠道指定支付信息，JSON字符串 */
    private String channelSpecifiedInfo;
    /** 渠道优惠信息，JSON字符串 */
    private String channelPromotionInfo;
    /** 限制付款人信息，JSON字符串 */
    private String identityInfo;
    /** 是否限制贷记卡 Y/N */
    private String limitCredit;
    /** token，先下单再支付模式时必填 */
    private String token;
    /** 易宝订单号，先下单再支付模式时必填 */
    private String uniqueOrderNo;
    /** 清算回调地址 */
    private String csUrl;
    /** 合作银行信息，JSON字符串 */
    private String accountLinkInfo;
    /** 银行编码，DCEP必填 */
    private String bankCode;
    /** 自定义参数信息，JSON字符串 */
    private String businessInfo;
    /** 记账簿编号，支持收款至预收账户 */
    private String ypAccountBookNo;
    /** 易宝营销产品信息，JSON字符串 */
    private String productInfo;
    /** 分账明细，JSON字符串 */
    private String divideDetail;
    /** 分账通知地址 */
    private String divideNotifyUrl;
    /** 手续费补贴信息，JSON字符串 */
    private String feeSubsidyInfo;
    /** 支付媒介 PRECONSUME:预消费 */
    private String payMedium;
    /** 终端信息，JSON字符串 */
    private String terminalInfo;
    /** 渠道活动信息，JSON字符串 */
    private String channelActivityInfo;
} 