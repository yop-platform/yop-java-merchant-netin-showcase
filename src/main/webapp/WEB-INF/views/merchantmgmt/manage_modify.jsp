<%--
  Copyright: Copyright (c)2014
  Company: 易宝支付(YeePay)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>商户信息修改 - 商户管理</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h3>商户信息修改</h3>
    <form id="modifyForm">
        <input type="hidden" name="requestNo" id="requestNo">
        <input type="hidden" name="notifyUrl" id="notifyUrl" value="https://notify.merchant.com/xxx">
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">商户编号 <span class="text-danger">*</span></label>
                <input type="text" class="form-control" name="merchantNo" id="merchantNo" required>
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">商户签约名 <span class="text-danger">*</span></label>
                <input type="text" class="form-control" name="signName" id="signName" required>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">商户简称 <span class="text-danger">*</span></label>
                <input type="text" class="form-control" name="shortName" id="shortName" required>
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">联系人姓名 <span class="text-danger">*</span></label>
                <input type="text" class="form-control" name="contactName" id="contactName" required>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">联系人手机号 <span class="text-danger">*</span></label>
                <input type="text" class="form-control" name="contactMobile" id="contactMobile" required>
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">联系人邮箱 <span class="text-danger">*</span></label>
                <input type="email" class="form-control" name="contactEmail" id="contactEmail" required>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">管理员邮箱</label>
                <input type="email" class="form-control" name="adminEmail" id="adminEmail">
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">管理员手机号</label>
                <input type="text" class="form-control" name="adminMobile" id="adminMobile">
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 mb-3">
                <label class="form-label">经营地址 <span class="text-danger">*</span></label>
                <input type="text" class="form-control" name="address" id="address" required>
            </div>
        </div>
        <div class="mb-3">
            <label class="form-label">额外信息</label>
            <textarea class="form-control" name="extraInfo" id="extraInfo" rows="2"></textarea>
        </div>
        <button type="submit" class="btn btn-primary">提交修改</button>
        <a href="/merchantmgmt/entry/listPage" class="btn btn-secondary ms-2">返回列表</a>
    </form>
    <div id="modifyResult" class="mt-3"></div>
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
    // 自动生成唯一requestNo
    function genRequestNo() {
        return 'MOD' + Date.now() + Math.floor(Math.random()*1000);
    }
    $('#requestNo').val(genRequestNo());
    var id = getQueryParam('id');
    if (id) {
        // 自动加载原值
        $.get('/merchantmgmt/entry/detail/' + id, function(entry){
            if(entry) {
                $('#merchantNo').val(entry.merchantNo||'');
                $('#signName').val(entry.signName||'');
                $('#shortName').val(entry.shortName||'');
                $('#contactName').val(entry.contactName||'');
                $('#contactMobile').val(entry.contactMobile||'');
                $('#contactEmail').val(entry.contactEmail||'');
                $('#adminEmail').val(entry.adminEmail||'');
                $('#adminMobile').val(entry.adminMobile||'');
                $('#address').val(entry.address||'');
                $('#extraInfo').val(entry.extraInfo||'');
            }
        });
    }
    // 只保留一个submit事件
    $('#modifyForm').on('submit', function(e) {
        e.preventDefault();
        // 每次提交前生成唯一requestNo
        $('#requestNo').val(genRequestNo());
        // 校验必填
        var required = ['merchantNo','signName','shortName','contactName','contactMobile','contactEmail','address'];
        for(var i=0;i<required.length;i++){
            if(!$('#'+required[i]).val().trim()){
                $('#modifyResult').html('<span class="text-danger">请填写所有必填项</span>');
                return;
            }
        }
        var data = {
            requestNo: $('#requestNo').val(),
            merchantNo: $('#merchantNo').val(),
            notifyUrl: $('#notifyUrl').val(),
            merchantSubjectInfo: JSON.stringify({
                signName: $('#signName').val(),
                shortName: $('#shortName').val()
            }),
            merchantContactInfo: JSON.stringify({
                contactName: $('#contactName').val(),
                contactMobile: $('#contactMobile').val(),
                contactEmail: $('#contactEmail').val()
            }),
            businessAddressInfo: JSON.stringify({
                address: $('#address').val()
            }),
            adminEmail: $('#adminEmail').val(),
            adminMobile: $('#adminMobile').val(),
            extraInfo: $('#extraInfo').val()
        };
        $('#modifyResult').html('提交中...');
        $.ajax({
            url: '/merchantmgmt/manage/info/modify',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(res) {
                if(res.returnCode === 'NIG00000') {
                    $('#modifyResult').html('<span class="text-success">修改成功</span>');
                } else {
                    $('#modifyResult').html('<span class="text-danger">' + (res.returnMsg||'修改失败') + '</span>');
                }
            },
            error: function(){ $('#modifyResult').html('<span class="text-danger">请求失败</span>'); }
        });
    });
});
</script>
</body>
</html> 