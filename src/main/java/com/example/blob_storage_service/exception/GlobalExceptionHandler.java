package com.example.blob_storage_service.exception;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidPdfException.class)
    public ResponseEntity<Map<String,String>> handleInvalidPdf(InvalidPdfException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(AzureUploadException.class)
    public ResponseEntity<Map<String,String>> handleAzureUpload(AzureUploadException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(DBSaveException.class)
    public ResponseEntity<Map<String,String>> handleDatabaseSave(DBSaveException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", ex.getMessage()));
    }
}
