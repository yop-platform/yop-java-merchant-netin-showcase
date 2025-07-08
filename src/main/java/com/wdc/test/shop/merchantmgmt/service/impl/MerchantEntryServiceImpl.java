/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.service.impl;

import com.wdc.test.shop.merchantmgmt.model.MerchantEntryRequest;
import com.wdc.test.shop.merchantmgmt.model.MerchantEntryResponse;
import com.wdc.test.shop.merchantmgmt.payment.yop.YopMerchantEntryClient;
import com.wdc.test.shop.merchantmgmt.service.MerchantEntryService;
import com.wdc.test.shop.merchantmgmt.model.MerchantEntry;
import com.wdc.test.shop.merchantmgmt.repository.MerchantEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * title: 商户进件服务实现<br>
 * description: 实现进件主流程，调用YOP Client，处理异常与日志<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@Service
public class MerchantEntryServiceImpl implements MerchantEntryService {
    private static final Logger logger = LoggerFactory.getLogger(MerchantEntryServiceImpl.class);

    @Autowired
    private YopMerchantEntryClient yopMerchantEntryClient;

    @Autowired
    private MerchantEntryRepository merchantEntryRepository;

    @Override
    public MerchantEntryResponse apply(MerchantEntryRequest request) {
        logger.info("[merchantmgmt] 进件服务调用YOP，参数: {}", request);
        try {
            MerchantEntryResponse resp = yopMerchantEntryClient.apply(request);
            // 保存进件信息
            MerchantEntry entry = new MerchantEntry();
            entry.setRequestNo(request.getRequestNo());
            entry.setSignName(request.getSignName());
            entry.setShortName(request.getShortName());
            entry.setLicenceUrl(request.getLicenceUrl());
            entry.setLicenceNo(request.getLicenceNo());
            entry.setLegalName(request.getLegalName());
            entry.setLegalLicenceType(request.getLegalLicenceType());
            entry.setLegalLicenceNo(request.getLegalLicenceNo());
            entry.setLegalLicenceFrontUrl(request.getLegalLicenceFrontUrl());
            entry.setLegalLicenceBackUrl(request.getLegalLicenceBackUrl());
            entry.setContactName(request.getContactName());
            entry.setContactMobile(request.getContactMobile());
            entry.setContactEmail(request.getContactEmail());
            entry.setAdminEmail(request.getAdminEmail());
            entry.setAdminMobile(request.getAdminMobile());
            entry.setPrimaryIndustryCategory(request.getPrimaryIndustryCategory());
            entry.setSecondaryIndustryCategory(request.getSecondaryIndustryCategory());
            entry.setProvince(request.getProvince());
            entry.setCity(request.getCity());
            entry.setDistrict(request.getDistrict());
            entry.setAddress(request.getAddress());
            entry.setSettlementDirection(request.getSettlementDirection());
            entry.setBankCode(request.getBankCode());
            entry.setBankAccountType(request.getBankAccountType());
            entry.setBankCardNo(request.getBankCardNo());
            entry.setProductInfo(request.getProductInfo());
            entry.setExtraInfo(request.getExtraInfo());
            entry.setApplicationNo(resp.getApplicationNo());
            entry.setMerchantNo(resp.getMerchantNo());
            entry.setApplicationStatus(resp.getApplicationStatus());
            entry.setReturnMsg(resp.getReturnMsg());
            merchantEntryRepository.save(entry);
            return resp;
        } catch (Exception e) {
            logger.error("[merchantmgmt] 进件服务异常", e);
            MerchantEntryResponse resp = new MerchantEntryResponse();
            resp.setReturnCode("ERROR");
            resp.setReturnMsg("进件异常：" + e.getMessage());
            return resp;
        }
    }

    @Override
    public java.util.List<MerchantEntry> listAll() {
        return merchantEntryRepository.findAll();
    }

    @Override
    public MerchantEntry getById(Long id) {
        return merchantEntryRepository.findById(id).orElse(null);
    }

    @Override
    public com.wdc.test.shop.merchantmgmt.model.MerchantStatusQueryResponse queryMerchantStatus(String merchantNo) {
        logger.info("[merchantmgmt] 商户状态查询服务调用YOP，merchantNo: {}", merchantNo);
        try {
            return yopMerchantEntryClient.queryMerchantStatus(merchantNo);
        } catch (Exception e) {
            logger.error("[merchantmgmt] 商户状态查询服务异常", e);
            com.wdc.test.shop.merchantmgmt.model.MerchantStatusQueryResponse resp = new com.wdc.test.shop.merchantmgmt.model.MerchantStatusQueryResponse();
            resp.setReturnCode("ERROR");
            resp.setReturnMsg("商户状态查询异常：" + e.getMessage());
            return resp;
        }
    }

    @Override
    public com.wdc.test.shop.merchantmgmt.model.MerchantRegisterQueryResponse queryRegisterProgress(String requestNo, String merchantNo) {
        logger.info("[merchantmgmt] 商户入网进度查询服务调用YOP，requestNo: {}, merchantNo: {}", requestNo, merchantNo);
        try {
            return yopMerchantEntryClient.queryRegisterProgress(requestNo, merchantNo);
        } catch (Exception e) {
            logger.error("[merchantmgmt] 商户入网进度查询服务异常", e);
            com.wdc.test.shop.merchantmgmt.model.MerchantRegisterQueryResponse resp = new com.wdc.test.shop.merchantmgmt.model.MerchantRegisterQueryResponse();
            resp.setReturnCode("ERROR");
            resp.setReturnMsg("商户入网进度查询异常：" + e.getMessage());
            return resp;
        }
    }

    @Override
    public String getSaasMerchantNo() {
        return yopMerchantEntryClient.getSaasMerchantNo();
    }
} 