package com.nsarupri.bookstore.dao.impl;

import com.nsarupri.bookstore.dao.InventoryDal;
import com.nsarupri.bookstore.models.entity.Inventory;
import com.nsarupri.bookstore.models.query.InventoryQuery;
import com.nsarupri.bookstore.models.query.UpdateInventoryQuery;
import com.nsarupri.bookstore.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class InventoryMySqlImpl implements InventoryDal {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public List<Inventory> getInventories(InventoryQuery inventoryQuery) throws Exception {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Inventory> query = cb.createQuery(Inventory.class);
        Root<Inventory> inventory = query.from(Inventory.class);

        query.select(inventory);
        return entityManager.createQuery(query)
                .setFirstResult(inventoryQuery.getPage())
                .setMaxResults(inventoryQuery.getSize())
                .getResultList();
    }

    @Override
    public Inventory getInventoryByBookId(int bookId) {
        return inventoryRepository.findByBookId(bookId);
    }

    @Override
    public Inventory addInventory(Inventory inventory) throws Exception {
        Inventory existingInventory = getInventoryByBookId(inventory.getBookId());

        if (null != existingInventory && inventory.getBookId() == existingInventory.getBookId())
            throw new Exception("Inventory Already exists.");
        Inventory savedInventory = inventoryRepository.save(inventory);
        return savedInventory;
    }

    @Override
    public Inventory updateInventory(UpdateInventoryQuery updateInventoryQuery) throws Exception {
        entityManager.setProperty("javax.persistence.query.timeout", 6000);
        Inventory inventory = entityManager.find(Inventory.class, updateInventoryQuery.getInventoryId());
        entityManager.lock(inventory, LockModeType.PESSIMISTIC_WRITE);
        if (null != updateInventoryQuery.getPrice())
            inventory.setPrice(updateInventoryQuery.getPrice());
        if (null != updateInventoryQuery.getQuantity()) {
            if (updateInventoryQuery.getQuantity() + inventory.getQuantity() < 0) {
                throw new Exception("Order Quantity exceeds the Inventory Quantity");
            }
            inventory.setQuantity(updateInventoryQuery.getQuantity() + inventory.getQuantity());
        }
        Inventory savedInventory = inventoryRepository.save(inventory);
        return savedInventory;
    }
}
