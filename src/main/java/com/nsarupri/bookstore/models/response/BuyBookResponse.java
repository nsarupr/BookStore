package com.nsarupri.bookstore.models.response;

import com.nsarupri.bookstore.constants.ResponseStatus;
import com.nsarupri.bookstore.models.entity.Book;

public class BuyBookResponse extends Response {
    private Book bookBought;

    public BuyBookResponse() {
    }

    public BuyBookResponse(boolean success, ResponseStatus responseStatus, String error, String notes, Book bookBought) {
        super(success, responseStatus, error, notes);
        this.bookBought = bookBought;
    }

    public Book getBookBought() {
        return bookBought;
    }

    public void setBookBought(Book bookBought) {
        this.bookBought = bookBought;
    }
}
