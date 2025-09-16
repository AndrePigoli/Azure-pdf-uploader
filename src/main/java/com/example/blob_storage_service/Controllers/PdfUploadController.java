package com.example.blob_storage_service.Controllers;

import com.example.blob_storage_service.Services.AzureBlobService;
import com.example.blob_storage_service.exception.PdfUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class PdfUploadController {

    private final AzureBlobService azureBlobService;

    public PdfUploadController(AzureBlobService azureBlobService) {
        this.azureBlobService = azureBlobService;
    }

    @PostMapping("/pdf")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) throws IOException {
        // Verifica se é PDF
        if (!file.getOriginalFilename().endsWith(".pdf")) {
            throw new PdfUploadException("Apenas arquivos PDF são permitidos.");
        }

        // Chama o service para enviar o arquivo
        String url = azureBlobService.uploadPdf(file);
        return ResponseEntity.status(HttpStatus.OK).body("Arquivo enviado com sucesso: " + url);
    }
}
