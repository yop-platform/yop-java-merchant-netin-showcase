/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.payment.yop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdc.test.shop.merchantmgmt.model.ProductFeeQueryRequest;
import com.wdc.test.shop.merchantmgmt.model.ProductFeeQueryResponse;
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
 * title: YOP产品费率查询客户端<br>
 * description: 负责对接YOP产品费率查询API，返回响应<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@Component
public class YopProductFeeQueryClient {
    private static final Logger logger = LoggerFactory.getLogger(YopProductFeeQueryClient.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private YopClient yopClient;

    @Value("${yeepay.product.fee.query.path:/rest/v2.0/mer/product/fee/query}")
    private String feeQueryPath;
    @Value("${yeepay.appkey.prefix:sandbox_rsa_}")
    private String appKeyPrefix;
    @Value("${yeepay.saas.merchant.no:10080662589}")
    private String saasMerchantNo;

    @PostConstruct
    public void init() {
        yopClient = YopClientBuilder.builder().build();
        logger.info("YOP产品费率查询客户端初始化成功");
    }

    /**
     * 调用YOP产品费率查询API
     * @param req 查询参数
     * @return 响应
     * @throws Exception 调用失败抛出异常
     */
    public ProductFeeQueryResponse queryFee(ProductFeeQueryRequest req) throws Exception {
        logger.info("[merchantmgmt] 调用YOP产品费率查询API, req={}", req);
        YopRequest request = new YopRequest(feeQueryPath, "GET");
        request.getRequestConfig().setAppKey(appKeyPrefix + saasMerchantNo);
        Map<String, Object> paramMap = buildParams(req);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (entry.getValue() != null) {
                request.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        logger.info("请求API：{}，参数：{}", feeQueryPath, paramMap);
        YopResponse response = yopClient.request(request);
        String result = response.getStringResult();
        logger.info("YOP产品费率查询响应：{}", result);
        return OBJECT_MAPPER.readValue(result, ProductFeeQueryResponse.class);
    }

    /**
     * 产品费率变更进度查询
     * @param req 查询参数
     * @return 查询响应
     * @throws Exception 调用失败抛出异常
     */
    public com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyQueryResponse queryModifyProgress(com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyQueryRequest req) throws Exception {
        Logger logger = LoggerFactory.getLogger(YopProductFeeQueryClient.class);
        logger.info("[merchantmgmt] 调用YOP产品费率变更进度查询API, req={}", req);
        YopRequest request = new YopRequest("/rest/v2.0/mer/product/modify/query", "GET");
        request.getRequestConfig().setAppKey(appKeyPrefix + saasMerchantNo);
        request.addParameter("requestNo", req.getRequestNo());
        YopResponse response = yopClient.request(request);
        String result = response.getStringResult();
        logger.info("YOP产品费率变更进度查询响应：{}", result);
        return OBJECT_MAPPER.readValue(result, com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyQueryResponse.class);
    }

    /**
     * 构建参数Map，过滤空值
     */
    private Map<String, Object> buildParams(ProductFeeQueryRequest req) {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantNo", req.getMerchantNo());
        params.put("productCode", req.getProductCode());
        params.put("parentMerchantNo", req.getParentMerchantNo());
        params.put("extraInfo", req.getExtraInfo());
        return params;
    }
} 