package edu.icet.service.impl;

import edu.icet.dto.Faculty;
import edu.icet.entity.FacultyEntity;
import edu.icet.repository.FacultyRepository;
import edu.icet.service.FacultyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {
    @Autowired
    private final FacultyRepository repository;
    private final ModelMapper mapper;

    @Override
    public List<Faculty> getAll() {
        return repository.findAll().stream()
                .map(entity -> mapper.map(entity, Faculty.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addFaculty(Faculty faculty, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            faculty.setFacImageName(imageFile.getOriginalFilename());
            faculty.setFaImageType(imageFile.getContentType());
            faculty.setFacImageData(imageFile.getBytes());
        }
        FacultyEntity entity = mapper.map(faculty, FacultyEntity.class);
        repository.save(entity);
    }

    @Override
    public Faculty searchFacultyById(Long id) {
        return repository.findById(id)
                .map(entity -> mapper.map(entity, Faculty.class))
                .orElse(null);
    }

    @Override
    public void deleteFacultyById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    @Override
    public void updateFacultyById(Faculty faculty, MultipartFile imageFile) throws IOException {
        Optional<FacultyEntity> existingEntityOpt = repository.findById(faculty.getFacultyId());

        if (existingEntityOpt.isPresent()) {
            FacultyEntity existingEntity = existingEntityOpt.get();

            existingEntity.setName(faculty.getName());
            existingEntity.setDescription(faculty.getDescription());
            existingEntity.setSpecializations(faculty.getSpecializations());
            existingEntity.setIcon(faculty.getIcon());

            if (imageFile != null && !imageFile.isEmpty()) {
                existingEntity.setFacImageName(imageFile.getOriginalFilename());
                existingEntity.setFacImageType(imageFile.getContentType());
                existingEntity.setFacImageData(imageFile.getBytes());
            }

            repository.save(existingEntity);
        } else {
            throw new EntityNotFoundException("Faculty with ID " + faculty.getFacultyId() + " not found");
        }
    }
}
