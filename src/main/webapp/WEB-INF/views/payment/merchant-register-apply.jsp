<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>商户入网申请</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <style>
        .form-section {
            margin-bottom: 2rem;
            padding: 1.5rem;
            border-radius: 0.25rem;
            background-color: #f8f9fa;
        }
        .form-section-title {
            margin-bottom: 1.5rem;
            padding-bottom: 0.5rem;
            border-bottom: 1px solid #dee2e6;
        }
        .preview-container {
            max-width: 200px;
            max-height: 150px;
            margin-top: 0.5rem;
        }
        .preview-container img {
            max-width: 100%;
            max-height: 100%;
            border: 1px solid #ddd;
            border-radius: 0.25rem;
        }
    </style>
</head>
<body>
    <div class="container mt-4 mb-5">
        <div class="row mb-4">
            <div class="col-md-8">
                <h2>商户入网申请</h2>
                <p class="text-muted">请填写商户信息，申请接入易宝支付</p>
            </div>
            <div class="col-md-4 text-end">
                <a href="/merchant/register/list" class="btn btn-outline-secondary">返回列表</a>
            </div>
        </div>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        
        <form action="/merchant/register/submit" method="post" id="registrationForm">
            <!-- 基本信息 -->
            <div class="form-section">
                <h4 class="form-section-title">基本信息</h4>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="signName" class="form-label">商户签约名 <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="signName" name="signName" value="${registration.signName}" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="shortName" class="form-label">商户简称 <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="shortName" name="shortName" value="${registration.shortName}" required>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="signType" class="form-label">商户类型 <span class="text-danger">*</span></label>
                        <select class="form-select" id="signType" name="signType" required>
                            <option value="">请选择商户类型</option>
                            <option value="ENTERPRISE" ${registration.signType == 'ENTERPRISE' ? 'selected' : ''}>企业</option>
                            <option value="INDIVIDUAL" ${registration.signType == 'INDIVIDUAL' ? 'selected' : ''}>个体工商户</option>
                        </select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="licenceNo" class="form-label">证件号码 <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="licenceNo" name="licenceNo" value="${registration.licenceNo}" required>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="businessRole" class="form-label">商户角色 <span class="text-danger">*</span></label>
                        <select class="form-select" id="businessRole" name="businessRole" required>
                            <option value="">请选择商户角色</option>
                            <option value="ORDINARY_MERCHANT" ${registration.businessRole == 'ORDINARY_MERCHANT' ? 'selected' : ''}>标准商户</option>
                            <option value="PLATFORM_MERCHANT" ${registration.businessRole == 'PLATFORM_MERCHANT' ? 'selected' : ''}>平台商</option>
                        </select>
                    </div>
                </div>
            </div>
            
            <!-- 证件照片 -->
            <div class="form-section">
                <h4 class="form-section-title">证件照片</h4>
                <div class="row">
                    <div class="col-md-4 mb-3">
                        <label class="form-label">营业执照 <span class="text-danger">*</span></label>
                        <input type="file" class="form-control" id="licenceFile" accept="image/*">
                        <div class="preview-container" id="licencePreview"></div>
                        <input type="hidden" id="licenceUrl" name="licenceUrl" value="${registration.licenceUrl}">
                    </div>
                    <div class="col-md-4 mb-3">
                        <label class="form-label">法人证件正面 <span class="text-danger">*</span></label>
                        <input type="file" class="form-control" id="legalLicenceFrontFile" accept="image/*">
                        <div class="preview-container" id="legalLicenceFrontPreview"></div>
                        <input type="hidden" id="legalLicenceFrontUrl" name="legalLicenceFrontUrl" value="${registration.legalLicenceFrontUrl}">
                    </div>
                    <div class="col-md-4 mb-3">
                        <label class="form-label">法人证件背面 <span class="text-danger">*</span></label>
                        <input type="file" class="form-control" id="legalLicenceBackFile" accept="image/*">
                        <div class="preview-container" id="legalLicenceBackPreview"></div>
                        <input type="hidden" id="legalLicenceBackUrl" name="legalLicenceBackUrl" value="${registration.legalLicenceBackUrl}">
                    </div>
                </div>
            </div>
            
            <!-- 法人信息 -->
            <div class="form-section">
                <h4 class="form-section-title">法人信息</h4>
                <div class="row">
                    <div class="col-md-4 mb-3">
                        <label for="legalName" class="form-label">法人姓名 <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="legalName" name="legalName" value="${registration.legalName}" required>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="legalLicenceType" class="form-label">法人证件类型 <span class="text-danger">*</span></label>
                        <select class="form-select" id="legalLicenceType" name="legalLicenceType" required>
                            <option value="">请选择证件类型</option>
                            <option value="ID_CARD" ${registration.legalLicenceType == 'ID_CARD' ? 'selected' : ''}>身份证</option>
                            <option value="PASSPORT" ${registration.legalLicenceType == 'PASSPORT' ? 'selected' : ''}>护照</option>
                        </select>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="legalLicenceNo" class="form-label">法人证件号码 <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="legalLicenceNo" name="legalLicenceNo" value="${registration.legalLicenceNo}" required>
                    </div>
                </div>
            </div>
            
            <!-- 联系人信息 -->
            <div class="form-section">
                <h4 class="form-section-title">联系人信息</h4>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="contactName" class="form-label">联系人姓名 <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="contactName" name="contactName" value="${registration.contactName}" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="contactMobile" class="form-label">联系人手机号 <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="contactMobile" name="contactMobile" value="${registration.contactMobile}" required>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="contactEmail" class="form-label">联系人邮箱 <span class="text-danger">*</span></label>
                        <input type="email" class="form-control" id="contactEmail" name="contactEmail" value="${registration.contactEmail}" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="contactLicenceNo" class="form-label">联系人证件号码 <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="contactLicenceNo" name="contactLicenceNo" value="${registration.contactLicenceNo}" required>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="adminEmail" class="form-label">管理员邮箱 <span class="text-danger">*</span></label>
                        <input type="email" class="form-control" id="adminEmail" name="adminEmail" value="${registration.adminEmail}" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="adminMobile" class="form-label">管理员手机号 <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="adminMobile" name="adminMobile" value="${registration.adminMobile}" required>
                    </div>
                </div>
            </div>
            
            <!-- 经营信息 -->
            <div class="form-section">
                <h4 class="form-section-title">经营信息</h4>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="primaryIndustryCategory" class="form-label">一级行业类目 <span class="text-danger">*</span></label>
                        <select class="form-select" id="primaryIndustryCategory" name="primaryIndustryCategory" required>
                            <option value="">请选择行业类目</option>
                            <option value="120" ${registration.primaryIndustryCategory == '120' ? 'selected' : ''}>百货</option>
                            <option value="122" ${registration.primaryIndustryCategory == '122' ? 'selected' : ''}>电商</option>
                        </select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="secondaryIndustryCategory" class="form-label">二级行业类目 <span class="text-danger">*</span></label>
                        <select class="form-select" id="secondaryIndustryCategory" name="secondaryIndustryCategory" required>
                            <option value="">请选择二级类目</option>
                            <!-- 百货二级类目 -->
                            <option class="category-120" value="120006" ${registration.secondaryIndustryCategory == '120006' ? 'selected' : ''}>家居/家装</option>
                            <option class="category-120" value="120011" ${registration.secondaryIndustryCategory == '120011' ? 'selected' : ''}>家用电器</option>
                            <option class="category-120" value="120013" ${registration.secondaryIndustryCategory == '120013' ? 'selected' : ''}>烟草/电子烟/酒类</option>
                            <!-- 电商二级类目 -->
                            <option class="category-122" value="122001" ${registration.secondaryIndustryCategory == '122001' ? 'selected' : ''}>社交电商/团购电商</option>
                            <option class="category-122" value="122002" ${registration.secondaryIndustryCategory == '122002' ? 'selected' : ''}>综合电商</option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 mb-3">
                        <label for="province" class="form-label">省份 <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="province" name="province" value="${registration.province}" required>
                    </div>
                    <div class="col-md-3 mb-3">
                        <label for="city" class="form-label">城市 <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="city" name="city" value="${registration.city}" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="district" class="form-label">区县 <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="district" name="district" value="${registration.district}" required>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 mb-3">
                        <label for="address" class="form-label">详细地址 <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="address" name="address" value="${registration.address}" required>
                    </div>
                </div>
            </div>
            
            <!-- 结算信息 -->
            <div class="form-section">
                <h4 class="form-section-title">结算信息</h4>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="settlementDirection" class="form-label">结算方向</label>
                        <select class="form-select" id="settlementDirection" name="settlementDirection">
                            <option value="">请选择结算方向</option>
                            <option value="ACCOUNT" ${registration.settlementDirection == 'ACCOUNT' ? 'selected' : ''}>结算到支付账户</option>
                            <option value="BANKCARD" ${registration.settlementDirection == 'BANKCARD' ? 'selected' : ''}>结算到银行账户</option>
                        </select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="bankCode" class="form-label">开户银行</label>
                        <select class="form-select" id="bankCode" name="bankCode">
                            <option value="">请选择银行</option>
                            <option value="ICBC" ${registration.bankCode == 'ICBC' ? 'selected' : ''}>工商银行</option>
                            <option value="ABC" ${registration.bankCode == 'ABC' ? 'selected' : ''}>农业银行</option>
                            <option value="BOC" ${registration.bankCode == 'BOC' ? 'selected' : ''}>中国银行</option>
                            <option value="CCB" ${registration.bankCode == 'CCB' ? 'selected' : ''}>建设银行</option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="bankAccountType" class="form-label">账户类型</label>
                        <select class="form-select" id="bankAccountType" name="bankAccountType">
                            <option value="">请选择账户类型</option>
                            <option value="UNIT_SETTLEMENT_CARD" ${registration.bankAccountType == 'UNIT_SETTLEMENT_CARD' ? 'selected' : ''}>单位结算卡</option>
                            <option value="ENTERPRISE_ACCOUNT" ${registration.bankAccountType == 'ENTERPRISE_ACCOUNT' ? 'selected' : ''}>对公账户</option>
                            <option value="DEBIT_CARD" ${registration.bankAccountType == 'DEBIT_CARD' ? 'selected' : ''}>借记卡</option>
                            <option value="PASSBOOK" ${registration.bankAccountType == 'PASSBOOK' ? 'selected' : ''}>存折</option>
                        </select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="bankCardNo" class="form-label">银行账号</label>
                        <input type="text" class="form-control" id="bankCardNo" name="bankCardNo" value="${registration.bankCardNo}">
                    </div>
                </div>
            </div>
            
            <!-- 产品信息 -->
            <div class="form-section">
                <h4 class="form-section-title">产品信息</h4>
                <div class="row">
                    <div class="col-md-12 mb-3">
                        <label for="productInfo" class="form-label">产品信息 <span class="text-danger">*</span></label>
                        <textarea class="form-control" id="productInfo" name="productInfo" rows="3" required>${registration.productInfo}</textarea>
                        <small class="form-text text-muted">示例: [{"productCode":"MERCHANT_SCAN_ALIPAY_OFFLINE","rateType":"SINGLE_PERCENT","percentRate":"0.1"}]</small>
                    </div>
                </div>
            </div>
            
            <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-4">
                <a href="/merchant/register/list" class="btn btn-secondary me-md-2">取消</a>
                <button type="submit" class="btn btn-primary" id="submitBtn">提交申请</button>
            </div>
        </form>
    </div>
    
    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.bundle.min.js"></script>
    
    <script>
        $(document).ready(function() {
            // 处理图片预览
            function handleFilePreview(fileId, previewId, urlId) {
                $('#' + fileId).change(function() {
                    const file = this.files[0];
                    if (file) {
                        // 预览图片
                        const reader = new FileReader();
                        reader.onload = function(e) {
                            $('#' + previewId).html('<img src="' + e.target.result + '">');
                        }
                        reader.readAsDataURL(file);
                        
                        // 上传文件
                        const formData = new FormData();
                        formData.append('file', file);
                        formData.append('fileType', fileId.replace('File', ''));
                        
                        $.ajax({
                            url: '/merchant/register/upload',
                            type: 'POST',
                            data: formData,
                            contentType: false,
                            processData: false,
                            success: function(response) {
                                if (response.success) {
                                    $('#' + urlId).val(response.fileUrl);
                                    alert('文件上传成功');
                                } else {
                                    alert('文件上传失败: ' + response.message);
                                }
                            },
                            error: function() {
                                alert('文件上传出错，请重试');
                            }
                        });
                    }
                });
                
                // 如果已有URL，显示预览
                const existingUrl = $('#' + urlId).val();
                if (existingUrl) {
                    $('#' + previewId).html('<p>已上传</p>');
                }
            }
            
            // 初始化文件上传预览
            handleFilePreview('licenceFile', 'licencePreview', 'licenceUrl');
            handleFilePreview('legalLicenceFrontFile', 'legalLicenceFrontPreview', 'legalLicenceFrontUrl');
            handleFilePreview('legalLicenceBackFile', 'legalLicenceBackPreview', 'legalLicenceBackUrl');
            
            // 一级类目变更时，过滤二级类目
            $('#primaryIndustryCategory').change(function() {
                const primaryCategory = $(this).val();
                $('#secondaryIndustryCategory option').hide();
                $('#secondaryIndustryCategory option:first').show();
                $('#secondaryIndustryCategory').val('');
                
                if (primaryCategory) {
                    $('.category-' + primaryCategory).show();
                }
            });
            
            // 触发一次一级类目变更事件，初始化二级类目
            $('#primaryIndustryCategory').trigger('change');
            
            // 表单提交前验证
            $('#registrationForm').submit(function(e) {
                // 检查必填项
                const requiredFields = [
                    'signName', 'shortName', 'signType', 'licenceNo', 'businessRole',
                    'licenceUrl', 'legalLicenceFrontUrl', 'legalLicenceBackUrl',
                    'legalName', 'legalLicenceType', 'legalLicenceNo',
                    'contactName', 'contactMobile', 'contactEmail', 'contactLicenceNo',
                    'adminEmail', 'adminMobile',
                    'primaryIndustryCategory', 'secondaryIndustryCategory',
                    'province', 'city', 'district', 'address',
                    'productInfo'
                ];
                
                let isValid = true;
                
                requiredFields.forEach(function(field) {
                    const value = $('#' + field).val();
                    if (!value) {
                        isValid = false;
                        $('#' + field).addClass('is-invalid');
                    } else {
                        $('#' + field).removeClass('is-invalid');
                    }
                });
                
                if (!isValid) {
                    e.preventDefault();
                    alert('请填写所有必填项');
                    return false;
                }
                
                // 确认提交
                if (!confirm('确认提交商户入网申请？')) {
                    e.preventDefault();
                    return false;
                }
            });
        });
    </script>
</body>
</html> 