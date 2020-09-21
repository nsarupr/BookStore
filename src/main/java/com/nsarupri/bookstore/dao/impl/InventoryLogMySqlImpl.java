package com.nsarupri.bookstore.dao.impl;

import com.nsarupri.bookstore.dao.InventoryLogDal;
import com.nsarupri.bookstore.models.entity.InventoryLog;
import com.nsarupri.bookstore.repository.InventoryLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryLogMySqlImpl implements InventoryLogDal {

    private InventoryLogRepository inventoryLogRepository;

    @Autowired
    public InventoryLogMySqlImpl(InventoryLogRepository inventoryLogRepository) {
        this.inventoryLogRepository = inventoryLogRepository;
    }

    @Override
    public InventoryLog addLog(InventoryLog inventoryLog) {
        return inventoryLogRepository.save(inventoryLog);
    }
}
