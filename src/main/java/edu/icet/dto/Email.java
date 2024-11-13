package edu.icet.dto;

import lombok.*;

@Setter
@Getter
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String email;
    private String password;
}
