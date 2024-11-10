package edu.icet.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Program {
    private Long programId;

    private String programName;
    private String programMission;
    private String programDetails;
    private String programVenue;
    private LocalDateTime programDateTime;

    private String programImageName;
    private String programImageType;

    private byte[] programImageData;
}
