package com.nsarupri.bookstore.services;

import com.nsarupri.bookstore.constants.ResponseStatus;
import com.nsarupri.bookstore.dao.BooksDal;
import com.nsarupri.bookstore.models.entity.Book;
import com.nsarupri.bookstore.models.query.BookQuery;
import com.nsarupri.bookstore.models.request.AddBookRequest;
import com.nsarupri.bookstore.models.request.BuyBookRequest;
import com.nsarupri.bookstore.models.request.UpdateInventoryRequest;
import com.nsarupri.bookstore.models.response.AddBookResponse;
import com.nsarupri.bookstore.models.response.BuyBookResponse;
import com.nsarupri.bookstore.models.response.GetBookResponse;
import com.nsarupri.bookstore.models.response.GetBooksResponse;
import com.nsarupri.bookstore.models.response.Response;
import com.nsarupri.bookstore.models.response.UpdateInventoryResponse;
import com.nsarupri.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {

    @Value("${default.page.size}")
    private int defaultPageSize;

    private BooksDal booksDal;
    private BookRepository bookRepository;
    private InventoryService inventoryService;

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Autowired
    public BookService(BooksDal booksDal, BookRepository bookRepository) {
        this.booksDal = booksDal;
        this.bookRepository = bookRepository;
    }

    public GetBooksResponse getBooks(String isbn, String author, String title, int page, int size) {
        GetBooksResponse response = new GetBooksResponse();
        size = getDefaultOrGivenSize(size);

        try {
            List<Book> books = booksDal.getBooks(new BookQuery(isbn, author, title, page, size));
            response.setSuccess(true);
            response.setResponseStatus(ResponseStatus.STATUS_2XX_OK);
            response.setBooks(books);
            response.setPage(page);
            response.setSize(books.size());
        } catch (Exception ex) {
            response.setSuccess(false);
            response.setResponseStatus(ResponseStatus.STATUS_4XX_NOT_FOUND);
            response.setError(ex.getMessage());
        }

        return response;
    }

    public GetBookResponse getBook(int id) {
        GetBookResponse response = new GetBookResponse();

        try {
            Book book = bookRepository.findById(id).get();
            response.setSuccess(true);
            response.setResponseStatus(ResponseStatus.STATUS_2XX_OK);
            response.setBook(book);
        } catch (NoSuchElementException ex) {
            response.setSuccess(false);
            response.setResponseStatus(ResponseStatus.STATUS_4XX_NOT_FOUND);
            response.setError("No Book With ID Found");
        } catch (Exception ex) {
            response.setSuccess(false);
            response.setResponseStatus(ResponseStatus.STATUS_5XX);
            response.setError(ex.getMessage());
        }

        return response;
    }

    public GetBookResponse getBookByIsbn(String isbn) {
        GetBookResponse response = new GetBookResponse();

        try {
            Book book = booksDal.getBook(isbn);
            response.setSuccess(true);
            response.setResponseStatus(ResponseStatus.STATUS_2XX_OK);
            response.setBook(book);
        } catch (NoSuchElementException ex) {
            response.setSuccess(false);
            response.setResponseStatus(ResponseStatus.STATUS_4XX_NOT_FOUND);
            response.setError("No Book With ID Found");
        } catch (Exception ex) {
            response.setSuccess(false);
            response.setResponseStatus(ResponseStatus.STATUS_5XX);
            response.setError(ex.getMessage());
        }

        return response;
    }

    public Response getBooksByAuthor(String author, int page, int size) {
        return getBooks(null, author, null, page, size);
    }

    public Response getBooksByTitle(String title, int page, int size) {
        return getBooks(null, null, title, page, size);
    }

    public AddBookResponse addBook(AddBookRequest addBookRequest) {
        AddBookResponse response = new AddBookResponse();
        StringBuilder errorBuilder = new StringBuilder();
        if (null == addBookRequest.getIsbn())
            errorBuilder.append("No ISBN Provided.");
        if (null == addBookRequest.getAuthor())
            errorBuilder.append("No Author Provided.");
        if (null == addBookRequest.getTitle())
            errorBuilder.append("No Title Provided.");

        if ( ! errorBuilder.toString().isEmpty()) {
            response.setSuccess(false);
            response.setError(errorBuilder.toString());
            response.setResponseStatus(ResponseStatus.STATUS_4XX_BAD_REQUEST);
            return response;
        }

        try {
            Book addedBook = booksDal.addBook(
                    new Book(addBookRequest.getIsbn(), addBookRequest.getAuthor(), addBookRequest.getTitle()));
            response.setSuccess(true);
            response.setResponseStatus(ResponseStatus.STATUS_2XX_CREATED);
            response.setBook(addedBook);
        } catch (Exception ex) {
            response.setSuccess(false);
            response.setError(ex.getMessage());
            response.setResponseStatus(ResponseStatus.STATUS_4XX_DUPLICATE);
        }

        return response;
    }

    public BuyBookResponse buyBook(int bookId) {
        BuyBookResponse response = new BuyBookResponse();
        GetBookResponse bookResponse = getBook(bookId);
        if (bookResponse.isSuccess()) {
            UpdateInventoryRequest updateInventoryRequest = new UpdateInventoryRequest();
            updateInventoryRequest.setBookId(bookResponse.getBook().getUid());
            updateInventoryRequest.setQuantity(-1);
            UpdateInventoryResponse updateInventoryResponse = inventoryService.updateInventory(updateInventoryRequest);
            response.setSuccess(updateInventoryResponse.isSuccess());
            response.setResponseStatus(updateInventoryResponse.getResponseStatus());
            response.setError(updateInventoryResponse.getError());
            if (response.isSuccess()) {
                response.setBookBought(bookResponse.getBook());
            }
        } else {
            response.setSuccess(bookResponse.isSuccess());
            response.setError(bookResponse.getError());
            response.setResponseStatus(bookResponse.getResponseStatus());
        }

        return response;
    }

    private int getDefaultOrGivenSize(int size) {
        if (size == 0)
            size = defaultPageSize;
        return size;
    }
}
