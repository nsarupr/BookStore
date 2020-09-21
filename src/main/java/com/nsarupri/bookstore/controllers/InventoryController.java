package com.nsarupri.bookstore.controllers;

import com.nsarupri.bookstore.models.request.AddInventoryRequest;
import com.nsarupri.bookstore.models.response.Response;
import com.nsarupri.bookstore.services.InventoryService;
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
@RequestMapping(value = "/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<Response> getInventories(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                   @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        Response response = inventoryService.getInventories(page, size);
        return new ResponseEntity<>(response, response.getResponseStatus().getStatus());
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<Response> getInventory(@PathVariable("id") int id) {
        Response response = inventoryService.getInventory(id);
        return new ResponseEntity<>(response, response.getResponseStatus().getStatus());
    }

    @GetMapping("/bookId/{bookId}")
    public ResponseEntity<Response> getInventoryByBookId(@PathVariable("bookId") int bookId) {
        Response response = inventoryService.getInventoryByBookId(bookId);
        return new ResponseEntity<>(response, response.getResponseStatus().getStatus());
    }

    @PostMapping
    public ResponseEntity<Response> addInventory(@RequestBody AddInventoryRequest addInventoryRequest) {
        Response response = inventoryService.addInventory(addInventoryRequest);
        return new ResponseEntity<>(response, response.getResponseStatus().getStatus());
    }
}
