/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.service.impl;

import com.wdc.test.shop.merchantmgmt.model.ProductFeeQueryRequest;
import com.wdc.test.shop.merchantmgmt.model.ProductFeeQueryResponse;
import com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyQueryRequest;
import com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyQueryResponse;
import com.wdc.test.shop.merchantmgmt.payment.yop.YopProductFeeQueryClient;
import com.wdc.test.shop.merchantmgmt.service.ProductFeeQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * title: 产品费率查询服务实现<br>
 * description: 实现产品费率查询主流程，调用YOP Client，处理异常与日志<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@Service
public class ProductFeeQueryServiceImpl implements ProductFeeQueryService {
    private static final Logger logger = LoggerFactory.getLogger(ProductFeeQueryServiceImpl.class);

    @Autowired
    private YopProductFeeQueryClient yopProductFeeQueryClient;

    @Override
    public ProductFeeQueryResponse queryFee(ProductFeeQueryRequest request) {
        logger.info("[merchantmgmt] 产品费率查询服务调用YOP，参数: {}", request);
        try {
            return yopProductFeeQueryClient.queryFee(request);
        } catch (Exception e) {
            logger.error("[merchantmgmt] 产品费率查询服务异常", e);
            ProductFeeQueryResponse resp = new ProductFeeQueryResponse();
            resp.setReturnCode("ERROR");
            resp.setReturnMsg("产品费率查询异常：" + e.getMessage());
            return resp;
        }
    }

    /**
     * 产品费率变更进度查询
     */
    @Override
    public ProductFeeModifyQueryResponse queryModifyProgress(ProductFeeModifyQueryRequest request) {
        logger.info("[merchantmgmt] 产品费率变更进度查询服务调用YOP，参数: {}", request);
        try {
            return yopProductFeeQueryClient.queryModifyProgress(request);
        } catch (Exception e) {
            logger.error("[merchantmgmt] 产品费率变更进度查询服务异常", e);
            ProductFeeModifyQueryResponse resp = new ProductFeeModifyQueryResponse();
            resp.setReturnCode("ERROR");
            resp.setReturnMsg("产品费率变更进度查询异常：" + e.getMessage());
            return resp;
        }
    }
} 