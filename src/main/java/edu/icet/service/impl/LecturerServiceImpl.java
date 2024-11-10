package edu.icet.service.impl;

import edu.icet.dto.Lecturer;
import edu.icet.entity.FacultyEntity;
import edu.icet.entity.LecturerEntity;
import edu.icet.repository.FacultyRepository;
import edu.icet.repository.LecturerRepository;
import edu.icet.service.LecturerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LecturerServiceImpl implements LecturerService {
    private final LecturerRepository lecturerRepository;
    private final FacultyRepository facultyRepository;
    private final ModelMapper mapper;

    @Override
    public List<Lecturer> getAllLecturer() {
        return lecturerRepository.findAll().stream()
                .map(entity -> mapper.map(entity, Lecturer.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addLecturer(Lecturer lecturer, MultipartFile imageFile) throws IOException {
        LecturerEntity entity = mapper.map(lecturer, LecturerEntity.class);

//        if (lecturer.getFacultyEntity() != null && lecturer.getFacultyEntity().getFacultyId() != null) {
//            FacultyEntity faculty = facultyRepository.findById(lecturer.getFacultyEntity().getFacultyId())
//                    .orElseThrow(() -> new EntityNotFoundException("Faculty with ID " + lecturer.getFacultyEntity().getFacultyId() + " not found"));
//            entity.setFaculty(faculty);
//        }

        if (imageFile != null && !imageFile.isEmpty()) {
            entity.setLecturerImageName(imageFile.getOriginalFilename());
            entity.setLecturerImageType(imageFile.getContentType());
            entity.setLecturerImageData(imageFile.getBytes());
        }

        lecturerRepository.save(entity);
    }

    @Override
    public Lecturer searchLecturerById(Long id) {
        return lecturerRepository.findById(id)
                .map(entity -> mapper.map(entity, Lecturer.class))
                .orElseThrow(() -> new EntityNotFoundException("Lecturer with ID " + id + " not found"));
    }

    @Override
    public void deleteLecturerById(Long id) {
        if (lecturerRepository.existsById(id)) {
            lecturerRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Lecturer with ID " + id + " not found");
        }
    }

    @Override
    public void updateLecturerById(Lecturer lecturer, MultipartFile imageFile) throws IOException {
        LecturerEntity existingEntity = lecturerRepository.findById(lecturer.getLecturer_id())
                .orElseThrow(() -> new EntityNotFoundException("Lecturer with ID " + lecturer.getLecturer_id() + " not found"));

        existingEntity.setLecturerName(lecturer.getLecturerName());
        existingEntity.setLecturerExperience(lecturer.getLecturerExperience());
        existingEntity.setLecturerDegrees(lecturer.getLecturerDegrees());

//        if (lecturer.getFacultyEntity() != null && lecturer.getFacultyEntity().getFacultyId() != null) {
//            FacultyEntity faculty = facultyRepository.findById(lecturer.getFacultyEntity().getFacultyId())
//                    .orElseThrow(() -> new EntityNotFoundException("Faculty with ID " + lecturer.getFacultyEntity().getFacultyId() + " not found"));
//            existingEntity.setFaculty(faculty);
//        }

        if (imageFile != null && !imageFile.isEmpty()) {
            existingEntity.setLecturerImageName(imageFile.getOriginalFilename());
            existingEntity.setLecturerImageType(imageFile.getContentType());
            existingEntity.setLecturerImageData(imageFile.getBytes());
        }

        lecturerRepository.save(existingEntity);
    }

    @Override
    public List<LecturerEntity> searchLecturerByFaculty(String faculty) {
        return lecturerRepository.findByFaculty(faculty);
    }


}
