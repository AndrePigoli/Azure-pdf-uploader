package com.example.blob_storage_service.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.BlobContainerClient;

@Configuration
public class AzureBlobConfig {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

    @Bean
    public BlobContainerClient blobContainerClient() {
        // Cria cliente do serviço Azure Storage
        BlobServiceClient serviceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        // Pega o cliente do container específico
        BlobContainerClient containerClient = serviceClient.getBlobContainerClient(containerName);

        // Cria o container se não existir
        if (!containerClient.exists()) {
            containerClient.create();
        }

        return containerClient;
    }
}