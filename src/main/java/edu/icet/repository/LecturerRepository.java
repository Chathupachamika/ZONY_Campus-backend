package edu.icet.repository;

import edu.icet.entity.LecturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LecturerRepository extends JpaRepository<LecturerEntity,Long> {
    List<LecturerEntity> findByFaculty(String faculty);
}
