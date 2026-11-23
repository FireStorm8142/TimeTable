package com.johan.timetable.studentgroup;

import com.johan.timetable.studentgroup.dto.StudentGroupRequest;
import com.johan.timetable.studentgroup.dto.StudentGroupResponse;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/student-groups")
public class StudentGroupController {

    private final StudentGroupService service;

    public StudentGroupController(StudentGroupService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentGroupResponse create(
            @Valid @RequestBody StudentGroupRequest request) {
        return service.create(request);
    }

    @GetMapping("/section/{sectionId}")
    public List<StudentGroupResponse> findBySection(
            @PathVariable UUID sectionId) {
        return service.findBySection(sectionId);
    }

    @GetMapping("/{id}")
    public StudentGroupResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public StudentGroupResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody StudentGroupRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable UUID id) {
        service.deactivate(id);
    }
}
