
package com.johan.timetable.teacher.dto;

import java.time.Instant;
import java.util.UUID;

public record TeacherResponse(
        UUID id,
        String fullName,
        String email,
        String phone,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {}
