package com.wdc.test.shop.payment.yeepay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeepay.yop.sdk.service.common.YopClient;
import com.yeepay.yop.sdk.service.common.YopClientBuilder;
import com.yeepay.yop.sdk.service.common.request.YopRequest;
import com.yeepay.yop.sdk.service.common.response.YopResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 易宝支付-付款码支付服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class YeepayPayService {
    private final YeepayConfig yeepayConfig;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private YopClient yopClient;

    @PostConstruct
    public void init() {
        yopClient = YopClientBuilder.builder().build();
        log.info("易宝支付-付款码支付SDK初始化成功");
    }

    /**
     * 付款码支付
     * @param req 请求参数
     * @return 响应
     */
    public YeepayPayResponse pay(@Valid YeepayPayRequest req) {
        log.info("==== 开始易宝付款码支付 ====");
        try {
            Map<String, Object> params = buildParams(req);
            log.debug("易宝付款码支付请求参数：{}", OBJECT_MAPPER.writeValueAsString(params));
            YopRequest request = new YopRequest("/rest/v1.0/aggpay/pay", "POST");
            request.getRequestConfig().setAppKey(yeepayConfig.getAppKeyPrefix() + yeepayConfig.getSaasMerchantNo());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() != null && StringUtils.hasText(entry.getValue().toString())) {
                    request.addParameter(entry.getKey(), entry.getValue().toString());
                }
            }
            log.info("请求API：/rest/v1.0/aggpay/pay");
            YopResponse response = yopClient.request(request);
            String result = response.getStringResult();
            YeepayPayResponse payResponse = OBJECT_MAPPER.readValue(result, YeepayPayResponse.class);
            log.info("易宝付款码支付结果：{}", payResponse);
            return payResponse;
        } catch (Exception e) {
            log.error("易宝付款码支付异常", e);
            YeepayPayResponse error = new YeepayPayResponse();
            error.setCode("ERROR");
            error.setMessage("支付异常：" + e.getMessage());
            return error;
        } finally {
            log.info("==== 结束易宝付款码支付 ====");
        }
    }

    /**
     * 构建请求参数，过滤空值
     */
    private Map<String, Object> buildParams(YeepayPayRequest req) {
        Map<String, Object> params = new HashMap<>();
        params.put("parentMerchantNo", req.getParentMerchantNo());
        params.put("merchantNo", req.getMerchantNo());
        params.put("orderId", req.getOrderId());
        params.put("orderAmount", req.getOrderAmount());
        params.put("expiredTime", req.getExpiredTime());
        params.put("notifyUrl", req.getNotifyUrl());
        params.put("memo", req.getMemo());
        params.put("goodsName", req.getGoodsName());
        params.put("fundProcessType", req.getFundProcessType());
        params.put("payWay", req.getPayWay());
        params.put("channel", req.getChannel());
        params.put("scene", req.getScene());
        params.put("authCode", req.getAuthCode());
        params.put("appId", req.getAppId());
        params.put("userIp", req.getUserIp());
        params.put("terminalId", req.getTerminalId());
        params.put("terminalSceneInfo", req.getTerminalSceneInfo());
        params.put("channelSpecifiedInfo", req.getChannelSpecifiedInfo());
        params.put("channelPromotionInfo", req.getChannelPromotionInfo());
        params.put("identityInfo", req.getIdentityInfo());
        params.put("limitCredit", req.getLimitCredit());
        params.put("token", req.getToken());
        params.put("uniqueOrderNo", req.getUniqueOrderNo());
        params.put("csUrl", req.getCsUrl());
        params.put("accountLinkInfo", req.getAccountLinkInfo());
        params.put("bankCode", req.getBankCode());
        params.put("businessInfo", req.getBusinessInfo());
        params.put("ypAccountBookNo", req.getYpAccountBookNo());
        params.put("productInfo", req.getProductInfo());
        params.put("divideDetail", req.getDivideDetail());
        params.put("divideNotifyUrl", req.getDivideNotifyUrl());
        params.put("feeSubsidyInfo", req.getFeeSubsidyInfo());
        params.put("payMedium", req.getPayMedium());
        params.put("terminalInfo", req.getTerminalInfo());
        params.put("channelActivityInfo", req.getChannelActivityInfo());
        // 过滤空值
        params.entrySet().removeIf(e -> e.getValue() == null || (e.getValue() instanceof String && !StringUtils.hasText((String) e.getValue())));
        return params;
    }
} 