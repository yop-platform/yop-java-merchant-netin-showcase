package com.wdc.test.shop.controller;

import com.wdc.test.shop.model.bill.TradeDayBillDownloadRequest;
import com.wdc.test.shop.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping("/download-page")
    public ModelAndView downloadPage() {
        return new ModelAndView("bill/download");
    }

    @GetMapping("/download")
    public void download(@Valid TradeDayBillDownloadRequest request, HttpServletResponse response) {
        billService.downloadTradeDayBill(request, response);
    }
} 