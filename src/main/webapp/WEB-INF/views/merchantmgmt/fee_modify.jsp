<%--
  Copyright: Copyright (c)2014
  Company: 易宝支付(YeePay)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>产品费率修改 - 商户管理</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h3>产品费率修改</h3>
    <form id="feeModifyForm">
        <input type="hidden" name="requestNo" id="requestNo">
        <input type="hidden" name="notifyUrl" id="notifyUrl" value="https://notify.merchant.com/xxx">
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">商户编号 <span class="text-danger">*</span></label>
                <input type="text" class="form-control" name="merchantNo" id="merchantNo" required>
            </div>
        </div>
        <div class="mb-3">
            <label class="form-label">变更产品信息（JSON）<span class="text-danger">*</span></label>
            <textarea class="form-control" name="productInfo" id="productInfo" rows="4" required>[{"productCode":"MERCHANT_SCAN_ALIPAY_OFFLINE","rateType":"SINGLE_PERCENT","percentRate":"0.1"}]</textarea>
            <small class="form-text text-muted">示例: [{"productCode":"MERCHANT_SCAN_ALIPAY_OFFLINE","rateType":"SINGLE_PERCENT","percentRate":"0.1"}]</small>
        </div>
        <div class="mb-3">
            <label class="form-label">额外信息</label>
            <textarea class="form-control" name="extraInfo" id="extraInfo" rows="2"></textarea>
        </div>
        <button type="submit" class="btn btn-primary">提交修改</button>
        <a href="/merchantmgmt/entry/listPage" class="btn btn-secondary ms-2">返回列表</a>
    </form>
    <div id="feeModifyResult" class="mt-3"></div>
    <div class="mt-4">
        <h5>产品费率查询</h5>
        <div class="input-group mb-2" style="max-width: 600px;">
            <input type="text" class="form-control" id="queryMerchantNo" placeholder="请输入商户编号">
            <input type="text" class="form-control" id="queryProductCode" placeholder="请输入产品编码">
            <button class="btn btn-info" id="queryFeeBtn">查询</button>
        </div>
        <div id="feeQueryResult" class="mb-3"></div>
    </div>
</div>
<script src="/static/js/jquery.min.js"></script>
<script>
// 获取URL参数
function getQueryParam(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURIComponent(r[2]);
    return '';
}
// 页面加载时填充商户编号
$(function(){
    function genRequestNo() {
        return 'FEE' + Date.now() + Math.floor(Math.random()*1000);
    }
    $('#requestNo').val(genRequestNo());
    $('#notifyUrl').val('https://notify.merchant.com/xxx');
    var merchantNo = getQueryParam('merchantNo');
    var id = getQueryParam('id');
    if (id) {
        $.get('/merchantmgmt/entry/detail/' + id, function(entry){
            if(entry) {
                $('#merchantNo').val(entry.merchantNo||'');
            }
        });
    } else if (merchantNo) {
        $('#merchantNo').val(merchantNo);
    }
    $('#feeModifyForm').on('submit', function(e) {
        e.preventDefault();
        $('#requestNo').val(genRequestNo());
        if(!$('#merchantNo').val().trim() || !$('#productInfo').val().trim()){
            $('#feeModifyResult').html('<span class="text-danger">请填写所有必填项</span>');
            return;
        }
        var data = {
            requestNo: $('#requestNo').val(),
            merchantNo: $('#merchantNo').val(),
            notifyUrl: $('#notifyUrl').val(),
            productInfo: $('#productInfo').val(),
            extraInfo: $('#extraInfo').val()
        };
        $('#feeModifyResult').html('提交中...');
        $.ajax({
            url: '/merchantmgmt/fee/modify',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(res) {
                if(res.returnCode === 'NIG00000') {
                    $('#feeModifyResult').html('<span class="text-success">修改成功</span>');
                } else {
                    $('#feeModifyResult').html('<span class="text-danger">' + (res.returnMsg||'修改失败') + '</span>');
                }
            },
            error: function(){ $('#feeModifyResult').html('<span class="text-danger">请求失败</span>'); }
        });
    });
    $('#queryFeeBtn').click(function(){
        var merchantNo = $('#queryMerchantNo').val().trim();
        var productCode = $('#queryProductCode').val().trim();
        if (!merchantNo || !productCode) {
            $('#feeQueryResult').html('<span class="text-danger">请输入商户编号和产品编码</span>');
            return;
        }
        $('#feeQueryResult').html('查询中...');
        $.ajax({
            url: '/merchantmgmt/fee/query',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({merchantNo: merchantNo, productCode: productCode}),
            success: function(res) {
                var html = '<pre>' + JSON.stringify(res, null, 2) + '</pre>';
                $('#feeQueryResult').html(html);
            },
            error: function(){ $('#feeQueryResult').html('<span class="text-danger">请求失败</span>'); }
        });
    });
});
</script>
</body>
</html> 