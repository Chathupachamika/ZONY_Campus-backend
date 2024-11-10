package edu.icet.dto;

import edu.icet.entity.FacultyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lecturer {
    private Long lecturer_id;
    private String lecturerName;
    private String lecturerExperience;
    private String lecturerDegrees;
    private FacultyEntity facultyEntity;
    private String learnSubjects;
    private String faculty;

    private String lecturerImageName;
    private String lecturerImageType;
    private byte[] lecturerImageData;
}
