<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>微信实名认证申请</title>
    <style>
        label { display: block; margin-top: 10px; }
        input, select, textarea { width: 300px; }
        .error { color: red; }
        .success { color: green; }
    </style>
</head>
<body>
    <h1>微信实名认证申请</h1>
    <c:if test="${not empty error}"><div class="error">${error}</div></c:if>
    <c:if test="${not empty success}"><div class="success">${success}</div></c:if>
    <form action="<c:url value='/user/wechat-auth-apply'/>" method="post">
        <label>请求号(requestNo)*:<input name="requestNo" required></label>
        <label>商户编号(subMerchantNo)*:<input name="subMerchantNo" required></label>
        <label>申请人类型(applicantType)*:
            <select name="applicantType" required>
                <option value="LEGAL">法人</option>
                <option value="TRANSACTOR">经办人</option>
            </select>
        </label>
        <label>申请人姓名(applicantName):<input name="applicantName"></label>
        <label>申请人手机号(applicantPhone)*:<input name="applicantPhone" required></label>
        <label>申请人身份证号(applicantIdCard):<input name="applicantIdCard"></label>
        <label>经办人信息(transactorInfo, JSON):<textarea name="transactorInfo"></textarea></label>
        <label>法人证件类型(identificationType):<input name="identificationType"></label>
        <label>法人证件正面(identificationFrontCopy):<input name="identificationFrontCopy"></label>
        <label>法人证件反面(identificationBackCopy):<input name="identificationBackCopy"></label>
        <label>法人证件有效期(identificationValidDate)*:<input name="identificationValidDate" required placeholder='["yyyy-MM-dd","forever"]'></label>
        <label>法人证件居住地址(identificationAddress):<input name="identificationAddress"></label>
        <label>主体证件照片(certCopy):<input name="certCopy"></label>
        <label>主体注册地址(companyAddress)*:<input name="companyAddress" required></label>
        <label>主体证件有效期(licenceValidDate):<input name="licenceValidDate"></label>
        <label>是否金融机构(isFinanceInstitution):
            <select name="isFinanceInstitution">
                <option value="">--请选择--</option>
                <option value="true">是</option>
                <option value="false">否</option>
            </select>
        </label>
        <label>金融机构许可证(financeInstitutionInfo, JSON):<textarea name="financeInstitutionInfo"></textarea></label>
        <label>登记证书类型(certType):<input name="certType"></label>
        <label>登记证书编号(certNumber):<input name="certNumber"></label>
        <label>单位证明函照片(companyProveCopy):<input name="companyProveCopy"></label>
        <label>法人是否为受益人(owner):
            <select name="owner">
                <option value="">--请选择--</option>
                <option value="true">是</option>
                <option value="false">否</option>
            </select>
        </label>
        <label>受益人信息列表(uboInfoList, JSON):<textarea name="uboInfoList"></textarea></label>
        <label>报备费率(reportFee):<input name="reportFee"></label>
        <label>渠道号(channelId):<input name="channelId"></label>
        <label>小微经营类型(microBizType):<input name="microBizType"></label>
        <label>门店名称(storeName):<input name="storeName"></label>
        <label>门店省市编码(storeAddressCode):<input name="storeAddressCode"></label>
        <label>门头照片(storeHeaderCopy):<input name="storeHeaderCopy"></label>
        <label>店内照片(storeIndoorCopy):<input name="storeIndoorCopy"></label>
        <button type="submit">提交申请</button>
    </form>
    <p><a href="<c:url value='/orders'/>">返回我的订单</a></p>
</body>
</html> 