package com.wdc.test.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdc.test.shop.payment.yeepay.YeepayPayRequest;
import com.wdc.test.shop.payment.yeepay.YeepayPayResponse;
import com.wdc.test.shop.payment.yeepay.YeepayPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("/pay/test")
@RequiredArgsConstructor
public class PayTestController {
    private final YeepayPayService yeepayPayService;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @GetMapping("/pay")
    public String payForm() {
        return "payment/pay_test";
    }

    @PostMapping("/pay")
    public String doPay(
            @RequestParam String merchantNo,
            @RequestParam String orderId,
            @RequestParam BigDecimal orderAmount,
            @RequestParam String payWay,
            @RequestParam(required = false) String channel,
            @RequestParam String authCode,
            @RequestParam String userIp,
            @RequestParam String terminalId,
            @RequestParam(required = false) String goodsName,
            @RequestParam(required = false) String notifyUrl,
            Model model
    ) {
        YeepayPayRequest req = YeepayPayRequest.builder()
                .merchantNo(merchantNo)
                .orderId(orderId)
                .orderAmount(orderAmount)
                .payWay(payWay)
                .channel(channel)
                .authCode(authCode)
                .userIp(userIp)
                .terminalId(terminalId)
                .goodsName(goodsName)
                .notifyUrl(notifyUrl)
                .build();
        YeepayPayResponse resp = yeepayPayService.pay(req);
        try {
            model.addAttribute("payResult", OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(resp));
        } catch (Exception e) {
            model.addAttribute("payResult", resp.toString());
        }
        return "payment/pay_test";
    }
} 