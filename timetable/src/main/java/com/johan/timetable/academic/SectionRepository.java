
package com.johan.timetable.academic;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SectionRepository
        extends JpaRepository<Section, UUID> {

    List<Section> findAllByActiveTrueOrderBySectionCodeAsc();

    List<Section> findAllByAcademicYear_IdAndActiveTrueOrderByGrade_GradeNumberAscSectionCodeAsc(
            UUID academicYearId);

    boolean existsByAcademicYear_IdAndGrade_IdAndSectionCodeIgnoreCase(
            UUID academicYearId, UUID gradeId, String sectionCode);

    boolean existsByAcademicYear_IdAndGrade_IdAndSectionCodeIgnoreCaseAndIdNot(
            UUID academicYearId, UUID gradeId, String sectionCode, UUID id);
}
