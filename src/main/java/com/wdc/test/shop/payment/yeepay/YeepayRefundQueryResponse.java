package com.wdc.test.shop.payment.yeepay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * 易宝支付-退款查询响应参数
 */
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class YeepayRefundQueryResponse {
    /** 返回码，OPR00000表示易宝受理成功，退款状态请以status为准 */
    private String code;
    /** 返回信息 */
    private String message;
    /** 发起方商编 */
    private String parentMerchantNo;
    /** 商户编号 */
    private String merchantNo;
    /** 商户收款请求号 */
    private String orderId;
    /** 商户退款请求号 */
    private String refundRequestId;
    /** 易宝收款订单号 */
    private String uniqueOrderNo;
    /** 易宝退款订单号 */
    private String uniqueRefundNo;
    /** 退款申请金额，单位:元 */
    private Double refundAmount;
    /** 退回商户手续费金额，单位:元 */
    private Double returnMerchantFee;
    /** 退款状态 PROCESSING/SUCCESS/FAILED/CANCEL/SUSPEND */
    private String status;
    /** 退款原因的简要描述 */
    private String description;
    /** 退款受理时间 */
    private String refundRequestDate;
    /** 退款成功时间 */
    private String refundSuccessDate;
    /** 退款失败原因 */
    private String failReason;
    /** 实际退款金额 */
    private Double realRefundAmount;
    /** 用户实退金额 */
    private Double cashRefundFee;
    /** 渠道侧优惠退回列表 */
    private List<BankPromotionInfo> bankPromotionInfoDTOList;
    /** 易宝侧优惠退回列表 */
    private List<YpPromotionInfo> ypPromotionInfoDTOList;
    /** 退款资金来源信息 */
    private String refundAccountDetail;
    /** 退款入账信息 */
    private String channelReceiverInfo;
    /** 扣账时间 */
    private String refundCsFinishDate;
    /** 退款银行订单号 */
    private String bankRefundOrderNo;
    /** 退款银行流水号 */
    private String bankRefundOrderId;
    /** 支付方式 */
    private String paymentMethod;
    /** 商户账户扣账金额 */
    private Double disAccountAmount;
    /** 原单订单管控状态 */
    private String orgFundControlCsStatus;
    /** 原单解冻完成时间 */
    private String orgCsUnFrozenCompleteDate;
    /** 原交易订单收方一级基础产品码 */
    private String orgBasicsProductFirst;
    /** 原交易订单收方二级基础产品码 */
    private String orgBasicsProductSecond;
    /** 原交易订单收方三级基础产品码 */
    private String orgBasicsProductThird;

    /**
     * 判断退款查询是否成功
     * @return true:成功，false:失败
     */
    public boolean isSuccess() {
        return "OPR00000".equals(code);
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BankPromotionInfo {
        private String promotionId;
        private String promotionName;
        private Double amountRefund;
        private String activityId;
        private String channelContribute;
        private String merchantContribute;
        private String otherContribute;
        private String memo;
        private String promotionType;
        private String promotionScope;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class YpPromotionInfo {
        private String marketNo;
        private String ypRefundAmount;
        private String type;
        private String couponNo;
    }
} 