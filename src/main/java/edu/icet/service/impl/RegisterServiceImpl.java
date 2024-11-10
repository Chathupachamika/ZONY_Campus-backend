package edu.icet.service.impl;

import edu.icet.dto.Register;
import edu.icet.entity.FacultyEntity;
import edu.icet.entity.RegisterEntity;
import edu.icet.repository.RegisterRepository;
import edu.icet.service.RegisterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private final RegisterRepository repository;
    private final ModelMapper mapper;

    @Override
    public List<Register> getAll() {
        List<Register> customerArrayList = new ArrayList<>();
        repository.findAll().forEach(entity->{
            customerArrayList.add(mapper.map(entity, Register.class));
        });
        return customerArrayList;
    }

    @Override
    public void addUser(Register user, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            user.setImageName(imageFile.getOriginalFilename());
            user.setImageType(imageFile.getContentType());
            user.setImageData(imageFile.getBytes());
        }
        RegisterEntity entity = mapper.map(user, RegisterEntity.class);
        repository.save(entity);
    }

    @Override
    public Register searchUserById(Long id) {
        Optional<RegisterEntity> entity = repository.findById(id);
        return entity.map(e -> mapper.map(e, Register.class)).orElse(null);
    }

    @Override
    public void deleteUserById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    @Override
    public void updateUserById(Register user, MultipartFile imageFile) throws IOException {
        Optional<RegisterEntity> existingEntityOpt = repository.findById(user.getReg_id());

        if (existingEntityOpt.isPresent()) {
            RegisterEntity existingEntity = existingEntityOpt.get();

            // Update user details
            existingEntity.setProgram(user.getProgram());
            existingEntity.setTitle(user.getTitle());
            existingEntity.setReferral(user.getReferral());
            existingEntity.setShortName(user.getShortName());
            existingEntity.setFullName(user.getFullName());
            existingEntity.setDob(user.getDob());
            existingEntity.setCountry(user.getCountry());
            existingEntity.setPassport(user.getPassport());
            existingEntity.setMobile(user.getMobile());
            existingEntity.setEmail(user.getEmail());
            existingEntity.setAddress(user.getAddress());

            // Set faculty if provided
//            if (user.getFaculty() != null) {
//                FacultyEntity facultyEntity = mapper.map(user.getFaculty(), FacultyEntity.class);
//                existingEntity.setFaculty(facultyEntity);
//            }

            existingEntity.setUsername(user.getUsername());
            existingEntity.setPassword(user.getPassword());

            // Update image data if a new image file is provided
            if (imageFile != null && !imageFile.isEmpty()) {
                existingEntity.setImageName(imageFile.getOriginalFilename());
                existingEntity.setImageType(imageFile.getContentType());
                existingEntity.setImageData(imageFile.getBytes());
            }

            // Save updated entity
            repository.save(existingEntity);
        } else {
            throw new EntityNotFoundException("User with ID " + user.getReg_id() + " not found");
        }
    }

}


