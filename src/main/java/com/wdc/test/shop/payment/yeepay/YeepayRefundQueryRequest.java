package com.wdc.test.shop.payment.yeepay;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 易宝支付-退款查询请求参数
 */
@Data
@EqualsAndHashCode
public class YeepayRefundQueryRequest {
    /**
     * 发起方商编（可选）
     * 发起方商户编号。与交易下单传入的保持一致
     */
    private String parentMerchantNo;

    /**
     * 原收款商户编号（必填）
     */
    private String merchantNo;

    /**
     * 原收款交易对应的商户收款请求号（必填）
     */
    private String orderId;

    /**
     * 商户退款请求号（必填）
     * 需保证在商户端不重复
     */
    private String refundRequestId;

    /**
     * 易宝退款订单号（可选）
     */
    private String uniqueRefundNo;
} 