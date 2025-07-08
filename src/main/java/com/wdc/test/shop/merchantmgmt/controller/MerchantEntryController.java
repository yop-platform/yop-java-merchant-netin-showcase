/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.controller;

import com.wdc.test.shop.merchantmgmt.model.MerchantEntryRequest;
import com.wdc.test.shop.merchantmgmt.model.MerchantEntryResponse;
import com.wdc.test.shop.merchantmgmt.model.MerchantStatusQueryResponse;
import com.wdc.test.shop.merchantmgmt.model.MerchantRegisterQueryResponse;
import com.wdc.test.shop.merchantmgmt.service.MerchantEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * title: 商户进件控制器<br>
 * description: 提供商户进件相关接口，分包隔离<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@RestController
@RequestMapping("/merchantmgmt/entry")
public class MerchantEntryController {
    private static final Logger logger = LoggerFactory.getLogger(MerchantEntryController.class);

    @Autowired
    private MerchantEntryService merchantEntryService;

    /**
     * 商户进件申请接口
     * @param request 进件参数
     * @return 进件响应
     */
    @PostMapping("/apply")
    public MerchantEntryResponse apply(@RequestBody MerchantEntryRequest request) {
        logger.info("[merchantmgmt] 进件申请请求: {}", request);
        return merchantEntryService.apply(request);
    }

    /**
     * 查询所有进件申请
     */
    @GetMapping("/list")
    public java.util.List<com.wdc.test.shop.merchantmgmt.model.MerchantEntry> listAll() {
        logger.info("[merchantmgmt] 查询进件列表");
        return merchantEntryService.listAll();
    }

    /**
     * 查询单个进件详情
     */
    @GetMapping("/detail/{id}")
    public com.wdc.test.shop.merchantmgmt.model.MerchantEntry detail(@PathVariable Long id) {
        logger.info("[merchantmgmt] 查询进件详情, id={}", id);
        return merchantEntryService.getById(id);
    }

    /**
     * 商户状态查询接口
     */
    @GetMapping("/status")
    public MerchantStatusQueryResponse queryStatus(@RequestParam String merchantNo) {
        logger.info("[merchantmgmt] 商户状态查询, merchantNo={}", merchantNo);
        return merchantEntryService.queryMerchantStatus(merchantNo);
    }

    /**
     * 商户入网进度查询接口
     */
    @GetMapping("/registerProgress")
    public MerchantRegisterQueryResponse queryRegisterProgress(@RequestParam(required = false) String requestNo, @RequestParam(required = false) String merchantNo) {
        logger.info("[merchantmgmt] 商户入网进度查询, requestNo={}, merchantNo={}", requestNo, merchantNo);
        return merchantEntryService.queryRegisterProgress(requestNo, merchantNo);
    }

    /**
     * 获取saas商编（parentMerchantNo）配置
     */
    @GetMapping("/saasMerchantNo")
    public String getSaasMerchantNo() {
        // 通过YeepayConfig获取
        return merchantEntryService.getSaasMerchantNo();
    }
} 