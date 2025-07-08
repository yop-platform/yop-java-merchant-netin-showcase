/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.service;

import com.wdc.test.shop.merchantmgmt.model.MerchantEntryRequest;
import com.wdc.test.shop.merchantmgmt.model.MerchantEntryResponse;

import java.util.List;

/**
 * title: 商户进件服务接口<br>
 * description: 定义进件主流程方法<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
public interface MerchantEntryService {
    /**
     * 商户进件申请
     * @param request 进件参数
     * @return 进件响应
     */
    MerchantEntryResponse apply(MerchantEntryRequest request);

    /**
     * 查询所有进件申请
     */
    List<com.wdc.test.shop.merchantmgmt.model.MerchantEntry> listAll();

    /**
     * 查询单个进件详情
     */
    com.wdc.test.shop.merchantmgmt.model.MerchantEntry getById(Long id);

    /**
     * 商户状态查询
     */
    com.wdc.test.shop.merchantmgmt.model.MerchantStatusQueryResponse queryMerchantStatus(String merchantNo);

    /**
     * 商户入网进度查询
     */
    com.wdc.test.shop.merchantmgmt.model.MerchantRegisterQueryResponse queryRegisterProgress(String requestNo, String merchantNo);

    /**
     * 获取saas商编（parentMerchantNo）配置
     */
    String getSaasMerchantNo();
} 