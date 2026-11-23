package com.johan.timetable.academic;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface GradeRepository
        extends JpaRepository<Grade, UUID> {

    List<Grade> findAllByOrderByGradeNumberAsc();
}
