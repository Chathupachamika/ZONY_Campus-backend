package edu.icet.controller;

import edu.icet.dto.Program;
import edu.icet.service.ProgramService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/program")
@RequiredArgsConstructor
public class ProgramController {

    @Autowired
    private final ProgramService service;

    @GetMapping("/get-allPrograms")
    public ResponseEntity<List<Program>> getPrograms() {
        return ResponseEntity.ok(service.getAllProgram());
    }

    @PostMapping("/add-Program")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> addProgram(
            @RequestPart("Program") Program program,
            @RequestPart("imageFile") MultipartFile imageFile) {

        Map<String, String> response = new HashMap<>();
        try {
            service.addProgram(program, imageFile);
            response.put("message", "Program created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("error", "Program creation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/searchProgram-by-id/{id}")
    public ResponseEntity<?> getProgramById(@PathVariable Long id) {
        try {
            Program program = service.searchProgramById(id); // Updated return type to Program
            return ResponseEntity.ok(program);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Program not found with ID: " + id);
        }
    }

    @DeleteMapping("/deleteProgram-by-id/{id}")
    public ResponseEntity<Map<String, String>> deleteProgramById(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            service.deleteProgramById(id);
            response.put("message", "Program deleted successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (EntityNotFoundException e) {
            response.put("error", "Program not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/update-Program")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Map<String, String>> updateProgram(
            @RequestPart("Program") Program program,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

        Map<String, String> response = new HashMap<>();
        try {
            service.updateProgramById(program, imageFile);
            response.put("message", "Program updated successfully");
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.put("error", "Program not found with ID: " + program.getProgramId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
