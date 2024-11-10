package edu.icet.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lecturer")
public class LecturerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecturer_id")
    private Long lecturer_id;

    private String lecturerName;
    private String lecturerExperience;
    private String lecturerDegrees;
    private String learnSubjects;
    private String faculty;

    private String lecturerImageName;
    private String lecturerImageType;

    @Lob
    @Column(name = "lecturer_image_data", columnDefinition = "BLOB")
    private byte[] lecturerImageData;

//    @ManyToOne
//    @JoinColumn(name = "fk_faculty_id")
//    @JsonBackReference
//    private FacultyEntity faculty;

}
