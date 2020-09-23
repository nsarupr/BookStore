package com.nsarupri.bookstore.controllers;

import com.nsarupri.bookstore.models.request.AddInventoryRequest;
import com.nsarupri.bookstore.models.response.Response;
import com.nsarupri.bookstore.services.InventoryService;
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
@RequestMapping(value = "/inventory")
public class InventoryController {

    @Autowired
    private TaskExecutor executor;

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public DeferredResult<ResponseEntity<Response>> getInventories(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                   @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        DeferredResult<ResponseEntity<Response>> responseEntityDeferredResult = new DeferredResult<ResponseEntity<Response>>();
        Thread thread = new Thread(() -> {
            Response response = inventoryService.getInventories(page, size);
            responseEntityDeferredResult.setResult(new ResponseEntity<>(response, response.getResponseStatus().getStatus()));
        }, "MyThread");
        thread.start();
        return responseEntityDeferredResult;
    }

    @GetMapping(value = "/id/{id}")
    public DeferredResult<ResponseEntity<Response>> getInventory(@PathVariable("id") int id) {
        DeferredResult<ResponseEntity<Response>> responseEntityDeferredResult = new DeferredResult<ResponseEntity<Response>>();
        Thread thread = new Thread(() -> {
            Response response = inventoryService.getInventory(id);
            responseEntityDeferredResult.setResult(new ResponseEntity<>(response, response.getResponseStatus().getStatus()));
        }, "MyThread");
        thread.start();
        return responseEntityDeferredResult;
    }

    @GetMapping("/bookId/{bookId}")
    public DeferredResult<ResponseEntity<Response>> getInventoryByBookId(@PathVariable("bookId") int bookId) {
        DeferredResult<ResponseEntity<Response>> responseEntityDeferredResult = new DeferredResult<ResponseEntity<Response>>();
        Thread thread = new Thread(() -> {
            Response response = inventoryService.getInventoryByBookId(bookId);
            responseEntityDeferredResult.setResult(new ResponseEntity<>(response, response.getResponseStatus().getStatus()));
        }, "MyThread");
        thread.start();
        return responseEntityDeferredResult;
    }

    @PostMapping
    public DeferredResult<ResponseEntity<Response>> addInventory(@RequestBody AddInventoryRequest addInventoryRequest) {
        DeferredResult<ResponseEntity<Response>> responseEntityDeferredResult = new DeferredResult<ResponseEntity<Response>>();
        Thread thread = new Thread(() -> {
            Response response = inventoryService.addInventory(addInventoryRequest);
            responseEntityDeferredResult.setResult(new ResponseEntity<>(response, response.getResponseStatus().getStatus()));
        }, "MyThread");
        thread.start();
        return responseEntityDeferredResult;
    }
}
