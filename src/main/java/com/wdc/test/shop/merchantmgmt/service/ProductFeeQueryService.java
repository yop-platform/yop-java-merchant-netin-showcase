/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.service;

import com.wdc.test.shop.merchantmgmt.model.ProductFeeQueryRequest;
import com.wdc.test.shop.merchantmgmt.model.ProductFeeQueryResponse;

/**
 * title: 产品费率查询服务接口<br>
 * description: 定义产品费率查询主流程方法<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
public interface ProductFeeQueryService {
    /**
     * 产品费率查询
     * @param request 查询参数
     * @return 查询响应
     */
    ProductFeeQueryResponse queryFee(ProductFeeQueryRequest request);

    /**
     * 产品费率变更进度查询
     * @param request 查询参数
     * @return 查询响应
     */
    com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyQueryResponse queryModifyProgress(com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyQueryRequest request);
} 