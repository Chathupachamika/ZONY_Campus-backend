package edu.icet.service;

import edu.icet.dto.Lecturer;
import edu.icet.entity.LecturerEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LecturerService {
    List<Lecturer> getAllLecturer();
    void addLecturer(Lecturer lecturer, MultipartFile imageFile) throws IOException;
    Lecturer searchLecturerById(Long id);
    void deleteLecturerById(Long id);
    void updateLecturerById(Lecturer lecturer, MultipartFile imageFile) throws IOException;

    List<LecturerEntity> searchLecturerByFaculty(String faculty);
}
