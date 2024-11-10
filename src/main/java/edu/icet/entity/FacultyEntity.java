package edu.icet.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Transactional
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "faculty")
public class FacultyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faculty_id")
    private Long facultyId;

    private String name;
    private String description;
    private String specializations;
    private String icon;
    private String facImageName;
    private String facImageType;

    @Lob
    @Column(name = "fac_image_data", columnDefinition = "BLOB")
    private byte[] facImageData;
//
//    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<RegisterEntity> registeredUsers;
//
//
//    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
//    private List<LecturerEntity> lecturers;
}
