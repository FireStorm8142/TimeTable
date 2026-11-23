package com.johan.timetable.studentgroup.dto;

import com.johan.timetable.studentgroup.StudentGroupType;

import java.time.Instant;
import java.util.UUID;

public record StudentGroupResponse(
        UUID id,
        UUID sectionId,
        String name,
        String code,
        StudentGroupType groupType,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {}
