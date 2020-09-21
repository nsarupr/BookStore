package com.nsarupri.bookstore.models.response;

import com.nsarupri.bookstore.constants.ResponseStatus;
import com.nsarupri.bookstore.models.entity.Inventory;

public class GetInventoryResponse extends Response{
    private Inventory inventory;

    public GetInventoryResponse() {
    }

    public GetInventoryResponse(boolean success, ResponseStatus responseStatus, String error, String notes, Inventory inventory) {
        super(success, responseStatus, error, notes);
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
