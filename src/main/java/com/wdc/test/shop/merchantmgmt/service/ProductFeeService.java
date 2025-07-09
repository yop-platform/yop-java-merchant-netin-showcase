/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.service;

import com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyRequest;
import com.wdc.test.shop.merchantmgmt.model.ProductFeeModifyResponse;

/**
 * title: 产品费率服务接口<br>
 * description: 定义产品费率修改主流程方法<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
public interface ProductFeeService {
    /**
     * 产品费率修改
     * @param request 修改参数
     * @return 修改响应
     */
    ProductFeeModifyResponse modifyFee(ProductFeeModifyRequest request);
} 