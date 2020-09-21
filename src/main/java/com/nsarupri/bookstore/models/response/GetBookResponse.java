package com.nsarupri.bookstore.models.response;

import com.nsarupri.bookstore.constants.ResponseStatus;
import com.nsarupri.bookstore.models.entity.Book;

public class GetBookResponse extends Response{
    private Book book;

    public GetBookResponse() {
    }

    public GetBookResponse(boolean success, ResponseStatus responseStatus, String error, String notes, Book book) {
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
