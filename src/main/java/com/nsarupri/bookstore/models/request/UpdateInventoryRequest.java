package com.nsarupri.bookstore.models.request;

public class UpdateInventoryRequest {
    private Integer inventoryId;
    private Integer bookId;
    private Integer quantity;
    private Double price;

    public UpdateInventoryRequest() {
    }

    public UpdateInventoryRequest(Integer inventoryId, Integer bookId, Integer quantity, Double price) {
        this.inventoryId = inventoryId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
