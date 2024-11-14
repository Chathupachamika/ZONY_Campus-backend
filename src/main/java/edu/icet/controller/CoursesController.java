package edu.icet.controller;

import edu.icet.dto.Course;
import edu.icet.dto.Register;
import edu.icet.service.CourseService;
import edu.icet.service.RegisterService;
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
@RequestMapping("/course")
@RequiredArgsConstructor
public class CoursesController {
    @Autowired
    private final CourseService service;

    @GetMapping("/get-all")
    public ResponseEntity<List<Course>> getCourse() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/add-user")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> addCourse(@RequestPart("user") Course course,
                                                       @RequestPart("imageFile") MultipartFile imageFile) {
        Map<String, String> response = new HashMap<>();
        service.addCourse(course, imageFile);
        response.put("message", "Course created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/searchCourse-by-id/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        try {
            Course course = service.searchCourseById(id);
            return ResponseEntity.ok(course);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found with ID: " + id);
        }
    }

    @DeleteMapping("/deleteCourse-by-id/{id}")
    public ResponseEntity<Map<String, String>> deleteCourseById(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            service.deleteCourseById(id);
            response.put("message", "Course deleted successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (EntityNotFoundException e) {
            response.put("error", "Course not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/update-course")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Map<String, String>> updateLecturer(
            @RequestPart("course") Course course,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        Map<String, String> response = new HashMap<>();
        try {
            service.updateCourseById(course, imageFile);
            response.put("message", "Course updated successfully");
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.put("error", "Course not found with ID: " + course.getCourseId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
