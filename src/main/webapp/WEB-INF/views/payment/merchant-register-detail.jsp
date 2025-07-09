<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>商户入网详情</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <style>
        .detail-section {
            margin-bottom: 2rem;
            padding: 1.5rem;
            border-radius: 0.25rem;
            background-color: #f8f9fa;
        }
        .detail-section-title {
            margin-bottom: 1.5rem;
            padding-bottom: 0.5rem;
            border-bottom: 1px solid #dee2e6;
        }
        .status-badge {
            font-size: 0.9rem;
            padding: 0.35rem 0.65rem;
        }
        .status-processing {
            background-color: #ffc107;
        }
        .status-reviewing {
            background-color: #17a2b8;
            color: white;
        }
        .status-review-back {
            background-color: #dc3545;
            color: white;
        }
        .status-authenticity-verifying {
            background-color: #6610f2;
            color: white;
        }
        .status-agreement-signing {
            background-color: #fd7e14;
        }
        .status-business-opening {
            background-color: #20c997;
        }
        .status-completed {
            background-color: #28a745;
            color: white;
        }
        .status-failed {
            background-color: #6c757d;
            color: white;
        }
        .detail-item {
            margin-bottom: 1rem;
        }
        .detail-label {
            font-weight: 600;
            color: #495057;
        }
        .detail-value {
            color: #212529;
        }
    </style>
