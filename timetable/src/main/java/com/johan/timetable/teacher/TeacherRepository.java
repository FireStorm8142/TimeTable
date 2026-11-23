package com.johan.timetable.teacher;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TeacherRepository
        extends JpaRepository<Teacher, UUID> {

    List<Teacher> findAllByActiveTrueOrderByFullNameAsc();

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(
            String email, UUID id);
}
