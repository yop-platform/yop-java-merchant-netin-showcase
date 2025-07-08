package com.wdc.test.shop.service.impl;

import com.wdc.test.shop.model.bill.TradeDayBillDownloadRequest;
import com.wdc.test.shop.service.BillService;
import com.yeepay.yop.sdk.model.yos.YosDownloadInputStream;
import com.yeepay.yop.sdk.model.yos.YosDownloadResponse;
import com.yeepay.yop.sdk.service.common.YopClient;
import com.yeepay.yop.sdk.service.common.YopClientBuilder;
import com.yeepay.yop.sdk.service.common.request.YopRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class BillServiceImpl implements BillService {

    private static final YopClient yopClient = YopClientBuilder.builder().build();

    @Override
    public void downloadTradeDayBill(TradeDayBillDownloadRequest request, HttpServletResponse response) {
        log.info("开始下载交易日对账单，请求参数：{}", request);
        
        try {
            YopRequest yopRequest = new YopRequest("/yos/v1.0/std/bill/tradedaydownload", "GET");
            yopRequest.addParameter("merchantNo", request.getMerchantNo());
            yopRequest.addParameter("dayString", request.getDayString());
            
            if (StringUtils.isNotBlank(request.getDataType())) {
                yopRequest.addParameter("dataType", request.getDataType());
            }

            YosDownloadResponse downloadResponse = yopClient.download(yopRequest);
            
            // 设置响应头
            String fileName = String.format("trade_bill_%s_%s.csv", request.getMerchantNo(), request.getDayString());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
            
            // 写入文件流
            try (YosDownloadInputStream inputStream = downloadResponse.getResult()) {
                StreamUtils.copy(inputStream, response.getOutputStream());
                response.flushBuffer();
                log.info("交易日对账单下载成功，商户号：{}，日期：{}", request.getMerchantNo(), request.getDayString());
            }
            
        } catch (Exception e) {
            log.error("下载交易日对账单失败，商户号：{}，日期：{}，错误信息：{}", 
                request.getMerchantNo(), request.getDayString(), e.getMessage(), e);
            try {
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().write("下载失败：" + e.getMessage());
            } catch (IOException ex) {
                log.error("写入错误响应失败", ex);
            }
        }
    }
} 