package com.example.blob_storage_service.exception;

/**
 * Lançada quando ocorre algum problema ao enviar o PDF para o Azure Blob Storage
 */
public class AzureUploadException extends RuntimeException {

    public AzureUploadException(String message) {
        super(message);
    }

    public AzureUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
