# 易宝支付(YOP)集成指南

本文档提供了如何在系统中集成和配置易宝支付的详细说明。

## 一、配置说明

### 1. SDK配置文件

易宝支付SDK使用配置文件方式加载商户信息和密钥。配置文件位于：

```
src/main/resources/config/yop_sdk_config_default.json
```

默认配置文件内容如下：

```json
{
  "app_key": "yop-boss",
  "isv_private_key": [
    {
      "app_key": "yop-boss",
      "store_type": "string",
      "cert_type": "RSA2048",
      "value": "MIIEvQIBADAN...（省略私钥内容）...31k="
    }
  ]
}
```

### 2. 配置项说明

| 配置项 | 说明 | 必填 |
|-------|------|-----|
| app_key | 易宝支付分配的应用标识 | 是 |
| isv_private_key | 商户私钥配置数组 | 是 |
| isv_private_key.app_key | 对应的应用标识 | 是 |
| isv_private_key.store_type | 密钥存储类型，固定值"string" | 是 |
| isv_private_key.cert_type | 证书类型，支持RSA2048/SM2 | 是 |
| isv_private_key.value | 私钥内容 | 是 |

### 3. 商户配置

在`application.properties`或`application.yml`中配置商户信息：

```properties
# 易宝支付商户配置
yeepay.saas.merchant.no=10080662589
yeepay.standard.merchant.no=10080086386
yeepay.appkey.prefix=sandbox_rsa_
yeepay.notify.url=https://your-domain.com/payments/callback/YEEPAY
```

## 二、配置步骤

### 1. 获取商户信息

从易宝支付商户后台或技术支持获取以下信息：
- 父商户编号(parentMerchantNo)
- 商户编号(merchantNo)
- 应用标识(app_key)
- 商户私钥

### 2. 更新配置文件

1. 编辑`yop_sdk_config_default.json`，替换以下内容：
   - `app_key`：替换为您的应用标识
   - `isv_private_key[0].app_key`：替换为您的应用标识
   - `isv_private_key[0].value`：替换为您的商户私钥

2. 编辑应用配置文件，更新商户信息：
   - `yeepay.parent.merchant.no`：替换为您的父商户编号
   - `yeepay.merchant.no`：替换为您的商户编号
   - `yeepay.notify.url`：替换为您的支付结果通知URL

## 三、使用说明

### 1. 支付流程

系统已集成易宝支付作为支付渠道之一，用户在订单支付页面可以选择"易宝支付"作为支付方式。当用户选择易宝支付并提交后，系统会：

1. 生成支付交易记录
2. 调用易宝支付接口创建支付单
3. 获取支付链接并展示给用户
4. 用户完成支付后，易宝支付会向系统发送支付结果通知

### 2. 支付状态查询

系统支持通过以下方式查询支付状态：

- 用户在"我的订单"页面查看订单和支付状态
- 支付完成后，系统会接收易宝支付的结果通知并更新支付状态

### 3. 日志说明

系统对易宝支付的调用过程进行了详细的日志记录，包括：

- 支付初始化日志
- 支付请求参数和响应结果
- 支付回调处理日志

如需查看日志，请检查应用日志文件中包含"YeepayChannel"的日志条目。

## 四、常见问题

### 1. 配置文件路径问题

确保配置文件位于`src/main/resources/config/`目录下，并且文件名为`yop_sdk_config_default.json`。SDK会自动从该路径加载配置文件。

### 2. 密钥格式问题

私钥必须是正确的格式，通常是以"MII"开头的长字符串。请确保复制完整，不要包含额外的空格或换行符。

### 3. 支付失败排查

如果支付请求失败，请检查：
- 配置信息是否正确
- 请求参数是否完整
- 应用日志中的详细错误信息

### 4. 测试环境与生产环境切换

本文档中的示例配置适用于生产环境。如需在测试环境中使用，请：
1. 使用测试环境的商户信息和密钥
2. 在配置文件中添加测试环境服务器地址配置

```json
{
  "app_key": "your-test-app-key",
  "isv_private_key": [...],
  "sandbox_server_root": "https://sandbox.yeepay.com/yop-center"
}
```

## 五、联系支持

如遇到问题，请联系技术支持或参考以下资源：
- 易宝支付开放平台文档：https://open.yeepay.com/docs/
- 易宝支付商户后台：https://merchant.yeepay.com/ 