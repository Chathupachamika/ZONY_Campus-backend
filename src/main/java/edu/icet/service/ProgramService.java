package edu.icet.service;

import edu.icet.dto.Lecturer;
import edu.icet.dto.Program;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProgramService {
    List<Program> getAllProgram();
    void addProgram(Program program, MultipartFile imageFile);
    void updateProgramById(Program program, MultipartFile imageFile);
    void deleteProgramById(Long id);
    Program searchProgramById(Long id);
}
