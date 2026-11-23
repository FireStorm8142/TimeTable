package com.johan.timetable.academic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record SectionRequest(

        @NotNull
        UUID academicYearId,

        @NotNull
        UUID gradeId,

        @NotBlank
        @Size(max = 10)
        String sectionCode

) {}
