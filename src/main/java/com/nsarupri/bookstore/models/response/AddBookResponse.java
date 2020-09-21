package com.nsarupri.bookstore.models.response;

import com.nsarupri.bookstore.constants.ResponseStatus;
import com.nsarupri.bookstore.models.entity.Book;

public class AddBookResponse extends Response {
    private Book book;

    public AddBookResponse() {
    }

    public AddBookResponse(boolean success, ResponseStatus responseStatus, String error, String notes, Book book) {
        super(success, responseStatus, error, notes);
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
