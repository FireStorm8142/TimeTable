package com.johan.timetable.studentgroup.dto;

import com.johan.timetable.studentgroup.StudentGroupType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record StudentGroupRequest(

        @NotNull
        UUID sectionId,

        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Size(max = 30)
        String code,

        @NotNull
        StudentGroupType groupType
) {}
