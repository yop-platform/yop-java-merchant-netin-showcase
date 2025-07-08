/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.service.impl;

import com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyRequest;
import com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyResponse;
import com.wdc.test.shop.merchantmgmt.payment.yop.YopProductFeeClient;
import com.wdc.test.shop.merchantmgmt.service.ProductFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * title: 产品费率服务实现<br>
 * description: 实现产品费率修改主流程，调用YOP Client，处理异常与日志<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@Service
public class ProductFeeServiceImpl implements ProductFeeService {
    private static final Logger logger = LoggerFactory.getLogger(ProductFeeServiceImpl.class);

    @Autowired
    private YopProductFeeClient yopProductFeeClient;

    @Override
    public ProductFeeModifyResponse modifyFee(ProductFeeModifyRequest request) {
        logger.info("[merchantmgmt] 产品费率修改服务调用YOP，参数: {}", request);
        try {
            return yopProductFeeClient.modifyFee(request);
        } catch (Exception e) {
            logger.error("[merchantmgmt] 产品费率修改服务异常", e);
            ProductFeeModifyResponse resp = new ProductFeeModifyResponse();
            resp.setReturnCode("ERROR");
            resp.setReturnMsg("产品费率修改异常：" + e.getMessage());
            return resp;
        }
    }
} 