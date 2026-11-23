
package com.johan.timetable.studentgroup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentGroupRepository
        extends JpaRepository<StudentGroup, UUID> {

    List<StudentGroup> findAllBySection_IdAndActiveTrueOrderByNameAsc(
            UUID sectionId);

    boolean existsBySection_IdAndCodeIgnoreCase(
            UUID sectionId, String code);

    boolean existsBySection_IdAndCodeIgnoreCaseAndIdNot(
            UUID sectionId, String code, UUID id);

    boolean existsBySection_IdAndGroupType(
            UUID sectionId, StudentGroupType groupType);
}
