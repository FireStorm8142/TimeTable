package com.johan.timetable.academic;

import com.johan.timetable.academic.dto.AcademicYearRequest;
import com.johan.timetable.academic.dto.AcademicYearResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AcademicYearService {

    private final AcademicYearRepository repository;

    public AcademicYearService(AcademicYearRepository repository) {
        this.repository = repository;
    }

    public AcademicYearResponse create(AcademicYearRequest request) {
        String name = request.name().trim();

        if (repository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Academic year already exists");
        }

        AcademicYear year = new AcademicYear(
                name,
                request.startDate(),
                request.endDate()
        );

        return toResponse(repository.save(year));
    }

    @Transactional(readOnly = true)
    public List<AcademicYearResponse> findAll() {
        return repository.findAllByOrderByStartDateDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AcademicYearResponse findById(UUID id) {
        return toResponse(getYear(id));
    }

    private AcademicYear getYear(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Academic year not found"));
    }

    private AcademicYearResponse toResponse(AcademicYear year) {
        return new AcademicYearResponse(
                year.getId(),
                year.getName(),
                year.getStartDate(),
                year.getEndDate(),
                year.isActive(),
                year.getCreatedAt(),
                year.getUpdatedAt()
        );
    }
}
