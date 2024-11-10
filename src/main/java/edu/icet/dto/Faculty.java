package edu.icet.dto;

import edu.icet.entity.FacultyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Faculty {
    private Long facultyId;
    private String name;
    private String description;
    private String specializations;
    private String icon;
    private FacultyEntity faculty;

    private String facImageName;
    private String faImageType;
    private byte[] facImageData;
}