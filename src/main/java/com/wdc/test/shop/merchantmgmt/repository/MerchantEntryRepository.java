/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.repository;

import com.wdc.test.shop.merchantmgmt.model.MerchantEntry;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * title: 商户进件仓储<br>
 * description: 负责进件信息的保存、查询、列表，初期用内存实现<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@Repository
public class MerchantEntryRepository {
    private final List<MerchantEntry> entries = new ArrayList<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public MerchantEntry save(MerchantEntry entry) {
        if (entry.getId() == null) {
            entry.setId(idGen.getAndIncrement());
        }
        entry.setCreateTime(new Date());
        entries.add(entry);
        return entry;
    }

    public List<MerchantEntry> findAll() {
        return new ArrayList<>(entries);
    }

    public Optional<MerchantEntry> findById(Long id) {
        return entries.stream().filter(e -> Objects.equals(e.getId(), id)).findFirst();
    }

    public Optional<MerchantEntry> findByRequestNo(String requestNo) {
        return entries.stream().filter(e -> Objects.equals(e.getRequestNo(), requestNo)).findFirst();
    }
} 