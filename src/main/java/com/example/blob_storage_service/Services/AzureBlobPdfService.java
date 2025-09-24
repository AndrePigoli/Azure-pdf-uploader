package com.example.blob_storage_service.Services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobClient;
import com.example.blob_storage_service.Repository.PdfMetadataRepository;
import com.example.blob_storage_service.Models.PdfMetadata;
import com.example.blob_storage_service.Models.DTOs.PdfMetadataDTO;
import com.example.blob_storage_service.exception.*;

import java.io.InputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Service
public class AzureBlobPdfService {

    private final BlobContainerClient containerClient;
    private final PdfMetadataRepository repository;

    @Autowired
    public AzureBlobPdfService(BlobContainerClient containerClient,
                               PdfMetadataRepository repository) {
        this.containerClient = containerClient;
        this.repository = repository;
    }

    /**
     * Faz upload de PDF e salva metadata no PostgreSQL
     */
    public PdfMetadataDTO uploadPdf(MultipartFile file) {
        // Validação: só PDF permitido
        if (file == null || file.isEmpty() || !file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            throw new InvalidPdfException("Somente arquivos PDF são permitidos");
        }
        // Checa o tamanho máximo
        long maxSize = Long.parseLong(System.getenv("MAX_UPLOAD_SIZE"));
        if (file.getSize() > maxSize) {
            throw new InvalidPdfSizeException("O arquivo excede o tamanho máximo permitido de " + maxSize + " bytes.");
        }
        //  Gera UUID aleatório
        String uuid = UUID.randomUUID().toString();

        // Nome do blob: UUID + '_' + nome original
        String blobName = uuid + "_" + file.getOriginalFilename();

        //  Cria BlobClient
        BlobClient blobClient = containerClient.getBlobClient(blobName);

        // Faz upload para o Azure Blob
        try (InputStream is = file.getInputStream()) {
            blobClient.upload(is, file.getSize(), true); // true = sobrescrever se existir
        } catch (Exception e) {
            throw new AzureUploadException("Falha ao enviar PDF para Azure Blob", e);
        }

        // Cria metadata
        PdfMetadata metadata = PdfMetadata.builder()
                .id(uuid)
                .fileName(file.getOriginalFilename())
                .size(file.getSize())
                .uploadedAt(Instant.now())
                .build();

        //  Salva no banco; se falhar, remove blob
        try {
            repository.save(metadata);
        } catch (Exception e) {
            try { blobClient.delete(); } catch (Exception ex) {}
            throw new DBSaveException("Falha ao salvar metadata no banco", e);
        }

        // Retorna DTO
        return PdfMetadataDTO.builder()
                .id(metadata.getId())
                .fileName(metadata.getFileName())
                .size(metadata.getSize())
                .build();
    }

}
