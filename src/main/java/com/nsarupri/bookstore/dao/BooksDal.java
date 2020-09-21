package com.nsarupri.bookstore.dao;

import com.nsarupri.bookstore.models.query.BookQuery;
import com.nsarupri.bookstore.models.entity.Book;

import java.util.List;

public interface BooksDal {
    List<Book> getBooks(BookQuery bookQuery) throws Exception;
    Book getBook(String isbn);
    Book addBook(Book book) throws Exception;
}