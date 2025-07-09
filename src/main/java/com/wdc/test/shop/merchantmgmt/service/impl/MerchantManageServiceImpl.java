/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.service.impl;

import com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyRequest;
import com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyResponse;
import com.wdc.test.shop.merchantmgmt.payment.yop.YopMerchantManageClient;
import com.wdc.test.shop.merchantmgmt.service.MerchantManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * title: 商户管理服务实现<br>
 * description: 实现商户信息修改主流程，调用YOP Client，处理异常与日志<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@Service
public class MerchantManageServiceImpl implements MerchantManageService {
    private static final Logger logger = LoggerFactory.getLogger(MerchantManageServiceImpl.class);

    @Autowired
    private YopMerchantManageClient yopMerchantManageClient;
    
    /**
     * 商户信息修改进度查询
     */
    @Override
    public com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyQueryResponse queryModifyProgress(com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyQueryRequest request) {
        logger.info("[merchantmgmt] 商户信息修改进度查询服务调用YOP，参数: {}", request);
        try {
            return yopMerchantManageClient.queryModifyProgress(request);
        } catch (Exception e) {
            logger.error("[merchantmgmt] 商户信息修改进度查询服务异常", e);
            com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyQueryResponse resp = new com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyQueryResponse();
            resp.setReturnCode("ERROR");
            resp.setReturnMsg("商户信息修改进度查询异常：" + e.getMessage());
            return resp;
        }
    }

    @Override
    public MerchantInfoModifyResponse modifyInfo(MerchantInfoModifyRequest request) {
        logger.info("[merchantmgmt] 商户信息修改服务调用YOP，参数: {}", request);
        try {
            return yopMerchantManageClient.modifyInfo(request);
        } catch (Exception e) {
            logger.error("[merchantmgmt] 商户信息修改服务异常", e);
            MerchantInfoModifyResponse resp = new MerchantInfoModifyResponse();
            resp.setReturnCode("ERROR");
            resp.setReturnMsg("商户信息修改异常：" + e.getMessage());
            return resp;
        }
    }
} 