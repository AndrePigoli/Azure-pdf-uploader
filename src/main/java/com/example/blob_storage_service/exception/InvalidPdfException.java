package com.example.blob_storage_service.exception;


//Lançada quando o arquivo enviado não é um PDF válido ou está vazio
public class InvalidPdfException extends RuntimeException {

    public InvalidPdfException(String message) {
        super(message);
    }

    public InvalidPdfException(String message, Throwable cause) {
        super(message, cause);
    }
}