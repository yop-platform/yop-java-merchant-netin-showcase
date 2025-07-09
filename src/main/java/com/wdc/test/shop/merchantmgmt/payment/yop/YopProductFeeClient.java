/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.payment.yop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyRequest;
import com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyResponse;
import com.yeepay.yop.sdk.service.common.YopClient;
import com.yeepay.yop.sdk.service.common.YopClientBuilder;
import com.yeepay.yop.sdk.service.common.request.YopRequest;
import com.yeepay.yop.sdk.service.common.response.YopResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * title: YOP产品费率客户端<br>
 * description: 负责对接YOP产品费率修改API，返回响应<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@Component
public class YopProductFeeClient {
    private static final Logger logger = LoggerFactory.getLogger(YopProductFeeClient.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private YopClient yopClient;

    @Value("${yeepay.product.fee.modify.path:/rest/v2.0/mer/product/fee/modify}")
    private String feeModifyPath;
    @Value("${yeepay.appkey.prefix:sandbox_rsa_}")
    private String appKeyPrefix;
    @Value("${yeepay.saas.merchant.no:10080662589}")
    private String saasMerchantNo;

    @PostConstruct
    public void init() {
        yopClient = YopClientBuilder.builder().build();
        logger.info("YOP产品费率客户端初始化成功");
    }

    /**
     * 调用YOP产品费率修改API
     * @param req 修改参数
     * @return 响应
     * @throws Exception 调用失败抛出异常
     */
    public ProductFeeModifyResponse modifyFee(ProductFeeModifyRequest req) throws Exception {
        logger.info("[merchantmgmt] 调用YOP产品费率修改API, req={}", req);
        YopRequest request = new YopRequest(feeModifyPath, "POST");
        request.getRequestConfig().setAppKey(appKeyPrefix + saasMerchantNo);
        Map<String, Object> paramMap = buildParams(req);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (entry.getValue() != null) {
                request.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        logger.info("请求API：{}，参数：{}", feeModifyPath, paramMap);
        YopResponse response = yopClient.request(request);
        String result = response.getStringResult();
        logger.info("YOP产品费率修改响应：{}", result);
        return OBJECT_MAPPER.readValue(result, ProductFeeModifyResponse.class);
    }

    /**
     * 构建参数Map，过滤空值
     */
    private Map<String, Object> buildParams(ProductFeeModifyRequest req) {
        Map<String, Object> params = new HashMap<>();
        params.put("requestNo", req.getRequestNo());
        params.put("merchantNo", req.getMerchantNo());
        params.put("notifyUrl", req.getNotifyUrl());
        params.put("productInfo", req.getProductInfo());
        params.put("extraInfo", req.getExtraInfo());
        return params;
    }
} 