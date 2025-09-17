package com.example.blob_storage_service.Models.DTOs;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PdfMetadataDTO {
    private String id;
    private String fileName;
    private long size;
}
