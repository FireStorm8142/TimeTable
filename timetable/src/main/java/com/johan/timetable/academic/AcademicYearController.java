package com.johan.timetable.academic;

import com.johan.timetable.academic.dto.AcademicYearRequest;
import com.johan.timetable.academic.dto.AcademicYearResponse;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/academic-years")
public class AcademicYearController {

    private final AcademicYearService service;

    public AcademicYearController(AcademicYearService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AcademicYearResponse create(
            @Valid @RequestBody AcademicYearRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<AcademicYearResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public AcademicYearResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }
}
