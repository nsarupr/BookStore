package com.nsarupri.bookstore.models.response;

import com.nsarupri.bookstore.constants.ResponseStatus;
import com.nsarupri.bookstore.models.entity.Book;

import java.util.List;

public class GetBooksResponse extends Response{
    private List<Book> books;
    private Integer page;
    private Integer size;

    public GetBooksResponse() {
    }

    public GetBooksResponse(boolean success, ResponseStatus responseStatus, String error, String notes, List<Book> books, Integer page, Integer size) {
        super(success, responseStatus, error, notes);
        this.books = books;
        this.page = page;
        this.size = size;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
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
