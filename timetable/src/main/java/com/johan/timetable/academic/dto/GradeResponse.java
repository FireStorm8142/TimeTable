package com.johan.timetable.academic.dto;

import java.util.UUID;

public record GradeResponse(
        UUID id,
        Short gradeNumber,
        String name
) {}
