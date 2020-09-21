package com.nsarupri.bookstore.constants;

import org.springframework.http.HttpStatus;

public enum ResponseStatus {
    STATUS_2XX_CREATED(HttpStatus.CREATED),
    STATUS_2XX_OK(HttpStatus.OK),
    STATUS_4XX_FAILED(HttpStatus.EXPECTATION_FAILED),
    STATUS_4XX_BAD_REQUEST(HttpStatus.BAD_REQUEST),
    STATUS_4XX_DUPLICATE(HttpStatus.CONFLICT),
    STATUS_4XX_NOT_FOUND(HttpStatus.NOT_FOUND),
    STATUS_5XX(HttpStatus.INTERNAL_SERVER_ERROR);
    HttpStatus status;

    ResponseStatus() {
    }

    ResponseStatus(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
