package com.johan.timetable.academic;

import com.johan.timetable.academic.dto.SectionRequest;
import com.johan.timetable.academic.dto.SectionResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SectionService {

    private final SectionRepository repository;
    private final AcademicYearRepository academicYearRepository;
    private final GradeRepository gradeRepository;

    public SectionService(
            SectionRepository repository,
            AcademicYearRepository academicYearRepository,
            GradeRepository gradeRepository) {
        this.repository = repository;
        this.academicYearRepository = academicYearRepository;
        this.gradeRepository = gradeRepository;
    }

    public SectionResponse create(SectionRequest request) {

        AcademicYear year = academicYearRepository
                .findById(request.academicYearId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Academic year not found"));

        Grade grade = gradeRepository
                .findById(request.gradeId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Grade not found"));

        String code = request.sectionCode().trim().toUpperCase();

        if (repository
                .existsByAcademicYear_IdAndGrade_IdAndSectionCodeIgnoreCase(
                        year.getId(), grade.getId(), code)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Section already exists for this grade and academic year");
        }

        Section section = new Section(year, grade, code);

        return toResponse(repository.save(section));
    }

    @Transactional(readOnly = true)
    public List<SectionResponse> findByAcademicYear(UUID academicYearId) {

        if (!academicYearRepository.existsById(academicYearId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Academic year not found");
        }

        return repository
                .findAllByAcademicYear_IdAndActiveTrueOrderByGrade_GradeNumberAscSectionCodeAsc(
                        academicYearId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SectionResponse findById(UUID id) {
        Section section = repository.findById(id)
                .filter(Section::isActive)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Section not found"));

        return toResponse(section);
    }

    private SectionResponse toResponse(Section section) {
        return new SectionResponse(
                section.getId(),
                section.getAcademicYear().getId(),
                section.getAcademicYear().getName(),
                section.getGrade().getId(),
                section.getGrade().getGradeNumber(),
                section.getGrade().getName(),
                section.getSectionCode(),
                section.isActive(),
                section.getCreatedAt(),
                section.getUpdatedAt()
        );
    }
}
