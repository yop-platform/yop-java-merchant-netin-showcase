package com.wdc.test.shop.payment;

import com.yeepay.yop.sdk.service.common.YopClient;
import com.yeepay.yop.sdk.service.common.YopClientBuilder;
import com.yeepay.yop.sdk.service.common.request.YopRequest;
import com.yeepay.yop.sdk.service.common.response.YopResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class WechatAuthApplyApiTest {
    private static YopClient client;
    private static final String API_PATH = "/rest/v1.0/mer/merchant/wechatauth/apply";

    @BeforeAll
    public static void setup() {
        client = YopClientBuilder.builder().withProvider("yeepay").withEnv("sandbox").build();
    }

    /**
     * 场景1：正常法人实名认证（企业/个体工商户）
     */
    @Test
    public void testLegalPersonApply_Success() {
        YopRequest request = new YopRequest(API_PATH, "POST");
        request.addParameter("requestNo", "REQ202507040001");
        request.addParameter("subMerchantNo", "10084321321");
        request.addParameter("applicantType", "LEGAL");
        request.addParameter("applicantName", "张三");
        request.addParameter("applicantPhone", "18876543210");
        request.addParameter("applicantIdCard", "220000000000000001");
        request.addParameter("identificationType", "IDENTIFICATION_TYPE_IDCARD");
        request.addParameter("identificationFrontCopy", "front_copy_example");
        request.addParameter("identificationBackCopy", "back_copy_example");
        request.addParameter("identificationValidDate", "[\"1970-01-01\",\"forever\"]");
        request.addParameter("companyAddress", "广东省深圳市南山区xx路xx号xx室");
        request.addParameter("licenceValidDate", "[\"1970-01-01\",\"forever\"]");
        request.addParameter("owner", true);
        request.addParameter("reportFee", "XIANXIA");
        request.addParameter("microBizType", "MICRO_TYPE_STORE");
        request.addParameter("storeName", "大郎烧饼");
        request.addParameter("storeAddressCode", "440305");
        YopResponse response = client.request(request);
        Map<String, Object> result = (Map<String, Object>) response.getResult();
        Assertions.assertEquals("BMS00000", result.get("returnCode"));
    }

    /**
     * 场景2：经办人实名认证
     */
    @Test
    public void testTransactorApply_Success() {
        YopRequest request = new YopRequest(API_PATH, "POST");
        request.addParameter("requestNo", "REQ202507040002");
        request.addParameter("subMerchantNo", "10084321321");
        request.addParameter("applicantType", "TRANSACTOR");
        request.addParameter("applicantName", "李四");
        request.addParameter("applicantPhone", "18876543211");
        request.addParameter("applicantIdCard", "220000000000000002");
        request.addParameter("transactorInfo", "{\"businessAuthorizationLetter\":\"auth_letter\",\"transactorIdentificationBackCopy\":\"back\",\"transactorIdentificationFrontCopy\":\"front\",\"transactorIdentificationType\":\"IDCARD\",\"transactorIdentificationValidDate\":[\"1970-01-01\",\"forever\"]}");
        request.addParameter("identificationType", "IDENTIFICATION_TYPE_IDCARD");
        request.addParameter("identificationFrontCopy", "front_copy_example");
        request.addParameter("identificationBackCopy", "back_copy_example");
        request.addParameter("identificationValidDate", "[\"1970-01-01\",\"forever\"]");
        request.addParameter("companyAddress", "广东省深圳市南山区xx路xx号xx室");
        request.addParameter("licenceValidDate", "[\"1970-01-01\",\"forever\"]");
        request.addParameter("owner", false);
        request.addParameter("reportFee", "XIANXIA");
        request.addParameter("microBizType", "MICRO_TYPE_STORE");
        request.addParameter("storeName", "大郎烧饼");
        request.addParameter("storeAddressCode", "440305");
        YopResponse response = client.request(request);
        Map<String, Object> result = (Map<String, Object>) response.getResult();
        Assertions.assertEquals("BMS00000", result.get("returnCode"));
    }
}
