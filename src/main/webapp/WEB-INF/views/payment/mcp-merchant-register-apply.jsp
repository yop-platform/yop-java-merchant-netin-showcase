<%--
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>MCP商户进件申请</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css"/>
    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <h2>MCP商户进件申请</h2>
    <form id="mcpRegisterForm">
        <div class="mb-3">
            <label>商户主体信息（JSON字符串）</label>
            <textarea class="form-control" name="merchantSubjectInfo" rows="2"></textarea>
        </div>
        <div class="mb-3">
            <label>法人信息（JSON字符串）</label>
            <textarea class="form-control" name="corporationInfo" rows="2"></textarea>
        </div>
        <div class="mb-3">
            <label>行业类别信息（JSON字符串）</label>
            <textarea class="form-control" name="industryCategoryInfo" rows="2"></textarea>
        </div>
        <div class="mb-3">
            <label>经营地址信息（JSON字符串）</label>
            <textarea class="form-control" name="businessAddressInfo" rows="2"></textarea>
        </div>
        <div class="mb-3">
            <label>账户信息（JSON字符串）</label>
            <textarea class="form-control" name="accountInfo" rows="2"></textarea>
        </div>
        <div class="mb-3">
            <label>产品信息（JSON字符串）</label>
            <textarea class="form-control" name="productInfo" rows="2"></textarea>
        </div>
        <div class="mb-3">
            <label>产品资质信息（JSON字符串）</label>
            <textarea class="form-control" name="productQualificationInfo" rows="2"></textarea>
        </div>
        <div class="mb-3">
            <label>功能服务信息（JSON字符串）</label>
            <textarea class="form-control" name="functionServiceInfo" rows="2"></textarea>
        </div>
        <div class="mb-3">
            <label>功能服务资质信息（JSON字符串）</label>
            <textarea class="form-control" name="functionServiceQualificationInfo" rows="2"></textarea>
        </div>
        <div class="mb-3">
            <label>业务场景信息（JSON字符串）</label>
            <textarea class="form-control" name="businessSceneInfo" rows="2"></textarea>
        </div>
        <div class="mb-3">
            <label>商户补充信息（JSON字符串）</label>
            <textarea class="form-control" name="merchantExtraInfo" rows="2"></textarea>
        </div>
        <div class="mb-3">
            <label>业务配置信息（JSON字符串）</label>
            <textarea class="form-control" name="businessConfig" rows="2"></textarea>
        </div>
        <div class="mb-3">
            <label>银行开户信息（JSON字符串）</label>
            <textarea class="form-control" name="bankOpenAccountInfo" rows="2"></textarea>
        </div>
        <div class="mb-3">
            <label>请求流水号</label>
            <input class="form-control" name="requestNo"/>
        </div>
        <div class="mb-3">
            <label>代理商编号</label>
            <input class="form-control" name="agentMerchantNo"/>
        </div>
        <div class="mb-3">
            <label>外部商户号</label>
            <input class="form-control" name="outMerchantNo"/>
        </div>
        <div class="mb-3">
            <label>进件渠道</label>
            <input class="form-control" name="registerChannel"/>
        </div>
        <div class="mb-3">
            <label>进件来源</label>
            <input class="form-control" name="registerSource"/>
        </div>
        <div class="mb-3">
            <label>进件类型</label>
            <input class="form-control" name="registerType"/>
        </div>
        <div class="mb-3">
            <label>进件备注</label>
            <input class="form-control" name="remark"/>
        </div>
        <button type="submit" class="btn btn-primary">提交进件</button>
    </form>
    <div class="mt-4">
        <h5>响应结果：</h5>
        <pre id="mcpRegisterResult"></pre>
    </div>
</div>
<script>
    $(function () {
        $('#mcpRegisterForm').on('submit', function (e) {
            e.preventDefault();
            var formData = {};
            $(this).serializeArray().forEach(function (item) {
                // 尝试将JSON字符串转为对象，否则保留原字符串
                try {
                    formData[item.name] = JSON.parse(item.value);
                } catch (err) {
                    formData[item.name] = item.value;
                }
            });
            $('#mcpRegisterResult').text('提交中...');
            $.ajax({
                url: '/mcp/merchant/register/saas/micro',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(formData),
                success: function (resp) {
                    $('#mcpRegisterResult').text(JSON.stringify(resp, null, 2));
                },
                error: function (xhr) {
                    $('#mcpRegisterResult').text('请求失败：' + xhr.responseText);
                }
            });
        });
    });
</script>
</body>
</html> 