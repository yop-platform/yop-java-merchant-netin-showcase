package com.wdc.test.shop.payment.yeepay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * 易宝支付-订单查询响应参数
 */
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class YeepayOrderQueryResponse {
    /** 返回码 OPR00000 成功 */
    private String code;
    /** 返回信息 */
    private String message;
    /** 交易发起方商编 */
    private String parentMerchantNo;
    /** 收款商户编号 */
    private String merchantNo;
    /** 商户收款请求号 */
    private String orderId;
    /** 易宝收款订单号 */
    private String uniqueOrderNo;
    /** 订单状态 PROCESSING/SUCCESS/TIME_OUT/FAIL/CLOSE */
    private String status;
    /** 订单金额，单位:元 */
    private Double orderAmount;
    /** 用户支付金额，单位:元 */
    private Double payAmount;
    /** 商户手续费，单位:元 */
    private Double merchantFee;
    /** 用户手续费，单位:元 */
    private Double customerFee;
    /** 支付成功时间 */
    private String paySuccessDate;
    /** 对账备注 */
    private String memo;
    /** 支付方式 */
    private String payWay;
    /** 支付授权token */
    private String token;
    /** 分账订单标识 */
    private String fundProcessType;
    /** 银行订单号 */
    private String bankOrderId;
    /** 渠道订单号 */
    private String channelOrderId;
    /** 渠道类型 */
    private String channel;
    /** 用户实际支付金额 */
    private Double realPayAmount;
    /** 剩余可分账金额 */
    private Double unSplitAmount;
    /** 累计已退款金额 */
    private Double totalRefundAmount;
    /** 支付者信息 */
    private PayerInfo payerInfo;
    /** 渠道侧优惠列表 */
    private List<ChannelPromotionInfo> channelPromotionInfo;
    /** 易宝优惠列表 */
    private List<YpPromotionInfo> ypPromotionInfo;
    /** 终端信息 */
    private String terminalInfo;
    /** 信用卡分期实体 */
    private InstallmentInfo installmentInfo;
    /** 渠道拓展信息 */
    private EnterprisePayInfo enterprisePayInfo;
    /** 支付失败原因 */
    private String failReason;
    /** 支付失败code码 */
    private String failCode;
    /** 清算状态 */
    private String clearStatus;
    /** 手续费出资详情列表 */
    private List<FeeContributeInfo> feeContributeInfo;
    /** 资金到账类型 */
    private String tradeType;
    /** 预授权状态 */
    private String preAuthStatus;
    /** 预授权金额 */
    private Double preAuthAmount;
    /** 聚合商户号信息 */
    private ChannelMerchantInfo channelMerchantInfo;
    /** 清算成功时间 */
    private String csSuccessDate;
    /** 入账金额 */
    private Double ypSettleAmount;
    /** 手续费承担商编 */
    private String feeMerchantNo;
    /** 手续费类型 */
    private String feeType;
    /** 清算核验渠道 */
    private String outClearChannel;
    /** 微信/支付宝订单号 */
    private String channelTrxId;
    /** 订单管控状态 */
    private String fundControlCsStatus;
    /** 管控订单解冻时间 */
    private String csUnFrozenCompleteDate;
    /** 收方一级基础产品码 */
    private String basicsProductFirst;
    /** 收方二级基础产品码 */
    private String basicsProductSecond;
    /** 收方三级基础产品码 */
    private String basicsProductThird;
    /** 原始可分账金额 */
    private Double originalDivideAmount;
    /** 累计已分账金额 */
    private Double totalDivideAmount;
    /** 外卡币种 */
    private String currencyCode;
    /** 外币金额 */
    private Double deductionAmount;
    /** 预授权方式 */
    private String preAuthWay;
    /** 微信appid */
    private String appID;
    /** 手续费费率信息 */
    private FeeRateInfo feeRateInfo;
    /** 信用分请求号 */
    private String creditOrderId;

    /**
     * 判断订单查询是否成功
     * @return true:成功，false:失败
     */
    public boolean isSuccess() {
        return "OPR00000".equals(code);
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PayerInfo {
        /** 银行编号，如：ABC、CBC等 */
        private String bankId;
        /** 账户名称，网银B2B支付会返回付款企业账户名称 */
        private String accountName;
        /** 银行卡号（前6后4） */
        private String bankCardNo;
        /** 手机号（前3后4) */
        private String mobilePhoneNo;
        /** 卡类型 DEBIT/CREDIT/CFT/QUASI_CREDIT/PUBLIC_ACCOUNT */
        private String cardType;
        /** 用户ID */
        private String userID;
        /** 支付宝买家登录账号 */
        private String buyerLogonId;
        /** 记帐簿编号 */
        private String ypAccountBookNo;
        /** 微信appid */
        private String appID;
        /** 微信/支付宝订单号 */
        private String channelTrxId;
        /** 银行附言 */
        private String bankPostscript;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChannelPromotionInfo {
        private String promotionId;
        private String promotionName;
        private String promotionScope;
        private Double amount;
        private Double amountRefund;
        private String activityId;
        private String channelContribute;
        private String merchantContribute;
        private String otherContribute;
        private String memo;
        private String promotionType;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class YpPromotionInfo {
        private String marketNo;
        private String couponNo;
        private String subsidyOrderNo;
        private String type;
        private String amount;
        private String contributeMerchant;
        private String status;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InstallmentInfo {
        private String instNumber;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EnterprisePayInfo {
        private List<EnterpriseInfo> enterpriseInfoList;
        private Double invoiceAmount;
        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class EnterpriseInfo {
            private String enterpriseId;
            private Double enterprisePayAmount;
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FeeContributeInfo {
        private String merchantNo;
        private String amount;
        private String type;
        private String marketSource;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChannelMerchantInfo {
        private String channelMerchantNo;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FeeRateInfo {
        private String rateType;
        private String paymentMethod;
        private String ladderCycleType;
        private List<FeeRateFormula> feeRateFormulaList;
        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class FeeRateFormula {
            private String ladderMin;
            private String ladderMax;
            private String periodLadderMin;
            private String periodLadderMax;
            private String rateType;
            private String percentRate;
            private String fixedRate;
            private String maxRate;
            private String minRate;
        }
    }
} 