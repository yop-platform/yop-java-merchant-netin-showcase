<%--
  Copyright: Copyright (c)2014
  Company: 易宝支付(YeePay)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>商户进件申请 - 商户管理</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h3>商户进件申请（merchantmgmt独立模块）</h3>
    <form id="entryForm">
        <input type="hidden" name="requestNo" id="requestNo" value="merNetInSaaS2685771840">
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">商户类型（businessRole）</label>
                <select class="form-select" name="businessRole" required>
                    <option value="ORDINARY_MERCHANT" selected>标准商户（ORDINARY_MERCHANT）</option>
                    <option value="PLATFORM_MERCHANT">平台商（PLATFORM_MERCHANT）</option>
                    <option value="SETTLED_MERCHANT">入驻商户（SETTLED_MERCHANT）</option>
                    <option value="SHARE_MERCHANT">分账接收方（SHARE_MERCHANT）</option>
                    <option value="HEAD_CHAIN">连锁总店（HEAD_CHAIN）</option>
                    <option value="BRANCH_CHAIN">连锁分店（BRANCH_CHAIN）</option>
                </select>
                <small class="form-text text-muted">
                    说明：<br>
                    - 标准商户：SAAS服务商拓展的收单商户，无上下游或相关业务方<br>
                    - 平台商：经营线上交易撮合平台，可入驻上下游<br>
                    - 入驻商户：只能入驻到特定平台商的下级<br>
                    - 分账接收方：只能作为特定平台商的下级商户，仅允许开通结算产品<br>
                    - 连锁总店/分店：适用于大型线下连锁机构
                </small>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">签约类型（signType）</label>
                <select class="form-select" name="signType" required>
                    <option value="INDIVIDUAL" selected>个体工商户（INDIVIDUAL）</option>
                    <option value="ENTERPRISE">企业（ENTERPRISE）</option>
                    <option value="INSTITUTION">事业单位（INSTITUTION）</option>
                    <option value="COMMUNITY">社会团体（COMMUNITY）</option>
                    <option value="PEOPLE_RUN_NON_ENTERPRISE">民办非企业（PEOPLE_RUN_NON_ENTERPRISE）</option>
                </select>
                <small class="form-text text-muted">
                    说明：
                    <ul>
                        <li>个体工商户：一般为个体户、个体工商户、个体经营</li>
                        <li>企业：一般为有限公司、有限责任公司</li>
                        <li>事业单位：如公安、教育、医疗等机构</li>
                        <li>社会团体：非营利性社会组织</li>
                        <li>民办非企业：民办非企业单位</li>
                    </ul>
                </small>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">商户签约名</label>
                <input type="text" class="form-control" name="signName" required value="商户入网测试一">
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">商户简称</label>
                <input type="text" class="form-control" name="shortName" required value="企业类简称">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">营业执照图片</label>
                <input type="file" class="form-control" id="licenceFile" required>
                <input type="hidden" name="licenceUrl" id="licenceUrl" value="http://qastaticres.yeepay.com/jcptb-merchant-netinjt01/home/2023/02/09/1675913120494783a420d2f4741348453d1f54a2346ba.jpeg">
                <button type="button" class="btn btn-secondary btn-sm mt-2" id="uploadLicenceBtn">上传</button>
                <span id="licenceUploadResult" class="ms-2"></span>
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">商户证件号码</label>
                <input type="text" class="form-control" name="licenceNo" required value="12345">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">法人姓名</label>
                <input type="text" class="form-control" name="legalName" required value="安娜莎">
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">法人证件类型</label>
                <input type="text" class="form-control" name="legalLicenceType" required value="ID_CARD">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">法人证件号码</label>
                <input type="text" class="form-control" name="legalLicenceNo" required value="152531199302040000">
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">法人证件正面图片</label>
                <input type="file" class="form-control" id="legalFrontFile" required>
                <input type="hidden" name="legalLicenceFrontUrl" id="legalLicenceFrontUrl" value="http://qastaticres.yeepay.com/jcptb-merchant-netinjt01/home/2021/03/04/1614827423505983c95b786c84b75be6953285a5d6407.png">
                <button type="button" class="btn btn-secondary btn-sm mt-2" id="uploadLegalFrontBtn">上传</button>
                <span id="legalFrontUploadResult" class="ms-2"></span>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">法人证件背面图片</label>
                <input type="file" class="form-control" id="legalBackFile" required>
                <input type="hidden" name="legalLicenceBackUrl" id="legalLicenceBackUrl" value="http://qastaticres.yeepay.com/jcptb-merchant-netinjt01/home/2020/06/25/1593091519474321762f68414423d8bfadab4a5e459a0.jpg">
                <button type="button" class="btn btn-secondary btn-sm mt-2" id="uploadLegalBackBtn">上传</button>
                <span id="legalBackUploadResult" class="ms-2"></span>
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">联系人姓名</label>
                <input type="text" class="form-control" name="contactName" required value="联系人xxx">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">联系人手机号</label>
                <input type="text" class="form-control" name="contactMobile" required value="18116780000">
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">联系人邮箱</label>
                <input type="email" class="form-control" name="contactEmail" required value="hma0000@yeepay.com">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">管理员邮箱</label>
                <input type="email" class="form-control" name="adminEmail" value="hma0000@yeepay.com">
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">管理员手机号</label>
                <input type="text" class="form-control" name="adminMobile" value="18116780000">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">回调地址（notifyUrl）</label>
                <input type="text" class="form-control" name="notifyUrl" required value="http://www.test.com">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">上级商户编号（parentMerchantNo）</label>
                <input type="text" class="form-control" name="parentMerchantNo" required value="10080662589">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">一级行业分类编码</label>
                <input type="text" class="form-control" name="primaryIndustryCategory" value="120">
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">二级行业分类编码</label>
                <input type="text" class="form-control" name="secondaryIndustryCategory" value="120006">
            </div>
        </div>
        <div class="row">
            <div class="col-md-3 mb-3">
                <label class="form-label">经营省</label>
                <input type="text" class="form-control" name="province" value="110000">
            </div>
            <div class="col-md-3 mb-3">
                <label class="form-label">经营市</label>
                <input type="text" class="form-control" name="city" value="110100">
            </div>
            <div class="col-md-3 mb-3">
                <label class="form-label">经营区</label>
                <input type="text" class="form-control" name="district" value="110101">
            </div>
            <div class="col-md-3 mb-3">
                <label class="form-label">经营地址</label>
                <input type="text" class="form-control" name="address" value="万通中心-xxx经营地址">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">结算方向</label>
                <input type="text" class="form-control" name="settlementDirection" value="BANKCARD">
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">开户总行编码</label>
                <input type="text" class="form-control" name="bankCode" value="CMBCHINA">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">银行账户类型</label>
                <input type="text" class="form-control" name="bankAccountType" value="ENTERPRISE_ACCOUNT">
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">银行账户号码</label>
                <input type="text" class="form-control" name="bankCardNo" value="6214830101880000">
            </div>
        </div>
        <div class="mb-3">
            <label class="form-label">开通产品信息(JSON)</label>
            <textarea class="form-control" name="productInfo" rows="2">[{"rateType":"SINGLE_PERCENT","productCode":"USER_SCAN_ALIPAY_OFFLINE","percentRate":"6.66"},{"rateType":"SINGLE_FIXED","productCode":"D1","fixedRate":"5"}]</textarea>
            <small class="form-text text-muted">示例: [{"productCode":"MERCHANT_SCAN_ALIPAY_OFFLINE","rateType":"SINGLE_PERCENT","percentRate":"0.1"}]</small>
        </div>
        <div class="mb-3">
            <label class="form-label">额外信息</label>
            <textarea class="form-control" name="extraInfo" rows="2"></textarea>
        </div>
        <button type="submit" class="btn btn-primary">提交进件申请</button>
    </form>
    <div id="entryResult" class="mt-3"></div>
