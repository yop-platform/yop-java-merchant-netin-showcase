package com.wdc.test.shop.repository;

import com.wdc.test.shop.model.MerchantRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 商户入网信息数据访问层
 */
@Repository
public class MerchantRegistrationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MerchantRegistrationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 保存商户入网信息
     */
    public MerchantRegistration save(MerchantRegistration registration) {
        if (registration.getId() == null) {
            return insert(registration);
        } else {
            return update(registration);
        }
    }

    /**
     * 新增商户入网信息
     */
    private MerchantRegistration insert(MerchantRegistration registration) {
        String sql = "INSERT INTO merchant_registration (" +
                "request_no, application_no, application_status, merchant_no, business_role, parent_merchant_no, " +
                "licence_url, legal_licence_front_url, legal_licence_back_url, " +
                "sign_name, sign_type, licence_no, short_name, " +
                "legal_name, legal_licence_type, legal_licence_no, " +
                "contact_name, contact_mobile, contact_email, contact_licence_no, admin_email, admin_mobile, " +
                "primary_industry_category, secondary_industry_category, province, city, district, address, " +
                "settlement_direction, bank_code, bank_account_type, bank_card_no, " +
                "notify_url, create_time, update_time, reject_reason, product_info, extra_info) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        Date now = new Date();
        registration.setCreateTime(now);
        registration.setUpdateTime(now);

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, registration.getRequestNo());
            ps.setString(2, registration.getApplicationNo());
            ps.setString(3, registration.getApplicationStatus());
            ps.setString(4, registration.getMerchantNo());
            ps.setString(5, registration.getBusinessRole());
            ps.setString(6, registration.getParentMerchantNo());
            ps.setString(7, registration.getLicenceUrl());
            ps.setString(8, registration.getLegalLicenceFrontUrl());
            ps.setString(9, registration.getLegalLicenceBackUrl());
            ps.setString(10, registration.getSignName());
            ps.setString(11, registration.getSignType());
            ps.setString(12, registration.getLicenceNo());
            ps.setString(13, registration.getShortName());
            ps.setString(14, registration.getLegalName());
            ps.setString(15, registration.getLegalLicenceType());
            ps.setString(16, registration.getLegalLicenceNo());
            ps.setString(17, registration.getContactName());
            ps.setString(18, registration.getContactMobile());
            ps.setString(19, registration.getContactEmail());
            ps.setString(20, registration.getContactLicenceNo());
            ps.setString(21, registration.getAdminEmail());
            ps.setString(22, registration.getAdminMobile());
            ps.setString(23, registration.getPrimaryIndustryCategory());
            ps.setString(24, registration.getSecondaryIndustryCategory());
            ps.setString(25, registration.getProvince());
            ps.setString(26, registration.getCity());
            ps.setString(27, registration.getDistrict());
            ps.setString(28, registration.getAddress());
            ps.setString(29, registration.getSettlementDirection());
            ps.setString(30, registration.getBankCode());
            ps.setString(31, registration.getBankAccountType());
            ps.setString(32, registration.getBankCardNo());
            ps.setString(33, registration.getNotifyUrl());
            ps.setTimestamp(34, new Timestamp(registration.getCreateTime().getTime()));
            ps.setTimestamp(35, new Timestamp(registration.getUpdateTime().getTime()));
            ps.setString(36, registration.getRejectReason());
            ps.setString(37, registration.getProductInfo());
            ps.setString(38, registration.getExtraInfo());
            return ps;
        }, keyHolder);

        registration.setId(keyHolder.getKey().longValue());
        return registration;
    }

    /**
     * 更新商户入网信息
     */
    private MerchantRegistration update(MerchantRegistration registration) {
        String sql = "UPDATE merchant_registration SET " +
                "request_no = ?, application_no = ?, application_status = ?, merchant_no = ?, business_role = ?, parent_merchant_no = ?, " +
                "licence_url = ?, legal_licence_front_url = ?, legal_licence_back_url = ?, " +
                "sign_name = ?, sign_type = ?, licence_no = ?, short_name = ?, " +
                "legal_name = ?, legal_licence_type = ?, legal_licence_no = ?, " +
                "contact_name = ?, contact_mobile = ?, contact_email = ?, contact_licence_no = ?, admin_email = ?, admin_mobile = ?, " +
                "primary_industry_category = ?, secondary_industry_category = ?, province = ?, city = ?, district = ?, address = ?, " +
                "settlement_direction = ?, bank_code = ?, bank_account_type = ?, bank_card_no = ?, " +
                "notify_url = ?, update_time = ?, reject_reason = ?, product_info = ?, extra_info = ? " +
                "WHERE id = ?";

        registration.setUpdateTime(new Date());

        jdbcTemplate.update(sql,
                registration.getRequestNo(),
                registration.getApplicationNo(),
                registration.getApplicationStatus(),
                registration.getMerchantNo(),
                registration.getBusinessRole(),
                registration.getParentMerchantNo(),
                registration.getLicenceUrl(),
                registration.getLegalLicenceFrontUrl(),
                registration.getLegalLicenceBackUrl(),
                registration.getSignName(),
                registration.getSignType(),
                registration.getLicenceNo(),
                registration.getShortName(),
                registration.getLegalName(),
                registration.getLegalLicenceType(),
                registration.getLegalLicenceNo(),
                registration.getContactName(),
                registration.getContactMobile(),
                registration.getContactEmail(),
                registration.getContactLicenceNo(),
                registration.getAdminEmail(),
                registration.getAdminMobile(),
                registration.getPrimaryIndustryCategory(),
                registration.getSecondaryIndustryCategory(),
                registration.getProvince(),
                registration.getCity(),
                registration.getDistrict(),
                registration.getAddress(),
                registration.getSettlementDirection(),
                registration.getBankCode(),
                registration.getBankAccountType(),
                registration.getBankCardNo(),
                registration.getNotifyUrl(),
                new Timestamp(registration.getUpdateTime().getTime()),
                registration.getRejectReason(),
                registration.getProductInfo(),
                registration.getExtraInfo(),
                registration.getId());

        return registration;
    }

    /**
     * 根据ID查询商户入网信息
     */
    public MerchantRegistration findById(Long id) {
        String sql = "SELECT * FROM merchant_registration WHERE id = ?";
        List<MerchantRegistration> results = jdbcTemplate.query(sql, getRowMapper(), id);
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * 根据请求号查询商户入网信息
     */
    public MerchantRegistration findByRequestNo(String requestNo) {
        String sql = "SELECT * FROM merchant_registration WHERE request_no = ?";
        List<MerchantRegistration> results = jdbcTemplate.query(sql, getRowMapper(), requestNo);
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * 根据商户编号查询商户入网信息
     */
    public MerchantRegistration findByMerchantNo(String merchantNo) {
        String sql = "SELECT * FROM merchant_registration WHERE merchant_no = ?";
        List<MerchantRegistration> results = jdbcTemplate.query(sql, getRowMapper(), merchantNo);
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * 查询所有商户入网信息
     */
    public List<MerchantRegistration> findAll() {
        String sql = "SELECT * FROM merchant_registration ORDER BY id DESC";
        return jdbcTemplate.query(sql, getRowMapper());
    }

    /**
     * 初始化数据表
     */
    public void initTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS merchant_registration (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "request_no VARCHAR(64), " +
                "application_no VARCHAR(64), " +
                "application_status VARCHAR(32), " +
                "merchant_no VARCHAR(32), " +
                "business_role VARCHAR(32), " +
                "parent_merchant_no VARCHAR(32), " +
                "licence_url TEXT, " +
                "legal_licence_front_url TEXT, " +
                "legal_licence_back_url TEXT, " +
                "sign_name VARCHAR(128), " +
                "sign_type VARCHAR(32), " +
                "licence_no VARCHAR(64), " +
                "short_name VARCHAR(64), " +
                "legal_name VARCHAR(64), " +
                "legal_licence_type VARCHAR(32), " +
                "legal_licence_no VARCHAR(64), " +
                "contact_name VARCHAR(64), " +
                "contact_mobile VARCHAR(32), " +
                "contact_email VARCHAR(128), " +
                "contact_licence_no VARCHAR(64), " +
                "admin_email VARCHAR(128), " +
                "admin_mobile VARCHAR(32), " +
                "primary_industry_category VARCHAR(32), " +
                "secondary_industry_category VARCHAR(32), " +
                "province VARCHAR(32), " +
                "city VARCHAR(32), " +
                "district VARCHAR(32), " +
                "address VARCHAR(255), " +
                "settlement_direction VARCHAR(32), " +
                "bank_code VARCHAR(32), " +
                "bank_account_type VARCHAR(32), " +
                "bank_card_no VARCHAR(64), " +
                "notify_url VARCHAR(255), " +
                "create_time TIMESTAMP, " +
                "update_time TIMESTAMP, " +
                "reject_reason TEXT, " +
                "product_info TEXT, " +
                "extra_info TEXT" +
                ")");
    }

    /**
     * 获取行映射器
     */
    private RowMapper<MerchantRegistration> getRowMapper() {
        return (rs, rowNum) -> {
            MerchantRegistration registration = new MerchantRegistration();
            registration.setId(rs.getLong("id"));
            registration.setRequestNo(rs.getString("request_no"));
            registration.setApplicationNo(rs.getString("application_no"));
            registration.setApplicationStatus(rs.getString("application_status"));
            registration.setMerchantNo(rs.getString("merchant_no"));
            registration.setBusinessRole(rs.getString("business_role"));
            registration.setParentMerchantNo(rs.getString("parent_merchant_no"));
            registration.setLicenceUrl(rs.getString("licence_url"));
            registration.setLegalLicenceFrontUrl(rs.getString("legal_licence_front_url"));
            registration.setLegalLicenceBackUrl(rs.getString("legal_licence_back_url"));
            registration.setSignName(rs.getString("sign_name"));
            registration.setSignType(rs.getString("sign_type"));
            registration.setLicenceNo(rs.getString("licence_no"));
            registration.setShortName(rs.getString("short_name"));
            registration.setLegalName(rs.getString("legal_name"));
            registration.setLegalLicenceType(rs.getString("legal_licence_type"));
            registration.setLegalLicenceNo(rs.getString("legal_licence_no"));
            registration.setContactName(rs.getString("contact_name"));
            registration.setContactMobile(rs.getString("contact_mobile"));
            registration.setContactEmail(rs.getString("contact_email"));
            registration.setContactLicenceNo(rs.getString("contact_licence_no"));
            registration.setAdminEmail(rs.getString("admin_email"));
            registration.setAdminMobile(rs.getString("admin_mobile"));
            registration.setPrimaryIndustryCategory(rs.getString("primary_industry_category"));
            registration.setSecondaryIndustryCategory(rs.getString("secondary_industry_category"));
            registration.setProvince(rs.getString("province"));
            registration.setCity(rs.getString("city"));
            registration.setDistrict(rs.getString("district"));
            registration.setAddress(rs.getString("address"));
            registration.setSettlementDirection(rs.getString("settlement_direction"));
            registration.setBankCode(rs.getString("bank_code"));
            registration.setBankAccountType(rs.getString("bank_account_type"));
            registration.setBankCardNo(rs.getString("bank_card_no"));
            registration.setNotifyUrl(rs.getString("notify_url"));
            registration.setCreateTime(rs.getTimestamp("create_time"));
            registration.setUpdateTime(rs.getTimestamp("update_time"));
            registration.setRejectReason(rs.getString("reject_reason"));
            registration.setProductInfo(rs.getString("product_info"));
            registration.setExtraInfo(rs.getString("extra_info"));
            return registration;
        };
    }
} 