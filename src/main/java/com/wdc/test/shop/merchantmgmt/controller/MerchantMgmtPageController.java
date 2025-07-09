/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * title: 商户入网页面跳转控制器<br>
 * description: 负责转发到商户入网相关JSP页面，便于前端入口统一管理和测试<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/12
 */
@Controller
public class MerchantMgmtPageController {
    @GetMapping("/merchantmgmt/entry/apply")
    public String entryApplyPage() { return "merchantmgmt/entry_apply"; }

    @GetMapping("/merchantmgmt/entry/listPage")
    public String entryListPage() { return "merchantmgmt/entry_list"; }

    @GetMapping("/merchantmgmt/file/uploadPage")
    public String fileUploadPage() { return "merchantmgmt/file_upload"; }

    @GetMapping("/merchantmgmt/manage/modify")
    public String manageModifyPage() { return "merchantmgmt/manage_modify"; }

    @GetMapping("/merchantmgmt/fee/modify")
    public String feeModifyPage() { return "merchantmgmt/fee_modify"; }

    @GetMapping("/merchantmgmt/entry/detail")
    public String entryDetailPage() { return "merchantmgmt/entry_detail"; }
} 