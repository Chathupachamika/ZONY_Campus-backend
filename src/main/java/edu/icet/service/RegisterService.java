package edu.icet.service;

import edu.icet.dto.Faculty;
import edu.icet.dto.Register;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RegisterService {

    List<Register> getAll();
    void addUser(Register user, MultipartFile imageFile) throws IOException;
    Register searchUserById(Long id);
    void deleteUserById(Long id);
    void updateUserById(Register user, MultipartFile imageFile) throws IOException;
}
