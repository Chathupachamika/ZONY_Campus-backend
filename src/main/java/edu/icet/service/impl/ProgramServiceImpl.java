package edu.icet.service.impl;

import edu.icet.dto.Program;
import edu.icet.entity.ProgramEntity;
import edu.icet.repository.ProgramRepository;
import edu.icet.service.ProgramService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;

    @Override
    public List<Program> getAllProgram() {
        return programRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addProgram(Program program, MultipartFile imageFile) {
        ProgramEntity programEntity = convertToEntity(program);
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                programEntity.setProgramImageName(imageFile.getOriginalFilename());
                programEntity.setProgramImageType(imageFile.getContentType());
                programEntity.setProgramImageData(imageFile.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to store image file: " + e.getMessage());
            }
        }
        programRepository.save(programEntity);
    }

    @Override
    public void updateProgramById(Program program, MultipartFile imageFile) {
        Optional<ProgramEntity> existingProgramOpt = programRepository.findById(program.getProgramId());
        if (existingProgramOpt.isPresent()) {
            ProgramEntity existingProgram = existingProgramOpt.get();
            existingProgram.setProgramName(program.getProgramName());
            existingProgram.setProgramMission(program.getProgramMission());
            existingProgram.setProgramDetails(program.getProgramDetails());
            existingProgram.setProgramVenue(program.getProgramVenue());
            existingProgram.setProgramDateTime(program.getProgramDateTime());

            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    existingProgram.setProgramImageName(imageFile.getOriginalFilename());
                    existingProgram.setProgramImageType(imageFile.getContentType());
                    existingProgram.setProgramImageData(imageFile.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to store image file: " + e.getMessage());
                }
            }

            programRepository.save(existingProgram);
        } else {
            throw new EntityNotFoundException("Program not found with ID: " + program.getProgramId());
        }
    }

    @Override
    public void deleteProgramById(Long id) {
        if (programRepository.existsById(id)) {
            programRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Program not found with ID: " + id);
        }
    }

    @Override
    public Program searchProgramById(Long id) {
        ProgramEntity programEntity = programRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Program not found with ID: " + id));
        return convertToDto(programEntity);
    }

    private Program convertToDto(ProgramEntity programEntity) {
        return new Program(
                programEntity.getProgramId(),
                programEntity.getProgramName(),
                programEntity.getProgramMission(),
                programEntity.getProgramDetails(),
                programEntity.getProgramVenue(),
                programEntity.getProgramDateTime(),
                programEntity.getProgramImageName(),
                programEntity.getProgramImageType(),
                programEntity.getProgramImageData()
        );
    }

    private ProgramEntity convertToEntity(Program program) {
        ProgramEntity programEntity = new ProgramEntity();
        programEntity.setProgramId(program.getProgramId());
        programEntity.setProgramName(program.getProgramName());
        programEntity.setProgramMission(program.getProgramMission());
        programEntity.setProgramDetails(program.getProgramDetails());
        programEntity.setProgramVenue(program.getProgramVenue());
        programEntity.setProgramDateTime(program.getProgramDateTime());
        programEntity.setProgramImageName(program.getProgramImageName());
        programEntity.setProgramImageType(program.getProgramImageType());
        programEntity.setProgramImageData(program.getProgramImageData());
        return programEntity;
    }
}
