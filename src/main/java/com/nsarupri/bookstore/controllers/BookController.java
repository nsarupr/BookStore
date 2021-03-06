package com.nsarupri.bookstore.controllers;

import com.nsarupri.bookstore.models.request.AddBookRequest;
import com.nsarupri.bookstore.models.response.Response;
import com.nsarupri.bookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private TaskExecutor executor;

    @Autowired
    private BookService bookService;

    @GetMapping
    public DeferredResult<ResponseEntity<Response>> getBooks(@RequestParam(value = "isbn", required = false) String isbn,
                                                            @RequestParam(value = "title", required = false) String title,
                                                            @RequestParam(value = "author", required = false) String author,
                                                            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                            @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        DeferredResult<ResponseEntity<Response>> responseEntityDeferredResult = new DeferredResult<ResponseEntity<Response>>();
        Thread thread = new Thread(() -> {
            Response response = bookService.getBooks(isbn, author, title, page, size);
            responseEntityDeferredResult.setResult(new ResponseEntity<>(response, response.getResponseStatus().getStatus()));
        }, "MyThread");
        thread.start();
        return responseEntityDeferredResult;
    }

    @GetMapping(value = "/id/{id}")
    public DeferredResult<ResponseEntity<Response>> getBook(@PathVariable(value = "id") int id) {
        DeferredResult<ResponseEntity<Response>> responseEntityDeferredResult = new DeferredResult<ResponseEntity<Response>>();
        Thread thread = new Thread(() -> {
            Response response = bookService.getBook(id);
            responseEntityDeferredResult.setResult(new ResponseEntity<>(response, response.getResponseStatus().getStatus()));
        }, "MyThread");
        thread.start();
        return responseEntityDeferredResult;
    }

    @GetMapping(value = "/id/{id}/quantity")
    public DeferredResult<ResponseEntity<Response>> getBookQuantity(@PathVariable(value = "id") int id) {
        DeferredResult<ResponseEntity<Response>> responseEntityDeferredResult = new DeferredResult<ResponseEntity<Response>>();
        Thread thread = new Thread(() -> {
            Response response = bookService.getBook(id);
            responseEntityDeferredResult.setResult(new ResponseEntity<>(response, response.getResponseStatus().getStatus()));
        }, "MyThread");
        thread.start();
        return responseEntityDeferredResult;
    }

    @GetMapping(value = "/isbn/{isbn}")
    public DeferredResult<ResponseEntity<Response>> getBookByIsbn(@PathVariable("isbn") String isbn) {
        DeferredResult<ResponseEntity<Response>> responseEntityDeferredResult = new DeferredResult<ResponseEntity<Response>>();
        Thread thread = new Thread(() -> {
            Response response = bookService.getBookByIsbn(isbn);
            responseEntityDeferredResult.setResult(new ResponseEntity<>(response, response.getResponseStatus().getStatus()));
        }, "MyThread");
        thread.start();
        return responseEntityDeferredResult;
    }

    @GetMapping(value = "/author/{author}")
    public DeferredResult<ResponseEntity<Response>> getBooksByAuthor(@PathVariable("author") String author,
                                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                     @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        DeferredResult<ResponseEntity<Response>> responseEntityDeferredResult = new DeferredResult<ResponseEntity<Response>>();
        Thread thread = new Thread(() -> {
            Response response = bookService.getBooksByAuthor(author, page, size);
            responseEntityDeferredResult.setResult(new ResponseEntity<>(response, response.getResponseStatus().getStatus()));
        }, "MyThread");
        thread.start();
        return responseEntityDeferredResult;
    }

    @GetMapping(value = "/title/{title}")
    public DeferredResult<ResponseEntity<Response>> getBooksByTitle(@PathVariable("title") String title,
                                                    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                    @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        DeferredResult<ResponseEntity<Response>> responseEntityDeferredResult = new DeferredResult<ResponseEntity<Response>>();
        Thread thread = new Thread(() -> {
            Response response = bookService.getBooksByTitle(title, page, size);
            responseEntityDeferredResult.setResult(new ResponseEntity<>(response, response.getResponseStatus().getStatus()));
        }, "MyThread");
        thread.start();
        return responseEntityDeferredResult;
    }

    @PostMapping
    public DeferredResult<ResponseEntity<Response>> addBook(@RequestBody AddBookRequest addBookRequest) {
        DeferredResult<ResponseEntity<Response>> responseEntityDeferredResult = new DeferredResult<ResponseEntity<Response>>();
        Thread thread = new Thread(() -> {
            Response response = bookService.addBook(addBookRequest);
            responseEntityDeferredResult.setResult(new ResponseEntity<>(response, response.getResponseStatus().getStatus()));
        }, "MyThread");
        thread.start();
        return responseEntityDeferredResult;
    }

    @PostMapping(value = "/buy/{bookId}")
    public DeferredResult<ResponseEntity<Response>> buyBook(@RequestParam(value = "bookId", required = true) int bookId) {
        DeferredResult<ResponseEntity<Response>> responseEntityDeferredResult = new DeferredResult<ResponseEntity<Response>>();
        Thread thread = new Thread(() -> {
            Response response = bookService.buyBook(bookId);
            responseEntityDeferredResult.setResult(new ResponseEntity<>(response, response.getResponseStatus().getStatus()));
        }, "MyThread");
        thread.start();
        return responseEntityDeferredResult;
    }
}
