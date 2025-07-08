<%--
  Copyright: Copyright (c)2014
  Company: 易宝支付(YeePay)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>进件详情 - 商户管理</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h3>进件详情（merchantmgmt独立模块）</h3>
    <table class="table table-bordered">
        <tbody id="entryDetailBody"></tbody>
    </table>
    <a href="/merchantmgmt/entry/listPage" class="btn btn-secondary">返回列表</a>
    <a href="#" id="modifyInfoBtn" class="btn btn-warning ms-2">信息修改</a>
    <a href="#" id="modifyFeeBtn" class="btn btn-info ms-2">费率修改</a>
    <div class="mt-4">
        <h5>商户入网进度查询</h5>
        <div class="input-group mb-2" style="max-width: 600px;">
            <input type="text" class="form-control" id="registerRequestNo" placeholder="请输入入网请求号">
            <input type="text" class="form-control" id="registerMerchantNo" placeholder="请输入商户编号">
            <button class="btn btn-primary" id="queryRegisterProgressBtn">查询</button>
        </div>
        <div id="registerProgressResult" class="mb-3"></div>
        <h5>商户状态查询</h5>
        <div class="input-group mb-2" style="max-width: 400px;">
            <input type="text" class="form-control" id="merchantNoForStatus" placeholder="请输入商户编号">
            <button class="btn btn-primary" id="queryMerchantStatusBtn">查询</button>
        </div>
        <div id="merchantStatusResult" class="mb-3"></div>
        <h5>商户信息修改进度查询</h5>
        <div class="input-group mb-2" style="max-width: 400px;">
            <input type="text" class="form-control" id="modifyRequestNo" placeholder="请输入信息修改请求号">
            <button class="btn btn-primary" id="queryModifyProgressBtn">查询</button>
        </div>
        <div id="modifyProgressResult" class="mb-3"></div>
        <h5>产品费率变更进度查询</h5>
        <div class="input-group mb-2" style="max-width: 400px;">
            <input type="text" class="form-control" id="feeModifyRequestNo" placeholder="请输入产品变更请求号">
            <button class="btn btn-primary" id="queryFeeModifyProgressBtn">查询</button>
        </div>
        <div id="feeModifyProgressResult"></div>
        <h5>产品费率查询</h5>
        <div class="input-group mb-2" style="max-width: 600px;">
            <input type="text" class="form-control" id="queryMerchantNo" placeholder="请输入商户编号">
            <input type="text" class="form-control" id="queryProductCode" placeholder="请输入产品编码">
            <input type="text" class="form-control" id="queryParentMerchantNo" placeholder="请输入上级商户编号" value="10080662589">
            <button class="btn btn-info" id="queryFeeBtn">查询</button>
        </div>
        <div id="feeQueryResult" class="mb-3"></div>
    </div>
