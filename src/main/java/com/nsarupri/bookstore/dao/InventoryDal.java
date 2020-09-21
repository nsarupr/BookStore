package com.nsarupri.bookstore.dao;

import com.nsarupri.bookstore.models.entity.Inventory;
import com.nsarupri.bookstore.models.query.InventoryQuery;
import com.nsarupri.bookstore.models.query.UpdateInventoryQuery;

import java.util.List;

public interface InventoryDal {
    List<Inventory> getInventories(InventoryQuery inventoryQuery) throws Exception;
    Inventory getInventoryByBookId(int bookId);
    Inventory addInventory(Inventory inventory) throws Exception;
    Inventory updateInventory(UpdateInventoryQuery updateInventoryQuery) throws  Exception;
}
