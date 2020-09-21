package com.nsarupri.bookstore.repository;

import com.nsarupri.bookstore.models.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    Book findByIsbn(String isbn);
    List<Book> findAllByAuthor(String author);
    List<Book> findAllByTitle(String title);
}
