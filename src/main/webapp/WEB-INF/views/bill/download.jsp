<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>对账单下载</title>
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">对账单下载</h2>
        <div class="card">
            <div class="card-body">
                <form id="downloadForm" action="${pageContext.request.contextPath}/bill/download" method="get">
                    <div class="mb-3">
                        <label for="merchantNo" class="form-label">商户编号</label>
                        <input type="text" class="form-control" id="merchantNo" name="merchantNo" required>
                        <div class="form-text">标准商户填写商户编号，平台商填写平台商商户编号</div>
                    </div>
                    
                    <div class="mb-3">
                        <label for="dayString" class="form-label">对账日期</label>
                        <input type="date" class="form-control" id="dayString" name="dayString" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="dataType" class="form-label">数据类型</label>
                        <select class="form-select" id="dataType" name="dataType">
                            <option value="">请选择</option>
                            <option value="merchant">商户对账文件</option>
                            <option value="platform">服务商对账文件</option>
                        </select>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">下载对账单</button>
                </form>
            </div>
        </div>
    </div>

    <script>
        $(document).ready(function() {
            // 格式化日期输入
            $('#dayString').on('change', function() {
                var date = $(this).val();
                if (date) {
                    $(this).val(date); // 确保日期格式为yyyy-MM-dd
                }
            });

            // 表单提交前验证
            $('#downloadForm').on('submit', function(e) {
                var merchantNo = $('#merchantNo').val().trim();
                var dayString = $('#dayString').val();
                
                if (!merchantNo) {
                    alert('请输入商户编号');
                    return false;
                }
                
                if (!dayString) {
                    alert('请选择对账日期');
                    return false;
                }
            });
        });
    </script>
</body>
</html> 