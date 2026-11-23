package com.johan.timetable.academic;

import com.johan.timetable.academic.dto.SectionRequest;
import com.johan.timetable.academic.dto.SectionResponse;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    private final SectionService service;

    public SectionController(SectionService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SectionResponse create(
            @Valid @RequestBody SectionRequest request) {
        return service.create(request);
    }

    @GetMapping("/academic-year/{academicYearId}")
    public List<SectionResponse> findByAcademicYear(
            @PathVariable UUID academicYearId) {
        return service.findByAcademicYear(academicYearId);
    }

    @GetMapping("/{id}")
    public SectionResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }
}
