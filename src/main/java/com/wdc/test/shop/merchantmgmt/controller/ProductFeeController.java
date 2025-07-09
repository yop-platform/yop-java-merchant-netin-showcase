/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.controller;

import com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyRequest;
import com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyResponse;
import com.wdc.test.shop.merchantmgmt.service.ProductFeeService;
import com.wdc.test.shop.merchantmgmt.model.ProductFeeQueryRequest;
import com.wdc.test.shop.merchantmgmt.model.ProductFeeQueryResponse;
import com.wdc.test.shop.merchantmgmt.service.ProductFeeQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * title: 产品费率控制器<br>
 * description: 提供产品费率修改相关接口，分包隔离<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@RestController
@RequestMapping("/merchantmgmt/fee")
public class ProductFeeController {
    private static final Logger logger = LoggerFactory.getLogger(ProductFeeController.class);

    @Autowired
    private ProductFeeService productFeeService;

    @Autowired
    private ProductFeeQueryService productFeeQueryService;

    /**
     * 产品费率修改接口
     * @param request 修改参数
     * @return 修改响应
     */
    @PostMapping("/modify")
    public ProductFeeModifyResponse modifyFee(@RequestBody ProductFeeModifyRequest request) {
        logger.info("[merchantmgmt] 产品费率修改请求: {}", request);
        return productFeeService.modifyFee(request);
    }

    /**
     * 产品费率查询接口
     * @param request 查询参数
     * @return 查询响应
     */
    @PostMapping("/query")
    public ProductFeeQueryResponse queryFee(@RequestBody ProductFeeQueryRequest request) {
        logger.info("[merchantmgmt] 产品费率查询请求: {}", request);
        return productFeeQueryService.queryFee(request);
    }

    /**
     * 产品费率变更进度查询接口
     * @param request 查询参数
     * @return 查询响应
     */
    @PostMapping("/modify/query")
    public com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyQueryResponse queryModifyProgress(@RequestBody com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyQueryRequest request) {
        logger.info("[merchantmgmt] 产品费率变更进度查询请求: {}", request);
        return productFeeQueryService.queryModifyProgress(request);
    }
} 