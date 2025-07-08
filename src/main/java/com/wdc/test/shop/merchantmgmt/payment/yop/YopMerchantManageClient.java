/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.payment.yop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyRequest;
import com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyResponse;
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
 * title: YOP商户管理客户端<br>
 * description: 负责对接YOP商户信息修改API，返回响应<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@Component
public class YopMerchantManageClient {
    private static final Logger logger = LoggerFactory.getLogger(YopMerchantManageClient.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private YopClient yopClient;

    @Value("${yeepay.merchant.info.modify.path:/rest/v1.0/mer/merchant/info/modify}")
    private String infoModifyPath;
    @Value("${yeepay.appkey.prefix:sandbox_rsa_}")
    private String appKeyPrefix;
    @Value("${yeepay.saas.merchant.no:10080662589}")
    private String saasMerchantNo;

    @PostConstruct
    public void init() {
        yopClient = YopClientBuilder.builder().build();
        logger.info("YOP商户管理客户端初始化成功");
    }

    /**
     * 调用YOP商户信息修改API
     * @param req 修改参数
     * @return 响应
     * @throws Exception 调用失败抛出异常
     */
    public MerchantInfoModifyResponse modifyInfo(MerchantInfoModifyRequest req) throws Exception {
        logger.info("[merchantmgmt] 调用YOP商户信息修改API, req={}", req);
        YopRequest request = new YopRequest(infoModifyPath, "POST");
        request.getRequestConfig().setAppKey(appKeyPrefix + saasMerchantNo);
        Map<String, Object> paramMap = buildParams(req);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (entry.getValue() != null) {
                request.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        logger.info("请求API：{}，参数：{}", infoModifyPath, paramMap);
        YopResponse response = yopClient.request(request);
        String result = response.getStringResult();
        logger.info("YOP商户信息修改响应：{}", result);
        return OBJECT_MAPPER.readValue(result, MerchantInfoModifyResponse.class);
    }

    /**
     * 商户信息修改进度查询
     * @param req 查询参数
     * @return 查询响应
     * @throws Exception 调用失败抛出异常
     */
    public com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyQueryResponse queryModifyProgress(com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyQueryRequest req) throws Exception {
        Logger logger = LoggerFactory.getLogger(YopMerchantManageClient.class);
        logger.info("[merchantmgmt] 调用YOP商户信息修改进度查询API, req={}", req);
        YopRequest request = new YopRequest("/rest/v1.0/mer/merchant/info/modify/query", "GET");
        request.getRequestConfig().setAppKey(appKeyPrefix + saasMerchantNo);
        request.addParameter("requestNo", req.getRequestNo());
        YopResponse response = yopClient.request(request);
        String result = response.getStringResult();
        logger.info("YOP商户信息修改进度查询响应：{}", result);
        return OBJECT_MAPPER.readValue(result, com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyQueryResponse.class);
    }

    /**
     * 构建参数Map，过滤空值
     */
    private Map<String, Object> buildParams(MerchantInfoModifyRequest req) {
        Map<String, Object> params = new HashMap<>();
        params.put("requestNo", req.getRequestNo());
        params.put("merchantNo", req.getMerchantNo());
        params.put("notifyUrl", req.getNotifyUrl());
        params.put("merchantSubjectInfo", req.getMerchantSubjectInfo());
        params.put("merchantContactInfo", req.getMerchantContactInfo());
        params.put("businessAddressInfo", req.getBusinessAddressInfo());
        params.put("adminEmail", req.getAdminEmail());
        params.put("adminMobile", req.getAdminMobile());
        params.put("extraInfo", req.getExtraInfo());
        return params;
    }
} 