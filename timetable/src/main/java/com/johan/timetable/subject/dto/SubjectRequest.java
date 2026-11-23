package com.johan.timetable.subject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SubjectRequest(

        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 30)
        String code

) {}
