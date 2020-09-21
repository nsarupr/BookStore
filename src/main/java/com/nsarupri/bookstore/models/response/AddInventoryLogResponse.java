package com.nsarupri.bookstore.models.response;

import com.nsarupri.bookstore.constants.ResponseStatus;
import com.nsarupri.bookstore.models.entity.InventoryLog;

public class AddInventoryLogResponse extends Response {
    private InventoryLog inventoryLog;

    public AddInventoryLogResponse() {
    }

    public AddInventoryLogResponse(boolean success, ResponseStatus responseStatus, String error, String notes, InventoryLog inventoryLog) {
        super(success, responseStatus, error, notes);
        this.inventoryLog = inventoryLog;
    }

    public InventoryLog getInventoryLog() {
        return inventoryLog;
    }

    public void setInventoryLog(InventoryLog inventoryLog) {
        this.inventoryLog = inventoryLog;
    }
}
