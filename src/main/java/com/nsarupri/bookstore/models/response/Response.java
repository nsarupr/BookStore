package com.nsarupri.bookstore.models.response;

import com.nsarupri.bookstore.constants.ResponseStatus;

public class Response {
    private boolean success;
    private ResponseStatus responseStatus;
    private String error;
    private String notes;

    public Response() {
    }

    public Response(boolean success, ResponseStatus responseStatus, String error, String notes) {
        this.success = success;
        this.responseStatus = responseStatus;
        this.error = error;
        this.notes = notes;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
