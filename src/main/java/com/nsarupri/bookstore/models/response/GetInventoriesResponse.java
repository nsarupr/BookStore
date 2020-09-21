package com.nsarupri.bookstore.models.response;

import com.nsarupri.bookstore.constants.ResponseStatus;
import com.nsarupri.bookstore.models.entity.Inventory;

import java.util.List;

public class GetInventoriesResponse extends Response {
    private List<Inventory> inventories;
    private Integer page;
    private Integer size;

    public GetInventoriesResponse() {
    }

    public GetInventoriesResponse(boolean success, ResponseStatus responseStatus, String error, String notes, List<Inventory> inventories, Integer page, Integer size) {
        super(success, responseStatus, error, notes);
        this.inventories = inventories;
        this.page = page;
        this.size = size;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
