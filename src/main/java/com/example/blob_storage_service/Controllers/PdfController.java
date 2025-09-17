package com.example.blob_storage_service.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.blob_storage_service.Services.AzureBlobPdfService;
import com.example.blob_storage_service.Models.DTOs.PdfMetadataDTO;
import com.example.blob_storage_service.exception.*;

import java.util.Map;

@RestController // Define que esta classe é um REST Controller
@RequestMapping("/api-blob/pdfs") //  Prefixo para todos os endpoints deste controller
public class PdfController {

    private final AzureBlobPdfService pdfService;

    @Autowired //  Injeta o serviço no controller
    public PdfController(AzureBlobPdfService pdfService) {
        this.pdfService = pdfService;
    }

    /**
     * Endpoint para upload de PDF
     * Recebe o arquivo enviado via Multipart
     */
    @PostMapping("/upload") // Mapeia POST /api/pdfs/upload
    public ResponseEntity<?> uploadPdf(@RequestParam("file") MultipartFile file) {
        try {
            //  Chama o serviço para processar o upload
            PdfMetadataDTO dto = pdfService.uploadPdf(file);

            //  Retorna 200 OK com os dados do arquivo enviado
            return ResponseEntity.ok(dto);

        } catch (InvalidPdfException e) {
            //  Caso o arquivo não seja PDF ou esteja vazio
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));

        } catch (AzureUploadException | DBSaveException e) {
            //  Caso haja erro no upload para o Azure ou ao salvar no banco
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
