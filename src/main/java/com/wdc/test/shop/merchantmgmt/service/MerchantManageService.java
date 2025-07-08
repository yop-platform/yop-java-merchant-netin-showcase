/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.service;

import com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyRequest;
import com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyResponse;

/**
 * title: 商户管理服务接口<br>
 * description: 定义商户信息修改主流程方法<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
public interface MerchantManageService {
    /**
     * 商户信息修改
     * @param request 修改参数
     * @return 修改响应
     */
    MerchantInfoModifyResponse modifyInfo(MerchantInfoModifyRequest request);

    /**
     * 商户信息修改进度查询
     * @param request 查询参数
     * @return 查询响应
     */
    com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyQueryResponse queryModifyProgress(com.wdc.test.shop.merchantmgmt.model.MerchantInfoModifyQueryRequest request);
} 