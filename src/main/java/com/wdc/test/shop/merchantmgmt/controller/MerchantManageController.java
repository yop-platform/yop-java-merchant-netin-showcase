/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.controller;

import com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyRequest;
import com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyResponse;
import com.wdc.test.shop.merchantmgmt.service.MerchantManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * title: 商户管理控制器<br>
 * description: 提供商户信息修改相关接口，分包隔离<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@RestController
@RequestMapping("/merchantmgmt/manage")
public class MerchantManageController {
    private static final Logger logger = LoggerFactory.getLogger(MerchantManageController.class);

    @Autowired
    private MerchantManageService merchantManageService;

    /**
     * 商户信息修改接口
     * @param request 修改参数
     * @return 修改响应
     */
    @PostMapping("/info/modify")
    public MerchantInfoModifyResponse modifyInfo(@RequestBody MerchantInfoModifyRequest request) {
        logger.info("[merchantmgmt] 商户信息修改请求: {}", request);
        return merchantManageService.modifyInfo(request);
    }

    /**
     * 商户信息修改进度查询接口
     * @param request 查询参数
     * @return 查询响应
     */
    @PostMapping("/info/modify/query")
    public com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyQueryResponse queryModifyProgress(@RequestBody com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyQueryRequest request) {
        logger.info("[merchantmgmt] 商户信息修改进度查询请求: {}", request);
        return merchantManageService.queryModifyProgress(request);
    }
} 