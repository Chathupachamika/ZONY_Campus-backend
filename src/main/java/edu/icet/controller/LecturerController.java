package edu.icet.controller;

import edu.icet.dto.Lecturer;
import edu.icet.entity.LecturerEntity;
import edu.icet.service.LecturerService;
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
@RequestMapping("/lecturer")
@RequiredArgsConstructor
public class LecturerController {
    @Autowired
    private final LecturerService service;

    @GetMapping("/get-allLecturer")
    public ResponseEntity<List<Lecturer>> getLecturers() {
        return ResponseEntity.ok(service.getAllLecturer());
    }

    @PostMapping("/add-lecturer")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> addLecturer(@RequestPart("lecturer") Lecturer lecturer,
                                                           @RequestPart("imageFile") MultipartFile imageFile) {
        Map<String, String> response = new HashMap<>();
        try {
            service.addLecturer(lecturer, imageFile);
            response.put("message", "Lecturer created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            response.put("error", "Error creating lecturer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/searchLecturer-by-id/{id}")
    public ResponseEntity<?> getLecturerById(@PathVariable Long id) {
        try {
            Lecturer lecturer = service.searchLecturerById(id);
            return ResponseEntity.ok(lecturer);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lecturer not found with ID: " + id);
        }
    }

    @GetMapping("/searchLecturer-by-faculty/{faculty}")
    public ResponseEntity<?> getLecturerByFaculty(@PathVariable String faculty) {
        try {
            List<LecturerEntity> lecturers = service.searchLecturerByFaculty(faculty);
            if (lecturers.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No lecturers found for faculty: " + faculty);
            }
            return ResponseEntity.ok(lecturers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while searching for lecturers.");
        }
    }

    @DeleteMapping("/deleteLecturer-by-id/{id}")
    public ResponseEntity<Map<String, String>> deleteLecturerById(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            service.deleteLecturerById(id);
            response.put("message", "Lecturer deleted successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (EntityNotFoundException e) {
            response.put("error", "Lecturer not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/update-lecturer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Map<String, String>> updateLecturer(
            @RequestPart("lecturer") Lecturer lecturer,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        Map<String, String> response = new HashMap<>();
        try {
            service.updateLecturerById(lecturer, imageFile);
            response.put("message", "Lecturer updated successfully");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("error", "Error updating lecturer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (EntityNotFoundException e) {
            response.put("error", "Lecturer not found with ID: " + lecturer.getLecturer_id());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
