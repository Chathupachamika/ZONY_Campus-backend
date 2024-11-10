package edu.icet.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Register {

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
    private Faculty faculty;
    private String password;

    private String imageName;
    private String imageType;
    private byte[] imageData;


}
