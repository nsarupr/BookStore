package com.nsarupri.bookstore.controllers;

import com.nsarupri.bookstore.models.request.AddBookRequest;
import com.nsarupri.bookstore.models.response.Response;
import com.nsarupri.bookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<Response> getBooks(@RequestParam(value = "isbn", required = false) String isbn,
                                             @RequestParam(value = "title", required = false) String title,
                                             @RequestParam(value = "author", required = false) String author,
                                             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                             @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        Response response = bookService.getBooks(isbn, author, title, page, size);
        return new ResponseEntity<>(response, response.getResponseStatus().getStatus());
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<Response> getBook(@PathVariable(value = "id") int id) {
        Response response = bookService.getBook(id);
        return new ResponseEntity<>(response, response.getResponseStatus().getStatus());
    }

    @GetMapping(value = "/id/{id}/quantity")
    public ResponseEntity<Response> getBookQuantity(@PathVariable(value = "id") int id) {
        Response response = bookService.getBook(id);
        return new ResponseEntity<>(response, response.getResponseStatus().getStatus());
    }

    @GetMapping(value = "/isbn/{isbn}")
    public ResponseEntity<Response> getBookByIsbn(@PathVariable("isbn") String isbn) {
        Response response = bookService.getBookByIsbn(isbn);
        return new ResponseEntity<>(response, response.getResponseStatus().getStatus());
    }

    @GetMapping(value = "/author/{author}")
    public ResponseEntity<Response> getBooksByAuthor(@PathVariable("author") String author,
                                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                     @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        Response response = bookService.getBooksByAuthor(author, page, size);
        return new ResponseEntity<>(response, response.getResponseStatus().getStatus());
    }

    @GetMapping(value = "/title/{title}")
    public ResponseEntity<Response> getBooksByTitle(@PathVariable("title") String title,
                                                    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                    @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        Response response = bookService.getBooksByTitle(title, page, size);
        return new ResponseEntity<>(response, response.getResponseStatus().getStatus());
    }

    @PostMapping
    public ResponseEntity<Response> addBook(@RequestBody AddBookRequest addBookRequest) {
        Response response = bookService.addBook(addBookRequest);
        return new ResponseEntity<>(response, response.getResponseStatus().getStatus());
    }

    @PostMapping(value = "/buy/{bookId}")
    public ResponseEntity<Response> buyBook(@RequestParam(value = "bookId", required = true) int bookId) {
        Response response = bookService.buyBook(bookId);
        return new ResponseEntity<>(response, response.getResponseStatus().getStatus());
    }
}
