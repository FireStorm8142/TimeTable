package com.johan.timetable.subject;

import com.johan.timetable.subject.dto.SubjectRequest;
import com.johan.timetable.subject.dto.SubjectResponse;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService service;

    public SubjectController(SubjectService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectResponse create(
            @Valid @RequestBody SubjectRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<SubjectResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SubjectResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public SubjectResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody SubjectRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable UUID id) {
        service.deactivate(id);
    }
}
