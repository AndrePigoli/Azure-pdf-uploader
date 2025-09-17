package com.example.blob_storage_service.exception;

public class DBSaveException extends RuntimeException {

    public DBSaveException(String message) {
        super(message);
    }

    public DBSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
