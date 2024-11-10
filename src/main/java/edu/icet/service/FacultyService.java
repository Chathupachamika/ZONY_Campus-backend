package edu.icet.service;

import edu.icet.dto.Faculty;
import edu.icet.dto.Register;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FacultyService {
    List<Faculty> getAll();
    void addFaculty(Faculty faculty, MultipartFile imageFile) throws IOException;
    Faculty searchFacultyById(Long id);
    void deleteFacultyById(Long id);
    void updateFacultyById(Faculty faculty, MultipartFile imageFile) throws IOException;
    }
