package com.nsarupri.bookstore.services;

import com.nsarupri.bookstore.constants.InventoryLogType;
import com.nsarupri.bookstore.constants.ResponseStatus;
import com.nsarupri.bookstore.dao.InventoryDal;
import com.nsarupri.bookstore.models.entity.Inventory;
import com.nsarupri.bookstore.models.entity.InventoryLog;
import com.nsarupri.bookstore.models.query.InventoryQuery;
import com.nsarupri.bookstore.models.query.UpdateInventoryQuery;
import com.nsarupri.bookstore.models.request.AddInventoryRequest;
import com.nsarupri.bookstore.models.request.UpdateInventoryRequest;
import com.nsarupri.bookstore.models.response.AddInventoryLogResponse;
import com.nsarupri.bookstore.models.response.AddInventoryResponse;
import com.nsarupri.bookstore.models.response.GetInventoriesResponse;
import com.nsarupri.bookstore.models.response.GetInventoryResponse;
import com.nsarupri.bookstore.models.response.Response;
import com.nsarupri.bookstore.models.response.UpdateInventoryResponse;
import com.nsarupri.bookstore.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class InventoryService {

    @Value("${default.page.size}")
    private int defaultPageSize;

    private InventoryDal inventoryDal;
    private InventoryRepository inventoryRepository;
    private BookService bookService;
    private InventoryLogService inventoryLogService;

    @Autowired
    public InventoryService(InventoryDal inventoryDal, InventoryRepository inventoryRepository, BookService bookService, InventoryLogService inventoryLogService) {
        this.inventoryDal = inventoryDal;
        this.inventoryRepository = inventoryRepository;
        this.bookService = bookService;
        this.inventoryLogService = inventoryLogService;
    }

    public GetInventoriesResponse getInventories(int page, int size) {
        GetInventoriesResponse response = new GetInventoriesResponse();
        size = getDefaultOrGivenSize(size);
        try {
            List<Inventory> inventories = inventoryDal.getInventories(new InventoryQuery(page, size));
            response.setSuccess(true);
            response.setResponseStatus(ResponseStatus.STATUS_2XX_OK);
            response.setInventories(inventories);
            response.setPage(page);
            response.setSize(inventories.size());
        } catch (Exception ex) {
            response.setSuccess(false);
            response.setResponseStatus(ResponseStatus.STATUS_4XX_NOT_FOUND);
            response.setError(ex.getMessage());
        }

        return response;
    }

    public GetInventoryResponse getInventory(int id) {
        GetInventoryResponse response = new GetInventoryResponse();

        try {
            Inventory inventory = inventoryRepository.findById(id).get();
            response.setSuccess(true);
            response.setResponseStatus(ResponseStatus.STATUS_2XX_OK);
            response.setInventory(inventory);
        } catch (NoSuchElementException ex) {
            response.setSuccess(false);
            response.setResponseStatus(ResponseStatus.STATUS_4XX_NOT_FOUND);
            response.setError("No Inventory With ID Found");
        } catch (Exception ex) {
            response.setSuccess(false);
            response.setResponseStatus(ResponseStatus.STATUS_5XX);
            response.setError(ex.getMessage());
        }
        return response;
    }

    public GetInventoryResponse getInventoryByBookId(int bookId) {
        GetInventoryResponse response = new GetInventoryResponse();

        try {
            Inventory inventory = inventoryDal.getInventoryByBookId(bookId);
            response.setSuccess(true);
            response.setResponseStatus(ResponseStatus.STATUS_2XX_OK);
            response.setInventory(inventory);
        } catch (Exception ex) {
            response.setSuccess(false);
            response.setResponseStatus(ResponseStatus.STATUS_4XX_NOT_FOUND);
            response.setError(ex.getMessage());
        }
        return response;
    }

    public AddInventoryResponse addInventory(AddInventoryRequest addInventoryRequest) {
        AddInventoryResponse response = new AddInventoryResponse();
        StringBuilder errorBuilder = new StringBuilder();
        if (null == addInventoryRequest.getBookId())
            errorBuilder.append("Book ID not present.");
        if (null == addInventoryRequest.getQuantity())
            errorBuilder.append("Quantity not present.");
        if (null == addInventoryRequest.getPrice())
            errorBuilder.append("Price not present.");

        if ( ! errorBuilder.toString().isEmpty()) {
            response.setSuccess(false);
            response.setError(errorBuilder.toString());
            response.setResponseStatus(ResponseStatus.STATUS_4XX_BAD_REQUEST);
            return response;
        }

        try {
            Response bookResponse = bookService.getBook(addInventoryRequest.getBookId());
            if (bookResponse.isSuccess()) {
                Inventory addedInventory = inventoryDal.addInventory(new Inventory(addInventoryRequest.getBookId(), addInventoryRequest.getPrice(), addInventoryRequest.getQuantity()));
                AddInventoryLogResponse logInventory = inventoryLogService.addInventoryLog(createAddInventoryInstance(addedInventory));
                response.setSuccess(true);
                response.setResponseStatus(ResponseStatus.STATUS_2XX_CREATED);
                response.setInventory(addedInventory);
                response.setInventoryLog(logInventory.getInventoryLog());
            } else {
                response.setSuccess(bookResponse.isSuccess());
                response.setError(bookResponse.getError());
                response.setResponseStatus(bookResponse.getResponseStatus());
            }
        } catch (Exception ex) {
            response.setSuccess(false);
            response.setError(ex.getMessage());
            response.setResponseStatus(ResponseStatus.STATUS_4XX_DUPLICATE);
        }
        return response;
    }

    public UpdateInventoryResponse updateInventory(UpdateInventoryRequest updateInventoryRequest) {
        UpdateInventoryResponse response = new UpdateInventoryResponse();
        UpdateInventoryQuery updateInventoryQuery = new UpdateInventoryQuery();
        if (null == updateInventoryRequest.getInventoryId() && null == updateInventoryRequest.getBookId()) {
            response.setSuccess(false);
            response.setError("No Inventory/Book Id Provided");
            response.setResponseStatus(ResponseStatus.STATUS_4XX_BAD_REQUEST);
            return response;
        }
        updateInventoryQuery.setInventoryId(updateInventoryRequest.getInventoryId());

        if (null == updateInventoryRequest.getPrice() && null == updateInventoryRequest.getQuantity()) {
            response.setSuccess(false);
            response.setError("No Data provided for Update.");
            response.setResponseStatus(ResponseStatus.STATUS_4XX_BAD_REQUEST);
        }

        updateInventoryQuery.setPrice(updateInventoryRequest.getPrice());
        updateInventoryQuery.setQuantity(updateInventoryRequest.getQuantity());

        try {
            Inventory currentInventory = new Inventory();
            if (null != updateInventoryRequest.getInventoryId()) {
                currentInventory = ((GetInventoryResponse) getInventory(updateInventoryRequest.getInventoryId())).getInventory();
            } else {
                currentInventory = ((GetInventoryResponse) getInventoryByBookId(updateInventoryRequest.getBookId())).getInventory();
            }
            updateInventoryQuery.setInventoryId(currentInventory.getUid());
            Inventory updatedInventory = inventoryDal.updateInventory(updateInventoryQuery);
            AddInventoryLogResponse logInventory =
                    inventoryLogService.addInventoryLog(createUpdateInventoryInstance(currentInventory, updatedInventory));
            response.setSuccess(true);
            response.setInventory(updatedInventory);
            response.setInventoryLog(logInventory.getInventoryLog());
            response.setResponseStatus(ResponseStatus.STATUS_2XX_OK);
        } catch (Exception ex) {
            response.setSuccess(false);
            response.setError(ex.getMessage());
            response.setResponseStatus(ResponseStatus.STATUS_4XX_FAILED);
        }
        return null;
    }

    private int getDefaultOrGivenSize(int size) {
        if (size == 0)
            size = defaultPageSize;
        return size;
    }

    private InventoryLog createAddInventoryInstance(Inventory inventory) {
        return new InventoryLog(
                InventoryLogType.ADD,
                inventory.getUid(),
                0,
                0,
                inventory.getQuantity(),
                inventory.getPrice()
        );
    }

    private InventoryLog createUpdateInventoryInstance(Inventory previousInventory, Inventory updatedInventory) {
        return new InventoryLog(
                InventoryLogType.UPDATE,
                updatedInventory.getUid(),
                previousInventory.getQuantity(),
                previousInventory.getPrice(),
                updatedInventory.getQuantity(),
                updatedInventory.getPrice()
        );
    }
}
