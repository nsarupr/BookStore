package com.nsarupri.bookstore.dao.impl;

import com.nsarupri.bookstore.dao.BooksDal;
import com.nsarupri.bookstore.models.entity.Book;
import com.nsarupri.bookstore.models.query.BookQuery;
import com.nsarupri.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class BooksMySqlImpl implements BooksDal {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getBooks(BookQuery bookQuery) throws Exception{
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);

        Path<String> isbnPath = book.get("isbn");
        Path<String> authorPath = book.get("author");
        Path<String> titlePath = book.get("title");

        List<Predicate> predicates = new ArrayList<>();

        if (null != bookQuery.getIsbn()) {
            predicates.add(cb.equal(isbnPath, bookQuery.getIsbn()));
        }
        if (null != bookQuery.getAuthor()) {
            predicates.add(cb.like(authorPath, "%" + bookQuery.getAuthor() + "%"));
        }

        if (null != bookQuery.getTitle()) {
            predicates.add(cb.like(titlePath, "%" + bookQuery.getTitle() + "%"));
        }

        if (predicates.size() > 0)
            query.select(book)
                .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
        else
            query.select(book);

        return entityManager.createQuery(query)
                .setFirstResult(bookQuery.getPage())
                .setMaxResults(bookQuery.getSize())
                .getResultList();
    }

    @Override
    public Book getBook(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Book addBook(Book book) throws Exception {
        Book existingBook = getBook(book.getIsbn());

        if (null != existingBook && null != existingBook.getIsbn() && book.getIsbn().equals(existingBook.getIsbn()))
            throw new Exception("Book Already Exists.");
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }
}
