package edu.icet.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Transactional
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    private String courseName;
    private String subjects;
    private Double courseFee;
    private String description;
    private String courseImageName;
    private String courseImageType;

    @Lob
    @Column(name = "course_image_data", columnDefinition = "BLOB")
    private byte[] courseImageData;
}
