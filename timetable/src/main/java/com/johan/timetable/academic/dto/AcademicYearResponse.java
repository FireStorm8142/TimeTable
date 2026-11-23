
package com.johan.timetable.academic.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record AcademicYearResponse(
        UUID id,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {}
