package com.johan.timetable.teacher;

import com.johan.timetable.teacher.dto.TeacherRequest;
import com.johan.timetable.teacher.dto.TeacherResponse;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService service;

    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherResponse create(
            @Valid @RequestBody TeacherRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<TeacherResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TeacherResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public TeacherResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody TeacherRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable UUID id) {
        service.deactivate(id);
    }
}
