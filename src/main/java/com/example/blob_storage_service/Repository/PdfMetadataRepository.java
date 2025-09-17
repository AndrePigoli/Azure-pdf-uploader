package com.example.blob_storage_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blob_storage_service.Models.PdfMetadata;


//Repositorio para herdar metodos JPA
public interface PdfMetadataRepository extends JpaRepository<PdfMetadata, String> {
    
}
