package com.nsarupri.bookstore.models.request;

public class AddInventoryRequest {
    private Integer bookId;
    private Integer quantity;
    private Double price;

    public AddInventoryRequest() {
    }

    public AddInventoryRequest(Integer bookId, Integer quantity, Double price) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
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
