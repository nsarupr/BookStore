package com.nsarupri.bookstore.models.query;

public class BookQuery {
    private String isbn;
    private String author;
    private String title;
    private int page;
    private int size;

    public BookQuery() {
    }

    public BookQuery(String isbn, String author, String title, int page, int size) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.page = page;
        this.size = size;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
