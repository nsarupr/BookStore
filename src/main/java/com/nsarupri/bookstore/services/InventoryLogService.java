package com.nsarupri.bookstore.services;

import com.nsarupri.bookstore.constants.ResponseStatus;
import com.nsarupri.bookstore.models.entity.InventoryLog;
import com.nsarupri.bookstore.models.response.AddInventoryLogResponse;
import com.nsarupri.bookstore.models.response.Response;
import com.nsarupri.bookstore.repository.InventoryLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InventoryLogService {

    @Value("${default.creation.retry}")
    private int defaultCreationRetry;

    private InventoryLogRepository inventoryLogRepository;

    @Autowired
    public InventoryLogService(InventoryLogRepository inventoryLogRepository) {
        this.inventoryLogRepository = inventoryLogRepository;
    }

    public AddInventoryLogResponse addInventoryLog(InventoryLog inventoryLog) {
        AddInventoryLogResponse response = new AddInventoryLogResponse();
        int retry = 0;
        while (! response.isSuccess() && retry < defaultCreationRetry) {
            try {
                InventoryLog savedLog = inventoryLogRepository.save(inventoryLog);
                response.setSuccess(true);
                response.setInventoryLog(inventoryLog);
                response.setResponseStatus(ResponseStatus.STATUS_2XX_CREATED);
            } catch (Exception ex) {
                response.setSuccess(false);
                response.setError(ex.getMessage());
                response.setResponseStatus(ResponseStatus.STATUS_4XX_FAILED);
            }
        }
        return response;
    }
}