</div>
<script src="/static/js/jquery.min.js"></script>
<script>
function renderDetail(entry) {
    var html = '';
    for (var key in entry) {
        if (entry.hasOwnProperty(key)) {
            html += '<tr><th>' + key + '</th><td>' + (entry[key]||'') + '</td></tr>';
        }
    }
    $('#entryDetailBody').html(html);
    // 记录商户编号
    window._merchantNo = entry.merchantNo || '';
    $('#merchantNoForStatus').val(window._merchantNo);
}
$(function(){
    var id = new URLSearchParams(window.location.search).get('id');
    if (!id) { $('#entryDetailBody').html('<tr><td colspan="2">参数缺失</td></tr>'); return; }
    $.get('/merchantmgmt/entry/detail/' + id, function(entry){
        renderDetail(entry);
        $('#registerRequestNo').val(entry.requestNo||'');
        $('#registerMerchantNo').val(entry.merchantNo||'');
        $('#queryMerchantNo').val(entry.merchantNo||'');
        // 动态获取saas商编
        $.get('/merchantmgmt/entry/saasMerchantNo', function(saasNo){
            $('#queryParentMerchantNo').val(saasNo);
            $('#queryParentMerchantNo').data('saasNo', saasNo);
        });
    });
    // 信息修改按钮跳转
    $('#modifyInfoBtn').click(function(e){
        e.preventDefault();
        var id = new URLSearchParams(window.location.search).get('id');
        if (id) {
            window.location.href = '/merchantmgmt/manage/modify?id=' + encodeURIComponent(id);
        } else {
            alert('未获取到进件ID，无法跳转');
        }
    });
    // 费率修改按钮跳转
    $('#modifyFeeBtn').click(function(e){
        e.preventDefault();
        var id = new URLSearchParams(window.location.search).get('id');
        if (window._merchantNo && id) {
            window.location.href = '/merchantmgmt/fee/modify?id=' + encodeURIComponent(id) + '&merchantNo=' + encodeURIComponent(window._merchantNo);
        } else {
            alert('未获取到商户编号或进件ID，无法跳转');
        }
    });
});
$('#queryMerchantStatusBtn').click(function(){
    var merchantNo = $('#merchantNoForStatus').val().trim();
    if (!merchantNo) { $('#merchantStatusResult').html('<span class="text-danger">请输入商户编号</span>'); return; }
    $('#merchantStatusResult').html('查询中...');
    $.get('/merchantmgmt/entry/status', {merchantNo: merchantNo}, function(res){
        if(res.returnCode === 'NIG00000') {
            var html = '<pre>' + JSON.stringify(res, null, 2) + '</pre>';
            $('#merchantStatusResult').html(html);
        } else {
            $('#merchantStatusResult').html('<span class="text-danger">' + (res.returnMsg||'查询失败') + '</span>');
        }
    }).fail(function(){ $('#merchantStatusResult').html('<span class="text-danger">请求失败</span>'); });
});
$('#queryModifyProgressBtn').click(function(){
    var reqNo = $('#modifyRequestNo').val().trim();
    if (!reqNo) { $('#modifyProgressResult').html('<span class="text-danger">请输入请求号</span>'); return; }
    $('#modifyProgressResult').html('查询中...');
    $.ajax({
        url: '/merchantmgmt/manage/info/modify/query',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({requestNo: reqNo}),
        success: function(res) {
            if(res.returnCode === 'NIG00000') {
                var html = '<pre>' + JSON.stringify(res, null, 2) + '</pre>';
                $('#modifyProgressResult').html(html);
            } else {
                $('#modifyProgressResult').html('<span class="text-danger">' + (res.returnMsg||'查询失败') + '</span>');
            }
        },
        error: function(){ $('#modifyProgressResult').html('<span class="text-danger">请求失败</span>'); }
    });
});
$('#queryFeeModifyProgressBtn').click(function(){
    var reqNo = $('#feeModifyRequestNo').val().trim();
    if (!reqNo) { $('#feeModifyProgressResult').html('<span class="text-danger">请输入请求号</span>'); return; }
    $('#feeModifyProgressResult').html('查询中...');
    $.ajax({
        url: '/merchantmgmt/fee/modify/query',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({requestNo: reqNo}),
        success: function(res) {
            if(res.returnCode === 'NIG00000') {
                var html = '<pre>' + JSON.stringify(res, null, 2) + '</pre>';
                $('#feeModifyProgressResult').html(html);
            } else {
                $('#feeModifyProgressResult').html('<span class="text-danger">' + (res.returnMsg||'查询失败') + '</span>');
            }
        },
        error: function(){ $('#feeModifyProgressResult').html('<span class="text-danger">请求失败</span>'); }
    });
});
$('#queryRegisterProgressBtn').click(function(){
    var reqNo = $('#registerRequestNo').val().trim();
    var merchantNo = $('#registerMerchantNo').val().trim();
    if (!reqNo && !merchantNo) { $('#registerProgressResult').html('<span class="text-danger">请输入入网请求号或商户编号</span>'); return; }
    $('#registerProgressResult').html('查询中...');
    $.get('/merchantmgmt/entry/registerProgress', {requestNo: reqNo, merchantNo: merchantNo}, function(res){
        if(res.returnCode === 'NIG00000') {
            var html = '<pre>' + JSON.stringify(res, null, 2) + '</pre>';
            $('#registerProgressResult').html(html);
        } else {
            $('#registerProgressResult').html('<span class="text-danger">' + (res.returnMsg||'查询失败') + '</span>');
        }
    }).fail(function(){ $('#registerProgressResult').html('<span class="text-danger">请求失败</span>'); });
});
$('#queryFeeBtn').click(function(){
    var merchantNo = $('#queryMerchantNo').val().trim();
    var productCode = $('#queryProductCode').val().trim();
    var parentMerchantNo = $('#queryParentMerchantNo').data('saasNo') || $('#queryParentMerchantNo').val().trim();
    if (!merchantNo || !productCode) {
        $('#feeQueryResult').html('<span class="text-danger">请输入商户编号和产品编码</span>');
        return;
    }
    $('#feeQueryResult').html('查询中...');
    $.ajax({
        url: '/merchantmgmt/fee/query',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({merchantNo: merchantNo, productCode: productCode, parentMerchantNo: parentMerchantNo}),
        success: function(res) {
            var html = '<pre>' + JSON.stringify(res, null, 2) + '</pre>';
            $('#feeQueryResult').html(html);
        },
        error: function(){ $('#feeQueryResult').html('<span class="text-danger">请求失败</span>'); }
    });
});
</script>
</body>
</html> 