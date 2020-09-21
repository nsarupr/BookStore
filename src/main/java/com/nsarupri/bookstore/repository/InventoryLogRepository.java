package com.nsarupri.bookstore.repository;

import com.nsarupri.bookstore.models.entity.InventoryLog;
import org.springframework.data.repository.CrudRepository;

public interface InventoryLogRepository extends CrudRepository<InventoryLog, Integer> {
}
