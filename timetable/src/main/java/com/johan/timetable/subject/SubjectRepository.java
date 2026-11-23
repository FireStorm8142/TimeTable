package com.johan.timetable.subject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubjectRepository
        extends JpaRepository<Subject, UUID> {

    List<Subject> findAllByActiveTrueOrderByNameAsc();

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(
            String name, UUID id);
}
