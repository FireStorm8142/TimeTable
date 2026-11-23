
package com.johan.timetable.studentgroup;

import com.johan.timetable.academic.Section;
import com.johan.timetable.academic.SectionRepository;
import com.johan.timetable.studentgroup.dto.StudentGroupRequest;
import com.johan.timetable.studentgroup.dto.StudentGroupResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class StudentGroupService {

    private final StudentGroupRepository repository;
    private final SectionRepository sectionRepository;

    public StudentGroupService(
            StudentGroupRepository repository,
            SectionRepository sectionRepository) {
        this.repository = repository;
        this.sectionRepository = sectionRepository;
    }

    public StudentGroupResponse create(StudentGroupRequest request) {
        Section section = getSection(request.sectionId());
        String code = normalize(request.code());

        if (repository.existsBySection_IdAndCodeIgnoreCase(
                section.getId(), code)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Student group code already exists in this section");
        }

        if (request.groupType() == StudentGroupType.WHOLE_SECTION
                && repository.existsBySection_IdAndGroupType(
                section.getId(), StudentGroupType.WHOLE_SECTION)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A whole-section group already exists");
        }

        StudentGroup group = new StudentGroup(
                section,
                request.name().trim(),
                code,
                request.groupType()
        );

        return toResponse(repository.save(group));
    }

    @Transactional(readOnly = true)
    public List<StudentGroupResponse> findBySection(UUID sectionId) {
        getSection(sectionId);

        return repository
                .findAllBySection_IdAndActiveTrueOrderByNameAsc(sectionId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudentGroupResponse findById(UUID id) {
        return toResponse(getActiveGroup(id));
    }

    public StudentGroupResponse update(
            UUID id, StudentGroupRequest request) {

        StudentGroup group = getActiveGroup(id);

        if (!group.getSection().getId().equals(request.sectionId())
                || group.getGroupType() != request.groupType()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Section and group type cannot be changed");
        }

        String code = normalize(request.code());

        if (repository.existsBySection_IdAndCodeIgnoreCaseAndIdNot(
                request.sectionId(), code, id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Student group code already exists");
        }

        group.setName(request.name().trim());
        group.setCode(code);

        return toResponse(repository.save(group));
    }

    public void deactivate(UUID id) {
        StudentGroup group = getActiveGroup(id);
        group.setActive(false);
        repository.save(group);
    }

    private Section getSection(UUID id) {
        return sectionRepository.findById(id)
                .filter(Section::isActive)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Active section not found"));
    }

    private StudentGroup getActiveGroup(UUID id) {
        return repository.findById(id)
                .filter(StudentGroup::isActive)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Student group not found"));
    }

    private String normalize(String value) {
        return value.trim().toUpperCase();
    }

    private StudentGroupResponse toResponse(StudentGroup group) {
        return new StudentGroupResponse(
                group.getId(),
                group.getSection().getId(),
                group.getName(),
                group.getCode(),
                group.getGroupType(),
                group.isActive(),
                group.getCreatedAt(),
                group.getUpdatedAt()
        );
    }
}
