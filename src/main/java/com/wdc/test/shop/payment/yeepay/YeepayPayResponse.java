package com.wdc.test.shop.payment.yeepay;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;

/**
 * 易宝支付-付款码支付响应参数
 * 对应接口：/rest/v1.0/aggpay/pay
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class YeepayPayResponse {
    /** 返回码 */
    private String code;
    /** 返回信息 */
    private String message;
    /** 商户收款请求号 */
    private String orderId;
    /** 易宝收款订单号 */
    private String uniqueOrderNo;
    /** 渠道侧商户请求号 */
    private String bankOrderId;
    /** 状态 PROCESSING:处理中 WAITPAY:等待用户输入密码 SUCCESS:支付成功 FAIL:支付失败 */
    private String status;
    /** 终端授权方式 MICROPAY/付款码支付 FACEPAY/刷脸支付 */
    private String payType;
    /** 支付完成时间 */
    private String paySuccessTime;
    /** 用户ID，微信openId/支付宝userId */
    private String userId;
    /** 付款银行 CFT/ALIPAY/OPEN_UPOP/WECHAT/VISA/ICBC等 */
    private String payBank;
    /** 渠道订单号 */
    private String transactionId;
    /** 渠道优惠信息 */
    private String channelPromotionInfo;
    /** 实际支付金额 */
    private BigDecimal realPayAmount;
    /** 银行订单号 */
    private String channelTrxId;
    /** 渠道类型 */
    private String channel;
    /** 支付方式 */
    private String payWay;
    /** 订单金额 */
    private BigDecimal orderAmount;
    /** 卡类型 DEBIT:借记卡 CREDIT:贷记卡 */
    private String cardType;

    /**
     * 判断支付是否成功
     * @return true:成功 false:失败
     */
    public boolean isSuccess() {
        return "00000".equals(code) && "SUCCESS".equalsIgnoreCase(status);
    }
} 