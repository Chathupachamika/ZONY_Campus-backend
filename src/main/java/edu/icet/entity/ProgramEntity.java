package edu.icet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "programs")
public class ProgramEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programId;

    private String programName;
    private String programMission;
    private String programDetails;
    private String programVenue;
    private LocalDateTime programDateTime;

    private String programImageName;
    private String programImageType;

    @Lob
    @Column(name = "imageData", columnDefinition = "BLOB")
    private byte[] programImageData;
}