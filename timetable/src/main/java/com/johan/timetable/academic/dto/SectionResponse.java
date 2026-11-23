package com.johan.timetable.academic.dto;

import java.time.Instant;
import java.util.UUID;

public record SectionResponse(
        UUID id,
        UUID academicYearId,
        String academicYearName,
        UUID gradeId,
        Integer gradeNumber,
        String gradeName,
        String sectionCode,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {}
