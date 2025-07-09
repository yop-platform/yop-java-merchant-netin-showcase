/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.payment.yop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdc.test.shop.merchantmgmt.model.MerchantEntryRequest;
import com.wdc.test.shop.merchantmgmt.model.MerchantEntryResponse;
import com.wdc.test.shop.merchantmgmt.model.MerchantStatusQueryResponse;
import com.wdc.test.shop.merchantmgmt.model.MerchantRegisterQueryResponse;
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
 * title: YOP商户进件客户端<br>
 * description: 负责对接YOP进件API，返回进件响应<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@Component
public class YopMerchantEntryClient {
    private static final Logger logger = LoggerFactory.getLogger(YopMerchantEntryClient.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private YopClient yopClient;

    @Value("${yeepay.merchant.entry.path:/rest/v2.0/mer/register/saas/merchant}")
    private String entryPath;
    @Value("${yeepay.appkey.prefix:sandbox_rsa_}")
    private String appKeyPrefix;
    @Value("${yeepay.saas.merchant.no:10080662589}")
    private String saasMerchantNo;

    @PostConstruct
    public void init() {
        yopClient = YopClientBuilder.builder().build();
        logger.info("YOP商户进件客户端初始化成功");
    }

    /**
     * 调用YOP进件API
     * @param req 进件参数
     * @return 进件响应
     * @throws Exception 调用失败抛出异常
     */
    public MerchantEntryResponse apply(MerchantEntryRequest req) throws Exception {
        logger.info("[merchantmgmt] 调用YOP进件API, req={}", req);
        YopRequest request = new YopRequest(entryPath, "POST");
        request.getRequestConfig().setAppKey(appKeyPrefix + saasMerchantNo);
        Map<String, Object> paramMap = buildParams(req);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (entry.getValue() != null) {
                request.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        logger.info("请求API：{}，参数：{}", entryPath, paramMap);
        YopResponse response = yopClient.request(request);
        String result = response.getStringResult();
        logger.info("YOP进件响应：{}", result);
        return OBJECT_MAPPER.readValue(result, MerchantEntryResponse.class);
    }

    /**
     * 商户状态查询
     * @param merchantNo 商户编号
     * @return 商户状态响应
     * @throws Exception 调用失败抛出异常
     */
    public MerchantStatusQueryResponse queryMerchantStatus(String merchantNo) throws Exception {
        logger.info("[merchantmgmt] 调用YOP商户状态查询API, merchantNo={}", merchantNo);
        YopRequest request = new YopRequest("/rest/v1.0/mer/status/query", "GET");
        request.getRequestConfig().setAppKey(appKeyPrefix + saasMerchantNo);
        request.addParameter("merchantNo", merchantNo);
        YopResponse response = yopClient.request(request);
        String result = response.getStringResult();
        logger.info("YOP商户状态查询响应：{}", result);
        return OBJECT_MAPPER.readValue(result, MerchantStatusQueryResponse.class);
    }

    /**
     * 商户入网进度查询
     * @param requestNo 入网请求号，可为空
     * @param merchantNo 商户编号，可为空
     * @return 入网进度响应
     * @throws Exception 调用失败抛出异常
     */
    public MerchantRegisterQueryResponse queryRegisterProgress(String requestNo, String merchantNo) throws Exception {
        logger.info("[merchantmgmt] 调用YOP商户入网进度查询API, requestNo={}, merchantNo={}", requestNo, merchantNo);
        YopRequest request = new YopRequest("/rest/v2.0/mer/register/query", "GET");
        request.getRequestConfig().setAppKey(appKeyPrefix + saasMerchantNo);
        if (requestNo != null && !requestNo.isEmpty()) request.addParameter("requestNo", requestNo);
        if (merchantNo != null && !merchantNo.isEmpty()) request.addParameter("merchantNo", merchantNo);
        YopResponse response = yopClient.request(request);
        String result = response.getStringResult();
        logger.info("YOP商户入网进度查询响应：{}", result);
        return OBJECT_MAPPER.readValue(result, MerchantRegisterQueryResponse.class);
    }

    public String getSaasMerchantNo() {
        return saasMerchantNo;
    }

    /**
     * 构建进件参数Map，过滤空值
     */
    private Map<String, Object> buildParams(MerchantEntryRequest req) {
        Map<String, Object> params = new HashMap<>();
        params.put("requestNo", req.getRequestNo());
        params.put("businessRole", req.getBusinessRole());
        params.put("parentMerchantNo", req.getParentMerchantNo());
        params.put("notifyUrl", req.getNotifyUrl());
        params.put("merchantSubjectInfo", buildSubjectInfo(req));
        params.put("merchantCorporationInfo", buildCorporationInfo(req));
        params.put("merchantContactInfo", buildContactInfo(req));
        params.put("industryCategoryInfo", buildIndustryInfo(req));
        params.put("businessAddressInfo", buildAddressInfo(req));
        params.put("settlementAccountInfo", buildSettleInfo(req));
        params.put("productInfo", req.getProductInfo());
        params.put("extraInfo", req.getExtraInfo());
        return params;
    }
    private String buildSubjectInfo(MerchantEntryRequest req) {
        Map<String, String> map = new HashMap<>();
        map.put("licenceUrl", req.getLicenceUrl());
        map.put("signName", req.getSignName());
        map.put("signType", req.getSignType());
        map.put("licenceNo", req.getLicenceNo());
        map.put("shortName", req.getShortName());
        try { return OBJECT_MAPPER.writeValueAsString(map); } catch (Exception e) { return ""; }
    }
    private String buildCorporationInfo(MerchantEntryRequest req) {
        Map<String, String> map = new HashMap<>();
        map.put("legalName", req.getLegalName());
        map.put("legalLicenceType", req.getLegalLicenceType());
        map.put("legalLicenceNo", req.getLegalLicenceNo());
        map.put("legalLicenceFrontUrl", req.getLegalLicenceFrontUrl());
        map.put("legalLicenceBackUrl", req.getLegalLicenceBackUrl());
        try { return OBJECT_MAPPER.writeValueAsString(map); } catch (Exception e) { return ""; }
    }
    private String buildContactInfo(MerchantEntryRequest req) {
        Map<String, String> map = new HashMap<>();
        map.put("contactName", req.getContactName());
        map.put("contactMobile", req.getContactMobile());
        map.put("contactEmail", req.getContactEmail());
        map.put("contactLicenceNo", req.getContactLicenceNo());
        map.put("adminEmail", req.getAdminEmail());
        map.put("adminMobile", req.getAdminMobile());
        try { return OBJECT_MAPPER.writeValueAsString(map); } catch (Exception e) { return ""; }
    }
    private String buildIndustryInfo(MerchantEntryRequest req) {
        Map<String, String> map = new HashMap<>();
        map.put("primaryIndustryCategory", req.getPrimaryIndustryCategory());
        map.put("secondaryIndustryCategory", req.getSecondaryIndustryCategory());
        try { return OBJECT_MAPPER.writeValueAsString(map); } catch (Exception e) { return ""; }
    }
    private String buildAddressInfo(MerchantEntryRequest req) {
        Map<String, String> map = new HashMap<>();
        map.put("province", req.getProvince());
        map.put("city", req.getCity());
        map.put("district", req.getDistrict());
        map.put("address", req.getAddress());
        try { return OBJECT_MAPPER.writeValueAsString(map); } catch (Exception e) { return ""; }
    }
    private String buildSettleInfo(MerchantEntryRequest req) {
        Map<String, String> map = new HashMap<>();
        map.put("settlementDirection", req.getSettlementDirection());
        map.put("bankCode", req.getBankCode());
        map.put("bankAccountType", req.getBankAccountType());
        map.put("bankCardNo", req.getBankCardNo());
        try { return OBJECT_MAPPER.writeValueAsString(map); } catch (Exception e) { return ""; }
    }
} 