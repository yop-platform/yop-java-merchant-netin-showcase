<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>商户入网管理</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <style>
        .status-badge {
            font-size: 0.8rem;
            padding: 0.25rem 0.5rem;
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
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="row mb-4">
            <div class="col-md-8">
                <h2>商户入网管理</h2>
            </div>
            <div class="col-md-4 text-end">
                <a href="/merchant/register/apply" class="btn btn-primary">申请入网</a>
            </div>
        </div>
        
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        
        <div class="card">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>签约名称</th>
                                <th>请求号</th>
                                <th>申请单号</th>
                                <th>商户号</th>
                                <th>申请状态</th>
                                <th>申请时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${registrations}" var="reg">
                                <tr>
                                    <td>${reg.id}</td>
                                    <td>${reg.signName}</td>
                                    <td>${reg.requestNo}</td>
                                    <td>${reg.applicationNo}</td>
                                    <td>${reg.merchantNo}</td>
                                    <td>
                                        <span class="badge status-badge 
                                            <c:choose>
                                                <c:when test="${reg.applicationStatus eq 'PROCESSING'}">status-processing</c:when>
                                                <c:when test="${reg.applicationStatus eq 'REVIEWING'}">status-reviewing</c:when>
                                                <c:when test="${reg.applicationStatus eq 'REVIEW_BACK'}">status-review-back</c:when>
                                                <c:when test="${reg.applicationStatus eq 'AUTHENTICITY_VERIFYING'}">status-authenticity-verifying</c:when>
                                                <c:when test="${reg.applicationStatus eq 'AGREEMENT_SIGNING'}">status-agreement-signing</c:when>
                                                <c:when test="${reg.applicationStatus eq 'BUSINESS_OPENING'}">status-business-opening</c:when>
                                                <c:when test="${reg.applicationStatus eq 'COMPLETED'}">status-completed</c:when>
                                                <c:when test="${reg.applicationStatus eq 'FAILED'}">status-failed</c:when>
                                            </c:choose>
                                        ">
                                            <c:choose>
                                                <c:when test="${reg.applicationStatus eq 'PROCESSING'}">处理中</c:when>
                                                <c:when test="${reg.applicationStatus eq 'REVIEWING'}">审核中</c:when>
                                                <c:when test="${reg.applicationStatus eq 'REVIEW_BACK'}">已驳回</c:when>
                                                <c:when test="${reg.applicationStatus eq 'AUTHENTICITY_VERIFYING'}">真实性核验中</c:when>
                                                <c:when test="${reg.applicationStatus eq 'AGREEMENT_SIGNING'}">待签署协议</c:when>
                                                <c:when test="${reg.applicationStatus eq 'BUSINESS_OPENING'}">业务开通中</c:when>
                                                <c:when test="${reg.applicationStatus eq 'COMPLETED'}">已完成</c:when>
                                                <c:when test="${reg.applicationStatus eq 'FAILED'}">失败</c:when>
                                                <c:otherwise>${reg.applicationStatus}</c:otherwise>
                                            </c:choose>
                                        </span>
                                    </td>
                                    <td><fmt:formatDate value="${reg.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td>
                                        <a href="/merchant/register/${reg.id}" class="btn btn-sm btn-info">查看</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            
                            <c:if test="${empty registrations}">
                                <tr>
                                    <td colspan="8" class="text-center">暂无商户入网记录</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
        <div class="mt-3">
            <a href="/" class="btn btn-secondary">返回首页</a>
        </div>
    </div>
    
    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.bundle.min.js"></script>
</body>
</html> 