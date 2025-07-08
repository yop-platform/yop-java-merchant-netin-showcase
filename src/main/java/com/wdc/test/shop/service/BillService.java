package com.wdc.test.shop.service;

import com.wdc.test.shop.model.bill.TradeDayBillDownloadRequest;
import javax.servlet.http.HttpServletResponse;

public interface BillService {
    
    /**
     * 下载交易日对账单
     *
     * @param request 下载请求参数
     * @param response HTTP响应对象，用于写入文件流
     */
    void downloadTradeDayBill(TradeDayBillDownloadRequest request, HttpServletResponse response);
} 