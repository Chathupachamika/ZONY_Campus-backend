package edu.icet.controller;

import edu.icet.dto.Register;
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
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    @Autowired
    private final RegisterService service;

    @GetMapping("/get-all")
    public ResponseEntity<List<Register>> getUser() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/add-user")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> addUser(@RequestPart("user") Register register,
                                                           @RequestPart("imageFile") MultipartFile imageFile) {
        Map<String, String> response = new HashMap<>();
        try {
            service.addUser(register, imageFile);
            response.put("message", "User created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            response.put("error", "Error creating User: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/searchUser-by-id/{id}")
    public ResponseEntity<?> getLecturerById(@PathVariable Long id) {
        try {
            Register register = service.searchUserById(id);
            return ResponseEntity.ok(register);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
        }
    }

    @DeleteMapping("/deleteUser-by-id/{id}")
    public ResponseEntity<Map<String, String>> deleteLecturerById(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            service.deleteUserById(id);
            response.put("message", "User deleted successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (EntityNotFoundException e) {
            response.put("error", "User not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/update-user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Map<String, String>> updateLecturer(
            @RequestPart("user") Register register,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        Map<String, String> response = new HashMap<>();
        try {
            service.updateUserById(register, imageFile);
            response.put("message", "User updated successfully");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("error", "Error updating User: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (EntityNotFoundException e) {
            response.put("error", "Lecturer not found with ID: " + register.getReg_id());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
