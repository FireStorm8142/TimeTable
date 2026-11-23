package com.johan.timetable.academic.dto;

import java.util.UUID;

public record GradeResponse(
        UUID id,
        Integer gradeNumber,
        String name
) {}