</div>
<script src="/static/js/jquery.min.js"></script>
<script>
$(function(){
    // 自动生成唯一requestNo
    var reqNo = 'merNetInSaaS' + Date.now();
    $('#requestNo').val(reqNo);
});
function uploadFile(fileInputId, fileType, urlInputId, resultSpanId) {
    var file = $(fileInputId)[0].files[0];
    if (!file) { $(resultSpanId).text('请选择文件'); return; }
    var formData = new FormData();
    formData.append('file', file);
    formData.append('fileType', fileType);
    $(resultSpanId).text('上传中...');
    $.ajax({
        url: '/merchantmgmt/file/upload',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function(res) {
            if(res.success) {
                $(urlInputId).val(res.fileUrl);
                $(resultSpanId).html('<span class="text-success">上传成功</span>');
            } else {
                $(resultSpanId).html('<span class="text-danger">上传失败：' + res.message + '</span>');
            }
        },
        error: function() {
            $(resultSpanId).html('<span class="text-danger">请求失败</span>');
        }
    });
}
$('#uploadLicenceBtn').click(function(){ uploadFile('#licenceFile', 'licence', '#licenceUrl', '#licenceUploadResult'); });
$('#uploadLegalFrontBtn').click(function(){ uploadFile('#legalFrontFile', 'legalFront', '#legalLicenceFrontUrl', '#legalFrontUploadResult'); });
$('#uploadLegalBackBtn').click(function(){ uploadFile('#legalBackFile', 'legalBack', '#legalLicenceBackUrl', '#legalBackUploadResult'); });
$('#entryForm').on('submit', function(e) {
    e.preventDefault();
    var data = {};
    $(this).serializeArray().forEach(function(item){
        data[item.name] = item.value;
    });
    $('#entryResult').html('提交中...');
    $.ajax({
        url: '/merchantmgmt/entry/apply',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function(res) {
            if(res.returnCode === 'NIG00000') {
                $('#entryResult').html('<span class="text-success">进件成功，申请单号：' + res.applicationNo + '，即将跳转列表页...</span>');
                setTimeout(function(){ window.location.href = '/merchantmgmt/entry/listPage'; }, 1500);
            } else {
                $('#entryResult').html('<span class="text-danger">' + res.returnMsg + '</span>');
            }
        },
        error: function() {
            $('#entryResult').html('<span class="text-danger">请求失败</span>');
        }
    });
});
</script>
</body>
</html> 