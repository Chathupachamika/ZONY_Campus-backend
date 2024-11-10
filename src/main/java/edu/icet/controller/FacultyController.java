package edu.icet.controller;

import edu.icet.dto.Faculty;
import edu.icet.service.FacultyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/faculty")
@RequiredArgsConstructor
public class FacultyController {
    @Autowired
    private final FacultyService service;

    @GetMapping("/get-allFaculty")
    public ResponseEntity<List<Faculty>> getFaculties() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/add-faculty")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> addFaculty(@RequestPart("faculty") Faculty faculty,
                                                          @RequestPart("imageFile") MultipartFile imageFile) {
        Map<String, String> response = new HashMap<>();
        try {
            service.addFaculty(faculty, imageFile);
            response.put("message", "Faculty created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            response.put("error", "Error creating faculty: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/searchFaculty-by-id/{id}")
    public ResponseEntity<?> getFacultyById(@PathVariable Long id) {
        try {
            Faculty faculty = service.searchFacultyById(id);
            return ResponseEntity.ok(faculty);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Faculty not found with ID: " + id);
        }
    }

    @DeleteMapping("/deleteFaculty-by-id/{id}")
    public ResponseEntity<Map<String, String>> deleteFacultyById(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            service.deleteFacultyById(id);
            response.put("message", "Faculty deleted successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (EntityNotFoundException e) {
            response.put("error", "Faculty not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/update-faculty")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Map<String, String>> updateFaculty(
            @RequestPart("faculty") Faculty faculty,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        Map<String, String> response = new HashMap<>();
        try {
            service.updateFacultyById(faculty, imageFile);
            response.put("message", "Faculty updated successfully");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("error", "Error updating faculty: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (EntityNotFoundException e) {
            response.put("error", "Faculty not found with ID: " + faculty.getFacultyId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
