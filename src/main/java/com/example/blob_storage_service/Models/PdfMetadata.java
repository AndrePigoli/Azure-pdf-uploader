package com.example.blob_storage_service.Models;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;




@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pdfs")
public class PdfMetadata {

    @Id
    @Column(length = 36)
    private String id;                 // UUID aleatório

    @Column(nullable = false)
    private String fileName;           // nome do PDF

    private long size;                 // tamanho em bytes

    private Instant uploadedAt;        // timestamp do upload

}
