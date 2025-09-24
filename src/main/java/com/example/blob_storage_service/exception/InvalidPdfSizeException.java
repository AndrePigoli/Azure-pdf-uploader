package com.example.blob_storage_service.exception;

public class InvalidPdfSizeException extends RuntimeException {
    public InvalidPdfSizeException(String message) {
        super(message);
    }
}
