package com.wdc.test.shop.model.bill;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class TradeDayBillDownloadRequest {
    
    /**
     * 商户编号
     * 标准商户:标准商户编号
     * 平台商:平台商商户编号, 文件包含平台商及平台商下所有入驻商户的交易信息
     */
    @NotBlank(message = "商户编号不能为空")
    private String merchantNo;

    /**
     * 日期，格式：yyyy-MM-dd
     */
    @NotBlank(message = "日期不能为空")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "日期格式不正确，应为yyyy-MM-dd")
    private String dayString;

    /**
     * 数据类型：
     * merchant:商户对账文件
     * platform:服务商对账文件，平台商对账文件
     */
    private String dataType;
} 