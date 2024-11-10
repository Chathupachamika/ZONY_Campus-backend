package edu.icet.repository;

import edu.icet.entity.ProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<ProgramEntity,Long> {

}
