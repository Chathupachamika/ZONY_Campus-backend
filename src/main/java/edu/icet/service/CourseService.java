package edu.icet.service;

import edu.icet.dto.Course;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    List<Course> getAll();
    void addCourse(Course course, MultipartFile imageFile);
    Course searchCourseById(Long id);
    void deleteCourseById(Long id);
    void updateCourseById(Course course, MultipartFile imageFile);
}
