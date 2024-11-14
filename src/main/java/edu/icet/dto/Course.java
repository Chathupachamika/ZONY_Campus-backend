package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course {
    private Long courseId;
    private String courseName;
    private String subjects;
    private Double courseFee;
    private String description;
    private String courseImageName;
    private String courseImageType;
    private byte[] courseImageData;
}
