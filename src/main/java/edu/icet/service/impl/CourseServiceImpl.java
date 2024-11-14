package edu.icet.service.impl;

import edu.icet.dto.Course;
import edu.icet.entity.CourseEntity;
import edu.icet.repository.CourseRepository;
import edu.icet.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private final ModelMapper mapper;

    @Override
    public List<Course> getAll() {
        return repository.findAll().stream()
                .map(courseEntity -> mapper.map(courseEntity, Course.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addCourse(Course course, MultipartFile imageFile) {
        try {
            CourseEntity courseEntity = mapper.map(course, CourseEntity.class);
            courseEntity.setCourseImageData(imageFile.getBytes());
            repository.save(courseEntity);
        } catch (IOException e) {
            e.printStackTrace();
            // handle exception appropriately
        }
    }

    @Override
    public Course searchCourseById(Long id) {
        return repository.findById(id)
                .map(courseEntity -> mapper.map(courseEntity, Course.class))
                .orElse(null);
    }

    @Override
    public void deleteCourseById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateCourseById(Course course, MultipartFile imageFile) {
        try {
            CourseEntity existingCourse = repository.findById(course.getCourseId()).orElse(null);
            if (existingCourse != null) {
                mapper.map(course, existingCourse);
                if (imageFile != null && !imageFile.isEmpty()) {
                    existingCourse.setCourseImageData(imageFile.getBytes());
                }
                repository.save(existingCourse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}