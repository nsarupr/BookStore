package com.nsarupri.bookstore.repository;

import com.nsarupri.bookstore.models.entity.Inventory;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<Inventory, Integer> {
    Inventory findByBookId(int bookId);
}
