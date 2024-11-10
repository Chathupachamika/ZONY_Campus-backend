package edu.icet.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "registered_guys")
public class RegisterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reg_id;
    private String program;
    private String title;
    private String referral;
    private String shortName;
    private String fullName;
    private Date dob;
    private String country;
    private String passport;
    private String mobile;
    private String email;
    private String address;
    private String username;
    private String password;

    private String imageName;
    private String imageType;

    @Lob
    @Column(name = "imageData", columnDefinition = "BLOB")
    private byte[] imageData;

//    @ManyToOne
//    @JoinColumn(name = "fk_faculty_id")
//    @JsonBackReference
//    private FacultyEntity faculty;

}
