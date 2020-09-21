package com.nsarupri.bookstore.models.response;

import com.nsarupri.bookstore.constants.ResponseStatus;
import com.nsarupri.bookstore.models.entity.Inventory;
import com.nsarupri.bookstore.models.entity.InventoryLog;

public class UpdateInventoryResponse extends Response{
    private Inventory inventory;
    private InventoryLog inventoryLog;

    public UpdateInventoryResponse() {
    }

    public UpdateInventoryResponse(boolean success, ResponseStatus responseStatus, String error, String notes, Inventory inventory, InventoryLog inventoryLog) {
        super(success, responseStatus, error, notes);
        this.inventory = inventory;
        this.inventoryLog = inventoryLog;
    }


    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public InventoryLog getInventoryLog() {
        return inventoryLog;
    }

    public void setInventoryLog(InventoryLog inventoryLog) {
        this.inventoryLog = inventoryLog;
    }
}
