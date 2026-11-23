package com.johan.timetable.academic;

import com.johan.timetable.academic.dto.GradeResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService service;

    public GradeController(GradeService service) {
        this.service = service;
    }

    @GetMapping
    public List<GradeResponse> findAll() {
        return service.findAll();
    }
}
