package com.example.blob_storage_service.Services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.PublicAccessType;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class AzureBlobService {

    private final String accountName = System.getenv("AZURE_STORAGE_ACCOUNT");
    private final String accountKey = System.getenv("AZURE_STORAGE_KEY");

    // Método privado que cria ou retorna o container "pdf"
    private BlobContainerClient getContainerClient() {
        String connectionString = String.format(
                "DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net",
                accountName, accountKey
        );

        BlobContainerClient containerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName("pdf") // sempre "pdf"
                .buildClient();

        if (!containerClient.exists()) {
            containerClient.create();
            containerClient.setAccessPolicy(PublicAccessType.CONTAINER, null);
            log.info("Container 'pdf' criado no Azure Blob Storage.");
        }

        return containerClient;
    }

    // Método público de upload de PDF
    public String uploadPdf(MultipartFile file) throws IOException {
        BlobContainerClient containerClient = getContainerClient();

        String blobName = file.getOriginalFilename();
        BlobClient blobClient = containerClient.getBlobClient(blobName);

        blobClient.upload(file.getInputStream(), file.getSize(), true);
        log.info("Arquivo '{}' enviado com sucesso para o container 'pdf'.", blobName);

        return blobClient.getBlobUrl();
    }

    @PostConstruct
    public void init() {
        log.info("AZURE_STORAGE_ACCOUNT = " + System.getenv("AZURE_STORAGE_ACCOUNT"));
        log.info("AZURE_STORAGE_KEY = " + System.getenv("AZURE_STORAGE_KEY"));
    }
}
