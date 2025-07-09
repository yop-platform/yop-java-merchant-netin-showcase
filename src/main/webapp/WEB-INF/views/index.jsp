<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>系统页面索引</title>
</head>
<body>
<h1>系统页面索引</h1>
<ul>
    <li><b>订单相关</b>
        <ul>
            <li><a href="/orders">订单列表</a></li>
        </ul>
    </li>
    <li><b>支付相关</b>
        <ul>
            <li><a href="/pay/test/pay">易宝付款码支付测试</a></li>
            <li><a href="/merchant/register/list">商户注册列表</a></li>
            <li><a href="/merchant/register/apply">商户注册申请</a></li>
            <li><a href="/payment/mcp-merchant-register-apply">MCP商户进件测试</a></li>
        </ul>
    </li>
    <li><b>商品相关</b>
        <ul>
            <li><a href="/products">商品列表</a></li>
        </ul>
    </li>
    <li><b>用户相关</b>
        <ul>
            <li><a href="/login">用户登录</a></li>
            <li><a href="/user/wechat-auth-apply">微信实名认证申请</a></li>
            <li><a href="${pageContext.request.contextPath}/bill/download-page">对账下载</a></li>
        </ul>
    </li>
    <li><b>商户入网功能测试</b>
        <ul>
            <li><a href="/merchantmgmt/entry/apply">商户进件申请</a></li>
            <li><a href="/merchantmgmt/entry/listPage">进件申请列表</a></li>
            <li><a href="/merchantmgmt/file/uploadPage">文件上传</a></li>
            <li><a href="/merchantmgmt/manage/modify">商户信息修改</a></li>
            <li><a href="/merchantmgmt/fee/modify">产品费率修改</a></li>
        </ul>
    </li>
</ul>

</body>
</html> 