</head>
<body>
    <div class="container mt-4 mb-5">
        <div class="row mb-4">
            <div class="col-md-8">
                <h2>商户入网详情</h2>
                <p class="text-muted">申请单号：${registration.applicationNo}</p>
            </div>
            <div class="col-md-4 text-end">
                <a href="/merchant/register/list" class="btn btn-outline-secondary">返回列表</a>
            </div>
        </div>
        
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        
        <!-- 状态信息 -->
        <div class="detail-section">
            <div class="row">
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">申请状态</div>
                        <div class="detail-value">
                            <span class="badge status-badge 
                                <c:choose>
                                    <c:when test="${registration.applicationStatus eq 'PROCESSING'}">status-processing</c:when>
                                    <c:when test="${registration.applicationStatus eq 'REVIEWING'}">status-reviewing</c:when>
                                    <c:when test="${registration.applicationStatus eq 'REVIEW_BACK'}">status-review-back</c:when>
                                    <c:when test="${registration.applicationStatus eq 'AUTHENTICITY_VERIFYING'}">status-authenticity-verifying</c:when>
                                    <c:when test="${registration.applicationStatus eq 'AGREEMENT_SIGNING'}">status-agreement-signing</c:when>
                                    <c:when test="${registration.applicationStatus eq 'BUSINESS_OPENING'}">status-business-opening</c:when>
                                    <c:when test="${registration.applicationStatus eq 'COMPLETED'}">status-completed</c:when>
                                    <c:when test="${registration.applicationStatus eq 'FAILED'}">status-failed</c:when>
                                </c:choose>
                            ">
                                <c:choose>
                                    <c:when test="${registration.applicationStatus eq 'PROCESSING'}">处理中</c:when>
                                    <c:when test="${registration.applicationStatus eq 'REVIEWING'}">审核中</c:when>
                                    <c:when test="${registration.applicationStatus eq 'REVIEW_BACK'}">已驳回</c:when>
                                    <c:when test="${registration.applicationStatus eq 'AUTHENTICITY_VERIFYING'}">真实性核验中</c:when>
                                    <c:when test="${registration.applicationStatus eq 'AGREEMENT_SIGNING'}">待签署协议</c:when>
                                    <c:when test="${registration.applicationStatus eq 'BUSINESS_OPENING'}">业务开通中</c:when>
                                    <c:when test="${registration.applicationStatus eq 'COMPLETED'}">已完成</c:when>
                                    <c:when test="${registration.applicationStatus eq 'FAILED'}">失败</c:when>
                                    <c:otherwise>${registration.applicationStatus}</c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">申请时间</div>
                        <div class="detail-value">
                            <fmt:formatDate value="${registration.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">商户编号</div>
                        <div class="detail-value">${registration.merchantNo}</div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">请求号</div>
                        <div class="detail-value">${registration.requestNo}</div>
                    </div>
                </div>
            </div>
            
            <c:if test="${registration.applicationStatus eq 'REVIEW_BACK' && not empty registration.rejectReason}">
                <div class="row mt-3">
                    <div class="col-12">
                        <div class="alert alert-danger">
                            <h5>拒绝原因</h5>
                            <p>${registration.rejectReason}</p>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
        
        <!-- 基本信息 -->
        <div class="detail-section">
            <h4 class="detail-section-title">基本信息</h4>
            <div class="row">
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">商户签约名</div>
                        <div class="detail-value">${registration.signName}</div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">商户简称</div>
                        <div class="detail-value">${registration.shortName}</div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">商户类型</div>
                        <div class="detail-value">
                            <c:choose>
                                <c:when test="${registration.signType eq 'ENTERPRISE'}">企业</c:when>
                                <c:when test="${registration.signType eq 'INDIVIDUAL'}">个体工商户</c:when>
                                <c:otherwise>${registration.signType}</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">证件号码</div>
                        <div class="detail-value">${registration.licenceNo}</div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">商户角色</div>
                        <div class="detail-value">
                            <c:choose>
                                <c:when test="${registration.businessRole eq 'ORDINARY_MERCHANT'}">标准商户</c:when>
                                <c:when test="${registration.businessRole eq 'PLATFORM_MERCHANT'}">平台商</c:when>
                                <c:otherwise>${registration.businessRole}</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">上级商户号</div>
                        <div class="detail-value">${registration.parentMerchantNo}</div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- 法人信息 -->
        <div class="detail-section">
            <h4 class="detail-section-title">法人信息</h4>
            <div class="row">
                <div class="col-md-4">
                    <div class="detail-item">
                        <div class="detail-label">法人姓名</div>
                        <div class="detail-value">${registration.legalName}</div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="detail-item">
                        <div class="detail-label">法人证件类型</div>
                        <div class="detail-value">
                            <c:choose>
                                <c:when test="${registration.legalLicenceType eq 'ID_CARD'}">身份证</c:when>
                                <c:when test="${registration.legalLicenceType eq 'PASSPORT'}">护照</c:when>
                                <c:otherwise>${registration.legalLicenceType}</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="detail-item">
                        <div class="detail-label">法人证件号码</div>
                        <div class="detail-value">${registration.legalLicenceNo}</div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- 联系人信息 -->
        <div class="detail-section">
            <h4 class="detail-section-title">联系人信息</h4>
            <div class="row">
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">联系人姓名</div>
                        <div class="detail-value">${registration.contactName}</div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">联系人手机号</div>
                        <div class="detail-value">${registration.contactMobile}</div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">联系人邮箱</div>
                        <div class="detail-value">${registration.contactEmail}</div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">联系人证件号码</div>
                        <div class="detail-value">${registration.contactLicenceNo}</div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">管理员邮箱</div>
                        <div class="detail-value">${registration.adminEmail}</div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">管理员手机号</div>
                        <div class="detail-value">${registration.adminMobile}</div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- 经营信息 -->
        <div class="detail-section">
            <h4 class="detail-section-title">经营信息</h4>
            <div class="row">
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">一级行业类目</div>
                        <div class="detail-value">
                            <c:choose>
                                <c:when test="${registration.primaryIndustryCategory eq '1001'}">零售</c:when>
                                <c:when test="${registration.primaryIndustryCategory eq '1002'}">餐饮</c:when>
                                <c:when test="${registration.primaryIndustryCategory eq '1003'}">电商</c:when>
                                <c:when test="${registration.primaryIndustryCategory eq '1004'}">服务</c:when>
                                <c:otherwise>${registration.primaryIndustryCategory}</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">二级行业类目</div>
                        <div class="detail-value">${registration.secondaryIndustryCategory}</div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="detail-item">
                        <div class="detail-label">经营地址</div>
                        <div class="detail-value">
                            ${registration.province} ${registration.city} ${registration.district} ${registration.address}
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- 结算信息 -->
        <div class="detail-section">
            <h4 class="detail-section-title">结算信息</h4>
            <div class="row">
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">结算方向</div>
                        <div class="detail-value">
                            <c:choose>
                                <c:when test="${registration.settlementDirection eq 'MERCHANT_SETTLEMENT'}">商户结算</c:when>
                                <c:when test="${registration.settlementDirection eq 'PLATFORM_SETTLEMENT'}">平台结算</c:when>
                                <c:otherwise>${registration.settlementDirection}</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">开户银行</div>
                        <div class="detail-value">
                            <c:choose>
                                <c:when test="${registration.bankCode eq 'ICBC'}">工商银行</c:when>
                                <c:when test="${registration.bankCode eq 'ABC'}">农业银行</c:when>
                                <c:when test="${registration.bankCode eq 'BOC'}">中国银行</c:when>
                                <c:when test="${registration.bankCode eq 'CCB'}">建设银行</c:when>
                                <c:when test="${registration.bankCode eq 'CMB'}">招商银行</c:when>
                                <c:otherwise>${registration.bankCode}</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">账户类型</div>
                        <div class="detail-value">
                            <c:choose>
                                <c:when test="${registration.bankAccountType eq 'CORPORATE_ACCOUNT'}">对公账户</c:when>
                                <c:when test="${registration.bankAccountType eq 'PERSONAL_ACCOUNT'}">对私账户</c:when>
                                <c:otherwise>${registration.bankAccountType}</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="detail-item">
                        <div class="detail-label">银行账号</div>
                        <div class="detail-value">${registration.bankCardNo}</div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- 产品信息 -->
        <div class="detail-section">
            <h4 class="detail-section-title">产品信息</h4>
            <div class="row">
                <div class="col-md-12">
                    <div class="detail-item">
                        <div class="detail-value">
                            <pre>${registration.productInfo}</pre>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="mt-3">
            <a href="/merchant/register/list" class="btn btn-secondary">返回列表</a>
            <c:if test="${registration.applicationStatus eq 'REVIEW_BACK'}">
                <a href="/merchant/register/apply" class="btn btn-primary">重新申请</a>
            </c:if>
        </div>
    </div>
    
    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.bundle.min.js"></script>
</body>
</html> 