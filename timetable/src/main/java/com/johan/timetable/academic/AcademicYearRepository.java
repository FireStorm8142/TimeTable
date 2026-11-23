
package com.johan.timetable.academic;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface AcademicYearRepository
        extends JpaRepository<AcademicYear, UUID> {

    List<AcademicYear> findAllByOrderByStartDateDesc();

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
}
