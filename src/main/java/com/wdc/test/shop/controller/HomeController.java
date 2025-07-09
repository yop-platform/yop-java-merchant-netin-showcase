package com.wdc.test.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/payment/mcp-merchant-register-apply")
    public String mcpMerchantRegisterApply() {
        return "/payment/mcp-merchant-register-apply";
    }
}